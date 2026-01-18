package missing.i18n.mixin;

import net.minecraft.client.resources.language.ClientLanguage;
import missing.i18n.I18nRecorder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ClientLanguage.class)
public class ClientLanguageMixin {
    @Shadow @Final private Map<String, String> storage;

    @Inject(method = "getOrDefault(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", at = @At("HEAD"))
    private void onGetOrDefault(String key, String defaultValue, CallbackInfoReturnable<String> cir) {
        if (this.storage != null && !this.storage.containsKey(key)) {
            I18nRecorder.record(key);
        }
    }
}
