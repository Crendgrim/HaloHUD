package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.component.HealthHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class HealthHalo extends HaloComponent {

	private final HealthHaloRenderer renderer;
	double previousHealth;

	public HealthHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(player, effects);
		previousHealth = (player.getHealth() / player.getMaxHealth());
		this.renderer = new HealthHaloRenderer(renderer);
	}

	public float getValue() {
		return player.getHealth() / player.getMaxHealth();
	}

	@Override
	public boolean shouldRenderImpl() {
		float health = getValue();
		return health < HaloHud.config().showHealthBelow || forceRender(activeEffects());
	}
	public boolean forceRender(ActiveEffects effects) {
		return effects.wither || effects.poison;
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);
		float health = getValue();
		if (previousHealth != health) {
			reveal();
			previousHealth = health;
		}
	}


	@Override
	public void render(MatrixStack matrixStack) {
		float haloSizeAbsorption = player.getAbsorptionAmount() / player.getMaxHealth();
		float haloSizeHealth = Float.min(getValue(), 1.0f - haloSizeAbsorption);
		renderer.render(matrixStack, activeEffects(), haloSizeHealth, haloSizeAbsorption, intensity());
	}
}
