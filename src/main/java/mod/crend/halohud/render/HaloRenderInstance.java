package mod.crend.halohud.render;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.awt.Color;

//? if >=1.21.2
/*import net.minecraft.client.gl.ShaderProgramKeys;*/

public class HaloRenderInstance {
	Matrix4f matrix;
	BufferBuilder buffer;
	double radius, width, x, y;
	double current;
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
		//? if <1.21 {
		buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
		//?} else {
		/*buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
		*///?}
		RenderSystem.enableBlend();;
		RenderSystem.defaultBlendFunc();
		RenderSystem.disableCull();

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

	private void setColor(Color color) {
		// The multiplier for the alpha value makes the halos fade in and out nicely.
		setColor(
				color.getRed() / 255.0f,
				color.getGreen() / 255.0f,
				color.getBlue() / 255.0f,
				intensity * (color.getAlpha() / 255.0f)
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
			buffer.vertex(matrix, (float) xOuter, (float) yOuter, 0).color(r, g, b, a)/*? if <1.21 {*/.next()/*?}*/;
			buffer.vertex(matrix, (float) xInner, (float) yInner, 0).color(r, g, b, a)/*? if <1.21 {*/.next()/*?}*/;
		} else {
			buffer.vertex(matrix, (float) xInner, (float) yInner, 0).color(r, g, b, a)/*? if <1.21 {*/.next()/*?}*/;
			buffer.vertex(matrix, (float) xOuter, (float) yOuter, 0).color(r, g, b, a)/*? if <1.21 {*/.next()/*?}*/;
		}
	}

	private void draw(double pct) {
		if (flipped) {
			double end = Math.max(left, (current - pct * (right - left)));
			for (; current > end; current -= Math.min(HaloRenderer.STEP_SIZE, current - end)) {
				drawCurrentSlice();
			}
		} else {
			double end = Math.min(right, (current + pct * (right - left)));
			for (; current < end; current += Math.min(HaloRenderer.STEP_SIZE, end - current)) {
				drawCurrentSlice();
			}
		}
	}

	public HaloRenderInstance draw(Color color, double pct) {
		setColor(color);
		draw(pct);
		return this;
	}

	public HaloRenderInstance finish(Color color) {
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
		//? if <1.21.2 {
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		//?} else
		/*RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);*/
		BufferRenderer.drawWithGlobalProgram(buffer.end());

		RenderSystem.enableCull();
		RenderSystem.disableBlend();
	}
}
