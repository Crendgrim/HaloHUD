package mod.crend.halohud.neoforge;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.screen.ModConfigScreen;
import mod.crend.libbamboo.LibBamboo;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ModConfigMenu {
	public static void register() {
		Config.CONFIG_STORE.withYacl().registerDummyConfig(new Config());
		if (LibBamboo.HAS_YACL) {
			ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
					() -> (minecraft, screen) -> new ModConfigScreen(Config.CONFIG_STORE.withYacl().setupScreen(), Config.CONFIG_STORE.withYacl().dummyConfig, screen)
			);
		}
	}
}
