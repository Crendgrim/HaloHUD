package mod.crend.halohud.neoforge;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.MinecraftClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
//? if <1.20.5 {
import net.neoforged.neoforge.event.TickEvent;
//?} else {
/*import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
*///?}

@Mod(HaloHud.MOD_ID)
//? if <1.20.5 {
@Mod.EventBusSubscriber(value = Dist.CLIENT)
//?} else
/*@EventBusSubscriber(value = Dist.CLIENT)*/
public class HaloHudNeoForge {
    @SubscribeEvent
    static void onClientTick(/*? if <1.20.5 {*/TickEvent.ClientTickEvent/*?} else {*//*ClientTickEvent.Post*//*?}*/ event) {
        //? if <1.20.5
        if (event.phase == TickEvent.Phase.START) return;
        HaloHud.clientTick(MinecraftClient.getInstance());
    }
}
