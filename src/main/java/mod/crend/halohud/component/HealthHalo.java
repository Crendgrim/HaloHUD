package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class HealthHalo extends HaloComponent {

	double haloSizeHealth;
	double haloSizeAbsorption;

	HealthHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<Hud.ActiveEffects> effects) {
		super(renderer, player, effects);
	}

	public float getValue() {
		float health = (player.getHealth() / player.getMaxHealth());
		haloSizeAbsorption = player.getAbsorptionAmount() / player.getMaxHealth();
		haloSizeHealth = Double.min(player.getHealth() / player.getMaxHealth(), 1.0d - haloSizeAbsorption);
		return health;
	}

	@Override
	public boolean shouldRender() {
		return getValue() < HaloHud.config.showHealthBelow || forceRender(activeEffects());
	}
	public boolean forceRender(Hud.ActiveEffects effects) {
		return effects.wither || effects.poison;
	}

	private int getColorForFilledHealthBar(Hud.ActiveEffects effects) {
		if (effects.wither) {
			return HaloHud.config.colorWither;
		} else if (effects.poison) {
			return HaloHud.config.colorPoison;
		} else if (effects.regeneration) {
			return HaloHud.config.colorRegeneration;
		} else {
			return HaloHud.config.colorHealth;
		}
	}

	@Override
	public void render(MatrixStack matrixStack) {
		if (haloSizeHealth > 0) {
			setColor(getColorForFilledHealthBar(activeEffects()));
			renderer.render(matrixStack, 0, haloSizeHealth);
		}
		if (haloSizeHealth + haloSizeAbsorption < 1.0d) {
			setColor(HaloHud.config.colorHealthEmpty);
			renderer.render(matrixStack, haloSizeHealth, 1.0d - haloSizeAbsorption);
		}
		if (haloSizeAbsorption > 0) {
			setColor(HaloHud.config.colorAbsorption);
			renderer.render(matrixStack, 1.0d - haloSizeAbsorption, 1.0f);
		}
	}
}
