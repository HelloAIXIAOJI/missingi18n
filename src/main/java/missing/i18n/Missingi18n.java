package missing.i18n;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Missingi18n.MODID)
public class Missingi18n {
    public static final String MODID = "missingi18n";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Missingi18n(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Missingi18n initialized");
    }
}
