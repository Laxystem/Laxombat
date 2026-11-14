package quest.laxla.laxombat

import dev.architectury.registry.CreativeTabOutput
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase
import kotlin.experimental.ExperimentalTypeInference

typealias RegistryKey<T> = ResourceKey<Registry<T>>

internal operator fun <T : Any> RegistryKey<T>.invoke(): DeferredRegister<T> =
	DeferredRegister.create(Laxombat.MOD_ID, this)

@PublishedApi
@JvmSynthetic
internal fun String.toLaxombatIdentifier() = Identifier.fromNamespaceAndPath(Laxombat.MOD_ID, this)

internal interface ItemPropertiesExtension {
	@Suppress("PropertyName")
	val `laxombat$id`: ResourceKey<Item>?
}

var Item.Properties.id: ResourceKey<Item>
	set(value) {
		setId(value)
	}
	get() = (this as ItemPropertiesExtension).`laxombat$id`
		?: throw NullPointerException("ID not set for Item.Properties")

fun ResourceKey<*>.toDescriptionComponent(suffix: String) =
	Component.translatable(identifier().toLanguageKey(registry().toShortLanguageKey(), suffix))

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> DeferredRegister<in T>.registerLaxombat(
	path: String,
	crossinline supplier: (Identifier) -> T
): RegistrySupplier<T> {
	val id = path.toLaxombatIdentifier()

	return register(id) {
		supplier(id)
	}
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T : Item> DeferredRegister<Item>.registerLaxombatItem(
	path: String,
	crossinline supplier: (Item.Properties) -> T
): RegistrySupplier<T> = registerLaxombat(path) { id: Identifier ->
	val key = ResourceKey.create(Registries.ITEM, id)

	supplier(Item.Properties().setId(key)).also {
		if (it is BlockItem) it.registerBlocks(Item.BY_BLOCK, it)
	}
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T : Block> DeferredRegister<Block>.registerLaxombatBlock(
	path: String,
	copy: BlockBehaviour? = null,
	crossinline supplier: (BlockBehaviour.Properties) -> T
): RegistrySupplier<T> = registerLaxombat(path) { id: Identifier ->
	val key = ResourceKey.create(Registries.BLOCK, id)
	val properties = if (copy == null) BlockBehaviour.Properties.of() else BlockBehaviour.Properties.ofFullCopy(copy)

	supplier(properties.setId(key))
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T : BlockItem> DeferredRegister<Item>.registerLaxombatBlockItem(
	block: RegistrySupplier<out Block>,
	path: String = block.id.path,
	crossinline supplier: (Block, Item.Properties) -> T
): RegistrySupplier<T> = registerLaxombatItem(path) {
	val block = block.get()

	supplier(block, it.useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures()))
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun DeferredRegister<Item>.registerLaxombatBlockItem(
	block: RegistrySupplier<out Block>,
	path: String = block.id.path
): RegistrySupplier<BlockItem> = registerLaxombatBlockItem(block, path, supplier = ::BlockItem)

@Suppress("FunctionName")
fun LootItem(
	item: RegistrySupplier<out ItemLike>,
	weight: Int = 1,
	quality: Int = 0
): UniformContainerBase.Builder<*> = LootItem(item.get().asItem(), weight, quality)

@Suppress("FunctionName")
fun LootItem(
	item: ItemLike,
	weight: Int = 1,
	quality: Int = 0
): UniformContainerBase.Builder<*> = LootItem.lootTableItem(item).setWeight(weight).setQuality(quality)

@Suppress("UnstableApiUsage")
fun CreativeTabOutput.acceptAfter(
	item: ItemLike,
	vararg items: RegistrySupplier<out ItemLike>,
	visibility: CreativeModeTab.TabVisibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
) = acceptAllAfter(
	item,
	items.map { it.get().asItem().defaultInstance },
	visibility
)

@Suppress("UnstableApiUsage")
fun CreativeTabOutput.acceptBefore(
	item: ItemLike,
	vararg items: RegistrySupplier<out ItemLike>,
	visibility: CreativeModeTab.TabVisibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
) = acceptAllBefore(
	item,
	items.map { it.get().asItem().defaultInstance },
	visibility
)
