package com.takaranoao.titankiller.Loader;

import com.takaranoao.titankiller.Handler.LivingAttack;
import com.takaranoao.titankiller.Handler.RefuseEvent;
import com.takaranoao.titankiller.Handler.RefuseHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventLoader {

    public EventLoader(FMLInitializationEvent e){
        MinecraftForge.EVENT_BUS.register(new RefuseEvent());
        MinecraftForge.EVENT_BUS.register(new LivingAttack());
        MinecraftForge.EVENT_BUS.register(new RefuseHandler());
    }

}
