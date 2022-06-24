package br.com.zenix.lobby.crate.type;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

import br.com.zenix.core.master.data.handler.type.DataType;

public enum CrateType {

	COAL("Carvão", Arrays.asList("§a3.000 §7Coins", "§a5.000 §7Coins", "§a10.000 §7Coins", "§e1 KIT§8(§7Para o hg ou pvp§8)"), DataType.CRATE_COAL, DataType.KEY, ChatColor.DARK_GRAY),
	SILVER(
			"Prata",
			Arrays.asList("§a5.000 §7Coins", "§a10.000 §7Coins", "§a20.000 §7Coins", "§e1 §7Double XP", "§e3 §7Double XP", "§c5 §7Double XP", "§d1 §7Vip ULTIMATE §8(§7Uma semana§8)"),
			DataType.CRATE_SILVER,
			DataType.KEY,
			ChatColor.WHITE),
	GOLD(
			"Ouro",
			Arrays.asList("§a10.000 §7Coins", "§a20.000 §7Coins", "§a30.000 §7Coins", "§e3 §7Double XP", "§e5 §7Double XP", "§c10 §7Double XP", "§d1 §7Vip ultimate §8(§7Um mês§8)"),
			DataType.CRATE_GOLD,
			DataType.KEY,
			ChatColor.GOLD),
	DIAMOND(
			"Diamante",
			Arrays.asList("§a15.000 §7Coins", "§a30.000 §7Coins", "§a40.000 §7Coins", "§e10 §7Double XP", "§e15 §7Double XP", "§c20 §7Double XP", "§d1 §7Vip ULTIMATE §8(§4Lifetime§8)"),
			DataType.CRATE_DIAMOND,
			DataType.KEY,
			ChatColor.AQUA),;

	private String name;
	private DataType boxData, keyData;
	private ChatColor color;
	private List<String> description;

	CrateType(String name, List<String> description, DataType boxData, DataType keyData, ChatColor color) {
		this.name = name;
		this.boxData = boxData;
		this.keyData = keyData;
		this.color = color;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public DataType getBoxData() {
		return boxData;
	}

	public DataType getKeyData() {
		return keyData;
	}

	public ChatColor getColor() {
		return color;
	}

	public List<String> getDescription() {
		return description;
	}

	public static CrateType getType(String name) {
		return Arrays.asList(values()).stream().filter(crate -> crate.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

}
