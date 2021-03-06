package craftZ.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;

import craftZ.ConfigManager;
import craftZ.CraftZ;


public class ThunderChangeListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWeatherChange(ThunderChangeEvent event) {
		
		if (CraftZ.isWorld(event.getWorld())) {
			if (!ConfigManager.getConfig("config").getBoolean("Config.world.weather.allow-weather-changing"))
				event.setCancelled(true);
		}
		
	}
	
}
