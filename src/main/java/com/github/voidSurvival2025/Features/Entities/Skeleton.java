package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Skeleton implements Listener {

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Skeleton skeleton)) return;

        EntityDamageEvent cause = skeleton.getLastDamageCause();

        if (cause == null) return;
        if (cause.getCause() != EntityDamageEvent.DamageCause.FIRE) return;

        Location location = skeleton.getLocation();
        Location below = location.clone().subtract(0, 1, 0);
        Material type = below.getBlock().getType();

        switch (type) {
            case BLACKSTONE:
            case BLACKSTONE_SLAB:
                location.getWorld().spawnEntity(location, EntityType.BLAZE);
                break;
            case SOUL_SOIL:
                location.getWorld().spawnEntity(location, EntityType.WITHER_SKELETON);
                break;
            default:
                return;
        }
        below.getBlock().setType(Material.AIR);
        event.getDrops().clear();
        below.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, below, 1);
        location.getWorld().playSound(location, Sound.ENTITY_WITHER_SPAWN, 0.7F, 1.0F);
    }
}
