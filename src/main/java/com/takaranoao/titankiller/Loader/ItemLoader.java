package com.takaranoao.titankiller.Loader;

import com.takaranoao.titankiller.Item.WoodenSword;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class ItemLoader {


    public static final ItemSword titankiller = new WoodenSword(Item.ToolMaterial.WOOD);

    public ItemLoader(FMLPreInitializationEvent e){
        registe(titankiller);
    }

    private void registe(Item item){
        GameRegistry.registerItem(item,item.getUnlocalizedName());
    }

}
