package network.ethereal.eMessage;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

// Main class
public class eMessage extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		this.log.info("eMessage Enabled!");
		
		// Allows the use of the /emsg command and passes in eMessage
		getCommand("emsg").setExecutor(new eMessageCommands(this));
		
		// Sends /emsg command to console to start the advertising.
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
	}   
	
	public void onDisable() {
		this.log.info("eMessage Disabled!");
	}
}