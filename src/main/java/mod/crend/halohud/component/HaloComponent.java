package mod.crend.halohud.component;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;

public abstract class HaloComponent extends DrawableHelper {
	static final float MAX_TICKS = 30;
	protected float ticksRemaining = 0;
	public void tick(float tickDelta, boolean shouldRender) {
		if (shouldRender) ticksRemaining = Math.min(MAX_TICKS, ticksRemaining + 2 * tickDelta);
		else ticksRemaining = Math.max(0, ticksRemaining - tickDelta);
	}

	protected void setColor(int argb) {
		RenderSystem.setShaderColor(
				ColorHelper.Argb.getRed(argb) / 255.0f,
				ColorHelper.Argb.getGreen(argb) / 255.0f,
				ColorHelper.Argb.getBlue(argb) / 255.0f,
				(ticksRemaining / MAX_TICKS) * (ColorHelper.Argb.getAlpha(argb) / 255.0f)
		);
	}

	public abstract float computeValue(ClientPlayerEntity player, Hud.ActiveEffects effects);
	public boolean forceRender() { return false; }
	public abstract void render(MatrixStack matrixStack, int x, int y);
}
