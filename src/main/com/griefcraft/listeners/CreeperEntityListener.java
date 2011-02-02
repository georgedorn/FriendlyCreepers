package com.griefcraft.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

import com.griefcraft.FriendlyCreepers;

public class CreeperEntityListener extends EntityListener {

	/**
	 * The parent object
	 */
	private FriendlyCreepers parent;
	
	/**
	 * The creeper's class
	 */
	public static final String CREEPER_CLASS = "CraftCreeper";
	
	public CreeperEntityListener(FriendlyCreepers parent) {
		this.parent = parent;
	}
	
	@Override
	public void onEntityExplode(EntityExplodeEvent event) {
		if(event.isCancelled()) {
			return;
		}
		
		Entity entity = event.getEntity();
		boolean shouldExplode = true;
		double alt = event.getLocation().getY();		
		if(entity == null) {
			/*
			 * It's TNT!
			 */

			shouldExplode = Boolean.parseBoolean(parent.getProperties().getProperty("enable-tnt", "false"));
			if(shouldExplode){
				Integer max_alt = Integer.parseInt(parent.getProperties().getProperty("tnt-max-altitude","128"));
				if (alt > max_alt){
					shouldExplode = false;
				}
			}
			if(shouldExplode){
				Integer min_alt = Integer.parseInt(parent.getProperties().getProperty("tnt-min-altitude","0"));
				if (alt < min_alt){
					shouldExplode = false;
				}	
			}
		} else if(entity.getClass().getSimpleName().equals(CREEPER_CLASS)) {
			/*
			 * Foul creeper!
			 */
			
			shouldExplode = Boolean.parseBoolean(parent.getProperties().getProperty("enable-creepers", "false"));
			if(shouldExplode){
				Integer max_alt = Integer.parseInt(parent.getProperties().getProperty("creeper-max-altitude","128"));
				if (alt > max_alt){
					shouldExplode = false;
				}
			}
			if(shouldExplode){
				Integer min_alt = Integer.parseInt(parent.getProperties().getProperty("creeper-min-altitude","0"));
				if (alt < min_alt){
					shouldExplode = false;
				}	
			
			}
		}
		
		/*
		 * Cancel it if the config option for the explosion allows us to!
		 */
		if(!shouldExplode) {
			event.setCancelled(true);
		}
	}
	
}
