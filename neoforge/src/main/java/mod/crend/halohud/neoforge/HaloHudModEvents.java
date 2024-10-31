package mod.crend.halohud.neoforge;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.ModKeyBindings;
import mod.crend.libbamboo.VersionUtils;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
//? if <1.20.6 {
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
//?} else {
/*import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
*///?}

//? if <1.20.6 {
@Mod.EventBusSubscriber(modid = HaloHud.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//?} else
/*@EventBusSubscriber(modid = HaloHud.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)*/
public class HaloHudModEvents {

	public static final Identifier HALO = VersionUtils.getIdentifier(HaloHud.MOD_ID, "halo");

	@SubscribeEvent
	static void onClientSetup(FMLClientSetupEvent event) {
		HaloHud.init();
		ModConfigMenu.register();
	}

	@SubscribeEvent
	static void onKeyMappingsRegister(RegisterKeyMappingsEvent event) {
		ModKeyBindings.ALL.forEach(event::register);
	}

	@SubscribeEvent
	static void onRegisterGuiOverlaysEvent(/*? if <1.20.6 {*/RegisterGuiOverlaysEvent/*?} else {*//*RegisterGuiLayersEvent*//*?}*/ event) {
		//? if <1.20.6 {
		event.registerAboveAll(HALO, (gui, context, tickDelta, screenWidth, screenHeight) -> HaloHud.render(context, tickDelta));
		//?} else
		/*event.registerAboveAll(HALO, HaloHud::render);*/
	}

}
