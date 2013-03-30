package me.JangoBrick.CraftZ.Listeners;

import java.util.Random;

import me.JangoBrick.CraftZ.CraftZ;
import me.JangoBrick.CraftZ.PlayerManager;
import net.minecraft.server.v1_5_R2.EntityZombie;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener implements Listener {
	
	public PlayerDeathListener(CraftZ plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		String value_world_name = plugin.getConfig().getString("Config.world.name");
		World eventWorld = event.getEntity().getWorld();
		if (eventWorld.getName().equalsIgnoreCase(value_world_name)) {
			
			Player eventPlayer = event.getEntity();
			String eventPlayerName = eventPlayer.getName();
			Location eventPlayerLoc = eventPlayer.getLocation();
			
			boolean value_modifyDeathMessages = plugin.getConfig().getBoolean("Config.chat.modify-death-messages");
			if (value_modifyDeathMessages) {
				
				event.setDeathMessage(eventPlayerName + " was killed.");
				
			}
			
			if (eventPlayer.getKiller() != null) {
				
				PlayerManager.getData(eventPlayer.getKiller().getName()).playersKilled++;
				eventPlayer.getKiller().sendMessage(ChatColor.GOLD + plugin.getLangConfig()
						.getString("Messages.killed.player").replaceAll("%p", eventPlayerName)
						.replaceAll("%k", "" + PlayerManager
								.getData(eventPlayer.getKiller().getName()).playersKilled));
				
			}
			
			event.setDroppedExp(0);
			event.setKeepLevel(false);
			//event.getDrops().clear();
			
			//for (ItemStack item : eventPlayer.getInventory().getContents()) {
			//	if (item != null) {
			//		eventPlayerLoc.getWorld().dropItem(eventPlayerLoc, item);
			//	}
			//}
			
			eventPlayer.getInventory().clear();
			eventPlayer.getEquipment().setArmorContents(new ItemStack[] {
					new ItemStack(Material.AIR), new ItemStack(Material.AIR),
					new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
			
			EntityZombie spawnedZombie = ((CraftZombie) eventPlayerLoc.getWorld()
					.spawnEntity(eventPlayerLoc, EntityType.ZOMBIE)).getHandle();
			
			spawnedZombie.setVillager(true);
			
			if (new Random().nextInt(7) > 0) {
				((CraftZombie) spawnedZombie.getBukkitEntity()).addPotionEffect(
						new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,
								(new Random().nextInt(3) + 1)), true);
				((CraftZombie) spawnedZombie.getBukkitEntity()).addPotionEffect(
						new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE,
								1, true));
			} else {
				((CraftZombie) spawnedZombie.getBukkitEntity()).addPotionEffect(
						new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE,
								1, true));
				((CraftZombie) spawnedZombie.getBukkitEntity()).addPotionEffect(
						new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE,
								1, true));
			}
			
			String kickMsg = "[CraftZ] " + plugin.getLangConfig().getString("Messages.died");
			kickMsg = kickMsg.replaceAll("%z", "" + PlayerManager.getData(eventPlayerName).zombiesKilled);
			kickMsg = kickMsg.replaceAll("%p", "" + PlayerManager.getData(eventPlayerName).playersKilled);
			kickMsg = kickMsg.replaceAll("%m", "" + PlayerManager.getData(eventPlayerName).minutesSurvived);
			
			eventPlayer.kickPlayer(kickMsg);
			
			PlayerManager.resetPlayer(eventPlayer);
			
		}
		
	}
	
	
	
	
	private CraftZ plugin;
	
}
