package com.howxu.infinitysword.Render.InfinityRender;

import com.howxu.infinitysword.Render.InfinityRender.RenderItem.RenderItemCosmic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

//核心类，用于渲染多层材质
public class CosmicRenderCore implements IItemRenderer
{
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        RenderItem r;
        Minecraft mc = Minecraft.getMinecraft();
        processLightLevel(type, item, data);

        switch (type) {
            case ENTITY:
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
                if (item.isOnItemFrame())
                    GL11.glTranslatef(0.0F, -0.3F, 0.01F);
                render(item, null);
                GL11.glPopMatrix();
                break;


            case EQUIPPED:
                render(item, (data[1] instanceof EntityPlayer) ? (EntityPlayer) data[1] : null);
                break;

            case EQUIPPED_FIRST_PERSON:
                render(item, (data[1] instanceof EntityPlayer) ? (EntityPlayer) data[1] : null);
                break;

            case INVENTORY:
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                RenderHelper.enableGUIStandardItemLighting();

                GL11.glDisable(3008);
                GL11.glDisable(2929);

                r = RenderItem.getInstance();
                r.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), item, 0, 0, true);

                if (item.getItem() instanceof RenderItemCosmic) {
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    RenderHelper.enableGUIStandardItemLighting();

                    GL11.glDisable(3008);
                    GL11.glDisable(2929);

                    RenderItemCosmic icri = (RenderItemCosmic) item.getItem();

                    //下面三个是核心渲染
                    CosmicRenderUtil.cosmicOpacity = icri.getMaskMultiplier(item, null);//其实这个没必要，因为默认里就是1.0F
                    CosmicRenderUtil.inventoryRender = true;//是否物品栏渲染
                    CosmicRenderUtil.useShader();//开始渲染，核心类

                    IIcon cosmicicon = icri.getMaskTexture(item, null);

                    GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);

                    float minu = cosmicicon.getMinU();
                    float maxu = cosmicicon.getMaxU();
                    float minv = cosmicicon.getMinV();
                    float maxv = cosmicicon.getMaxV();

                    Tessellator t = Tessellator.instance;

                    t.startDrawingQuads();
                    t.addVertexWithUV(0.0D, 0.0D, 0.0D, minu, minv);
                    t.addVertexWithUV(0.0D, 16.0D, 0.0D, minu, maxv);
                    t.addVertexWithUV(16.0D, 16.0D, 0.0D, maxu, maxv);
                    t.addVertexWithUV(16.0D, 0.0D, 0.0D, maxu, minv);
                    t.draw();

                    CosmicRenderUtil.releaseShader();
                    CosmicRenderUtil.inventoryRender = false;
                }

                GL11.glEnable(3008);
                GL11.glEnable(32826);
                GL11.glEnable(2929);

                r.renderWithColor = true;

                GL11.glDisable(3042);
                GL11.glPopMatrix();
                break;
        }
    }

    public void render(ItemStack item, EntityPlayer player) {
        int passes = 1;
        if (item.getItem().requiresMultipleRenderPasses()) {
            passes = item.getItem().getRenderPasses(item.getItemDamage());
        }

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);


        float scale = 0.0625F;


        for (int i = 0; i < passes; i++) {

            IIcon icon = getStackIcon(item, i, player);


            float f = icon.getMinU();
            float f1 = icon.getMaxU();
            float f2 = icon.getMinV();
            float f3 = icon.getMaxV();

            int colour = item.getItem().getColorFromItemStack(item, i);
            float r = (colour >> 16 & 0xFF) / 255.0F;
            float g = (colour >> 8 & 0xFF) / 255.0F;
            float b = (colour & 0xFF) / 255.0F;
            GL11.glColor4f(r, g, b, 1.0F);
            ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), scale);
        }

        if (item.getItem() instanceof RenderItemCosmic) {
            GL11.glDisable(3008);
            GL11.glDepthFunc(514);
            RenderItemCosmic icri = (RenderItemCosmic) item.getItem();
            CosmicRenderUtil.cosmicOpacity = icri.getMaskMultiplier(item, player);
            CosmicRenderUtil.useShader();

            IIcon cosmicicon = icri.getMaskTexture(item, player);

            float minu = cosmicicon.getMinU();
            float maxu = cosmicicon.getMaxU();
            float minv = cosmicicon.getMinV();
            float maxv = cosmicicon.getMaxV();
            ItemRenderer.renderItemIn2D(Tessellator.instance, maxu, minv, minu, maxv, cosmicicon.getIconWidth(), cosmicicon.getIconHeight(), scale);
            CosmicRenderUtil.releaseShader();
            GL11.glDepthFunc(515);
            GL11.glEnable(3008);
        }

        GL11.glDisable(3042);
        GL11.glPopMatrix();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void processLightLevel(ItemRenderType type, ItemStack item, Object... data) {
        EntityItem entityItem;
        EntityLivingBase ent;
        switch (type) {
            case ENTITY:
                entityItem = (EntityItem) data[1];
                if (entityItem != null) {
                    CosmicRenderUtil.setLightFromLocation(((Entity) entityItem).worldObj, MathHelper.floor_double(((Entity) entityItem).posX), MathHelper.floor_double(((Entity) entityItem).posY), MathHelper.floor_double(((Entity) entityItem).posZ));
                }
                return;

            case EQUIPPED:
                ent = (EntityLivingBase) data[1];
                if (ent != null) {
                    CosmicRenderUtil.setLightFromLocation(((Entity) ent).worldObj, MathHelper.floor_double(((Entity) ent).posX), MathHelper.floor_double(((Entity) ent).posY), MathHelper.floor_double(((Entity) ent).posZ));
                }
                return;

            case EQUIPPED_FIRST_PERSON:
                ent = (EntityLivingBase) data[1];
                if (ent != null) {
                    CosmicRenderUtil.setLightFromLocation(((Entity) ent).worldObj, MathHelper.floor_double(((Entity) ent).posX), MathHelper.floor_double(((Entity) ent).posY), MathHelper.floor_double(((Entity) ent).posZ));
                }
                return;

            case INVENTORY:
                CosmicRenderUtil.setLightLevel(1.2F);
                return;
        }

        CosmicRenderUtil.setLightLevel(1.0F);
    }


    public IIcon getStackIcon(ItemStack stack, int pass, EntityPlayer player) {
        return stack.getItem().getIcon(stack, pass);
    }
}

