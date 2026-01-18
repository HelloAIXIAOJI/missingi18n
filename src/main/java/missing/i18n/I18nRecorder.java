package missing.i18n;

import net.minecraft.client.Minecraft;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class I18nRecorder {
    private static final Logger LOGGER = LoggerFactory.getLogger("Missingi18nRecorder");
    private static final Path ROOT_DIR = FMLPaths.GAMEDIR.get().resolve("missingi18n");
    private static final Set<String> RECORDED_KEYS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void record(String key) {
        if (key == null || key.isEmpty()) {
            return;
        }

        String language = getLanguage();
        String cacheKey = language + ":" + key;
        if (RECORDED_KEYS.contains(cacheKey)) {
            return;
        }

        String modid = "minecraft";
        int dotIndex = key.indexOf('.');
        if (dotIndex != -1) {
            String[] parts = key.split("\\.");
            if (parts.length >= 2) {
                if (isCommonCategory(parts[0])) {
                    modid = parts[1];
                } else {
                    modid = parts[0];
                }
            }
        }

        saveKey(modid, language, key);
        RECORDED_KEYS.add(cacheKey);
    }

    private static boolean isCommonCategory(String part) {
        return part.equals("block") || part.equals("item") || part.equals("entity") || 
               part.equals("container") || part.equals("gui") || part.equals("key") || 
               part.equals("stat") || part.equals("advancements") || part.equals("biome") ||
               part.equals("effect") || part.equals("enchantment") || part.equals("trim_material") ||
               part.equals("trim_pattern") || part.equals("upgrade") || part.equals("itemGroup");
    }

    private static String getLanguage() {
        try {
            // Client side
            return Minecraft.getInstance().getLanguageManager().getSelected();
        } catch (Throwable t) {
            // Server side or early client
            return "en_us";
        }
    }

    private static synchronized void saveKey(String modid, String language, String key) {
        try {
            Path modDir = ROOT_DIR.resolve(modid);
            if (!Files.exists(modDir)) {
                Files.createDirectories(modDir);
            }
            Path file = modDir.resolve(language + ".txt");
            
            // 检查文件内容，防止重复
            if (Files.exists(file)) {
                String content = Files.readString(file, StandardCharsets.UTF_8);
                if (content.contains(key + "|")) {
                    return;
                }
            }

            String line = key + "|";
            Files.writeString(file, line, StandardCharsets.UTF_8, 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.error("Failed to save missing i18n key: " + key, e);
        }
    }
}
