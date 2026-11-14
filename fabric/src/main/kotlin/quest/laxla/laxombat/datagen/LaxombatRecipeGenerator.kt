package quest.laxla.laxombat.datagen

import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.advancements.Advancement
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import quest.laxla.laxombat.Laxombat
import java.util.concurrent.CompletableFuture

class LaxombatRecipeGenerator(
	output: FabricPackOutput,
	registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricRecipeProvider(output, registriesFuture) {
	override fun createRecipeProvider(
		registries: HolderLookup.Provider,
		recipes: BootstrapContext<Recipe<*>>,
		advancements: BootstrapContext<Advancement>
	): RecipeProvider = object : RecipeProvider(recipes, advancements) {
		override fun buildRecipes() {
			nineBlockStorageRecipesRecipesWithCustomUnpacking(
				RecipeCategory.MISC,
				Laxombat.roseGoldIngot.get(),
				RecipeCategory.BUILDING_BLOCKS,
				Laxombat.roseGoldBlock.get(),
				"rose_gold_ingot_from_rose_gold_block",
				"rose_gold_ingot"
			)

			val roseGoldUpgradeTemplate = Laxombat.roseGoldUpgradeSmithingTemplate.get()

			shaped(RecipeCategory.MISC, roseGoldUpgradeTemplate, 2)
				.define('#', Items.DIAMOND)
				.define('C', ItemTags.TERRACOTTA)
				.define('S', roseGoldUpgradeTemplate)
				.pattern("#S#")
				.pattern("#C#")
				.pattern("###")
				.unlockedBy(getHasName(roseGoldUpgradeTemplate), has(roseGoldUpgradeTemplate))
				.save(output)

			fun roseGoldSmithing(
				gold: Item,
				roseGold: RegistrySupplier<Item>,
				category: RecipeCategory
			) {
				val roseGold = roseGold.get()

				SmithingTransformRecipeBuilder
					.smithing(
						Ingredient.of(roseGoldUpgradeTemplate),
						Ingredient.of(gold),
						tag(ItemTags.COPPER_TOOL_MATERIALS),
						category,
						roseGold
					).unlocks(getHasName(gold), has(gold))
					.save(output, getItemName(roseGold) + "_smithing")
			}

			roseGoldSmithing(Items.GOLDEN_HELMET, Laxombat.roseGoldHelmet, RecipeCategory.COMBAT)
			roseGoldSmithing(Items.GOLDEN_CHESTPLATE, Laxombat.roseGoldChestplate, RecipeCategory.COMBAT)
			roseGoldSmithing(Items.GOLDEN_LEGGINGS, Laxombat.roseGoldLeggings, RecipeCategory.COMBAT)
			roseGoldSmithing(Items.GOLDEN_BOOTS, Laxombat.roseGoldBoots, RecipeCategory.COMBAT)
			roseGoldSmithing(Items.GOLDEN_SWORD, Laxombat.roseGoldSword, RecipeCategory.COMBAT)
			roseGoldSmithing(Items.GOLDEN_SHOVEL, Laxombat.roseGoldShovel, RecipeCategory.TOOLS)
			roseGoldSmithing(Items.GOLDEN_PICKAXE, Laxombat.roseGoldPickaxe, RecipeCategory.TOOLS)
			roseGoldSmithing(Items.GOLDEN_AXE, Laxombat.roseGoldAxe, RecipeCategory.TOOLS)
			roseGoldSmithing(Items.GOLDEN_HOE, Laxombat.roseGoldHoe, RecipeCategory.TOOLS)
		}

	}

	override fun getName(): String = this::class.simpleName!!
}
