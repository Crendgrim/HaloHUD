package mod.crend.halohud.neoforge;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.ModKeyBindings;
import mod.crend.halohud.config.Config;
import mod.crend.libbamboo.neoforge.ConfigScreen;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod.EventBusSubscriber(modid = HaloHud.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HaloHudModEvents {

	public static final Identifier HALO = new Identifier(HaloHud.MOD_ID, "halo");

	@SubscribeEvent
	static void onClientSetup(FMLClientSetupEvent event) {
		HaloHud.init();
		ConfigScreen.register(Config.CONFIG_STORE);
	}

	@SubscribeEvent
	static void onKeyMappingsRegister(RegisterKeyMappingsEvent event) {
		ModKeyBindings.ALL.forEach(event::register);
	}

	@SubscribeEvent
	static void onRegisterGuiOverlaysEvent(RegisterGuiLayersEvent event) {
		event.registerAboveAll(HALO, HaloHud.hud::render);
	}

}
