package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;

import static mod.crend.halohud.component.Hud.OUTER_HALO_MAX_HEIGHT;

public class AirHalo extends HaloComponent {
	int haloSizeAir;

	@Override
	public float computeValue(ClientPlayerEntity player, Hud.ActiveEffects effects) {
		float air = player.getAir() / (float) player.getMaxAir();
		haloSizeAir = (int) (air * OUTER_HALO_MAX_HEIGHT);
		return air;
	}

	@Override
	public void render(MatrixStack matrixStack, int x, int y) {
		setColor(HaloHud.config.colorAir);
		drawTexture(matrixStack, x - 2, y - 2, 32, 0, 31, haloSizeAir);
	}
}
