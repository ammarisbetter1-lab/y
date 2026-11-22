package com.example.skinmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.example.skinmod.SkinManager;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.item.ItemStack;

/**
 * Purely visual: swaps the stack the renderer looks at for a copy that has a
 * custom_model_data tag. The real item (and anything sent to the server) is never touched.
 */
@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
    @ModifyVariable(
            method = {"appendItemLayers", "updateForTopItem", "updateForLiving"},
            at = @At("HEAD"),
            argsOnly = true)
    private ItemStack skinmod$swapStack(ItemStack stack) {
        return SkinManager.apply(stack);
    }
}
