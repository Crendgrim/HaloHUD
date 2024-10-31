package mod.crend.halohud;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.Hud;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

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

    public static void render(DrawContext context, /*? if <1.21 {*/float /*?} else {*//*RenderTickCounter*//*?}*/ renderTickCounter) {
        if (hud != null) {
            hud.render(context, renderTickCounter);
        }
    }
}
