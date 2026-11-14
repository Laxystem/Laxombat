package quest.laxla.laxombat.neoforge

import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentMap
import net.minecraft.world.item.Item
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent
import quest.laxla.laxombat.DataComponentModificationEvent

@JvmInline
internal value class NeoForgeDataComponentModificationEvent(
	val event: ModifyDefaultComponentsEvent
) : DataComponentModificationEvent {
	override fun Item.invoke(builder: context(Item, HolderLookup.Provider) (DataComponentMap.Builder) -> Unit) =
		event.modify(this) { mapBuilder, lookupProvider, item ->
			builder(item, lookupProvider, mapBuilder)
		}

	override fun Collection<Item>.invoke(builder: context(Item, HolderLookup.Provider) (DataComponentMap.Builder) -> Unit) {
		for (item in this) item(builder)
	}
}
