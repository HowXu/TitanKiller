package com.takaranoao.titankiller.util;

import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.tools.HealthKiller;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EntityTools {
    //激活寰宇支配之剑改
    public static void titanKillerTryKillEntity(EntityLivingBase victim,EntityLivingBase player,boolean isSneaking){
            HealthKiller.RemoveTitanHealth(new ItemStack(ItemLoader.titankiller),(EntityPlayer) player);
            HealthKiller.RemoveEntityHealth(new ItemStack(ItemLoader.titankiller),(EntityPlayer) player);
    }
}
