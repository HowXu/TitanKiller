package com.takaranoao.titankiller.tools;

import com.takaranoao.titankiller.Loader.ItemLoader;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HealthKiller {


    public static void RemoveTitanHealth(ItemStack item, EntityPlayer player) {
        if (!Loader.isModLoaded("thetitans")) {
            return;
        }
        List<Entity> entitylist = player.worldObj.loadedEntityList;
        if (entitylist != null && !entitylist.isEmpty())
        {
            for (int i = 0; i < entitylist.size(); i++) {
                Entity entity2 = entitylist.get(i);
                if (entity2 instanceof EntityTitanSpirit)
                {
                    entity2.setDead();
                }
            }
        }
        Vec3 vec3 = player.getLook(1.0F);
        double dx = vec3.xCoord * 6.0D;
        double dy = player.getEyeHeight() + vec3.yCoord * 6.0D;
        double dz = vec3.zCoord * 6.0D;
        List<Entity> list3 = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(50.0D, 50.0D, 50.0D).offset(dx, dy, dz));

        List<Entity> list1 = new ArrayList<Entity>(player.worldObj.loadedEntityList);
        if (list1 != null && !list1.isEmpty())
        {
            for (int i = 0; i < list1.size(); i++) {
                Entity entity1 = list1.get(i);
                if (entity1 != player && entity1 instanceof EntityLivingBase)
                {
                    if (!entity1.isDead && entity1 instanceof EntityTitan) {
                        ((EntityTitan)entity1).setTitanHealth(0.0F);
                        // ((EntityTitan)entity1).getHealth() - ((EntityTitan)entity1).getHealth() / 2.0F
                        entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 40.0F);
                        if (((EntityTitan)entity1).getHealth() >= 0.0F)
                        {
                            ((EntityTitan)entity1).setTitanHealth(0.0F);
                        }
                    }
                }
            }
        }
    }

    public static void RemoveEntityHealth(ItemStack item, EntityPlayer player) {
        Vec3 vec3 = player.getLook(1.0F);
        double dx1 = vec3.xCoord * 4.0D;
        double dy1 = player.getEyeHeight() + vec3.yCoord * 4.0D;
        double dz1 = vec3.zCoord * 4.0D;
        List<Entity> list11 = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4.0D, 4.0D, 4.0D).offset(dx1, dy1, dz1));
        if (list11 != null && !list11.isEmpty())
        {
            for (int I = 0; I < list11.size(); I++) {
                Entity entity1 = list11.get(I);

                if (entity1 != player && entity1 instanceof EntityLivingBase) {
                    //entity1.motionX = (-MathHelper.sin(entity1.rotationYaw * 3.1415927F / 180.0F) * 2.0F) + 1.0D;让子弹飞
                    //entity1.motionY = 3.0D;
                    //entity1.motionZ = (MathHelper.cos(entity1.rotationYaw * 3.1415927F / 180.0F) * 2.0F) + 1.0D;
                    if(entity1 instanceof EntityPlayer){
                        EntityPlayer mplayer = (EntityPlayer) entity1;
                        if(!(mplayer.inventory.hasItem(ItemLoader.titankiller))){
                            if(!(mplayer.worldObj.isRemote)){
                                mplayer.inventory.dropAllItems();//解决砧板剑的无敌
                            }
                        }
                    }
                    EntityTool.setHealth((EntityLivingBase)entity1,0.0F );
                    if(entity1 instanceof EntityPlayer){
                        EntityPlayer target =  (EntityPlayer) entity1;
                        //target.attackEntityFrom((new DamageSourceInfinitySword((Entity)player)).setFireDamage().setDamageBypassesArmor(), 4.0F);
                        //target.attackEntityFrom(DamageSource.causePlayerDamage(player), 4.0F);
                        //target.inventory.dropAllItems();//所有物品掉落
                    }
                    //血量清空的部分,((EntityLivingBase)entity1).getHealth() / 4.0F
                    //entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 111110.0F);//实体死亡信息
                }
            }
            //死亡播报的部分
            List<Entity> list2 = list11;
            list2.remove(player);
            for (int i = 0; i < list2.size();i++){
                Entity entity1 = list2.get(i);
                System.out.println("Have had Player" + entity1.getCommandSenderName() + i);
                System.out.println("SIZE = " + list2.size());
                if (entity1 instanceof EntityPlayer){
                    Logger logger = Logger.getLogger("TitanKiller2");
                    logger.log(Level.WARNING,"可以提的");
                    EntityLivingBase target = (EntityLivingBase) entity1;
                    EntityPlayer MStarget = (EntityPlayer) target;
                    EntityTool.ChatKiller(player,MStarget);//打印死亡输出
                }
            }
        }
        double dx = vec3.xCoord * 6.0D;
        double dy = player.getEyeHeight() + vec3.yCoord * 6.0D;
        double dz = vec3.zCoord * 6.0D;
        List<Entity> list1 = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(6.0D, 6.0D, 6.0D).offset(dx, dy, dz));
        if (list1 != null && !list1.isEmpty())
        {
            for (int i11 = 0; i11 < list1.size(); i11++) {
                Entity entity1 = list1.get(i11);
                if (entity1 != player && entity1 instanceof EntityLivingBase) {
                    if (player.isSneaking()) {
                        //entity1.motionY++;让子弹飞
                    }
                    try {
                        ReflectionHelper.findField(EntityLivingBase.class, "recentlyHit", "field_70718_bc").setInt(entity1, 100);
                    }
                    catch (Exception exception) {}
                    //设置生物的血量和定义上的死亡，but会抽搐
                    /*if (player.isSneaking() && entity1 instanceof EntityLivingBase) {
                        EntityTool.setHealth((EntityLivingBase)entity1, 0.0F);
                        if (entity1 instanceof EntityPlayer){
                            EntityPlayer attackedone = (EntityPlayer) entity1;
                            //attackedone.func_110142_aN().func_94547_a(new TKDamageSource((Entity)player), attackedone.getHealth(), attackedone.getHealth());
                        }
                        entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 11100.0F);
                        ((EntityLivingBase)entity1).deathTime++;
                        if (!(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && ((EntityLivingBase)entity1).deathTime > 1) {
                            ((EntityLivingBase)entity1).isDead = true;
                            if (!entity1.isEntityAlive()) {
                                ((EntityLivingBase)entity1).onDeath(DamageSource.magic);
                                entity1.worldObj.setEntityState(entity1, (byte)3);
                            }
                        }
                        ((EntityLivingBase)entity1).getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(((EntityLivingBase)entity1).getHealth() - 5000.0F, 0.0F, ((EntityLivingBase)entity1).getMaxHealth())));
                    }//*/
                }
            }
        }
    }
}
