package com.howxu.infinitysword.proxy;

import com.howxu.infinitysword.Infinity;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    
    public void Pre(FMLPreInitializationEvent e){
        GameRegistry.registerItem(Infinity.CosmicSword,Infinity.CosmicSword.getUnlocalizedName());
        GameRegistry.registerItem(Infinity.ULSword,Infinity.ULSword.getUnlocalizedName());
    }

    public void Init(FMLInitializationEvent e){
    }

    public void Post(FMLPostInitializationEvent e) {

    }
}
