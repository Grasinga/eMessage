package network.ethereal.eMessage;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class eMessage extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	  
	public void onEnable() {
		this.log.info("eMessage Enabled!");
		
		getCommand("emsg").setExecutor(new eMessageCommands(this));
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
	}   
	
	public void onDisable() {
		this.log.info("eMessage Disabled!");
	}
}