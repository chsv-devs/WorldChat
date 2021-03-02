package org.chocoserver.worldchat;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;

import java.util.HashSet;

public class WorldChat extends PluginBase implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent ev){
        Player player = ev.getPlayer();
        Level level = player.getLevel();

        if(player.hasPermission("worldchat.bypass")) return;

        HashSet<CommandSender> recipients = new HashSet<>();
        for (CommandSender recipient : ev.getRecipients()) {
            if(recipient instanceof Player && ((Player) recipient).getLevel().equals(level)){
                recipients.add(recipient);
            }else if(recipient.hasPermission("worldchat.bypass")){
                recipients.add(recipient);
            }
        }
        recipients.add(getServer().getConsoleSender());

        ev.setRecipients(recipients);
    }
}
