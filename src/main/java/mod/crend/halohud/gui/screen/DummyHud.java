package mod.crend.halohud.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.component.Component;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.SimpleHaloRenderer;
import mod.crend.halohud.render.component.AttackHaloRenderer;
import mod.crend.halohud.render.component.HealthHaloRenderer;
import mod.crend.halohud.render.component.HungerHaloRenderer;
import mod.crend.halohud.util.HaloType;
import net.minecraft.client.util.math.MatrixStack;

public class DummyHud {

	Config dummyConfig;

	HealthHaloRenderer health;
	HungerHaloRenderer hunger;
	SimpleHaloRenderer armor;
	SimpleHaloRenderer status;
	SimpleHaloRenderer tools;
	SimpleHaloRenderer toolMainHand;
	SimpleHaloRenderer toolOffhand;
	AttackHaloRenderer attack;

	DummyHud(Config dummyConfig) {
		this.dummyConfig = dummyConfig;
	}

	public void init() {
		health = new HealthHaloRenderer(new HaloRenderer(() -> dummyConfig.haloRadius, () -> dummyConfig.haloWidth, HaloType.Left));
		hunger = new HungerHaloRenderer(new HaloRenderer(() -> dummyConfig.haloRadius, () -> dummyConfig.haloWidth, HaloType.Right));
		armor = new SimpleHaloRenderer(new HaloRenderer(() -> dummyConfig.halo2Radius, () -> dummyConfig.halo2Width, HaloType.Left));
		status = new SimpleHaloRenderer(new HaloRenderer(() -> dummyConfig.halo2Radius, () -> dummyConfig.halo2Width, HaloType.Right));
		toolMainHand = new SimpleHaloRenderer(new HaloRenderer(() -> dummyConfig.halo2Radius, () -> dummyConfig.halo2Width, HaloType.Bottom));
		toolOffhand = new SimpleHaloRenderer(new HaloRenderer(() -> dummyConfig.halo2Radius + 3, () -> dummyConfig.halo2Width, HaloType.Bottom));
		tools = new SimpleHaloRenderer(new HaloRenderer(() -> dummyConfig.halo2Radius, () -> dummyConfig.halo2Width + 3, HaloType.Bottom));
		attack = new AttackHaloRenderer(new HaloRenderer(() -> dummyConfig.haloRadius, () -> dummyConfig.haloWidth, HaloType.Bottom));
	}

	public void render(MatrixStack matrixStack) {
		RenderSystem.enableBlend();
		health.render(matrixStack, dummyConfig, DummyData.effects, DummyData.health, DummyData.absorption, 1.0f);
		hunger.render(matrixStack, dummyConfig, DummyData.effects, DummyData.hunger, DummyData.handItemFoodValue, 1.0f);
		attack.render(matrixStack, dummyConfig, DummyData.effects, DummyData.progress, DummyData.toolProgress, 1.0f);
		armor.render(matrixStack, dummyConfig, dummyConfig.colorElytra, DummyData.elytraDurability, 1.0f);
		status.render(matrixStack, dummyConfig, dummyConfig.colorAir, DummyData.air, 1.0f);
		toolMainHand.render(matrixStack, dummyConfig, dummyConfig.colorTool, DummyData.toolDurabilityMainHand, 1.0f);
		toolOffhand.render(matrixStack, dummyConfig, dummyConfig.colorTool, DummyData.toolDurabilityOffHand, 1.0f);
	}


	public Component getHoveredComponent(double radius, double theta) {
		double deg = theta * 180 / Math.PI;
		if (deg < 0) deg += 360;

		RenderSystem.enableBlend();
		if (radius >= dummyConfig.haloRadius && radius <= dummyConfig.haloRadius + dummyConfig.haloWidth) {
			if (deg >= 40 && deg <= 175) {
				return Component.Hunger;
			} else if (deg >= 185 && deg <= 320) {
				return Component.Health;
			} else if (deg >= 325 || deg <= 35) {
				return Component.Attack;
			}
		} else if (radius >= dummyConfig.halo2Radius && radius <= dummyConfig.halo2Radius + dummyConfig.halo2Width) {
			if (deg >= 40 && deg <= 175) {
				return Component.Status;
			} else if (deg >= 185 && deg <= 320) {
				return Component.Armor;
			} else if (deg >= 325 || deg <= 35) {
				return Component.Tool;
			}
		} else if (radius >= dummyConfig.halo2Radius && radius <= dummyConfig.halo2Radius + dummyConfig.halo2Width + 3) {
			if (deg >= 325 || deg <= 35) {
				return Component.Tool;
			}
		}
		return Component.None;
	}

	public void renderComponent(MatrixStack matrixStack, Component component) {
		switch (component) {
			case Armor -> armor.renderer.render(matrixStack, 0.5f).execute(dummyConfig);
			case Attack -> attack.renderer.render(matrixStack, 0.5f).execute(dummyConfig);
			case Health -> health.renderer.render(matrixStack, 0.5f).execute(dummyConfig);
			case Hunger -> hunger.renderer.render(matrixStack, 0.5f).execute(dummyConfig);
			case Status -> status.renderer.render(matrixStack, 0.5f).execute(dummyConfig);
			case Tool -> tools.renderer.render(matrixStack, 0.5f).execute(dummyConfig);
		};
	}

	public void tick() {
		DummyData.staticTick();
		HaloRenderer.staticTick();
	}
}
