package quest.laxla.laxombat.mixin;

import net.minecraft.world.item.component.AttackRange;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AttackRange.class)
public class AttackRangeMixin {
//    @WrapMethod(method = "defaultFor")
//    private static AttackRange defaultFor(@NonNull LivingEntity livingEntity, Operation<AttackRange> original) {
//        final var defaultEntityAttackReachAttribute = Laxombat.INSTANCE.getDefaultEntityAttackReach().asHolder();
//
//        if (livingEntity.getAttributes().hasAttribute(defaultEntityAttackReachAttribute)) {
//            final var defaultEntityAttackReach = (float) livingEntity.getAttributeValue(defaultEntityAttackReachAttribute);
//
//            return new AttackRange(
//                    0.0F,
//                    defaultEntityAttackReach,
//                    0.0F,
//                    defaultEntityAttackReach,
//                    0.0F,
//                    1.0F
//            );
//        } else return original.call(livingEntity);
//    }
}
