package br.com.zenix.lobby.crate.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.lobby.crate.constructors.Crate;
import br.com.zenix.lobby.crate.constructors.Reward;
import br.com.zenix.lobby.crate.constructors.Reward.RewardType;
import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.manager.Manager;

public class Silver extends Crate {

	public Silver(Manager manager) {
		super(manager, "Prata", CrateType.SILVER, null, new ItemStack(Material.AIR));

		Reward reward = null;
		int random = getRandom().nextInt(1200);

		if (random < 400) {
			setRewardIcon(new ItemBuilder(Material.GOLD_NUGGET).setName("§a5.000 §7Coins").getStack());
			reward = new Reward(5000, RewardType.COINS);

		} else if (random >= 400 && random < 600) {
			setRewardIcon(new ItemBuilder(Material.GOLD_INGOT).setName("§a10.000 §7Coins").getStack());
			reward = new Reward(10000, RewardType.COINS);

		} else if (random >= 600 && random < 700) {
			setRewardIcon(new ItemBuilder(Material.GOLD_BLOCK).setName("§a20.000 §7Coins").getStack());
			reward = new Reward(20000, RewardType.COINS);

		} else if (random >= 700 && random < 800) {
			setRewardIcon(new ItemBuilder(Material.EXP_BOTTLE).setName("§e1 §7Double XP").getStack());
			reward = new Reward(1, RewardType.DOUBLE);

		} else if (random >= 800 && random < 900) {
			setRewardIcon(new ItemBuilder(Material.EXP_BOTTLE).setName("§e3 §7Double XP").getStack());
			reward = new Reward(3, RewardType.DOUBLE);

		} else if (random >= 900 && random < 990) {
			setRewardIcon(new ItemBuilder(Material.EXP_BOTTLE).setName("§c5 §7Double XP").getStack());
			reward = new Reward(5, RewardType.DOUBLE);

		} else if (random >= 1000) {
			setRewardIcon(new ItemBuilder(Material.NETHER_STAR).setName("§d1§7 VIP ULTIMATE").getStack());
			reward = new Reward(1, RewardType.VIP);

		}

		setReward(reward);

	}

}
