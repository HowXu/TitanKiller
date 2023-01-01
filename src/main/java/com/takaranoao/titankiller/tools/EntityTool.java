package com.takaranoao.titankiller.tools;

import com.takaranoao.titankiller.Entity.RainbowLightningBolt;
import com.takaranoao.titankiller.Item.WoodenSword;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityTool {

    public static  boolean HasPrinted = false;

    public static void LightingWhileHasTitans(World world,EntityPlayer player){
        List<Entity> allEntities = new ArrayList<Entity>(((Entity)player).worldObj.loadedEntityList);

        for(int i = 0;i < allEntities.size();i++) {
            if (Loader.isModLoaded("thetitans")) {
                if (allEntities.get(i) instanceof EntityTitan) {
                    RainbowLighting(world, player);
                    System.out.println("杨值四");
                } else {
                    System.out.println("No sheep");
                }
            }
        }
    }

    public static void RainbowLighting(World world,EntityPlayer player){
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            System.out.println("雷电");
            double angle = random.nextDouble() * 20.0D * Math.PI;
            double distance = random.nextGaussian() * 100.0D;
            double x = Math.sin(angle) * distance + ((Entity)player).posX;
            double z = Math.cos(angle) * distance + ((Entity)player).posZ;
            RainbowLightningBolt entitylighting = new RainbowLightningBolt(world, x, world.getPrecipitationHeight((int)x, (int)z), z);
            world.spawnEntityInWorld((Entity)entitylighting); }
    }

    public static void PickItems(EntityPlayer player){

        List<Entity> allEntities = new ArrayList<Entity>(((Entity)player).worldObj.loadedEntityList);
        List<Entity> items = new ArrayList<Entity>();

        for (Entity entity : allEntities) {
            if (entity == null || entity instanceof EntityPlayer) {
                continue;
            }
            if (entity instanceof EntityItem || entity instanceof EntityXPOrb) {
                items.add(entity);
                continue;
            }
        }
        if (!((Entity)player).worldObj.isRemote){
            for (Entity item : items) {
                if (item instanceof EntityItem) {
                    ((EntityItem)item).onCollideWithPlayer(player);
                    continue;
                }
                if (item instanceof EntityXPOrb) {
                    player.xpCooldown = 0;
                    ((EntityXPOrb)item).onCollideWithPlayer(player);
                }
            }
        }
    }

    public static boolean HasSubscribed(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() instanceof WoodenSword) {
                return true;
            }
        }
        return false;
    }

    public static void setHealth(EntityLivingBase target, float f) {
        target.getDataWatcher().updateObject(6, MathHelper.clamp_float(f, 0.0F, target.getMaxHealth()));
    }

    public void RemoveEntityHealth(ItemStack item, EntityPlayer player) {
        Vec3 vec3 = player.getLook(1.0F);
        double dx1 = vec3.xCoord * 4.0D;
        double dy1 = player.getEyeHeight() + vec3.yCoord * 4.0D;
        double dz1 = vec3.zCoord * 4.0D;
        List<Entity> list11 = player.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.expand(4.0D, 4.0D, 4.0D).offset(dx1, dy1, dz1));
        if (list11 != null && !list11.isEmpty())
        {
            for (int i211 = 0; i211 < list11.size(); i211++) {

                Entity entity1 = list11.get(i211);
                if (entity1 != player && entity1 instanceof EntityLivingBase) {

                    entity1.motionX = (-MathHelper.sin(entity1.rotationYaw * 3.1415927F / 180.0F) * 2.0F) + 1.0D;
                    entity1.motionY = 3.0D;
                    entity1.motionZ = (MathHelper.cos(entity1.rotationYaw * 3.1415927F / 180.0F) * 2.0F) + 1.0D;
                    setHealth((EntityLivingBase)entity1, ((EntityLivingBase)entity1).getHealth() / 4.0F);
                    entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.0F);
                }
            }
        }
        double dx = vec3.xCoord * 6.0D;
        double dy = player.getEyeHeight() + vec3.yCoord * 6.0D;
        double dz = vec3.zCoord * 6.0D;
        List<Entity> list1 = player.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.expand(6.0D, 6.0D, 6.0D).offset(dx, dy, dz));
        if (list1 != null && !list1.isEmpty())
        {
            for (int i11 = 0; i11 < list1.size(); i11++) {
                Entity entity1 = list1.get(i11);
                if (entity1 != player && entity1 instanceof EntityLivingBase) {
                    if (player.isSneaking()) {
                        entity1.motionY++;
                    }

                    try {
                        ReflectionHelper.findField(EntityLivingBase.class, new String[] { "recentlyHit", "field_70718_bc" }).setInt(entity1, 100);
                    }
                    catch (Exception exception) {}

                    if (player.isSneaking() && entity1 instanceof EntityLivingBase) {
                        setHealth((EntityLivingBase)entity1, 0.0F);
                        entity1.attackEntityFrom(DamageSource.causePlayerDamage(player), 11100.0F);
                        ((EntityLivingBase)entity1).deathTime++;
                        if (!(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && ((EntityLivingBase)entity1).deathTime > 1) {

                            ((EntityLivingBase)entity1).isDead = true;
                            if (!entity1.isEntityAlive()) {

                                ((EntityLivingBase)entity1).onDeath(DamageSource.magic);
                                entity1.worldObj.setEntityState(entity1, (byte)3);
                            }
                        }
                        (entity1).getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(((EntityLivingBase)entity1).getHealth() - 5000.0F, 0.0F, ((EntityLivingBase)entity1).getMaxHealth())));
                    }
                }
            }
        }
    }

    public static void AttackEntityPlayer(World worldObj, Entity entity2, EntityPlayer entityPlayer) {
         if (worldObj.isRemote) {
              return;
           }
            if (entity2.isDead) {
                return;
             }

     if (entity2 instanceof EntityPlayer) {
            EntityPlayer entityE = (EntityPlayer)entity2;
             if (!entityE.isDead) {

                 if (entityE.getHealth() <= 0.0F) {
                         entityE.setDead();
                 }

                 entityE.onDeath((DamageSource)new EntityDamageSource("killer", (Entity)entityPlayer));
                 entityE.setLastAttacker((Entity)entityPlayer);
                 entityE.func_110142_aN().func_94547_a((DamageSource)new EntityDamageSource("killer", (Entity)entityPlayer), entityE.getHealth(), entityE.getHealth());
                 //entityE.attackEntityFrom((new TKDamageSource((Entity)entityPlayer)).setDamageBypassesArmor().setDamageAllowedInCreativeMode(), entityE.getHealth());
                 entityE.setHealth(0.0F);
                 entityPlayer.onKillEntity((EntityLivingBase)entityE);
                 entityE.worldObj.setEntityState((Entity)entityE, (byte)2);
                 entityE.handleHealthUpdate((byte)3);
                 entityE.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0.0D);
                 entityE.addStat(StatList.deathsStat, 1);
                 entityE.setLastAttacker((Entity)entityPlayer);
                 entityE.closeScreen();

                 entityE.motionY = 0.10000000149011612D;
                 entityE.width = 0.2F;
                 entityE.height = 0.2F;
                 entityE.motionX = (-MathHelper.cos((entityE.attackedAtYaw + entityE.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
                 entityE.motionZ = (-MathHelper.sin((entityE.attackedAtYaw + entityE.rotationYaw) * 3.1415927F / 180.0F) * 0.1F);
                  }

            if (entityE.deathTime >= 10) {
                entityE.inventory.dropAllItems();
                entityE.clearActivePotions();
                entityE.setHealth(0.0F);
                 }
        }
    }
    public static void AttackSimpleEntity(World worldObj, EntityLivingBase entityLivingBase, EntityLivingBase entityPlayer) {
     if (worldObj.isRemote) {
           return;
     }
      if (entityLivingBase.isDead) {
         return;
      }
        if (entityLivingBase instanceof EntityPlayer) {
            return;
        }
        if (entityLivingBase.getHealth() <= 0.0F) {
               entityLivingBase.setDead();
        }
         entityLivingBase.onDeath(DamageSource.outOfWorld);
         entityLivingBase.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
         entityLivingBase.attackEntityFrom(DamageSource.outOfWorld, 1.0E10F);
         entityLivingBase.setAIMoveSpeed(0.0F);
         entityLivingBase.hurtResistantTime = 0;
         entityLivingBase.velocityChanged = true;
         entityLivingBase.addVelocity((-MathHelper.sin(entityLivingBase.rotationYaw * 3.1415927F / 180.0F) * 1.0F * 0.5F), 0.1D, (MathHelper.cos(entityLivingBase.rotationYaw * 3.1415927F / 180.0F) * 1.0F * 0.5F));
         entityLivingBase.motionX *= 0.6D;
         entityLivingBase.motionZ *= 0.6D;
         entityLivingBase.setHealth(-1111.0F);
         entityLivingBase.attackEntityFrom(DamageSource.outOfWorld, 300000.0F);
         entityLivingBase.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(-110.0D);
         entityLivingBase.setLastAttacker((Entity)entityPlayer);
    }
    public static void ChatPrint(String message ) {
        List list = getAllServerPlayers();
        Iterator var2 = list.iterator();
        while(var2.hasNext() && !HasPrinted) {
            EntityPlayer player = (EntityPlayer)var2.next();
            player.addChatComponentMessage(Text(message));
            HasPrinted = true;
        }

    }
    public static List getAllServerPlayers() {
        return MinecraftServer.getServer().getConfigurationManager().playerEntityList;
    }
    public static ChatComponentTranslation Text(String text) {
        return new ChatComponentTranslation(text, new Object[0]);
    }

}
