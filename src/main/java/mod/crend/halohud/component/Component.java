package mod.crend.halohud.component;

import dev.isxander.yacl.api.NameableEnum;
import net.minecraft.text.Text;

public enum Component implements NameableEnum {
	Armor,
	Attack,
	Health,
	Hunger,
	Status,
	Tool,
	None;

	public Text getDisplayName() {
		return switch (this) {
			case Armor -> Text.translatable("halohud.component.armor");
			case Attack -> Text.translatable("halohud.component.attack");
			case Health -> Text.translatable("halohud.component.health");
			case Hunger -> Text.translatable("halohud.component.hunger");
			case Status -> Text.translatable("halohud.component.status");
			case Tool -> Text.translatable("halohud.component.tool");
			case None -> Text.translatable("halohud.component.none");
		};
	}

}
