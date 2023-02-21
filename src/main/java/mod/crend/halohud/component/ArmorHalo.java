package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;

import java.lang.ref.Reference;

public class ArmorHalo extends HaloComponent {
	float value;

	public ArmorHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(renderer, player, effects);
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
		return (player.isFallFlying() && value < HaloHud.config.showElytraBelow);
	}

	@Override
	public void render(MatrixStack matrixStack) {
		setColor(HaloHud.config.colorElytra);
		renderer.render(matrixStack, 0.0d, value);
	}
}
