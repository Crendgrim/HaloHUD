package mod.crend.halohud.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.api.utils.OptionUtils;
import dev.isxander.yacl.gui.YACLScreen;
import mod.crend.halohud.config.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigScreen extends YACLScreen {

	protected final Screen parent;
	protected final Config dummyConfig;
	DummyHud dummyHud;

	public ConfigScreen(YetAnotherConfigLib config, Config dummyConfig, Screen parent) {
		super(config, parent);
		this.dummyConfig = dummyConfig;
		this.parent = parent;
		this.dummyHud = new DummyHud(dummyConfig);
	}

	@Override
	protected void init() {
		super.init();
		dummyHud.init();
	}


	@Override
	public void tick() {
		super.tick();
		dummyHud.tick();
	}

	private void setSaveButtonMessage(Text message, Text tooltip) {
		saveButtonMessage = message;
		saveButtonTooltipMessage = tooltip;
	}

	private boolean pendingChanges() {
		AtomicBoolean pendingChanges = new AtomicBoolean(false);
		OptionUtils.consumeOptions(config, (option) -> {
			if (option.changed()) {
				pendingChanges.set(true);
				return true;
			}
			return false;
		});

		return pendingChanges.get();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			int x = client.getWindow().getScaledWidth() / 6;
			int y = client.getWindow().getScaledHeight() / 2;
			double radius = Math.sqrt((mouseX - x) * (mouseX - x) + (mouseY - y) * (mouseY - y));
			double theta = Math.atan2(mouseX - x, mouseY - y);

			Optional<DummyComponent> hoveredComponent = dummyHud.getHoveredComponent(radius, theta);
			if (hoveredComponent.isPresent()) {
				if (pendingChanges()) {
					setSaveButtonMessage(Text.translatable("yacl.gui.save_before_exit").formatted(Formatting.RED), Text.translatable("yacl.gui.save_before_exit.tooltip"));
					return false;
				}
				Screen ref = (this.parent instanceof ConfigScreen ? this.parent : this);
				switch (hoveredComponent.get().getComponent()) {
					case Armor -> this.client.setScreen(ConfigScreenFactory.makeArmorComponentScreen(ref));
					case Attack -> this.client.setScreen(ConfigScreenFactory.makeAttackComponentScreen(ref));
					case Health -> this.client.setScreen(ConfigScreenFactory.makeHealthComponentScreen(ref));
					case Hunger -> this.client.setScreen(ConfigScreenFactory.makeHungerComponentScreen(ref));
					case Status -> this.client.setScreen(ConfigScreenFactory.makeStatusComponentScreen(ref));
					case Tool -> this.client.setScreen(ConfigScreenFactory.makeToolComponentScreen(ref));
				}
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		int x = client.getWindow().getScaledWidth() / 2;
		double deltaX = x / 3.0 - x;
		x /= 3;
		int y = client.getWindow().getScaledHeight() / 2;
		double radius = Math.sqrt((mouseX - x) * (mouseX - x) + (mouseY - y) * (mouseY - y));
		double theta = Math.atan2(mouseX - x, mouseY - y);

		Optional<DummyComponent> hoveredComponent = dummyHud.getHoveredComponent(radius, theta);

		matrices.push();
		matrices.translate(deltaX, 0, 0);
		RenderSystem.enableBlend();
		hoveredComponent.ifPresent(component -> dummyHud.renderHoveredComponent(matrices, component));
		dummyHud.render(matrices);
		matrices.pop();
		hoveredComponent.ifPresent(component -> renderTooltip(matrices, component.getComponent().getDisplayName(), mouseX, mouseY));
	}
}
