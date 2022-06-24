package br.com.zenix.lobby;

import br.com.zenix.core.bukkit.Core;
import br.com.zenix.lobby.manager.Manager;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of
 * this file, via any medium is strictly prohibited proprietary and confidential
 */

public class Lobby extends Core {

	private static Manager manager;

	public void onEnable() {
		super.onEnable();
		
		manager = new Manager(this);
		if (!isCorrectlyStarted())
			return;
	}

	public void onDisable() {
		super.onDisable();
	}

	public void onLoad() {
		super.onLoad();
	}

	public static Manager getManager() {
		return manager;
	}

}
