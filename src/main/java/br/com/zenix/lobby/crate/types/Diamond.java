package br.com.zenix.lobby.crate.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.lobby.crate.constructors.Crate;
import br.com.zenix.lobby.crate.constructors.Reward;
import br.com.zenix.lobby.crate.constructors.Reward.RewardType;
import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.manager.Manager;

public class Diamond extends Crate {

	public Diamond(Manager manager) {
		super(manager, "Diamond", CrateType.DIAMOND, null, new ItemStack(Material.AIR));

		Reward reward = null;
		int random = getRandom().nextInt(1000);

		if (random < 400) {
			
			setRewardIcon(new ItemBuilder(Material.GOLD_NUGGET).setName("§a15.000 §7Coins").getStack());
			reward = new Reward(15000, RewardType.COINS);

		} else if (random >= 400 && random < 600) {
			
			setRewardIcon(new ItemBuilder(Material.GOLD_INGOT).setName("§a20.000 §7Coins").getStack());
			reward = new Reward(30000, RewardType.COINS);

		} else if (random >= 600 && random < 700) {

			setRewardIcon(new ItemBuilder(Material.GOLD_BLOCK).setName("§a40.000 §7Coins").getStack());
			reward = new Reward(40000, RewardType.COINS);

		} else if (random >= 700 && random < 800) {

			setRewardIcon(new ItemBuilder(Material.EXP_BOTTLE).setName("§e10 §7Double XP").getStack());
			reward = new Reward(10, RewardType.DOUBLE);

		} else if (random >= 800 && random < 900) {

			setRewardIcon(new ItemBuilder(Material.EXP_BOTTLE).setName("§e15 §7Double XP").getStack());
			reward = new Reward(15, RewardType.DOUBLE);

		} else if (random >= 900 && random < 990) {

			setRewardIcon(new ItemBuilder(Material.EXP_BOTTLE).setName("§c20 §7Double XP").getStack());
			reward = new Reward(20, RewardType.DOUBLE);

		} else if (random >= 990) {

			setRewardIcon(new ItemBuilder(Material.NETHER_STAR).setName("§d1§7 VIP ULTIMATE").getStack());
			reward = new Reward(3, RewardType.VIP);

		}

		setReward(reward);
	}

}
