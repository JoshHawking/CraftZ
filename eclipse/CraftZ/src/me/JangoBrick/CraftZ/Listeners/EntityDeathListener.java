package me.JangoBrick.CraftZ.Listeners;

import java.util.List;

import me.JangoBrick.CraftZ.CraftZ;
import me.JangoBrick.CraftZ.PlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeathListener implements Listener {
	
	public EntityDeathListener(CraftZ plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		
		String value_world_name = plugin.getConfig().getString("Config.world.name");
		World eventWorld = event.getEntity().getWorld();
		if (eventWorld.getName().equalsIgnoreCase(value_world_name)) {
		
			LivingEntity eventEntity = event.getEntity();
			EntityType eventEntityType = eventEntity.getType();
			List<ItemStack> drops = event.getDrops();
			
			event.setDroppedExp(0);
			
			if (eventEntityType == EntityType.ZOMBIE) {
				
				if (eventEntity.getKiller() != null) {
					
					PlayerManager.getData(event.getEntity().getKiller().getName()).zombiesKilled++;
					eventEntity.getKiller().sendMessage(ChatColor.GOLD + plugin.getLangConfig()
							.getString("Messages.killed.zombie").replaceAll("%k", "" + PlayerManager
									.getData(eventEntity.getKiller().getName()).zombiesKilled));
					
				}
				
				drops.clear();
				
				boolean value_zombies_drops_enable = plugin.getConfig().getBoolean("Config.mobs.zombies.enable-drops");
				if (value_zombies_drops_enable) {
					
					ItemStack rottenflesh;
					ItemStack arrows;
					
					boolean value_zombies_drops_rf = plugin.getConfig().getBoolean("Config.mobs.zombies.drops.rottenflesh");
					if (value_zombies_drops_rf) {
						rottenflesh = new ItemStack(Material.ROTTEN_FLESH, 1);
					} else {
						rottenflesh = new ItemStack(Material.ROTTEN_FLESH, 0);
					}
					
					boolean value_zombies_drops_arrows = plugin.getConfig().getBoolean("Config.mobs.zombies.drops.arrows");
					if (value_zombies_drops_arrows) {
						arrows = new ItemStack(Material.ARROW, 2);
					} else {
						arrows = new ItemStack(Material.ARROW, 0);
					}
					
					
					double value_zombies_drops_chance = 1 - plugin.getConfig().getDouble("Config.mobs.zombies.drops.chance");
					if (Math.random() >= value_zombies_drops_chance) {
						drops.add(rottenflesh);
					}
					if (Math.random() >= value_zombies_drops_chance) {
						drops.add(arrows);
					}
					
				}
				
			}
			
			if (eventEntityType == EntityType.COW) {
				
			}
			
			if (eventEntityType == EntityType.CHICKEN) {
				
			}
			
			if (eventEntityType == EntityType.PIG) {
				
			}
			
			if (eventEntityType == EntityType.SHEEP) {
				
			}
		
		}
		
	}
	
	
	
	private CraftZ plugin;
	
}
