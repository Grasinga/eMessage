package network.ethereal.eMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import io.puharesource.mc.titlemanager.api.ActionbarTitleObject; // Import from TitleManager

public class eMessageCommands implements CommandExecutor {
	
	// Initialize plugin variable
	private JavaPlugin plugin = null;
	
	 // Variable used to change between messages
	private int msg = 1;
	
	// Constructor sets the plugin variable to eMessage
	public eMessageCommands(JavaPlugin p){plugin = p;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] extras) {
		if (label.equalsIgnoreCase("emsg")){
			if(extras.length <= 0){
				// /emsg creates a recurring advertisement. The text of the advertisement is based on the variable msg.
				 Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		            @Override
		            public void run() {
		            	if(msg == 1)
		            	{
		            		new ActionbarTitleObject(ChatColor.GREEN + "Visit us at: www.ethereal.network").broadcast();
		            		msg = 2;
		            	}
		            	else if(msg == 2)
		            	{
		            		new ActionbarTitleObject(ChatColor.GREEN + "TeamSpeak IP: ethereal.network").broadcast();
		            		msg = 3;
		            	}
		            	else if(msg == 3)
		            	{
		            		new ActionbarTitleObject(ChatColor.GREEN + "Splatoon - ethereal.network:4444").broadcast();
		            		msg = 1;
		            	}
			            		
			        }
			     }, 0L, 375L); // 375 = Number of in-game ticks before next message appears.
				 return true;
			}
			// Reload command /emsg reload
			else if(extras.length == 1 && extras[0].equalsIgnoreCase("reload")) {
				try{
				 Bukkit.getServer().getPluginManager().disablePlugin(plugin);
				 Bukkit.getServer().getPluginManager().enablePlugin(plugin);
				 sender.sendMessage(ChatColor.GREEN + "eMessage has been reloaded!");
				}catch(Exception e){sender.sendMessage("Error: " + e); return false;} // Fails to reload.
				return true;
			}
			// If anything besides "/emsg" OR "/emsg reload" is entered.
			else if(extras.length > 1 || !extras[0].equalsIgnoreCase("reload"))
				sender.sendMessage(ChatColor.RED + "Incorrect arguments!");
		}
		return false;
	}
}
