package mod.crend.halohud.render;

import mod.crend.halohud.config.Config;
import net.minecraft.client.util.math.MatrixStack;

public class SimpleHaloRenderer {

	public final HaloRenderer renderer;

	public SimpleHaloRenderer(HaloRenderer renderer) {
		this.renderer = renderer;
	}


	public void render(MatrixStack matrixStack, Config config, int color, float value, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(color, value)
				.execute(config);
	}
}
