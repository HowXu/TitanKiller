/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2023-01-01 10:35:06
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-01-27 12:40:43
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\Event\EventHandler.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.Event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EventHandler {
//设置一个判断来实现事件选择,可以把赋值绑定到Item的使用上
    public static boolean AntiEvent = false;

    @SubscribeEvent//实体攻击事件
    public void onLivingAttack(LivingAttackEvent event){
            if(event.entity instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) event.entity;
                if(player.inventory.hasItem(com.howxu.demo.Main.DemoItem)){
                    event.setCanceled(true);//判断玩家物品栏，并取消事件
                    //判断伤害是否来自实体
                    if(event.source.getEntity() != null){
                        //可伤害的实体只来自EntityLivingBase
                        if(event.source.getEntity() instanceof EntityLivingBase){
                            EntityLivingBase entity = (EntityLivingBase) event.source.getEntity();
                            entity.getEntityData().setBoolean("BeDemoAttacked", true);//添加nbt标记
                            entity.onDeath(DamageSource.outOfWorld);//添加死亡
                        }
                    }

                }
            }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event){
        if(event.entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.entity;
            //判断玩家是否在数组中
            if(AntiDisarm.HasIt(player.getCommandSenderName())){
                //判断是否具有物品
                if(!player.inventory.hasItem(com.howxu.demo.Main.DemoItem)){
                    //没有就加一个
                    player.inventory.addItemStackToInventory(new ItemStack(com.howxu.demo.Main.DemoItem));
                    //好低级的防缴械
                }
            }else{
                if(EventUtil.TimeStop){
                    event.setCanceled(true);
                }
            }
        }else{
            if(EventUtil.TimeStop){
                event.setCanceled(true);
            }
        }
    }

}
