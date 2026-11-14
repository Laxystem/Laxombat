package quest.laxla.laxombat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import quest.laxla.laxombat.Laxombat;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {
    @WrapOperation(method = "createSwordAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers;builder()Lnet/minecraft/world/item/component/ItemAttributeModifiers$Builder;"))
    ItemAttributeModifiers.Builder createSwordAttributes$addEntityReach(@NonNull Operation<ItemAttributeModifiers.Builder> original) {
        return original.call().add(
                Attributes.ENTITY_INTERACTION_RANGE,
                new AttributeModifier(
                        Laxombat.INSTANCE.getBaseEntityInteractionRangeID(),
                        0.5,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
        );
    }
}
