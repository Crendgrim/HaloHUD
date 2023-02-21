package mod.crend.halohud.render;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.util.HaloType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class HaloRenderer {
	Supplier<Double> radius;
	Supplier<Double> width;
	public HaloType type;
	float r = 1.0f;
	float g = 1.0f;
	float b = 1.0f;
	float a = 1.0f;
	public boolean flipped = false;

	public HaloRenderer(Supplier<Double> radius, Supplier<Double> width, HaloType type) {
		this.radius = radius;
		this.width = width;
		this.type = type;
	}

	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void tick() {
		flipped = (HaloHud.config.flipHalos && type == HaloType.Left) || (!HaloHud.config.flipHalos && type == HaloType.Right);
	}

	public void render(MatrixStack matrixStack, double from, double to) {
		if (flipped) {
			double tmp = to;
			to = 1.0d - from;
			from = 1.0d - tmp;
		}

		int left = 0, right = 0;
		switch (type) {
			case Full -> {
				left = 40;
				right = 320;
			}
			case Left -> {
				left = 185;
				right = 320;
			}
			case Right -> {
				left = 40;
				right = 175;
			}
			case Bottom -> {
				left = 325;
				right = 360 + 35;
			}
		}

		int start = (int) (left + from * (right - left));
		int end = (int) (left + to * (right - left));

		// Setup render buffer
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

		// Setup circle arc
		double _radius = radius.get();
		double _width = width.get();
		double x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0d;
		double y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0d;
		for (double i = start; i < end; i += Math.min(1, end - i)) {
			double sin = Math.sin(Math.toRadians(i));
			double cos = Math.cos(Math.toRadians(i));
			double xInner = x + sin * _radius;
			double xOuter = xInner + sin * _width;
			double yInner = y + cos * _radius;
			double yOuter = yInner + cos * _width;
			buffer.vertex(matrix, (float) xInner, (float) yInner, 0).color(r, g, b, a).next();
			buffer.vertex(matrix, (float) xOuter, (float) yOuter, 0).color(r, g, b, a).next();
		}

		// Draw to the screen
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		BufferRenderer.drawWithGlobalProgram(buffer.end());
	}

}
