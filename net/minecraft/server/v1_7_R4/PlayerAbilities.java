/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PlayerAbilities
/*    */ {
/*    */   public boolean isInvulnerable;
/*    */   public boolean isFlying;
/*    */   public boolean canFly;
/*    */   public boolean canInstantlyBuild;
/*    */   public boolean mayBuild = true;
/* 10 */   public float flySpeed = 0.05F;
/* 11 */   public float walkSpeed = 0.1F;
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 16 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*    */     
/* 18 */     nbttagcompound1.setBoolean("invulnerable", this.isInvulnerable);
/* 19 */     nbttagcompound1.setBoolean("flying", this.isFlying);
/* 20 */     nbttagcompound1.setBoolean("mayfly", this.canFly);
/* 21 */     nbttagcompound1.setBoolean("instabuild", this.canInstantlyBuild);
/* 22 */     nbttagcompound1.setBoolean("mayBuild", this.mayBuild);
/* 23 */     nbttagcompound1.setFloat("flySpeed", this.flySpeed);
/* 24 */     nbttagcompound1.setFloat("walkSpeed", this.walkSpeed);
/* 25 */     nbttagcompound.set("abilities", nbttagcompound1);
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 29 */     if (nbttagcompound.hasKeyOfType("abilities", 10)) {
/* 30 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("abilities");
/*    */       
/* 32 */       this.isInvulnerable = nbttagcompound1.getBoolean("invulnerable");
/* 33 */       this.isFlying = nbttagcompound1.getBoolean("flying");
/* 34 */       this.canFly = nbttagcompound1.getBoolean("mayfly");
/* 35 */       this.canInstantlyBuild = nbttagcompound1.getBoolean("instabuild");
/* 36 */       if (nbttagcompound1.hasKeyOfType("flySpeed", 99)) {
/* 37 */         this.flySpeed = nbttagcompound1.getFloat("flySpeed");
/* 38 */         this.walkSpeed = nbttagcompound1.getFloat("walkSpeed");
/*    */       } 
/*    */       
/* 41 */       if (nbttagcompound1.hasKeyOfType("mayBuild", 1)) {
/* 42 */         this.mayBuild = nbttagcompound1.getBoolean("mayBuild");
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public float a() {
/* 48 */     return this.flySpeed;
/*    */   }
/*    */   
/*    */   public float b() {
/* 52 */     return this.walkSpeed;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerAbilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */