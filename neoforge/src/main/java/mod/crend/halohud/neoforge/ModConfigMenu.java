package mod.crend.halohud.neoforge;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.screen.ModConfigScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;

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
