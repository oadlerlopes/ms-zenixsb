package br.com.zenix.lobby.custom;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.zenix.lobby.Lobby;
import br.com.zenix.lobby.manager.Manager;
/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of
 * this file, via any medium is strictly prohibited proprietary and confidential
 */

public abstract class BukkitCommand extends Command {

	private Manager manager;
	public boolean enabled = true;
	public final String ERROR = "§c§lERROR §f";
	public final String NO_PERMISSION = "§c§lPERMISSAO §f";
	public final String OFFLINE = "§c§lOFFLINE §f";
	
	public BukkitCommand(String name) {
		super(name);
	}

	public BukkitCommand(String name, String description) {
		super(name, description, "", new ArrayList<String>());
	}

	public BukkitCommand(String name, String description, List<String> aliases) {
		super(name, description, "", aliases);
	}

	public abstract boolean execute(CommandSender commandSender, String label, String[] args);

	public Manager getManager() {
		this.manager = Lobby.getManager();
		return manager;
	}

	public boolean checkPermission(CommandSender sender, String perm) {
		return sender.hasPermission("commons.cmd." + perm);
	}

	public boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	public boolean isNumeric(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	public String getError() {
		return ERROR;
	}

	public String getOffline() {
		return OFFLINE;
	}

	public String getNoPermission() {
		return NO_PERMISSION;
	}

	public void sendNumericMessage(CommandSender commandSender) {
		commandSender.sendMessage(getError() + "Você inseriu uma informação errada. O argumento é um §c§lNUMERAL.");
	}

	public void sendPermissionMessage(CommandSender commandSender) {
		commandSender.sendMessage(getNoPermission() + "Você não tem §c§lPERMISSAO§f para executar esse comando!");
	}

	public void sendExecutorMessage(CommandSender commandSender) {
		commandSender.sendMessage("ERRO: Somente players podem usar esse comando.");
	}
	
	public void sendWarning(String warning) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("pvp.alerts.admin")) {
				player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "[" + warning + "]");
			}
		}
	}
}