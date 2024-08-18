package com.takaranoao.titankiller.Handler;

import com.takaranoao.titankiller.Item.WoodenSword;
import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.tools.EntityTool;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import java.util.List;

public class RefuseEvent {

    int i;
    int d;
    int dropgo;
    int know;

    @SubscribeEvent//LivingEvent.LivingUpdateEvent是缓和更新
    public void RefuseClean(LivingEvent.LivingUpdateEvent event){
        /*
        if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer) {
             EntityPlayer player = (EntityPlayer)event.entityLiving;
           List<Entity> list = player.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.expand(2.0D, 2.0D, 2.0D));
            if (list != null && !list.isEmpty()) {
                      for (int i1 = 0; i1 < list.size(); i1++) {

                        Entity entity = list.get(i1);
                        if (entity != null && entity instanceof EntityItem && (
                                    (EntityItem)entity).getEntityItem() != null && ((EntityItem)entity).getEntityItem().getItem() == ItemLoader.titankiller && EntityTool.HasSubscribed(player))
                          {
                           entity.setDead();
                          }
                       }
           }
          if (player.getHeldItem() == null) {
               this.d = 0; //如果拿的东西是空气 i6 = 0
                }
             if (EntityTool.HasSubscribed(player)) {
                 if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof WoodenSword)
                      {
                          this.i = 55;//是混沌剑 i = 55
                      }
                   if (player.getHeldItem() != null && !(player.getHeldItem().getItem() instanceof WoodenSword))
                   {
                     this.i = 0;//不是混沌剑i = 0
              }
             }


           if (this.i != 0 && this.i == 55 && !EntityTool.HasSubscribed(player) && this.d != 3) {
                   ItemStack item1 = new ItemStack(ItemLoader.titankiller);
                if (player.getHeldItem() == null) {
                      player.inventory.mainInventory[player.inventory.currentItem] = item1;
                } else if (player.getHeldItem() != null && player.getHeldItem().getItem() != ItemLoader.titankiller) {
                          player.inventory.addItemStackToInventory(item1);
                }
                      this.i = 0;
                     this.d = 3;
                }
           if (this.d == 3) {
                     player.addChatMessage((IChatComponent)new ChatComponentText("一个奇怪的力量要把木剑夺走，这时木剑上的牙印亮了一下，什么都没有发生"));
                     this.d = 0;
           }
           */



        //一般的防抢夺，只在玩家更新时才可以生效
            if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.entityLiving;
            List<Entity> list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(2.0D, 2.0D, 2.0D));

          if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (entity != null && entity instanceof EntityItem && ((EntityItem)entity).getEntityItem() != null && ((EntityItem)entity).getEntityItem().getItem() == ItemLoader.titankiller && EntityTool.HasSubscribed(player))
           {
             entity.setDead();
        }
       }

          if (player.getHeldItem() == null) {
                //this.know = 0;
                player.getEntityData().setInteger("know",0);
          }

            if (EntityTool.HasSubscribed(player)) {

            if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof WoodenSword)
                {
                    player.getEntityData().setInteger("dropgo",1);
                  //this.dropgo = 1;
                }if (player.getHeldItem() != null && !(player.getHeldItem().getItem() instanceof WoodenSword))
                {
                    player.getEntityData().setInteger("dropgo",0);
                  //this.dropgo = 0;
                }
          }
        //inventory
            if (player.getEntityData().getInteger("dropgo") != 0 && player.getEntityData().getInteger("know") != 3 && player.getEntityData().getInteger("dropgo") == 1 && !(player.inventory.hasItem(ItemLoader.titankiller))) {
          //  System.out.println("已经加载");
            ItemStack item = new ItemStack(ItemLoader.titankiller);
            if (player.getHeldItem() != null) {
                player.inventory.mainInventory[player.inventory.currentItem] = item;
                System.out.println("L111215515151"+player.getEntityData().getInteger("dropgo"));
            } else if (player.getHeldItem() == null) {// && player.getHeldItem().getItem() != ItemLoader.titankiller
                player.inventory.addItemStackToInventory(item);
                System.out.println("HMKLJH"+player.getEntityData().getInteger("dropgo"));
            }
                //player.getEntityData().setInteger("dropgo",0);
                player.getEntityData().setInteger("know",3);
                System.out.println("Location"+player.getEntityData().getInteger("know"));
                System.out.println("GameTO"+player.getEntityData().getInteger("dropgo"));
            //this.dropgo = 0;
            //this.know = 3;
        }
        if (player.getEntityData().getInteger("know") == 3) {
            if(player.worldObj.isRemote){//设置单端监听
            player.addChatMessage(new ChatComponentText("一个奇怪的力量要把木剑夺走，这时木剑上的牙印亮了一下，什么都没有发生"));
            //this.know = 0;
            player.getEntityData().setInteger("know",0);
            System.out.println("FAnsgjaidsqo"+player.getEntityData().getInteger("know"));
            }
        }
        }
     }
    }

    @SubscribeEvent
    public void OnPlayerDrop(PlayerDropsEvent event){
        if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer)event.entityLiving;
            for (int i = 0; i < event.drops.size(); i++) {

                EntityItem entityItem = event.drops.get(i);
                ItemStack itemStack = entityItem.getEntityItem();
                Item item = itemStack.getItem();
                if (item != null && item == ItemLoader.titankiller)
                {
                    event.setCanceled(true);
                }
            }
        }
    }
}
