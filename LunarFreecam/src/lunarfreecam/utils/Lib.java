package lunarfreecam.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class Lib {

    public static String colorCode(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String removeColorCode(String s) {
        return ChatColor.stripColor(s);
    }

    public static void fillGUI(Inventory inv, ItemStack material) {
        for(int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null) {
                inv.setItem(i, material);
            }
        }
    }

    public static ItemStack customItem(Material item, int amount, int item_byte, String name, ItemFlag flag, String... lore) {
        ItemStack cItem = new ItemStack(item, amount, (byte)item_byte);
        ItemMeta cItemMeta = cItem.getItemMeta();

        //Setting the item's name.
        cItemMeta.setDisplayName(name);
        //Setting the item's unbreakable state.

        ArrayList<String> cLore = new ArrayList<String>();
        for(int i = 0; i < lore.length; i++) {
            cLore.add(lore[i]);
        }

        //Setting the item's lore.
        cItemMeta.setLore(cLore);
        //Setting the item's flag.
        cItemMeta.addItemFlags(flag);

        //Setting the item's meta.
        cItem.setItemMeta(cItemMeta);

        //Return the item.
        return cItem;

    }

    public static ItemStack customItem(Material item, int amount, int item_byte, String name, ArrayList<String> lore) {
        ItemStack cItem = new ItemStack(item, amount, (byte)item_byte);
        ItemMeta cItemMeta = cItem.getItemMeta();

        //Setting the item's name.
        cItemMeta.setDisplayName(name);
        //Setting the item's unbreakable state.

        ArrayList<String> cLore = lore;

        //Setting the item's lore.
        cItemMeta.setLore(cLore);
        cItemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        cItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //Setting the item's flag.

        //Setting the item's meta.
        cItem.setItemMeta(cItemMeta);

        //Return the item.
        return cItem;

    }

    public static ItemStack customItem(Material item, int amount, int item_byte, String name) {
        ItemStack cItem = new ItemStack(item, amount, (byte)item_byte);
        ItemMeta cItemMeta = cItem.getItemMeta();

        //Setting the item's name.
        cItemMeta.setDisplayName(name);
        //Setting the item's unbreakable state.

        //Setting the item's meta.
        cItem.setItemMeta(cItemMeta);

        //Return the item.
        return cItem;

    }

    public static ItemStack customSkull(String owner, String name, ItemFlag flag, String... lore) {
        ItemStack cItem = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta cItemMeta = (SkullMeta) cItem.getItemMeta();

        //Setting the skull's owner.
        cItemMeta.setOwner(owner);
        //Setting the item's name.
        cItemMeta.setDisplayName(name);

        ArrayList<String> cLore = new ArrayList<String>();
        for(int i = 0; i < lore.length; i++) {
            cLore.add(lore[i]);
        }

        //Setting the item's lore.
        cItemMeta.setLore(cLore);

        //Setting the item's flag.
        cItemMeta.addItemFlags(flag);

        //Setting the item's meta.
        cItem.setItemMeta(cItemMeta);

        //Return the item.
        return cItem;

    }

    public static void fillGUIFrame(Inventory inv, ItemStack material, boolean bottomline) {
        if(inv.getSize() == 54) {
            if(bottomline == true) {
                for(int i=0; i<9; i++) {
                    inv.setItem(i, material);
                }
                inv.setItem(9, material);
                inv.setItem(17, material);
                inv.setItem(18, material);
                inv.setItem(26, material);
                inv.setItem(27, material);
                inv.setItem(35, material);
                for(int i=0; i<9; i++) {
                    int j = i + 36;
                    inv.setItem(j, material);
                }
            }
            else {
                for(int i=0; i<9; i++) {
                    inv.setItem(i, material);
                }
                inv.setItem(9, material);
                inv.setItem(17, material);
                inv.setItem(18, material);
                inv.setItem(26, material);
                inv.setItem(27, material);
                inv.setItem(35, material);
                inv.setItem(36, material);
                inv.setItem(44, material);
                for(int i=0; i<9; i++) {
                    int j = i + 45;
                    inv.setItem(j, material);
                }
            }
        }
        else if(inv.getSize() == 45) {
            if(bottomline == true) {
                for(int i=0; i<9; i++) {
                    inv.setItem(i, material);
                }
                inv.setItem(9, material);
                inv.setItem(17, material);
                inv.setItem(18, material);
                inv.setItem(26, material);
                for(int i=0; i<9; i++) {
                    int j = i + 27;
                    inv.setItem(j, material);
                }
            }
            else {
                for(int i=0; i<9; i++) {
                    inv.setItem(i, material);
                }
                inv.setItem(9, material);
                inv.setItem(17, material);
                inv.setItem(18, material);
                inv.setItem(26, material);
                inv.setItem(27, material);
                inv.setItem(35, material);
                for(int i=0; i<9; i++) {
                    int j = i + 36;
                    inv.setItem(j, material);
                }
            }
        }
        else if(inv.getSize() == 36) {
            if(bottomline == true) {
                for(int i=0; i<9; i++) {
                    inv.setItem(i, material);
                }
                inv.setItem(9, material);
                inv.setItem(17, material);
                inv.setItem(18, material);
                inv.setItem(26, material);
                for(int i=0; i<9; i++) {
                    int j = i + 18;
                    inv.setItem(j, material);
                }
            }
            else {
                for(int i=0; i<9; i++) {
                    inv.setItem(i, material);
                }
                inv.setItem(9, material);
                inv.setItem(17, material);
                inv.setItem(18, material);
                inv.setItem(26, material);
                for(int i=0; i<9; i++) {
                    int j = i + 27;
                    inv.setItem(j, material);
                }
            }
        }
        else if(inv.getSize() == 27) {
            for(int i=0; i<9; i++) {
                inv.setItem(i, material);
            }
            inv.setItem(9, material);
            inv.setItem(17, material);
            inv.setItem(18, material);
            inv.setItem(26, material);
            for(int i=0; i<9; i++) {
                int j = i + 27;
                inv.setItem(j, material);
            }
        }

    }

    public static void PlayerListShowOnGUI(Inventory inv, List<String> list) {
        if(!list.isEmpty()) {
            for(String element : list) {
                ItemStack skull = customSkull(element, Bukkit.getPlayer(element).getName(), ItemFlag.HIDE_ATTRIBUTES, colorCode("&7Test line."));
                inv.setItem(inv.firstEmpty(), skull);
            }
        }
        else {
            return;
        }
    }

}
