package quest.laxla.laxombat

import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

object Laxombat {
	const val MOD_ID = "laxombat"

	private val items = Registries.ITEM()
	private val blocks = Registries.BLOCK()

	val ruby = items.registerLaxombat("ruby", ::Item)

	fun register() {
		items.register()
	}
}