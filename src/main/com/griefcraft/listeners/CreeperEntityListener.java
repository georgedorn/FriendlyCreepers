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
		
		if(entity == null) {
			/*
			 * It's TNT!
			 */

			shouldExplode = Boolean.parseBoolean(parent.getProperties().getProperty("enable-tnt", "false"));
		} else if(entity.getClass().getSimpleName().equals(CREEPER_CLASS)) {
			/*
			 * Fowl creeper!
			 */
			
			shouldExplode = Boolean.parseBoolean(parent.getProperties().getProperty("enable-creepers", "false"));
		}
		
		/*
		 * Cancel it if the config option for the explosion allows us to!
		 */
		if(!shouldExplode) {
			event.setCancelled(true);
		}
	}
	
}
