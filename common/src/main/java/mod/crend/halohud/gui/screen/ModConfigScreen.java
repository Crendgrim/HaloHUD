package mod.crend.halohud.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.gui.YACLScreen;
import mod.crend.halohud.config.Config;
import mod.crend.halohud.gui.screen.DummyComponent;
import mod.crend.halohud.gui.screen.DummyHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Optional;

public class ModConfigScreen extends YACLScreen {

	protected final Screen parent;
	protected final Config dummyConfig;
	DummyHud dummyHud;

	public ModConfigScreen(YetAnotherConfigLib config, Config dummyConfig, Screen parent) {
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

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			int x = 5 * this.width / 6;
			int y = this.height / 2;
			double radius = Math.sqrt((mouseX - x) * (mouseX - x) + (mouseY - y) * (mouseY - y));
			double theta = Math.atan2(mouseX - x, mouseY - y);

			Optional<DummyComponent> hoveredComponent = dummyHud.getHoveredComponent(radius, theta);
			if (hoveredComponent.isPresent()) {
				switch (hoveredComponent.get().getComponent()) {
					case Armor  -> tabNavigationBar.selectTab(3, true);
					case Attack -> tabNavigationBar.selectTab(4, true);
					case Health -> tabNavigationBar.selectTab(5, true);
					case Food   -> tabNavigationBar.selectTab(6, true);
					case Status -> tabNavigationBar.selectTab(7, true);
					case Tool   -> tabNavigationBar.selectTab(8, true);
				}
				return true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		int x = 5 * this.width / 6;
		// Drawing begins at the center of the screen
		int deltaX = x - this.width / 2;
		int y = this.height / 2;

		double radius = Math.sqrt((mouseX - x) * (mouseX - x) + (mouseY - y) * (mouseY - y));
		double theta = Math.atan2(mouseX - x, mouseY - y);

		Optional<DummyComponent> hoveredComponent = dummyHud.getHoveredComponent(radius, theta);

		MatrixStack matrices = context.getMatrices();
		matrices.push();
		matrices.translate(deltaX, 0, 0);
		RenderSystem.enableBlend();
		hoveredComponent.ifPresent(component -> dummyHud.renderHoveredComponent(matrices, component));
		dummyHud.render(matrices);
		matrices.pop();
		hoveredComponent.ifPresent(component -> context.drawTooltip(textRenderer, component.getComponent().getDisplayName(), mouseX, mouseY));
	}
}
