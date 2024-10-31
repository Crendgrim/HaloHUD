package mod.crend.halohud.neoforge;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.screen.ModConfigScreen;
import mod.crend.libbamboo.LibBamboo;
import net.minecraft.client.gui.screen.Screen;
import net.neoforged.fml.ModLoadingContext;
//? if <1.21.1 {
import net.minecraft.client.MinecraftClient;
//?} else {
/*import net.neoforged.fml.ModContainer;
 *///?}
//? if <1.20.5 {
import net.neoforged.neoforge.client.ConfigScreenHandler;
//?} else {
/*import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}

public class ModConfigMenu {
	public static void register() {
		Config.CONFIG_STORE.withYacl().registerDummyConfig(new Config());
		if (LibBamboo.HAS_YACL) {
			ModLoadingContext.get().registerExtensionPoint(
					//? if <1.20.5 {
					ConfigScreenHandler.ConfigScreenFactory.class,
					//?} else
					/*IConfigScreenFactory.class,*/
					() ->/*? if <1.20.5 {*/new ConfigScreenHandler.ConfigScreenFactory/*?}*/(
							(client, parentScreen) -> new ModConfigScreen(Config.CONFIG_STORE.withYacl().setupScreen(), Config.CONFIG_STORE.withYacl().dummyConfig, parentScreen)
					)
			);
		}
	}
}
