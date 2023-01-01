package com.takaranoao.titankiller.Loader;

import com.takaranoao.titankiller.Entity.RainbowLightningBolt;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityLoader {
    public EntityLoader(FMLPreInitializationEvent e){
        EntityRegistry.registerGlobalEntityID(RainbowLightningBolt.class, "RainbowLightningBolt", EntityRegistry.findGlobalUniqueEntityId());
    }
}
