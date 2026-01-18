package missing.i18n.example;

import missing.i18n.api.MissingTranslationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;

/**
 * 演示如何监听 Missingi18n 发布的缺失翻译事件。
 */
public class Missingi18nEventListener {

    @SubscribeEvent
    public void onMissingTranslation(MissingTranslationEvent event) {
        String key = event.getKey();
        String modId = event.getModId();
        String language = event.getLanguage();

        // 1. 控制台打印 (日志通常建议使用英文以保证通用性)
        Missingi18nExample.LOGGER.warn("Captured missing key: [{}] from mod: [{}] in language: [{}]", 
            key, modId, language);

        // 2. 聊天栏打印 (仅客户端)
        try {
            if (Minecraft.getInstance().player != null) {
                Component message = Component.translatable("chat.missingi18n_example.missing_key", key, modId);
                Minecraft.getInstance().player.displayClientMessage(message, false);
            }
        } catch (Throwable ignored) {
            // 防止在服务器端运行时崩溃
        }
    }
}
