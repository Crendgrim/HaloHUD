package mod.crend.halohud.gui.screen;

import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.ColorController;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.gui.controllers.cycling.CyclingListController;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
import dev.isxander.yacl.gui.controllers.slider.DoubleSliderController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import dev.isxander.yacl.impl.GenericBindingImpl;
import mod.crend.halohud.component.Component;
import mod.crend.halohud.config.AnimationType;
import mod.crend.halohud.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.reverse;


public class ConfigScreenFactory {
	private static final List<Component> VALID_COMPONENTS = Arrays.stream(Component.values()).toList();
	private static final List<AnimationType> VALID_ANIMATION_TYPES = Arrays.stream(AnimationType.values()).toList();

	public static ConfigChangeListener configChangeListener = () -> { };

	private static Config getDummyConfig() {
		try {
			return (Config) Config.INSTANCE.getConfig().clone();
		} catch (CloneNotSupportedException e) {
			return new Config();
		}
	}

	public static Screen makeArmorComponentScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
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
							.listener((opt, val) -> dummyConfig.showElytraBelow = val)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorElytra"))
							.binding(
									new Color(defaults.colorElytra, true),
									() -> new Color(config.colorElytra, true),
									val -> config.colorElytra = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorElytra = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.armor"))
					.category(categoryBuilder.build());
		}), dummyConfig, parent);
	}

	public static Screen makeAttackComponentScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.component.attack"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.attackIndicator"))
					.option(Option.createBuilder(AttackIndicator.class)
							.name(Text.translatable("halohud.option.attackIndicator.title"))
							.tooltip(Text.translatable("halohud.option.attackIndicator.tooltip"))
							.binding(new GenericBindingImpl<>(
									AttackIndicator.OFF,
									MinecraftClient.getInstance().options.getAttackIndicator()::getValue,
									MinecraftClient.getInstance().options.getAttackIndicator()::setValue
							))
							.controller(EnumController::new)
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorAttack"))
							.binding(
									new Color(defaults.colorAttack, true),
									() -> new Color(config.colorAttack, true),
									val -> config.colorAttack = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorAttack = val.getRGB();
								DummyData.fakeAttack();
							})
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorStrength"))
							.binding(
									new Color(defaults.colorStrength, true),
									() -> new Color(config.colorStrength, true),
									val -> config.colorStrength = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorStrength = val.getRGB();
								DummyData.enableFrom(StatusEffects.STRENGTH);
								DummyData.fakeAttack();
							})
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorWeakness"))
							.binding(
									new Color(defaults.colorWeakness, true),
									() -> new Color(config.colorWeakness, true),
									val -> config.colorWeakness = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorWeakness = val.getRGB();
								DummyData.enableFrom(StatusEffects.WEAKNESS);
								DummyData.fakeAttack();
							})
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
							.listener((opt, val) -> dummyConfig.colorProgress = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHaste"))
							.binding(
									new Color(defaults.colorHaste, true),
									() -> new Color(config.colorHaste, true),
									val -> config.colorHaste = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorHaste = val.getRGB();
								DummyData.enableFrom(StatusEffects.HASTE);
							})
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorMiningFatigue"))
							.binding(
									new Color(defaults.colorMiningFatigue, true),
									() -> new Color(config.colorMiningFatigue, true),
									val -> config.colorMiningFatigue = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorMiningFatigue = val.getRGB();
								DummyData.enableFrom(StatusEffects.MINING_FATIGUE);
							})
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.attack"))
					.category(categoryBuilder.build());
		}), dummyConfig, parent);
	}

	public static Screen makeHealthComponentScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
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
							.listener((opt, val) -> dummyConfig.showHealthBelow = val)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHealth"))
							.binding(
									new Color(defaults.colorHealth, true),
									() -> new Color(config.colorHealth, true),
									val -> config.colorHealth = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorHealth = val.getRGB())
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
							.listener((opt, val) -> {
								dummyConfig.colorAbsorption = val.getRGB();
								DummyData.enableFrom(StatusEffects.ABSORPTION);
							})
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorRegeneration"))
							.binding(
									new Color(defaults.colorRegeneration, true),
									() -> new Color(config.colorRegeneration, true),
									val -> config.colorRegeneration = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorRegeneration = val.getRGB();
								DummyData.enableFrom(StatusEffects.REGENERATION);
							})
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
							.listener((opt, val) -> {
								dummyConfig.colorPoison = val.getRGB();
								DummyData.enableFrom(StatusEffects.POISON);
							})
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorWither"))
							.binding(
									new Color(defaults.colorWither, true),
									() -> new Color(config.colorWither, true),
									val -> config.colorWither = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorWither = val.getRGB();
								DummyData.enableFrom(StatusEffects.WITHER);
							})
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.health"))
					.category(categoryBuilder.build());
		}), dummyConfig, parent);
	}

	public static Screen makeHungerComponentScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
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
							.listener((opt, val) -> dummyConfig.showFoodBelow = val)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(AnimationType.class)
							.name(Text.translatable("halohud.option.heldFoodAnimationType"))
							.binding(
									defaults.heldFoodAnimationType,
									() -> config.heldFoodAnimationType,
									val -> config.heldFoodAnimationType = val
							)
							.listener((opt, val) -> dummyConfig.heldFoodAnimationType = val)
							.controller(opt -> new CyclingListController<>(opt, VALID_ANIMATION_TYPES))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorFood"))
							.binding(
									new Color(defaults.colorFood, true),
									() -> new Color(config.colorFood, true),
									val -> config.colorFood = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorFood = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHeldFood"))
							.binding(
									new Color(defaults.colorHeldFood, true),
									() -> new Color(config.colorHeldFood, true),
									val -> config.colorHeldFood = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorHeldFood = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorHunger"))
							.binding(
									new Color(defaults.colorHunger, true),
									() -> new Color(config.colorHunger, true),
									val -> config.colorHunger = val.getRGB()
							)
							.listener((opt, val) -> {
								dummyConfig.colorHunger = val.getRGB();
								DummyData.enableFrom(StatusEffects.HUNGER);
							})
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.hunger"))
					.category(categoryBuilder.build());
		}), dummyConfig, parent);
	}

	public static Screen makeStatusComponentScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
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
							.listener((opt, val) -> dummyConfig.showAirBelow = val)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorAir"))
							.binding(
									new Color(defaults.colorAir, true),
									() -> new Color(config.colorAir, true),
									val -> config.colorAir = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorAir = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.status"))
					.category(categoryBuilder.build());
		}), dummyConfig, parent);
	}

	public static Screen makeToolComponentScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
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
							.listener((opt, val) -> dummyConfig.showToolBelow = val)
							.controller(opt -> new DoubleSliderController(opt, 0.0, 1.0, 0.1))
							.build())

					.option(Option.createBuilder(boolean.class)
							.name(Text.translatable("halohud.option.showOffhand"))
							.binding(
									defaults.showOffhand,
									() -> config.showOffhand,
									val -> config.showOffhand = val
							)
							.listener((opt, val) -> dummyConfig.showOffhand = val)
							.controller(TickBoxController::new)
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorTool"))
							.binding(
									new Color(defaults.colorTool, true),
									() -> new Color(config.colorTool, true),
									val -> config.colorTool = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorTool = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())
					.build());

			return builder
					.title(Text.translatable("halohud.component.tool"))
					.category(categoryBuilder.build());
		}), dummyConfig, parent);
	}

	public static Screen makeScreen(Screen parent) {
		Config dummyConfig = getDummyConfig();
		DummyData.lock();
		return new ConfigScreen(YetAnotherConfigLib.create(Config.INSTANCE, (defaults, config, builder) -> {
			var categoryBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.title"));
			var categoryHaloBuilder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.category.halo"));
			var categoryHalo2Builder = ConfigCategory.createBuilder()
					.name(Text.translatable("halohud.category.halo2"));

			categoryBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.general"))
					.option(Option.createBuilder(int.class)
							.name(Text.translatable("halohud.option.ticksRevealed"))
							.binding(
									defaults.ticksRevealed,
									() -> config.ticksRevealed,
									val -> config.ticksRevealed = val
							)
							.listener((opt, val) -> dummyConfig.ticksRevealed = val)
							.controller(opt -> new IntegerSliderController(opt, 5, 60, 5))
							.build())

					.option(Option.createBuilder(Color.class)
							.name(Text.translatable("halohud.option.colorEmpty"))
							.binding(
									new Color(defaults.colorEmpty, true),
									() -> new Color(config.colorEmpty, true),
									val -> config.colorEmpty = val.getRGB()
							)
							.listener((opt, val) -> dummyConfig.colorEmpty = val.getRGB())
							.controller(opt -> new ColorController(opt, true))
							.build())

					.build()
			);

			categoryHaloBuilder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.halo"))
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.haloRadius.title"))
							.binding(
									defaults.haloRadius,
									() -> config.haloRadius,
									val -> config.haloRadius = val
							)
							.listener((opt, val) -> dummyConfig.haloRadius = val)
							.controller(opt -> new DoubleSliderController(opt, 10, 40, 0.5))
							.build())
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.haloWidth.title"))
							.binding(
									defaults.haloWidth,
									() -> config.haloWidth,
									val -> config.haloWidth = val
							)
							.listener((opt, val) -> dummyConfig.haloWidth = val)
							.controller(opt -> new DoubleSliderController(opt, 0.5, 10, 0.5))
							.build())
					.build()
			);

			categoryHalo2Builder.group(OptionGroup.createBuilder()
					.name(Text.translatable("halohud.category.halo2"))
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.halo2Radius.title"))
							.binding(
									defaults.halo2Radius,
									() -> config.halo2Radius,
									val -> config.halo2Radius = val
							)
							.listener((opt, val) -> dummyConfig.halo2Radius = val)
							.controller(opt -> new DoubleSliderController(opt, 10, 40, 0.5))
							.build())
					.option(Option.createBuilder(double.class)
							.name(Text.translatable("halohud.option.halo2Width.title"))
							.binding(
									defaults.halo2Width,
									() -> config.halo2Width,
									val -> config.halo2Width = val
							)
							.listener((opt, val) -> dummyConfig.halo2Width = val)
							.controller(opt -> new DoubleSliderController(opt, 0.5, 10, 0.5))
							.build())
					.build()
			);

			ListOption<Component> haloComponents = ListOption.createBuilder(Component.class)
					.name(Text.translatable("halohud.option.haloComponents.title"))
					.tooltip(Text.translatable("halohud.option.haloComponents.tooltip"))
					.binding(
							reverse(defaults.haloComponents),
							() -> reverse(config.haloComponents),
							val -> config.haloComponents = reverse(val)
					)
					.controller(opt -> new CyclingListController<>(opt, VALID_COMPONENTS))
					.initial(Component.None)
					.build();
			haloComponents.addListener((opt, val) -> dummyConfig.haloComponents = reverse(val));
			categoryHaloBuilder.group(haloComponents);

			ListOption<Component> halo2Components = ListOption.createBuilder(Component.class)
					.name(Text.translatable("halohud.option.halo2Components.title"))
					.tooltip(Text.translatable("halohud.option.haloComponents.tooltip"))
					.binding(
							reverse(defaults.halo2Components),
							() -> reverse(config.halo2Components),
							val -> config.halo2Components = reverse(val)
					)
					.controller(opt -> new CyclingListController<>(opt, VALID_COMPONENTS))
					.initial(Component.None)
					.build();
			halo2Components.addListener((opt, val) -> dummyConfig.halo2Components = reverse(val));
			categoryHalo2Builder.group(halo2Components);

			return builder
					.title(Text.translatable("halohud.title"))
					.save(() -> { Config.INSTANCE.save(); configChangeListener.onConfigChange(); })
					.category(categoryBuilder.build())
					.category(categoryHaloBuilder.build())
					.category(categoryHalo2Builder.build());
		}), dummyConfig, parent);
	}

	public interface ConfigChangeListener {
		void onConfigChange();
	}
}