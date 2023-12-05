package mod.crend.halohud.neoforge;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.ModKeyBindings;
import mod.crend.halohud.config.Config;
import mod.crend.yaclx.neoforge.ConfigScreen;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod.EventBusSubscriber(modid = HaloHud.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HaloHudModEvents {

	public static final Identifier HALO = new Identifier(HaloHud.MOD_ID, "halo");

	@SubscribeEvent
	static void onClientSetup(FMLClientSetupEvent event) {
		//MixinExtrasBootstrap.init();
		HaloHud.init();
		ConfigScreen.register(Config.CONFIG_STORE);
	}

	@SubscribeEvent
	static void onKeyMappingsRegister(RegisterKeyMappingsEvent event) {
		ModKeyBindings.ALL.forEach(event::register);
	}

	@SubscribeEvent
	static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
		event.registerAboveAll(HALO, (gui, context, tickDelta, screenWidth, screenHeight)-> HaloHud.hud.render(context, tickDelta));
	}

}
