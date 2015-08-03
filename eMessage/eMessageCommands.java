package network.ethereal.eMessage;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
				
				// If player just typed /emsg
				if(sender instanceof Player){
					sender.sendMessage(ChatColor.GOLD + "/emsg time <seconds> | Sets time delay between ads.");
					sender.sendMessage(ChatColor.GOLD + "/emsg add <message> | Adds the advertisement.");
					sender.sendMessage(ChatColor.GOLD + "/emsg remove <message> | Removes the advertisement.");
					sender.sendMessage(ChatColor.GOLD + "/emsg reload | Reloads the plugin.");
					return true;
				}
				
				// /emsg creates a recurring advertisement. The text of the advertisements are found in the configuration file.
				 ads = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		            @Override
		            public void run() {
		            	// Restarts the messages.
		            	if(msg >= msgAmount)
		            		msg = 0;
		            	
		            	// Plays messages with TitleManager plugin.
		            	if(eCheck.hasTM)
		            		new ActionbarTitleObject(ChatColor.GREEN + messages.get(msg)).broadcast();
		            	// Plays messages with /say <message>
		            	else
		            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say " + messages.get(msg));
		            	
		            	msg++;
			            		
			        }
			     }, 0L, (20 * timeDelay)); // Number of seconds before next message appears.
				 return true;
			}
			else if(extras.length >= 1 && extras[0].equalsIgnoreCase("time")){
				
				// See if a number was entered.
				try{Integer.parseInt(extras[1]);}
				// An number wasn't entered.
				catch(Exception e){sender.sendMessage(ChatColor.RED + "/emsg time <seconds>"); return false;}
				
				// Change and restart ads with new time delay.
				if(Integer.parseInt(extras[1]) >= 0){
					plugin.getConfig().set("TimeDelayBetweenMessages", Integer.parseInt(extras[1]));
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "Time between ads was changed to " + Integer.parseInt(extras[1]) + " seconds.");
					plugin.reloadConfig();
					timeDelay = plugin.getConfig().getInt("TimeDelayBetweenMessages");
					Bukkit.getScheduler().cancelTask(ads);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
					return true;
				}
				
				// Number entered was less than 0.
				else if(Integer.parseInt(extras[1]) < 0)
					sender.sendMessage(ChatColor.RED + "Enter a number greater than -1!");
				else
					sender.sendMessage(ChatColor.RED + "/emsg time <seconds>");
				return false;
			}
			// Add ads in-game.
			else if(extras.length >= 1 && extras[0].equalsIgnoreCase("add")){
				
				if(extras.length == 1 || extras[1].toString().equals("")){
					sender.sendMessage(ChatColor.RED + "/emsg add <messsage>");
					return false;
				}
				
				String newMessage = "";
				for(int i=1;i<extras.length;i++)
					newMessage += (extras[i].toString() + " ");
				newMessage = newMessage.substring(0, newMessage.length() - 1);
				messages.add(newMessage);
				
				plugin.getConfig().set("Messages", messages);
				plugin.saveConfig();
				sender.sendMessage(ChatColor.GREEN + "\"" + newMessage + "\" was added!");
				plugin.reloadConfig();
				messages = plugin.getConfig().getStringList("Messages");
				msgAmount = messages.size();
				Bukkit.getScheduler().cancelTask(ads);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
				return true;
			}
			// Remove ads in-game.
			else if(extras.length >= 1 && extras[0].equalsIgnoreCase("remove")){
				
				if(extras.length == 1){
					sender.sendMessage(ChatColor.RED + "/emsg remove <messsage>");
					return false;
				}
				
				String removeMessage = "";
				for(int i=1;i<extras.length;i++)
					removeMessage += (extras[i].toString() + " ");
				removeMessage = removeMessage.substring(0, removeMessage.length() - 1);
				
				if(messages.contains(removeMessage)){
					messages.remove(removeMessage);
					plugin.getConfig().set("Messages", messages);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "\"" + removeMessage + "\" was removed!");
					plugin.reloadConfig();
					messages = plugin.getConfig().getStringList("Messages");
					msgAmount = messages.size();
					Bukkit.getScheduler().cancelTask(ads);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
					return true;
				}
				else{
					sender.sendMessage(ChatColor.RED + "\"" + removeMessage + "\" was not found!");
					return true;
				}
			}
			// Reload command /emsg reload
			else if(extras.length == 1 && extras[0].equalsIgnoreCase("reload")) {
				try{
					plugin.getServer().getPluginManager().disablePlugin(plugin);
                    plugin.getServer().getPluginManager().enablePlugin(plugin);
					sender.sendMessage(ChatColor.GREEN + "[eMessage] has been reloaded!");					
				}catch(Exception e){sender.sendMessage(ChatColor.RED + "Error: " + e); return false;} // Fails to reload.
				return true;
			}
			// If anything besides the above commands are entered.
			else
				sender.sendMessage(ChatColor.RED + "Invalid arguments!");
		}
		return false;
	}
}
