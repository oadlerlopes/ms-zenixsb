package br.com.zenix.lobby.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.zenix.core.bukkit.player.account.Account;
import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.core.master.data.handler.DataHandler;
import br.com.zenix.core.master.data.handler.type.DataType;
import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.custom.BukkitListener;

public class CrateSelectInventory extends BukkitListener {

	public void generate(Player player, CrateType crateType) {
		Inventory inventory = Bukkit.createInventory(null, 27, "Caixa de " + crateType.getName());
		Account account = getManager().getCoreManager().getAccountManager().getAccount(player);
		DataHandler dataHandler = account.getDataHandler();

		List<String> desc = new ArrayList<>();

		crateType.getDescription().stream().filter(prize -> !prize.equals(" ")).forEach(prize -> desc.add("§7» " + prize));

		int boxes = dataHandler.getValue(crateType.getBoxData()).getValue();
		int keys = dataHandler.getValue(crateType.getKeyData()).getValue();

		Material materialKey = Material.AIR;
		Material materialBox = Material.AIR;
		
		if (crateType == CrateType.COAL) {
			materialBox = Material.COAL_BLOCK;
			materialKey = Material.COAL;
		} else if (crateType == CrateType.DIAMOND) {
			materialBox = Material.DIAMOND_BLOCK;
			materialKey = Material.DIAMOND;
		} else if (crateType == CrateType.SILVER) {
			materialBox = Material.IRON_BLOCK;
			materialKey = Material.IRON_INGOT;
		} else if (crateType == CrateType.GOLD) {
			materialBox = Material.GOLD_BLOCK;
			materialKey = Material.GOLD_INGOT;
		}

		inventory.setItem(12, new ItemBuilder(materialBox).setName("§7Você possui §a" + boxes + "§7 caixas.").setAmount(boxes).getStack());
		inventory.setItem(13, new ItemBuilder(Material.SIGN).setName("§fPrêmios possíveis:").setDescription(desc).getStack());
		inventory.setItem(14, new ItemBuilder(materialKey).setName("§7Você possui §a" + keys + "§7 chaves.").setAmount(keys).getStack());
		if (crateType == CrateType.COAL) {
			inventory.setItem(21, new ItemBuilder(Material.CHEST).setName("§7Comprar caixa §6(15000 coins)").setAmount(1).getStack());
		}
		inventory.setItem(22, new ItemBuilder(Material.EMERALD).setName("§7Comprar key §6(2000 coins)").setAmount(1).getStack());

		ItemStack redGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§cFechar inventário.").setDurability(14).getStack();
		ItemStack greenGlass = new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§aAbrir caixa.").setDurability(5).getStack();

		inventory.setItem(0, redGlass);
		inventory.setItem(9, redGlass);
		inventory.setItem(10, redGlass);
		inventory.setItem(18, redGlass);

		inventory.setItem(8, greenGlass);
		inventory.setItem(16, greenGlass);
		inventory.setItem(17, greenGlass);
		inventory.setItem(26, greenGlass);

		player.openInventory(inventory);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getClickedInventory() != null) {
			if (event.getClickedInventory().getTitle().contains("Caixa de")) {
				event.setCancelled(true);

				if (event.getInventory().getSize() != 27 && event.getInventory().getSize() != 26) {
					return;
				}

				event.setCancelled(true);

				Player player = (Player) event.getWhoClicked();
				ItemStack item = event.getCurrentItem();
				Material material = item.getType();

				CrateType crate = CrateType.getType(event.getClickedInventory().getTitle().replace("Caixa de ", ""));

				if (material == Material.STAINED_GLASS_PANE) {
					int durability = item.getDurability();

					if (durability == 14) {
						player.closeInventory();
					} else if (durability == 5) {
						getManager().getCrateManager().generate(crate, getManager().getCoreManager().getAccountManager().getAccount(player));
					}
				}
				
				if (material == Material.EMERALD){
					Account account = getManager().getCoreManager().getAccountManager().getAccount(player);
					DataHandler dataHandler = account.getDataHandler();
					if (dataHandler.getValue(DataType.GLOBAL_COINS).getValue() >= 2000){
						
						dataHandler.getValue(DataType.GLOBAL_COINS).setValue(dataHandler.getValue(DataType.GLOBAL_COINS).getValue() - 2000);
						dataHandler.getValue(DataType.KEY).setValue(dataHandler.getValue(DataType.KEY).getValue() + 1);
						
						dataHandler.update(DataType.GLOBAL_COINS);
						dataHandler.update(DataType.KEY);
						player.sendMessage("§d§lKEYS §fVocê adquiriu §a§l1§f KEY por 2000 coins!");
						player.closeInventory();
					} else {
						player.sendMessage("§d§lKEYS §fVocê não tem coins o suficiente!");
						player.closeInventory();
					}
				}
				if (material == Material.CHEST){
					Account account = getManager().getCoreManager().getAccountManager().getAccount(player);
					DataHandler dataHandler = account.getDataHandler();
					if (dataHandler.getValue(DataType.GLOBAL_COINS).getValue() >= 15000){
						
						dataHandler.getValue(DataType.GLOBAL_COINS).setValue(dataHandler.getValue(DataType.GLOBAL_COINS).getValue() - 15000);
						dataHandler.getValue(DataType.CRATE_COAL).setValue(dataHandler.getValue(DataType.CRATE_COAL).getValue() + 1);
						
						dataHandler.update(DataType.GLOBAL_COINS);
						dataHandler.update(DataType.CRATE_COAL);
						player.sendMessage("§d§lCAIXAS §fVocê adquiriu §a§l1§f CAIXA por 15000 coins!");
						player.closeInventory();
					} else {
						player.sendMessage("§d§lCAIXAS §fVocê não tem coins o suficiente!");
						player.closeInventory();
					}
				}
			}
		}
	}

}
