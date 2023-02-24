package mod.crend.halohud.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.GsonConfigInstance;
import mod.crend.halohud.component.Component;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class Config implements Cloneable {
    public static final GsonConfigInstance<Config> INSTANCE = new GsonConfigInstance<>(
            Config.class,
            FabricLoader.getInstance().getConfigDir().resolve("halohud.json"),
            GsonBuilder::setPrettyPrinting
    );

    @ConfigEntry
    public int ticksRevealed = 20;

    @ConfigEntry public List<Component> haloComponents = List.of(Component.Health, Component.Hunger, Component.Attack);
    @ConfigEntry public List<Component> halo2Components = List.of(Component.Armor, Component.Status, Component.Tool);

    @ConfigEntry public double haloRadius = 14;
    @ConfigEntry public double haloWidth = 2;
    @ConfigEntry public double halo2Radius = 17;
    @ConfigEntry public double halo2Width = 1;
    @ConfigEntry public int colorEmpty = 0x20FFFFFF;

    @ConfigEntry public double showElytraBelow = 0.5;
    @ConfigEntry public int colorElytra = 0xC0DDDDDD;

    @ConfigEntry public int colorAttack = 0xC0FFFFFF;
    @ConfigEntry public int colorProgress = 0xC0308030;
    @ConfigEntry public int colorStrength = 0xC0932423;
    @ConfigEntry public int colorHaste = 0xC0D9C043;
    @ConfigEntry public int colorWeakness = 0xC0484D48;
    @ConfigEntry public int colorMiningFatigue = 0xC04A4217;

    @ConfigEntry public double showHealthBelow = 0.7;
    @ConfigEntry public int colorHealth = 0xC0F82423;
    @ConfigEntry public int colorAbsorption = 0xC02552A5;
    @ConfigEntry public int colorRegeneration = 0xC0CD5CAB;
    @ConfigEntry public int colorPoison = 0xC04E9331;
    @ConfigEntry public int colorWither = 0xC0352A27;

    @ConfigEntry public double showFoodBelow = 0.7;
    @ConfigEntry public int colorFood = 0xC0AA8000;
    @ConfigEntry public int colorHunger = 0xC0587653;

    @ConfigEntry public double showAirBelow = 1.0;
    @ConfigEntry public int colorAir = 0xC0AAAAFF;

    @ConfigEntry public double showToolBelow = 0.5;
    @ConfigEntry public int colorTool = 0xC0FFFFFF;

    public Object clone() throws CloneNotSupportedException { return super.clone(); }
}
