package quest.laxla.laxombat.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Weapon;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import quest.laxla.laxombat.ItemPropertiesExtension;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin implements ItemPropertiesExtension {
    @Shadow
    private @Nullable ResourceKey<Item> id;

    @WrapMethod(method = "axe")
    Item.Properties axe(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, @NonNull Operation<Item.Properties> original) {
        return original.call(material, attackDamageBaseline, attackSpeedBaseline)
                .component(DataComponents.WEAPON, new Weapon(1, 1.6f));
    }

    /// @reason compensates for reduced player attack speed
    @Definition(id = "attackDuration", local = @Local(type = float.class, name = "attackDuration", argsOnly = true))
    @Expression("1.0 / attackDuration")
    @ModifyExpressionValue(method = "spear", at = @At("MIXINEXTRAS:EXPRESSION"))
    float spear(float original) {
        return original + 1.5f;
    }

    @Override
    public @Nullable ResourceKey<@NotNull Item> getLaxombat$id() {
        return id;
    }
}
