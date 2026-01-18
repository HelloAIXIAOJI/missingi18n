package missing.i18n;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen {
    public TestScreen() {
        super(Component.translatable("gui.missingi18n.test_screen.title"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 使用透明背景渲染，避免 1.21.1 的模糊冲突 (IllegalStateException: Can only blur once per frame)
        this.renderTransparentBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        // 故意写上一个没翻译的 ID
        guiGraphics.drawCenteredString(this.font, 
            Component.translatable("text.missingi18n.this_is_not_translated"), 
            this.width / 2, this.height / 2, 0xFFFFFFFF);
            
        guiGraphics.drawCenteredString(this.font, 
            Component.translatable("text.missingi18n.another_missing_key"), 
            this.width / 2, this.height / 2 + 20, 0xFFFFFFFF);
    }
}
