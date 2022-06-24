package br.com.zenix.lobby.listeners.interact;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.custom.BukkitListener;
import br.com.zenix.lobby.gamer.Gamer;
import br.com.zenix.lobby.gamer.item.CacheItems;
import br.com.zenix.lobby.inventories.InventoryMaker;

public class InteractEvents extends BukkitListener {

	public static ArrayList<UUID> toggled = new ArrayList<UUID>();
	public static ArrayList<UUID> cooldown = new ArrayList<UUID>();

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Gamer gamer = getManager().getGamerManager().getGamer(player);
		ItemStack item = event.getItem();

		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null) {
			Block block = event.getClickedBlock();

			if (block.getType().equals(Material.ENDER_CHEST) || block.getType() == Material.CHEST) {
				if (getManager().getCrateManager().coalHologram.getLocation().clone().add(0, -1, 0)
						.distance(block.getLocation()) <= 1) {
					getManager().getInventoryManager().getCrateSelectInventory().generate(player, CrateType.COAL);
					event.setCancelled(true);
				} else if (getManager().getCrateManager().silverHologram.getLocation().clone().add(0, -1, 0)
						.distance(block.getLocation()) <= 1) {
					getManager().getInventoryManager().getCrateSelectInventory().generate(player, CrateType.SILVER);
					event.setCancelled(true);
				} else if (getManager().getCrateManager().goldHologram.getLocation().clone().add(0, -1, 0)
						.distance(block.getLocation()) <= 1) {
					getManager().getInventoryManager().getCrateSelectInventory().generate(player, CrateType.GOLD);
					event.setCancelled(true);
				} else if (getManager().getCrateManager().diamondHologram.getLocation().clone().add(0, -1, 0)
						.distance(block.getLocation()) < 1) {
					getManager().getInventoryManager().getCrateSelectInventory().generate(player, CrateType.DIAMOND);
					event.setCancelled(true);
				}
			}
		}

		if (event.getAction().name().contains("RIGHT")) {

			if (item == null)
				return;

			if (item.equals(CacheItems.JOIN.getItem(0).getStack())) {
				event.setCancelled(true);
				player.openInventory(InventoryMaker.gameInventory);
			}

			if (item.equals(CacheItems.JOINSW.getItem(0).getStack())) {
				event.setCancelled(true);
				getManager().getServerManager().randomServer(player, "sw");
			}

			if (item.equals(CacheItems.JOIN.getItem(1).getStack())) {
				event.setCancelled(true);
				player.openInventory(InventoryMaker.lobbyInventory);
			}

			if (item.getItemMeta().getDisplayName().startsWith("§fJogadores [ON]")) {
				event.setCancelled(true);

				for (Player players : Bukkit.getOnlinePlayers()) {
					event.getPlayer().hidePlayer(players);
				}

				gamer.setShow(false);

				new ItemBuilder(Material.INK_SACK).setDurability(10).setName("§fJogadores [OFF]")
						.setDescription("§eStatus> §cDesabilitado").build(player, 7);

				player.sendMessage("§cA visibilidade dos jogadores agora está desativada.");
			}

			if (item.getItemMeta().getDisplayName().startsWith("§fJogadores [OFF]")) {
				event.setCancelled(true);

				for (Player players : Bukkit.getOnlinePlayers()) {
					event.getPlayer().showPlayer(players);
				}

				gamer.setShow(true);

				new ItemBuilder(Material.INK_SACK).setDurability(8).setName("§fJogadores [ON]")
						.setDescription("§eStatus> §aHabilitado").build(player, 7);

				getManager().getVanish().updateVanished();

				player.sendMessage("§eA visibilidade dos jogadores agora está ativa.");
			}
		}

	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		Gamer gamer = getManager().getGamerManager().getGamer(event.getPlayer());
		if (gamer.isFly() == true) {
			return;
		}
		if (!toggled.contains(event.getPlayer().getUniqueId())) {
			if (!cooldown.contains(event.getPlayer().getUniqueId())) {
				if (player.getGameMode() == GameMode.CREATIVE) {
					return;
				}
				event.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				Vector vec = player.getLocation().getDirection().multiply(4).setY(1.5);
				player.setVelocity(vec);
				cooldown.add(event.getPlayer().getUniqueId());
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_PLING, 1, 0);

				new BukkitRunnable() {
					public void run() {
						cooldown.remove(event.getPlayer().getUniqueId());
					}
				}.runTaskLater(getManager().getPlugin(), 1 * 15);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!toggled.contains(event.getPlayer().getUniqueId())) {
			Player player = event.getPlayer();
			Gamer gamer = getManager().getGamerManager().getGamer(event.getPlayer());
			if (gamer.isFly() == true) {
				return;
			}
			if (!cooldown.contains(event.getPlayer().getUniqueId())) {
				if ((player.getGameMode() != GameMode.CREATIVE)
						&& (player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR)
						&& (!player.isFlying())) {
					player.setAllowFlight(true);
				}
			}
		}
	}

}
