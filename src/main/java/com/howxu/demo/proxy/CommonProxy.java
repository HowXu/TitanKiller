package com.howxu.demo.proxy;

import com.howxu.demo.Event.EventHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public void Pre(FMLPreInitializationEvent e){
         GameRegistry.registerItem(com.howxu.demo.Main.DemoItem, com.howxu.demo.Main.DemoItem.getUnlocalizedName());
    }

    public void Init(FMLInitializationEvent e){
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void Post(FMLPostInitializationEvent w){

    }
}
