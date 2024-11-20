package cc.reconnected.library;

import cc.reconnected.library.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RccLib implements ModInitializer {
    public static final String MOD_ID = "rcclib";
    public static RccLibConfig CONFIG;
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        try {
            loadConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadConfig() throws IOException {
        CONFIG = ConfigManager.load(RccLibConfig.class);
    }
}
