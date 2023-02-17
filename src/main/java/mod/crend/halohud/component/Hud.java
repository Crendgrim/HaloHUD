package mod.crend.halohud.component;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.render.HaloRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.lang.ref.SoftReference;

public class Hud extends DrawableHelper {

	MinecraftClient client;
	ClientPlayerEntity player = null;
	final ActiveEffects effects = new ActiveEffects();
	boolean active = true;
	HaloComponent[] components = new HaloComponent[3];

	public Hud() {
		// Initialize render environment.
		this.client = MinecraftClient.getInstance();
	}

	private void init() {
		Window window = client.getWindow();
		double centerX = window.getScaledWidth() / 2.0d;
		double centerY = window.getScaledHeight() / 2.0d;
		player = client.player;

		components[0] = new HealthHalo(
				new HaloRenderer(centerX, centerY, 14, 2, true),
				player,
				new SoftReference<>(effects)
		);
		components[1] = new HungerHalo(
				new HaloRenderer(centerX, centerY, 14, 2, false),
				player,
				new SoftReference<>(effects)
		);
		components[2] = new AirHalo(
				new HaloRenderer(centerX, centerY, 16, 1, 30, 330),
				player,
				new SoftReference<>(effects)
		);
	}


	public void toggleHud() {
		active = !active;
	}

	public void render(MatrixStack matrixStack, float tickDelta) {
		// Setup.
		if (client.player != player) {
			if (player == null) {
				init();
			}
			player = client.player;
			if (player == null) return;
			for (HaloComponent component : components) {
				component.setPlayer(player);
			}
		}
		if (!active) {
			boolean hasVisibleComponent = false;
			for (HaloComponent component : components) {
				component.tick(1.5f * tickDelta, false);
				if (component.isVisible()) hasVisibleComponent = true;
			}
			if (!hasVisibleComponent) return;
		}

		// Pre-computation.
		effects.reset();
		for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
			StatusEffect effect = effectInstance.getEffectType();
			if (effect == StatusEffects.REGENERATION) effects.regeneration = true;
			else if (effect == StatusEffects.POISON) effects.poison = true;
			else if (effect == StatusEffects.WITHER) effects.wither = true;
			else if (effect == StatusEffects.HUNGER) effects.hunger = true;
		}

		// Render.
		RenderSystem.enableBlend();
		for (HaloComponent component : components) {
			if (active) component.tick(tickDelta, component.shouldRender());
			if (component.isVisible()) {
				component.render(matrixStack);
			}
		}
	}

	static class ActiveEffects {
		boolean regeneration = false;
		boolean poison = false;
		boolean wither = false;
		boolean hunger = false;

		void reset() {
			regeneration = poison = wither = hunger = false;
		}
	}

}
