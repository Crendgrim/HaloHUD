package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class HungerHalo extends HaloComponent {
	HungerHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<Hud.ActiveEffects> effects) {
		super(renderer, player, effects);
	}

	@Override
	public float getValue() {
		return player.getHungerManager().getFoodLevel() / 20.0f;
	}

	private int getColorForFilledHungerBar(Hud.ActiveEffects effects) {
		if (effects.hunger) {
			return HaloHud.config.colorHunger;
		} else {
			return HaloHud.config.colorFood;
		}
	}

	@Override
	public void render(MatrixStack matrixStack) {
		setColor(getColorForFilledHungerBar(activeEffects()));
		float hunger = getValue();
		renderer.render(matrixStack, 0.0d, hunger);
		setColor(HaloHud.config.colorFoodEmpty);
		renderer.render(matrixStack, hunger, 1.0d);
	}
}
