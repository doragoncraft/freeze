package me.carl230690.freeze;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
  implements Listener
{
  public Permission canPerformCommand = new Permission("player.freeze");
  public PluginDescriptionFile PDFFile;
@SuppressWarnings({ "unchecked", "rawtypes" })
ArrayList<String> frozen = new ArrayList();
  @SuppressWarnings({ "unchecked", "rawtypes" })
ArrayList<String> mute = new ArrayList();

  public void onEnable()
  {
    this.PDFFile = getDescription();

    Logger.getLogger("Minecraft").info(
      this.PDFFile.getName() + " v" + this.PDFFile.getVersion() + 
      " has been enabled!");

    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(this, this);
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    if (p != null){
    if (this. frozen.contains(p.getName()))
    {
      e.setTo(e.getFrom());
    }
    }
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();
    if (p != null) {
    if (this.frozen.contains(p.getName()))
    {
      e.setCancelled(true);
    }
    }
  }
  
  @EventHandler
  public void onPreCommand(PlayerCommandPreprocessEvent event)
  {
  	Player player = event.getPlayer();
      String message = event.getMessage();
      if (this.mute.contains(player.getName())) {
      if (message.contains("/me"))
      {
          event.setCancelled(true);
          player.sendMessage("Use of this command while muted has been blocked!");
          return;
      }}}

  @EventHandler
  public void onPlayerInteractEntity(PlayerInteractEntityEvent e)
  {
    Player p = e.getPlayer();
    if (p != null) {
    if (this.frozen.contains(p.getName()))
    {
      e.setCancelled(true);
    }
    }
  }
  
  @EventHandler
  public void OnPlayerDamage(EntityDamageEvent event) {
	  Entity e = event.getEntity();
	  if(e instanceof Player){
		  Player p = (Player)e;
		  if (this.frozen.contains(p.getName())){
		  event.setCancelled(true);
		  }
	  }
	  
  }
  
  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent e)
  {
    Player player = e.getPlayer();
    if (this.mute.contains(player.getName()))
    {
      e.setCancelled(true);
      player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + 
        "Reminder" + ChatColor.WHITE + "] " + ChatColor.RED + 
        "You're muted!");
    }
  }


  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    int timer;
	if (commandLabel.equalsIgnoreCase("freeze"))
    {
      if (sender.hasPermission("player.freeze"))
      {
        if ((sender instanceof Player))
        {
          final Player player = (Player)sender;
          if (args.length == 0)
          {
            player.sendMessage(ChatColor.RED + "Error: " + 
              ChatColor.DARK_RED + "Player not specified!");
            return true;
          }
          if (args.length == 1)
          {
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null)
            {
              player.sendMessage(ChatColor.RED + "Error: " + 
                ChatColor.DARK_RED + 
                "Could not find player " + "'" + args[0] + 
                ";" + "!");
              return true;
            }
            if (this.frozen.contains(target.getName()))
            {
              this.frozen.remove(target.getName());
              
              Bukkit.broadcast(ChatColor.DARK_GRAY + "Player " + 
                ChatColor.RED + sender.getName() + 
                ChatColor.DARK_GRAY + " has unfrozen " + 
                ChatColor.RED + target.getName(), "player.staff");
              
              player.sendMessage(ChatColor.DARK_GRAY + 
                "You have unfrozen player: " + 
                ChatColor.RED + target.getName());
              target.sendMessage(ChatColor.DARK_GRAY + 
                "You have been unfrozen by player: " + 
                ChatColor.RED + sender.getName());
              return true;
            }

            this.frozen.add(target.getName());
            
            Bukkit.broadcast(ChatColor.DARK_GRAY + "Player " + 
              ChatColor.RED + sender.getName() + 
              ChatColor.DARK_GRAY + " has frozen " + 
              ChatColor.RED + target.getName(), "player.staff");
            
            player.sendMessage(ChatColor.DARK_GRAY + 
              "You have frozen player: " + 
              ChatColor.RED + target.getName());
            target.sendMessage(ChatColor.DARK_GRAY + 
              "You have been frozen by player: " + 
              ChatColor.RED + sender.getName());
            return true;
          }

          if (args.length == 2)
          {
            final Player target = Bukkit.getServer().getPlayer(
              args[0]);

            if (target == null)
            {
              player.sendMessage(ChatColor.RED + "Error: " + 
                ChatColor.DARK_RED + 
                "Could not find player " + "'" + args[0] + 
                ";" + "!");
              return true;
            }
            if (this.frozen.contains(target.getName()))
            {
              this.frozen.remove(target.getName());
              
              Bukkit.broadcast(ChatColor.DARK_GRAY + "Player " + 
                ChatColor.RED + sender.getName() + 
                ChatColor.DARK_GRAY + " has unfrozen " + 
                ChatColor.RED + target.getName(), "player.staff");
              
              player.sendMessage(ChatColor.DARK_GRAY + 
                "You have unfrozen player: " + 
                ChatColor.RED + target.getName());
              target.sendMessage(ChatColor.DARK_GRAY + 
                "You have been unfrozen by player: " + 
                ChatColor.RED + sender.getName());
              return true;
            }

            try
            {
              timer = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e)
            {
              player.sendMessage(ChatColor.RED + 
                "That's not a valid number!");
              return false;
            }
            this.frozen.add(target.getName());
            
            Bukkit.broadcast(ChatColor.DARK_GRAY + "Player " + 
              ChatColor.RED + sender.getName() + 
              ChatColor.DARK_GRAY + " has frozen " + 
              ChatColor.RED + target.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_GRAY + args[1] + ChatColor.DARK_GRAY + " minutes", "player.staff");
            
            player.sendMessage(ChatColor.DARK_GRAY + 
              "You have frozen player: " + 
              ChatColor.RED + target.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_GRAY + args[1] + ChatColor.DARK_GRAY + " minutes");
            target.sendMessage(ChatColor.DARK_GRAY + 
              "You have been frozen by player: " + 
              ChatColor.RED + sender.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_GRAY + args[1] + ChatColor.DARK_GRAY + " minutes");

            Bukkit.getScheduler().runTaskLater(this, 
              new Runnable()
            {
              public void run()
              {
                main.this.frozen.remove(target.getName());
                player.sendMessage(ChatColor.RED + target.getName() + ChatColor.DARK_GRAY + " has been unfrozen.");
                target.sendMessage(ChatColor.DARK_GRAY + "You have been unfrozen.");
              }
            }
            , timer * 1200);
            return true;
          }

          if (args.length > 2)
          {
            player.sendMessage(ChatColor.RED + 
              "Too many arguments.");
          }

        }
        else
        {
          sender.sendMessage(ChatColor.RED + 
            "You cannot send this command through console.");
        }
      }
      else
      {
        sender.sendMessage(ChatColor.RED + 
          "You do not have the permissions to perform this command.");
      }

    }
    else if (commandLabel.equalsIgnoreCase("Mute"))
    {
      if (sender.hasPermission("player.mute"))
      {
        if ((sender instanceof Player))
        {
          final Player player = (Player)sender;
          if (args.length == 0)
          {
            player.sendMessage(ChatColor.RED + "Error: " + 
              ChatColor.DARK_RED + "Player not specified!");
            return true;
          }
          if (args.length == 1)
          {
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null)
            {
              sender.sendMessage(ChatColor.RED + "Error: " + 
                ChatColor.DARK_RED + "Could not find player " + 
                "'" + args[0] + ";" + "!");
              return true;
            }
            if (this.mute.contains(target.getName()))
            {
              this.mute.remove(target.getName());
              
              Bukkit.broadcast(ChatColor.DARK_GRAY + 
                "Player " + ChatColor.RED + sender.getName() + 
                ChatColor.DARK_GRAY + " has unmuted " + 
                ChatColor.RED + target.getName(), "player.staff");
              
              sender.sendMessage(ChatColor.DARK_GRAY + 
                "You have unmuted player: " + ChatColor.RED + 
                target.getName());
              target.sendMessage(ChatColor.DARK_GRAY + 
                "You have been unmuted by player: " + 
                ChatColor.RED + sender.getName());
              return true;
            }

            this.mute.add(target.getName());
            
            Bukkit.broadcast(ChatColor.DARK_GRAY + 
              "Player " + ChatColor.RED + sender.getName() + 
              ChatColor.DARK_GRAY + " has muted " + 
              ChatColor.RED + target.getName(), "player.staff");
            
            sender.sendMessage(ChatColor.DARK_GRAY + 
              "You have muted player: " + ChatColor.RED + 
              target.getName());
            target.sendMessage(ChatColor.DARK_GRAY + 
              "You have been muted by player: " + 
              ChatColor.RED + sender.getName());
            return true;
          }

          if (args.length == 2)
          {
            final Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null)
            {
              sender.sendMessage(ChatColor.RED + "Error: " + 
                ChatColor.DARK_RED + "Could not find player " + 
                "'" + args[0] + ";" + "!");
              return true;
            }
            if (this.mute.contains(target.getName()))
            {
              this.mute.remove(target.getName());
              
              Bukkit.broadcast(ChatColor.DARK_GRAY + "Player " + ChatColor.RED + sender.getName() + ChatColor.DARK_GRAY + " has unmuted " + ChatColor.RED + target.getName(), "player.staff");
              sender.sendMessage(ChatColor.DARK_GRAY + 
                "You have unmuted player: " + ChatColor.RED + 
                target.getName());
              target.sendMessage(ChatColor.DARK_GRAY + 
                "You have been unmuted by player: " + 
                ChatColor.RED + sender.getName());
              return true;
            }

            try
            {
              timer = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e)
            {
              player.sendMessage(ChatColor.RED + 
                "That's not a valid number!");
              return false;
            }
            this.mute.add(target.getName());
            Bukkit.broadcast(ChatColor.WHITE + "[" + 
              ChatColor.GOLD + "Broadcast" + 
              ChatColor.WHITE + "] " + ChatColor.DARK_GRAY + 
              "Player " + ChatColor.RED + sender.getName() + 
              ChatColor.DARK_GRAY + " has muted " + 
              ChatColor.RED + target.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_GRAY + args[1] + ChatColor.DARK_GRAY + " minutes", "player.staff");
            sender.sendMessage(ChatColor.DARK_GRAY + 
              "You have muted player: " + ChatColor.RED + 
              target.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_GRAY + args[1] + ChatColor.DARK_GRAY + " minutes");
            target.sendMessage(ChatColor.DARK_GRAY + 
              "You have been muted by player: " + 
              ChatColor.RED + sender.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_GRAY + args[1] + ChatColor.DARK_GRAY + " minutes");

            Bukkit.getScheduler().runTaskLater(this, 
              new Runnable()
            {
              public void run()
              {
                main.this.mute.remove(target.getName());
                player.sendMessage(ChatColor.RED + target.getName() + ChatColor.DARK_GRAY + " has been unmuted.");
                target.sendMessage(ChatColor.DARK_GRAY + "You have been unmuted.");
              }
            }
            , timer * 1200);
            return true;
          }

          if (args.length > 2)
          {
            player.sendMessage(ChatColor.RED + "Too many arguments!");
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + 
            "This command cannot be used through console!");
        }
      }
      else
      {
        sender.sendMessage(ChatColor.RED + 
          "You do not have the permissions to perform this command.");
      }
    }
    return false;
  }
}

/*@EventHandler
public void onPreCommand(PlayerCommandPreprocessEvent event)
{
	Player player = event.getPlayer();
    String message = event.getMessage();
    if (message.contains("/me"))
    {
        event.setCancelled(true);
        player.sendMessage("Use of this command while muted has been blocked!.");
        return;
    }}             */
	