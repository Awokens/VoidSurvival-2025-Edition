package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.VoidSurvival2025;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

import java.util.List;

public class PlayerChat implements Listener {

    private final String pattern = "\\[(item|inv|ec|levels)]";
    private final String itemPattern = "\\[item]";
    private final String invPattern = "\\[inv]";
    private final String ecPattern = "\\[ec]";
    private final String levelPattern = "\\[levels]";

    public String[] BANNED_PHRASES = {
            "join 10",
            "join ten",
            "nigger",
            "nigga",
            "fagget",
            "fag",
            "figget"
    };

    private final VoidSurvival2025 plugin;
    public PlayerChat(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chat(AsyncChatEvent event) {

        if (event.isCancelled()) return;

        event.setCancelled(true);

        String message = event.signedMessage().message().trim();
        Player player = event.getPlayer();

        for (String bannedPhrase : BANNED_PHRASES) {
            if (message.toLowerCase().contains(bannedPhrase)) {
                player.kick(MiniMessage.miniMessage().deserialize("""
                        Nuh uh
                        """), PlayerKickEvent.Cause.ILLEGAL_ACTION);
                return;
            }
        }

        if (message.matches(pattern)) {
            if (message.matches(invPattern)) {
                inv(player);
                return;
            }
            if (message.matches(ecPattern)) {
                ec(player);
                return;
            }
            if (message.matches(levelPattern)) {
                Bukkit.broadcast(levels(player));
                return;
            }
            if (message.matches(itemPattern)) {
                Bukkit.broadcast(item(player));
                return;
            }
        }

        Bukkit.broadcast(MiniMessage.miniMessage().deserialize( plugin.luckPermsUtils().getPlayerRole(player) + player.getName()
                + ": <white>" + message.trim()));
    }

    public Component item(Player player) {

        ItemStack item = player.getInventory().getItemInMainHand();

        // fancy i know
        int amount = player.getInventory().all(item.getType())
                .values()
                .stream()
                .mapToInt(ItemStack::getAmount)
                .sum();

        String name = item.getType().name().replaceAll("_", " ").toLowerCase();
        HoverEvent<HoverEvent.ShowItem> hover = Bukkit.getItemFactory().asHoverEvent(item,
                showItem -> showItem);


        return MiniMessage.miniMessage().deserialize(
                plugin.luckPermsUtils().getPlayerRole(player)  + player.getName() + ": <gray>[<aqua>" + amount +
                        "x of " + name +
                        "</aqua>]</gray>"
        ).hoverEvent(hover);

    }

    public void inv(Player player) {

        ItemStack[] items = player.getInventory().getContents();

        ItemStack bundle = new ItemStack(Material.BUNDLE);


        BundleMeta meta = (BundleMeta) bundle.getItemMeta();

        meta.setMaxStackSize(99);


        SGMenu snapshot = plugin.spiGUI().create("&8Inventory Snapshot", 5);
        for (ItemStack item : items) {
            if (item == null || item.isEmpty()) continue;
            meta.addItem(item);
            snapshot.addButton(new SGButton(item));
        }

        final Inventory inventory = snapshot.getInventory();

        meta.displayName(MiniMessage.miniMessage().deserialize(
                "<yellow>" + player.getName() + "'s Inventory Snapshot"
        ).decoration(TextDecoration.ITALIC, false));

        meta.lore(List.of(
                MiniMessage.miniMessage().deserialize("<white>Click me to preview full snapshot")
                        .decoration(TextDecoration.ITALIC, false)
        ));

        bundle.setItemMeta(meta);

        for (Player individual : Bukkit.getOnlinePlayers()) {
            HoverEvent<HoverEvent.ShowItem> hover = Bukkit.getItemFactory().asHoverEvent(bundle,
                    showItem -> showItem);

            final Component component = MiniMessage.miniMessage().deserialize(
                            plugin.luckPermsUtils().getPlayerRole(player) + player.getName() + ": " + "<gray>[" + "<yellow>Inventory Snapshot<gray>]")
                    .hoverEvent(hover)
                    .clickEvent(ClickEvent.callback(audience -> {
                        individual.openInventory(inventory);
                    }, ClickCallback.Options.builder().uses(ClickCallback.UNLIMITED_USES).build()));
            individual.sendMessage(component);
        }
    }

    public Component levels(Player player) {
        return MiniMessage.miniMessage().deserialize(
                plugin.luckPermsUtils().getPlayerRole(player)  + player.getName() + ": " + "<gray>[<green>XP Level " + player.getLevel() + "</gray>]"
        ).decoration(TextDecoration.ITALIC, false);
    }

    public void ec(Player player) {

        ItemStack[] items = player.getEnderChest().getContents();

        ItemStack bundle = new ItemStack(Material.BUNDLE);

        BundleMeta meta = (BundleMeta) bundle.getItemMeta();


        SGMenu snapshot = plugin.spiGUI().create("&8Enderchest Snapshot", 3);
        for (ItemStack item : items) {
            if (item == null || item.isEmpty()) continue;
            meta.addItem(item);
            snapshot.addButton(new SGButton(item));
        }

        meta.displayName(MiniMessage.miniMessage().deserialize(
                "<yellow>" + player.getName() + "'s Enderchest Snapshot"
        ).decoration(TextDecoration.ITALIC, false));

        bundle.setItemMeta(meta);

        for (Player individual : Bukkit.getOnlinePlayers()) {
            HoverEvent<HoverEvent.ShowItem> hover = Bukkit.getItemFactory().asHoverEvent(bundle,
                    showItem -> showItem);

            final Component component = MiniMessage.miniMessage().deserialize(
                            plugin.luckPermsUtils().getPlayerRole(player)  + player.getName() + ": " + "<gray>[" + "<yellow>Enderchest Snapshot<gray>]")
                    .hoverEvent(hover)
                    .clickEvent(ClickEvent.callback((callback) -> {
                        individual.openInventory(snapshot.getInventory());
                    }));
            individual.sendMessage(component);
        }
    }
}
