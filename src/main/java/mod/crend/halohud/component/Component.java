package mod.crend.halohud.component;

import dev.isxander.yacl.api.NameableEnum;
import net.minecraft.text.Text;

public enum Component implements NameableEnum {
	Armor,
	Attack,
	Food,
	Health,
	Status,
	Tool,
	None;

	public Text getDisplayName() {
		return switch (this) {
			case Armor -> Text.translatable("halohud.component.armor");
			case Attack -> Text.translatable("halohud.component.attack");
			case Food -> Text.translatable("halohud.component.food");
			case Health -> Text.translatable("halohud.component.health");
			case Status -> Text.translatable("halohud.component.status");
			case Tool -> Text.translatable("halohud.component.tool");
			case None -> Text.translatable("halohud.component.none");
		};
	}

}
