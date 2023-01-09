package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import static mod.crend.halohud.component.Hud.INNER_HALO_MAX_HEIGHT;

public class HealthHalo extends HaloComponent {

	Hud.ActiveEffects effects;
	int haloSizeHealth;
	int haloSizeAbsorption;

	@Override
	public float computeValue(ClientPlayerEntity player, Hud.ActiveEffects effects) {
		this.effects = effects;
		float health = player.getHealth() / player.getMaxHealth();
		float absorption = player.getAbsorptionAmount() / player.getMaxHealth();

		haloSizeAbsorption = (int) (absorption * INNER_HALO_MAX_HEIGHT);
		haloSizeHealth = Integer.min((int) (health * INNER_HALO_MAX_HEIGHT), INNER_HALO_MAX_HEIGHT - haloSizeAbsorption);
		return health;
	}

	@Override
	public boolean forceRender() {
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
	public void render(MatrixStack matrixStack, int x, int y) {
		if (haloSizeHealth > 0) {
			setColor(getColorForFilledHealthBar(effects));
			drawTexture(matrixStack, x, y, 0, 0, 13, haloSizeHealth);
		}
		if (haloSizeHealth + haloSizeAbsorption < INNER_HALO_MAX_HEIGHT) {
			setColor(HaloHud.config.colorHealthEmpty);
			drawTexture(matrixStack, x, y + haloSizeHealth, 0, haloSizeHealth, 13, INNER_HALO_MAX_HEIGHT - haloSizeHealth - haloSizeAbsorption);
		}
		if (haloSizeAbsorption > 0) {
			setColor(HaloHud.config.colorAbsorption);
			drawTexture(matrixStack, x, y + INNER_HALO_MAX_HEIGHT - haloSizeAbsorption, 0, INNER_HALO_MAX_HEIGHT - haloSizeAbsorption, 13, haloSizeAbsorption);
		}
	}
}
