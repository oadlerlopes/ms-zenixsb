package br.com.zenix.lobby.custom;

import org.bukkit.event.Listener;

import br.com.zenix.lobby.Lobby;
import br.com.zenix.lobby.manager.Manager;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of
 * this file, via any medium is strictly prohibited proprietary and confidential
 */

public class BukkitListener implements Listener {

	public Manager getManager() {
		return Lobby.getManager();
	}

}
