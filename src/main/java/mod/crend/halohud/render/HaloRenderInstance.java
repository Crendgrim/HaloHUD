package mod.crend.halohud.render;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;

public class HaloRenderInstance {
	Matrix4f matrix;
	BufferBuilder buffer;
	double radius, width, x, y;
	int current;
	int left = 0, right = 0;
	boolean flipped;
	float r = 1.0f;
	float g = 1.0f;
	float b = 1.0f;
	float a = 1.0f;
	float intensity;

	public HaloRenderInstance(MatrixStack matrixStack, HaloDimensions dimensions, float intensity) {
		// Setup render buffer
		matrix = matrixStack.peek().getPositionMatrix();
		buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

		// Setup circle arc
		this.radius = dimensions.radius();
		this.width = dimensions.width();
		x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0d;
		y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0d;

		this.left = dimensions.left();
		this.right = dimensions.right();
		this.flipped = dimensions.flipped();
		this.current = flipped ? right : left;
		this.intensity = intensity;
	}

	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	private void setColor(int argb) {
		// The multiplier for the alpha value makes the halos fade in and out nicely.
		setColor(
				ColorHelper.Argb.getRed(argb) / 255.0f,
				ColorHelper.Argb.getGreen(argb) / 255.0f,
				ColorHelper.Argb.getBlue(argb) / 255.0f,
				intensity * (ColorHelper.Argb.getAlpha(argb) / 255.0f)
		);
	}

	private void drawCurrentSlice() {
		double sin = Math.sin(Math.toRadians(current));
		double cos = Math.cos(Math.toRadians(current));
		double xInner = x + sin * radius;
		double xOuter = xInner + sin * width;
		double yInner = y + cos * radius;
		double yOuter = yInner + cos * width;
		if (flipped) {
			buffer.vertex(matrix, (float) xOuter, (float) yOuter, 0).color(r, g, b, a).next();
			buffer.vertex(matrix, (float) xInner, (float) yInner, 0).color(r, g, b, a).next();
		} else {
			buffer.vertex(matrix, (float) xInner, (float) yInner, 0).color(r, g, b, a).next();
			buffer.vertex(matrix, (float) xOuter, (float) yOuter, 0).color(r, g, b, a).next();
		}
	}

	private void draw(double pct) {
		if (flipped) {
			int end = Math.max(left, (int) (current - pct * (right - left)));
			for (; current > end; current -= Math.min(HaloRenderer.STEP_SIZE, current - end)) {
				drawCurrentSlice();
			}
		} else {
			int end = Math.min(right, (int) (current + pct * (right - left)));
			for (; current < end; current += Math.min(HaloRenderer.STEP_SIZE, end - current)) {
				drawCurrentSlice();
			}
		}
	}

	public HaloRenderInstance draw(int argb, double pct) {
		setColor(argb);
		draw(pct);
		return this;
	}

	public HaloRenderInstance finish(int color) {
		setColor(color);
		if (flipped) {
			for (; current > left; current -= Math.min(HaloRenderer.STEP_SIZE, current - left)) {
				drawCurrentSlice();
			}
		} else {
			for (; current < right; current += Math.min(HaloRenderer.STEP_SIZE, right - current)) {
				drawCurrentSlice();
			}
		}
		drawCurrentSlice();
		return this;
	}

	public void execute(Config config) {
		// Finish the halo
		finish(config.colorEmpty);
		// Draw to the screen
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		BufferRenderer.drawWithGlobalProgram(buffer.end());
	}
}
