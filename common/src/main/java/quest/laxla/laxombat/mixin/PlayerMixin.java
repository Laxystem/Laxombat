package quest.laxla.laxombat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapMethod(method = "createAttributes")
    private static AttributeSupplier.@NonNull Builder createAttributes(@NonNull Operation<AttributeSupplier.Builder> original) {
        return original.call()
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.ATTACK_SPEED, 2.5)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 2.5)
                //.add(Laxombat.INSTANCE.getDefaultEntityAttackReach().asHolder())
        ;
    }
}
