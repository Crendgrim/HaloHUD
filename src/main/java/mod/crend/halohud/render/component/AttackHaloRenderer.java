package mod.crend.halohud.render.component;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class AttackHaloRenderer {

	public final HaloRenderer renderer;

	public AttackHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private Color getColor(Config config, ActiveEffects effects, float toolProgress) {
		if (toolProgress > 0) {
			if (effects.miningFatigue) {
				return config.colorMiningFatigue;
			} else if (effects.haste) {
				return config.colorHaste;
			} else {
				return config.colorProgress;
			}
		} else {
			if (effects.strength) {
				return config.colorStrength;
			} else if (effects.weakness) {
				return config.colorWeakness;
			} else {
				return config.colorAttack;
			}
		}
	}

	public void render(MatrixStack matrixStack, Config config, ActiveEffects effects, float progress, float toolProgress, float intensity) {
		Color color = getColor(config, effects, toolProgress);
		if (toolProgress == 0 && progress < 1.0f) {
			color = HaloRenderer.modifyAlpha(color, 0.7f);
		}
		renderer.render(matrixStack, intensity)
				.draw(color, progress)
				.execute(config);
	}
}
