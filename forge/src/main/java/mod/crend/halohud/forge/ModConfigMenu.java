package mod.crend.halohud.forge;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.screen.ModConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class ModConfigMenu {
	public static void register() {
		Config.CONFIG_STORE.withYacl().registerDummyConfig(new Config());
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
			return new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
				return new ModConfigScreen(Config.CONFIG_STORE.withYacl().setupScreen(), Config.CONFIG_STORE.withYacl().dummyConfig, screen);
			});
		});
	}
}
