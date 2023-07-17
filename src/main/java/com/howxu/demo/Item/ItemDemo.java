/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 12:02:32
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-07-17 10:40:23
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\Item\ItemDemo.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/*
 * @Author: HowXu howxu366@outlook.com
 * @Date: 2022-12-31 12:02:32
 * @LastEditors: HowXu howxu366@outlook.com
 * @LastEditTime: 2023-01-01 20:40:53
 * @FilePath: \DemoCore\src\main\java\com\howxu\demo\Item\ItemDemo.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package com.howxu.demo.Item;

import java.util.List;

import com.howxu.demo.Event.AntiDisarm;
import com.howxu.demo.Event.EventUtil;
import com.howxu.demo.Event.Hello;
import com.howxu.demo.Event.RegenUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDemo extends ItemSword{

    public ItemDemo(ToolMaterial p_i45356_1_) {
        super(p_i45356_1_);
        setUnlocalizedName("mydemosword");
        setCreativeTab(CreativeTabs.tabTools);
        setTextureName("minecraft:gold_sword");
    }
    @Override
    public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_,boolean p_77663_5_) {
        super.onUpdate(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
        //持有者更新
        if(p_77663_3_ instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) p_77663_3_;
            if(player.posY <= -45.0D){
                player.posY += 10D;//判断是否在-45以下，添加坐标（不是tp）
            }

            //已经拿了Demo的玩家是否已经加入数组
            if(!AntiDisarm.HasIt(player.getCommandSenderName())){
                    AntiDisarm.Add(player.getCommandSenderName());//没有则加一个
            }

        }
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        // TODO Auto-generated method stub
        //能不能丢出去，return false就丢不出去
        return true;
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        // 这个方法可以把名字写死
        return "你好".trim();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

        if(entity instanceof EntityLivingBase/*判断是否是可以被清空health的生物，tnt不能被清空 */){
        entity.getEntityData().setBoolean("BeDemoAttacked", true);//为实体设置一个NBT判定，但凡是被攻击的实体都会被标记，前面是标记名称，后面是值
        ((EntityLivingBase) entity).onDeath(DamageSource.outOfWorld);//发送掉出世界的信息，这个东西是可以自己写个类自定义的
    }
    
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {

        RegenUtil.Regen(p_77659_3_.worldObj, (int) p_77659_3_.posX, (int) p_77659_3_.posZ);//区块覆写

        //EventUtil.TimeStop = true;//右键时启动时间停止，但是我不推荐


        //右键时监听，为true改为false，为false改为true
        if(EventUtil.TimeStop){
            EventUtil.TimeStop = false;
        }else if(!EventUtil.TimeStop){
            EventUtil.TimeStop = true;
        }
        //时停和黑白渲染慎用，win11真的是垃圾

        //全图杀
        for(int i = 0;i < p_77659_2_.loadedEntityList.size();i++){
            //for循环遍历
            Entity entity = (Entity) p_77659_2_.loadedEntityList.get(i);//拿出来
            if(entity != p_77659_3_){//防止干死自己
            if(entity instanceof EntityLivingBase){
                EntityLivingBase Base = (EntityLivingBase) entity;//转换一下
                Base.getEntityData().setBoolean("BeDemoAttacked", true);//设置nbt标签，EventUtil接到了就会瞬杀
                Base.onDeath(DamageSource.outOfWorld);//这个死亡信息完全可以自定义
            }
        }
    }

        return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    
    @Override
    public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
        //EventUtil.TimeStop = false;//取消右键即取消时间停止，但是我不推荐
        super.onPlayerStoppedUsing(p_77615_1_, p_77615_2_, p_77615_3_, p_77615_4_);
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {

        return EnumRarity.epic;//返回物品附魔特效   super.getRarity(p_77661_1_)
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {

        return false;//改成true物品无规则附魔   super.hasEffect(p_77661_1_)
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {

        return EnumAction.bow;//故名思义，返回物品蓄力动画    super.getItemUseAction(p_77661_1_)
    }

    @Override
    //检测实体挥动物品
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        Vec3 Vector = entityLiving.getLookVec();//获取实体朝向
        for(int i = 0 ; i  < 512/*攻击范围多大这个就多大 */;i++){
            double x = Vector.xCoord * i;
            double y = entityLiving.getEyeHeight() + Vector.yCoord * i;
            double z = Vector.zCoord * i;
            //扫描获取所有实体
            List<Entity> Entities = entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(entityLiving, entityLiving.boundingBox.expand(3.0D, 3.0D, 3.0D).offset(x, y, z));
            //获取玩家模型，把模型扩大三倍之后就找不到玩家了，然后用expand调整一下玩家位置
            //遍历所有的实体
            for(Entity entity : Entities){
                if(entity instanceof EntityLivingBase){
                    ((EntityLivingBase)entity).onDeath(DamageSource.outOfWorld);//掉出世界
                        if(entity instanceof EntityPlayer && entityLiving instanceof EntityPlayer){
                    //嘲讽
                    Hello.ChatPrint((EntityPlayer) entity, new StringBuilder().append(((EntityPlayer)entityLiving).getCommandSenderName()).append("垃圾").toString());
                        }
                    //System.out.println(entity.getCommandSenderName());
                }
                //System.out.println("KMLSLAKJDJIAUIDAWDHUQWD");
                entity.worldObj.removePlayerEntityDangerously(entity);
                entity.isDead = true;//防止重写setdead，直接返回死亡
            }
        }
        return super.onEntitySwing(entityLiving, stack);
    }

}
