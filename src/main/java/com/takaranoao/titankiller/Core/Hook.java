package com.takaranoao.titankiller.Core;

import com.google.common.base.Throwables;
import com.takaranoao.titankiller.Event.TKPlayerEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.IEventListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Hook {
    //hook方法重写泰坦生物的触发机制，这样不会被凋零斯拉的闪电秒杀
    public static List hook(List list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        LinkedList<Object> list2 = new LinkedList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object object = it.next();
            if (object instanceof net.minecraft.entity.player.EntityPlayerMP && !MinecraftForge.EVENT_BUS.post(new TKPlayerEvent((EntityPlayer)object))) {
                list2.add(object); continue;
            }  if (object instanceof net.minecraft.entity.EntityAgeable || object instanceof net.minecraft.entity.passive.EntityAmbientCreature || object instanceof net.minecraft.entity.passive.EntityWaterMob) {
                it.remove();
            }
        }
        return list2;
    }

    public static void kickPlayerFromServer(NetHandlerPlayServer server,String p_147360_1_)
    {
        final ChatComponentText chatcomponenttext = new ChatComponentText(p_147360_1_);
        server.netManager.scheduleOutboundPacket(new S40PacketDisconnect(chatcomponenttext), new GenericFutureListener[] {
                new GenericFutureListener() {
                    private static final String __OBFID = "CL_00001453";
                    public void operationComplete(Future p_operationComplete_1_)
                    {
                        //server.netManager.closeChannel(chatcomponenttext);
                    }
        }
        });
        server.netManager.disableAutoRead();
    }

    public static void kick(String a){

    }


    //重写post方法
    public static boolean post(EventBus bus, Event event){
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
}
