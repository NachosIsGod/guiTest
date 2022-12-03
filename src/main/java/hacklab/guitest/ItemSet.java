package hacklab.guitest;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class ItemSet {
    public Inventory setItem(Inventory inv, Material itemList[], boolean backButton){

        for(int i = 0; i < itemList.length; i++){
            ItemStack item = new ItemStack(itemList[i],1);
            inv.setItem(i,item);
            //itemMeta.setLore(Collections.singletonList("For Farmer"));
        }

        if(backButton){
            ItemStack back = new ItemStack(Material.ARROW,1);
            ItemMeta meta = back.getItemMeta();
            meta.setDisplayName("back");
            back.setItemMeta(meta);

            inv.setItem(inv.getSize()-1,back);
        }

        return inv;
    }
}
