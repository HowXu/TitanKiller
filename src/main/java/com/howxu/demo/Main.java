package com.howxu.demo;

import com.howxu.demo.Item.ItemDemo;
import com.howxu.demo.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;

/*这是一个CoreMod的Demo */

@Mod(name = "Demo", modid = "demo", version = "1.0")
public class Main {

    public static Main instance;

    public static final ItemSword DemoItem = new ItemDemo(ToolMaterial.GOLD);;

    public Main() {
        instance = this;
    }

    @SidedProxy(serverSide = "com.howxu.demo.proxy.CommonProxy", clientSide = "com.howxu.demo.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void FMLPRE(FMLPreInitializationEvent e) {
        proxy.Pre(e);
    }

    @EventHandler
    public void FMLINIT(FMLInitializationEvent e) {
        proxy.Init(e);
    }

    @EventHandler
    public void FMLPOST(FMLPostInitializationEvent e) {
        proxy.Post(e);
    }

}
