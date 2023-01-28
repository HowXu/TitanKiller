package com.howxu.infinitysword.Render.InfinityRender.RenderItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface RenderItemCosmic {

    //这个接口为ItemRender提供方法和设定使实现多种不同渲染
    //同样用于判断是否为mod物品再渲染

    @SideOnly(Side.CLIENT)
    IIcon getMaskTexture(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);

    @SideOnly(Side.CLIENT)
    float getMaskMultiplier(ItemStack paramItemStack, EntityPlayer paramEntityPlayer);
}