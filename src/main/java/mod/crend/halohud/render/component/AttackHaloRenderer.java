package mod.crend.halohud.render.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

public class AttackHaloRenderer {

	public final HaloRenderer renderer;

	public AttackHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private int getColor(ActiveEffects effects, float toolProgress) {
		if (toolProgress > 0) {
			if (effects.miningFatigue) {
				return HaloHud.config().colorMiningFatigue;
			} else if (effects.haste) {
				return HaloHud.config().colorHaste;
			} else {
				return HaloHud.config().colorProgress;
			}
		} else {
			if (effects.strength) {
				return HaloHud.config().colorStrength;
			} else if (effects.weakness) {
				return HaloHud.config().colorWeakness;
			} else {
				return HaloHud.config().colorAttack;
			}
		}
	}

	public void render(MatrixStack matrixStack, ActiveEffects effects, float progress, float toolProgress, float intensity) {
		int color = getColor(effects, toolProgress);
		if (toolProgress == 0 && progress < 1.0f) {
			color = HaloRenderer.modifyAlpha(color, 0.7f);
		}
		renderer.render(matrixStack, intensity)
				.draw(color, progress)
				.execute();
	}
}
