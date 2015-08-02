package network.ethereal.eMessage;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

// Main class
public class eMessage extends JavaPlugin
{
	// Variable to keep track of whether to use TitleManager or to use /say.
	public static boolean hasTM = false;
	
	public void onEnable() {
		
		getLogger().info("Enabled!");
		
		// Checks to see if the server has the TitleManager plugin.
		checkForTM();
		
		// Configuration file
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		// Allows the use of the /emsg command and passes in eMessage
		getCommand("emsg").setExecutor(new eMessageCommands(this));
		
		// Sends /emsg command to console to start the advertising.
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
	}   
	
	public void onDisable() {
		getLogger().info("Disabled!");
	}
	
	private void checkForTM(){
		if (getServer().getPluginManager().getPlugin("TitleManager") != null && getServer().getPluginManager().getPlugin("TitleManager").isEnabled()){
			hasTM = true;
			getLogger().info("Successfully hooked into TitleManager!");
		}
		else {
			hasTM = false;
			getLogger().warning("Failed to hook into TitleManager, using /say instead!");
		}
	}
	
}