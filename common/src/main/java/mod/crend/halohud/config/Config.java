package mod.crend.halohud.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import mod.crend.halohud.HaloHud;
import mod.crend.halohud.component.Component;
import mod.crend.halohud.gui.screen.DummyData;
import mod.crend.yaclx.auto.annotation.*;
import mod.crend.yaclx.opt.ConfigStore;

import java.awt.Color;
import java.util.List;

@AutoYaclConfig(modid = HaloHud.MOD_ID, filename = "halohud.json5")
public class Config implements Cloneable {

    public static final ConfigStore<Config> CONFIG_STORE = new ConfigStore<>(Config.class);

    @SerialEntry
    public boolean enabled = true;
    @NumericRange(min = 5, max = 60, interval = 5)
    @SerialEntry public int ticksRevealed = 20;

    @Category(name = "halo")
    @Reverse
    @SerialEntry public List<Component> haloComponents = List.of(Component.Health, Component.Food, Component.Attack);
    @Category(name = "halo2")
    @Reverse
    @SerialEntry public List<Component> halo2Components = List.of(Component.Armor, Component.Status, Component.Tool);

    @Category(name = "halo")
    @FloatingPointRange(min = 10, max = 40, interval = 0.5)
    @SerialEntry public double haloRadius = 28;
    @Category(name = "halo")
    @FloatingPointRange(min = 0.5, max = 10, interval = 0.5)
    @SerialEntry public double haloWidth = 2;
    @Category(name = "halo2")
    @FloatingPointRange(min = 10, max = 40, interval = 0.5)
    @SerialEntry public double halo2Radius = 31;
    @Category(name = "halo2")
    @FloatingPointRange(min = 0.5, max = 10, interval = 0.5)
    @SerialEntry public double halo2Width = 1;
    @SerialEntry public Color colorEmpty = new Color(0x20FFFFFF, true);

    @Category(name = "armor")
    @SerialEntry public boolean showArmorAlways = false;
    @Category(name = "armor")
    @FloatingPointRange(min = 0, max = 1, interval = 0.1)
    @SerialEntry public double showElytraBelow = 0.5;
    @Category(name = "armor")
    @SerialEntry public Color colorElytra = new Color(0xC0DDDDDD, true);

    @Category(name = "attack")
    @SerialEntry public boolean showAttackAlways = false;
    @Category(name = "attack", group = "attackIndicator")
    @Listener(DummyData.Attack.class)
    @SerialEntry public Color colorAttack = new Color(0xC0FFFFFF, true);
    @Category(name = "attack", group = "progressIndicator")
    @Listener(DummyData.Attack.class)
    @SerialEntry public Color colorProgress = new Color(0xC0308030, true);
    @Category(name = "attack", group = "attackIndicator")
    @Listener(DummyData.Attack.class)
    @Listener(DummyData.Strength.class)
    @SerialEntry public Color colorStrength = new Color(0xC0932423, true);
    @Category(name = "attack", group = "progressIndicator")
    @Listener(DummyData.Attack.class)
    @Listener(DummyData.Haste.class)
    @SerialEntry public Color colorHaste = new Color(0xC0D9C043, true);
    @Category(name = "attack", group = "attackIndicator")
    @Listener(DummyData.Attack.class)
    @Listener(DummyData.Weakness.class)
    @SerialEntry public Color colorWeakness = new Color(0xC0484D48, true);
    @Category(name = "attack", group = "progressIndicator")
    @Listener(DummyData.Attack.class)
    @Listener(DummyData.MiningFatigue.class)
    @SerialEntry public Color colorMiningFatigue = new Color(0xC04A4217, true);

    @Category(name = "health")
    @SerialEntry public boolean showHealthAlways = false;
    @Category(name = "health")
    @FloatingPointRange(min = 0, max = 1, interval = 0.1)
    @EnableIf(field = "showHealthAlways", value = EnableIf.NegativeBooleanPredicate.class)
    @SerialEntry public double showHealthBelow = 0.7;
    @Category(name = "health")
    @SerialEntry public Color colorHealth = new Color(0xC0F82423, true);
    @Category(name = "health", group = "positiveEffects")
    @Listener(DummyData.Absorption.class)
    @SerialEntry public Color colorAbsorption = new Color(0xC02552A5, true);
    @Category(name = "health", group = "positiveEffects")
    @Listener(DummyData.Regeneration.class)
    @SerialEntry public Color colorRegeneration = new Color(0xC0CD5CAB, true);
    @Category(name = "health", group = "negativeEffects")
    @Listener(DummyData.Poison.class)
    @SerialEntry public Color colorPoison = new Color(0xC04E9331, true);
    @Category(name = "health", group = "negativeEffects")
    @Listener(DummyData.Wither.class)
    @SerialEntry public Color colorWither = new Color(0xC0352A27, true);

    @Category(name = "food")
    @SerialEntry public boolean showFoodAlways = false;
    @Category(name = "food")
    @FloatingPointRange(min = 0, max = 1, interval = 0.1)
    @EnableIf(field = "showFoodAlways", value = EnableIf.NegativeBooleanPredicate.class)
    @SerialEntry public double showFoodBelow = 0.7;
    @Category(name = "food")
    @SerialEntry public Color colorFood = new Color(0xC0AA8000, true);
    @Category(name = "food")
    @SerialEntry public Color colorHeldFood = new Color(0xC0AA8000, true);
    @Category(name = "food")
    @Listener(DummyData.Hunger.class)
    @SerialEntry public Color colorHunger = new Color(0xC0587653, true);
    @Category(name = "food")
    @SerialEntry public AnimationType heldFoodAnimationType = AnimationType.Pulsating;

    @Category(name = "status")
    @SerialEntry public boolean showStatusAlways = false;
    @Category(name = "status")
    @FloatingPointRange(min = 0, max = 1, interval = 0.1)
    @SerialEntry public double showAirBelow = 1.0;
    @Category(name = "status")
    @SerialEntry public Color colorAir = new Color(0xC0AAAAFF, true);
    @Category(name = "status")
    @SerialEntry public Color colorFreezing = new Color(0xC0AAFFFF, true);

    @Category(name = "tool")
    @SerialEntry public boolean showToolAlways = false;
    @Category(name = "tool")
    @FloatingPointRange(min = 0, max = 1, interval = 0.1)
    @EnableIf(field = "showToolAlways", value = EnableIf.NegativeBooleanPredicate.class)
    @SerialEntry public double showToolBelow = 0.5;
    @Category(name = "tool")
    @SerialEntry public boolean showOffhand = true;
    @Category(name = "tool")
    @SerialEntry public Color colorTool = new Color(0xC0FFFFFF, true);

    public Object clone() throws CloneNotSupportedException { return super.clone(); }
}
