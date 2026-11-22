# Weapon Skins - CLIENT-ONLY (Minecraft 26.2, Fabric)

Press **K** to pick a sword skin (Karambit, Lightsaber, Double Lightsaber, Butterfly Knife). Only YOU see it.
With the Butterfly Knife skin, press **G** to flick it (rebind in Options > Controls > Key Binds > Misc).
The flick plays 22 steps over 0.8 s (change `FLICK_NANOS` in `SkinManager.java` to speed it up or slow it down)
and also moves the knife in your hand (lift, tilt, kick) - see the `display` block in `butterfly_flick_*.json`.
The mod never sends anything extra to the server and never changes your actual items -
it only changes how swords are drawn on your own screen, so it works on servers that
don't have the mod.

IMPORTANT: check the server's rules before joining a server with any mod
(DonutSMP included). Visual-only mods are often allowed, but that is the server's call.

## Easiest way to get the .jar (no installs)
1. Make a free account at github.com and create a new repository.
2. Upload ALL files from this folder (keep the `.github` folder).
3. Open the **Actions** tab -> the "Build mod" run -> download **weapon-skins-client-mod**.
4. Unzip it. Inside is `weapon-skins-client-1.0.0.jar`.

## Install
1. Run the Fabric installer for 26.2 (fabricmc.net/use/installer).
2. Put **Fabric API (26.2)** and the jar in `%appdata%\.minecraft\mods`.
3. Launch the Fabric profile, press **K**. Your choice is remembered (config/skinmod.txt).

## Known limits
- Swords only, all six types. Other players never see your skins.
- If the server forces its own resource pack that restyles swords, it can hide the skin.
- If the game crashes at startup with a Mixin error mentioning ItemModelResolver, the
  rendering method was renamed in your version - send me the log.

## Adding a skin
1. Add its id and name to `SkinManager.java`.
2. Add `models/item/<id>.json` and `textures/item/<id>.png` under assets/skinmod.
3. Add a case for it in each `assets/minecraft/items/*_sword.json`.
