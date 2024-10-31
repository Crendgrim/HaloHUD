package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.SimpleHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.lang.ref.Reference;

public class ArmorHalo extends HaloComponent {
	float value;

	private final SimpleHaloRenderer renderer;

	public ArmorHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(player, effects);
		this.renderer = new SimpleHaloRenderer(renderer);
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);

		if (player./*? if <1.21.2 {*/isFallFlying/*?} else {*//*isGliding*//*?}*/()) {
			ItemStack chestArmor = player.getInventory().getArmorStack(2);
			if (chestArmor.getItem() == Items.ELYTRA) {
				value = 1.0f - ((float) chestArmor.getDamage()) / chestArmor.getMaxDamage();
			}
		}
	}

	@Override
	public boolean shouldRenderImpl() {
		return HaloHud.config().showArmorAlways || (player./*? if <1.21.2 {*/isFallFlying/*?} else {*//*isGliding*//*?}*/() && value < HaloHud.config().showElytraBelow);
	}

	@Override
	public void render(MatrixStack matrixStack, Config config) {
		renderer.render(matrixStack, config, config.colorElytra, value, intensity());
	}
}
