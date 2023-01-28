package com.howxu.infinitysword.Item;

import com.howxu.infinitysword.Render.InfinityRender.RenderItem.RenderItemCosmic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;

public class ItemCosmicSword extends ItemSword implements RenderItemCosmic {

    private IIcon cosmicMask;
    private IIcon pommel;
    //这两个字段设定物品的多重材质来源

    public ItemCosmicSword() {
        super(ToolMaterial.EMERALD);
        setUnlocalizedName("infinity");
        setTextureName("infinity:infinity");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getMaskTexture(ItemStack stack, EntityPlayer player) {
        return cosmicMask;//在渲染类中即使用这个方法获取多重材质的路径
    }

    @SideOnly(Side.CLIENT)
    public float getMaskMultiplier(ItemStack stack, EntityPlayer player) {
        return 1.0F;//设定渲染速度，其实内置就是1.0F。
    }

    @SideOnly(Side.CLIENT)//物品渲染是client事件
    public void registerIcons(IIconRegister ir) {
        super.registerIcons(ir);
        this.cosmicMask = ir.registerIcon("infinity:infinity_sword_mask");
        this.pommel = ir.registerIcon("infinity:infinity_sword_pommel");//我懒得改了
        //注册多重材质，其与modid绑定路径，放置在texture的items路径下
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 ? this.pommel : super.getIcon(stack, pass);
        //获取材质
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;//告诉渲染器需要多渲染，其实不告诉也行
    }

}
