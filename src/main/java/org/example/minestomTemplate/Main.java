package org.example.minestomTemplate;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;

public class Main {

	public static InstanceManager instanceManager;
	public static InstanceContainer instanceContainer;

	public static void main(String[] args) {
		MinecraftServer server = MinecraftServer.init();

		instanceManager = MinecraftServer.getInstanceManager();
		instanceContainer = instanceManager.createInstanceContainer();

		instanceContainer.setChunkSupplier(LightingChunk::new);

		GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
		globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
			event.setSpawningInstance(instanceContainer);
		});

		server.start("0.0.0.0", 25565);
	}
}