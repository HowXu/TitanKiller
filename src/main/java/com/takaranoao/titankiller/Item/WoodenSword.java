package com.takaranoao.titankiller.Item;

import com.takaranoao.titankiller.Loader.ItemLoader;
import com.takaranoao.titankiller.tools.EntityTool;
import com.takaranoao.titankiller.tools.HealthKiller;
import com.takaranoao.titankiller.tools.RainbowText;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

import java.util.List;
import java.util.Random;

public class WoodenSword extends ItemSword {

    public WoodenSword(ToolMaterial p_i45356_1_) {
        super(p_i45356_1_);
        setUnlocalizedName("titankiller");
        setTextureName("minecraft:wood_sword");
        setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
        list.add("被宝蓝咬了一口的木剑，上面还有一个浅浅的牙印");
        list.add(RainbowText.makeRBW("听说这把剑打人超疼的，就跟被宝蓝咬了一样"));
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        return "木剑".trim();
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {

       /*
       if (player.getMaxHealth() > 0.0F) {
              player.setHealth(player.getMaxHealth());
        } else {
            player.setHealth(20.0F);
        }
              player.isDead = false;
      if (!entity.worldObj.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer victim = (EntityPlayer)entity;
                if (!victim.isDead) {
                   EntityTool.AttackEntityPlayer(player.worldObj, entity, player);
                   return true;
            }
          }

       if (!entity.worldObj.isRemote && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase) {
             EntityTool.AttackSimpleEntity(player.worldObj, (EntityLivingBase)entity, (EntityLivingBase)player);
            return true;
           }
           */

        //秒TNT
        if (!entity.worldObj.isRemote && !(entity instanceof EntityPlayer) && !(entity instanceof EntityLivingBase) && !(entity instanceof net.minecraft.entity.item.EntityItem) && entity != null) {
             entity.setDead();
             return true;
             }
            return false;
    }

    @Override
    public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity pq, int p_77663_4_, boolean p_77663_5_) {
        super.onUpdate(p_77663_1_, p_77663_2_, pq, p_77663_4_, p_77663_5_);
            EntityPlayer player = (EntityPlayer) pq;
            player.maxHurtTime = 0;
            if(EntityTool.HasSubscribed(player)){
                player.capabilities.allowFlying = true;
            }
           // player.worldObj.getGameRules().setOrCreateGameRule("keepInventory", "true");//永远不掉落
        if (player != null && player instanceof EntityPlayer) {
            if (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemLoader.titankiller && player.isUsingItem() && player.isSneaking()) {
                removeBadEffetct(player,Potion.weakness);
                removeBadEffetct(player,Potion.wither);
                removeBadEffetct(player,Potion.confusion);
                removeBadEffetct(player, Potion.digSlowdown);
                removeBadEffetct(player, Potion.blindness);
                removeBadEffetct(player, Potion.hunger);
                removeBadEffetct(player, Potion.poison);
                removeBadEffetct(player, Potion.moveSlowdown);
               }
        }
        if (player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() < 20.0D) {

            player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
            player.setHealth(player.getMaxHealth());
        }
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase target) {
        if (!target.worldObj.isRemote && (target instanceof net.minecraft.entity.EntityLiving || target instanceof EntityPlayer)) {
            //EntityTool.setHealth(target, target.getHealth() / 10.0F - 10.0F);
            //target.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((target.getMaxHealth() / 10.0F - 10.0F));
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        //EntityTool.LightingWorld(p_77659_2_,p_77659_3_);
        EntityTool.PickItems(p_77659_3_);
        //RemoveHealth(p_77659_1_,0,p_77659_3_);
        HealthKiller.RemoveTitanHealth(p_77659_1_,p_77659_3_);
        HealthKiller.RemoveEntityHealth(p_77659_1_,p_77659_3_);
        return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
    }


    private void RemoveHealth(ItemStack itemstack, int p_77615_4_, EntityPlayer player){
        int j = getMaxItemUseDuration(itemstack) - p_77615_4_;
        ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, j);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return;
        }
        j = event.charge;
        float f = j / 60.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f < 0.1D) {
            return;
        }
        if (f > 1.0F)
        {
            f = 1.0F;
        }
       // player.swingItem();
        Vec3 vec3 = player.getLook(1.0F);
        double dx = vec3.xCoord * 6.0D;
        double dy = player.getEyeHeight() + vec3.yCoord * 6.0D;
        double dz = vec3.zCoord * 6.0D;
        List list1 = player.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.expand(6.0D, 6.0D, 6.0D).offset(dx, dy, dz));
        double dx1 = vec3.xCoord * 64.0D;
        double dy1 = player.getEyeHeight() + vec3.yCoord * 64.0D;
        double dz1 = vec3.zCoord * 64.0D;
        List<Entity> list2 = player.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)player, player.boundingBox.expand(64.0D, 64.0D, 64.0D).offset(dx1, dy1, dz1));
        List<Entity> list3 = player.worldObj.loadedEntityList;
        if (list3 != null && !list3.isEmpty())
        {
            for (int i1111 = 0; i1111 < list3.size(); i1111++) {
                Entity entity2 = list3.get(i1111);
                if (Loader.isModLoaded("thetitans")) {
                    if (!entity2.isDead && entity2 instanceof EntityTitanSpirit)
                    {
                        ((EntityTitanSpirit)entity2).setDead();
                    }
                    if (!entity2.isDead && entity2 instanceof EntityWitherzilla)
                    {
                        ((EntityWitherzilla)entity2).setTitanHealth(0.0F);
                    }
                }
            }
        }
        if (list2 != null && !list2.isEmpty())
        {
            for (int i111 = 0; i111 < list2.size(); i111++) {
                Entity entity1 = list2.get(i111);
                if (player.isSneaking()) {
                    if (entity1 instanceof EntityLivingBase) {
                        EntityLivingBase target = (EntityLivingBase) entity1;
                        target.hurtResistantTime = 0;
                        EntityTool.setHealth(target, 0.0F);
                        target.motionX = (-MathHelper.sin(target.rotationYaw * 3.1415927F / 180.0F) * 6.0F) + 5.0D;
                        target.motionY = 11.0D;
                        target.motionZ = (MathHelper.cos(target.rotationYaw * 3.1415927F / 180.0F) * 6.0F) + 5.0D;
                        target.setFire(2147483647);
                        ((EntityLivingBase)entity1).deathTime++;
                        if (!(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && ((EntityLivingBase)entity1).deathTime > 1) {
                            ((EntityLivingBase)entity1).isDead = true;
                            if (!entity1.isEntityAlive()) {
                                ((EntityLivingBase)entity1).onDeath(DamageSource.magic);
                                entity1.worldObj.setEntityState(entity1, (byte)3);
                            }
                        }
                    } else if (!(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && !(entity1 instanceof net.minecraft.entity.item.EntityItem)) {
                        if (!entity1.isDead)
                        {
                            entity1.isDead = true;
                        }
                        if (!entity1.isDead && entity1 instanceof EntityLivingBase && !(entity1 instanceof net.minecraft.entity.item.EntityItem)) {
                            ((EntityLivingBase)entity1).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0.0D);
                            entity1.handleHealthUpdate((byte)3);
                        }
                    }
                }
                if (entity1 instanceof EntityPlayer) {
                    EntityPlayer target = (EntityPlayer) entity1;
                    //target.attackEntityFrom((new DamageSourceInfinitySword(player)).setFireDamage().setDamageBypassesArmor(), 4.0F);//这个要求是EntityPlayer
                    //target.attackEntityFrom(DamageSource.causeThornsDamage(player), 11100.0F);//实体死亡信息，要求是EntityLivingBase

                    EntityTool.setHealth(target, 0.0F);//血量清空的部分

                    String name = target.getCommandSenderName();
                    String name1 = player.getCommandSenderName();
                    Random random = new Random();
                    int KillerInt = random.nextInt(2);
                    String Killer;
                    if (KillerInt == 0){
                        Killer = "喂了猪";
                    }else {
                        Killer = "玩坏掉了";
                    }
                   // EntityTool.ChatPrint(name + "被" + name1 + Killer);//这样写比较好，不用DamageSource
                }
            }
        }
    }

    private void removeBadEffetct(EntityPlayer player, Potion potion){
        if (player.getActivePotionEffect(potion) != null){
            player.removePotionEffect(potion.id);
        }
    }











































}
