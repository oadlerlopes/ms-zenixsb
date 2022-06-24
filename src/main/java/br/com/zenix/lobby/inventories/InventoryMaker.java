package br.com.zenix.lobby.inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.zenix.core.bukkit.server.type.ServerType;
import br.com.zenix.core.proxy.server.FullyServerStatus;
import br.com.zenix.lobby.custom.BukkitListener;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.utilitaries.item.ItemBuilder;

public class InventoryMaker extends BukkitListener {

	private static final String titlePvP = "§8§nServidores do ZenixPvP";
	private static final String titleHG = "§8§nServidores do Zenix-HG";
	private static final String titleEvent = "§8§nServidores de Evento";
	private static final String titleOITC = "§8§nServidores de OITC";
	private static final String titleGladiator = "§8§nServidores de Gladiator";
	private static final String titleSkyWars = "§8§nServidores de SkyWars";
	private static final String titleLobby = "§8§nServidores do Lobby";
	private static final String titleGame = "§8§nEscolha um modo de jogo";

	public static final Inventory lobbyInventory = Bukkit.createInventory(null, 27, titleLobby);
	public static final Inventory oitcInventory = Bukkit.createInventory(null, 27, titleOITC);
	public static final Inventory pvpInventory = Bukkit.createInventory(null, 27, titlePvP);
	public static final Inventory eventInventory = Bukkit.createInventory(null, 27, titleEvent);
	public static final Inventory gameInventory = Bukkit.createInventory(null, 36, titleGame);
	public static final Inventory skyWarsInventory = Bukkit.createInventory(null, 27, titleSkyWars);
	public static final Inventory gladiatorInventory = Bukkit.createInventory(null, 27, titleGladiator);
	public static final Inventory hgInventory = Bukkit.createInventory(null, 36, titleHG);

	boolean allowEvent;

	public void startUpdate(Manager manager) {
		new BukkitRunnable() {
			public void run() {

				updateInventory("pvp");
				updateInventory("gladiator");
				updateInventory("lobby");
				updateInventory("hg");
				updateInventory("evento");
				updateInventory("skywars");
				updateInventory("oitc");

				gameInventory.setItem(11,
						new ItemBuilder(Material.IRON_SWORD).setName("§a§lPvP")
								.setDescription(
										"\n§fTreine suas habilidades em §fcombate e prepare-se para o §fgrande confronto!\n\n§3§l"
												+ getManager().getServerManager().getServerPlayersPath("PVP")
												+ " §fplayers conectados\n")
								.getStack());

				gameInventory.setItem(12,
						new ItemBuilder(Material.MUSHROOM_SOUP).setName("§a§lHungerGames")
								.setDescription(
										"\n§fSerá que você é bravo o suficiente §fpara sobreviver contra 80 players?\n\n§3§l"
												+ getManager().getServerManager().getServerPlayersPath("HG")
												+ " §fplayers conectados")
								.getStack());

				gameInventory.setItem(13,
						new ItemBuilder(Material.IRON_FENCE).setName("§a§lGladiator")
								.setDescription(
										"\n§fVocê e seu adversário presos §fem uma jaula, somente o melhor §firá sair dela. §fO que está esperando?\n\n§3§l"
												+ getManager().getServerManager().getServerPlayersPath("GLADIATOR")
												+ " §fplayers conectados")
								.getStack());

				gameInventory.setItem(14,
						new ItemBuilder(Material.SLIME_BALL).setName("§a§lEventos")
								.setDescription("\n§fSalas reservadas para eventos\n\n§3§l"
										+ getManager().getServerManager().getServerPlayersPath("EVENTO")
										+ " §fplayers conectados")
								.getStack());

				gameInventory.setItem(15,
						new ItemBuilder(Material.BOW).setName("§a§lOne in the chamber")
								.setDescription(
										"\n§fOne in the chamber é um modo §fde jogo cujo objetivo é ter §fo máximo de kills possível. §3§l"
												+ getManager().getServerManager().getServerPlayersPath("OITC")
												+ " §fplayers conectados")
								.getStack());
				
				gameInventory.setItem(22,
						new ItemBuilder(Material.GRASS).setName("§a§lSkyWars")
								.setDescription(
										"\n§fVocê é corajoso o §fsuficiente §fpara travar uma batalha\n§fnas alturas? Tem certeza?\n\n§3§l"
												+ getManager().getServerManager().getServerPlayersPath("sw")
												+ " §fplayers conectados")
								.getStack());

			}
		}.runTaskTimer(manager.getPlugin(), 1, 5);

	}

	public void updateInventory(String serverName) {
		List<FullyServerStatus> servers = null;
		ServerType type = null;

		if (serverName.equalsIgnoreCase("pvp")) {
			servers = getManager().getServerManager().getPvPServers();
			type = ServerType.PVP;
		} else if (serverName.equalsIgnoreCase("lobby")) {
			servers = getManager().getServerManager().getLobbyServers();
			type = ServerType.LOBBY;
		} else if (serverName.equalsIgnoreCase("hg")) {
			servers = getManager().getServerManager().getHungerGamesServers();
			type = ServerType.HG;
		} else if (serverName.equalsIgnoreCase("skywars")) {
			servers = getManager().getServerManager().getSkyWarsServers();
			type = ServerType.SKYWARS;
		} else if (serverName.equalsIgnoreCase("gladiator")) {
			servers = getManager().getServerManager().getGladiatorServer();
			type = ServerType.GLADIATOR;
		} else if (serverName.equalsIgnoreCase("evento")) {
			servers = getManager().getServerManager().getEventServer();
			type = ServerType.HG;
		} else if (serverName.equalsIgnoreCase("oitc")) {
			servers = getManager().getServerManager().getOITCServer();
			type = ServerType.OITC;
		} else {
			type = ServerType.NONE;
		}

		int slot = type == ServerType.PVP ? 10 : 10;

		if (type == ServerType.PVP) {
			pvpInventory.clear();
		} else if (type == ServerType.LOBBY) {
			lobbyInventory.clear();
		} else if (type == ServerType.GLADIATOR) {
			gladiatorInventory.clear();
		} else if (type == ServerType.SKYWARS) {
			skyWarsInventory.clear();
		} else if (type == ServerType.OITC) {
			oitcInventory.clear();
		} else if (type == ServerType.HG && !serverName.equalsIgnoreCase("evento")) {
			hgInventory.clear();
		} else if (type == ServerType.HG && serverName.equalsIgnoreCase("evento")) {
			eventInventory.clear();
		}

		slot = type == ServerType.PVP ? 10 : 10;
		int i = 1;
		for (FullyServerStatus server : servers) {
			if (i == 15)
				break;

			if (slot % 9 > 7)
				slot += 2;

			if (type == ServerType.PVP) {
				pvpInventory.setItem(slot, getItemPvP(server).getStack());
			} else if (type == ServerType.LOBBY) {
				lobbyInventory.setItem(slot, getItemLobby(server).getStack());
			} else if (type == ServerType.GLADIATOR) {
				gladiatorInventory.setItem(slot, getItemLobby(server).getStack());
			} else if (type == ServerType.SKYWARS) {
				skyWarsInventory.setItem(slot, getItemMinigame(server).getStack());
			} else if (type == ServerType.OITC) {
				oitcInventory.setItem(slot, getItemMinigame(server).getStack());
			} else if (type == ServerType.HG && !serverName.equalsIgnoreCase("evento")) {
				hgInventory.setItem(slot, getItem(server).getStack());
			} else if (type == ServerType.HG && serverName.equalsIgnoreCase("evento")) {
				if (server.getName().startsWith("EVENTO-") && server.getCustomMessage().contains("ALPHA-1")) {
					eventInventory.setItem(slot, getItem(server).getStack());
				}
			}
			slot++;
			i++;
		}
	}

	public ItemBuilder getItem(FullyServerStatus server) {
		if (server.getName().contains("EVENTO-")) {
			if (server.getCustomMessage().contains("ALPHA-1")) {
				allowEvent = true;
			} else {
				allowEvent = false;
			}
		}

		return new ItemBuilder(Material.INK_SACK)
				.setName("§a§L" + server.getName().replace("HG-", "HG (").replace("EVENTO-", "EVENTO (") + ")")
				.setDurability(getManager().getServerManager().getStageShort(server))
				.setDescription(new String[] {
						"§f" + server.getCustomMessage().replace("ALPHA-1", "")
								.replace("O jogo começa em", "Iniciando em:§e§l")
								.replace("A invencibilidade acaba em ", "Invencibilidade:§e§l")
								.replace("O jogo está em andamento há ", "Jogo em andamento há:§e§l"),
						" ", "§3§l" + server.getPlayers() + "§f players conectados", " ", "§b§lClique para conectar" })
				.setAmount(server.getPlayers());
	}

	public ItemBuilder getItemMinigame(FullyServerStatus server) {
		return new ItemBuilder(Material.PAPER).setName("§a§L" + server.getName().replace("OITC-", "OITC (").replace("SW-", "SW (") + ")")

				.setDescription(new String[] {
						"§f" + server.getCustomMessage().replace("ALPHA-1", "")
								.replace("O jogo começa em", "Iniciando em:§e§l")
								.replace("A invencibilidade acaba em ", "Invencibilidade:§e§l")
								.replace("O jogo está em andamento há ", "Jogo em andamento há:§e§l"),
						" ", "§3§l" + server.getPlayers() + "§f players conectados", " ", "§b§lClique para conectar" })
				.setAmount(server.getPlayers());
	}

	public ItemBuilder getItemPvP(FullyServerStatus server) {
		return new ItemBuilder((server.getName().endsWith("1") ? Material.DIAMOND_SWORD : Material.WOOD_SWORD))
				.setName("§a§L" + server.getName().replace("PVP-", "PVP (").replace("EVENTO-", "EVENTO (")
						.replace("LB-", "LB (") + ")")
				.setDescription(new String[] {
						"§fModo: §a§l" + (server.getName().endsWith("1") ? "FullIron" : "Simulator"), " ",
						"§3§l" + server.getPlayers() + "§f players conectados", " ", "§b§lClique para conectar" });
	}

	public ItemBuilder getItemLobby(FullyServerStatus server) {
		return new ItemBuilder(Material.INK_SACK)
				.setName("§a§L" + server.getName().replace("LOBBY-", "LOBBY (").replace("GLADIATOR-", "GLADIATOR (")
						+ ")")
				.setDescription(new String[] { " ", "§3§l" + server.getPlayers() + "§f players conectados", " ",
						"§b§lClique para conectar" })
				.setAmount(server.getPlayers()).setDurability(getManager().getServerManager().getStageShort(server));

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle() != null && event.getCurrentItem() != null) {
			Player player = (Player) event.getWhoClicked();
			ItemStack current = event.getCurrentItem();
			Material type = current.getType();

			String name = "";

			if (event.getInventory().getTitle().equalsIgnoreCase(titlePvP)) {
				name = "pvp";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleLobby)) {
				name = "lobby";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleHG)) {
				name = "hg";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleEvent)) {
				name = "evento";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleOITC)) {
				name = "oitc";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleGladiator)) {
				name = "gladiator";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleSkyWars)) {
				name = "sw";
			} else if (event.getInventory().getTitle().equalsIgnoreCase(titleGame)) {
				boolean right = event.getClick().isRightClick();
				event.setCancelled(true);
				if (type.equals(Material.IRON_SWORD)) {
					if (right) {
						getManager().getServerManager().randomServer(player, "pvp");
						return;
					}
					if (type.equals(Material.IRON_SWORD)) {
						player.openInventory(pvpInventory);
					}
				} else if (type.equals(Material.COMPASS)) {
					if (right) {
						getManager().getServerManager().randomServer(player, "lobby");
						return;
					}
					player.openInventory(lobbyInventory);
				} else if (type.equals(Material.MUSHROOM_SOUP)) {
					if (right) {
						getManager().getServerManager().randomServer(player, "hg");
						return;
					}
					player.openInventory(hgInventory);
				} else if (type.equals(Material.SLIME_BALL)) {
					if (right) {
						getManager().getServerManager().randomServer(player, "evento");
						return;
					}
					player.openInventory(eventInventory);
				} else if (type.equals(Material.IRON_FENCE)) {
					if (right) {
						getManager().getServerManager().randomServer(player, "gladiator");
						return;
					}
					player.openInventory(gladiatorInventory);
				} else if (type.equals(Material.BOW)) {
					if (right) {
						getManager().getServerManager().randomServer(player, "oitc");
						return;
					}
					player.openInventory(oitcInventory);
				} else if (type.equals(Material.GRASS)) {
					getManager().getServerManager().connectPlayer(player, "lobby-2");
				}
			} else {
				return;
			}

			if (type.equals(Material.REDSTONE)) {
				event.setCancelled(true);
				player.openInventory(gameInventory);
				return;
			} else if (type.equals(Material.NETHER_STAR)) {
				event.setCancelled(true);
				getManager().getServerManager().randomServer(player, name);
				return;
			}

			if (!type.equals(Material.INK_SACK) && !type.equals(Material.IRON_SWORD) && !type.equals(Material.DIAMOND_SWORD) && !type.equals(Material.PAPER)
					&& !type.equals(Material.WOOD_SWORD) && !type.equals(Material.getMaterial(159)))
				return;

			FullyServerStatus server = getManager().getServerManager().getServer(
					current.getItemMeta().getDisplayName().replace("§a§L", "").replace(" (", "-").replace(")", ""));

			if (server == null)
				return;
			
			if (server.getPlayers() >= server.getMaxPlayers() && !player.hasPermission("server.whitelist")) {
				player.closeInventory();
				player.sendMessage(
						"§4§lERRO §fO servidor está lotado! Compre §6§lVIP§f em §b§lLOJA.ZENIX.CC§f para entrar ou tente novamente em §c§lBREVE!");
				return;
			}

			getManager().getServerManager().connectPlayer(player, server.getName());

			player.closeInventory();

		}
	}

}
