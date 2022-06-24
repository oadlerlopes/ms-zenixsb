package br.com.zenix.lobby.crate.constructors;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.manager.Manager;

public class Crate {

	private Manager manager;
	private String name;
	private CrateType boxType;
	private Reward reward;
	private ItemStack rewardIcon;
	private Random random;

	public Crate(Manager manager, String name, CrateType boxType, Reward reward, ItemStack rewardIcon) {
		this.manager = manager;
		this.name = name;
		this.boxType = boxType;
		this.reward = reward;
		this.rewardIcon = rewardIcon;
		this.random = new Random();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CrateType getType() {
		return boxType;
	}

	public void setType(CrateType boxType) {
		this.boxType = boxType;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public ItemStack getRewardIcon() {
		return rewardIcon;
	}

	public void setRewardIcon(ItemStack rewardIcon) {
		this.rewardIcon = rewardIcon;
	}

	public Random getRandom() {
		return random;
	}

}
