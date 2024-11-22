package cc.reconnected.library;

import cc.reconnected.library.config.ConfigManager;
import cc.reconnected.library.event.ReadyEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RccLibrary implements ModInitializer {
    public static final String MOD_ID = "rcc-library";
    public static RccLibConfig CONFIG;
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private LuckPerms luckPerms;

    public LuckPerms luckPerms() {
        return luckPerms;
    }

    private static RccLibrary instance;
    public static RccLibrary getInstance() {
        return instance;
    }

    public RccLibrary() {
        instance = this;
    }

    @Override
    public void onInitialize() {
        try {
            loadConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            luckPerms = LuckPermsProvider.get();
            ReadyEvent.EVENT.invoker().onReady(server, this);
        });
    }

    public static void loadConfig() throws IOException {
        CONFIG = ConfigManager.load(RccLibConfig.class);
    }
}
