package mod.crend.halohud.gui.screen;

import mod.crend.halohud.component.Component;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.render.HaloRenderer;
import mod.crend.halohud.util.HaloDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DummyHud {

	Config dummyConfig;

	List<DummyComponent> components = new ArrayList<>();

	public DummyHud(Config dummyConfig) {
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
				case Armor  -> components.add(new DummyComponent.Armor(renderer));
				case Attack -> components.add(new DummyComponent.Attack(renderer));
				case Health -> components.add(new DummyComponent.Health(renderer));
				case Food   -> components.add(new DummyComponent.Food(renderer));
				case Status -> components.add(new DummyComponent.Status(renderer));
				case Tool   -> {
					if (dummyConfig.showOffhand) {
						boolean mainHandOnRight = MinecraftClient.getInstance().options.getMainArm().getValue() == Arm.RIGHT;
						components.add(new DummyComponent.Tool(new HaloRenderer(dim.splitLeft()), !mainHandOnRight));
						components.add(new DummyComponent.Tool(new HaloRenderer(dim.splitRight()), mainHandOnRight));
						components.add(new DummyComponent.None(renderer));
					} else {
						components.add(new DummyComponent.Tool(renderer, true));
					}
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
			if (dummyConfig.showOffhand && component instanceof DummyComponent.Tool) continue;
			HaloDimensions dimensions = component.getDimensions();
			// Expand the dimensions a bit for narrow halos so they're easier to click on.
			double componentRadius = dimensions.radius();
			double componentWidth = dimensions.width();
			if (componentWidth < 5) {
				componentWidth = 5;
				// Expand away from the other halo.
				if (componentRadius < Math.max(dummyConfig.haloRadius, dummyConfig.halo2Radius)) {
					componentRadius -= 5;
				}
			}
			if (dimensions.component() != Component.None
					&& radius >= componentRadius && radius <= componentRadius + componentWidth) {

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
		DummyData.tick();
	}
}
