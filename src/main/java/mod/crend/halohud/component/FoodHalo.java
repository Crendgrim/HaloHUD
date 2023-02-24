package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.component.FoodHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class FoodHalo extends HaloComponent {

	private final FoodHaloRenderer renderer;
	float handItemFoodValue = 0;

	public FoodHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(player, effects);
		this.renderer = new FoodHaloRenderer(renderer);
	}

	public float getValue() {
		return player.getHungerManager().getFoodLevel() / 20.0f;
	}

	@Override
	public boolean shouldRenderImpl() {
		return getValue() < HaloHud.config().showFoodBelow;
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


	@Override
	public void render(MatrixStack matrixStack, Config config) {
		renderer.render(matrixStack, config, activeEffects(), getValue(), handItemFoodValue, intensity());
	}
}
