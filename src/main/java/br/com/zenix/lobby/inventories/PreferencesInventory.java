package br.com.zenix.lobby.inventories;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.zenix.core.bukkit.player.account.Account;
import br.com.zenix.lobby.custom.BukkitListener;

public class PreferencesInventory extends BukkitListener {

	public static final HashMap<UUID, Integer> inventoryIndex = new HashMap<>();

	public void generate(Player player) {
		Inventory inventory = Bukkit.createInventory(player, InventoryType.HOPPER, "Status");
		int index = inventoryIndex.containsKey(player.getUniqueId()) ? inventoryIndex.get(player.getUniqueId()) : 1;
		update(player, inventory, index);
		player.openInventory(inventory);
	}

	public void update(Player player, Inventory inventory, int index) {
		inventoryIndex.put(player.getUniqueId(), index);

		inventory.clear();

		Account account = getManager().getCoreManager().getAccountManager().getAccount(player);

		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM);
		itemStack.setDurability((short) 3);

		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		skullMeta.setOwner(player.getName());
		skullMeta.setDisplayName("§eInformações sobre o player");
		skullMeta.setLore(Arrays.asList(new String[] { "",
				"§fGrupo: " + account.getRank().getTag().getColor() + account.getRank().getName().toUpperCase(),
				"§fLiga: " + account.getLeague().getColor() + account.getLeague().getName(), "§fXP: §b" + account.getXp(),
				"§fDoubleXP: §e" + account.getDoubleXP(), "§fMoedas: §b" + account.getCoins() + "" }));
		itemStack.setItemMeta(skullMeta);

		inventory.setItem(0, itemStack);

		player.playSound(player.getLocation(), Sound.CLICK, 5F, 5F);
		player.updateInventory();
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getClickedInventory() != null) {
			if (event.getClickedInventory().getTitle().startsWith("Status")) {
				event.setCancelled(true);
				if (event.getCurrentItem() != null) {

					if (!inventoryIndex.containsKey(player.getUniqueId()))
						inventoryIndex.put(player.getUniqueId(), 1);

					Material type = event.getCurrentItem().getType();

					if (type == Material.AIR)
						return;

				}
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (inventoryIndex.containsKey(event.getPlayer().getUniqueId()))
			inventoryIndex.remove(event.getPlayer().getUniqueId());
	}

	public int[] range(int start, int stop) {
		int[] result = new int[stop - start];

		for (int i = 0; i < stop - start; i++)
			result[i] = start + i;

		return result;
	}

}
