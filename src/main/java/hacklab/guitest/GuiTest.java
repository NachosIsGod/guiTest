package hacklab.guitest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class GuiTest extends JavaPlugin implements Listener{

    //GUI作成
    Inventory mainInv = Bukkit.createInventory(null,9,"Menu");
    Inventory swordInv = Bukkit.createInventory(null, 9, "Sword");

    Material[] mainInvItem = {
            Material.DIAMOND_SWORD,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_SHOVEL,
            Material.DIAMOND_AXE,
            Material.DIAMOND_HOE,
            Material.TRIDENT,
            Material.SPYGLASS,
            Material.TOTEM_OF_UNDYING,
            Material.COOKED_BEEF,
    };

    Material[] swordInvItem = {
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.GOLDEN_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD
    };

    List<Inventory> invMem = new ArrayList<Inventory>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("thisPluginIsGUITest");

        mainInv = new ItemSet().setItem(mainInv,mainInvItem,false);
        swordInv = new ItemSet().setItem(swordInv,swordInvItem,true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(Objects.requireNonNull(e.getItem()).getType() == Material.STICK){
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                //プレイヤーに表示
                e.getPlayer().openInventory(mainInv);
                System.out.println("open");
            }
        }
    }

    //インベントリ閉じたとき
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        invMem.add(e.getInventory());
        e.getPlayer().addScoreboardTag("MenuOpen");
    }

    //インベントリ閉じたとき
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Set<String> UserTags = e.getPlayer().getScoreboardTags(); //タグ取得
        if(UserTags.contains("MenuOpen")){
            e.getPlayer().removeScoreboardTag("MenuOpen");
        }
    }

    //インベントリのアイテムを取ったとき
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();
        Set<String> UserTags = e.getWhoClicked().getScoreboardTags(); //タグ取得
        if(UserTags.contains("MenuOpen")){
            e.setCancelled(true);

            ItemStack clickedItem = e.getCurrentItem();
            Player clicker = ((Player) e.getWhoClicked()).getPlayer();

            if(clickedItem.getType() == Material.DIAMOND_SWORD){
                clicker.openInventory(swordInv);
            }else if(clickedItem.getType() == Material.ARROW || clickedItem.getItemMeta().getDisplayName().equals("back")){
                p.openInventory( invMem.get(invMem.size()-2) );
            }else{
                //p.getInventory().addItem(e.getCurrentItem());

            }
            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 2, 1);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;
        p.openInventory( invMem.get(invMem.size()-2));
        return true;
    }

}
