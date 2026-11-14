package quest.laxla.laxombat.fabric

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import quest.laxla.laxombat.Laxombat.register

class LaxombatFabric : ModInitializer {
	override fun onInitialize() {
		register()

        DefaultItemComponentEvents.MODIFY.register {
            FabricDataComponentModificationEvent(it).register()
        }
	}
}
