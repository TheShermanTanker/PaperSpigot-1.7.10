/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TileEntityPiston
/*     */   extends TileEntity {
/*     */   private Block a;
/*     */   private int i;
/*     */   private int j;
/*     */   private boolean k;
/*     */   private boolean l;
/*     */   private float m;
/*     */   private float n;
/*  16 */   private List o = new ArrayList();
/*     */   
/*     */   public TileEntityPiston() {}
/*     */   
/*     */   public TileEntityPiston(Block block, int i, int j, boolean flag, boolean flag1) {
/*  21 */     this.a = block;
/*  22 */     this.i = i;
/*  23 */     this.j = j;
/*  24 */     this.k = flag;
/*  25 */     this.l = flag1;
/*     */   }
/*     */   
/*     */   public Block a() {
/*  29 */     return this.a;
/*     */   }
/*     */   
/*     */   public int p() {
/*  33 */     return this.i;
/*     */   }
/*     */   
/*     */   public boolean b() {
/*  37 */     return this.k;
/*     */   }
/*     */   
/*     */   public int c() {
/*  41 */     return this.j;
/*     */   }
/*     */   
/*     */   public float a(float f) {
/*  45 */     if (f > 1.0F) {
/*  46 */       f = 1.0F;
/*     */     }
/*     */     
/*  49 */     return this.n + (this.m - this.n) * f;
/*     */   }
/*     */   
/*     */   private void a(float f, float f1) {
/*  53 */     if (this.k) {
/*  54 */       f = 1.0F - f;
/*     */     } else {
/*  56 */       f--;
/*     */     } 
/*     */     
/*  59 */     AxisAlignedBB axisalignedbb = Blocks.PISTON_MOVING.a(this.world, this.x, this.y, this.z, this.a, f, this.j);
/*     */     
/*  61 */     if (axisalignedbb != null) {
/*  62 */       List list = this.world.getEntities((Entity)null, axisalignedbb);
/*     */       
/*  64 */       if (!list.isEmpty()) {
/*  65 */         this.o.addAll(list);
/*  66 */         Iterator<Entity> iterator = this.o.iterator();
/*     */         
/*  68 */         while (iterator.hasNext()) {
/*  69 */           Entity entity = iterator.next();
/*     */           
/*  71 */           entity.move((f1 * Facing.b[this.j]), (f1 * Facing.c[this.j]), (f1 * Facing.d[this.j]));
/*     */         } 
/*     */         
/*  74 */         this.o.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void f() {
/*  80 */     if (this.n < 1.0F && this.world != null) {
/*  81 */       this.n = this.m = 1.0F;
/*  82 */       this.world.p(this.x, this.y, this.z);
/*  83 */       s();
/*  84 */       if (this.world.getType(this.x, this.y, this.z) == Blocks.PISTON_MOVING) {
/*  85 */         this.world.setTypeAndData(this.x, this.y, this.z, this.a, this.i, 3);
/*  86 */         this.world.e(this.x, this.y, this.z, this.a);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void h() {
/*  92 */     if (this.world == null)
/*     */       return; 
/*  94 */     this.n = this.m;
/*  95 */     if (this.n >= 1.0F) {
/*  96 */       a(1.0F, 0.25F);
/*  97 */       this.world.p(this.x, this.y, this.z);
/*  98 */       s();
/*  99 */       if (this.world.getType(this.x, this.y, this.z) == Blocks.PISTON_MOVING) {
/* 100 */         this.world.setTypeAndData(this.x, this.y, this.z, this.a, this.i, 3);
/* 101 */         this.world.e(this.x, this.y, this.z, this.a);
/*     */       } 
/*     */     } else {
/* 104 */       this.m += 0.5F;
/* 105 */       if (this.m >= 1.0F) {
/* 106 */         this.m = 1.0F;
/*     */       }
/*     */       
/* 109 */       if (this.k) {
/* 110 */         a(this.m, this.m - this.n + 0.0625F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 116 */     super.a(nbttagcompound);
/* 117 */     this.a = Block.getById(nbttagcompound.getInt("blockId"));
/* 118 */     this.i = nbttagcompound.getInt("blockData");
/* 119 */     this.j = nbttagcompound.getInt("facing");
/* 120 */     this.n = this.m = nbttagcompound.getFloat("progress");
/* 121 */     this.k = nbttagcompound.getBoolean("extending");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 125 */     super.b(nbttagcompound);
/* 126 */     nbttagcompound.setInt("blockId", Block.getId(this.a));
/* 127 */     nbttagcompound.setInt("blockData", this.i);
/* 128 */     nbttagcompound.setInt("facing", this.j);
/* 129 */     nbttagcompound.setFloat("progress", this.n);
/* 130 */     nbttagcompound.setBoolean("extending", this.k);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityPiston.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */