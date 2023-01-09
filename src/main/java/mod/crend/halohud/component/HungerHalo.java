package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import static mod.crend.halohud.component.Hud.INNER_HALO_MAX_HEIGHT;

public class HungerHalo extends HaloComponent {
	Hud.ActiveEffects effects;
	int haloSizeHunger;

	@Override
	public float computeValue(ClientPlayerEntity player, Hud.ActiveEffects effects) {
		this.effects = effects;
		float hunger = player.getHungerManager().getFoodLevel() / 20.0f;
		haloSizeHunger = (int) (hunger * INNER_HALO_MAX_HEIGHT);
		return hunger;
	}

	private int getColorForFilledHungerBar(Hud.ActiveEffects effects) {
		if (effects.hunger) {
			return HaloHud.config.colorHunger;
		} else {
			return HaloHud.config.colorFood;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, int x, int y) {
		setColor(getColorForFilledHungerBar(effects));
		drawTexture(matrixStack, x + 14, y, 14, 0, 13, haloSizeHunger);
		setColor(HaloHud.config.colorFoodEmpty);
		drawTexture(matrixStack, x + 14, y + haloSizeHunger, 14, haloSizeHunger, 13, INNER_HALO_MAX_HEIGHT - haloSizeHunger);
	}
}
