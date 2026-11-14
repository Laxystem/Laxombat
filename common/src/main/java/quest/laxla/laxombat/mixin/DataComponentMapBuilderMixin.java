package quest.laxla.laxombat.mixin;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import quest.laxla.laxombat.DataComponentMapBuilderExtension;

@Mixin(DataComponentMap.Builder.class)
public class DataComponentMapBuilderMixin implements DataComponentMapBuilderExtension {
    @Shadow
    @Final
    private Reference2ObjectMap<DataComponentType<?>, Object> map;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T laxombat$getOrDefault(@NotNull DataComponentType<T> dataComponentType, T _default) {
        return (T) map.getOrDefault(dataComponentType, _default);
    }
}
