package com.howxu.infinitysword.Render.InfinityRender;

import com.howxu.infinitysword.Event.InfinityRenderEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.World;
import org.lwjgl.opengl.ARBShaderObjects;

import java.lang.reflect.Field;

public class CosmicRenderUtil {
    public static final ShaderCallback shaderCallback = new ShaderCallback() {
        public void call(int shader) {
            Minecraft mc = Minecraft.getMinecraft();
            float yaw = 0.0F;
            float pitch = 0.0F;
            float scale = 1.0F;
            if (!CosmicRenderUtil.inventoryRender) {
                yaw = (float)((double)(mc.thePlayer.rotationYaw * 2.0F) * Math.PI / 360.0);
                pitch = -((float)((double)(mc.thePlayer.rotationPitch * 2.0F) * Math.PI / 360.0));
            } else {
                scale = 25.0F;
            }

            int x = ARBShaderObjects.glGetUniformLocationARB(shader, "yaw");
            ARBShaderObjects.glUniform1fARB(x, yaw);
            int z = ARBShaderObjects.glGetUniformLocationARB(shader, "pitch");
            ARBShaderObjects.glUniform1fARB(z, pitch);
            int l = ARBShaderObjects.glGetUniformLocationARB(shader, "lightlevel");
            ARBShaderObjects.glUniform3fARB(l, CosmicRenderUtil.lightlevel[0], CosmicRenderUtil.lightlevel[1], CosmicRenderUtil.lightlevel[2]);
            int lightmix = ARBShaderObjects.glGetUniformLocationARB(shader, "lightmix");
            ARBShaderObjects.glUniform1fARB(lightmix, 0.2F);
            int uvs = ARBShaderObjects.glGetUniformLocationARB(shader, "cosmicuvs");
            ARBShaderObjects.glUniformMatrix2ARB(uvs, false, InfinityRenderEvent.cosmicUVs);//这里使用event中添加的新文件
            int s = ARBShaderObjects.glGetUniformLocationARB(shader, "externalScale");
            ARBShaderObjects.glUniform1fARB(s, scale);
            int o = ARBShaderObjects.glGetUniformLocationARB(shader, "opacity");
            ARBShaderObjects.glUniform1fARB(o, CosmicRenderUtil.cosmicOpacity);
        }
    };
    public static float[] lightlevel = new float[3];
    public static String[] lightmapobf = new String[]{"lightmapColors", "field_78504_Q", "U"};
    public static boolean inventoryRender = false;
    public static float cosmicOpacity = 1.0F;
    private static Field mapfield;

    public CosmicRenderUtil() {
    }

    public static void useShader() {
        //调用ShaderHelper的方法，因为shader文件在其他方法中还是要使用，所以这里不能换
        ShaderHelper.useShader(ShaderHelper.cosmicShader, shaderCallback);
    }

    public static void releaseShader() {
        ShaderHelper.releaseShader();
    }

    public static void setLightFromLocation(World world, int x, int y, int z) {
        if (world == null) {
            setLightLevel(1.0F);
        } else {
            int coord = world.getLightBrightnessForSkyBlocks(x, y, z, 0);
            int[] map = null;

            try {
                map = (int[])((int[])mapfield.get(Minecraft.getMinecraft().entityRenderer));
            } catch (Exception var9) {

            }

            if (map == null) {
                setLightLevel(1.0F);
            } else {
                int mx = coord % 65536 / 16;
                int my = coord / 65536 / 16;
                int lightcolour = map[my * 16 + mx];
                setLightLevel((float)(lightcolour >> 16 & 255) / 256.0F, (float)(lightcolour >> 8 & 255) / 256.0F, (float)(lightcolour & 255) / 256.0F);
            }
        }
    }

    public static void setLightLevel(float level) {
        setLightLevel(level, level, level);
    }

    public static void setLightLevel(float r, float g, float b) {
        lightlevel[0] = Math.max(0.0F, Math.min(1.0F, r));
        lightlevel[1] = Math.max(0.0F, Math.min(1.0F, g));
        lightlevel[2] = Math.max(0.0F, Math.min(1.0F, b));
    }

    public static void bindItemTexture() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
    }

    static {
        mapfield = ReflectionHelper.findField(EntityRenderer.class, lightmapobf);
    }
}
