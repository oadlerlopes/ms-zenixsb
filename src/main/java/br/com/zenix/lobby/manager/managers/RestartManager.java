package br.com.zenix.lobby.manager.managers;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;

public class RestartManager extends Management {

	private int time;

	public RestartManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {

		Random random = new Random();
		int rnd = random.nextInt(600);

		time = 9600 + rnd;
		start();
		return true;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void start() {
		new BukkitRunnable() {
			public void run() {
				if (time == 0) {
					Bukkit.shutdown();
					cancel();
					return;
				}

				time--;
			}
		}.runTaskTimer(getManager().getPlugin(), 0L, 20L);
	}

}
