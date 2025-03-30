package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.Manager.SpawnPointManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlayerDeath implements Listener {

    @EventHandler
    public void lifesteal(PlayerDeathEvent event) {

        Player victim = event.getPlayer();
        EntityDamageEvent cause = victim.getLastDamageCause();

        if (cause == null) return;

        DamageSource source = cause.getDamageSource();

        if (source.getCausingEntity() == null) return;

        Entity attacker = source.getCausingEntity();
        if ((!(attacker instanceof Player))) return;

        if (attacker.equals(victim)) return;


        double health = Objects.requireNonNull(
                victim.getAttribute(Attribute.MAX_HEALTH)).getValue();

        if (health <= 1.0) {
            Objects.requireNonNull(victim.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(21.0);
            return;
        }

        health = health - 1.0;
        Objects.requireNonNull(victim.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(health);
        victim.setHealthScale(health);
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        event.deathMessage();
        event.deathMessage(Component.text(""));

        Player victim = event.getPlayer();
        EntityDamageEvent cause = victim.getLastDamageCause();

        String reason = cause == null ? "unknown causes" : switch (cause.getCause()) {
            case FALL -> "fall damage";
            case FIRE -> "fire";
            case LAVA -> "lava";
            case VOID -> "falling into the abyss";
            case BLOCK_EXPLOSION -> "an explosion";
            case KILL, CONTACT, ENTITY_ATTACK, ENTITY_SWEEP_ATTACK, ENTITY_EXPLOSION, PROJECTILE -> {
                Entity attacker = cause.getDamageSource().getCausingEntity(); // ignore

                if (attacker == null) {
                    yield "an unknown entity";
                }

                if (attacker.getType() == EntityType.PLAYER) {
                    yield "a " + attacker.getName().toLowerCase();
                }

                if (attacker instanceof Projectile projectile) {
                    if (projectile.getShooter() instanceof Entity entity) {
                        yield "a " + entity.getType().name().toLowerCase();
                    }
                    yield "an unknown shooter";
                }
                yield attacker.getType().name();
            }
            default -> "unknown causes";
        };

        reason = reason.toLowerCase().replaceAll("_", " ").trim();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(MiniMessage.miniMessage().deserialize(
                    "<gradient:#84D0FC:#ABDDFA:#84D0FC>" + victim.getName() + " has died by " + reason
            ));
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (!event.isBedSpawn()) {
            event.setRespawnLocation(SpawnPointManager.getWorldSpawn());
        }

        Location respawn = event.getRespawnLocation();

        player.setVelocity(new Vector());
        player.teleportAsync(respawn).thenRun(new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5, 1, false, false, false));
            }
        });
    }
}
