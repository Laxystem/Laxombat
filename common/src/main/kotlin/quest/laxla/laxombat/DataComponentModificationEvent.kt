package quest.laxla.laxombat

import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.ItemAttributeModifiers
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface DataComponentModificationEvent {
	typealias Builder = context(Item, HolderLookup.Provider) (DataComponentMap.Builder) -> Unit

	operator fun Item.invoke(builder: Builder)
	operator fun RegistrySupplier<Item>.invoke(builder: Builder) = get()(builder)
	operator fun Collection<Item>.invoke(builder: Builder)

	fun onAll(vararg items: Item, builder: Builder) = items.toSet()(builder)
}

internal interface DataComponentMapBuilderExtension {
	@Suppress("FunctionName")
	fun <T> `laxombat$getOrDefault`(dataComponentType: DataComponentType<T & Any>, default: T): T
}

internal fun <T> DataComponentMap.Builder.getOrDefault(dataComponentType: DataComponentType<T & Any>, default: T) =
	(this as DataComponentMapBuilderExtension).`laxombat$getOrDefault`(dataComponentType, default)

data class Modifier(
	val amount: Double,
	val operation: AttributeModifier.Operation,
	val slot: EquipmentSlotGroup,
	val display: ItemAttributeModifiers.Display = ItemAttributeModifiers.Display.attributeModifiers()
)

data class ModifierKey(
	val attribute: Holder<Attribute>,
	val id: Identifier
)

operator fun Holder<Attribute>.get(id: Identifier) = ModifierKey(attribute = this, id)

fun ItemAttributeModifiers.Entry.separateKey(): Pair<ModifierKey, Modifier> =
	ModifierKey(attribute, modifier.id) to Modifier(modifier.amount, modifier.operation, slot, display)

@JvmInline
value class AttributesBuilderContext(
	private val defaultModifiers: Map<ModifierKey, Modifier>
) : Map<ModifierKey, Modifier> by defaultModifiers

/**
 * Constructs a new ATTRIBUTE_MODIFIERS data component while respecting defaults.
 */
@OptIn(ExperimentalContracts::class)
fun DataComponentMap.Builder.attributes(block: context(AttributesBuilderContext) ItemAttributeModifiers.Builder.() -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}

	val default = getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY)
	val modifiers = mutableMapOf<ModifierKey, Modifier>()

	for (entry in default.modifiers) {
		val (modifierKey, modifier) = entry.separateKey()
		val existingModifier = modifiers[modifierKey]

		fun makeErrorMessage(conflicting: String) =
			"Found two same-keyed attribute modifiers [$modifierKey.id] with different $conflicting [$existingModifier, $modifier]"

		when {
			existingModifier == null -> modifiers[modifierKey] = modifier
			existingModifier.slot != modifier.slot -> error(makeErrorMessage("operations"))

			existingModifier.operation != modifier.operation -> error(makeErrorMessage("slots"))

			else -> {
				if (existingModifier.display != modifier.display)
					Laxombat.logger.warn(makeErrorMessage("displays") + "; picking more recent option")

				modifiers[modifierKey] = Modifier(
					amount = existingModifier.amount + modifier.amount,
					operation = modifier.operation,
					slot = modifier.slot,
					display = modifier.display
				)
			}
		}
	}

	val builder = ItemAttributeModifiers.builder()

	context(AttributesBuilderContext(modifiers)) {
		builder.block()
	}

	val consumedModifierKeys = mutableSetOf<ModifierKey>()

	this[DataComponents.ATTRIBUTE_MODIFIERS] = ItemAttributeModifiers.builder().apply {
		for (entry in builder.build().modifiers) {
			val (modifierKey, modifier) = entry.separateKey()

			if (consumedModifierKeys.add(modifierKey)) modifierKey.attribute[modifierKey.id] = modifier
		}

		for ((modifierKey, modifier) in modifiers)
			if (consumedModifierKeys.add(modifierKey)) modifierKey.attribute[modifierKey.id] = modifier
	}.build()
}

context(builder: ItemAttributeModifiers.Builder)
operator fun Holder<Attribute>.set(id: Identifier, modifier: Modifier) {
	builder.add(this, AttributeModifier(id, modifier.amount, modifier.operation), modifier.slot, modifier.display)
}

// TODO: make this a delegate
context(context: AttributesBuilderContext, builder: ItemAttributeModifiers.Builder)
var baseAttackDamage: Double
	get() = context[ModifierKey(Attributes.ATTACK_DAMAGE, Item.BASE_ATTACK_DAMAGE_ID)]?.amount ?: 0.0
	set(amount) {
		Attributes.ATTACK_DAMAGE[Item.BASE_ATTACK_DAMAGE_ID] = Modifier(
			amount = amount,
			operation = AttributeModifier.Operation.ADD_VALUE,
			slot = EquipmentSlotGroup.MAINHAND
		)
	}

context(context: AttributesBuilderContext, builder: ItemAttributeModifiers.Builder)
var baseAttackSpeed: Double
	get() = context[ModifierKey(Attributes.ATTACK_SPEED, Item.BASE_ATTACK_SPEED_ID)]?.amount ?: 0.0
	set(amount) {
		Attributes.ATTACK_SPEED[Item.BASE_ATTACK_SPEED_ID] = Modifier(
			amount = amount,
			operation = AttributeModifier.Operation.ADD_VALUE,
			slot = EquipmentSlotGroup.MAINHAND
		)
	}

context(context: AttributesBuilderContext, builder: ItemAttributeModifiers.Builder)
var baseEntityInteractionRange: Double
	get() = context[ModifierKey(Attributes.ENTITY_INTERACTION_RANGE, Laxombat.baseEntityInteractionRangeID)]?.amount
		?: 0.0
	set(amount) {
		Attributes.ENTITY_INTERACTION_RANGE[Laxombat.baseEntityInteractionRangeID] = Modifier(
			amount = amount,
			operation = AttributeModifier.Operation.ADD_VALUE,
			slot = EquipmentSlotGroup.MAINHAND
		)
	}
