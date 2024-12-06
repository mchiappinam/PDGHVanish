package me.mchiappinam.pdghvanish;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.devpaulo.legendchat.api.events.PrivateMessageEvent;


public class Main extends JavaPlugin implements Listener, CommandExecutor {

	List<String> desativado = new ArrayList<String>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginCommand("v").setExecutor(this);
		getServer().getPluginCommand("von").setExecutor(this);
		getServer().getPluginCommand("voff").setExecutor(this);
		getServer().getConsoleSender().sendMessage("§3[PDGHVanish] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHVanish] §2Acesse: http://pdgh.com.br/");
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHVanish] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHVanish] §2Acesse: http://pdgh.com.br/");
	}
		
	@EventHandler
	private void onJoin(PlayerJoinEvent e) {
		check(e.getPlayer());
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		final Player p = (Player) cs;
		if(cmd.getName().equalsIgnoreCase("v")) {
			if(!p.hasPermission("pdgh.moderador")) {
				p.sendMessage("§cSem permissões");
				return true;
			}
			if(args.length > 0) {
				p.sendMessage("§cUse §l/v");
				return true;
			}
			if (!desativado.contains(p.getName().toLowerCase())) {
				desativado.add(p.getName().toLowerCase());
				aparecerExSTAFF(p);
      	    	p.sendMessage("§f[ⓅⒹⒼⒽ]");
      	    	p.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §c[DESATIVADO] Você está §lvisível");
      	    	p.sendMessage("§f[ⓅⒹⒼⒽ]");
      	    	return true;
			}
			desativado.remove(p.getName().toLowerCase());
			desaparecerExSTAFF(p);
            p.sendMessage("§f[ⓅⒹⒼⒽ]");
      	    p.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a[ATIVADO] Você está §linvisível");
      	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
      	    return true;
		}
		if(cmd.getName().equalsIgnoreCase("von")) {
			if(!p.hasPermission("pdgh.moderador")) {
				p.sendMessage("§cSem permissões");
				return true;
			}
			if(args.length > 0) {
				p.sendMessage("§cUse §l/v");
				return true;
			}
			if(desativado.contains(p.getName().toLowerCase()))
				desativado.remove(p.getName().toLowerCase());
			desaparecerExSTAFF(p);
            /**p.sendMessage("§f[ⓅⒹⒼⒽ]");
      	    p.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a[ATIVADO] Você está §linvisível");
      	    p.sendMessage("§f[ⓅⒹⒼⒽ]");*/
      	    return true;
		}
		if(cmd.getName().equalsIgnoreCase("voff")) {
			if(!p.hasPermission("pdgh.moderador")) {
				p.sendMessage("§cSem permissões");
				return true;
			}
			if(args.length > 0) {
				p.sendMessage("§cUse §l/v");
				return true;
			}
			if(!desativado.contains(p.getName().toLowerCase()))
				desativado.add(p.getName().toLowerCase());
			aparecerExSTAFF(p);
      	    /**p.sendMessage("§f[ⓅⒹⒼⒽ]");
      	    p.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §c[DESATIVADO] Você está §lvisível");
      	    p.sendMessage("§f[ⓅⒹⒼⒽ]");*/
      	    return true;
		}
		return false;
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	private void onPCmd(PlayerCommandPreprocessEvent e) {
	    if(e.getMessage().toLowerCase().startsWith(("/tell "))) {
	    	String[] args = e.getMessage().split(" ");
	    	if(getServer().getPlayer(args[1]) != null) {
		        Player targetplayer = getServer().getPlayer(args[1]);
		        if((targetplayer.hasPermission("pdgh.moderador"))&&(!desativado.contains(targetplayer.getName().toLowerCase()))) {
		        	e.setCancelled(true);
		        	e.getPlayer().sendMessage("§cJogador nao encontrado.");
		        	e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
		        	targetplayer.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §e"+e.getPlayer().getName()+" §ctentou te enviar uma mensagem privada com o assunto: §e"+e.getMessage());
		        }
	        }
	    }
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	private void onChat(PrivateMessageEvent e) {
		if((e.getReceiver().hasPermission("pdgh.moderador"))&&(!desativado.contains(e.getReceiver().getName().toLowerCase()))) {
			e.setCancelled(true);
			e.getSender().sendMessage("§cJogador nao encontrado.");
			e.getReceiver().sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §e"+e.getSender().getName()+" §cestá com o tell travado (travou quando você estava visível) ou está te respondendo (r/reply) e tentou te enviar uma mensagem privada com o assunto: §e"+e.getMessage());
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	private void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player)
			if(e.getDamager() instanceof Player||e.getDamager() instanceof Projectile) {
				if(((Player)e.getEntity()).hasPermission("pdgh.moderador"))
					if(!desativado.contains(((Player)e.getEntity()).getName().toLowerCase())) {
						e.setCancelled(true);
						//((Player)e.getEntity()).sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a"+((Player)e.getDamager()).getName()+" tentou te bater com a sua invisibilidade ativada.");
				}
			}
	}
	
	/**@EventHandler(priority = EventPriority.LOWEST)
	private void onDamageP(PotionSplashEvent e) {
		for(Entity ent2 : e.getAffectedEntities())
			if(ent2 instanceof Player)
				if(e.getPotion().getShooter() instanceof Player)
					if(((Player)ent2.getPassenger()).hasPermission("pdgh.moderador"))
						if(!desativado.contains(((Player)ent2.getPassenger()).getName().toLowerCase())) {
							e.setCancelled(true);
							((Player)ent2.getPassenger()).sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a"+((Player)e.getEntity().getShooter()).getName()+" tentou atirar em você com a sua invisibilidade ativada.");
						}
	}*/

	public void check(final Player p) {
		for (Player todos : getServer().getOnlinePlayers()) {
			if((todos.hasPermission("pdgh.moderador"))&&(!desativado.contains(todos.getName().toLowerCase()))) {
				desaparecerExSTAFF(todos);
			}
		}
		
		if(p.hasPermission("pdgh.moderador")) {
			if(desativado.contains(p.getName().toLowerCase())) {
				
				for (Player todos : getServer().getOnlinePlayers()) {
					if(todos.hasPermission("pdgh.moderador"))
						todos.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a[DESATIVADO] "+p.getName()+" está visível.");
				}
				
				getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					public void run() {
						p.sendMessage("§f[ⓅⒹⒼⒽ]");
		  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
		  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §cVocê se logou §lvisível§c. Para ativar, digite §f§l/v");
		  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
		  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
						
						for (Player todos : getServer().getOnlinePlayers()) {
							if(todos.hasPermission("pdgh.moderador"))
								todos.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §c[DESATIVADO] "+p.getName()+" está visível.");
						}
						
		    		}
				}, 10*20L);
				return;
			}
			desaparecerExSTAFF(p);
			
			for (Player todos : getServer().getOnlinePlayers()) {
				if(todos.hasPermission("pdgh.moderador"))
					todos.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a[ATIVADO] "+p.getName()+" está invisível.");
			}
			
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					p.sendMessage("§f[ⓅⒹⒼⒽ]");
	  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
	  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §aVocê se logou §linvisível§a. Para desativar, digite §f§l/v");
	  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
	  	  	  	    p.sendMessage("§f[ⓅⒹⒼⒽ]");
					
					for (Player todos : getServer().getOnlinePlayers()) {
						if(todos.hasPermission("pdgh.moderador"))
							todos.sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §a[ATIVADO] "+p.getName()+" está invisível.");
					}
					
	    		}
			}, 10*20L);
			return;
		}
	}

	public void desaparecerExSTAFF(Player p) {
		for (Player todos : getServer().getOnlinePlayers()) {
			if(!todos.hasPermission("pdgh.moderador"))
				todos.hidePlayer(p);
		}
	}

	public void aparecerExSTAFF(Player p) {
		for (Player todos : getServer().getOnlinePlayers()) {
			if(!todos.hasPermission("pdgh.moderador"))
				todos.showPlayer(p);
		}
	}
    
    @EventHandler
    private void onPick(PlayerPickupItemEvent e) {
    	if((e.getPlayer().hasPermission("pdgh.moderador"))&&(!desativado.contains(e.getPlayer().getName().toLowerCase()))) {
	    	e.setCancelled(true);
	    	e.getPlayer().sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §cVocê está invisível e não pode pegar itens.");
    	}
    }
    
    @EventHandler
    private void onDrop(PlayerDropItemEvent e) {
    	if((e.getPlayer().hasPermission("pdgh.moderador"))&&(!desativado.contains(e.getPlayer().getName().toLowerCase()))) {
	    	e.setCancelled(true);
	    	e.getPlayer().sendMessage("§f[ⓅⒹⒼⒽ] §6"+ChatColor.BOLD+"⋐⋑ §cVocê está invisível e não pode dropar itens.");
    	}
    }
}