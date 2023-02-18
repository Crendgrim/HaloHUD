package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class HungerHalo extends HaloComponent {
	public HungerHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(renderer, player, effects);
	}

	@Override
	public float getValue() {
		return player.getHungerManager().getFoodLevel() / 20.0f;
	}

	@Override
	public boolean shouldRenderImpl() {
		return getValue() < HaloHud.config.showFoodBelow;
	}

	private int getColorForFilledHungerBar(ActiveEffects effects) {
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
