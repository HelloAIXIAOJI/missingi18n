package missing.i18n.api;

import net.neoforged.bus.api.Event;
/**
 * Fired when a missing translation key is detected.
 * This event is fired on the NeoForge.EVENT_BUS.
 */
public class MissingTranslationEvent extends Event {
    private final String key;
    private final String language;
    private final String modId;

    public MissingTranslationEvent(String key, String language, String modId) {
        this.key = key;
        this.language = language;
        this.modId = modId;
    }

    public String getKey() {
        return key;
    }

    public String getLanguage() {
        return language;
    }

    public String getModId() {
        return modId;
    }
}
