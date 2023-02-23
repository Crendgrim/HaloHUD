package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.SimpleHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;

import java.lang.ref.Reference;

public class ArmorHalo extends HaloComponent {
	float value;

	private final SimpleHaloRenderer renderer;

	public ArmorHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(player, effects);
		this.renderer = new SimpleHaloRenderer(renderer, HaloHud.config().colorElytra);
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);

		if (player.isFallFlying()) {
			ItemStack chestArmor = player.getInventory().getArmorStack(2);
			if (chestArmor.getItem() instanceof ElytraItem) {
				value = 1.0f - ((float) chestArmor.getDamage()) / chestArmor.getMaxDamage();
			}
		}
	}

	@Override
	public boolean shouldRenderImpl() {
		return (player.isFallFlying() && value < HaloHud.config().showElytraBelow);
	}

	@Override
	public void render(MatrixStack matrixStack) {
		renderer.render(matrixStack, value, intensity());
	}
}
