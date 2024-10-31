package mod.crend.halohud.render;

import mod.crend.halohud.config.AnimationType;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;

public class HaloRenderer {
	static final int STEP_SIZE = 4;

	HaloDimensions dimensions;

	static int animationState = 0;

	public HaloRenderer(HaloDimensions dimensions) {
		this.dimensions = dimensions;
	}

	public void setDimensions(HaloDimensions dimensions) {
		this.dimensions = dimensions;
	}
	public HaloDimensions getDimensions() {
		return this.dimensions;
	}

	public static Color modifyAlpha(Color color, float multiplier) {
		int a = (int) (multiplier * color.getAlpha());
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
	}
	public static Color animatePulsating(Color color) {
		float alphaMultiplier = Math.abs(animationState - 10) / 10.0f;
		return modifyAlpha(color, alphaMultiplier);
	}

	public static Color animatePulsating(Color color1, Color color2) {
		float multiplier = Math.abs(animationState - 10) / 10.0f;
		int a1 = color1.getAlpha();
		int a2 = color2.getAlpha();
		int r1 = color1.getRed();
		int r2 = color2.getRed();
		int g1 = color1.getGreen();
		int g2 = color2.getGreen();
		int b1 = color1.getBlue();
		int b2 = color2.getBlue();
		return new Color(
				r1 + (int) (multiplier * (r2 - r1)),
				g1 + (int) (multiplier * (g2 - g1)),
				b1 + (int) (multiplier * (b2 - b1)),
				a1 + (int) (multiplier * (a2 - a1))
		);
	}

	public static Color animate(Color color1, Color color2, AnimationType animationType) {
		return switch (animationType) {
			case Pulsating -> animatePulsating(color1, color2);
			case Flashing -> animationState < 10 ? color1 : color2;
			case Static -> color1;
			case Disabled -> color2;
		};
	}

	public static void tick() {
		animationState++;
		if (animationState == 20) animationState = 0;
	}

	public HaloRenderInstance render(MatrixStack matrixStack, float intensity) {
		return new HaloRenderInstance(matrixStack, dimensions, intensity);
	}
}
