/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 11:26:24
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-01-01 20:16:03
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\Event\EventUtil.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.Event;


import com.howxu.demo.render.WhiteBlackRender;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EventUtil {

    public static Boolean TimeStop = false;

    //这里把getHealth调用出来，然后就可以自己写这个方法了
    public static float getHealth(EntityLivingBase entity){

        if(entity instanceof EntityPlayer){
        
        EntityPlayer theplayer = (EntityPlayer) entity;//强制转换一下，这样拿到的就是player

        if(HasItemDemo(theplayer) && theplayer.getMaxHealth() == 0.0F){
            theplayer.setHealth(theplayer.getMaxHealth() + 20.0F);//将血量修改，以防止丢掉物品死亡
            return theplayer.getMaxHealth() + 20.0F;//如果最大血量为0，直接返回最大血量+20，实现锁血
        }else if(HasItemDemo(theplayer)){
            theplayer.setHealth(theplayer.getMaxHealth());
            return theplayer.getMaxHealth();
        }
    }

        if(entity.getEntityData().getBoolean("BeDemoAttacked")){
            entity.setHealth(0.0F);
            return 0.0F;
        }
        
        //return 0.0F;
        //这里默认所有实体的血量为0
        return entity.getDataWatcher().getWatchableObjectFloat(6);
        //这是正常的返回值,从datawatch里面读取血量
    }

    public static void runGameLoop(Minecraft mc){
        //通过这个简单的判断可以实现时停任然可以打开暂停等效果
        //添加一个实体更新，这样就可以动了
        
        mc.isGamePaused = TimeStop;
        
        /*if(TimeStop){
            mc.isGamePaused = TimeStop;
        }else if(!TimeStop){
            mc.isGamePaused = TimeStop;
        }*/

        //时停时激活这个方法
        if(TimeStop){

            //时停同时激活渲染
            if(!WhiteBlackRender.IsBlackAndWhiteScreen()){
                WhiteBlackRender.BlackAndWhiteScreen(true);
            }

            for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++){
            Entity entity = (Entity) mc.theWorld.loadedEntityList.get(i);
            if (entity instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) entity;
                if(HasItemDemo(player)){
                    player.onUpdate();
                    }
                }
            }
        }else if(!TimeStop) { //没有时停结束渲染
            if(!WhiteBlackRender.IsBlackAndWhiteScreen()){
                WhiteBlackRender.BlackAndWhiteScreen(false);
            }
        }
    }

    public static boolean HasItemDemo(EntityPlayer player){
        //一个简单的判断，判断玩家是否持有DemoItem
        if(player.inventory.hasItem(com.howxu.demo.Main.DemoItem)){
            return true;
        }else{
            return false;
        }
    }

}
