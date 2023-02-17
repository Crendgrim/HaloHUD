package mod.crend.halohud.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class HaloRenderer {
	Supplier<Double> radius;
	Supplier<Double> width;
	public int start;
	public int end;
	float r = 1.0f;
	float g = 1.0f;
	float b = 1.0f;
	float a = 1.0f;
	public boolean flipped = false;

	public HaloRenderer(Supplier<Double> radius, Supplier<Double> width, HaloType type) {
		this.radius = radius;
		this.width = width;
		switch (type) {
			case Full -> {
				this.start = 30;
				this.end = 330;
			}
			case Left -> {
				this.start = 185;
				this.end = 330;
			}
			case Right -> {
				this.start = 30;
				this.end = 175;
			}
		}
	}

	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void render(MatrixStack matrices) {
		render(matrices, start, end);
	}
	public void render(MatrixStack matrices, double from, double to) {
		if (flipped) {
			double tmp = to;
			to = 1.0d - from;
			from = 1.0d - tmp;
		}
		int s = (int) (start + from * (end - start));
		int e = (int) (start + to * (end - start));
		renderImpl(matrices, s, e);
	}

	private void renderImpl(MatrixStack matrixStack, int start, int end) {
		// Setup render buffer
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

		// Setup circle arc
		double from = Math.toRadians(start);
		double to = Math.toRadians(end);
		double _radius = radius.get();
		double _width = width.get();
		double x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0d;
		double y = MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0d;
		for (double i = from; i < to; i += Math.min(0.0125, to - i)) {
			double sin = Math.sin(i);
			double cos = Math.cos(i);
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
