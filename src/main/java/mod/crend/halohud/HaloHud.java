package mod.crend.halohud;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import mod.crend.halohud.component.Hud;
import mod.crend.halohud.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HaloHud implements ClientModInitializer {

    public static final String MOD_ID = "halohud";

	public static Config config;

    private static Hud hud;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(Config.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();

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
