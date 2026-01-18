package missing.i18n;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Missingi18n.MODID, dist = Dist.CLIENT)
public class Missingi18nClient {

    public Missingi18nClient(ModContainer container, IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(this::onRegisterClientCommands);
    }

    private void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("missingi18n")
                .then(Commands.literal("test")
                        .executes(context -> {
                            // 在主线程执行打开 UI 的操作
                            Minecraft.getInstance().execute(() -> {
                                Minecraft.getInstance().setScreen(new TestScreen());
                            });
                            return 1;
                        })
                )
        );
    }
}
