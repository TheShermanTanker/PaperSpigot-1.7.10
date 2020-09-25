/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class TileEntityEnchantTable
/*    */   extends TileEntity
/*    */ {
/*    */   public int a;
/*    */   public float i;
/*    */   public float j;
/*    */   public float k;
/*    */   public float l;
/* 14 */   private static Random r = new Random(); public float m; public float n; public float o; public float p;
/*    */   public float q;
/*    */   private String s;
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 19 */     super.b(paramNBTTagCompound);
/* 20 */     if (b()) paramNBTTagCompound.setString("CustomName", this.s);
/*    */   
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 25 */     super.a(paramNBTTagCompound);
/* 26 */     if (paramNBTTagCompound.hasKeyOfType("CustomName", 8)) this.s = paramNBTTagCompound.getString("CustomName");
/*    */   
/*    */   }
/*    */   
/*    */   public void h() {
/* 31 */     super.h();
/* 32 */     this.n = this.m;
/* 33 */     this.p = this.o;
/*    */     
/* 35 */     EntityHuman entityHuman = this.world.findNearbyPlayer((this.x + 0.5F), (this.y + 0.5F), (this.z + 0.5F), 3.0D);
/* 36 */     if (entityHuman != null) {
/* 37 */       double d1 = entityHuman.locX - (this.x + 0.5F);
/* 38 */       double d2 = entityHuman.locZ - (this.z + 0.5F);
/*    */       
/* 40 */       this.q = (float)Math.atan2(d2, d1);
/*    */       
/* 42 */       this.m += 0.1F;
/*    */       
/* 44 */       if (this.m < 0.5F || r.nextInt(40) == 0) {
/* 45 */         float f = this.k;
/*    */         do {
/* 47 */           this.k += (r.nextInt(4) - r.nextInt(4));
/* 48 */         } while (f == this.k);
/*    */       } 
/*    */     } else {
/*    */       
/* 52 */       this.q += 0.02F;
/* 53 */       this.m -= 0.1F;
/*    */     } 
/*    */     
/* 56 */     while (this.o >= 3.1415927F)
/* 57 */       this.o -= 6.2831855F; 
/* 58 */     while (this.o < -3.1415927F)
/* 59 */       this.o += 6.2831855F; 
/* 60 */     while (this.q >= 3.1415927F)
/* 61 */       this.q -= 6.2831855F; 
/* 62 */     while (this.q < -3.1415927F)
/* 63 */       this.q += 6.2831855F; 
/* 64 */     float f1 = this.q - this.o;
/* 65 */     while (f1 >= 3.1415927F)
/* 66 */       f1 -= 6.2831855F; 
/* 67 */     while (f1 < -3.1415927F) {
/* 68 */       f1 += 6.2831855F;
/*    */     }
/* 70 */     this.o += f1 * 0.4F;
/*    */     
/* 72 */     if (this.m < 0.0F) this.m = 0.0F; 
/* 73 */     if (this.m > 1.0F) this.m = 1.0F;
/*    */     
/* 75 */     this.a++;
/* 76 */     this.j = this.i;
/*    */     
/* 78 */     float f2 = (this.k - this.i) * 0.4F;
/* 79 */     float f3 = 0.2F;
/* 80 */     if (f2 < -f3) f2 = -f3; 
/* 81 */     if (f2 > f3) f2 = f3; 
/* 82 */     this.l += (f2 - this.l) * 0.9F;
/*    */     
/* 84 */     this.i += this.l;
/*    */   }
/*    */   
/*    */   public String a() {
/* 88 */     return b() ? this.s : "container.enchant";
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 92 */     return (this.s != null && this.s.length() > 0);
/*    */   }
/*    */   
/*    */   public void a(String paramString) {
/* 96 */     this.s = paramString;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityEnchantTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */