package br.com.zenix.lobby.hologram;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.zenix.core.bukkit.player.account.Account;
import br.com.zenix.core.bukkit.player.hologram.Hologram;
import br.com.zenix.core.bukkit.player.league.type.LeagueType;
import br.com.zenix.core.master.data.management.utilitaries.Callback;
import br.com.zenix.lobby.hologram.PlayerTop.PlayerTopHGWins;
import br.com.zenix.lobby.hologram.PlayerTop.PlayerTopPvP;
import br.com.zenix.lobby.hologram.PlayerTop.PlayerTopSWDeaths;
import br.com.zenix.lobby.hologram.PlayerTop.PlayerTopSWElo;
import br.com.zenix.lobby.hologram.PlayerTop.PlayerTopSWWins;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */
public class HologramManager extends Management {

	private static final List<PlayerTop> topScore = new ArrayList<>();
	private static final List<PlayerTopHGWins> topScoreHG = new ArrayList<>();
	private static final List<PlayerTopPvP> topScorePvP = new ArrayList<>();

	private static final List<PlayerTopSWElo> topScoreSWElo = new ArrayList<>();
	private static final List<PlayerTopSWWins> topScoreSWWins = new ArrayList<>();
	private static final List<PlayerTopSWDeaths> topScoreSWDeaths = new ArrayList<>();

	public Hologram scoreHologram;
	public Hologram scoreHG;
	public Hologram scorePvP;

	public Hologram scoreSWELO;
	public Hologram scoreSWWINS;
	public Hologram scoreSWDEATHS;

	public HologramManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		return updateHolograms();
	}

	public boolean updateHolograms() {
		// 1008, 103.5, 989
		topScore.clear();
		topScoreHG.clear();
		topScorePvP.clear();

		topScoreSWElo.clear();
		topScoreSWDeaths.clear();
		topScoreSWWins.clear();

		loadList(topScore);
		loadListHG(topScoreHG);
		loadListPvP(topScorePvP);
		return setHolograms();
	}

	public boolean loadList(List<PlayerTop> list) {
		ResultSet set = getManager().getCoreManager().getDataManager().getMySQL().executeQuery("SELECT * FROM `global_data` WHERE `type`=12 ORDER BY `value` DESC LIMIT 15");
		try {
			int id = 0;
			while (set.next()) {
				id++;
				list.add(new PlayerTop(id, getManager().getCoreManager().getNameFetcher().getName(set.getInt("player")), set.getInt("value")));
			}
			set.close();
			return true;
		} catch (Exception exeption) {
			exeption.printStackTrace();
			return false;
		}

	}

	public boolean loadListHG(List<PlayerTopHGWins> list) {
		ResultSet set = getManager().getCoreManager().getDataManager().getMySQL().executeQuery("SELECT * FROM `global_data` WHERE `type`=5 ORDER BY `value` DESC LIMIT 15");
		try {
			int id = 0;
			while (set.next()) {
				id++;
				list.add(new PlayerTopHGWins(id, getManager().getCoreManager().getNameFetcher().getName(set.getInt("player")), set.getInt("value")));
			}
			set.close();
			return true;
		} catch (Exception exeption) {
			exeption.printStackTrace();
			return false;
		}

	}

	public boolean loadListPvP(List<PlayerTopPvP> list) {
		ResultSet set = getManager().getCoreManager().getDataManager().getMySQL().executeQuery("SELECT * FROM `global_data` WHERE `type`=8 ORDER BY `value` DESC LIMIT 15");
		try {
			int id = 0;
			while (set.next()) {
				id++;
				list.add(new PlayerTopPvP(id, getManager().getCoreManager().getNameFetcher().getName(set.getInt("player")), set.getInt("value")));
			}
			set.close();
			return true;
		} catch (Exception exeption) {
			exeption.printStackTrace();
			return false;
		}

	}

	public boolean loadListSWELO(List<PlayerTopSWElo> list) {
		ResultSet set = getManager().getCoreManager().getDataManager().getMySQL().executeQuery("SELECT * FROM `global_data` WHERE `type`=40 ORDER BY `value` DESC LIMIT 15");
		try {
			int id = 0;
			while (set.next()) {
				id++;
				list.add(new PlayerTopSWElo(id, getManager().getCoreManager().getNameFetcher().getName(set.getInt("player")), set.getInt("value")));
			}
			set.close();
			return true;
		} catch (Exception exeption) {
			exeption.printStackTrace();
			return false;
		}

	}

	public boolean loadListSWWins(List<PlayerTopSWWins> list) {
		ResultSet set = getManager().getCoreManager().getDataManager().getMySQL().executeQuery("SELECT * FROM `global_data` WHERE `type`=39 ORDER BY `value` DESC LIMIT 15");
		try {
			int id = 0;
			while (set.next()) {
				id++;
				list.add(new PlayerTopSWWins(id, getManager().getCoreManager().getNameFetcher().getName(set.getInt("player")), set.getInt("value")));
			}
			set.close();
			return true;
		} catch (Exception exeption) {
			exeption.printStackTrace();
			return false;
		}

	}

	public boolean loadListSWDeaths(List<PlayerTopSWDeaths> list) {
		ResultSet set = getManager().getCoreManager().getDataManager().getMySQL().executeQuery("SELECT * FROM `global_data` WHERE `type`=38 ORDER BY `value` DESC LIMIT 15");
		try {
			int id = 0;
			while (set.next()) {
				id++;
				list.add(new PlayerTopSWDeaths(id, getManager().getCoreManager().getNameFetcher().getName(set.getInt("player")), set.getInt("value")));
			}
			set.close();
			return true;
		} catch (Exception exeption) {
			exeption.printStackTrace();
			return false;
		}

	}

	public boolean setHolograms() {
		topScore.clear();
		loadList(topScore);

		if (scoreHologram != null) {
			scoreHologram.remove();
			scoreHologram = null;
		}

		Location scoreLocation = new Location(Bukkit.getServer().getWorld("world"), -115.44, 167.09, -195.70);
		scoreHologram = new Hologram("§E§lTOP 15 §f§lGLOBAL RANKING", scoreLocation, true);
		DecimalFormat df = new DecimalFormat("######.#");
		scoreHologram.addLine("   ");

		List<String> ss = new ArrayList<String>();

		for (PlayerTop scoreTop : topScore) {
			if (scoreTop != null) {
				LeagueType leagueType = LeagueType.getRanked(scoreTop.getTop());

				String player = scoreTop.getName();
				UUID uuid = getManager().getCoreManager().getNameFetcher().getUUID(player);

				Account account = new Account(uuid);

				if (!account.isLoaded()) {
					account.load(new Callback<Boolean>() {
						public void finish(Boolean bool) {
							if (bool)
								getManager().getCoreManager().getAccountManager().getAccounts().put(uuid, account);
							else {
								try {
									Thread.sleep(500L);
								} catch (InterruptedException exception) {
									exception.printStackTrace();
								}
							}
						}
					});
					account.updatePlayer(player);
				}

				String line = "";
				if (account.isLoaded() && account != null) {
					String group = getManager().getCoreManager().getPermissionManager().getRank(account.getRank().getId()).getTag().getColor();
					String name = group + scoreTop.getName();

					line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + leagueType.getColor() + leagueType.getSymbol() + " §7- §e" + df.format(scoreTop.getTop()) + " XP";

				} else {

					String name = scoreTop.getName();

					line = "§a" + scoreTop.getId() + ". §7" + name + " §7- §e" + leagueType.getColor() + leagueType.getSymbol() + " §7- §e" + df.format(scoreTop.getTop()) + " XP";
				}

				scoreHologram.addLine(line);

				ss.add("§a" + scoreTop.getId() + ". " + scoreTop.getName() + " - " + leagueType.getColor() + leagueType.getSymbol() + " - " + df.format(scoreTop.getTop()) + " XP");
			}
		}

		for (String string : ss) {
			System.out.print("" + string);
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			scoreHologram.hide(player);
			scoreHologram.show(player);

		}

		//////////////

		topScoreHG.clear();
		loadListHG(topScoreHG);

		if (scoreHG != null) {
			scoreHG.remove();
			scoreHG = null;
		}

		Location scoreLocationHG = new Location(Bukkit.getServer().getWorld("world"), -99.58, 167.09, -179.56);
		scoreHG = new Hologram("§E§lTOP 15 §f§lHG WINS", scoreLocationHG, true);
		scoreHG.addLine("   ");

		for (PlayerTopHGWins scoreTop : topScoreHG) {
			if (scoreTop != null) {
				String player = scoreTop.getName();
				UUID uuid = getManager().getCoreManager().getNameFetcher().getUUID(player);

				Account account = new Account(uuid);

				if (!account.isLoaded()) {
					account.load(new Callback<Boolean>() {
						public void finish(Boolean bool) {
							if (bool)
								getManager().getCoreManager().getAccountManager().getAccounts().put(uuid, account);
							else {
								try {
									Thread.sleep(500L);
								} catch (InterruptedException exception) {
									exception.printStackTrace();
								}
							}
						}
					});
					account.updatePlayer(player);
				}

				String line = "";

				if ((account.isLoaded() && account != null) && account.getRank() != null) {
					String group = getManager().getCoreManager().getPermissionManager().getRank(account.getRank().getId()).getTag().getColor();
					String name = group + scoreTop.getName();

					line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " WINS";

				} else {

					String name = scoreTop.getName();

					line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " WINS";

				}
				scoreHG.addLine(line);
			}
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			scoreHG.hide(player);
			scoreHG.show(player);
		}

		////////////

		topScorePvP.clear();
		loadListPvP(topScorePvP);

		if (scorePvP != null) {
			scorePvP.remove();
			scorePvP = null;
		}

		Location scoreLocationPvP = new Location(Bukkit.getServer().getWorld("world"), -115.49, 167.09, -162.77);
		scorePvP = new Hologram("§E§lTOP 15 §f§lPVP WINS", scoreLocationPvP, true);
		scorePvP.addLine("   ");

		for (PlayerTopPvP scoreTop : topScorePvP) {
			if (scoreTop != null) {
				String player = scoreTop.getName();
				UUID uuid = getManager().getCoreManager().getNameFetcher().getUUID(player);

				Account account = new Account(uuid);

				if (!account.isLoaded()) {
					account.load(new Callback<Boolean>() {
						public void finish(Boolean bool) {
							if (bool)
								getManager().getCoreManager().getAccountManager().getAccounts().put(uuid, account);
							else {
								try {
									Thread.sleep(500L);
								} catch (InterruptedException exception) {
									exception.printStackTrace();
								}
							}
						}
					});
					account.updatePlayer(player);
				}

				String line = "";
				if (account.isLoaded() && account != null) {
					String group = getManager().getCoreManager().getPermissionManager().getRank(account.getRank().getId()).getTag().getColor();
					String name = group + scoreTop.getName();

					line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " WINS";

				} else {

					String name = scoreTop.getName();

					line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " WINS";

				}
				scorePvP.addLine(line);
			}
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			scorePvP.hide(player);
			scorePvP.show(player);
		}

		if (getManager().isSkyWarsLobby()) {
			topScoreSWWins.clear();
			loadListSWWins(topScoreSWWins);

			if (scoreSWWINS != null) {
				scoreSWWINS.remove();
				scoreSWWINS = null;
			}

			Location scoreLocationSWWINS = new Location(Bukkit.getServer().getWorld("world"), 999, 105.3, 1002);
			scoreSWWINS = new Hologram("§E§lTOP 15 §f§lSKYWARS WINS", scoreLocationSWWINS, true);
			scoreSWWINS.addLine("   ");

			for (PlayerTopSWWins scoreTop : topScoreSWWins) {
				if (scoreTop != null) {
					String player = scoreTop.getName();
					UUID uuid = getManager().getCoreManager().getNameFetcher().getUUID(player);

					Account account = new Account(uuid);

					if (!account.isLoaded()) {
						account.load(new Callback<Boolean>() {
							public void finish(Boolean bool) {
								if (bool)
									getManager().getCoreManager().getAccountManager().getAccounts().put(uuid, account);
								else {
									try {
										Thread.sleep(500L);
									} catch (InterruptedException exception) {
										exception.printStackTrace();
									}
								}
							}
						});
						account.updatePlayer(player);
					}

					String line = "";
					if (account.isLoaded() && account != null) {
						String group = getManager().getCoreManager().getPermissionManager().getRank(account.getRank().getId()).getTag().getColor();
						String name = group + scoreTop.getName();

						line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " WINS";

					} else {

						String name = scoreTop.getName();

						line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " WINS";

					}
					scoreSWWINS.addLine(line);
				}
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				scoreSWWINS.hide(player);
				scoreSWWINS.show(player);
			}
		}

		if (getManager().isSkyWarsLobby()) {
			topScoreSWDeaths.clear();
			loadListSWDeaths(topScoreSWDeaths);

			if (scoreSWDEATHS != null) {
				scoreSWDEATHS.remove();
				scoreSWDEATHS = null;
			}

			Location scoreLocationSWWINS = new Location(Bukkit.getServer().getWorld("world"), 1004, 105.3, 998);
			scoreSWDEATHS = new Hologram("§E§lTOP 15 §f§lSKYWARS DEATHS", scoreLocationSWWINS, true);
			scoreSWDEATHS.addLine("   ");

			for (PlayerTopSWDeaths scoreTop : topScoreSWDeaths) {
				if (scoreTop != null) {
					String player = scoreTop.getName();
					UUID uuid = getManager().getCoreManager().getNameFetcher().getUUID(player);

					Account account = new Account(uuid);

					if (!account.isLoaded()) {
						account.load(new Callback<Boolean>() {
							public void finish(Boolean bool) {
								if (bool)
									getManager().getCoreManager().getAccountManager().getAccounts().put(uuid, account);
								else {
									try {
										Thread.sleep(500L);
									} catch (InterruptedException exception) {
										exception.printStackTrace();
									}
								}
							}
						});
						account.updatePlayer(player);
					}

					String line = "";
					if (account.isLoaded() && account != null) {
						String group = getManager().getCoreManager().getPermissionManager().getRank(account.getRank().getId()).getTag().getColor();
						String name = group + scoreTop.getName();

						line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " MORTES";

					} else {

						String name = scoreTop.getName();

						line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " MORTES";

					}
					scoreSWDEATHS.addLine(line);
				}
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				scoreSWDEATHS.hide(player);
				scoreSWDEATHS.show(player);
			}
		}

		if (getManager().isSkyWarsLobby()) {
			topScoreSWElo.clear();
			loadListSWELO(topScoreSWElo);

			if (scoreSWELO != null) {
				scoreSWELO.remove();
				scoreSWELO = null;
			}

			Location scoreLocationSWWINS = new Location(Bukkit.getServer().getWorld("world"), 995, 105.3, 1006);
			scoreSWELO = new Hologram("§E§lTOP 15 §f§lSKYWARS ELO", scoreLocationSWWINS, true);
			scoreSWELO.addLine("   ");

			for (PlayerTopSWElo scoreTop : topScoreSWElo) {
				if (scoreTop != null) {
					String player = scoreTop.getName();
					UUID uuid = getManager().getCoreManager().getNameFetcher().getUUID(player);

					Account account = new Account(uuid);

					if (!account.isLoaded()) {
						account.load(new Callback<Boolean>() {
							public void finish(Boolean bool) {
								if (bool)
									getManager().getCoreManager().getAccountManager().getAccounts().put(uuid, account);
								else {
									try {
										Thread.sleep(500L);
									} catch (InterruptedException exception) {
										exception.printStackTrace();
									}
								}
							}
						});
						account.updatePlayer(player);
					}

					String line = "";
					if (account.isLoaded() && account != null) {
						String group = getManager().getCoreManager().getPermissionManager().getRank(account.getRank().getId()).getTag().getColor();
						String name = group + scoreTop.getName();

						line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " ELO";

					} else {

						String name = scoreTop.getName();

						line = "§a" + scoreTop.getId() + ". §6" + name.replace("§l", "") + " §7- §e" + scoreTop.getTop() + " ELO";

					}
					scoreSWELO.addLine(line);
				}
			}

			for (Player player : Bukkit.getOnlinePlayers()) {
				scoreSWELO.hide(player);
				scoreSWELO.show(player);
			}
		}

		return true;
	}
}