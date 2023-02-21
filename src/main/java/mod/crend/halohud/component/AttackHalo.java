package mod.crend.halohud.component;

import mod.crend.halohud.HaloHud;
import mod.crend.halohud.mixin.ClientPlayerInteractionManagerAccessor;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;

import java.lang.ref.Reference;

public class AttackHalo extends HaloComponent {
	MinecraftClient client;
	float toolProgress = 0;
	boolean fullyCharged = false;

	public AttackHalo(HaloRenderer renderer, ClientPlayerEntity player, Reference<ActiveEffects> effects) {
		super(renderer, player, effects);
		client = MinecraftClient.getInstance();
	}

	protected float getValue() {
		if (toolProgress > 0) return toolProgress;
		return player.getAttackCooldownProgress(0.0F);
	}

	@Override
	protected boolean shouldRenderImpl() {
		if (getValue() < 1.0f || fullyCharged) return true;

		return (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity && client.targetedEntity.isAlive());
	}

	@Override
	public void tick(boolean shouldRender) {
		super.tick(shouldRender);

		fullyCharged = false;
		toolProgress = ((ClientPlayerInteractionManagerAccessor) client.interactionManager).getCurrentBreakingProgress();
		if (toolProgress == 0 && !player.getActiveItem().isEmpty()) {
			ItemStack activeItemStack = player.getActiveItem();
			if (activeItemStack.getUseAction() == UseAction.BOW) {
				toolProgress = BowItem.getPullProgress(activeItemStack.getMaxUseTime() - player.getItemUseTimeLeft());
				fullyCharged = true;
			} else if (activeItemStack.getUseAction() == UseAction.SPEAR) {
				int i = activeItemStack.getMaxUseTime() - player.getItemUseTimeLeft();
				toolProgress = Math.min(1.0f, i / 10.0f);
				fullyCharged = true;
			} else {
				toolProgress = 1.0f - player.getItemUseTimeLeft() / (float) activeItemStack.getMaxUseTime();
			}
		}
	}

	private int getColor(ActiveEffects effects) {
		if (toolProgress > 0) {
			if (effects.miningFatigue) {
				return HaloHud.config.colorToolMiningFatigue;
			} else if (effects.haste) {
				return HaloHud.config.colorToolHaste;
			} else {
				return HaloHud.config.colorProgress;
			}
		} else {
			if (effects.strength) {
				return HaloHud.config.colorStrength;
			} else if (effects.weakness) {
				return HaloHud.config.colorWeakness;
			} else {
				return HaloHud.config.colorAttack;
			}
		}
	}

	@Override
	public void render(MatrixStack matrixStack) {
		float progress = getValue();
		int color = getColor(activeEffects());
		if (toolProgress == 0 && progress < 1.0f) {
			color = modifyAlpha(color, 0.7f);
		}
		setColor(color);
		renderer.render(matrixStack, 0.0d, progress);
		setColor(HaloHud.config.colorAttackEmpty);
		renderer.render(matrixStack, progress, 1.0d);
	}
}
