package com.takaranoao.titankiller;


import com.takaranoao.titankiller.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TitanKiller.MOD_ID, name = TitanKiller.NAME, version = TitanKiller.VERSION)
public class TitanKiller {

    public static final String MOD_ID = "titankiller";
    public static final String NAME = "titankiller";
    public static final String VERSION = "2.0";

    private final TitanKiller Instance;
    public TitanKiller(){
        Instance = this;
    }
    @SidedProxy(serverSide = "com.takaranoao.titankiller.proxy.CommonProxy",clientSide = "com.takaranoao.titankiller.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void FMLPRE(FMLPreInitializationEvent e){
        proxy.FMLPRELOAD(e);
    }

    @Mod.EventHandler
    public void FMLINIT(FMLInitializationEvent e){
        proxy.FMLINITLOAD(e);
    }

    @Mod.EventHandler
    public void FMLPOST(FMLPostInitializationEvent e){
        proxy.FMLPOSTLOAD(e);
    }

}
