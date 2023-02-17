package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;

import java.lang.ref.Reference;
import java.util.Objects;

public abstract class HaloComponent {

	private float ticksRemaining = 0;
	protected final HaloRenderer renderer;
	protected ClientPlayerEntity player;
	private final Reference<ActiveEffects> effects;

	HaloComponent(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		this.renderer = renderer;
		this.player = player;
		this.effects = effects;
	}

	public void setPlayer(ClientPlayerEntity player) { this.player = player; }

	public boolean isVisible() { return ticksRemaining > 0; }

	public void tick(boolean shouldRender) {
		if (shouldRender) ticksRemaining = Math.min(HaloHud.config.ticksRevealed, ticksRemaining + 4);
		else if (ticksRemaining > 0) ticksRemaining -= 2;
		renderer.flipped = (HaloHud.config.flipHalos && renderer.start < 180) || (!HaloHud.config.flipHalos && renderer.start > 180);
	}

	public abstract boolean shouldRender();

	public abstract void render(MatrixStack matrixStack);


	protected abstract float getValue();

	protected void setColor(int argb) {
		// The multiplier for the alpha value makes the halos fade in and out nicely.
		renderer.setColor(
				ColorHelper.Argb.getRed(argb) / 255.0f,
				ColorHelper.Argb.getGreen(argb) / 255.0f,
				ColorHelper.Argb.getBlue(argb) / 255.0f,
				(ticksRemaining / HaloHud.config.ticksRevealed) * (ColorHelper.Argb.getAlpha(argb) / 255.0f)
		);
	}

	protected ActiveEffects activeEffects() {
		return Objects.requireNonNull(effects.get());
	}

}
