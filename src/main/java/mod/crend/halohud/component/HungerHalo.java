package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class HungerHalo extends HaloComponent {

	float handItemFoodValue = 0;

	public HungerHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(renderer, player, effects);
	}

	public float getValue() {
		return player.getHungerManager().getFoodLevel() / 20.0f;
	}

	@Override
	public boolean shouldRenderImpl() {
		return getValue() < HaloHud.config.showFoodBelow;
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);
		handItemFoodValue = 0;
		if (player.getHungerManager().getFoodLevel() < 20) {
			if (player.getMainHandStack().isFood()) {
				handItemFoodValue = (player.getMainHandStack().getItem().getFoodComponent().getHunger() / 20.0f);
				reveal();
			} else if (player.getOffHandStack().isFood()) {
				handItemFoodValue = (player.getOffHandStack().getItem().getFoodComponent().getHunger() / 20.0f);
				reveal();
			}
		}
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
		setColor(animate(HaloHud.config.colorFood));
		renderer.render(matrixStack, hunger, Math.min(1.0d, hunger + handItemFoodValue));
		setColor(HaloHud.config.colorFoodEmpty);
		renderer.render(matrixStack, hunger, 1.0d);
	}
}
