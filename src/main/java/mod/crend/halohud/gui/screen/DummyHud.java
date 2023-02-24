package mod.crend.halohud.gui.screen;

import mod.crend.halohud.component.Component;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DummyHud {

	Config dummyConfig;

	List<DummyComponent> components = new ArrayList<>();

	DummyHud(Config dummyConfig) {
		this.dummyConfig = dummyConfig;
	}

	public void init() {
		recalculateHalos();
	}
	public void recalculateHalos() {
		components.clear();
		List<HaloDimensions> haloDimensions = HaloDimensions.getDimensions(dummyConfig);
		for (var dim : haloDimensions) {
			HaloRenderer renderer = new HaloRenderer(dim);
			switch (dim.component()) {
				case Armor -> components.add(new DummyComponent.Armor(renderer));
				case Attack -> components.add(new DummyComponent.Attack(renderer));
				case Health -> components.add(new DummyComponent.Health(renderer));
				case Hunger -> components.add(new DummyComponent.Hunger(renderer));
				case Status -> components.add(new DummyComponent.Status(renderer));
				case Tool -> {
					components.add(new DummyComponent.Tool(renderer, true));
					HaloDimensions offhand = new HaloDimensions(
							dim.component(),
							dim.radius() + dim.width() + 1,
							dim.width(),
							dim.left(),
							dim.right(),
							dim.flipped());
					components.add(new DummyComponent.Tool(new HaloRenderer(offhand), false));
					HaloDimensions full = new HaloDimensions(
							dim.component(),
							dim.radius(),
							dim.width() * 2 + 1,
							dim.left(),
							dim.right(),
							dim.flipped());
					components.add(new DummyComponent.None(new HaloRenderer(full)));
				}
			}
		}
	}

	public void render(MatrixStack matrixStack) {
		components.forEach(c -> c.render(matrixStack, dummyConfig));
	}

	public Optional<DummyComponent> getHoveredComponent(double radius, double theta) {
		double deg = theta * 180 / Math.PI;
		if (deg < 0) deg += 360;

		for (DummyComponent component : components) {
			HaloDimensions dimensions = component.getDimensions();
			if (dimensions.component() != Component.None
					&& radius >= dimensions.radius() && radius <= dimensions.radius() + dimensions.width()) {

				if ((deg >= dimensions.left() && deg <= dimensions.right())
						|| (deg + 360 >= dimensions.left() && deg + 360 <= dimensions.right())
				) {
					return Optional.of(component);
				}
			}
		}

		return Optional.empty();
	}
	public void renderHoveredComponent(MatrixStack matrixStack, DummyComponent component) {
		component.renderBackground(matrixStack, dummyConfig);
	}

	public void tick() {
		recalculateHalos();
		DummyData.staticTick();
		HaloRenderer.staticTick();
	}
}
