package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import java.lang.ref.Reference;

public class AirHalo extends HaloComponent {

	AirHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<Hud.ActiveEffects> effects) {
		super(renderer, player, effects);
	}

	@Override
	public float getValue() {
		return player.getAir() / (float) player.getMaxAir();
	}

	@Override
	public void render(MatrixStack matrixStack) {
		setColor(HaloHud.config.colorAir);
		double delta = (1.0 - getValue()) / 2.0d;
		renderer.render(matrixStack, delta, 1.0d - delta);
	}
}
