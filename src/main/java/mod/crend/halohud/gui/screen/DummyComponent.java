package mod.crend.halohud.gui.screen;

import mod.crend.halohud.component.Component;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.render.SimpleHaloRenderer;
import mod.crend.halohud.render.component.AttackHaloRenderer;
import mod.crend.halohud.render.component.HealthHaloRenderer;
import mod.crend.halohud.render.component.FoodHaloRenderer;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.util.math.MatrixStack;

public abstract class DummyComponent {

	public abstract void render(MatrixStack matrixStack, Config config, float intensity);
	public void render(MatrixStack matrixStack, Config config) {
		render(matrixStack, config, 1.0f);
	}
	public void renderBackground(MatrixStack matrixStack, Config config) {
		render(matrixStack, config, 0.5f);
	}
	public abstract void setDimensions(HaloDimensions dimensions);
	public abstract HaloDimensions getDimensions();
	public Component getComponent() {
		return getDimensions().component();
	}

	public static class Health extends DummyComponent {
		HealthHaloRenderer renderer;
		public Health(HaloRenderer renderer) {
			this.renderer = new HealthHaloRenderer(renderer);
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, DummyData.effects, DummyData.health, DummyData.absorption, intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}

	public static class Food extends DummyComponent {
		FoodHaloRenderer renderer;
		public Food(HaloRenderer renderer) {
			this.renderer = new FoodHaloRenderer(renderer);
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, DummyData.effects, DummyData.food, DummyData.handItemFoodValue, intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}

	public static class Attack extends DummyComponent {
		AttackHaloRenderer renderer;
		public Attack(HaloRenderer renderer) {
			this.renderer = new AttackHaloRenderer(renderer);
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, DummyData.effects, DummyData.progress, DummyData.toolProgress, intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}

	public static class Armor extends DummyComponent {
		SimpleHaloRenderer renderer;
		public Armor(HaloRenderer renderer) {
			this.renderer = new SimpleHaloRenderer(renderer);
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, config.colorElytra, DummyData.elytraDurability, intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}

	public static class Status extends DummyComponent {
		SimpleHaloRenderer renderer;
		public Status(HaloRenderer renderer) {
			this.renderer = new SimpleHaloRenderer(renderer);
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, config.colorAir, DummyData.air, intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}

	public static class Tool extends DummyComponent {
		SimpleHaloRenderer renderer;
		boolean mainHand;
		public Tool(HaloRenderer renderer, boolean mainHand) {
			this.renderer = new SimpleHaloRenderer(renderer);
			this.mainHand = mainHand;
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, config.colorTool, (mainHand ? DummyData.toolDurabilityMainHand : DummyData.toolDurabilityOffHand), intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}

	public static class None extends DummyComponent {
		SimpleHaloRenderer renderer;
		public None(HaloRenderer renderer) {
			this.renderer = new SimpleHaloRenderer(renderer);
		}

		@Override
		public void render(MatrixStack matrixStack, Config config, float intensity) {
			renderer.render(matrixStack, config, config.colorEmpty, 1.0f, intensity);
		}

		@Override
		public void setDimensions(HaloDimensions dimensions) {
			renderer.renderer.setDimensions(dimensions);
		}
		public HaloDimensions getDimensions() {
			return renderer.renderer.getDimensions();
		}
	}
}
