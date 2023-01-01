package com.takaranoao.titankiller.Loader;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeLoader {
    public RecipeLoader(FMLInitializationEvent e){
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.titankiller),new Object[]{" L ","LXL"," L ",'L',Items.nether_star,'X', Items.wooden_sword});
    }
}
