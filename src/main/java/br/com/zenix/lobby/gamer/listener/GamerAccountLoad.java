package br.com.zenix.lobby.gamer.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

import br.com.zenix.lobby.custom.BukkitListener;
import br.com.zenix.lobby.gamer.Gamer;

public class GamerAccountLoad extends BukkitListener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void login(PlayerLoginEvent event) {
		if (event.getResult() != org.bukkit.event.player.PlayerLoginEvent.Result.ALLOWED)
			return;

		Player player = event.getPlayer();
		Gamer gamer = new Gamer(getManager().getCoreManager().getAccountManager().getAccount(player));
		getManager().getGamerManager().addGamer(gamer);
		getManager().getGamerManager().getLogger().log(
				"The player with uuid " + player.getUniqueId() + "(" + player.getName() + ") was loaded correctly.");
	}

}
