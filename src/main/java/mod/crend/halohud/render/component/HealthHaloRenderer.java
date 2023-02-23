package mod.crend.halohud.render.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

public class HealthHaloRenderer {

	public final HaloRenderer renderer;

	public HealthHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private int getColor(ActiveEffects effects) {
		if (effects.wither) {
			return HaloHud.config().colorWither;
		} else if (effects.poison) {
			return HaloHud.config().colorPoison;
		} else if (effects.regeneration) {
			return HaloHud.config().colorRegeneration;
		} else {
			return HaloHud.config().colorHealth;
		}
	}

	public void render(MatrixStack matrixStack, ActiveEffects effects, float health, float absorption, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(getColor(effects), health)
				.draw(HaloHud.config().colorAbsorption, absorption)
				.execute();
	}
}
