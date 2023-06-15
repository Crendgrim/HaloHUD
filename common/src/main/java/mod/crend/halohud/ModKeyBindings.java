package mod.crend.halohud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ModKeyBindings {
	public static final String CATEGORY = "key.category.halohud";

	public static final KeyBinding TOGGLE_HUD = new KeyBinding(
			"key.halohud.toggle-hud",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_H,
			CATEGORY
	);

	public static final List<KeyBinding> ALL = List.of(
			TOGGLE_HUD
	);

	public static void clientTick(MinecraftClient client) {
		while (TOGGLE_HUD.wasPressed()) {
			HaloHud.hud.toggleHud();
		}
	}
}
