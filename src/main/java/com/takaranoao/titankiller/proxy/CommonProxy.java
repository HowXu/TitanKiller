package com.takaranoao.titankiller.proxy;

import com.takaranoao.titankiller.Loader.EntityLoader;
import com.takaranoao.titankiller.Loader.EventLoader;
import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.Loader.RecipeLoader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void FMLPRELOAD(FMLPreInitializationEvent e){
        new ItemLoader(e);
        new EntityLoader(e);
    }
    public void FMLINITLOAD(FMLInitializationEvent e){
        new EventLoader(e);
        new RecipeLoader(e);
    }
    public void FMLPOSTLOAD(FMLPostInitializationEvent e){

    }
}
