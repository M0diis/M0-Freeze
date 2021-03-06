package me.M0dii.Freeze;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreezeListener implements Listener
{
    private final Main plugin;
    
    
    
    public FreezeListener(Main plugin)
    {
        this.plugin = plugin;
    }
    
    private boolean has(Player p)
    {
        return this.plugin.getFrozenPlayers().contains(p.getUniqueId());
    }
    
    @EventHandler
    public void cancelOnMoveEvent(PlayerMoveEvent e)
    {
        if(has(e.getPlayer()))
        {
            e.setTo(e.getFrom());
            
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void cancelOnJumpEvent(PlayerJumpEvent e)
    {
        if(has(e.getPlayer()))
        {
            if(Config.DENY_JUMP)
            {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void cancelCommands(PlayerCommandPreprocessEvent e)
    {
        if(Config.BLOCK_CMDS)
        {
            List<String> blockedCmds = Config.BLOCKED_CMDS;
            
            String[] args = e.getMessage().split(" ");
    
            String cmd = args[0];
    
            for(String blocked : blockedCmds)
            {
                if(cmd.equalsIgnoreCase(blocked))
                {
                    e.setCancelled(true);
                    
                    e.getPlayer().sendMessage(Config.ACTION_BLOCKED);
                }
            }
        }
    }
}
