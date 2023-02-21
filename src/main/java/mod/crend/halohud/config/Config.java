package mod.crend.halohud.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = "halohud")
public class Config implements ConfigData {
    public int ticksRevealed = 20;

    public boolean flipHalos = false;

    public double haloRadius = 14;
    public double haloWidth = 2;
    public double halo2Radius = 17;
    public double halo2Width = 1;

    public double showHealthBelow = 0.7;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHealth = 0xC0F82423;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorWither = 0xC0352A27;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorPoison = 0xC04E9331;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorRegeneration = 0xC0CD5CAB;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAbsorption = 0xC02552A5;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHealthEmpty = 0x20FFFFFF;

    public double showFoodBelow = 0.7;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorFood = 0xC0AA8000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHunger = 0xC0587653;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorFoodEmpty = 0x20FFFFFF;

    public double showAirBelow = 1.0;
    public double showElytraBelow = 0.5;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAir = 0xC0AAAAFF;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorElytra = 0xC0DDDDDD;

    public double showToolBelow = 0.5;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorTool = 0xC0FFFFFF;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorToolHaste = 0xC0D9C043;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorToolMiningFatigue = 0xC04A4217;


    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAttack = 0xC0FFFFFF;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorProgress = 0xC0308030;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorStrength = 0xC0932423;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorWeakness = 0xC0484D48;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAttackEmpty = 0x20FFFFFF;
}
