package mod.crend.halohud.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@me.shedaniel.autoconfig.annotation.Config(name = "halohud")
public class Config implements ConfigData {
    public int ticksRevealed = 30;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHealth = 0xFFFF0000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorWither = 0xFF000000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorPoison = 0xFF60A000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorRegeneration = 0xFFFF00D0;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAbsorption = 0xFFE0A000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHealthEmpty = 0x20FFFFFF;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorFood = 0xFFAA8000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorHunger = 0xFF60A000;
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorFoodEmpty = 0x20FFFFFF;

    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int colorAir = 0x80AAAAFF;
}
