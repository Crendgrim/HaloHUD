package mod.crend.halohud.util;

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
}
