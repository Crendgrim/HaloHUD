package mod.crend.halohud.component;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.HaloType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
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
		player = client.player;

		components[0] = new HealthHalo(
				new HaloRenderer(() -> HaloHud.config.haloRadius, () -> HaloHud.config.haloWidth, HaloType.Left),
				player,
				new SoftReference<>(effects)
		);
		components[1] = new HungerHalo(
				new HaloRenderer(() -> HaloHud.config.haloRadius, () -> HaloHud.config.haloWidth, HaloType.Right),
				player,
				new SoftReference<>(effects)
		);
		components[2] = new AirHalo(
				new HaloRenderer(() -> HaloHud.config.halo2Radius, () -> HaloHud.config.halo2Width, HaloType.Full),
				player,
				new SoftReference<>(effects)
		);
	}

	public void toggleHud() {
		active = !active;
	}

	public void tick() {
		// Setup.
		if (client.player == null) {
			return;
		}
		if (client.player != player) {
			if (player == null) {
				init();
			} else {
				player = client.player;
				for (HaloComponent component : components) {
					component.setPlayer(player);
				}
			}
		}

		// Tick components.
		boolean hasVisibleComponent = false;
		for (HaloComponent component : components) {
			component.tick(active && component.shouldRender());
			if (!active && component.isVisible()) hasVisibleComponent = true;
		}
		if (!active && !hasVisibleComponent) return;

		// Pre-computation.
		effects.reset();
		for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
			StatusEffect effect = effectInstance.getEffectType();
			if (effect == StatusEffects.REGENERATION) effects.regeneration = true;
			else if (effect == StatusEffects.POISON) effects.poison = true;
			else if (effect == StatusEffects.WITHER) effects.wither = true;
			else if (effect == StatusEffects.HUNGER) effects.hunger = true;
		}
	}

	public void render(MatrixStack matrixStack, float tickDelta) {
		if (player == null) return;

		RenderSystem.enableBlend();
		for (HaloComponent component : components) {
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
