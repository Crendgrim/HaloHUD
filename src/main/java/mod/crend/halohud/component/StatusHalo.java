package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.component.StatusHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class StatusHalo extends HaloComponent {

	private final StatusHaloRenderer renderer;
	float air;
	float airIntensity = 1;
	float freezing;
	float value;
	int ticksBeforeDecay = 0;

	public StatusHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(player, effects);
		this.renderer = new StatusHaloRenderer(renderer);
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);

		freezing = player.getFreezingScale();
		air = player.getAir() / (float) player.getMaxAir();

		if (player.getFrozenTicks() > 0) {
			value = freezing;
		} else if (air < HaloHud.config().showAirBelow) {
			value = air;
			// The following is required because freezing default is zero, but air-default is one. We want to hide the
			// air after a while of not being underwater even if we always show the halo segment.
			airIntensity = 1;
			ticksBeforeDecay = HaloHud.config().ticksRevealed;
		} else if (ticksBeforeDecay > 0) {
			if (HaloHud.config().showStatusAlways) {
				airIntensity = Math.min(1.0f, ticksBeforeDecay / Math.min(10.0f, HaloHud.config().ticksRevealed));
			} else {
				airIntensity = 1;
			}
			--ticksBeforeDecay;
		} else {
			value = 0;
		}
	}

	@Override
	public boolean shouldRenderImpl() {
		return freezing > 0 || HaloHud.config().showStatusAlways || air < HaloHud.config().showAirBelow;
	}

	@Override
	public void render(MatrixStack matrixStack, Config config) {
		renderer.render(matrixStack, config, value, freezing > 0, airIntensity, intensity());
	}
}
