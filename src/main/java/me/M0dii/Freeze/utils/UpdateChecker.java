package me.m0dii.freeze.utils;

import me.m0dii.freeze.FreezePlugin;
import org.bukkit.Bukkit;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker
{
    private final FreezePlugin plugin;
    private final int resourceId;
    
    public UpdateChecker(FreezePlugin plugin, int resourceId)
    {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }
    
    public void getVersion(final Consumer<String> consumer)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () ->
        {
            try(InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
            
                Scanner scanner = new Scanner(inputStream))
            {
                if(scanner.hasNext())
                {
                    consumer.accept(scanner.next());
                }
            }
            catch(IOException ex)
            {
                plugin.getLogger().warning("Failed to check for updates: " + ex.getMessage());
            }
        });
    }
}