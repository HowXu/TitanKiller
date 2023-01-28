package com.howxu.infinitysword.proxy;

import com.howxu.infinitysword.Event.InfinityRenderEvent;
import com.howxu.infinitysword.Infinity;
import com.howxu.infinitysword.Render.InfinityRender.CosmicRenderCore;
import com.howxu.infinitysword.Render.InfinityRender.ShaderHelper;
import com.howxu.infinitysword.Render.UltimateRender.RenderUltimateCore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

    @Override
    public void Pre(FMLPreInitializationEvent e) {
        super.Pre(e);

        RenderUltimateCore UltimateCore = new RenderUltimateCore();
        MinecraftForgeClient.registerItemRenderer(Infinity.ULSword,UltimateCore);

        CosmicRenderCore InfinityCore = new CosmicRenderCore();
        MinecraftForgeClient.registerItemRenderer(Infinity.CosmicSword,InfinityCore);//注册物品渲染器

        InfinityRenderEvent renderEvent =  new InfinityRenderEvent();
        MinecraftForge.EVENT_BUS.register(renderEvent);//渲染事件注册
        FMLCommonHandler.instance().bus().register(renderEvent);//cosmic文件注册
        ShaderHelper.initShaders();//shader文件注册
    }

    @Override
    public void Init(FMLInitializationEvent e) {
        super.Init(e);
    }

    @Override
    public void Post(FMLPostInitializationEvent e) {
        super.Post(e);
    }
}
