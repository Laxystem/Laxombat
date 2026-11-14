package quest.laxla.laxombat.neoforge

import net.neoforged.bus.api.IEventBus
import quest.laxla.laxombat.Laxombat
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent
import quest.laxla.laxombat.Laxombat.register

@Mod(Laxombat.MOD_ID)
class LaxombatNeoForge(bus: IEventBus) {
    init {
	    register()

        bus.addListener<ModifyDefaultComponentsEvent> {
            NeoForgeDataComponentModificationEvent(it).register()
        }
    }
}
