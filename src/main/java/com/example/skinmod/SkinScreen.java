package com.example.skinmod;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SkinScreen extends Screen {
    public SkinScreen() {
        super(Component.literal("Weapon Skins"));
    }

    @Override
    protected void init() {
        int y = this.height / 2 - 50;

        Button title = Button.builder(Component.literal("Sword skin (only you see it)"), b -> {})
                .bounds(this.width / 2 - 90, y - 28, 180, 20).build();
        title.active = false;
        this.addRenderableWidget(title);

        for (int i = 0; i < SkinManager.IDS.size(); i++) {
            final String id = SkinManager.IDS.get(i);
            this.addRenderableWidget(Button.builder(Component.literal(SkinManager.NAMES.get(i)), b -> {
                SkinManager.set(id);
                this.onClose();
            }).bounds(this.width / 2 - 60, y + i * 24, 120, 20).build());
        }
    }
}
