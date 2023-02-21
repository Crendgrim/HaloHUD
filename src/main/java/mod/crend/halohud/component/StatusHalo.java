package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class StatusHalo extends HaloComponent {

	boolean freezing;
	float value;

	public StatusHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(renderer, player, effects);
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);

		freezing = false;
		if (player.getFrozenTicks() > 0) {
			value = player.getFreezingScale();
			freezing = true;
		} else if (player.getAir() != player.getMaxAir()) {
			value = player.getAir() / (float) player.getMaxAir();
		} else {
			value = 1;
		}
	}

	@Override
	public boolean shouldRenderImpl() {
		return freezing || value < HaloHud.config.showAirBelow;
	}

	@Override
	public void render(MatrixStack matrixStack) {
		setColor(HaloHud.config.colorAir);
		renderer.render(matrixStack, 0.0d, value);
	}
}
