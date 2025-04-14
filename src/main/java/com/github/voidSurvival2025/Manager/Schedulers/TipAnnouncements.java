package com.github.voidSurvival2025.Manager.Schedulers;

import com.github.voidSurvival2025.VoidSurvival2025;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TipAnnouncements implements Runnable {

    private final VoidSurvival2025 plugin;

    private final List<String> tips = List.of(
            "Check out all the cool stuff in the <color:#ffac12>Spring Edition II</color> wiki!",
            "Made by this dude ->>>> <color:#3bd1ff>Awokens</color>!",
            "Every 10 seconds, you get 1 of these items: \n<color:#ffac12>Cobblestone</color>, <color:#ffac12>Iron nuggets</color>, <color:#ffac12>Dirt</color>, or <color:#ffac12>Oak Planks</color>. Sweet, right?",
            "Type <color:#ffac12>/commands</color> to see all the things you can run!",
            "<color:#ffac12>Bats</color> drop leather when they bite the dust!",
            "<color:#ffac12>Drowned</color> don’t spawn with fancy gear. Oops!",
//            "Want to cure a <color:#ffac12>Zombie Nitwit</color>? Use a <color:#ffac12>Potion of Weakness</color> \nand an <color:#ffac12>Enchanted Golden Apple</color>. You got this!",
            "Kill an <color:#ffac12>Elder Guardian</color> for <color:#ffac12>Luck 3</color> \nand it might drop a <color:#ffac12>Heart of The Sea</color>. Fancy!",
            "<color:#ffac12>Guardians</color> give you <color:#ffac12>Luck 2</color> when you have <color:#ffac12>Luck 1</color>. It's like a lucky combo!",
            "Feed a <color:#ffac12>Zombified Piglin</color> a <color:#ffac12>Strength Potion</color> \nand an <color:#ffac12>Enchanted Golden Apple</color> to turn them into a <color:#ffac12>Piglin</color>. Magic!",
            "<color:#ffac12>Piglins</color> don’t spawn naturally. They're a bit shy!",
            "<color:#ffac12>Wandering Traders</color> drop emeralds when they bite the dust. Cha-ching!",
            "<color:#ffac12>Witches</color> sometimes drop <color:#ffac12>Nether Wart</color>. You gotta be lucky!",
            "Bone meal makes <color:#ffac12>rooted dirt</color> grow into grass. Nature’s magic!",
            "Sneak-right-click a <color:#ffac12>crafting table</color> \nto open a crafting bench, like, for real!",
            "Type <color:#ffac12>[item]</color> in chat to share your item snapshot. \nType <color:#ffac12>[inv]</color> to show your whole inventory! Sharing is caring.",
            "Death messages show up in the action bar \nright above your hotbar. Oof!",
            "Right-click <color:#ffac12>Mud</color> with a <color:#ffac12>water bottle</color> \nto make <color:#ffac12>Clay</color> or <color:#ffac12>Sand</color>. Just like that!",
            "Make <color:#ffac12>Obsidian</color> by placing a lit campfire, \na cauldron, and 32 cobbled deepslate. Boom, Obsidian!",
            "Ignite <color:#ffac12>TNT</color> to create a snake trail explosion \nthat can wreck up to 270 blocks. Kaboom!",
            "Use bone meal on a <color:#ffac12>dead bush</color> \nto turn it into a <color:#ffac12>tree sapling</color>. From dead to living!",
            "<color:#ffac12>TNT</color> is cool, but this is even better: <color:#ffac12>Fireballs</color> launched like crazy!",
            "Right-click a <color:#ffac12>Nautilus Shell</color> \nto get <color:#ffac12>treasure loot</color>. Who doesn’t love treasure?",
            "Right-click a <color:#ffac12>lightning rod</color> with a <color:#ffac12>copper ingot</color> \nwhile it rains to summon <color:#ffac12>lightning</color>. Shazam!",
            "Fish up <color:#ffac12>Drowned</color>, <color:#ffac12>Elder Guardians</color>, or <color:#ffac12>Guardians</color> \nfor some rare loot. Fishy treasures!",
//            "Sneak right-click to trade items with your pals. It's like item swaps!",
//            "Only 1 piece of <color:#ffac12>diamond armor</color> at a time. Gotta keep it balanced!",
            "Right-click <color:#ffac12>dirt</color> to make <color:#ffac12>moss</color>. Moss everywhere!",
            "Place an <color:#ffac12>Eye of Ender</color> in an <color:#ffac12>End Portal Frame</color> \nto teleport to the End. Adventure time!",
            "Brush <color:#ffac12>suspicious sand</color> to find cool <color:#ffac12>armor trims</color>, \nitems, or even <color:#ffac12>diamonds</color>. Gotta brush 'em all!",
            "Burn a <color:#ffac12>skeleton</color> on <color:#ffac12>soul soil</color> \nto summon a <color:#ffac12>Wither Skeleton</color>. It’s a fiery transformation!",
            "Burn a <color:#ffac12>skeleton</color> on <color:#ffac12>blackstone</color> \nto summon a <color:#ffac12>Blaze</color>. Fire meets skeleton!",
            "Breaking <color:#ffac12>leaves</color> can drop an <color:#ffac12>apple</color>. Crunchy and tasty!",
            "Eat a <color:#ffac12>Corian Root</color> to get your capped hearts back\nor even gain more hearts. Nom nom!",
            "Expect tons of <color:#ffac12>farming</color> and <color:#ffac12>water</color> in the <color:#ffac12>Overworld</color>. Water, water, everywhere!",
            "The <color:#ffac12>Overworld</color> sometimes clears itself. Mysterious, huh?",
            "The border in the <color:#ffac12>Overworld</color> is all <color:#ffac12>random-sized</color>. Surprises!",
            "Can’t place blocks above <color:#ffac12>Y32</color> in the <color:#ffac12>Overworld</color>. Sorry, no sky towers!",
            "Keep Inventory is off in the <color:#ffac12>The End</color>. Oh no!",
            "Farming and water are here in <color:#ffac12>The End</color> \nbut no clearing. Keep it cool!",
            "Go back the way you came in <color:#ffac12>The End</color>. Time for the return trip!",
            "Keep Inventory is off in the <color:#ffac12>Nether</color>. Yikes!",
            "Gold farming is big in the <color:#ffac12>Nether</color>. Get that gold!",
            "No water in the <color:#ffac12>Nether</color>, \nbut no clearing either. It's hot here!",
            "Go back the way you came in the <color:#ffac12>Nether</color>. No shortcuts!",
            "Wear <color:#ffac12>mob skulls</color> to gain their natural abilities.",
            "<color:#ffac12>Piglins</color> hit hard like <color:#ffac12>Mike Tyson</color>. Watch out!"


    );

    public TipAnnouncements(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        int index = ThreadLocalRandom.current().nextInt(0, tips.size() - 1);

        String nextTip = tips.get(index);

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<b><yellow>Did you know?</yellow></b> " + nextTip
            ));
        }

    }
}
