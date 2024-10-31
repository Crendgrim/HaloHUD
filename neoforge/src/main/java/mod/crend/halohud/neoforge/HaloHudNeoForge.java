package mod.crend.halohud.neoforge;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@Mod(HaloHud.MOD_ID)
@EventBusSubscriber(value = Dist.CLIENT)
public class HaloHudNeoForge {
    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {
        HaloHud.clientTick(MinecraftClient.getInstance());
    }
}
