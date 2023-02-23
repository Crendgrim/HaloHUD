package mod.crend.halohud.render.component;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

public class HungerHaloRenderer {

	public final HaloRenderer renderer;

	public HungerHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private int getColor(Config config, ActiveEffects effects) {
		if (effects.hunger) {
			return config.colorHunger;
		} else {
			return config.colorFood;
		}
	}

	public void render(MatrixStack matrixStack, Config config, ActiveEffects effects, float hunger, float handItemFoodValue, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(getColor(config, effects), hunger)
				.draw(HaloRenderer.animate(config.colorFood, config.colorEmpty), handItemFoodValue)
				.execute(config);
	}
}
