package quest.laxla.laxombat.fabric

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentMap
import net.minecraft.world.item.Item
import quest.laxla.laxombat.DataComponentModificationEvent

@JvmInline
internal value class FabricDataComponentModificationEvent(
	val context: DefaultItemComponentEvents.ModifyContext
) : DataComponentModificationEvent {
	override fun Item.invoke(builder: context(Item, HolderLookup.Provider) (DataComponentMap.Builder) -> Unit) =
		context.modify(this) { builder, lookupProvider, item ->
			builder(item, lookupProvider, builder)
		}

	override fun Collection<Item>.invoke(builder: context(Item, HolderLookup.Provider) (DataComponentMap.Builder) -> Unit) =
		context.modify(this) { builder, lookupProvider, item ->
			builder(item, lookupProvider, builder)
		}
}
