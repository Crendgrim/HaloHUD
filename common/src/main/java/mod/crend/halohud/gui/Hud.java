package mod.crend.halohud.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.component.*;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import mod.crend.halohud.util.HaloDimensions;
import mod.crend.libbamboo.LibBamboo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Arm;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class Hud {

	MinecraftClient client;
	ClientPlayerEntity player = null;
	final ActiveEffects effects = new ActiveEffects();
	List<HaloComponent> components = new ArrayList<>();

	public Hud() {
		// Initialize render environment.
		this.client = MinecraftClient.getInstance();
		if (LibBamboo.HAS_YACL) {
			Config.CONFIG_STORE.withYacl().configChangeEvent.register(this::init);
		}
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
				case Food -> components.add(new FoodHalo(renderer, player, new SoftReference<>(effects)));
				case Status -> components.add(new StatusHalo(renderer, player, new SoftReference<>(effects)));
				case Tool -> {
					if (HaloHud.config().showOffhand) {
						boolean mainHandOnRight = MinecraftClient.getInstance().options.getMainArm().getValue() == Arm.RIGHT;
						components.add(new ToolHalo(new HaloRenderer(dim.splitLeft()), player, new SoftReference<>(effects), !mainHandOnRight));
						components.add(new ToolHalo(new HaloRenderer(dim.splitRight()), player, new SoftReference<>(effects), mainHandOnRight));
					} else {
						components.add(new ToolHalo(renderer, player, new SoftReference<>(effects), true));
					}
				}
			}
		}
	}

	public void toggleHud() {
		HaloHud.config().enabled = !HaloHud.config().enabled;
		Config.CONFIG_STORE.save();
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
		boolean active = HaloHud.config().enabled;
		for (HaloComponent component : components) {
			component.tick(active && component.shouldRender());
			if (!active && component.isVisible()) hasVisibleComponent = true;
		}
		if (!active && !hasVisibleComponent) return;

		// Pre-computation.
		effects.reset();
		for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
			effects.enableFrom(effectInstance.getEffectType());
		}
	}

	public void render(DrawContext context, RenderTickCounter tickDelta) {
		if (player == null) return;

		RenderSystem.enableBlend();
		for (HaloComponent component : components) {
			if (component.isVisible()) {
				component.render(context.getMatrices(), HaloHud.config());
			}
		}
	}

}
