package br.com.zenix.lobby.gamer;

import java.util.UUID;

import br.com.zenix.core.bukkit.player.account.Account;

public class Gamer {
	private final Account account;

	private UUID uniqueId;

	private boolean fly = false, scoreboard, show;

	public Gamer(Account account) {
		this.account = account;
		this.uniqueId = account.getUniqueId();
		this.fly = false;
		this.scoreboard = false;
		this.show = true;
	}

	public Account getAccount() {
		return account;
	}

	public boolean isScoreboard() {
		return scoreboard;
	}

	public UUID getUniqueId() {
		return uniqueId;
	}

	public boolean isFly() {
		return fly;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public void setFly(boolean fly) {
		this.fly = fly;
	}

}
