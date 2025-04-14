package com.github.voidSurvival2025.Commands;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class WikiCmd {

    private final VoidSurvival2025 plugin;
    public WikiCmd(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        com.github.voidSurvival2025.Manager.WikiManager wiki = new com.github.voidSurvival2025.Manager.WikiManager();
        wiki.setTitle("Wiki");
        wiki.setAuthor("Awokens");

        List<String> pages = new ArrayList<>();
        pages.add("""
                <bold>      VOID WIKI</bold><newline><gradient:#7C17C6:#9B22F4:#A12AF9:#7C17C6>  Created By Awokens</gradient>
                 \s
                This book will list our custom features and their purposes.
                → Chat, player, mobs and more
                \s
                <bold>TIP</bold>: Hover over some text for more info.
               \s""");

        pages.add("""
                <color:#f2d200>Chat Features</color>
                
                • <hover:show_text:'Displays your held item in chat'>[item] - held item</hover>
                • <hover:show_text:'Displays a preview of your inventory in chat
                  And you can see an extended preview by clicking the message'>[inv] - inventory</hover>
                • <hover:show_text:'Displays a preview of your enderchest in chat
                  And you can see an extended preview by clicking the message'>[ec] - enderchest</hover>
                """);

        pages.add("""
                <color:#be00f2>Player Features</color>
                
                • <hover:show_text:'You can open your crafting table by sneaking\s
                  and right click the crafting table while holding it'>Crafting Table</hover>
                • <hover:show_text:'Let\\'s get technicaly here...
                
                  If you kill a player, they <red>lose half a heart</red>. And so
                  they spawn back with <red>less hearts</red> each time basically.
                
                  Once they <red>lose all their hearts</red>, all their items drop
                  except for <color:#ffe100>their hotbar or worn armor</color>.
                
                  While for you, <color:#4dff00>you gain half a heart</color> up to the default.
                  Unless you eat a <color:#ff005d>corian root</color>, you gain additional hearts too.
                
                  '>Limited hearts</hover>
                • <hover:show_text:'All player death messages are sent above
                  your hotbar AKA action bar.'>Death messages</hover>
                """);

        pages.add("""
                <color:#ff0044>Hostile Mobs</color>
                
                • <hover:show_text:'A guardian or elder guardian can\s
                  uncommonly be summoned by fishing'>[Elder] Guardian</hover>
                • <hover:show_text:'You can get Zombified Piglins by
                  striking lightning onto pigs with a lightning rod'>Zombified Piglin</hover>
                • <hover:show_text:'You can cure a Zombified Piglin to
                  become a Pigin by affecting the Piglin with Strength
                  and giving the Piglin a golden apple'>Piglin</hover>
                • <hover:show_text:'Witches can uncommonly drop nether warts
                  And chances increase with Looting'>Witch</hover>
                """);

        pages.add("""
                <color:#7d7d7d>Block Features</color>
                
                • <hover:show_text:'TNT explodes differently on here.
                  When ignited, it destroys blocks
                  In a snake trail pattern, making
                  raiding more fun and combative.'>TNT</hover>
                • <hover:show_text:'Functions just like TNT!
                  Just shoot it and boom!'>Fireball</hover>
                • <hover:show_text:'Drop 32 Tuff blocks into an empty cauldron,\s
                  while there is a campfire below the cauldron.'>How to make Obsidian</hover>
                • <hover:show_text:'Simply right click on a dirt block with bone meal.'>How to create Moss</hover>
                • <hover:show_text:'Simply right click on a lightning rod with copper ingot whilst raining'>Striking Lightning</hover>
                • <hover:show_text:'Reviving a deadbush with bonemeal creates a\s
                  random tree sapling or potentially a torchflower'>Reviving a Deadbush</hover>
                • <hover:show_text:'Simply right click on a rooted dirt block to make grass blocks'>How to create Grass</hover>
                • <hover:show_text:'Right click a dirt block with a water bottle'>Mud Block</hover>
                • <hover:show_text:'Right click a mud block with a water bottle'>Clay Block</hover>
                • <hover:show_text:'Right click a clay block with a water bottle'>Suspicious Sand</hover>
                • <hover:show_text:'Breaking any type of tree leaf will uncommonly drop an apple'>Tree Leaves</hover>
                """);

        pages.add("""
                <color:#bd6500>Item Features</color>
                
                • Nautilus shell
                • Prismarine Shard
                • Eye of Ender
                """);
        pages.add("""
                <color:#1787ff>Custom Mechanics</color>
                
                • Summoning a Skeleton
                • Summong a Wither Skeleton
                • Curing a Zombified Piglin
                """);
        pages.add("""
                <dark_aqua>Overworld</dark_aqua>
                
                • Keep inventory is on by default
                • World blocks clear every 5 days
                • Random large border size
                • Cannot place blocks above Y 32
                • Smells like mister_woo2
                """);

        pages.add("""
                <color:#a656b8>The End</color>
                
                • Keep inventory is off by default
                • World blocks do not clear
                • Fixed border small size
                • Cannot place blocks above Y 128
                • Smells like Awokens
                """);

        pages.add("""
                <color:#c71208>The Nether</color>
                
                • Keep inventory is off by default
                • Blocks do not clear
                • Fixed border small size
                • Cannot place blocks above Y 64
                • Smells like Htxx
                """);

        wiki.setPages(pages);

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setAuthor(wiki.getAuthor());
        bookMeta.setTitle(wiki.getTitle());

        for (String page : wiki.getPages()) {
            bookMeta.addPages(MiniMessage.miniMessage().deserialize(page));
        }
        book.setItemMeta(bookMeta);

        new CommandAPICommand("wiki")
                .withAliases("help", "tutorial")
                .withFullDescription("Void Survival Book guide")
                .executesPlayer((player, args) -> {
                    player.openBook(book);
                    player.playSound(player, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0F, 1.0F);
                }).register();

    }
}
