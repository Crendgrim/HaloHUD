package mod.crend.halohud.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.component.*;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import mod.crend.halohud.util.HaloType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class Hud extends DrawableHelper {

	MinecraftClient client;
	ClientPlayerEntity player = null;
	final ActiveEffects effects = new ActiveEffects();
	boolean active = true;
	List<HaloComponent> components = new ArrayList<>();

	public Hud() {
		// Initialize render environment.
		this.client = MinecraftClient.getInstance();
	}

	private void init() {
		player = client.player;

		components.add(new HealthHalo(
				new HaloRenderer(() -> HaloHud.config().haloRadius, () -> HaloHud.config().haloWidth, HaloType.Left),
				player,
				new SoftReference<>(effects)
		));
		components.add(new HungerHalo(
				new HaloRenderer(() -> HaloHud.config().haloRadius, () -> HaloHud.config().haloWidth, HaloType.Right),
				player,
				new SoftReference<>(effects)
		));
		components.add(new ArmorHalo(
				new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width, HaloType.Left),
				player,
				new SoftReference<>(effects)
		));
		components.add(new StatusHalo(
				new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width, HaloType.Right),
				player,
				new SoftReference<>(effects)
		));
		components.add(new ToolHalo(
				new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width, HaloType.Bottom),
				player,
				new SoftReference<>(effects),
				true
		));
		components.add(new ToolHalo(
				new HaloRenderer(() -> HaloHud.config().halo2Radius + 2, () -> HaloHud.config().halo2Width, HaloType.Bottom),
				player,
				new SoftReference<>(effects),
				false
		));
		components.add(new AttackHalo(
				new HaloRenderer(() -> HaloHud.config().haloRadius, () -> HaloHud.config().haloWidth, HaloType.Bottom),
				player,
				new SoftReference<>(effects)
		));
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
		HaloRenderer.staticTick();

		// Pre-computation.
		effects.reset();
		for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
			StatusEffect effect = effectInstance.getEffectType();
			if (effect == StatusEffects.REGENERATION) effects.regeneration = true;
			else if (effect == StatusEffects.POISON) effects.poison = true;
			else if (effect == StatusEffects.WITHER) effects.wither = true;
			else if (effect == StatusEffects.HUNGER) effects.hunger = true;
			else if (effect == StatusEffects.HASTE) effects.haste = true;
			else if (effect == StatusEffects.MINING_FATIGUE) effects.miningFatigue = true;
			else if (effect == StatusEffects.STRENGTH) effects.strength = true;
			else if (effect == StatusEffects.WEAKNESS) effects.weakness = true;
		}
	}

	public void render(MatrixStack matrixStack, float tickDelta) {
		if (player == null) return;

		RenderSystem.enableBlend();
		for (HaloComponent component : components) {
			if (component.isVisible()) {
				component.render(matrixStack, HaloHud.config());
			}
		}
	}

}
