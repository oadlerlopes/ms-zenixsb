package br.com.zenix.lobby.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import br.com.zenix.core.master.data.jedis.JedisExecutor;
import br.com.zenix.core.proxy.server.FullyServerStatus;
import br.com.zenix.core.proxy.server.ServerStatus;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */

public class ServerManager extends Management {

	private static final Map<String, FullyServerStatus> servers = new HashMap<>();

	public ServerManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		try {
			Bukkit.getServer().getScheduler().runTaskAsynchronously(getManager().getPlugin(), () -> {
				initServers();
			});
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void initServers() {
		new Thread(new JedisExecutor(getManager().getCoreManager().getDataManager().getJedisHandler().getSecondaryJedis(), JedisExecutor.SERVER_INFO_DATA_CHANNEL) {
			public void onMessage(String channel, String message) {
				Bukkit.getServer().getScheduler().runTaskAsynchronously(getManager().getPlugin(), () -> {
					FullyServerStatus status = FullyServerStatus.decode(message);
					servers.put(status.getName(), status);
				});
			}
		}).run();
	}

	public Map<String, FullyServerStatus> getServers() {
		return servers;
	}

	public ArrayList<FullyServerStatus> getConnectedServers(String name) {
		ArrayList<FullyServerStatus> connectedServers = new ArrayList<>();

		for (FullyServerStatus server : servers.values()) {
			if (server.getName().toLowerCase().contains(name.toLowerCase())) {
				if (getStatus(server) == ServerStatus.FULL) {
					connectedServers.add(server);
				}
				if (getStatus(server) == ServerStatus.GAME) {
					connectedServers.add(server);
				}
				if (getStatus(server) == ServerStatus.INVENCIBILITY) {
					connectedServers.add(server);
				}
				if (getStatus(server) == ServerStatus.PREGAME) {
					connectedServers.add(server);
				}
				if (getStatus(server) == ServerStatus.ONLINE) {
					connectedServers.add(server);
				}
			}
		}

		connectedServers.sort(Comparator.comparing(FullyServerStatus::getServerStatus).thenComparing(FullyServerStatus::getPlayers).thenComparing(FullyServerStatus::getName));

		return connectedServers;
	}
	

	public ArrayList<FullyServerStatus> getSW(String name) {
		ArrayList<FullyServerStatus> connectedServers = new ArrayList<>();

		for (FullyServerStatus server : servers.values()) {
			if (server.getName().toLowerCase().contains(name.toLowerCase())) {
				if (getStatus(server) == ServerStatus.PREGAME) {
					connectedServers.add(server);
				}
			}
		}

		connectedServers.sort(Comparator.comparing(FullyServerStatus::getServerStatus).thenComparing(FullyServerStatus::getPlayers).thenComparing(FullyServerStatus::getName));

		return connectedServers;
	}

	public ArrayList<FullyServerStatus> getPvPServers() {
		return getConnectedServers("PVP");
	}

	public ArrayList<FullyServerStatus> getLobbyServers() {
		return getConnectedServers("LOBBY");
	}

	public ArrayList<FullyServerStatus> getHungerGamesServers() {
		return getConnectedServers("HG");
	}

	public ArrayList<FullyServerStatus> getSkyWarsServers() {
		return getSW("SW");
	}

	public ArrayList<FullyServerStatus> getGladiatorServer() {
		return getConnectedServers("GLADIATOR");
	}

	public ArrayList<FullyServerStatus> getOITCServer() {
		return getConnectedServers("OITC");
	}

	public ArrayList<FullyServerStatus> getEventServer() {
		return getConnectedServers("EVENTO");
	}

	public FullyServerStatus getServer(String serverName) {
		for (FullyServerStatus server : servers.values()) {
			if (server.getName().contains(serverName))
				return server;
		}
		return null;
	}

	public ServerStatus getStatus(FullyServerStatus server) {
		return server.getServerStatus();
	}

	public int getStageShort(String server) {
		return getStageShort(getServer(server));
	}

	public int getStageShort(FullyServerStatus server) {
		int stage = 1;

		ServerStatus status = server.getServerStatus();

		if (server.getPlayers() >= server.getMaxPlayers()) {
			stage = 11;
		}

		if (status == ServerStatus.ONLINE) {
			stage = 10;
		} else if (status == ServerStatus.PREGAME) {
			stage = 10;
		} else if (status == ServerStatus.INVENCIBILITY) {
			stage = 1;
		} else if (status == ServerStatus.GAME) {
			stage = 1;
		} else if (status == ServerStatus.FULL) {
			stage = 11;
		} else if (status == ServerStatus.STARTING) {
			stage = 10;
		} else if (status == ServerStatus.OFFLINE) {
			stage = 1;
		}

		return stage;
	}

	public boolean canJoin(FullyServerStatus server, Player player, String name) {
		boolean canJoin = true;

		if (server.getServerStatus() == ServerStatus.OFFLINE) {
			canJoin = false;
		} else if (server.getServerStatus() == ServerStatus.OFFLINE) {
			canJoin = false;
		}
		return canJoin;
	}

	public ArrayList<FullyServerStatus> getPossibleServers(Player player, String name) {
		ArrayList<FullyServerStatus> servers = new ArrayList<>();

		for (FullyServerStatus server : name.equals("pvp") ? getPvPServers() : name.equals("hg") ? getHungerGamesServers() : name.equals("sw") ? getSkyWarsServers() : getLobbyServers()) {
			if (canJoin(server, player, name)) {
				servers.add(server);
			}
		}

		return servers;
	}

	public void randomServer(Player player, String name) {
		ArrayList<FullyServerStatus> servers = getPossibleServers(player, name.toLowerCase());

		if (servers.size() != 0) {

			FullyServerStatus lastServer = null;
			for (FullyServerStatus server : servers) {
				int players = server.getPlayers();
				if ((lastServer == null || (players < server.getMaxPlayers() && players > lastServer.getPlayers()))) {
					lastServer = server;
				}
			}

			connectPlayer(player, lastServer.getName());

			player.closeInventory();
		} else {
			player.sendMessage("§cNenhum servidor disponível no momento!");
		}
	}

	public void connectPlayer(Player p, String ip) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);

			out.writeUTF("Connect");
			out.writeUTF(ip);

			((PluginMessageRecipient) p).sendPluginMessage(getManager().getPlugin(), "BungeeCord", b.toByteArray());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public boolean isOnline(String name) {
		boolean online = false;

		for (FullyServerStatus server : servers.values()) {
			if (server.getName().contains(name) && server.getServerStatus() != ServerStatus.OFFLINE)
				online = true;
		}
		return online;
	}

	public int getServerPlayersPath(String name) {
		int online = 0;

		for (FullyServerStatus server : servers.values()) {
			if (server.getName().toLowerCase().contains(name.toLowerCase()))
				online += server.getPlayers();
		}
		return online;
	}

	public int getServerPlayers(String name) {
		int online = 0;

		for (FullyServerStatus server : servers.values()) {
			if (server.getName().equalsIgnoreCase(name))
				online += server.getPlayers();
		}
		return online;
	}

	public int getTotalPlayers() {
		return servers.get("ALL").getPlayers();
	}

	public int getTotalMaxPlayers() {
		int max = 0;
		for (FullyServerStatus server : servers.values())
			max += server.getMaxPlayers();
		return max;
	}

}
