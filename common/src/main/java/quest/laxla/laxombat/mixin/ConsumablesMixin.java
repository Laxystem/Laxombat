package quest.laxla.laxombat.mixin;

import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import quest.laxla.laxombat.LaxombatFoodConstants;

@Mixin(Consumables.class)
public class ConsumablesMixin {
    @ModifyConstant(method = "defaultDrink", constant = @Constant(floatValue = Consumable.DEFAULT_CONSUME_SECONDS))
    private static float defaultDrink$consumeSeconds(float constant) {
        return LaxombatFoodConstants.INSTANCE.getDrinkDefaultConsumeSeconds();
    }
}
