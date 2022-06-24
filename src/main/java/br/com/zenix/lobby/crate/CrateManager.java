package br.com.zenix.lobby.crate;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.zenix.core.bukkit.integration.npc.NPC;
import br.com.zenix.core.bukkit.integration.npc.NPCProfile;
import br.com.zenix.core.bukkit.player.account.Account;
import br.com.zenix.core.bukkit.player.hologram.Hologram;
import br.com.zenix.core.bukkit.player.item.ItemBuilder;
import br.com.zenix.core.bukkit.player.particle.ParticleEffect;
import br.com.zenix.core.master.data.handler.DataHandler;
import br.com.zenix.core.master.data.handler.type.DataType;
import br.com.zenix.lobby.crate.constructors.Crate;
import br.com.zenix.lobby.crate.constructors.Reward;
import br.com.zenix.lobby.crate.constructors.Reward.RewardType;
import br.com.zenix.lobby.crate.type.CrateType;
import br.com.zenix.lobby.crate.types.Coal;
import br.com.zenix.lobby.crate.types.Diamond;
import br.com.zenix.lobby.crate.types.Gold;
import br.com.zenix.lobby.crate.types.Silver;
import br.com.zenix.lobby.manager.Manager;
import br.com.zenix.lobby.manager.constructor.Management;
import br.com.zenix.lobby.utilitaries.particles.UtilVelocity;

public class CrateManager extends Management {

	public Hologram coalHologram, silverHologram, goldHologram, diamondHologram;
	//public Hologram pvpHologram;

	public CrateManager(Manager manager) {
		super(manager);
	}

	public NPC npc1;
	public NPCProfile npcprofile;

	public boolean initialize() {

		for (int x = -250; x < 250; x++) {
			for (int z = -250; z < 250; z++) {
				Chunk chunk = Bukkit.getWorld("world").getBlockAt(x, 64, z).getChunk();
				if (chunk.isLoaded()) {
					continue;
				}
				chunk.load(true);
			}
		}

		for (Entity ent : Bukkit.getWorld("world").getEntities()) {
			ent.remove();
		}

		coalHologram = new Hologram("§8Caixa de Carvão", new Location(Bukkit.getWorld("world"), -106.47, 164.87, -182.44),
				true);
		coalHologram.addLine("§aClique no baú para abrir.");

		silverHologram = new Hologram("§fCaixa de Prata", new Location(Bukkit.getWorld("world"), -106.57, 164.87, -176.55),
				true);
		silverHologram.addLine("§aClique no baú para abrir.");

		goldHologram = new Hologram("§6Caixa de Ouro", new Location(Bukkit.getWorld("world"), -107.54, 164.87, -184.44),
				true);
		goldHologram.addLine("§aClique no baú para abrir.");

		diamondHologram = new Hologram("§bCaixa de Diamante",
				new Location(Bukkit.getWorld("world"), -107.54, 164.87, -174.44), true);
		diamondHologram.addLine("§aClique no baú para abrir.");

		//pvpHologram = new Hologram("§bPvP", new Location(Bukkit.getWorld("world"), 54, 63, 0), true);
		//pvpHologram.addLine("§e" + getManager().getServerManager().getServerPlayersPath("PVP") + " jogando agora");

		executeSpiral(diamondHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.WITCH_MAGIC, 0.3F);
		executeSpiral(diamondHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FLAME, 0.7F);
		executeSpiral(diamondHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FIREWORKS_SPARK, 0.7F);

		executeSpiral(goldHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.WITCH_MAGIC, 0.3F);
		executeSpiral(goldHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FLAME, 0.7F);
		executeSpiral(goldHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FIREWORKS_SPARK, 0.7F);

		//	final NPCFactory factory = new NPCFactory();
		//	final Location location = new Location(Bukkit.getWorld("world"), 54, 62, 0);
		//	new Thread() {
		//
		//		@Override
		//		public void run() {
		//			npcprofile = NPCProfile.loadProfile("", "Huffass");
		//			Bukkit.getScheduler().runTask(getManager().getPlugin(), new Runnable() {
		//
		//			@Override
		//			public void run() {
		//				npc1 = factory.spawnHumanNPC(location, npcprofile);
		//				npc1.setEquipment(EquipmentSlot.HAND, new ItemStack(Material.DIAMOND_SWORD));
		//				npc1.setYaw(270.0f);
		//				npc1.setEntityCollision(false);
						//				npc1.getBukkitEntity().setCustomName("§7[npc-pvp]");
						//				npc1.getBukkitEntity().setCustomNameVisible(false);
		//			}
		//		});
		//	}
		//}.start();
		return true;
	}

	//public NPC getNpc1() {
	//	return npc1;
	//}
	
	//public NPCProfile getNpcprofile() {
	//	return npcprofile;
	//}

	//public Hologram getPvpHologram() {
	//	return pvpHologram;
	//}

	//public boolean setHolograms() {
	//
	//	if (pvpHologram != null) {
	//		pvpHologram.remove();
	//		pvpHologram = null;
	//	}
	//
	//	pvpHologram = new Hologram("§bPvP", new Location(Bukkit.getWorld("world"), 54, 63, 0), true);
	//	pvpHologram.addLine("§e" + getManager().getServerManager().getServerPlayersPath("PVP") + " jogando agora");
	//
	//	for (player player : Bukkit.getOnlinePlayers()) {
	//		pvpHologram.hide(player);
	//		pvpHologram.show(player);
	//	}
	//
	//	return true;

	//}

	public boolean generate(CrateType crate, Account account) {
		DataHandler dataHandler = account.getDataHandler();
		if (dataHandler.getValue(crate.getBoxData()).getValue() < 1) {
			account.getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma caixa do tipo §e§l" + crate.getColor()
					+ crate.getName() + "§f.");
			return false;
		} else if (dataHandler.getValue(crate.getKeyData()).getValue() < 1) {
			account.getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma chave do tipo §e§l" + crate.getColor()
					+ crate.getName() + "§f.");
			return false;
		}

		dataHandler.setValue(crate.getBoxData(), dataHandler.getValue(crate.getBoxData()).getValue() - 1);
		dataHandler.setValue(crate.getKeyData(), dataHandler.getValue(crate.getKeyData()).getValue() - 1);

		dataHandler.update(DataType.CRATE_COAL);
		dataHandler.update(DataType.KEY);

		createInventory(account.getPlayer(), crate);
		return true;
	}

	public void createInventory(Player player, CrateType crateType) {
		Inventory inventory = Bukkit.createInventory(player, 18, "Caixa de " + crateType.getName());

		for (int i = 0; i <= 17; i++) {
			if (inventory.getItem(i) != null)
				continue;
			new ItemBuilder(Material.STAINED_GLASS_PANE).setName("§a").setBreakable().setDurability(15).build(inventory,
					i);
		}

		new ItemBuilder(Material.HOPPER).setName("§3Prêmio").setBreakable().build(inventory, 4);

		createAnimation(player, inventory, crateType);
		player.openInventory(inventory);
	}

	private void givePrize(Player player, Crate crate) {
		Account account = getManager().getCoreManager().getAccountManager().getAccount(player);

		DataHandler dataHandler = account.getDataHandler();
		Reward reward = crate.getReward();

		if (reward.getType() == RewardType.COINS) {
			dataHandler.setValue(DataType.GLOBAL_COINS,
					dataHandler.getValue(DataType.GLOBAL_COINS).getValue() + reward.getAmmount());
			dataHandler.update(DataType.GLOBAL_COINS);

			account.sendMessage("§e§lCAIXA §fVocê ganhou §a" + reward.getAmmount() + " coins §fna caixa §b"
					+ crate.getName() + "§f.");
		} else if (reward.getType() == RewardType.DOUBLE) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"givedxp " + account.getUniqueId() + " " + reward.getAmmount());

			account.sendMessage("§e§lCAIXA §fVocê ganhou §a" + reward.getAmmount() + " double xp §fna caixa §b"
					+ crate.getName() + "§f.");
		} else if (reward.getType() == RewardType.VIP) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givevip " + account.getUniqueId() + " ultimate "
					+ (reward.getAmmount() == 1 ? "7d" : reward.getAmmount() == 2 ? "30d" : "3y"));
			Bukkit.broadcastMessage(
					"§e§lCAIXAS §fO player " + account.getNickname() + " acabou de ganhar um §d§lULTIMATE "
							+ (reward.getAmmount() == 1 ? "de 7 dias"
									: reward.getAmmount() == 2 ? "§a§lde 30 dias" : "§a§lETERNO")
							+ "§f na caixa de " + crate.getName());

			account.sendMessage("§e§lCAIXA §fVocê ganhou §a"
					+ (reward.getAmmount() == 1 ? "7 dias" : reward.getAmmount() == 2 ? "1 mes" : "a eternidade")
					+ " de vip ultimate§f na caixa §b" + crate.getName() + "§f.");
		} else if (reward.getType() == RewardType.KIT) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"permission add " + account.getPlayer().getName() + " "
							+ (reward.getAmmount() == 1 ? "pvp.kit." : "hgkit.") + reward.getReward().replace("kit", "")
							+ " 90d");

			account.sendMessage("§e§lCAIXA §fVocê ganhou o kit §a" + reward.getReward() + " no "
					+ (reward.getAmmount() == 1 ? "pvp" : "hg") + "§f na caixa §b" + crate.getName() + "§f.");
		}

	}

	private void createAnimation(Player player, Inventory inventory, CrateType boxType) {
		AtomicInteger startIndex = new AtomicInteger(0);
		Crate[] boxes = new Crate[50];

		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = createBoxByType(boxType);
		}

		new Thread(new Runnable() {
			int time = 0;
			Crate winCrate;

			@Override
			public void run() {
				while (startIndex.get() < boxes.length - 1) {
					startIndex.incrementAndGet();
					int currentIndex = 0;
					for (int i = startIndex.get(); i <= startIndex.get() + 6; i++) {
						if (i >= boxes.length - 1) {
							break;
						}
						inventory.setItem(10 + currentIndex, boxes[i].getRewardIcon());
						player.playSound(player.getLocation(), Sound.CLICK, 5, 5);

						if (currentIndex == 3)
							winCrate = boxes[i];

						currentIndex++;
					}

					if (!inventory.getViewers().contains(player)) {
						player.openInventory(inventory);
					}

					if (startIndex.get() >= boxes.length - 1) {
						player.closeInventory();
					}

					if (time * 5 >= 500) {
						time += 500;
						givePrize(player, winCrate);
						break;
					} else {
						time += 5;
					}
					try {
						Thread.sleep(time * 5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private Crate createBoxByType(CrateType crateType) {
		if (crateType == CrateType.COAL)
			return new Coal(getManager());
		if (crateType == CrateType.SILVER)
			return new Silver(getManager());
		if (crateType == CrateType.GOLD)
			return new Gold(getManager());
		if (crateType == CrateType.DIAMOND)
			return new Diamond(getManager());
		return null;
	}

	public void executeSpiral(Location crateLocation, ParticleEffect effect, float radius) {
		new BukkitRunnable() {
			double xRotation, yRotation, zRotation = 5.0D;
			double angularVelocityX = 0.01570796326794897D, angularVelocityY = 0.01847995678582231D,
					angularVelocityZ = 0.0202683397005793D;
			float step = 0.0F;
			double xSubtract, ySubtract, zSubtract;
			boolean enableRotation = true;
			int particles = 20;

			public void run() {
				Location location = crateLocation.clone();
				location.add(0.0D, 1.0D, 0.0D);
				location.subtract(xSubtract, ySubtract, zSubtract);
				double inc = 6.283185307179586D / particles;
				double angle = step * inc;
				Vector v = new Vector();
				v.setX(Math.cos(angle) * radius);
				v.setZ(Math.sin(angle) * radius);
				UtilVelocity.rotateVector(v, xRotation, yRotation, zRotation);
				if (this.enableRotation) {
					UtilVelocity.rotateVector(v, angularVelocityX * step, angularVelocityY * step,
							angularVelocityZ * step);
				}

				effect.setParticle(location.add(v), 0, 0, 0, 0, 1);

				step += 1.0F;
			}
		}.runTaskTimer(getManager().getPlugin(), 0, 1);
	}

	public void executeOpsetSpiral(Location crateLocation, ParticleEffect effect, float radius) {
		new BukkitRunnable() {
			double xRotation, yRotation, zRotation = 5.0D;
			double angularVelocityX = 0.01570796326794897D, angularVelocityY = 0.01847995678582231D,
					angularVelocityZ = 0.0202683397005793D;
			double xSubtract, ySubtract, zSubtract;

			float step = 0.0F;
			boolean enableRotation = true;
			int particles = 20;

			public void run() {
				Location location = crateLocation.clone();
				location.add(0.0D, 1.0D, 0.0D);
				location.subtract(xSubtract, ySubtract, zSubtract);
				double inc = 6.283185307179586D / particles;
				double angle = step * inc;
				Vector v = new Vector();
				v.setX(Math.cos(angle) * radius);
				v.setZ(Math.sin(angle) * radius);
				UtilVelocity.rotateVector(v, xRotation, yRotation, zRotation);
				if (this.enableRotation) {
					UtilVelocity.rotateVector(v, angularVelocityX * step, angularVelocityY * step,
							angularVelocityZ * step);
				}

				effect.setParticle(location.add(v), 0, 0, 0, 0, 1);

				step -= 1.0F;
			}
		}.runTaskTimer(getManager().getPlugin(), 0, 1);
	}

}
