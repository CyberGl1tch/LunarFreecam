package FreecamUtils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.nbtapi.NBTEntity;
import lunarfreecam.freecam.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;


public class npcManager {
    private Main plugin;



    public npcManager(Main plugin) {
        this.plugin = plugin;
    }
    public void createtmpNPC(Player p){
        /**
         * Spawn the Zombie-NPC
         */
        Zombie zombie = p.getWorld().spawn(p.getLocation(),Zombie.class);
        while(zombie.getPassenger()!=null && zombie.getPassenger().getType().equals(EntityType.CHICKEN) || zombie.isBaby() || zombie.isVillager()){
            zombie.remove();
            zombie = p.getWorld().spawn(p.getLocation(),Zombie.class);
        }
        NBTEntity zombieNBT = new NBTEntity(zombie);
        zombieNBT.setByte("Silent",(byte)1);
        zombieNBT.setByte("IsBaby",(byte)0);
        zombieNBT.setByte("NoAI",(byte)1);
        zombieNBT.setByte("NoGravity",(byte)0);
        zombieNBT.setByte("CustomNameVisible",(byte)1);
        zombie.setCustomName(p.getDisplayName());

        ItemStack playerhead = new ItemStack(Objects.requireNonNull(XMaterial.PLAYER_HEAD.parseMaterial()),1,(byte) 3);
        SkullMeta meta = (SkullMeta) playerhead.getItemMeta();
        meta.setOwner(p.getName());
        meta.setDisplayName(p.getDisplayName());
        playerhead.setItemMeta(meta);
        /**
         * Zombie Equipment
         */
        zombie.getEquipment().setHelmet(playerhead);
        zombie.getEquipment().setItemInHand(p.getItemInHand());
        zombie.getEquipment().setChestplate(p.getEquipment().getChestplate() != null && !p.getEquipment().getChestplate().getType().equals(Material.AIR) ?  p.getEquipment().getChestplate() : XMaterial.LEATHER_CHESTPLATE.parseItem());
        zombie.getEquipment().setLeggings(p.getEquipment().getLeggings() != null && !p.getEquipment().getLeggings().getType().equals(Material.AIR) ?  p.getEquipment().getLeggings() : XMaterial.LEATHER_LEGGINGS.parseItem());
        zombie.getEquipment().setBoots(p.getEquipment().getBoots() != null && !p.getEquipment().getBoots().getType().equals(Material.AIR) ?  p.getEquipment().getBoots() : XMaterial.LEATHER_BOOTS.parseItem());
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

}
