package br.com.zenix.lobby.utilitaries.nms;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import br.com.zenix.core.master.utilitaries.Utils;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityTypes;

@SuppressWarnings("deprecation")
public enum CustomEntities {

	BODY("Body", EntityType.PLAYER.getTypeId(), CustomBody.class),
	CUSTOM_ITEM("CustomItem", EntityType.DROPPED_ITEM.getTypeId(), CustomItemEntity.class);

	public static void spawnEntity(Entity entity, Location loc) {
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		entity.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(entity, SpawnReason.SPAWNER);
	}

	CustomEntities(String name, int id, Class<? extends Entity> custom) {
		addToMaps(custom, name, id);
	}

	@SuppressWarnings("unchecked")
	private static void addToMaps(Class<? extends Entity> clazz, String name, int id) {
		((Map<String, Class<? extends Entity>>) Utils.getValue("c", EntityTypes.class, null)).put(name, clazz);
		((Map<Class<? extends Entity>, String>) Utils.getValue("d", EntityTypes.class, null)).put(clazz, name);
		((Map<Class<? extends Entity>, Integer>) Utils.getValue("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
	}

}
