package mod.crend.halohud.gui.screen;

import mod.crend.halohud.util.ActiveEffects;
import net.minecraft.entity.effect.StatusEffect;

public class DummyData {
	private static final int TICKS_CHANGED = 20;
	private static final int TICKS_ATTACK = 10;
	private static int ticksRemaining = 0;
	private static int ticksAttack = 0;
	private static boolean locked = false;

	public static ActiveEffects effects = new ActiveEffects();

	public static float health;
	public static float absorption;
	public static float hunger;
	public static float handItemFoodValue;
	public static float progress;
	public static float toolProgress;
	public static float elytraDurability;
	public static float air;
	public static float toolDurabilityMainHand;
	public static float toolDurabilityOffHand;

	public static void reset() {
		effects.reset();
		health = 0.7f;
		absorption = 0.2f;
		hunger = 0.3f;
		handItemFoodValue = 0.3f;
		progress = 0.7f;
		toolProgress = 0.7f;
		elytraDurability = 0.2f;
		air = 0.9f;
		toolDurabilityMainHand = 0.7f;
		toolDurabilityOffHand = 0.9f;
	}

	public static void tick() {
		if (ticksAttack > 0) {
			--ticksAttack;
			progress = ((float) TICKS_ATTACK - ticksAttack) / TICKS_ATTACK;
		}
		if (ticksRemaining > 0) {
			--ticksRemaining;
			if (ticksRemaining == 0) {
				reset();
			}
		}
		locked = false;
	}

	public static void lock() {
		locked = true;
	}

	public static void enableFrom(StatusEffect effect) {
		if (locked) return;
		effects.enableFrom(effect);
		ticksRemaining = TICKS_CHANGED;
	}

	public static void fakeAttack() {
		if (locked) return;
		toolProgress = 0;
		ticksAttack = TICKS_ATTACK;
		ticksRemaining = TICKS_CHANGED;
	}

	static {
		reset();
	}
}
