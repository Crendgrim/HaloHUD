package mod.crend.halohud.render;

import mod.crend.halohud.util.HaloType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;

import java.util.function.DoubleSupplier;

public class HaloRenderer {
	static final int STEP_SIZE = 4;

	DoubleSupplier radius;
	DoubleSupplier width;
	public HaloType type;

	static int animationState = 0;

	public HaloRenderer(DoubleSupplier radius, DoubleSupplier width, HaloType type) {
		this.radius = radius;
		this.width = width;
		this.type = type;
	}

	public static int modifyAlpha(int argb, float multiplier) {
		int a = (int) (multiplier * ColorHelper.Argb.getAlpha(argb));
		return (a << 24) | argb & 0x00FFFFFF;
	}
	public static int animate(int argb) {
		float alphaMultiplier = Math.abs(animationState - 10) / 10.0f;
		return modifyAlpha(argb, alphaMultiplier);
	}

	public static int animate(int color1, int color2) {
		float multiplier = Math.abs(animationState - 10) / 10.0f;
		int a1 = ColorHelper.Argb.getAlpha(color1);
		int a2 = ColorHelper.Argb.getAlpha(color2);
		int r1 = ColorHelper.Argb.getRed(color1);
		int r2 = ColorHelper.Argb.getRed(color2);
		int g1 = ColorHelper.Argb.getGreen(color1);
		int g2 = ColorHelper.Argb.getGreen(color2);
		int b1 = ColorHelper.Argb.getBlue(color1);
		int b2 = ColorHelper.Argb.getBlue(color2);
		return ColorHelper.Argb.getArgb(
				a1 + (int) (multiplier * (a2 - a1)),
				r1 + (int) (multiplier * (r2 - r1)),
				g1 + (int) (multiplier * (g2 - g1)),
				b1 + (int) (multiplier * (b2 - b1))
		);
	}

	public static void staticTick() {
		animationState++;
		if (animationState == 20) animationState = 0;
	}
	public boolean shouldFlip() {
		return (type == HaloType.Left);
	}

	public HaloRenderInstance render(MatrixStack matrixStack, float intensity) {
		return new HaloRenderInstance(matrixStack, radius.getAsDouble(), width.getAsDouble(), shouldFlip(), intensity, type);
	}

}
