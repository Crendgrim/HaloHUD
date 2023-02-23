package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.SimpleHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.lang.ref.Reference;

public class ToolHalo extends HaloComponent {

	private final SimpleHaloRenderer renderer;

	float value;
	boolean active;
	boolean mainHand;

	public ToolHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects, boolean mainHand) {
		super(player, effects);
		this.renderer = new SimpleHaloRenderer(renderer);
		this.mainHand = mainHand;
	}

	private void computeValue(ItemStack itemStack) {
		if (itemStack.isDamaged()) {
			value = 1 - ((float) itemStack.getDamage()) / itemStack.getMaxDamage();
			if (value < 1) active = true;
		} else {
			active = false;
		}
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);
		computeValue(mainHand ? player.getMainHandStack() : player.getOffHandStack());
	}

	@Override
	protected boolean shouldRenderImpl() {
		return active && value < HaloHud.config().showToolBelow;
	}

	@Override
	public void render(MatrixStack matrixStack, Config config) {
		this.renderer.render(matrixStack, config, config.colorTool, value, intensity());
	}
}
