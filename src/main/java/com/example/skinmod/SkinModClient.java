package com.example.skinmod;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class SkinModClient implements ClientModInitializer {
    private static KeyMapping openMenu;
    private static KeyMapping flickKey;

    @Override
    public void onInitializeClient() {
        SkinManager.load();

        openMenu = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.skinmod.open", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KeyMapping.Category.MISC));
        flickKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.skinmod.flick", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KeyMapping.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (openMenu.consumeClick()) {
                if (mc.player != null && mc.gui.screen() == null) mc.setScreenAndShow(new SkinScreen());
            }
            while (flickKey.consumeClick()) {
                if (mc.player != null && mc.gui.screen() == null) SkinManager.startFlick();
            }
            SkinManager.tick();
        });
    }
}
