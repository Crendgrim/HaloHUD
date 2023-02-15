package mod.crend.halohud.component;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;

public class Hud extends DrawableHelper {
	public static final Identifier haloTexture = new Identifier(mod.crend.halohud.HaloHud.MOD_ID, "textures/gui/halo.png");

	boolean debug = true;

	MinecraftClient client;
	ClientPlayerEntity player = null;

	public Hud() {
		this.client = MinecraftClient.getInstance();
	}

	boolean active = true;
	int scaledWidth;
	int scaledHeight;
	HaloComponent[] components = new HaloComponent[3];

	public void toggleHud() {
		active = !active;
	}

	static final int INNER_HALO_MAX_HEIGHT = 24;
	static final int OUTER_HALO_MAX_HEIGHT = 27;

	public void render(MatrixStack matrixStack, float tickDelta) {
		if (client.player != player) {
			player = client.player;
			components[0] = new HealthHalo();
			components[1] = new HungerHalo();
			components[2] = new AirHalo();
		}
		if (player == null) return;
		if (!active) {
			boolean hasVisibleComponent = false;
			for (HaloComponent component : components) {
				component.tick(1.5f * tickDelta, false);
				if (component.ticksRemaining > 0) hasVisibleComponent = true;
			}
			if (!hasVisibleComponent) return;
		}

		// Initialize render environment.
		Window window = this.client.getWindow();
		this.scaledWidth = window.getScaledWidth();
		this.scaledHeight = window.getScaledHeight();
		int previousShaderTexture = RenderSystem.getShaderTexture(0);
		RenderSystem.setShaderTexture(0, haloTexture);
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

		// Pre-computation.
		ActiveEffects effects = new ActiveEffects();

		for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
			StatusEffect effect = effectInstance.getEffectType();
			if (effect == StatusEffects.REGENERATION) effects.regeneration = true;
			else if (effect == StatusEffects.POISON) effects.poison = true;
			else if (effect == StatusEffects.WITHER) effects.wither = true;
			else if (effect == StatusEffects.HUNGER) effects.hunger = true;
		}

		// Anchor point
		int x = (scaledWidth - 27) / 2;
		int y = (scaledHeight - 27) / 2;

		for (HaloComponent component : components) {
			float value = component.computeValue(player, effects);
			boolean shouldRender = (value < 1.0f || component.forceRender());
			if (active) component.tick(tickDelta, shouldRender);
			if (component.ticksRemaining > 0) {
				component.render(matrixStack, x, y);
			}
		}

		// Be kind and clean up after ourselves.
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		RenderSystem.setShaderTexture(0, previousShaderTexture);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}


	static class ActiveEffects {
		boolean regeneration = false;
		boolean poison = false;
		boolean wither = false;
		boolean hunger = false;
	}
}
