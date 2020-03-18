package FreecamUtils;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import lunarfreecam.freecam.general.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class npcManager {
    private Main plugin;



    public npcManager(Main plugin) {
        this.plugin = plugin;


    }
    public void createtmpNPC(Player p){
        Zombie zombie = p.getWorld().spawn(p.getLocation(),Zombie.class);
        NBTEntity nbtent = new NBTEntity(zombie);
        nbtent.mergeCompound(new NBTContainer("{Silent:1b,NoAI:1b,IsBaby:0,NoGravity:0b,CustomNameVisible:1b}"));
        zombie.setCustomName(p.getDisplayName());
        //zombie.setCustomNameVisible(true);

        ItemStack playerhead = new ItemStack(Material.SKULL_ITEM,1,(byte) 3);
        SkullMeta meta = (SkullMeta) playerhead.getItemMeta();
        meta.setOwner(p.getName());
        meta.setDisplayName(p.getDisplayName());
        playerhead.setItemMeta(meta);

        zombie.getEquipment().setHelmet(playerhead);
        zombie.getEquipment().setItemInHand(p.getItemInHand());
        //setEquipment(zombie,3, p.getEquipment().getChestplate());
        zombie.getEquipment().setChestplate(p.getEquipment().getChestplate());
        zombie.getEquipment().setLeggings(p.getEquipment().getLeggings());
        zombie.getEquipment().setBoots(p.getEquipment().getBoots());
        //setSilent(zombie,false);
       // setAI(zombie,false);
        Main.npcalive.put(p.getUniqueId(),zombie);



    }
    public void deleteNpc(Player p){
        LivingEntity zombietokill = Main.npcalive.get(p.getUniqueId());
         zombietokill.remove();

    }
    public void goBack(Player player){
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Main.npcalive.get(player.getUniqueId()).getLocation());
        this.deleteNpc(player);
        Main.npcalive.remove(player.getUniqueId());
    }
   /* public static void setAI(LivingEntity entity, boolean hasAi) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();

        handle.getDataWatcher().watch(15, (byte) (hasAi ? 0 : 1));

    }
    public static void setSilent(LivingEntity entity, boolean hasAi) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();

        handle.getDataWatcher().watch(4, (byte) (hasAi ? 0 : 1));

    }
*/
}
