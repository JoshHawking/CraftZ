package craftZ.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import craftZ.CraftZ;
import craftZ.PlayerManager;
import craftZ.util.BlockChecker;

public class PlayerInteractListener implements Listener {
	
	public PlayerInteractListener(CraftZ plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	
	
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		String value_world_name = plugin.getConfig().getString("Config.world.name");
		World eventWorld = event.getPlayer().getWorld();
		if (eventWorld.getName().equalsIgnoreCase(value_world_name)) {
			
			Player eventPlayer = event.getPlayer();
			ItemStack eventItem = event.getItem();
			Material eventItemType;
			if (eventItem != null) {
				eventItemType = eventItem.getType();
			} else {
				eventItemType = Material.AIR;
			}
			Action eventAction = event.getAction();
			Block eventBlock = event.getClickedBlock();
			
			if (eventAction == Action.RIGHT_CLICK_AIR || eventAction == Action.RIGHT_CLICK_BLOCK) {
				
				if (eventItemType == Material.SUGAR) {
					
					boolean value_enableSugarEffect = plugin.getConfig().getBoolean("Config.players.medical.enable-sugar-speed-effect");
					if (value_enableSugarEffect == true) {
						
						if (eventPlayer.getItemInHand().getAmount() < 2) {
							ItemStack airItemStack = new ItemStack(Material.AIR, 0);
							eventPlayer.setItemInHand(airItemStack);
						} else {
							eventPlayer.getItemInHand().setAmount(eventPlayer.getItemInHand().getAmount() - 1);
						}
						
						PotionEffect sugarSpeedEffect = new PotionEffect(PotionEffectType.SPEED, 3600, 2);
						eventPlayer.addPotionEffect(sugarSpeedEffect);
						eventPlayer.playSound(eventPlayer.getLocation(), Sound.BURP, 1, 1);
						
					}
					
				}
				
				
				
				if (eventItemType == Material.PAPER) {
					
					if (plugin.getConfig().getBoolean("Config.players.medical.bleeding.heal-with-paper")) {
						
						eventPlayer.playSound(eventPlayer.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
						
						if (eventPlayer.getItemInHand().getAmount() < 2) {
							ItemStack airItemStack = new ItemStack(Material.AIR, 0);
							eventPlayer.setItemInHand(airItemStack);
						} else {
							eventPlayer.getItemInHand().setAmount(eventPlayer.getItemInHand().getAmount() - 1);
						}
						
						PlayerManager.getData(eventPlayer.getName()).bleeding = false;
						
						eventPlayer.sendMessage(ChatColor.DARK_RED + plugin.getLangConfig()
								.getString("Messages.bandaged"));
						
					}
					
				}
				
				
				
				if (eventItemType == Material.INK_SACK && eventItem.getDurability() == 1) {
					
					if (plugin.getConfig().getBoolean("Config.players.medical.healing.heal-with-rosered")
							&& !plugin.getConfig().getBoolean("Config.players.medical.healing.only-healing-others")) {
						
						eventPlayer.playSound(eventPlayer.getLocation(), Sound.BREATH, 1, 1);
						
						if (eventPlayer.getItemInHand().getAmount() < 2) {
							eventPlayer.setItemInHand(new ItemStack(Material.AIR, 0));
						} else {
							eventPlayer.getItemInHand().setAmount(eventPlayer.getItemInHand().getAmount() - 1);
						}
						
						eventPlayer.setHealth(20D);
						
						eventPlayer.sendMessage(ChatColor.DARK_RED + plugin.getLangConfig()
								.getString("Messages.bloodbag"));
						
					}
					
				}
				
				
				
				if (eventItemType == Material.INK_SACK && eventItem.getDurability() == 10) {
					
					if (plugin.getConfig().getBoolean("Config.players.medical.poisoning.cure-with-limegreen")) {
						
						eventPlayer.playSound(eventPlayer.getLocation(), Sound.ZOMBIE_UNFECT, 1, 1);
						
						if (eventPlayer.getItemInHand().getAmount() < 2) {
							eventPlayer.setItemInHand(new ItemStack(Material.AIR, 0));
						} else {
							eventPlayer.getItemInHand().setAmount(eventPlayer.getItemInHand().getAmount() - 1);
						}
						
						PlayerManager.getData(eventPlayer.getName()).poisoned = false;
						
						eventPlayer.sendMessage(ChatColor.DARK_RED + plugin.getLangConfig()
								.getString("Messages.unpoisoned"));
						
					}
					
				}
				
			}
			
			
			
			if (eventAction == Action.RIGHT_CLICK_BLOCK) {
				
				if (eventItemType == Material.IRON_AXE) {
					
					boolean isTreeBlock = BlockChecker.isTree(eventBlock);
					if (isTreeBlock == true) {
						
						Inventory evtPlrInv = eventPlayer.getInventory();
						if (!evtPlrInv.contains(Material.LOG)) {
							
							ItemStack logFromTree = new ItemStack(Material.LOG, 1);
							Item item = eventWorld.dropItem(eventPlayer.getLocation(), logFromTree);
							item.setPickupDelay(0);
							String msg_harvestedTree = plugin.getLangConfig().getString("Messages.harvested-tree");
							eventPlayer.sendMessage(msg_harvestedTree);
							
						} else {
							String msg_alreadyHaveWood = plugin.getLangConfig().getString("Messages.already-have-wood");
							eventPlayer.sendMessage(msg_alreadyHaveWood);
						}
						
					} else {
						String msg_isntTree = plugin.getLangConfig().getString("Messages.isnt-a-tree");
						eventPlayer.sendMessage(msg_isntTree);
					}
					
				}
				
				
				
				if (eventItemType == Material.MINECART) {
					
					boolean value_enableCars = plugin.getConfig().getBoolean("Config.vehicles.enable");
					if (value_enableCars) {
						
						Location locForMinecart = eventBlock.getLocation();
						locForMinecart.add(new Vector(0, 1, 0));
						eventWorld.spawn(locForMinecart, Minecart.class);
						
						if (eventPlayer.getGameMode() != GameMode.CREATIVE) {
							eventPlayer.getInventory().removeItem(new ItemStack[] {
									event.getPlayer().getInventory().getItemInHand() });
						}
					
					}
					
				}
				
				
				
				if (eventBlock.getType() == Material.FIRE) {
					
					String msg_alreadyHaveWood = plugin.getLangConfig().getString("Messages.already-have-wood");
					eventPlayer.sendMessage(msg_alreadyHaveWood);
					
				}
				
			}
		
		}
		
	}
	
	
	
	private CraftZ plugin;
	
}