package quest.laxla.laxombat.datagen

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.model.ModelTemplates
import net.minecraft.client.data.models.model.TexturedModel
import quest.laxla.laxombat.Laxombat

internal class LaxombatModelGenerator(packOutput: FabricPackOutput) : FabricModelProvider(packOutput) {
	override fun generateBlockStateModels(blockModelGenerators: BlockModelGenerators) = blockModelGenerators.run {
		createTrivialCube(Laxombat.roseGoldBlock.get())
		createRotatableColumn(Laxombat.roseGoldPillar.get())
		TexturedModel.COLUMN.create(Laxombat.roseGoldPillar.get(), modelOutput)

		Unit
	}

	override fun generateItemModels(itemModelGenerators: ItemModelGenerators) = itemModelGenerators.run {
		generateFlatItem(Laxombat.roseGoldUpgradeSmithingTemplate.get(), ModelTemplates.FLAT_ITEM)
		generateFlatItem(Laxombat.roseGoldSword.get(), ModelTemplates.FLAT_HANDHELD_ITEM)
		generateFlatItem(Laxombat.roseGoldShovel.get(), ModelTemplates.FLAT_HANDHELD_ITEM)
		generateFlatItem(Laxombat.roseGoldPickaxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM)
		generateFlatItem(Laxombat.roseGoldAxe.get(), ModelTemplates.FLAT_HANDHELD_ITEM)
		generateFlatItem(Laxombat.roseGoldHoe.get(), ModelTemplates.FLAT_HANDHELD_ITEM)
		generateFlatItem(Laxombat.roseGoldBoots.get(), ModelTemplates.FLAT_ITEM)
		generateFlatItem(Laxombat.roseGoldLeggings.get(), ModelTemplates.FLAT_ITEM)
		generateFlatItem(Laxombat.roseGoldChestplate.get(), ModelTemplates.FLAT_ITEM)
		generateFlatItem(Laxombat.roseGoldHelmet.get(), ModelTemplates.FLAT_ITEM)
		generateFlatItem(Laxombat.roseGoldIngot.get(), ModelTemplates.FLAT_ITEM)
		generateFlatItem(Laxombat.roseGoldNugget.get(), ModelTemplates.FLAT_ITEM)
	}

	override fun getName(): String = this::class.simpleName!!
}
