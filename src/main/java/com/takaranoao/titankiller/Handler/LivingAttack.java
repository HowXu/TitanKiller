package com.takaranoao.titankiller.Handler;

import com.takaranoao.titankiller.Item.WoodenSword;
import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.tools.EntityTool;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingAttack {

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event){
        if (event.player instanceof EntityPlayer) {

            EntityPlayer player = event.player;
            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof WoodenSword) {

                player.setHealth(player.getMaxHealth());
                player.noClip = false;
                player.setHealth(player.getHealth() * 2.0F + 10.0F);
            }
            if(!EntityTool.HasSubscribed(player)){
                player.capabilities.allowFlying = false;
            }
        }
    }
    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isUsingItem()) {
            event.setCanceled(true);
            player.setHealth(player.getHealth() * 2.0F + 2.0F);
        }
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isSneaking()) {
            event.setCanceled(true);
            player.setHealth(player.getHealth() * 2.0F + 2.0F);
        }
        if (EntityTool.HasSubscribed(player)) {

            Entity source = event.source.getEntity();
            event.setCanceled(true);
            player.setHealth(player.getMaxHealth());
            player.noClip = false;
            player.isEntityUndead();
            if (source == null)
            {
                event.setCanceled(true);
            }
            if (source != null) {

                EntityLivingBase el = null;
                if (source instanceof EntityArrow) {

                    Entity se = ((EntityArrow)source).shootingEntity;
                    if (se instanceof EntityLivingBase) {
                        el = (EntityLivingBase)se;
                    }
                }
                if (source instanceof EntityLivingBase) {
                    el = (EntityLivingBase)source;
                }

                if (Loader.isModLoaded("thetitans"))
                {
                    if (el instanceof EntityTitan || el instanceof net.minecraft.entity.titan.EntitySlimeTitan || source instanceof net.minecraft.entity.titan.EntitySnowGolemTitan)
                    {
                     // ((EntityTitan)el).setTitanHealth(el.getHealth() / 4.0F);    //对生成的泰坦造成区域爆杀
                    }
                }
                if (el != null && !el.isDead && el instanceof EntityPlayer) {

                    //将攻击者生命设置为0,but泰坦杀手不需要
                    //el.hurtResistantTime = 0;
                    //el.setHealth(0.0F);
                    //el.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0.0D);
                    //el.handleHealthUpdate((byte)3);
                    //el.deathTime++;
                    //el.getDataWatcher().updateObject(6, MathHelper.clamp_float(el.getHealth() - 5000.0F, 0.0F, el.getMaxHealth()));


                    if (el.getHealth() <= 0.0F) {
                        //el.isDead = true;
                        //el.setDead();
                    }
                    //el.onDeath((DamageSource)new EntityDamageSource("infinity", (Entity)player));
                }
                if (el != null && !el.isDead && el.getHealth() < 1000.0F) {

                    //el.setHealth(el.getHealth() / 10.0F - 10.0F);
                    if (el.getHealth() <= 0.0F);

                    //el.onDeath((DamageSource)new EntityDamageSource("infinity", (Entity)player));
                }
            }
        }

        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof WoodenSword && player.isUsingItem()) {
            event.setCanceled(true);
            player.setHealth(player.getHealth() * 2.0F + 10.0F);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer)event.entityLiving;
            if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isUsingItem()) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
                player.isDead = false;
            }
            if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isSneaking()) {
                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
                player.isDead = false;
            }
            if (EntityTool.HasSubscribed(player)) {

                event.setCanceled(true);
                player.setHealth(player.getMaxHealth());
                player.isDead = false;
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event){
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isUsingItem()) {
            event.setCanceled(true);
            player.setHealth(player.getHealth() * 2.0F + 2.0F);
        }
        if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isSneaking()) {
            event.setCanceled(true);
            player.setHealth(player.getHealth() * 2.0F + 2.0F);
        }
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof WoodenSword && player.isUsingItem()) {
            event.setCanceled(true);
            player.setHealth(player.getHealth() * 2.0F + 10.0F);
            player.isDead = false;
        }
        if (EntityTool.HasSubscribed(player)) {

            event.setCanceled(true);
            player.setHealth(player.getMaxHealth());
            player.noClip = false;
            player.isEntityUndead();
        }
    }
}
