package mod.crend.halohud.util;

import mod.crend.halohud.component.Component;
import mod.crend.halohud.config.Config;

import java.util.List;
import java.util.stream.Stream;

public record HaloDimensions(Component component, double radius, double width, int left, int right, boolean flipped) {

	public static List<HaloDimensions> getDimensions(Config config) {
		return Stream.concat(
				getDimensions(config.haloComponents, config.haloRadius, config.haloWidth).stream(),
				getDimensions(config.halo2Components, config.halo2Radius, config.halo2Width).stream()
		).toList();
	}
	public static List<HaloDimensions> getDimensions(List<Component> haloComponents, double haloRadius, double haloWidth) {
		return switch (haloComponents.size()) {
			case 0 -> List.of();
			case 1 -> List.of(
					new HaloDimensions(haloComponents.get(0), haloRadius, haloWidth, 40, 320, true)
			);
			case 2 -> List.of(
					new HaloDimensions(haloComponents.get(0), haloRadius, haloWidth, 185, 320, true),
					new HaloDimensions(haloComponents.get(1), haloRadius, haloWidth, 40, 175, false)
			);
			case 3 -> List.of(
					new HaloDimensions(haloComponents.get(0), haloRadius, haloWidth, 185, 320, true),
					new HaloDimensions(haloComponents.get(1), haloRadius, haloWidth, 40, 175, false),
					new HaloDimensions(haloComponents.get(2), haloRadius, haloWidth, 325, 360 + 35, false)
			);
			case 4 -> List.of(
					new HaloDimensions(haloComponents.get(0), haloRadius, haloWidth, 210, 320, true),
					new HaloDimensions(haloComponents.get(1), haloRadius, haloWidth, 40, 140, false),
					new HaloDimensions(haloComponents.get(2), haloRadius, haloWidth, 325, 360 + 35, false),
					new HaloDimensions(haloComponents.get(3), haloRadius, haloWidth, 145, 215, true)
			);
			default -> List.of(
					// bottom left
					new HaloDimensions(haloComponents.get(0), haloRadius, haloWidth, 253, 323, true),
					// bottom right
					new HaloDimensions(haloComponents.get(1), haloRadius, haloWidth, 37, 107, false),
					// bottom
					new HaloDimensions(haloComponents.get(2), haloRadius, haloWidth, 325, 360 + 35, false),
					// top left
					new HaloDimensions(haloComponents.get(3), haloRadius, haloWidth, 181, 251, true),
					// top right
					new HaloDimensions(haloComponents.get(4), haloRadius, haloWidth, 109, 179, false)
			);
		};
	}
}
