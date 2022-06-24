package br.com.zenix.lobby.listeners.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.zenix.core.bukkit.player.detection.events.AsyncPreDamageEvent;
import br.com.zenix.lobby.custom.BukkitListener;

public class EntityEvents extends BukkitListener {

	@EventHandler
	public void onChuva(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	private void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
	}

	@EventHandler
	private void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
		event.getWorld().setTime(0);
	}

	@EventHandler
	private void onEntityDamage(EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	private void onEntityDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	private void onEntityDamage(AsyncPreDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().hasItemMeta()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().hasPermission("commons.staff.admin")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent event) {
		if (!event.getPlayer().hasPermission("commons.staff.admin")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void onMove(PlayerMoveEvent event) {
		if (event.getPlayer().getLocation().getY() <= 5) {
			Location location = (getManager().isSkyWarsLobby() ? new Location(Bukkit.getWorld("world"), 1012, 102, 1007) : Bukkit.getWorld("world").getSpawnLocation());

			if (!getManager().isSkyWarsLobby()) {
				location.setYaw(270.0f);
			} else {
				location.setYaw(180.0f);
			}

			event.getPlayer().teleport(location);

		}
		if (event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.SPONGE)) {
			event.getPlayer().setVelocity(event.getPlayer().getEyeLocation().getDirection().multiply(7F).setY(1.0F));
			event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.FIREWORK_TWINKLE, 4.0F, 4.0F);
		}
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		getManager().getCoreManager().getTagManager().updateTagCommand(event.getPlayer());
	}

}
