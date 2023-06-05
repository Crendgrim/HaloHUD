package mod.crend.halohud.config;

import com.terraformersmc.modmenu.api.ModMenuApi;
import mod.crend.halohud.gui.screen.ConfigScreen;
import mod.crend.yaclx.YaclX;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public com.terraformersmc.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (YaclX.HAS_YACL) {
            Config.CONFIG_STORE.withYacl().registerDummyConfig(new Config());
            return parent -> new ConfigScreen(Config.CONFIG_STORE.withYacl().setupScreen(), Config.CONFIG_STORE.withYacl().dummyConfig, parent);
        } else {
            return Config.CONFIG_STORE::makeScreen;
        }
    }
}
