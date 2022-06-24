package br.com.zenix.lobby.gamer.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.zenix.lobby.utilitaries.item.ItemBuilder;

public enum CacheItems {

	JOIN(new ItemBuilder[] { new ItemBuilder(Material.COMPASS).setName("§aMenu de jogos §7(Clique aqui)"), new ItemBuilder(Material.WATCH).setName("§aSeletor de Lobby §7(Clique)"), },
			new Integer[] { 0, 8 }),
	JOINSW(new ItemBuilder[] { new ItemBuilder(Material.GRASS).setName("§aPartida aleatoria §7(Clique aqui)") },
			new Integer[] { 4 }), ITEM1(new ItemBuilder[] { new ItemBuilder(Material.INK_SACK).setDurability(10).setName("§fVisibilidade dos jogadores").setDescription("§eStatus> §aHabilitado"),
					new ItemBuilder(Material.INK_SACK).setName("§fVisibilidade dos jogadores").setDescription("§eStatus> §cDesabilitado").setDurability(10), }, new Integer[] { 7, 87 });

	private ItemBuilder[] items;
	private Integer[] slots;

	CacheItems(ItemBuilder[] items, Integer[] slots) {
		this.items = items;
		this.slots = slots;
	}

	public ItemBuilder getItem(int id) {
		return id <= items.length - 1 ? items[id] : items[0];
	}

	public Integer[] getSlots() {
		return slots;
	}

	public void build(Inventory inventory) {
		for (int i = 0; i < items.length; i++) {
			if (slots == null)
				getItem(i).build(inventory);
			else
				getItem(i).build(inventory, slots[i]);
		}
	}

	public void build(Player player) {
		build(player.getInventory());
	}

}