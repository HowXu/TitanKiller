package com.howxu.infinitysword.Event;

import com.howxu.infinitysword.Render.InfinityRender.CosmicRenderUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class InfinityRenderEvent {
    public static FloatBuffer cosmicUVs;
    public static String[] cosmicTextures = new String[10];
    public static IIcon[] cosmicIcons;

    static {
        for (int i = 0; i < 10; ++i) {
            cosmicTextures[i] = "infinity:cosmic" + i;
            //设置cosmic，也就是那一堆星星的目录地址
            //因为是添加非Forge定义的文件，要拿到FMLCommon里面注册一下
        }
        cosmicUVs = BufferUtils.createFloatBuffer(4 * cosmicTextures.length);
        cosmicIcons = new IIcon[cosmicTextures.length];
    }

    @SubscribeEvent
    public void letsMakeAQuilt(TextureStitchEvent.Pre event) {
        if (((TextureStitchEvent) event).map.getTextureType() != 1)
            return;
        for (int i = 0; i < cosmicTextures.length; i++) {
            IIcon icon = ((TextureStitchEvent) event).map.registerIcon(cosmicTextures[i]);
            cosmicIcons[i] = icon;
        }
    }

    @SubscribeEvent
    public void pushTheCosmicFancinessToTheLimit(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            cosmicUVs = BufferUtils.createFloatBuffer(4 * cosmicIcons.length);

            for (int i = 0; i < cosmicIcons.length; ++i) {
                IIcon icon = cosmicIcons[i];
                cosmicUVs.put(icon.getMinU());//如果不在FMLCommon注册这里会空指针报错(废话)
                cosmicUVs.put(icon.getMinV());
                cosmicUVs.put(icon.getMaxU());
                cosmicUVs.put(icon.getMaxV());
            }

            cosmicUVs.flip();
        }

    }

    @SubscribeEvent
    public void makeCosmicStuffLessDumbInGUIs(GuiScreenEvent.DrawScreenEvent.Pre event) {
        CosmicRenderUtil.inventoryRender = true;
    }

    @SubscribeEvent
    public void finishMakingCosmicStuffLessDumbInGUIs(GuiScreenEvent.DrawScreenEvent.Post event) {
        CosmicRenderUtil.inventoryRender = false;
    }

    @SubscribeEvent
    public void weMadeAQuilt(TextureStitchEvent.Post event) {
        CosmicRenderUtil.bindItemTexture();
    }
}
