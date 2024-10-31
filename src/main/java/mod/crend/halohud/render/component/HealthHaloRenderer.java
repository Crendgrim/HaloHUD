package mod.crend.halohud.render.component;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class HealthHaloRenderer {

	public final HaloRenderer renderer;

	public HealthHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private Color getColor(Config config, ActiveEffects effects) {
		if (effects.wither) {
			return config.colorWither;
		} else if (effects.poison) {
			return config.colorPoison;
		} else if (effects.regeneration) {
			return config.colorRegeneration;
		} else {
			return config.colorHealth;
		}
	}

	public void render(MatrixStack matrixStack, Config config, ActiveEffects effects, float health, float absorption, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(getColor(config, effects), health)
				.draw(config.colorAbsorption, absorption)
				.execute(config);
	}
}
