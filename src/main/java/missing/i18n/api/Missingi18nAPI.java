package missing.i18n.api;

import missing.i18n.I18nRecorder;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Public API for Missingi18n mod.
 */
public class Missingi18nAPI {
    
    /**
     * Records a missing translation key.
     * 
     * @param key The translation key that is missing.
     */
    public static void recordMissingKey(String key) {
        I18nRecorder.record(key);
    }

    /**
     * Checks if a translation key has already been recorded for a specific language.
     * 
     * @param key The translation key.
     * @param language The language code (e.g., "en_us").
     * @return true if already recorded.
     */
    public static boolean isRecorded(String key, String language) {
        return I18nRecorder.isRecorded(key, language);
    }

    /**
     * Internal method to fire the MissingTranslationEvent.
     * Should not be called by other mods unless they are implementing their own recording logic.
     */
    public static void fireEvent(String key, String language, String modId) {
        NeoForge.EVENT_BUS.post(new MissingTranslationEvent(key, language, modId));
    }
}
