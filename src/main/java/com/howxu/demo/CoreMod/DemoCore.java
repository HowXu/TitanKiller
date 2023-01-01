/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 09:45:27
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2022-12-31 11:51:31
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\CoreMod\DemoCore.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.CoreMod;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

//创建核心mod
public class DemoCore implements IFMLLoadingPlugin{

  //  public static boolean Debug = false;

    @Override
    public String[] getASMTransformerClass() {
        // TODO Auto-generated method stub
        return new String[]{"com.howxu.demo.CoreMod.DemoTransformer",""};//这里可以有很多个transformer
    }

    @Override
    public String getModContainerClass() {
        // TODO Auto-generated method stub
        return "com.howxu.demo.CoreMod.DemoContainer";
    }

    @Override
    public String getSetupClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
       // Debug = (Boolean) data.get("runtimeDeobfscationEnabled");
        //确定是否为编译环境
    }

    @Override
    public String getAccessTransformerClass() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
