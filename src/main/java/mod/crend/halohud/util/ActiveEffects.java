package mod.crend.halohud.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public class ActiveEffects {
	public boolean regeneration = false;
	public boolean poison = false;
	public boolean wither = false;
	public boolean hunger = false;
	public boolean haste = false;
	public boolean miningFatigue = false;
	public boolean strength = false;
	public boolean weakness = false;

	public void reset() {
		regeneration
				= poison
				= wither
				= hunger
				= haste
				= miningFatigue
				= strength
				= weakness
				= false;
	}

	public void enableFrom(/*? if <1.20.5 {*/StatusEffect/*?} else {*//*RegistryEntry<StatusEffect>*//*?}*/ effect) {
		if (effect == StatusEffects.REGENERATION) this.regeneration = true;
		else if (effect == StatusEffects.POISON) this.poison = true;
		else if (effect == StatusEffects.WITHER) this.wither = true;
		else if (effect == StatusEffects.HUNGER) this.hunger = true;
		else if (effect == StatusEffects.HASTE) this.haste = true;
		else if (effect == StatusEffects.MINING_FATIGUE) this.miningFatigue = true;
		else if (effect == StatusEffects.STRENGTH) this.strength = true;
		else if (effect == StatusEffects.WEAKNESS) this.weakness = true;
	}
}
