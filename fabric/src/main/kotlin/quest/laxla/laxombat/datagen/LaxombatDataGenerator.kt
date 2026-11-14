package quest.laxla.laxombat.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object LaxombatDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(generator: FabricDataGenerator) = generator.createPack().run {
		addProvider(::LaxombatModelGenerator)
		addProvider(::LaxombatRecipeGenerator)
		addProvider(::LaxombatItemTagGenerator)

		Unit
	}
}
