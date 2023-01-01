package com.takaranoao.titankiller.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


 public class RainbowLightningBolt
   extends Entity {
   public long boltVertex;
   private int boltLivingTime;
   private byte lightningState;

   public RainbowLightningBolt(World world, double x, double y, double z) {
     super(world);
     setLocationAndAngles(x, y, z, 0.0F, 0.0F);
     this.lightningState = 2;
     this.boltVertex = this.rand.nextLong();
     this.boltLivingTime = this.rand.nextInt(3) + 1;
   }

   public void onUpdate() {
     super.onUpdate();
     if (this.lightningState == 2) {
       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
       this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
     }
     this.lightningState = (byte)(this.lightningState - 1);
     if (this.lightningState < 0) {
       if (this.boltLivingTime == 0) {
         setDead();
       }
       else if (this.lightningState < -this.rand.nextInt(10)) {
         this.boltLivingTime--;
         this.lightningState = 1;
         this.boltVertex = this.rand.nextLong();
       }
     }
     if (this.lightningState >= 0 &&
       this.worldObj.isRemote)
       this.worldObj.lastLightningBolt = 2;
   }

   protected void entityInit() {}

   protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {}

   protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {}
 }
