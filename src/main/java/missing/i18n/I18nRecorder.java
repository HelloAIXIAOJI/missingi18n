package missing.i18n;

import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class I18nRecorder {
    private static final Logger LOGGER = LoggerFactory.getLogger("Missingi18nRecorder");
    private static final Path ROOT_DIR = FMLPaths.GAMEDIR.get().resolve("missingi18n");
    private static final Set<String> RECORDED_KEYS = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static Set<String> MOD_IDS = null;

    public static void record(String key) {
        if (key == null || key.isEmpty()) {
            return;
        }

        String language = getLanguage();
        String cacheKey = language + ":" + key;
        if (RECORDED_KEYS.contains(cacheKey)) {
            return;
        }

        String modid = detectModId(key);
        saveKey(modid, language, key);
        saveKey("all", language, key); // 额外保存到 all 文件夹
        RECORDED_KEYS.add(cacheKey);
    }

    private static String detectModId(String key) {
        if (MOD_IDS == null) {
            initModIds();
        }

        String[] parts = key.split("\\.");
        
        // 1. 优先检查每一段是否完全匹配某个 Mod ID
        for (String part : parts) {
            if (MOD_IDS.contains(part)) {
                return part;
            }
        }

        // 2. 如果都没匹配上，尝试取第一段作为保底
        if (parts.length > 0) {
            return parts[0];
        }

        return "minecraft";
    }

    private static synchronized void initModIds() {
        if (MOD_IDS != null) return;
        MOD_IDS = new HashSet<>();
        try {
            ModList.get().getMods().forEach(mod -> MOD_IDS.add(mod.getModId()));
        } catch (Throwable t) {
            // 在极早期加载时可能无法获取 ModList，此时回退到基础集合
            MOD_IDS.add("minecraft");
            MOD_IDS.add("neoforge");
            MOD_IDS.add("missingi18n");
        }
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
