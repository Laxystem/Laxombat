package quest.laxla.laxombat.datagen

import dev.architectury.registry.registries.RegistrySupplier
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.references.ItemIds
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import quest.laxla.laxombat.Laxombat
import java.util.concurrent.CompletableFuture

class LaxombatItemTagGenerator(
	output: FabricPackOutput,
	registryLookupFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider.ItemTagsProvider(output, registryLookupFuture) {
	override fun addTags(registries: HolderLookup.Provider) {
		tag(ItemTags.HEAD_ARMOR).add(Laxombat.roseGoldHelmet.key)
		tag(ItemTags.CHEST_ARMOR).add(Laxombat.roseGoldChestplate.key)
		tag(ItemTags.LEG_ARMOR).add(Laxombat.roseGoldLeggings.key)
		tag(ItemTags.FOOT_ARMOR).add(Laxombat.roseGoldBoots.key)
		tag(ItemTags.SWORDS).add(Laxombat.roseGoldSword.key)
		tag(ItemTags.SHOVELS).add(Laxombat.roseGoldShovel.key)
		tag(ItemTags.PICKAXES).add(Laxombat.roseGoldPickaxe.key)
		tag(ItemTags.AXES).add(Laxombat.roseGoldAxe.key)
		tag(ItemTags.HOES).add(Laxombat.roseGoldHoe.key)

		tag(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
			Laxombat.roseGoldSword.key,
			Laxombat.roseGoldAxe.key,
			ItemIds.IRON_HOE,
			ItemIds.GOLDEN_HOE,
			Laxombat.roseGoldHoe.key,
			ItemIds.DIAMOND_HOE,
			ItemIds.NETHERITE_HOE
		)

		// utility tag for development
		tag(Laxombat.allItems).addAll(Laxombat.streamItems(false).map(RegistrySupplier<Item>::getKey))
	}
}