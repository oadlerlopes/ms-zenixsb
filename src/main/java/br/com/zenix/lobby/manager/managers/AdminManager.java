package br.com.zenix.lobby.manager.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.zenix.core.bukkit.commands.base.MessagesType;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */
public class AdminManager extends Management {

	private static ArrayList<UUID> admin = new ArrayList<UUID>(), quick = new ArrayList<UUID>();
	public static HashMap<UUID, ItemStack[]> items = new HashMap<UUID, ItemStack[]>();

	public AdminManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		return true;
	}

	public void setAdmin(Player player, boolean admin) {
		if (admin)
			setAdmin(player);
		else
			setPlayer(player);
	}

	public void setAdmin(Player player) {
		if ((!admin.contains(player.getUniqueId())) || (admin.isEmpty())) {
			admin.add(player.getUniqueId());
		}
		player.setGameMode(GameMode.CREATIVE);

		getManager().getVanish().makeVanished(player);
		getManager().getVanish().updateVanished();

		player.sendMessage("§dVoce agora está no modo ADMIN");
		player.sendMessage("§dVoce esta INVISIVEL para " + getInvisible(player) + "");
	}

	public void setPlayer(Player player) {
		admin.remove(player.getUniqueId());

		getManager().getVanish().makeVisible(player);
		getManager().getVanish().updateVanished();

		if (player.isOnline()) {
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("§dVoce agora SAIU do modo ADMIN");
			player.sendMessage("§dVoce esta VISIVEL para TODOS OS PLAYERS");
		}
		
		MessagesType.sendActionBarMessage(player, "§fVocê não está mais no modo §aVANISH");

	}

	public void setPlayerQuit(Player player) {
		admin.remove(player.getUniqueId());

		for (Player players : Bukkit.getOnlinePlayers()) {
			players.showPlayer(player);
		}

		if (player.isOnline()) {
			player.getInventory().clear();

			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("§dVoce agora SAIU do modo ADMIN");
			player.sendMessage("§dVoce esta VISIVEL para TODOS OS PLAYERS");
		}

	}

	public AdminLevel getPlayerLevel(Player p) {
		return (AdminLevel) Vanish.vanished.get(p.getUniqueId());
	}

	public boolean isAdmin(Player player) {
		return admin.contains(player.getUniqueId());
	}

	public boolean isVanished(Player p) {
		return (Vanish.vanished.containsKey(p.getUniqueId()));
	}

	public boolean isQuick(Player player) {
		return quick.contains(player.getUniqueId());
	}

	public static enum AdminLevel {
		PLAYER, MOD, MODPLUS, ADMIN, DONO;
	}

	private String getInvisible(Player player) {
		if (player.hasPermission("commons.tag.dono")) {
			return "DIRETORES e ABAIXO";
		}
		if (player.hasPermission("commons.tag.diretor")) {
			return "ADMINS e ABAIXO";
		}
		if (player.hasPermission("commons.tag.admin")) {
			return "MODPLUS e ABAIXO";
		}
		if (player.hasPermission("commons.tag.modplus")) {
			return "MOD e ABAIXO";
		}
		if (player.hasPermission("commons.tag.youtuber")) {
			return "TRIAL e ABAIXO";
		}
		if (player.hasPermission("commons.tag.trial")) {
			return "TRIAL e ABAIXO";
		}
		return "JOGADORES";
	}
}
