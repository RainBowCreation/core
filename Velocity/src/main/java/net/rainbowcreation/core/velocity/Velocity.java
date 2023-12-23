package net.rainbowcreation.core.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.rainbowcreation.core.api.utils.Str;
import net.rainbowcreation.core.velocity.event.PlayerEvent;
import net.rainbowcreation.core.velocity.utils.Reference;

import javax.inject.Inject;
import java.util.logging.Logger;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class Velocity {
    private final ProxyServer server;
    private final Logger logger;

    private static Velocity instance;
    public static Velocity getInstance(){
        return instance;
    }
    public Logger getLogger() {
        return logger;
    }

    @Inject
    public Velocity(ProxyServer server, Logger logger) {
        instance = this;
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) {
        Str.header(Reference.NAME+":"+Reference.VERSION, logger);
        server.getEventManager().register(this, new PlayerEvent());
    }

}
