package com.tensortime.redimension;

import com.blamejared.mtlib.helpers.LogHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod(modid = "redimection", name = "ReDIMEction", version = "0.1", dependencies = "required-after:crafttweaker;required-after:mtlib", certificateFingerprint = "@FINGERPRINT@")
public class Redimension {

	public static final Map<Integer, List<Integer>> DESTINATION_MAP = new HashMap<>();
	public static final Map<Integer, Integer> SOURCE_MAP = new HashMap<>();

	private static int newDestination = 0;
	private static boolean redirectEventFlag = false;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (redirectEventFlag) {
			LogHelper.logInfo("Clearing state and redirecting player in dimension " + event.player.dimension
					+ " to dimension " + newDestination);

			redirectEventFlag = false;
			event.player.changeDimension(newDestination);
			newDestination = 0;
		}
	}

	@SubscribeEvent
	public void onDimChange(EntityTravelToDimensionEvent event) {

		final Entity entity = event.getEntity();

		if (entity instanceof EntityPlayer) {

			attemptTravel(entity, event);

		} else if (entity instanceof EntityThrowable) {

			final EntityThrowable throwable = (EntityThrowable) event.getEntity();
			attemptTravel(throwable.getThrower(), event);

		} else if (entity instanceof EntityItem) {

			final EntityItem item = (EntityItem) event.getEntity();
			if (item.getThrower() != null && !item.getThrower().isEmpty()) {
				attemptTravel(entity.getEntityWorld().getPlayerEntityByName(item.getThrower()), event);
			}

		} else if (entity instanceof EntityArrow) {

			final EntityArrow arrow = (EntityArrow) event.getEntity();
			attemptTravel(arrow.shootingEntity, event);

		}
	}

	private static boolean isValid(Entity entity, EntityTravelToDimensionEvent event) {
		return true;
	}

	private static void attemptTravel(Entity entity, EntityTravelToDimensionEvent event) {

		Integer destination = event.getDimension();
		final List<Integer> allowedSources = DESTINATION_MAP.get(destination);

		if (entity instanceof EntityPlayer) {

			if ((allowedSources != null) && !(allowedSources.contains(entity.dimension))) {
				event.setCanceled(true);
				LogHelper.logInfo("Blocked dimensional travel to dimension " + destination + " from source dimension "
						+ entity.dimension);
			}

			if (SOURCE_MAP.containsKey(entity.dimension)) {
				newDestination = SOURCE_MAP.get(entity.dimension);

				if (newDestination != destination) {
					LogHelper.logInfo("Blocked dimensional travel based upon redirection rule");
					event.setCanceled(true);

					LogHelper.logInfo("Setting State to redirect player in dimension " + entity.dimension
							+ " to dimension " + newDestination);
					redirectEventFlag = true;

				}
			}
		}
	}

}