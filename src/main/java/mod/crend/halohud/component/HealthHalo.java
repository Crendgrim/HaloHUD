package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class HealthHalo extends HaloComponent {

	double previousHealth;

	public HealthHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(renderer, player, effects);
		previousHealth = (player.getHealth() / player.getMaxHealth());
	}

	public float getValue() {
		return player.getHealth() / player.getMaxHealth();
	}

	@Override
	public boolean shouldRenderImpl() {
		float health = getValue();
		return health < HaloHud.config.showHealthBelow || forceRender(activeEffects());
	}
	public boolean forceRender(ActiveEffects effects) {
		return effects.wither || effects.poison;
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);
		float health = getValue();
		if (previousHealth > health) {
			showForTicks = HaloHud.config.ticksRevealed;
		}
		previousHealth = health;
	}

	private int getColorForFilledHealthBar(ActiveEffects effects) {
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
		double haloSizeAbsorption = player.getAbsorptionAmount() / player.getMaxHealth();
		double haloSizeHealth = Double.min(getValue(), 1.0d - haloSizeAbsorption);
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
