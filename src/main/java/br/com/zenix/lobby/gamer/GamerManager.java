package br.com.zenix.lobby.gamer;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.PlayerConnection;

/**
 * Copyright (C) Zenix, all rights reserved unauthorized copying of this file,
 * via any medium is strictly prohibited proprietary and confidential
 */

public class GamerManager extends Management {

	private HashMap<UUID, Gamer> gamers = new HashMap<UUID, Gamer>();

	public GamerManager(Manager manager) {
		super(manager, "Gamer");
	}

	public boolean initialize() {
		return true;
	}

	public Gamer addGamer(Gamer gamer) {
		gamers.put(gamer.getUniqueId(), gamer);
		return gamer;
	}

	public void removeGamer(UUID uniqueId) {
		gamers.remove(uniqueId);
	}

	public Gamer getGamer(UUID uniqueId) {
		return gamers.get(uniqueId);
	}

	public Gamer getGamer(Player player) {
		return gamers.get(player.getUniqueId());
	}

	public HashMap<UUID, Gamer> getGamers() {
		return gamers;
	}

	public void updateTab(Gamer gamer) {
		if (gamer == null)
			return;
		
		StringBuilder headerBuilder = new StringBuilder();
		headerBuilder.append(" \n ");
		headerBuilder.append("§6§lZENIX");
		headerBuilder.append(" \n ");
		StringBuilder footerBuilder = new StringBuilder();
		footerBuilder.append(" \n ");
		footerBuilder.append("§bSite: §fwww.zenix.cc");
		footerBuilder.append(" \n ");
		footerBuilder.append("§bDiscord: §fwww.zenix.cc/discord");
		footerBuilder.append(" \n ");
		footerBuilder.append("§bLoja: §floja.zenix.cc");
		footerBuilder.append(" \n ");

		updateTab(gamer.getAccount().getPlayer(), headerBuilder.toString(), footerBuilder.toString());
	}

	public void updateTab(Player player, String up, String down) {
		if (((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion() >= 46) {
			PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
			connection.sendPacket(new ProtocolInjector.PacketTabHeader(ChatSerializer.a("{'text': '" + up + "'}"),
					ChatSerializer.a("{'text': '" + down + "'}")));
		}
	}

}
