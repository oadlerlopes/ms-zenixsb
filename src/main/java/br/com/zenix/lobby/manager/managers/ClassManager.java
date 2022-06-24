package br.com.zenix.lobby.manager.managers;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.event.Listener;

import br.com.zenix.core.master.utilitaries.loader.Getter;
import br.com.zenix.lobby.custom.BukkitCommand;
import br.com.zenix.lobby.custom.BukkitListener;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of
 * this file, via any medium is strictly prohibited proprietary and confidential
 */

public class ClassManager extends Management {

	public ClassManager(Manager manager) {
		super(manager, "ClassManager");
	}

	public boolean initialize() {
		return load();
	}

	public boolean load() {
		getLogger().log("Starting trying to load all the classes of commands and listeners of the plugin.");

		for (Class<?> classes : Getter.getClassesForPackage(getManager().getPlugin(), "br.com.zenix.lobby")) {
			try {
				if (BukkitCommand.class.isAssignableFrom(classes) && !classes.getSimpleName().equals("BukkitCommand")) {
					BukkitCommand command = (BukkitCommand) classes.newInstance();
					((CraftServer) Bukkit.getServer()).getCommandMap().register(command.getName(), command);
					getManager().getLogger().debug("The commmand " + classes.getSimpleName() + " has loaded.");
				}
			} catch (Exception exception) {
				getManager().getLogger().error("Error in load command - " + classes.getSimpleName() + "!",
						exception);
				return false;
			}

			try {
				Listener listener = null;
				
				if (classes.getSimpleName().equals("")) {
					continue;
				} else if (Listener.class.isAssignableFrom(classes) && !BukkitListener.class.isAssignableFrom(classes)) {
					listener = (Listener) classes.getConstructor(Manager.class).newInstance(getManager());
				}
				if (Listener.class.isAssignableFrom(classes) && BukkitListener.class.isAssignableFrom(classes)) {
					listener = (Listener) classes.getConstructor().newInstance();
				}
				if (listener == null)
					continue;
				Bukkit.getPluginManager().registerEvents(listener, getManager().getPlugin());
				getManager().getLogger().debug("The listener " + listener.getClass().getSimpleName() + " has loaded!");
			} catch (Exception exception) {
				getManager().getLogger().error("Error in load listener - " + classes.getSimpleName() + "!",
						exception);
				return false;
			}
		}
		return true;
	}

}
