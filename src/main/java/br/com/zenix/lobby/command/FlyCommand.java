package br.com.zenix.lobby.command;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.zenix.lobby.custom.BukkitCommand;
import br.com.zenix.lobby.gamer.Gamer;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of
 * this file, via any medium is strictly prohibited proprietary and confidential
 */

public class FlyCommand extends BukkitCommand {

	public FlyCommand() {
		super("fly", "Entre no modo de voo.");
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (!isPlayer(commandSender)) {
			sendExecutorMessage(commandSender);
			return false;
		}

		if (!checkPermission(commandSender, "fly")) {
			sendPermissionMessage(commandSender);
			return true;
		}
		

		Player g = (Player)commandSender;
		Gamer gamer = getManager().getGamerManager().getGamer(g);
		
		g.getPlayer().setAllowFlight(!g.getPlayer().getAllowFlight());
		gamer.setFly(g.getPlayer().getAllowFlight());
		
		g.sendMessage("§b§lFLY §fVocê " + (g.getPlayer().getAllowFlight() ? "§a§lHABILITOU" : "§c§lDESABILITOU") + "§f o §b§lFLY.");
		return true;
	}
}

