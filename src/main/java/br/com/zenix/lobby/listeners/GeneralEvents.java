package br.com.zenix.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.zenix.core.bukkit.commands.base.MessagesType;
import br.com.zenix.core.bukkit.player.bossbar.nw.BarUtil;
import br.com.zenix.core.bukkit.player.events.ServerTimeEvent;
import br.com.zenix.core.bukkit.player.hologram.Hologram;
import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.core.master.data.handler.type.DataType;
import br.com.zenix.lobby.custom.BukkitListener;
import br.com.zenix.lobby.gamer.Gamer;
import br.com.zenix.lobby.gamer.item.CacheItems;
import br.com.zenix.lobby.utilitaries.StringUtils;

public class GeneralEvents extends BukkitListener {

	public boolean spawned = false;
	int number;

	@EventHandler
	private void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);

		Player player = event.getPlayer();
		Gamer gamer = getManager().getGamerManager().getGamer(player);

		Location location = (getManager().isSkyWarsLobby() ? new Location(Bukkit.getWorld("world"), 1012, 102, 1007) : Bukkit.getWorld("world").getSpawnLocation());

		if (!getManager().isSkyWarsLobby()) {
			location.setYaw(270.0f);
		} else {
			location.setYaw(180.0f);
		}

		player.teleport(location);

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(20.0D);
		player.setFoodLevel(20);
		player.setAllowFlight(false);
		player.setGameMode(GameMode.ADVENTURE);
		player.setLevel(gamer.getAccount().getDataHandler().getValue(DataType.GLOBAL_XP).getValue());

		if (gamer.getAccount().getRank() == null) {
			player.kickPlayer("");
		}

		if (!getManager().isSkyWarsLobby()) {
			CacheItems.JOIN.build(player);
		} else {
			CacheItems.JOINSW.build(player);
		}

		if (!getManager().isSkyWarsLobby()) {
			if (gamer.isShow()) {
				new ItemBuilder(Material.INK_SACK).setDurability(8).setName("§fJogadores [ON]").setDescription("§eStatus> §aHabilitado").build(player, 7);
			} else {
				new ItemBuilder(Material.INK_SACK).setDurability(10).setName("§fJogadores [OFF]").setDescription("§eStatus> §cDesabilitado").build(player, 7);
			}
		}

		if (player.hasPermission("commons.cmd.moderate")) {
			player.chat("/admin");
		}

		BarUtil.setBar(player, "§c§lNOVO! §f§lSKY WARS SOLO > ALPHA! MINIGAMES.ZENIX.CC", 100);

		MessagesType.sendTitleMessage(player, "§6§lZenix", "§aServidores de Minecraft §7: §ewww.zenix.cc");

		StringUtils.sendCenteredMessage(player, "");
		StringUtils.sendCenteredMessage(player, "§6§lZenix");
		StringUtils.sendCenteredMessage(player, "");
		StringUtils.sendCenteredMessage(player, "§6§lwww.zenix.cc");
		StringUtils.sendCenteredMessage(player, "");

		getManager().getVanish().updateVanished();

		for (Player players : Bukkit.getOnlinePlayers()) {
			Gamer gamers = getManager().getGamerManager().getGamer(players);
			if (gamers.isShow()) {
				players.hidePlayer(player);
			}
		}
		
		//1008, 103.5, 989
		
		Hologram hl = new Hologram("§6§lSEUS STATUS", new Location(Bukkit.getWorld("world"), 1008, 103.5, 989), false);
		hl.addLine("");
		hl.addLine("§fSeu ELO: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_ELO).getValue());
		hl.addLine("§fPartidas ganhas: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_WINS).getValue());
		hl.addLine("");
		hl.addLine("§fPartidas perdidas: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_DEATHS).getValue());
		hl.addLine("§fJogadores abatidos: §7" + gamer.getAccount().getDataHandler().getValue(DataType.SKYWARS_KILLS).getValue());
		hl.addLine("");
		
		hl.show(player);
	}

	@EventHandler
	public void onServerAction(ServerTimeEvent event) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (getManager().getAdminManager().isAdmin(players)) {
				MessagesType.sendActionBarMessage(players, "§fVocê está no modo §cVANISH");
			}
		}

		if (getManager().isSkyWarsLobby()) {
			Bukkit.getWorld("world").setTime(12500L);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		getManager().getAdminManager().setPlayerQuit(event.getPlayer());
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

}
