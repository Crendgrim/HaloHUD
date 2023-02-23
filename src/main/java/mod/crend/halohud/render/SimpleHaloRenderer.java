package mod.crend.halohud.render;

import net.minecraft.client.util.math.MatrixStack;

public class SimpleHaloRenderer {

	public final HaloRenderer renderer;
	private final int color;

	public SimpleHaloRenderer(HaloRenderer renderer, int color) {
		this.renderer = renderer;
		this.color = color;
	}


	public void render(MatrixStack matrixStack, float value, float intensity) {
		renderer.render(matrixStack, intensity)
				.draw(color, value)
				.execute();
	}
}
