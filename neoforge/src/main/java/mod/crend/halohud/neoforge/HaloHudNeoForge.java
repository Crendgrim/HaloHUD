package mod.crend.halohud.neoforge;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

@Mod(HaloHud.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HaloHudNeoForge {
    @SubscribeEvent
    static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            HaloHud.clientTick(MinecraftClient.getInstance());
        }
    }
}
