package mod.crend.halohud.render.component;

import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

public class FoodHaloRenderer {

	public final HaloRenderer renderer;

	public FoodHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private int getColor(Config config, ActiveEffects effects) {
		if (effects.hunger) {
			return config.colorHunger;
		} else {
			return config.colorFood;
		}
	}

	public void render(MatrixStack matrixStack, Config config, ActiveEffects effects, float food, float handItemFoodValue, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(getColor(config, effects), food)
				.draw(HaloRenderer.animate(config.colorHeldFood, config.colorEmpty, config.heldFoodAnimationType), handItemFoodValue)
				.execute(config);
	}
}
