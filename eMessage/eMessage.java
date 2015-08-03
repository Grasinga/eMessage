package network.ethereal.eMessage;

import org.bukkit.plugin.java.JavaPlugin;

// Main class
public class eMessage extends JavaPlugin
{
	
	public void onEnable() {
		
		getLogger().info("Enabled!");
		
		// Checks to see if the server has the TitleManager plugin.
		new eCheck(this);
		
		// Configuration file
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		// Allows the use of the /emsg command and passes in eMessage
		getCommand("emsg").setExecutor(new eMessageCommands(this));
	}   
	
	public void onDisable() {
		getLogger().info("Disabled!");
	}
	
}