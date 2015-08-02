package network.ethereal.eMessage;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import io.puharesource.mc.titlemanager.api.ActionbarTitleObject; // Import from TitleManager

public class eMessageCommands implements CommandExecutor {
	
	// Initialize plugin variable.
	private JavaPlugin plugin = null;
	
	 // Variable used to store messages.
	private List<String> messages;
	
	// Variable to get time delay between messages.
	private int timeDelay = 0;
	
	// Variable to store number of messages.
	private int msgAmount = 0;
	
	 // Variable used to change between messages.
	private int msg = 0;
	
	// Variable to start and cancel the repeating task for advertisements.
	private int ads = 0;
	
	// Constructor sets the plugin variable to eMessage and gets the messages from the configuration file.
	public eMessageCommands(JavaPlugin p){
		plugin = p;
		timeDelay = plugin.getConfig().getInt("TimeDelayBetweenMessages");
		messages = plugin.getConfig().getStringList("Messages");
		msgAmount = messages.size();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] extras) {
		if (label.equalsIgnoreCase("emsg")){
			if(extras.length <= 0){
				// /emsg creates a recurring advertisement. The text of the advertisements are found in the configuration file.
				 ads = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		            @Override
		            public void run() {
		            	// Restarts the messages.
		            	if(msg >= msgAmount)
		            		msg = 0;
		            	
		            	// Plays messages with TitleManager plugin.
		            	if(eMessage.hasTM)
		            		new ActionbarTitleObject(ChatColor.GREEN + messages.get(msg)).broadcast();
		            	// Plays messages with /say <message>
		            	else
		            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + messages.get(msg));
		            	
		            	msg++;
			            		
			        }
			     }, 0L, (20 * timeDelay)); // Number of seconds before next message appears.
				 return true;
			}
			// Reload command /emsg reload
			else if(extras.length == 1 && extras[0].equalsIgnoreCase("reload")) {
				try{
					plugin.reloadConfig();
					timeDelay = plugin.getConfig().getInt("TimeDelayBetweenMessages");
					messages = plugin.getConfig().getStringList("Messages");
					msgAmount = messages.size();
					Bukkit.getScheduler().cancelTask(ads);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
					sender.sendMessage(ChatColor.GREEN + "eMessage's configuration has been reloaded!");					
				}catch(Exception e){sender.sendMessage(ChatColor.RED + "Error: " + e); return false;} // Fails to reload.
				return true;
			}
			// If anything besides "/emsg" OR "/emsg reload" is entered.
			else if(extras.length > 1 || !extras[0].equalsIgnoreCase("reload"))
				sender.sendMessage(ChatColor.RED + "Invalid arguments!");
		}
		return false;
	}
}
