package quest.laxla.laxombat.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.Mth;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodData;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import quest.laxla.laxombat.LaxombatFoodConstants;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private float saturationLevel;

    @Shadow
    private int foodLevel;

    /// Saturation is no longer accumulated by eating multiple instances of food. Instead, saturation is set to the eaten
    /// food's saturation value, as long as said saturation value exceeds the player's current saturation.
    @Redirect(
            method = "add",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F")
    )
    float add(float value, float min, float max, @Local(argsOnly = true, name = "saturation") float saturation) {
        return Mth.clamp(saturation, saturationLevel, foodLevel);
    }

    @Definition(id = "saturationLevel", field = "Lnet/minecraft/world/food/FoodData;saturationLevel:F")
    @Expression("this.saturationLevel > 0.0")
    @ModifyExpressionValue(method = "tick", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
    boolean tick$disableSaturationHealthEffect(boolean original) {
        return false;
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = FoodConstants.HEALTH_TICK_COUNT))
    int tick$healthTickCount(int constant) {
        return LaxombatFoodConstants.INSTANCE.getHealthTickCount();
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = FoodConstants.HEAL_LEVEL))
    int tick$healLevel(int constant) {
        return LaxombatFoodConstants.INSTANCE.getHealLevel();
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"))
    void tick$changeHealCost(@NonNull FoodData instance, float amount) {
        instance.setFoodLevel(instance.getFoodLevel() - 1);
    }
}
