/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 11:26:24
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-07-17 10:38:36
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\Event\EventUtil.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.Event;


import java.util.Arrays;
import java.util.Collections;

import com.google.common.base.Throwables;
import com.howxu.demo.render.WhiteBlackRender;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.IEventListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;

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
                    //player.worldObj.updateEntity(player);这个方法更新太慢了
                    player.onUpdate();//这个方法不会卡顿
                    //
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

    //重写post方法
    public static boolean post(EventBus bus,Event event){
        if (EventHandler.AntiEvent) {
            return false;//设置一个事件全部取消
        }
        IEventListener[] listeners = event.getListenerList().getListeners(bus.busID);
        int index = 0;
        try
        {
            for (; index < listeners.length; index++)
            {
                listeners[index].invoke(event);
            }
        }
        catch (Throwable throwable)
        {
            bus.exceptionHandler.handleException(bus, event, listeners, index, throwable);
            Throwables.propagate(throwable);
        }
        return (event.isCancelable() ? event.isCanceled() : false);
    }

    public static void tick(IntegratedServer server){

        if(server.isGamePaused){
            for(int i = 0; i < server.getEntityWorld().loadedEntityList.size(); i++){
                Entity entity = (Entity) server.getEntityWorld().loadedEntityList.get(i);
                if (entity instanceof EntityPlayer){
                    EntityPlayer player = (EntityPlayer) entity;
                    if(HasItemDemo(player)){
                        player.worldObj.updateEntity(player);//同样的更新
                        }
                    }
                }


        }
    }

    public static void tick2(MinecraftServer Server){

        if(EventUtil.TimeStop){
            for (WorldServer server : Server.worldServers) {
                for(int i = 0; i < server.loadedEntityList.size(); i++){
                    Entity entity = (Entity) server.loadedEntityList.get(i);
                    if (entity instanceof EntityPlayer){
                        EntityPlayer player = (EntityPlayer) entity;
                        if(HasItemDemo(player)){
                            player.worldObj.updateEntity(player);//同样的更新实体
                        }
                        }
                    }
            }
        }else{
            long i = System.nanoTime();
            FMLCommonHandler.instance().onPreServerTick();
            ++Server.tickCounter;
    
            if (Server.startProfiling)
            {
                Server.startProfiling = false;
                Server.theProfiler.profilingEnabled = true;
                Server.theProfiler.clearProfiling();
            }
    
            Server.theProfiler.startSection("root");
            Server.updateTimeLightAndEntities();
    
            if (i - Server.field_147142_T >= 5000000000L)
            {
                Server.field_147142_T = i;
                Server.field_147147_p.func_151319_a(new ServerStatusResponse.PlayerCountData(Server.getMaxPlayers(), Server.getCurrentPlayerCount()));
                GameProfile[] agameprofile = new GameProfile[Math.min(Server.getCurrentPlayerCount(), 12)];
                int j = MathHelper.getRandomIntegerInRange(Server.field_147146_q, 0, Server.getCurrentPlayerCount() - agameprofile.length);
    
                for (int k = 0; k < agameprofile.length; ++k)
                {
                    agameprofile[k] = ((EntityPlayerMP)Server.serverConfigManager.playerEntityList.get(j + k)).getGameProfile();
                }
    
                Collections.shuffle(Arrays.asList(agameprofile));
                Server.field_147147_p.func_151318_b().func_151330_a(agameprofile);
            }
    
            if (Server.tickCounter % 900 == 0)
            {
                Server.theProfiler.startSection("save");
                Server.serverConfigManager.saveAllPlayerData();
                Server.saveAllWorlds(true);
                Server.theProfiler.endSection();
            }
    
            Server.theProfiler.startSection("tallying");
            Server.tickTimeArray[Server.tickCounter % 100] = System.nanoTime() - i;
            Server.theProfiler.endSection();
            Server.theProfiler.startSection("snooper");
    
            if (!Server.usageSnooper.isSnooperRunning() && Server.tickCounter > 100)
            {
                Server.usageSnooper.startSnooper();
            }
    
            if (Server.tickCounter % 6000 == 0)
            {
                Server.usageSnooper.addMemoryStatsToSnooper();
            }
    
            Server.theProfiler.endSection();
            Server.theProfiler.endSection();
            FMLCommonHandler.instance().onPostServerTick();
        }
        
    }
    

}
