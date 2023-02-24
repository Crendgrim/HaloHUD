package mod.crend.halohud.config;

import net.minecraft.text.Text;

public enum AnimationType {
	Flashing,
	Pulsating,
	Static,
	Disabled;

	public Text getDisplayName() {
		return switch (this) {
			case Flashing -> Text.translatable("halohud.animationtype.flashing");
			case Pulsating -> Text.translatable("halohud.animationtype.pulsating");
			case Static -> Text.translatable("halohud.animationtype.static");
			case Disabled -> Text.translatable("halohud.animationtype.disabled");
		};
	}
}
