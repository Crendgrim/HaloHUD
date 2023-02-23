package mod.crend.halohud.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.component.Component;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.SimpleHaloRenderer;
import mod.crend.halohud.render.component.AttackHaloRenderer;
import mod.crend.halohud.render.component.HealthHaloRenderer;
import mod.crend.halohud.render.component.HungerHaloRenderer;
import mod.crend.halohud.util.ActiveEffects;
import mod.crend.halohud.util.HaloType;
import net.minecraft.client.util.math.MatrixStack;

public class DummyHud {

	HealthHaloRenderer health;
	HungerHaloRenderer hunger;
	SimpleHaloRenderer armor;
	SimpleHaloRenderer status;
	SimpleHaloRenderer tools;
	SimpleHaloRenderer toolMainHand;
	SimpleHaloRenderer toolOffhand;
	AttackHaloRenderer attack;

	ActiveEffects effects = new ActiveEffects();

	DummyHud() {
	}

	public void init() {
		health = new HealthHaloRenderer(new HaloRenderer(() -> HaloHud.config().haloRadius, () -> HaloHud.config().haloWidth, HaloType.Left));
		hunger = new HungerHaloRenderer(new HaloRenderer(() -> HaloHud.config().haloRadius, () -> HaloHud.config().haloWidth, HaloType.Right));
		armor = new SimpleHaloRenderer(new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width, HaloType.Left), HaloHud.config().colorElytra);
		status = new SimpleHaloRenderer(new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width, HaloType.Right), HaloHud.config().colorAir);
		toolMainHand = new SimpleHaloRenderer(new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width, HaloType.Bottom), HaloHud.config().colorTool);
		toolOffhand = new SimpleHaloRenderer(new HaloRenderer(() -> HaloHud.config().halo2Radius + 3, () -> HaloHud.config().halo2Width, HaloType.Bottom), HaloHud.config().colorTool);
		tools = new SimpleHaloRenderer(new HaloRenderer(() -> HaloHud.config().halo2Radius, () -> HaloHud.config().halo2Width + 3, HaloType.Bottom), HaloHud.config().colorTool);
		attack = new AttackHaloRenderer(new HaloRenderer(() -> HaloHud.config().haloRadius, () -> HaloHud.config().haloWidth, HaloType.Bottom));
	}

	public void render(MatrixStack matrixStack) {
		RenderSystem.enableBlend();
		health.render(matrixStack, effects, 0.7f, 0.2f, 1.0f);
		hunger.render(matrixStack, effects, 0.3f, 0.3f, 1.0f);
		attack.render(matrixStack, effects, 0.7f, 0.7f, 1.0f);
		armor.render(matrixStack, 0.2f, 1.0f);
		status.render(matrixStack, 0.9f, 1.0f);
		toolMainHand.render(matrixStack, 0.7f, 1.0f);
		toolOffhand.render(matrixStack, 0.9f, 1.0f);
	}


	public Component getHoveredComponent(double radius, double theta) {
		double deg = theta * 180 / Math.PI;
		if (deg < 0) deg += 360;

		RenderSystem.enableBlend();
		if (radius >= HaloHud.config().haloRadius && radius <= HaloHud.config().haloRadius + HaloHud.config().haloWidth) {
			if (deg >= 40 && deg <= 175) {
				return Component.Hunger;
			} else if (deg >= 185 && deg <= 320) {
				return Component.Health;
			} else if (deg >= 325 || deg <= 35) {
				return Component.Attack;
			}
		} else if (radius >= HaloHud.config().halo2Radius && radius <= HaloHud.config().halo2Radius + HaloHud.config().halo2Width) {
			if (deg >= 40 && deg <= 175) {
				return Component.Status;
			} else if (deg >= 185 && deg <= 320) {
				return Component.Armor;
			} else if (deg >= 325 || deg <= 35) {
				return Component.Tool;
			}
		} else if (radius >= HaloHud.config().halo2Radius && radius <= HaloHud.config().halo2Radius + HaloHud.config().halo2Width + 3) {
			if (deg >= 325 || deg <= 35) {
				return Component.Tool;
			}
		}
		return Component.None;
	}

	public void renderComponent(MatrixStack matrixStack, Component component) {
		switch (component) {
			case Armor -> armor.renderer.render(matrixStack, 0.5f).execute();
			case Attack -> attack.renderer.render(matrixStack, 0.5f).execute();
			case Health -> health.renderer.render(matrixStack, 0.5f).execute();
			case Hunger -> hunger.renderer.render(matrixStack, 0.5f).execute();
			case Status -> status.renderer.render(matrixStack, 0.5f).execute();
			case Tool -> tools.renderer.render(matrixStack, 0.5f).execute();
		};
	}

	public void tick() {
		HaloRenderer.staticTick();
	}
}
