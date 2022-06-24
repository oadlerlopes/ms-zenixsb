package br.com.zenix.lobby.manager;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.zenix.core.bukkit.Core;
import br.com.zenix.core.bukkit.manager.CoreManager;
import br.com.zenix.core.bukkit.server.type.ServerType;
import br.com.zenix.core.master.data.management.DataManager;
import br.com.zenix.core.master.logger.FormattedLogger;
import br.com.zenix.core.master.utilitaries.Utils;
import br.com.zenix.lobby.Lobby;
import br.com.zenix.lobby.crate.CrateManager;
import br.com.zenix.lobby.gamer.GamerManager;
import br.com.zenix.lobby.hologram.HologramManager;
import br.com.zenix.lobby.inventories.InventoryMaker;
import br.com.zenix.lobby.inventories.InventoryManager;
import br.com.zenix.lobby.manager.managers.AdminManager;
import br.com.zenix.lobby.manager.managers.ClassManager;
import br.com.zenix.lobby.manager.managers.RestartManager;
import br.com.zenix.lobby.manager.managers.Vanish;
import br.com.zenix.lobby.scoreboard.ScoreboardManager;
import br.com.zenix.lobby.server.ServerManager;
import br.com.zenix.lobby.utilitaries.BodyManager;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */

public class Manager {

	private final CoreManager coreManager;

	private Lobby plugin;
	private Utils utils;

	private ServerManager serverManager;
	private GamerManager gamerManager;
	private ScoreboardManager scoreboardManager;
	private AdminManager adminManager;
	private InventoryManager inventoryManager;
	private RestartManager restartManager;
	private HologramManager hologramManager;
	private CrateManager crateManager;
	private BodyManager bodyManager;

	private Vanish vanish;

	private ClassManager classManager;

	private Random random;

	private boolean skywarsLobby;

	public Manager(Core core) {
		this.coreManager = Core.getCoreManager();

		plugin = Lobby.getPlugin(Lobby.class);

		getLogger().log("Starting the plugin " + plugin.getName() + " version " + plugin.getDescription().getVersion() + "...");
		getLogger().log("Making connection with plugin " + coreManager.getPlugin().getName() + " version " + coreManager.getPlugin().getDescription().getVersion() + ".");

		skywarsLobby = getPlugin().getConfig().getBoolean("skywars_lobby");
		utils = coreManager.getUtils();

		random = new Random();

		gamerManager = new GamerManager(this);
		if (!gamerManager.correctlyStart()) {
			return;
		}

		scoreboardManager = new ScoreboardManager(this);
		if (!scoreboardManager.correctlyStart()) {
			return;
		}

		classManager = new ClassManager(this);
		if (!classManager.correctlyStart()) {
			return;
		}

		serverManager = new ServerManager(this);
		if (!serverManager.correctlyStart()) {
			return;
		}

		adminManager = new AdminManager(this);
		if (!adminManager.correctlyStart()) {
			return;
		}

		inventoryManager = new InventoryManager(this);
		if (!inventoryManager.correctlyStart()) {
			return;
		}

		restartManager = new RestartManager(this);
		if (!restartManager.correctlyStart()) {
			return;
		}

		crateManager = new CrateManager(this);
		if (!crateManager.correctlyStart()) {
			return;
		}

		hologramManager = new HologramManager(this);
		if (!hologramManager.correctlyStart()) {
			return;
		}

		bodyManager = new BodyManager(this);
		if (!bodyManager.correctlyStart()) {
			return;
		}

		vanish = new Vanish(this);

		new BukkitRunnable() {
			public void run() {
				hologramManager.setHolograms();
			}
		}.runTaskTimer(getPlugin(), 40 * 60 * 10, 40 * 60 * 10);

		// new BukkitRunnable() {
		// public void run() {
		// crateManager.setHolograms();
		// }
		// }.runTaskTimer(getPlugin(), 0L, 120L);

		for (World world : Bukkit.getWorlds()) {
			world.setThundering(false);
			world.setStorm(false);
			world.setWeatherDuration(1000000);
			world.setTime(0L);
		}

		for (int x = -400; x < 400; x++) {
			for (int z = -400; z < 400; z++) {
				Chunk chunk = Bukkit.getWorld("world").getBlockAt(x, 130, z).getChunk();
				chunk.load(true);
			}
		}

		new InventoryMaker().startUpdate(this);

		getLogger().log("The plugin " + plugin.getName() + " version " + plugin.getDescription().getVersion() + " was started correcly.");
	}

	public boolean isSkyWarsLobby() {
		return skywarsLobby;
	}

	public void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, getPlugin());
	}

	public FormattedLogger getLogger() {
		return getCoreManager().getLogger();
	}

	public Vanish getVanish() {
		return vanish;
	}

	public ServerType getServerType() {
		return coreManager.getServerType();
	}

	public FileConfiguration getConfig() {
		return plugin.getConfig();
	}

	public Lobby getPlugin() {
		return plugin;
	}

	public BodyManager getBodyManager() {
		return bodyManager;
	}

	public Utils getUtils() {
		return utils;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

	public AdminManager getAdminManager() {
		return adminManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public GamerManager getGamerManager() {
		return gamerManager;
	}

	public ScoreboardManager getScoreListener() {
		return scoreboardManager;
	}

	public CoreManager getCoreManager() {
		return coreManager;
	}

	public CrateManager getCrateManager() {
		return crateManager;
	}

	public DataManager getMySQLManager() {
		return coreManager.getDataManager();
	}

	public Random getRandom() {
		return random;
	}

	public ClassManager getClassLoader() {
		return classManager;
	}
}
