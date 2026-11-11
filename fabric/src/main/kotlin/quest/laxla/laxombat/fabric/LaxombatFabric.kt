package quest.laxla.laxombat.fabric

import quest.laxla.laxombat.Laxombat
import net.fabricmc.api.ModInitializer

class LaxombatFabric: ModInitializer {
    override fun onInitialize() {
        Laxombat.register()
    }
}