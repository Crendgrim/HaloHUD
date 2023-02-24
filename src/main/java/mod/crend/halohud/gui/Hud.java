package mod.crend.halohud.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.component.*;
import mod.crend.halohud.gui.screen.ConfigScreenFactory;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;

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
		ConfigScreenFactory.configChangeListener = this::init;
	}

	private void init() {
		if (client.player == null) return;
		player = client.player;

		components.clear();
		List<HaloDimensions> haloDimensions = HaloDimensions.getDimensions(HaloHud.config());
		for (var dim : haloDimensions) {
			HaloRenderer renderer = new HaloRenderer(dim);
			switch (dim.component()) {
				case Armor -> components.add(new ArmorHalo(renderer, player, new SoftReference<>(effects)));
				case Attack -> components.add(new AttackHalo(renderer, player, new SoftReference<>(effects)));
				case Health -> components.add(new HealthHalo(renderer, player, new SoftReference<>(effects)));
				case Hunger -> components.add(new HungerHalo(renderer, player, new SoftReference<>(effects)));
				case Status -> components.add(new StatusHalo(renderer, player, new SoftReference<>(effects)));
				case Tool -> {
					components.add(new ToolHalo(renderer, player, new SoftReference<>(effects), true));
					HaloDimensions offhand = new HaloDimensions(Component.Tool,
							dim.radius() + 3,
							dim.width(),
							dim.left(),
							dim.right(),
							dim.flipped());
					components.add(new ToolHalo(new HaloRenderer(offhand), player, new SoftReference<>(effects), false));
				}
			}
		}
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
			effects.enableFrom(effectInstance.getEffectType());
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
