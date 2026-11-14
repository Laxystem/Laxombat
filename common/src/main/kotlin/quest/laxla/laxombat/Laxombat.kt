package quest.laxla.laxombat

import com.google.common.collect.Maps
import dev.architectury.event.events.common.LootEvent
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.CreativeTabRegistry.defer
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.*
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.item.equipment.EquipmentAssets
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem
import org.slf4j.LoggerFactory
import java.util.stream.Stream
import java.util.stream.StreamSupport

@Suppress("UnstableApiUsage")
object Laxombat {
	const val MOD_ID = "laxombat"

	@JvmSynthetic
	internal val logger = LoggerFactory.getLogger(MOD_ID)

	private val attributes = Registries.ATTRIBUTE()

//	val defaultEntityAttackReach = attributes.registerLaxombat("default_entity_attack_reach") {
//		RangedAttribute(it.toLanguageKey("attribute.name"), 2.5, 0.0, 64.0)
//			.setSyncable(true)
//	}

	val baseEntityInteractionRangeID = "base_entity_interaction_range".toLaxombatIdentifier()


	private val blocks = Registries.BLOCK()

	val roseGoldBlock = blocks.registerLaxombatBlock("rose_gold_block", Blocks.GOLD_BLOCK) {
		Block(it) // TODO: properties and tags
	}

	val roseGoldPillar = blocks.registerLaxombatBlock("rose_gold_pillar", Blocks.GOLD_BLOCK) {
		LaxombatDirectionalBlock(it)
	}

	private val items = Registries.ITEM()

	/**
	 * Development utility tag. Unused besides that.
	 */
	val allItems = TagKey.create(Registries.ITEM, "items".toLaxombatIdentifier())

	fun streamItems(parallel: Boolean): Stream<RegistrySupplier<Item>> =
		StreamSupport.stream(items.spliterator(), parallel)

	val roseGoldBlockItem = items.registerLaxombatBlockItem(roseGoldBlock)
	val roseGoldPillarItem = items.registerLaxombatBlockItem(roseGoldPillar)

	val roseGoldUpgradeSmithingTemplate = items.registerLaxombatItem("rose_gold_upgrade_smithing_template") {
		SmithingTemplateItem(
			it.id.toDescriptionComponent(suffix = "applies_to").withStyle(ChatFormatting.BLUE),
			it.id.toDescriptionComponent(suffix = "ingredients").withStyle(ChatFormatting.BLUE),
			it.id.toDescriptionComponent(suffix = "base_slot_description"),
			it.id.toDescriptionComponent(suffix = "additions_slot_description"),
			listOf(
				Identifier.withDefaultNamespace("container/slot/helmet"),
				Identifier.withDefaultNamespace("container/slot/sword"),
				Identifier.withDefaultNamespace("container/slot/chestplate"),
				Identifier.withDefaultNamespace("container/slot/pickaxe"),
				Identifier.withDefaultNamespace("container/slot/leggings"),
				Identifier.withDefaultNamespace("container/slot/axe"),
				Identifier.withDefaultNamespace("container/slot/boots"),
				Identifier.withDefaultNamespace("container/slot/hoe"),
				Identifier.withDefaultNamespace("container/slot/shovel"),
				Identifier.withDefaultNamespace("container/slot/nautilus_armor"),
				Identifier.withDefaultNamespace("container/slot/spear")
			),
			listOf(Identifier.withDefaultNamespace("container/slot/ingot")),
			it.rarity(Rarity.UNCOMMON)
		)
	}

	val roseGoldToolMaterial = ToolMaterial(
		BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
		509,
		10F,
		2.5F,
		8,
		ItemTags.GOLD_TOOL_MATERIALS
	)

	val roseGoldSword = items.registerLaxombatItem("rose_gold_sword") {
		Item(it.sword(roseGoldToolMaterial, 1.0F, 0.5F))
	}

	//TODO: val roseGoldSpear = items.registerLaxombatItem("rose_gold_spear") {
	//	Item(
	//		it
	//			.`arch$tab`(CreativeModeTabs.COMBAT)
	//			.sword(roseGoldToolMaterial, 1.0F, 0.5F)
	//	)
	//}

	val roseGoldShovel = items.registerLaxombatItem("rose_gold_shovel") {
		Item(it.shovel(roseGoldToolMaterial, -1.0F, -0.5F))
	}

	val roseGoldPickaxe = items.registerLaxombatItem("rose_gold_pickaxe") {
		Item(it.pickaxe(roseGoldToolMaterial, 0f, 0f))
	}

	val roseGoldAxe = items.registerLaxombatItem("rose_gold_axe") {
		Item(it.axe(roseGoldToolMaterial, 2.0F, -0.5F))
	}

	val roseGoldHoe = items.registerLaxombatItem("rose_gold_hoe") {
		Item(it.hoe(roseGoldToolMaterial, -1.5F, 0.75F))
	}

	val roseGoldArmorMaterial = ArmorMaterial(
		22,
		Maps.newEnumMap(
			mapOf(
				ArmorType.BOOTS to 2,
				ArmorType.LEGGINGS to 5,
				ArmorType.CHESTPLATE to 7,
				ArmorType.HELMET to 2,
				ArmorType.BODY to 7,
			)
		),
		8,
		SoundEvents.ARMOR_EQUIP_GOLD,
		1.0F,
		0.0F,
		ItemTags.REPAIRS_GOLD_ARMOR,
		EquipmentAssets.GOLD // TODO
	)

	val roseGoldBoots = items.registerLaxombatItem("rose_gold_boots") {
		Item(it.humanoidArmor(roseGoldArmorMaterial, ArmorType.BOOTS))
	}

	val roseGoldLeggings = items.registerLaxombatItem("rose_gold_leggings") {
		Item(it.humanoidArmor(roseGoldArmorMaterial, ArmorType.LEGGINGS))
	}

	val roseGoldChestplate = items.registerLaxombatItem("rose_gold_chestplate") {
		Item(it.humanoidArmor(roseGoldArmorMaterial, ArmorType.CHESTPLATE))
	}

	val roseGoldHelmet = items.registerLaxombatItem("rose_gold_helmet") {
		Item(it.humanoidArmor(roseGoldArmorMaterial, ArmorType.HELMET))
	}

	val roseGoldIngot = items.registerLaxombatItem("rose_gold_ingot",::Item)
	val roseGoldNugget = items.registerLaxombatItem("rose_gold_nugget", ::Item)


	fun register() {
		blocks.register()
		attributes.register()
		items.register()

		CreativeTabRegistry.modify(defer(CreativeModeTabs.TOOLS_AND_UTILITIES)) { featureFlags, output, op ->
			output.acceptAfter(
				Items.GOLDEN_HOE,
				roseGoldShovel,
				roseGoldPickaxe,
				roseGoldAxe,
				roseGoldHoe
			)
		}

		CreativeTabRegistry.modify(defer(CreativeModeTabs.INGREDIENTS)) { featureFlags, output, op ->
			output.acceptBefore(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, roseGoldUpgradeSmithingTemplate)
			output.acceptAfter(Items.GOLD_NUGGET, roseGoldNugget)
			output.acceptAfter(Items.GOLD_INGOT, roseGoldIngot)
		}

		CreativeTabRegistry.modify(defer(CreativeModeTabs.COMBAT)) { featureFlags, output, op ->
			output.acceptAfter(Items.GOLDEN_SWORD, roseGoldSword)
			output.acceptAfter(
				Items.GOLDEN_AXE,
				roseGoldAxe,
				visibility = CreativeModeTab.TabVisibility.PARENT_TAB_ONLY
			)

			output.acceptAfter(
				Items.GOLDEN_BOOTS,
				roseGoldHelmet,
				roseGoldChestplate,
				roseGoldLeggings,
				roseGoldBoots
			)
		}

		CreativeTabRegistry.modify(defer(CreativeModeTabs.BUILDING_BLOCKS)) { featureFlags, output, op ->
			output.acceptAfter(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, roseGoldBlockItem, roseGoldPillarItem)
		}

		LootEvent.MODIFY_LOOT_TABLE.register { registries, key, context, builtin ->
			if (builtin) when (key) {
				BuiltInLootTables.ABANDONED_CAMP_SECRET_CHEST -> LootPool.lootPool()
					.add(LootItem(roseGoldUpgradeSmithingTemplate, quality = 1))
					.add(EmptyLootItem.emptyItem().setWeight(2))

				BuiltInLootTables.ABANDONED_CAMP_COMMON_CHEST -> LootPool.lootPool()
					.add(LootItem(roseGoldUpgradeSmithingTemplate, quality = 2))
					.add(EmptyLootItem.emptyItem().setWeight(14))

				BuiltInLootTables.SPAWN_BONUS_CHEST,
				BuiltInLootTables.RUINED_PORTAL -> LootPool.lootPool()
					.add(LootItem(roseGoldUpgradeSmithingTemplate, quality = 1))
					.add(EmptyLootItem.emptyItem().setWeight(19))

				BuiltInLootTables.SIMPLE_DUNGEON -> LootPool.lootPool()
					.add(LootItem(roseGoldUpgradeSmithingTemplate))
					.add(EmptyLootItem.emptyItem())

				// TODO: archeology
				// BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON,
				// BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
				// BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
				// BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY,

				else -> null
			}?.let(context::addPool)
		}
	}

	fun DataComponentModificationEvent.register() {
		Items.POTION {
			it[DataComponents.MAX_STACK_SIZE] = 16
		}

		Items.SPLASH_POTION {
			it[DataComponents.MAX_STACK_SIZE] = 8
		}

		Items.LINGERING_POTION {
			it[DataComponents.MAX_STACK_SIZE] = 4
		}

		onAll(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.COPPER_SWORD, Items.GOLDEN_SWORD) {
			it.attributes {
				baseAttackDamage = 2.0
				baseAttackSpeed = 0.5
			}
		}

		Items.IRON_SWORD {
			it.attributes {
				baseAttackDamage = 3.0
				baseAttackSpeed = 0.5
			}
		}

		Items.DIAMOND_SWORD {
			it.attributes {
				baseAttackDamage = 4.0
				baseAttackSpeed = 0.5
			}
		}

		Items.NETHERITE_SWORD {
			it.attributes {
				baseAttackDamage = 5.0
				baseAttackSpeed = 0.5
			}
		}

		onAll(Items.WOODEN_SPEAR, Items.GOLDEN_SPEAR) {
			it.attributes {
				baseAttackDamage = 1.0
			}
		}

		onAll(Items.STONE_SPEAR, Items.COPPER_SPEAR) {
			it.attributes {
				baseAttackDamage = 2.0
			}
		}

		Items.IRON_SPEAR {
			it.attributes {
				baseAttackDamage = 3.0
			}
		}

		Items.DIAMOND_SPEAR {
			it.attributes {
				baseAttackDamage = 4.0
			}
		}

		Items.NETHERITE_SPEAR {
			it.attributes {
				baseAttackDamage = 5.0
			}
		}

		// TODO: axe enchants

		onAll(Items.WOODEN_AXE, Items.STONE_AXE, Items.COPPER_AXE, Items.GOLDEN_AXE) {
			it.attributes {
				baseAttackDamage = 3.0
				baseAttackSpeed = -0.5
			}
		}

		Items.IRON_AXE {
			it.attributes {
				baseAttackDamage = 4.0
				baseAttackSpeed = -0.5
			}
		}

		roseGoldAxe {
			it.attributes {
				baseEntityInteractionRangeID
			}
		}

		Items.DIAMOND_AXE {
			it.attributes {
				baseAttackDamage = 5.0
				baseAttackSpeed = -0.5
			}
		}

		Items.NETHERITE_AXE {
			it.attributes {
				baseAttackDamage = 6.0
				baseAttackSpeed = -0.5
			}
		}

		Items.TRIDENT {
			// TODO: shooting from dispensers
			it.attributes {
				baseAttackDamage = 5.0
				baseAttackSpeed = -0.5
				baseEntityInteractionRange = 1.0
			}
		}

		Items.WOODEN_HOE {
			it.attributes {
				baseAttackDamage = 0.0
				baseAttackSpeed = -0.5
				baseEntityInteractionRange = 1.0
			}
		}

		onAll(Items.STONE_HOE, Items.COPPER_HOE) {
			it.attributes {
				baseAttackDamage = 0.0
				baseAttackSpeed = 0.0
				baseEntityInteractionRange = 1.0
			}
		}

		Items.GOLDEN_HOE {
			it.attributes {
				baseAttackDamage = 0.0
				baseAttackSpeed = 1.0
				baseEntityInteractionRange = 1.0
			}
		}

		Items.IRON_HOE {
			it.attributes {
				baseAttackDamage = 1.0
				baseAttackSpeed = 0.5
				baseEntityInteractionRange = 1.0
			}
		}

		Items.DIAMOND_HOE {
			it.attributes {
				baseAttackDamage = 1.0
				baseAttackSpeed = 1.0
				baseEntityInteractionRange = 1.0
			}
		}

		Items.NETHERITE_HOE {
			it.attributes {
				baseAttackDamage = 2.0
				baseAttackSpeed = 1.0
				baseEntityInteractionRange = 1.0
			}
		}

		onAll(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.COPPER_PICKAXE, Items.GOLDEN_PICKAXE) {
			it.attributes {
				baseAttackDamage = 1.0
				baseAttackSpeed = 0.0
			}
		}

		Items.IRON_PICKAXE {
			it.attributes {
				baseAttackDamage = 2.0
				baseAttackSpeed = 0.0
			}
		}

		Items.DIAMOND_PICKAXE {
			it.attributes {
				baseAttackDamage = 3.0
				baseAttackSpeed = 0.0
			}
		}

		Items.NETHERITE_PICKAXE {
			it.attributes {
				baseAttackDamage = 4.0
				baseAttackSpeed = 0.0
			}
		}

		onAll(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.COPPER_SHOVEL, Items.GOLDEN_SHOVEL) {
			it.attributes {
				baseAttackDamage = 0.0
				baseAttackSpeed = -0.5
			}
		}

		Items.IRON_SHOVEL {
			it.attributes {
				baseAttackDamage = 1.0
				baseAttackSpeed = -0.5
			}
		}

		Items.DIAMOND_SHOVEL {
			it.attributes {
				baseAttackDamage = 2.0
				baseAttackSpeed = -0.5
			}
		}

		Items.NETHERITE_SHOVEL {
			it.attributes {
				baseAttackDamage = 3.0
				baseAttackSpeed = -0.5
			}
		}

		Items.MACE {
			it.attributes {
				baseAttackDamage = 4.0
				baseAttackSpeed = -1.9 // TODO
				baseEntityInteractionRange = 0.5
			}
		}
	}
}
