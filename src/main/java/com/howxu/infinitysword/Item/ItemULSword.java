package com.howxu.infinitysword.Item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemULSword extends ItemSword {
    public ItemULSword() {
        super(ToolMaterial.EMERALD);
        setUnlocalizedName("ulsword");
        setCreativeTab(CreativeTabs.tabTools);
        //setTextureName("infinity:ul");因为改了模型所以材质不需要了
    }

}
