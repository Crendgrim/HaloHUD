package mod.crend.halohud;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.Hud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HaloHud implements ClientModInitializer {

    public static final String MOD_ID = "halohud";

    private static Hud hud;

    public static Config config() {
        return Config.INSTANCE.getConfig();
    }

    @Override
    public void onInitializeClient() {
        Config.INSTANCE.load();
        hud = new Hud();

        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> hud.render(matrixStack, tickDelta));

        KeyBinding toggleHudKeyBinding = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.halohud.toggle-hud",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        "key.category.halohud"
                ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            hud.tick();
            while (toggleHudKeyBinding.wasPressed()) {
                hud.toggleHud();
            }
        });
    }
}
