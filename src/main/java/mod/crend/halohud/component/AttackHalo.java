package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.mixin.ClientPlayerInteractionManagerAccessor;
import mod.crend.halohud.render.component.AttackHaloRenderer;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
//? if <1.21.2 {
import net.minecraft.util.UseAction;
//?} else
/*import net.minecraft.item.consume.UseAction;*/

import java.lang.ref.Reference;

public class AttackHalo extends HaloComponent {
	MinecraftClient client;
	private final AttackHaloRenderer renderer;
	float toolProgress = 0;
	boolean fullyCharged = false;

	public AttackHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(player, effects);
		client = MinecraftClient.getInstance();
		this.renderer = new AttackHaloRenderer(renderer);
	}

	protected float getValue() {
		if (toolProgress > 0) return toolProgress;
		return player.getAttackCooldownProgress(0.0F);
	}

	@Override
	protected boolean shouldRenderImpl() {
		if (getValue() < 1.0f || fullyCharged) return true;

		return HaloHud.config().showAttackAlways || (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity && client.targetedEntity.isAlive());
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);

		fullyCharged = false;
		toolProgress = ((ClientPlayerInteractionManagerAccessor) client.interactionManager).getCurrentBreakingProgress();
		if (toolProgress == 0 && !player.getActiveItem().isEmpty()) {
			ItemStack activeItemStack = player.getActiveItem();
			if (activeItemStack.getUseAction() == UseAction.BOW) {
				toolProgress = BowItem.getPullProgress(activeItemStack.getMaxUseTime(/*? if >=1.21 >>*//*player*/ ) - player.getItemUseTimeLeft());
				fullyCharged = true;
			} else if (activeItemStack.getUseAction() == UseAction.SPEAR) {
				int i = activeItemStack.getMaxUseTime(/*? if >=1.21 >>*//*player*/ ) - player.getItemUseTimeLeft();
				toolProgress = Math.min(1.0f, i / 10.0f);
				fullyCharged = true;
			} else {
				toolProgress = 1.0f - player.getItemUseTimeLeft() / (float) activeItemStack.getMaxUseTime(/*? if >=1.21 >>*//*player*/ );
			}
		}
	}

	@Override
	public void render(MatrixStack matrixStack, Config config) {
		renderer.render(matrixStack, config, activeEffects(), getValue(), toolProgress, intensity());
	}
}
