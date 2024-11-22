package cc.reconnected.library.config;

import cc.reconnected.library.RccLibrary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            .create();

    public static Path getPath(String id) {
        return FabricLoader.getInstance().getConfigDir().resolve(id + ".json");

    }

    public static <T> T load(Class<T> clazz) throws IOException {
        if(!clazz.isAnnotationPresent(Config.class)) {
            throw new IllegalArgumentException("Config is not annotated with @Config");
        }

        var id = clazz.getAnnotation(Config.class).value();

        var path = getPath(id);
        if (!path.toFile().exists()) {
            T config;
            try {
                config = clazz.getConstructor((Class<?>[]) null).newInstance();
            } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            save(config);
            return config;
        }
        var bf = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8));
        var config = gson.fromJson(bf, clazz);
        bf.close();
        save(config);
        return config;
    }

    public static <T> void save(T config) {
        var id = config.getClass().getAnnotation(Config.class).value();
        var path = getPath(id);
        var json = gson.toJson(config);
        try (var fw = new FileWriter(path.toFile(), StandardCharsets.UTF_8)) {
            fw.write(json);
        } catch (Exception e) {
            RccLibrary.LOGGER.error("Error saving {} config file.", id, e);
        }
    }
}
