package br.com.zenix.lobby.crate.types;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.lobby.crate.constructors.Crate;
import br.com.zenix.lobby.crate.constructors.Reward;
import br.com.zenix.lobby.crate.constructors.Reward.RewardType;
import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.manager.Manager;

public class Coal extends Crate {

	public List<String> pvpKits = Arrays.asList("PvP", "Ninja", "Anchor", "Magma", "Grappler", "Grappler", "Fisherman", "Archer", "Gladiator");
	public List<String> hgKits = Arrays.asList("Achilles", "Anchor", "Barbarian", "Blink", "Boxer", "Camel", "Cannibal, Cultivator", "Demoman", "Endermage", "Fireman", "Fisherman", "Forger", "Gladaitor", "Grandpa", "Grappler", "Hulk",
			"JackHammer", "Kangaroo", "Launcher", "LumberJack", "Madman", "Magma", "Miner", "Monk", "Ninja", "Phantom", "Poseidon", "Pyro", "Reaper", "Scout", "Snail", "Specialist", "Stomper", "Crate", "Switcher", "Tank", "Thor",
			"Timelord", "Turtle", "Urgal", "Viking", "Viper", "Worm");

	public Coal(Manager manager) {
		super(manager, "Carvão", CrateType.COAL, null, new ItemStack(Material.AIR));

		Reward reward = null;
		int random = getRandom().nextInt(2000);

		if (random < 400) {
			setRewardIcon(new ItemBuilder(Material.GOLD_NUGGET).setName("§a3.000 §7Coins").getStack());
			reward = new Reward(3000, RewardType.COINS);

		} else if (random >= 400 && random < 600) {
			setRewardIcon(new ItemBuilder(Material.GOLD_INGOT).setName("§a5.000 §7Coins").getStack());
			reward = new Reward(5000, RewardType.COINS);

		} else if (random >= 600 && random < 700) {

			setRewardIcon(new ItemBuilder(Material.GOLD_BLOCK).setName("§a10.000 §7Coins").getStack());
			reward = new Reward(10000, RewardType.COINS);

		} else if (random >= 700 && random < 1200) {

			if (getRandom().nextInt(100) <= 70) {

				String kit = pvpKits.get(getRandom().nextInt(pvpKits.size()));
				reward = new Reward(kit, 1, RewardType.KIT);

				setRewardIcon(new ItemBuilder(Material.DIAMOND_SWORD).setName("§a" + kit).getStack());

			} else {
				String kit = hgKits.get(getRandom().nextInt(hgKits.size()));
				reward = new Reward(kit, 2, RewardType.KIT);

				setRewardIcon(new ItemBuilder(Material.DIAMOND_SWORD).setName("§a" + kit).getStack());
			}

		} else {
			setRewardIcon(new ItemBuilder(Material.GOLD_NUGGET).setName("§a3.000 §7Coins").getStack());
			reward = new Reward(3000, RewardType.COINS);
		}

		setReward(reward);

	}

}
