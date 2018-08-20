package com.tensortime.redimection;

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

@Mod(modid = "redimection", name = "ReDIMEction", version = "@VERSION@", dependencies = "required-after:crafttweaker", certificateFingerprint = "@FINGERPRINT@")
public class ReDIMEction {

	public static final Map<Integer, List<Integer>> DESTINATION_MAP = new HashMap<>();
	public static final Map<Integer, Integer> SOURCE_MAP = new HashMap<>();

	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onDimChange(EntityTravelToDimensionEvent event) {

		final Entity entity = event.getEntity();

		if (entity instanceof EntityPlayer) {
			//	attemptEntry(event.getEntity(), destination, event);
			//
		
		} else if (entity instanceof EntityThrowable) {
		
			final EntityThrowable throwable = (EntityThrowable) event.getEntity();
			attemptTravel(throwable.getThrower(), event);
		
		} else if (entity  instanceof EntityItem) {
			final EntityItem item = (EntityItem) event.getEntity();
			if (item.getThrower() != null && !item.getThrower().isEmpty()) {
				attemptTravel(entity.getEntityWorld().getPlayerEntityByName(item.getThrower()),
						event);
			}
		} else if (entity instanceof EntityArrow) {
			final EntityArrow arrow = (EntityArrow) event.getEntity();
			attemptTravel(arrow.shootingEntity, event);
		}
	}

	private static boolean isValid (Entity entity, EntityTravelToDimensionEvent event) {
		return true;
	}
	
	private static void attemptTravel(Entity entity, EntityTravelToDimensionEvent event) {
		
		Integer destination = event.getDimension();
		final List<Integer> allowedSources = DESTINATION_MAP.get(destination);
		
		
        if (entity instanceof EntityPlayer) {
        	if (!(allowedSources.contains(entity.dimension))) {
        		event.setCanceled(true);
        	}
        
        	
        	if (SOURCE_MAP.containsKey(entity.dimension)) {
        		event.setCanceled(true);
        		EntityTravelToDimensionEvent newEvent = new EntityTravelToDimensionEvent(entity, SOURCE_MAP.get(entity.dimension));
        		MinecraftForge.EVENT_BUS.post(newEvent);
        	}
        }
	}

}