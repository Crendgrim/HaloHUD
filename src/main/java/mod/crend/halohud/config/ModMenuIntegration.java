package mod.crend.halohud.config;

import com.terraformersmc.modmenu.api.ModMenuApi;
import mod.crend.halohud.gui.screen.ConfigScreenFactory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    public static Screen getScreen(Screen parent) {
        return ConfigScreenFactory.makeScreen(parent);
    }

    @Override
    public com.terraformersmc.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModMenuIntegration::getScreen;
    }
}
