package mod.crend.halohud.util;

public class ActiveEffects {
	public boolean regeneration = false;
	public boolean poison = false;
	public boolean wither = false;
	public boolean hunger = false;

	public void reset() {
		regeneration = poison = wither = hunger = false;
	}
}
