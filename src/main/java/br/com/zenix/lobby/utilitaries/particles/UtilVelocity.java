package br.com.zenix.lobby.utilitaries.particles;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class UtilVelocity {

	public static Vector getBumpVector(Entity paramEntity, Location paramLocation, double paramDouble) {
		Vector localVector = paramEntity.getLocation().toVector().subtract(paramLocation.toVector()).normalize();
		localVector.multiply(paramDouble);
		return localVector;
	}

	public static Vector getPullVector(Entity paramEntity, Location paramLocation, double paramDouble) {
		Vector localVector = paramLocation.toVector().subtract(paramEntity.getLocation().toVector()).normalize();
		localVector.multiply(paramDouble);
		return localVector;
	}

	public static void bumpEntity(Entity paramEntity, Location paramLocation, double paramDouble) {
		paramEntity.setVelocity(getBumpVector(paramEntity, paramLocation, paramDouble));
	}

	public static void bumpEntity(Entity paramEntity, Location paramLocation, double paramDouble1, double paramDouble2) {
		Vector localVector = getBumpVector(paramEntity, paramLocation, paramDouble1);
		localVector.setY(paramDouble2);
		paramEntity.setVelocity(localVector);
	}

	public static void pullEntity(Entity paramEntity, Location paramLocation, double paramDouble) {
		paramEntity.setVelocity(getPullVector(paramEntity, paramLocation, paramDouble));
	}

	public static void pullEntity(Entity paramEntity, Location paramLocation, double paramDouble1, double paramDouble2) {
		Vector localVector = getPullVector(paramEntity, paramLocation, paramDouble1);
		localVector.setY(paramDouble2);
		paramEntity.setVelocity(localVector);
	}

	public static void velocity(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3) {
		velocity(paramEntity, paramEntity.getLocation().getDirection(), paramDouble1, false, 0.0D, paramDouble2, paramDouble3);
	}

	public static void velocity(Entity paramEntity, Vector paramVector, double paramDouble1, boolean paramBoolean, double paramDouble2, double paramDouble3, double paramDouble4) {
		if ((Double.isNaN(paramVector.getX())) || (Double.isNaN(paramVector.getY())) || (Double.isNaN(paramVector.getZ())) || (paramVector.length() == 0.0D)) {
			return;
		}
		if (paramBoolean) {
			paramVector.setY(paramDouble2);
		}
		paramVector.normalize();
		paramVector.multiply(paramDouble1);

		paramVector.setY(paramVector.getY() + paramDouble3);
		if (paramVector.getY() > paramDouble4) {
			paramVector.setY(paramDouble4);
		}
		paramEntity.setFallDistance(0.0F);
		paramEntity.setVelocity(paramVector);
	}

	public static final Vector rotateAroundAxisX(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getY() * d3 - paramVector.getZ() * d4;
		double d2 = paramVector.getY() * d4 + paramVector.getZ() * d3;
		return paramVector.setY(d1).setZ(d2);
	}

	public static final Vector rotateAroundAxisY(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getX() * d3 + paramVector.getZ() * d4;
		double d2 = paramVector.getX() * -d4 + paramVector.getZ() * d3;
		return paramVector.setX(d1).setZ(d2);
	}

	public static final Vector rotateAroundAxisZ(Vector paramVector, double paramDouble) {
		double d3 = Math.cos(paramDouble);
		double d4 = Math.sin(paramDouble);
		double d1 = paramVector.getX() * d3 - paramVector.getY() * d4;
		double d2 = paramVector.getX() * d4 + paramVector.getY() * d3;
		return paramVector.setX(d1).setY(d2);
	}

	public static final Vector rotateVector(Vector paramVector, double paramDouble1, double paramDouble2, double paramDouble3) {
		rotateAroundAxisX(paramVector, paramDouble1);
		rotateAroundAxisY(paramVector, paramDouble2);
		rotateAroundAxisZ(paramVector, paramDouble3);
		return paramVector;
	}

	public static final double angleToXAxis(Vector paramVector) {
		return Math.atan2(paramVector.getX(), paramVector.getY());
	}

	public static void velocity(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean) {
		velocity(paramEntity, paramEntity.getLocation().getDirection(), paramDouble1, false, 0.0D, paramDouble2, paramDouble3, paramBoolean);
	}

	public static void velocity(Entity paramEntity, Vector paramVector, double paramDouble1, boolean paramBoolean1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean2) {
		if ((Double.isNaN(paramVector.getX())) || (Double.isNaN(paramVector.getY())) || (Double.isNaN(paramVector.getZ())) || (paramVector.length() == 0.0D)) {
			return;
		}
		if (paramBoolean1) {
			paramVector.setY(paramDouble2);
		}
		paramVector.normalize();
		paramVector.multiply(paramDouble1);

		paramVector.setY(paramVector.getY() + paramDouble3);
		if (paramVector.getY() > paramDouble4) {
			paramVector.setY(paramDouble4);
		}
		if (paramBoolean2) {
			paramVector.setY(paramVector.getY() + 0.2D);
		}
		paramEntity.setFallDistance(0.0F);
		paramEntity.setVelocity(paramVector);
	}

}
