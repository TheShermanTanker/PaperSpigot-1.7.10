/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinecartTrackLogic
/*     */ {
/*     */   private World b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private final boolean f;
/*  23 */   private List g = new ArrayList();
/*     */   
/*     */   public MinecartTrackLogic(BlockMinecartTrackAbstract paramBlockMinecartTrackAbstract, World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  26 */     this.b = paramWorld;
/*  27 */     this.c = paramInt1;
/*  28 */     this.d = paramInt2;
/*  29 */     this.e = paramInt3;
/*     */     
/*  31 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/*  32 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  33 */     if (((BlockMinecartTrackAbstract)block).a) {
/*  34 */       this.f = true;
/*  35 */       i &= 0xFFFFFFF7;
/*     */     } else {
/*  37 */       this.f = false;
/*     */     } 
/*  39 */     a(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(int paramInt) {
/*  44 */     this.g.clear();
/*  45 */     if (paramInt == 0) {
/*  46 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*  47 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  48 */     } else if (paramInt == 1) {
/*  49 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  50 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  51 */     } else if (paramInt == 2) {
/*  52 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  53 */       this.g.add(new ChunkPosition(this.c + 1, this.d + 1, this.e));
/*  54 */     } else if (paramInt == 3) {
/*  55 */       this.g.add(new ChunkPosition(this.c - 1, this.d + 1, this.e));
/*  56 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  57 */     } else if (paramInt == 4) {
/*  58 */       this.g.add(new ChunkPosition(this.c, this.d + 1, this.e - 1));
/*  59 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  60 */     } else if (paramInt == 5) {
/*  61 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*  62 */       this.g.add(new ChunkPosition(this.c, this.d + 1, this.e + 1));
/*  63 */     } else if (paramInt == 6) {
/*  64 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  65 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  66 */     } else if (paramInt == 7) {
/*  67 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  68 */       this.g.add(new ChunkPosition(this.c, this.d, this.e + 1));
/*  69 */     } else if (paramInt == 8) {
/*  70 */       this.g.add(new ChunkPosition(this.c - 1, this.d, this.e));
/*  71 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*  72 */     } else if (paramInt == 9) {
/*  73 */       this.g.add(new ChunkPosition(this.c + 1, this.d, this.e));
/*  74 */       this.g.add(new ChunkPosition(this.c, this.d, this.e - 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b() {
/*  79 */     for (byte b = 0; b < this.g.size(); b++) {
/*  80 */       MinecartTrackLogic minecartTrackLogic = a(this.g.get(b));
/*  81 */       if (minecartTrackLogic == null || !minecartTrackLogic.a(this)) {
/*  82 */         this.g.remove(b--);
/*     */       } else {
/*  84 */         this.g.set(b, new ChunkPosition(minecartTrackLogic.c, minecartTrackLogic.d, minecartTrackLogic.e));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean a(int paramInt1, int paramInt2, int paramInt3) {
/*  90 */     if (BlockMinecartTrackAbstract.b_(this.b, paramInt1, paramInt2, paramInt3)) return true; 
/*  91 */     if (BlockMinecartTrackAbstract.b_(this.b, paramInt1, paramInt2 + 1, paramInt3)) return true; 
/*  92 */     if (BlockMinecartTrackAbstract.b_(this.b, paramInt1, paramInt2 - 1, paramInt3)) return true; 
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   private MinecartTrackLogic a(ChunkPosition paramChunkPosition) {
/*  97 */     if (BlockMinecartTrackAbstract.b_(this.b, paramChunkPosition.x, paramChunkPosition.y, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y, paramChunkPosition.z); 
/*  98 */     if (BlockMinecartTrackAbstract.b_(this.b, paramChunkPosition.x, paramChunkPosition.y + 1, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y + 1, paramChunkPosition.z); 
/*  99 */     if (BlockMinecartTrackAbstract.b_(this.b, paramChunkPosition.x, paramChunkPosition.y - 1, paramChunkPosition.z)) return new MinecartTrackLogic(this.a, this.b, paramChunkPosition.x, paramChunkPosition.y - 1, paramChunkPosition.z); 
/* 100 */     return null;
/*     */   }
/*     */   
/*     */   private boolean a(MinecartTrackLogic paramMinecartTrackLogic) {
/* 104 */     for (byte b = 0; b < this.g.size(); b++) {
/* 105 */       ChunkPosition chunkPosition = this.g.get(b);
/* 106 */       if (chunkPosition.x == paramMinecartTrackLogic.c && chunkPosition.z == paramMinecartTrackLogic.e) {
/* 107 */         return true;
/*     */       }
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   private boolean b(int paramInt1, int paramInt2, int paramInt3) {
/* 114 */     for (byte b = 0; b < this.g.size(); b++) {
/* 115 */       ChunkPosition chunkPosition = this.g.get(b);
/* 116 */       if (chunkPosition.x == paramInt1 && chunkPosition.z == paramInt3) {
/* 117 */         return true;
/*     */       }
/*     */     } 
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   protected int a() {
/* 124 */     byte b = 0;
/*     */     
/* 126 */     if (a(this.c, this.d, this.e - 1)) b++; 
/* 127 */     if (a(this.c, this.d, this.e + 1)) b++; 
/* 128 */     if (a(this.c - 1, this.d, this.e)) b++; 
/* 129 */     if (a(this.c + 1, this.d, this.e)) b++;
/*     */     
/* 131 */     return b;
/*     */   }
/*     */   
/*     */   private boolean b(MinecartTrackLogic paramMinecartTrackLogic) {
/* 135 */     if (a(paramMinecartTrackLogic)) return true; 
/* 136 */     if (this.g.size() == 2) {
/* 137 */       return false;
/*     */     }
/* 139 */     if (this.g.isEmpty()) {
/* 140 */       return true;
/*     */     }
/*     */     
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   private void c(MinecartTrackLogic paramMinecartTrackLogic) {
/* 147 */     this.g.add(new ChunkPosition(paramMinecartTrackLogic.c, paramMinecartTrackLogic.d, paramMinecartTrackLogic.e));
/*     */     
/* 149 */     boolean bool1 = b(this.c, this.d, this.e - 1);
/* 150 */     boolean bool2 = b(this.c, this.d, this.e + 1);
/* 151 */     boolean bool3 = b(this.c - 1, this.d, this.e);
/* 152 */     boolean bool4 = b(this.c + 1, this.d, this.e);
/*     */     
/* 154 */     byte b = -1;
/*     */     
/* 156 */     if (bool1 || bool2) b = 0; 
/* 157 */     if (bool3 || bool4) b = 1; 
/* 158 */     if (!this.f) {
/* 159 */       if (bool2 && bool4 && !bool1 && !bool3) b = 6; 
/* 160 */       if (bool2 && bool3 && !bool1 && !bool4) b = 7; 
/* 161 */       if (bool1 && bool3 && !bool2 && !bool4) b = 8; 
/* 162 */       if (bool1 && bool4 && !bool2 && !bool3) b = 9; 
/*     */     } 
/* 164 */     if (b == 0) {
/* 165 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e - 1)) b = 4; 
/* 166 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e + 1)) b = 5; 
/*     */     } 
/* 168 */     if (b == 1) {
/* 169 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c + 1, this.d + 1, this.e)) b = 2; 
/* 170 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c - 1, this.d + 1, this.e)) b = 3;
/*     */     
/*     */     } 
/* 173 */     if (b < 0) b = 0;
/*     */     
/* 175 */     int i = b;
/* 176 */     if (this.f) {
/* 177 */       i = this.b.getData(this.c, this.d, this.e) & 0x8 | b;
/*     */     }
/*     */     
/* 180 */     this.b.setData(this.c, this.d, this.e, i, 3);
/*     */   }
/*     */   
/*     */   private boolean c(int paramInt1, int paramInt2, int paramInt3) {
/* 184 */     MinecartTrackLogic minecartTrackLogic = a(new ChunkPosition(paramInt1, paramInt2, paramInt3));
/* 185 */     if (minecartTrackLogic == null) return false; 
/* 186 */     minecartTrackLogic.b();
/* 187 */     return minecartTrackLogic.b(this);
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean1, boolean paramBoolean2) {
/* 191 */     boolean bool1 = c(this.c, this.d, this.e - 1);
/* 192 */     boolean bool2 = c(this.c, this.d, this.e + 1);
/* 193 */     boolean bool3 = c(this.c - 1, this.d, this.e);
/* 194 */     boolean bool4 = c(this.c + 1, this.d, this.e);
/*     */     
/* 196 */     byte b = -1;
/*     */     
/* 198 */     if ((bool1 || bool2) && !bool3 && !bool4) b = 0; 
/* 199 */     if ((bool3 || bool4) && !bool1 && !bool2) b = 1;
/*     */     
/* 201 */     if (!this.f) {
/* 202 */       if (bool2 && bool4 && !bool1 && !bool3) b = 6; 
/* 203 */       if (bool2 && bool3 && !bool1 && !bool4) b = 7; 
/* 204 */       if (bool1 && bool3 && !bool2 && !bool4) b = 8; 
/* 205 */       if (bool1 && bool4 && !bool2 && !bool3) b = 9; 
/*     */     } 
/* 207 */     if (b == -1) {
/* 208 */       if (bool1 || bool2) b = 0; 
/* 209 */       if (bool3 || bool4) b = 1;
/*     */       
/* 211 */       if (!this.f) {
/* 212 */         if (paramBoolean1) {
/* 213 */           if (bool2 && bool4) b = 6; 
/* 214 */           if (bool3 && bool2) b = 7; 
/* 215 */           if (bool4 && bool1) b = 9; 
/* 216 */           if (bool1 && bool3) b = 8; 
/*     */         } else {
/* 218 */           if (bool1 && bool3) b = 8; 
/* 219 */           if (bool4 && bool1) b = 9; 
/* 220 */           if (bool3 && bool2) b = 7; 
/* 221 */           if (bool2 && bool4) b = 6;
/*     */         
/*     */         } 
/*     */       }
/*     */     } 
/* 226 */     if (b == 0) {
/* 227 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e - 1)) b = 4; 
/* 228 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c, this.d + 1, this.e + 1)) b = 5; 
/*     */     } 
/* 230 */     if (b == 1) {
/* 231 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c + 1, this.d + 1, this.e)) b = 2; 
/* 232 */       if (BlockMinecartTrackAbstract.b_(this.b, this.c - 1, this.d + 1, this.e)) b = 3;
/*     */     
/*     */     } 
/* 235 */     if (b < 0) b = 0;
/*     */     
/* 237 */     a(b);
/*     */     
/* 239 */     int i = b;
/* 240 */     if (this.f) {
/* 241 */       i = this.b.getData(this.c, this.d, this.e) & 0x8 | b;
/*     */     }
/*     */     
/* 244 */     if (paramBoolean2 || this.b.getData(this.c, this.d, this.e) != i) {
/* 245 */       this.b.setData(this.c, this.d, this.e, i, 3);
/* 246 */       for (byte b1 = 0; b1 < this.g.size(); b1++) {
/* 247 */         MinecartTrackLogic minecartTrackLogic = a(this.g.get(b1));
/* 248 */         if (minecartTrackLogic != null) {
/* 249 */           minecartTrackLogic.b();
/*     */           
/* 251 */           if (minecartTrackLogic.b(this))
/* 252 */             minecartTrackLogic.c(this); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MinecartTrackLogic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */