package mod.crend.halohud.gui.screen;

import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.ColorController;
import dev.isxander.yacl.gui.controllers.slider.DoubleSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import mod.crend.halohud.config.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;


public class ConfigScreenFactory {

	public static Screen makeArmorComponentScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.armor"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.general"))
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.showElytraBelow"))
							.binding(
									defaults.showElytraBelow,
									() -> config.showElytraBelow,
									val -> config.showElytraBelow = val
							)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorElytra"))
							.binding(
									new Color(defaults.colorElytra, true),
									() -> new Color(config.colorElytra, true),
									val -> config.colorElytra = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.armor"))
					.category(categoryBuilder.build());
		}), parent);
	}

	public static Screen makeAttackComponentScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.attack"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.attackIndicator"))
					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorAttack"))
							.binding(
									new Color(defaults.colorAttack, true),
									() -> new Color(config.colorAttack, true),
									val -> config.colorAttack = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorStrength"))
							.binding(
									new Color(defaults.colorStrength, true),
									() -> new Color(config.colorStrength, true),
									val -> config.colorStrength = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorWeakness"))
							.binding(
									new Color(defaults.colorWeakness, true),
									() -> new Color(config.colorWeakness, true),
									val -> config.colorWeakness = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.progressIndicator"))
					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorProgress"))
							.binding(
									new Color(defaults.colorProgress, true),
									() -> new Color(config.colorProgress, true),
									val -> config.colorProgress = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHaste"))
							.binding(
									new Color(defaults.colorHaste, true),
									() -> new Color(config.colorHaste, true),
									val -> config.colorHaste = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorMiningFatigue"))
							.binding(
									new Color(defaults.colorMiningFatigue, true),
									() -> new Color(config.colorMiningFatigue, true),
									val -> config.colorMiningFatigue = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.attack"))
					.category(categoryBuilder.build());
		}), parent);
	}

	public static Screen makeHealthComponentScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.health"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.general"))
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.showHealthBelow"))
							.binding(
									defaults.showHealthBelow,
									() -> config.showHealthBelow,
									val -> config.showHealthBelow = val
							)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHealth"))
							.binding(
									new Color(defaults.colorHealth, true),
									() -> new Color(config.colorHealth, true),
									val -> config.colorHealth = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.positiveEffects"))
					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorAbsorption"))
							.binding(
									new Color(defaults.colorAbsorption, true),
									() -> new Color(config.colorAbsorption, true),
									val -> config.colorAbsorption = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorRegeneration"))
							.binding(
									new Color(defaults.colorRegeneration, true),
									() -> new Color(config.colorRegeneration, true),
									val -> config.colorRegeneration = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.negativeEffects"))
					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorPoison"))
							.binding(
									new Color(defaults.colorPoison, true),
									() -> new Color(config.colorPoison, true),
									val -> config.colorPoison = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorWither"))
							.binding(
									new Color(defaults.colorWither, true),
									() -> new Color(config.colorWither, true),
									val -> config.colorWither = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.health"))
					.category(categoryBuilder.build());
		}), parent);
	}

	public static Screen makeHungerComponentScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.hunger"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.general"))
					.collapsed(false)
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.showFoodBelow"))
							.binding(
									defaults.showFoodBelow,
									() -> config.showFoodBelow,
									val -> config.showFoodBelow = val
							)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorFood"))
							.binding(
									new Color(defaults.colorFood, true),
									() -> new Color(config.colorFood, true),
									val -> config.colorFood = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHunger"))
							.binding(
									new Color(defaults.colorHunger, true),
									() -> new Color(config.colorHunger, true),
									val -> config.colorHunger = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.hunger"))
					.category(categoryBuilder.build());
		}), parent);
	}

	public static Screen makeStatusComponentScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.status"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.general"))
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.showAirBelow"))
							.binding(
									defaults.showAirBelow,
									() -> config.showAirBelow,
									val -> config.showAirBelow = val
							)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorAir"))
							.binding(
									new Color(defaults.colorAir, true),
									() -> new Color(config.colorAir, true),
									val -> config.colorAir = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.status"))
					.category(categoryBuilder.build());
		}), parent);
	}

	public static Screen makeToolComponentScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.tool"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.general"))
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.showToolBelow"))
							.binding(
									defaults.showToolBelow,
									() -> config.showToolBelow,
									val -> config.showToolBelow = val
							)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorTool"))
							.binding(
									new Color(defaults.colorTool, true),
									() -> new Color(config.colorTool, true),
									val -> config.colorTool = val.getRGB()
							)
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.tool"))
					.category(categoryBuilder.build());
		}), parent);
	}

	public static Screen makeScreen(Screen parent) {
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.title"));

			categoryBuilder.option(Option.createBuilder(int.class)
					.name(Text.translatable("halohud.option.ticksRevealed"))
					.binding(
							defaults.ticksRevealed,
							() -> config.ticksRevealed,
							val -> config.ticksRevealed = val
					)
					.controller(opt -> new IntegerSliderController(opt, 5, 60, 5))
					.build());

			categoryBuilder.option(Option.createBuilder(double.class)
					.name(Text.translatable("halohud.option.haloRadius.title"))
					.tooltip(Text.translatable("halohud.option.haloRadius.tooltip"))
					.binding(
							defaults.haloRadius,
							() -> config.haloRadius,
							val -> config.haloRadius = val
					)
					.controller(opt -> new DoubleSliderController(opt, 10, 40, 0.5))
					.build());
			categoryBuilder.option(Option.createBuilder(double.class)
					.name(Text.translatable("halohud.option.haloWidth.title"))
					.tooltip(Text.translatable("halohud.option.haloWidth.tooltip"))
					.binding(
							defaults.haloWidth,
							() -> config.haloWidth,
							val -> config.haloWidth = val
					)
					.controller(opt -> new DoubleSliderController(opt, 0.5, 10, 0.5))
					.build());

			categoryBuilder.option(Option.createBuilder(double.class)
					.name(Text.translatable("halohud.option.halo2Radius.title"))
					.tooltip(Text.translatable("halohud.option.halo2Radius.tooltip"))
					.binding(
							defaults.halo2Radius,
							() -> config.halo2Radius,
							val -> config.halo2Radius = val
					)
					.controller(opt -> new DoubleSliderController(opt, 10, 40, 0.5))
					.build());
			categoryBuilder.option(Option.createBuilder(double.class)
					.name(Text.translatable("halohud.option.halo2Width.title"))
					.tooltip(Text.translatable("halohud.option.halo2Width.tooltip"))
					.binding(
							defaults.halo2Width,
							() -> config.halo2Width,
							val -> config.halo2Width = val
					)
					.controller(opt -> new DoubleSliderController(opt, 0.5, 10, 0.5))
					.build());
			categoryBuilder.option(Option.createBuilder(Color.class)
					.name(Text.translatable("halohud.option.colorEmpty"))
					.binding(
							new Color(defaults.colorEmpty, true),
							() -> new Color(config.colorEmpty, true),
							val -> config.colorEmpty = val.getRGB()
					)
					.controller(opt -> new ColorController(opt, true))
					.build());

			return builder
					.title(Text.translatable("halohud.title"))
					.category(categoryBuilder.build());
		}), parent);
	}
}