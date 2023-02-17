package mod.crend.halohud.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = "halohud")
public class Config implements ConfigData {
    public int ticksRevealed = 30;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHealth = 0x80FF0000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorWither = 0x80000000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorPoison = 0x8060A000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorRegeneration = 0x80FF00D0;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAbsorption = 0x80E0A000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHealthEmpty = 0x20FFFFFF;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorFood = 0x80AA8000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHunger = 0x8060A000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorFoodEmpty = 0x20FFFFFF;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAir = 0x80AAAAFF;
}
