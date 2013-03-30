package me.JangoBrick.CraftZ.Listeners;

import me.JangoBrick.CraftZ.CraftZ;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class CreatureSpawnListener implements Listener {
	
	public CreatureSpawnListener(CraftZ plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		
		String value_world_name = plugin.getConfig().getString("Config.world.name");
		World eventWorld = event.getEntity().getWorld();
		if (eventWorld.getName().equalsIgnoreCase(value_world_name)) {
			
			EntityType eventCreatureType = event.getEntityType();
			SpawnReason spawnReason = event.getSpawnReason();
			
			if (eventCreatureType == EntityType.SKELETON) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.SPIDER) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.CREEPER) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.GHAST) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.SILVERFISH) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.SLIME) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.ENDERMAN) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.SQUID) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.PIG_ZOMBIE) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.MAGMA_CUBE) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.CAVE_SPIDER) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.BLAZE) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.OCELOT) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.BAT) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.WITCH) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.VILLAGER) {
				event.setCancelled(true);
			}
			if (eventCreatureType == EntityType.WITHER) {
				event.setCancelled(true);
			}
			
			
			
			Boolean value_animalspawns_allow = plugin.getConfig().getBoolean("Config.mobs.animals.spawning.enable");
			if (!value_animalspawns_allow) {
				event.setCancelled(true);
			}
			
			
			
			if (eventCreatureType == EntityType.ZOMBIE) {
				
				if (spawnReason != SpawnReason.CUSTOM && spawnReason != SpawnReason.SPAWNER_EGG) {
					event.setCancelled(true);
				}
				
			}
		
		}
		
	}
	
	
	
	
	private CraftZ plugin;
	
}
