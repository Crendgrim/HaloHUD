package mod.crend.halohud.component;

import net.minecraft.text.Text;

import java.util.Optional;

public enum Component {
	Armor,
	Attack,
	Health,
	Hunger,
	Status,
	Tool,
	None;

	public Optional<Text> text() {
		return switch (this) {
			case Armor -> Optional.of(Text.translatable("halohud.component.armor"));
			case Attack -> Optional.of(Text.translatable("halohud.component.attack"));
			case Health -> Optional.of(Text.translatable("halohud.component.health"));
			case Hunger -> Optional.of(Text.translatable("halohud.component.hunger"));
			case Status -> Optional.of(Text.translatable("halohud.component.status"));
			case Tool -> Optional.of(Text.translatable("halohud.component.tool"));
			case None -> Optional.empty();
		};
	}

}
