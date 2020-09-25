/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ public abstract class StructureStart
/*     */ {
/*  10 */   protected LinkedList a = new LinkedList();
/*     */   
/*     */   protected StructureBoundingBox b;
/*     */   
/*     */   private int c;
/*     */   private int d;
/*     */   
/*     */   public StructureStart() {}
/*     */   
/*     */   public StructureStart(int paramInt1, int paramInt2) {
/*  20 */     this.c = paramInt1;
/*  21 */     this.d = paramInt2;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox a() {
/*  25 */     return this.b;
/*     */   }
/*     */   
/*     */   public LinkedList b() {
/*  29 */     return this.a;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/*  33 */     Iterator<StructurePiece> iterator = this.a.iterator();
/*  34 */     while (iterator.hasNext()) {
/*  35 */       StructurePiece structurePiece = iterator.next();
/*  36 */       if (structurePiece.c().a(paramStructureBoundingBox) && 
/*  37 */         !structurePiece.a(paramWorld, paramRandom, paramStructureBoundingBox)) {
/*  38 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  45 */     this.b = StructureBoundingBox.a();
/*     */     
/*  47 */     for (StructurePiece structurePiece : this.a) {
/*  48 */       this.b.b(structurePiece.c());
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(int paramInt1, int paramInt2) {
/*  53 */     NBTTagCompound nBTTagCompound = new NBTTagCompound();
/*     */     
/*  55 */     nBTTagCompound.setString("id", WorldGenFactory.a(this));
/*  56 */     nBTTagCompound.setInt("ChunkX", paramInt1);
/*  57 */     nBTTagCompound.setInt("ChunkZ", paramInt2);
/*  58 */     nBTTagCompound.set("BB", this.b.h());
/*     */     
/*  60 */     NBTTagList nBTTagList = new NBTTagList();
/*  61 */     for (StructurePiece structurePiece : this.a) {
/*  62 */       nBTTagList.add(structurePiece.b());
/*     */     }
/*  64 */     nBTTagCompound.set("Children", nBTTagList);
/*     */     
/*  66 */     a(nBTTagCompound);
/*     */     
/*  68 */     return nBTTagCompound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {}
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, NBTTagCompound paramNBTTagCompound) {
/*  77 */     this.c = paramNBTTagCompound.getInt("ChunkX");
/*  78 */     this.d = paramNBTTagCompound.getInt("ChunkZ");
/*  79 */     if (paramNBTTagCompound.hasKey("BB")) {
/*  80 */       this.b = new StructureBoundingBox(paramNBTTagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  83 */     NBTTagList nBTTagList = paramNBTTagCompound.getList("Children", 10);
/*  84 */     for (byte b = 0; b < nBTTagList.size(); b++) {
/*  85 */       this.a.add(WorldGenFactory.b(nBTTagList.get(b), paramWorld));
/*     */     }
/*     */     
/*  88 */     b(paramNBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {}
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, Random paramRandom, int paramInt) {
/*  96 */     int i = 63 - paramInt;
/*     */ 
/*     */     
/*  99 */     int j = this.b.c() + 1;
/*     */     
/* 101 */     if (j < i) {
/* 102 */       j += paramRandom.nextInt(i - j);
/*     */     }
/*     */ 
/*     */     
/* 106 */     int k = j - this.b.e;
/* 107 */     this.b.a(0, k, 0);
/* 108 */     for (StructurePiece structurePiece : this.a) {
/* 109 */       structurePiece.c().a(0, k, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 115 */     int i = paramInt2 - paramInt1 + 1 - this.b.c();
/* 116 */     int j = 1;
/*     */     
/* 118 */     if (i > 1) {
/* 119 */       j = paramInt1 + paramRandom.nextInt(i);
/*     */     } else {
/* 121 */       j = paramInt1;
/*     */     } 
/*     */ 
/*     */     
/* 125 */     int k = j - this.b.b;
/* 126 */     this.b.a(0, k, 0);
/* 127 */     for (StructurePiece structurePiece : this.a) {
/* 128 */       structurePiece.c().a(0, k, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public int e() {
/* 137 */     return this.c;
/*     */   }
/*     */   
/*     */   public int f() {
/* 141 */     return this.d;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StructureStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */