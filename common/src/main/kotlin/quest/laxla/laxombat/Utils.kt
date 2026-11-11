package quest.laxla.laxombat

import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import kotlin.experimental.ExperimentalTypeInference

typealias RegistryKey<T> = ResourceKey<Registry<T>>

internal operator fun <T : Any> RegistryKey<T>.invoke(): DeferredRegister<T> =
	DeferredRegister.create(Laxombat.MOD_ID, this)

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> DeferredRegister<in T>.registerLaxombat(
	path: String,
	crossinline supplier: (Identifier) -> T
): RegistrySupplier<T> {
	val id = Identifier.fromNamespaceAndPath(Laxombat.MOD_ID, path)

	return register(id) {
		supplier(id)
	}
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("registerLaxombatItem")
inline fun <T : Item> DeferredRegister<Item>.registerLaxombat(
	path: String,
	crossinline supplier: (Item.Properties) -> T
): RegistrySupplier<T> = registerLaxombat(path) { id: Identifier ->
	val key = ResourceKey.create(Registries.ITEM, id)

	supplier(Item.Properties().setId(key))
}
