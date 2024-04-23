package mod.crend.halohud.forge;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.ModKeyBindings;
import mod.crend.libbamboo.LibBamboo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HaloHud.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HaloHudModEvents {
	@SubscribeEvent
	static void onClientSetup(FMLClientSetupEvent event) {
		MixinExtrasBootstrap.init();
		HaloHud.init();
		if (LibBamboo.HAS_YACL) {
			ModConfigMenu.register();
		}
	}

	@SubscribeEvent
	static void onKeyMappingsRegister(RegisterKeyMappingsEvent event) {
		ModKeyBindings.ALL.forEach(event::register);
	}

	@SubscribeEvent
	static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
		event.registerAboveAll(HaloHud.MOD_ID, (gui, context, tickDelta, screenWidth, screenHeight)-> HaloHud.hud.render(context, tickDelta));
	}

}
