package com.takaranoao.titankiller.Core;

import com.takaranoao.titankiller.Event.TKPlayerEvent;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
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
            if (object instanceof net.minecraft.entity.player.EntityPlayerMP && !MinecraftForge.EVENT_BUS.post((Event)new TKPlayerEvent((EntityPlayer)object))) {
                list2.add(object); continue;
            }  if (object instanceof net.minecraft.entity.EntityAgeable || object instanceof net.minecraft.entity.passive.EntityAmbientCreature || object instanceof net.minecraft.entity.passive.EntityWaterMob) {
                it.remove();
            }
        }
        return list2;
}
}
