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
	            		msg = 1;
	            	}
		            		
		            }
		        }, 0L, 375L);
			 return true;
		}
		return false;
	}
}
