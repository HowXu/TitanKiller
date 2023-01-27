/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 09:39:42
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-01-25 16:55:43
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\proxy\CommonProxy.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
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
