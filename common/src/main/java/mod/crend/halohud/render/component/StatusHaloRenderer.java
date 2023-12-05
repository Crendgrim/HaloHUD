package mod.crend.halohud.render.component;

import mod.crend.halohud.component.HaloComponent;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class StatusHaloRenderer {

	public final HaloRenderer renderer;

	public StatusHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}

	private Color getColor(Config config, boolean freezing, float airIntensity) {
		if (freezing) {
			return config.colorFreezing;
		} else {
			if (airIntensity < 1) return HaloRenderer.modifyAlpha(config.colorAir, airIntensity);
			return config.colorAir;
		}
	}

	public void render(MatrixStack matrixStack, Config config, float value, boolean freezing, float airIntensity, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(getColor(config, freezing, airIntensity), value)
				.execute(config);
	}
}
