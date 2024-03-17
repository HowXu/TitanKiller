/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2023-01-01 19:40:50
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-01-01 20:08:28
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\render\WhiteBlackRender.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;

public class WhiteBlackRender {

    public static final ResourceLocation location = new ResourceLocation("shaders/post/desaturate.json");//渲染使用的json文件 在原版Minecraft就有
    //黑白渲染,这个涉及线程操作，必须用try
    public static void BlackAndWhiteScreen(Boolean Switch){

        try {
            Minecraft mc = Minecraft.getMinecraft();//获取线程
            if(Switch){
                ShaderGroup group = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), location);
                group.createBindFramebuffers(mc.displayWidth, mc.displayHeight);//获取窗口大小并进行渲染
                mc.entityRenderer.theShaderGroup = group;
            }else{
                mc.entityRenderer.theShaderGroup = null;//为false则关闭黑白渲染
            }
        } catch (Exception e) {

        }



    }

    public static boolean IsBlackAndWhiteScreen(){
        try {
            return Minecraft.getMinecraft().entityRenderer.theShaderGroup.getShaderGroupName() == location.toString();//判断是否开启渲染
        } catch (Exception e) {
            return false;
        }

    }
    
}
