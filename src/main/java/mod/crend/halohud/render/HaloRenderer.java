package mod.crend.halohud.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class HaloRenderer {
	double x;
	double y;
	double radius;
	double width;
	int start;
	int end;
	float r = 1.0f;
	float g = 1.0f;
	float b = 1.0f;
	float a = 1.0f;

	public HaloRenderer(double x, double y, double radius, double width, boolean left) {
		this(x, y, radius, width, left ? 185 : 30, left ? 330 : 175);
	}

	public HaloRenderer(double x, double y, double radius, double width, int start, int end) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.width = width;
		this.start = start;
		this.end = end;
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
		if (start > 180) {
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
		for (double i = from; i < to; i += Math.min(0.0125, to - i)) {
			double sin = Math.sin(i);
			double cos = Math.cos(i);
			double xInner = x + sin * radius;
			double xOuter = x + sin * (radius + width);
			double yInner = y + cos * radius;
			double yOuter = y + cos * (radius + width);
			buffer.vertex(matrix, (float) xInner, (float) yInner, 0).color(r, g, b, a).next();
			buffer.vertex(matrix, (float) xOuter, (float) yOuter, 0).color(r, g, b, a).next();
		}

		// Draw to the screen
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		BufferRenderer.drawWithGlobalProgram(buffer.end());
	}
}
