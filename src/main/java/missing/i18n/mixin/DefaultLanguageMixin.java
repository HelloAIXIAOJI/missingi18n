package missing.i18n.mixin;

import net.minecraft.locale.Language;
import missing.i18n.I18nRecorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.locale.Language$1")
public class DefaultLanguageMixin {
    @Inject(method = "getOrDefault(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", at = @At("HEAD"))
    private void onGetOrDefault(String key, String defaultValue, CallbackInfoReturnable<String> cir) {
        Language instance = (Language)(Object)this;
        if (!instance.has(key)) {
            I18nRecorder.record(key);
        }
    }
}
