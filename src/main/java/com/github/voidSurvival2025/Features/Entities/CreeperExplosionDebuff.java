package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class CreeperExplosionDebuff implements Listener {

    @EventHandler
    public void creeperIgnite(ExplosionPrimeEvent event) {

        if (event.getEntityType() != EntityType.CREEPER) return;

        event.setRadius(2);

    }
}
