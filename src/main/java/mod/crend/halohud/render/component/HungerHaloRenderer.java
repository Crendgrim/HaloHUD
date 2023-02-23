package mod.crend.halohud.render.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.util.math.MatrixStack;

public class HungerHaloRenderer {

	public final HaloRenderer renderer;

	public HungerHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private int getColor(ActiveEffects effects) {
		if (effects.hunger) {
			return HaloHud.config().colorHunger;
		} else {
			return HaloHud.config().colorFood;
		}
	}

	public void render(MatrixStack matrixStack, ActiveEffects effects, float hunger, float handItemFoodValue, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(getColor(effects), hunger)
				.draw(HaloRenderer.animate(HaloHud.config().colorFood, HaloHud.config().colorEmpty), handItemFoodValue)
				.execute();
	}
}
