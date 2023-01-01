package com.takaranoao.titankiller.Handler;

import com.takaranoao.titankiller.Event.CancelAblePlayerEvent1;
import com.takaranoao.titankiller.Event.TKPlayerEvent;
import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.tools.EntityTool;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class RefuseHandler {

    @SubscribeEvent
    public void onPlayer1(TKPlayerEvent event) {
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {

        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.entityLiving;
            if (EntityTool.HasSubscribed(player)) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
            }
        }

    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {

            EntityPlayer player2 = (EntityPlayer)event.entityLiving;
            if (EntityTool.HasSubscribed(player2)) {
                event.setCanceled(true);
                player2.setHealth(player2.getMaxHealth());
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof EntityPlayer) {
            EntityPlayer player = event.player;
            if (player.getHealth() != player.getMaxHealth() && player.getHeldItem() != null && player.getHeldItem().getItem() ==ItemLoader.titankiller) {
                player.isDead = false;
                player.setHealth(player.getMaxHealth());
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player3 = (EntityPlayer)event.entityLiving;
            if (EntityTool.HasSubscribed(player3)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer)event.entityLiving;

            if (EntityTool.HasSubscribed(player)) {
                player.setHealth(player.getMaxHealth());
                player.isEntityUndead();
                player.capabilities.disableDamage = true;
                player.setHealth(player.getMaxHealth());
                player.noClip = false;
                player.isEntityUndead();
                if (player.getHealth() <= 0.0F || player.isDead) {
                    player.isDead = false;
                    player.setHealth(player.getMaxHealth());
                }
                if (player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() < 20.0D)
                    player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
                player.setHealth(player.getMaxHealth());
            }
            if (event.entityLiving.worldObj.isRemote && event.entityLiving instanceof EntityPlayerSP) {
                EntityPlayerSP playerSP = (EntityPlayerSP) event.entityLiving;
                if (EntityTool.HasSubscribed(player)) {
                    player.capabilities.allowFlying = true;
                }
            }
        }
    }
    @SubscribeEvent
    public void onPlayer2(CancelAblePlayerEvent1 event) {
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller) {
            event.setCanceled(true);
        }
    }

}
