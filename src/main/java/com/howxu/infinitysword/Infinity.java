package com.howxu.infinitysword;

import com.howxu.infinitysword.Item.ItemCosmicSword;
import com.howxu.infinitysword.Item.ItemULSword;
import com.howxu.infinitysword.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.ItemSword;

@Mod(name = "infinity",modid = "infinity",version = "1.0")
public class Infinity {

    public static final ItemSword CosmicSword = new ItemCosmicSword();
    public static final ItemSword ULSword = new ItemULSword();

    public static Infinity instance;
    public Infinity(){
        instance = this;
    }


    @SidedProxy(serverSide = "com.howxu.infinitysword.proxy.CommonProxy", clientSide = "com.howxu.infinitysword.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void FMLPRE(FMLPreInitializationEvent e) {
        proxy.Pre(e);
    }

    @Mod.EventHandler
    public void FMLINIT(FMLInitializationEvent e) {
        proxy.Init(e);
    }

    @Mod.EventHandler
    public void FMLPOST(FMLPostInitializationEvent e) {
        proxy.Post(e);
    }

}
