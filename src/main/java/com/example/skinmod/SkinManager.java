package com.example.skinmod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;

public class SkinManager {
    /** "default" = vanilla look. Every other id needs a model + texture (see README). */
    public static final List<String> IDS = List.of("default", "karambit", "lightsaber", "lightsaber_double", "butterfly_knife");
    public static final List<String> NAMES = List.of("Default", "Karambit", "Lightsaber", "Double Lightsaber", "Butterfly Knife");

    /** Steps 1..FLICK_FRAMES are the flick animation, 0 = resting. */
    public static final int FLICK_FRAMES = 22;
    /** How long one flick lasts. Raise for slower, lower for faster. */
    private static final long FLICK_NANOS = 800_000_000L;

    public static String current = "default";
    private static CustomModelData tag = null;
    private static final CustomModelData[] flickTags = new CustomModelData[FLICK_FRAMES + 1];
    private static long flickStart = -1L;

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("skinmod.txt");
    }

    public static void set(String id) {
        if (!IDS.contains(id)) id = "default";
        current = id;
        flickStart = -1L;
        tag = id.equals("default") ? null : new CustomModelData(List.of(), List.of(), List.of(id), List.of());
        if (id.equals("butterfly_knife")) {
            for (int k = 1; k <= FLICK_FRAMES; k++) {
                flickTags[k] = new CustomModelData(List.of((float) k), List.of(), List.of(id), List.of());
            }
        }
        try {
            Files.writeString(file(), id);
        } catch (IOException ignored) {
        }
    }

    public static void load() {
        try {
            if (Files.exists(file())) set(Files.readString(file()).trim());
        } catch (IOException ignored) {
        }
    }

    /** Start the flick animation (butterfly knife skin only). */
    public static void startFlick() {
        if (current.equals("butterfly_knife") && flickStart < 0) flickStart = System.nanoTime();
    }

    /** Current flick step (1..FLICK_FRAMES), or 0 when not flicking. Based on real time, not ticks. */
    private static int currentFlickFrame() {
        if (flickStart < 0) return 0;
        long elapsed = System.nanoTime() - flickStart;
        if (elapsed >= FLICK_NANOS) {
            flickStart = -1L;
            return 0;
        }
        int f = 1 + (int) (elapsed * FLICK_FRAMES / FLICK_NANOS);
        return Math.min(f, FLICK_FRAMES);
    }

    /** Call once per client tick. */
    public static void tick() {
        currentFlickFrame(); // lets a finished flick end even if nothing is drawn
    }

    /** Called by the renderer for every item it draws. */
    public static ItemStack apply(ItemStack stack) {
        if (tag == null || stack.isEmpty() || !stack.is(ItemTags.SWORDS)) return stack;
        if (stack.has(DataComponents.CUSTOM_MODEL_DATA)) return stack; // leave server-made items alone
        ItemStack copy = stack.copy();
        int frame = currentFlickFrame();
        copy.set(DataComponents.CUSTOM_MODEL_DATA, frame > 0 ? flickTags[frame] : tag);
        return copy;
    }
}
