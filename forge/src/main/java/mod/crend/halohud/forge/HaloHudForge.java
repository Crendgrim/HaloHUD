package mod.crend.halohud.forge;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(HaloHud.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HaloHudForge {
    @SubscribeEvent
    static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            HaloHud.clientTick(MinecraftClient.getInstance());
        }
    }
}
