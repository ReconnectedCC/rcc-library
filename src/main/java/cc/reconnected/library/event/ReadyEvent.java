package cc.reconnected.library.event;

import cc.reconnected.library.RccLibrary;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

public interface ReadyEvent {
    Event<ReadyEvent> EVENT = EventFactory.createArrayBacked(ReadyEvent.class,
            (listeners) -> (server, instance) -> {
                for (var listener : listeners) {
                    listener.onReady(server, instance);
                }
            });

    void onReady(MinecraftServer server, RccLibrary instance);
}
