package missing.i18n.example;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import missing.i18n.api.Missingi18nAPI;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

/**
 * Missingi18n 联动示例模组。
 */
@Mod(Missingi18nExample.MODID)
public class Missingi18nExample {
    public static final String MODID = "missingi18n_example";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Missingi18nExample(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new Missingi18nEventListener());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Missingi18n 示例联动模组已启动");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("missingi18nexample")
                .then(Commands.literal("record")
                    .then(Commands.argument("key", StringArgumentType.string())
                        .executes(context -> {
                            String key = StringArgumentType.getString(context, "key");
                            Missingi18nAPI.recordMissingKey(key);
                            context.getSource().sendSuccess(() -> Component.translatable("command.missingi18n_example.record.success", key), true);
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("get")
                    .then(Commands.argument("key", StringArgumentType.string())
                        .executes(context -> {
                            String key = StringArgumentType.getString(context, "key");
                            // 这里简单模拟获取当前语言，实际可能需要更复杂的逻辑
                            boolean recorded = Missingi18nAPI.isRecorded(key, "zh_cn");
                            context.getSource().sendSuccess(() -> Component.translatable("command.missingi18n_example.get.result", key, recorded), true);
                            return 1;
                        })
                    )
                )
        );
    }
}
