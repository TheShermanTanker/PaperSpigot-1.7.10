/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class EntityPainting
/*    */   extends EntityHanging {
/*    */   public EnumArt art;
/*    */   
/*    */   public EntityPainting(World world) {
/* 10 */     super(world);
/* 11 */     this.art = EnumArt.values()[this.random.nextInt((EnumArt.values()).length)];
/*    */   }
/*    */   
/*    */   public EntityPainting(World world, int i, int j, int k, int l) {
/* 15 */     super(world, i, j, k, l);
/* 16 */     ArrayList<EnumArt> arraylist = new ArrayList();
/* 17 */     EnumArt[] aenumart = EnumArt.values();
/* 18 */     int i1 = aenumart.length;
/*    */     
/* 20 */     for (int j1 = 0; j1 < i1; j1++) {
/* 21 */       EnumArt enumart = aenumart[j1];
/*    */       
/* 23 */       this.art = enumart;
/* 24 */       setDirection(l);
/* 25 */       if (survives()) {
/* 26 */         arraylist.add(enumart);
/*    */       }
/*    */     } 
/*    */     
/* 30 */     if (!arraylist.isEmpty()) {
/* 31 */       this.art = arraylist.get(this.random.nextInt(arraylist.size()));
/*    */     }
/*    */     
/* 34 */     setDirection(l);
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 38 */     nbttagcompound.setString("Motive", this.art.B);
/* 39 */     super.b(nbttagcompound);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 43 */     String s = nbttagcompound.getString("Motive");
/* 44 */     EnumArt[] aenumart = EnumArt.values();
/* 45 */     int i = aenumart.length;
/*    */     
/* 47 */     for (int j = 0; j < i; j++) {
/* 48 */       EnumArt enumart = aenumart[j];
/*    */       
/* 50 */       if (enumart.B.equals(s)) {
/* 51 */         this.art = enumart;
/*    */       }
/*    */     } 
/*    */     
/* 55 */     if (this.art == null) {
/* 56 */       this.art = EnumArt.KEBAB;
/*    */     }
/*    */     
/* 59 */     super.a(nbttagcompound);
/*    */   }
/*    */   
/*    */   public int f() {
/* 63 */     return this.art.C;
/*    */   }
/*    */   
/*    */   public int i() {
/* 67 */     return this.art.D;
/*    */   }
/*    */   
/*    */   public void b(Entity entity) {
/* 71 */     if (entity instanceof EntityHuman) {
/* 72 */       EntityHuman entityhuman = (EntityHuman)entity;
/*    */       
/* 74 */       if (entityhuman.abilities.canInstantlyBuild) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 79 */     a(new ItemStack(Items.PAINTING), 0.0F);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */