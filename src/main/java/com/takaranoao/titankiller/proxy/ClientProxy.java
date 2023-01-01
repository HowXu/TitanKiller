package com.takaranoao.titankiller.proxy;

import com.takaranoao.titankiller.Entity.RainbowLightningBolt;
import com.takaranoao.titankiller.Handler.ToolTipHandler;
import com.takaranoao.titankiller.Render.RainbowLightingRender;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
    @Override
    public void FMLPRELOAD(FMLPreInitializationEvent e) {
        super.FMLPRELOAD(e);
    }

    @Override
    public void FMLINITLOAD(FMLInitializationEvent e) {
        super.FMLINITLOAD(e);
        MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
        RenderingRegistry.registerEntityRenderingHandler(RainbowLightningBolt.class, new RainbowLightingRender());
    }

    @Override
    public void FMLPOSTLOAD(FMLPostInitializationEvent e) {
        super.FMLPOSTLOAD(e);
    }
}
