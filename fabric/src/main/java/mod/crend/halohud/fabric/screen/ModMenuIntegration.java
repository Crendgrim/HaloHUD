package mod.crend.halohud.fabric.screen;

import com.terraformersmc.modmenu.api.ModMenuApi;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.screen.ModConfigScreen;
import mod.crend.libbamboo.LibBamboo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public com.terraformersmc.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (LibBamboo.HAS_YACL) {
            Config.CONFIG_STORE.withYacl().registerDummyConfig(new Config());
            return parent -> new ModConfigScreen(Config.CONFIG_STORE.withYacl().setupScreen(), Config.CONFIG_STORE.withYacl().dummyConfig, parent);
        } else {
            return Config.CONFIG_STORE::makeScreen;
        }
    }
}
