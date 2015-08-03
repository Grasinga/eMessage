package network.ethereal.eMessage;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class eCheck {

	eMessage plugin = null;
	
	// Variable to keep track of whether to use TitleManager or to use /say.
	public static boolean hasTM = false;
	
	// Variable to know when to first run /emsg
	private boolean firstRun = true;
	
	public eCheck(eMessage p){plugin = p; Checking();}
	
	private void Checking(){	
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	    scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
	    	public void run()
			{
				if(plugin.getServer().getPluginManager().getPlugin("TitleManager") != null 
						&& plugin.getServer().getPluginManager().getPlugin("TitleManager").isEnabled())
					hasTM = true;
				else
					hasTM = false;

				// (Only runs when Checking() is first called.)
				if(firstRun){
				    if(hasTM)
						System.out.println("Successfully hooked into TitleManager!");
				    else
						System.out.println("Failed to hook into TitleManager, using /say instead!");
				    
				    // Sends /emsg command to console to start the advertising.
				    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "emsg");
				    
				    firstRun = false;
				}
					
			}
		}, 0, 20*(plugin.getConfig().getInt("TimeDelayBetweenMessages") - 2)); // Checks just before a message is to be played
    }// End of Checking()
	
}
