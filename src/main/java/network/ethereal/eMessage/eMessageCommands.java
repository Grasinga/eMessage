package network.ethereal.eMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;

public class eMessageCommands implements CommandExecutor {
	
	private JavaPlugin plugin = null;
	private int msg = 1;
	
	public eMessageCommands(JavaPlugin p){plugin = p;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] extras) {
		if (label.equalsIgnoreCase("emsg")){
			if(extras.length <= 0){
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
			     }, 0L, 375L);
				 return true;
			}
			else if(extras.length == 1 && extras[0].equalsIgnoreCase("reload")) {
				try{
				 Bukkit.getServer().getPluginManager().disablePlugin(plugin);
				 Bukkit.getServer().getPluginManager().enablePlugin(plugin);
				 sender.sendMessage(ChatColor.GREEN + "eMessage has been reloaded!");
				}catch(Exception e){sender.sendMessage("Error: " + e); return false;}
				return true;
			}
			else if(extras.length > 1 || !extras[0].equalsIgnoreCase("reload"))
				sender.sendMessage(ChatColor.RED + "Incorrect arguments!");
		}
		return false;
	}
}
