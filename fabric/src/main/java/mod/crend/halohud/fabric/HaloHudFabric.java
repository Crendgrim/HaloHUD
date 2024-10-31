package mod.crend.halohud.fabric;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.ModKeyBindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HaloHudFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HaloHud.init();

        HudRenderCallback.EVENT.register(HaloHud::render);

        ModKeyBindings.ALL.forEach(KeyBindingHelper::registerKeyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(HaloHud::clientTick);

    }
}
