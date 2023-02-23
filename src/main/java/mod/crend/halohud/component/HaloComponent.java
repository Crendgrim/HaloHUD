package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;
import java.util.Objects;

public abstract class HaloComponent {

	private int ticksRemaining = 0;
	protected int showForTicks = 0;
	int animationState = 0;
	protected ClientPlayerEntity player;
	private final Reference<ActiveEffects> effects;

	HaloComponent(ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		this.player = player;
		this.effects = effects;
	}

	public void setPlayer(ClientPlayerEntity player) { this.player = player; }

	public boolean isVisible() { return ticksRemaining > 0; }

	public void tick(boolean shouldRender) {
		if (shouldRender) ticksRemaining = Math.min(HaloHud.config().ticksRevealed, ticksRemaining + 2);
		else if (ticksRemaining > 0) ticksRemaining--;
		if (showForTicks > 0) --showForTicks;
		animationState++;
		if (animationState == 20) animationState = 0;
	}

	public void reveal() {
		showForTicks = HaloHud.config().ticksRevealed;
	}

	public boolean shouldRender() {
		return showForTicks > 0 || shouldRenderImpl();
	}

	protected abstract boolean shouldRenderImpl();

	public float intensity() { return ticksRemaining / (float) HaloHud.config().ticksRevealed; }

	public abstract void render(MatrixStack matrixStack, Config config);

	protected ActiveEffects activeEffects() {
		return Objects.requireNonNull(effects.get());
	}

}
