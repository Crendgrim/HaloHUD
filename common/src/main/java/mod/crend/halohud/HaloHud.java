package mod.crend.halohud;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.Hud;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.MinecraftClient;

public class HaloHud {

    public static final String MOD_ID = "halohud";

    public static Hud hud;

    public static Config config() {
        return Config.CONFIG_STORE.config();
    }

    public static void init() {
        hud = new Hud();

        config();
    }

    public static void clientTick(MinecraftClient client) {
        HaloRenderer.tick();
        hud.tick();
        ModKeyBindings.clientTick(client);
    }
}
