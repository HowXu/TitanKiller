/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 09:45:41
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2022-12-31 11:50:15
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\CoreMod\DemoContainer.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.CoreMod;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;


//创建容器
@MCVersion(value = "1.7.10")
@Name(value = "DemoCore")
public class DemoContainer extends DummyModContainer{
    public static ModMetadata metadata;
    public DemoContainer(){
        super(new ModMetadata());
        metadata = getMetadata();
        metadata.modId = "DemoCore";
        metadata.authorList = Arrays.asList(new String[] { "HowXu" });
        metadata.name = "DemoCore";
        metadata.version = "1.7.10";
        //这里写错的话就不会加载CoreMod，别问为什么知道
    }
    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        // TODO Auto-generated method stub
        bus.register(this);
        return true;
    }
}
