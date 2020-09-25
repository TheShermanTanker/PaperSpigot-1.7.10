/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkSection
/*     */ {
/*     */   private int yPos;
/*     */   private int nonEmptyBlockCount;
/*     */   private int tickingBlockCount;
/*     */   private byte[] blockIds;
/*     */   private NibbleArray extBlockIds;
/*     */   private NibbleArray blockData;
/*     */   private NibbleArray emittedLight;
/*     */   private NibbleArray skyLight;
/*     */   private int compactId;
/*     */   private byte compactExtId;
/*     */   private byte compactData;
/*     */   private byte compactEmitted;
/*     */   private byte compactSky;
/*     */   boolean isDirty;
/*  24 */   private static NibbleArray[] compactPregen = new NibbleArray[16];
/*     */   static {
/*  26 */     for (int i = 0; i < 16; i++) {
/*  27 */       compactPregen[i] = expandCompactNibble((byte)i);
/*     */     }
/*     */   }
/*     */   
/*     */   private static NibbleArray expandCompactNibble(byte value) {
/*  32 */     byte[] data = new byte[2048];
/*  33 */     Arrays.fill(data, (byte)(value | value << 4));
/*  34 */     return new NibbleArray(data, 4);
/*     */   }
/*     */   
/*     */   private boolean canBeCompact(byte[] array) {
/*  38 */     byte value = array[0];
/*  39 */     for (int i = 1; i < array.length; i++) {
/*  40 */       if (value != array[i]) {
/*  41 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSection(int i, boolean flag) {
/*  50 */     this.yPos = i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (!flag) {
/*  60 */       this.compactSky = -1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkSection(int y, boolean flag, byte[] blkIds, byte[] extBlkIds) {
/*  67 */     this.yPos = y;
/*  68 */     setIdArray(blkIds);
/*  69 */     if (extBlkIds != null) {
/*  70 */       setExtendedIdArray(new NibbleArray(extBlkIds, 4));
/*     */     }
/*  72 */     if (!flag) {
/*  73 */       this.compactSky = -1;
/*     */     }
/*  75 */     recalcBlockCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getTypeId(int i, int j, int k) {
/*  81 */     if (this.blockIds == null) {
/*  82 */       int id = this.compactId;
/*  83 */       if (this.extBlockIds == null) {
/*  84 */         id |= this.compactExtId << 8;
/*     */       } else {
/*  86 */         id |= this.extBlockIds.a(i, j, k) << 8;
/*     */       } 
/*     */       
/*  89 */       return Block.getById(id);
/*     */     } 
/*     */ 
/*     */     
/*  93 */     int l = this.blockIds[j << 8 | k << 4 | i] & 0xFF;
/*     */     
/*  95 */     if (this.extBlockIds != null) {
/*  96 */       l |= this.extBlockIds.a(i, j, k) << 8;
/*     */     }
/*     */     
/*  99 */     return Block.getById(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTypeId(int i, int j, int k, Block block) {
/* 104 */     Block block1 = getTypeId(i, j, k);
/* 105 */     if (block == block1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 110 */     if (block1 != Blocks.AIR) {
/* 111 */       this.nonEmptyBlockCount--;
/* 112 */       if (block1.isTicking()) {
/* 113 */         this.tickingBlockCount--;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if (block != Blocks.AIR) {
/* 118 */       this.nonEmptyBlockCount++;
/* 119 */       if (block.isTicking()) {
/* 120 */         this.tickingBlockCount++;
/*     */       }
/*     */     } 
/*     */     
/* 124 */     int i1 = Block.getId(block);
/*     */ 
/*     */     
/* 127 */     if (this.blockIds == null) {
/* 128 */       this.blockIds = new byte[4096];
/* 129 */       Arrays.fill(this.blockIds, (byte)(this.compactId & 0xFF));
/*     */     } 
/*     */ 
/*     */     
/* 133 */     this.blockIds[j << 8 | k << 4 | i] = (byte)(i1 & 0xFF);
/* 134 */     if (i1 > 255) {
/* 135 */       if (this.extBlockIds == null) {
/* 136 */         this.extBlockIds = expandCompactNibble(this.compactExtId);
/*     */       }
/*     */       
/* 139 */       this.extBlockIds.a(i, j, k, (i1 & 0xF00) >> 8);
/* 140 */     } else if (this.extBlockIds != null) {
/* 141 */       this.extBlockIds.a(i, j, k, 0);
/*     */     } 
/*     */     
/* 144 */     this.isDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getData(int i, int j, int k) {
/* 149 */     if (this.blockData == null) {
/* 150 */       return this.compactData;
/*     */     }
/*     */     
/* 153 */     return this.blockData.a(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(int i, int j, int k, int l) {
/* 158 */     if (this.blockData == null) {
/* 159 */       if (this.compactData == l) {
/*     */         return;
/*     */       }
/* 162 */       this.blockData = expandCompactNibble(this.compactData);
/*     */     } 
/*     */     
/* 165 */     this.blockData.a(i, j, k, l);
/* 166 */     this.isDirty = true;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 170 */     return (this.nonEmptyBlockCount == 0);
/*     */   }
/*     */   
/*     */   public boolean shouldTick() {
/* 174 */     return (this.tickingBlockCount > 0);
/*     */   }
/*     */   
/*     */   public int getYPosition() {
/* 178 */     return this.yPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkyLight(int i, int j, int k, int l) {
/* 183 */     if (this.skyLight == null) {
/* 184 */       if (this.compactSky == l) {
/*     */         return;
/*     */       }
/* 187 */       this.skyLight = expandCompactNibble(this.compactSky);
/*     */     } 
/*     */     
/* 190 */     this.skyLight.a(i, j, k, l);
/* 191 */     this.isDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkyLight(int i, int j, int k) {
/* 196 */     if (this.skyLight == null) {
/* 197 */       return this.compactSky;
/*     */     }
/*     */     
/* 200 */     return this.skyLight.a(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEmittedLight(int i, int j, int k, int l) {
/* 205 */     if (this.emittedLight == null) {
/* 206 */       if (this.compactEmitted == l) {
/*     */         return;
/*     */       }
/* 209 */       this.emittedLight = expandCompactNibble(this.compactEmitted);
/*     */     } 
/*     */     
/* 212 */     this.emittedLight.a(i, j, k, l);
/* 213 */     this.isDirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEmittedLight(int i, int j, int k) {
/* 218 */     if (this.emittedLight == null) {
/* 219 */       return this.compactEmitted;
/*     */     }
/*     */     
/* 222 */     return this.emittedLight.a(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recalcBlockCounts() {
/* 227 */     int cntNonEmpty = 0;
/* 228 */     int cntTicking = 0;
/*     */     
/* 230 */     if (this.blockIds == null) {
/* 231 */       int id = this.compactId;
/* 232 */       if (this.extBlockIds == null) {
/* 233 */         id |= this.compactExtId << 8;
/* 234 */         if (id > 0) {
/* 235 */           Block block = Block.getById(id);
/* 236 */           if (block == null) {
/* 237 */             this.compactId = 0;
/* 238 */             this.compactExtId = 0;
/*     */           } else {
/* 240 */             cntNonEmpty = 4096;
/* 241 */             if (block.isTicking()) {
/* 242 */               cntTicking = 4096;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 247 */         byte[] ext = this.extBlockIds.a;
/* 248 */         for (int off = 0, off2 = 0; off < 4096; ) {
/* 249 */           byte extid = ext[off2];
/* 250 */           int l = id & 0xFF | (extid & 0xF) << 8;
/* 251 */           if (l > 0) {
/* 252 */             Block block = Block.getById(l);
/* 253 */             if (block == null) {
/* 254 */               this.compactId = 0;
/* 255 */               ext[off2] = (byte)(ext[off2] & 0xF0);
/*     */             } else {
/* 257 */               cntNonEmpty++;
/* 258 */               if (block.isTicking()) {
/* 259 */                 cntTicking++;
/*     */               }
/*     */             } 
/*     */           } 
/* 263 */           off++;
/* 264 */           l = id & 0xFF | (extid & 0xF0) << 4;
/* 265 */           if (l > 0) {
/* 266 */             Block block = Block.getById(l);
/* 267 */             if (block == null) {
/* 268 */               this.compactId = 0;
/* 269 */               ext[off2] = (byte)(ext[off2] & 0xF);
/*     */             } else {
/* 271 */               cntNonEmpty++;
/* 272 */               if (block.isTicking()) {
/* 273 */                 cntTicking++;
/*     */               }
/*     */             } 
/*     */           } 
/* 277 */           off++;
/* 278 */           off2++;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 282 */       byte[] blkIds = this.blockIds;
/* 283 */       if (this.extBlockIds == null) {
/* 284 */         for (int off = 0; off < blkIds.length; off++) {
/* 285 */           int l = blkIds[off] & 0xFF;
/* 286 */           if (l > 0) {
/* 287 */             if (Block.getById(l) == null) {
/* 288 */               blkIds[off] = 0;
/*     */             } else {
/* 290 */               cntNonEmpty++;
/* 291 */               if (Block.getById(l).isTicking()) {
/* 292 */                 cntTicking++;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } else {
/* 298 */         byte[] ext = this.extBlockIds.a;
/* 299 */         for (int off = 0, off2 = 0; off < blkIds.length; ) {
/* 300 */           byte extid = ext[off2];
/* 301 */           int l = blkIds[off] & 0xFF | (extid & 0xF) << 8;
/* 302 */           if (l > 0) {
/* 303 */             if (Block.getById(l) == null) {
/* 304 */               blkIds[off] = 0;
/* 305 */               ext[off2] = (byte)(ext[off2] & 0xF0);
/*     */             } else {
/* 307 */               cntNonEmpty++;
/* 308 */               if (Block.getById(l).isTicking()) {
/* 309 */                 cntTicking++;
/*     */               }
/*     */             } 
/*     */           }
/* 313 */           off++;
/* 314 */           l = blkIds[off] & 0xFF | (extid & 0xF0) << 4;
/* 315 */           if (l > 0) {
/* 316 */             if (Block.getById(l) == null) {
/* 317 */               blkIds[off] = 0;
/* 318 */               ext[off2] = (byte)(ext[off2] & 0xF);
/*     */             } else {
/* 320 */               cntNonEmpty++;
/* 321 */               if (Block.getById(l).isTicking()) {
/* 322 */                 cntTicking++;
/*     */               }
/*     */             } 
/*     */           }
/* 326 */           off++;
/* 327 */           off2++;
/*     */         } 
/*     */       } 
/*     */     } 
/* 331 */     this.nonEmptyBlockCount = cntNonEmpty;
/* 332 */     this.tickingBlockCount = cntTicking;
/*     */   }
/*     */ 
/*     */   
/*     */   public void old_recalcBlockCounts() {
/* 337 */     this.nonEmptyBlockCount = 0;
/* 338 */     this.tickingBlockCount = 0;
/*     */     
/* 340 */     for (int i = 0; i < 16; i++) {
/* 341 */       for (int j = 0; j < 16; j++) {
/* 342 */         for (int k = 0; k < 16; k++) {
/* 343 */           Block block = getTypeId(i, j, k);
/*     */           
/* 345 */           if (block != Blocks.AIR) {
/* 346 */             this.nonEmptyBlockCount++;
/* 347 */             if (block.isTicking()) {
/* 348 */               this.tickingBlockCount++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getIdArray() {
/* 358 */     if (this.blockIds == null) {
/* 359 */       byte[] ids = new byte[4096];
/* 360 */       Arrays.fill(ids, (byte)(this.compactId & 0xFF));
/* 361 */       return ids;
/*     */     } 
/*     */     
/* 364 */     return this.blockIds;
/*     */   }
/*     */ 
/*     */   
/*     */   public NibbleArray getExtendedIdArray() {
/* 369 */     if (this.extBlockIds == null && this.compactExtId != 0) {
/* 370 */       return compactPregen[this.compactExtId];
/*     */     }
/*     */     
/* 373 */     return this.extBlockIds;
/*     */   }
/*     */ 
/*     */   
/*     */   public NibbleArray getDataArray() {
/* 378 */     if (this.blockData == null) {
/* 379 */       return compactPregen[this.compactData];
/*     */     }
/*     */     
/* 382 */     return this.blockData;
/*     */   }
/*     */ 
/*     */   
/*     */   public NibbleArray getEmittedLightArray() {
/* 387 */     if (this.emittedLight == null) {
/* 388 */       return compactPregen[this.compactEmitted];
/*     */     }
/*     */     
/* 391 */     return this.emittedLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public NibbleArray getSkyLightArray() {
/* 396 */     if (this.skyLight == null && this.compactSky != -1) {
/* 397 */       return compactPregen[this.compactSky];
/*     */     }
/*     */     
/* 400 */     return this.skyLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIdArray(byte[] abyte) {
/* 405 */     if (abyte == null) {
/* 406 */       this.compactId = 0;
/* 407 */       this.blockIds = null; return;
/*     */     } 
/* 409 */     if (canBeCompact(abyte)) {
/* 410 */       this.compactId = abyte[0] & 0xFF;
/*     */       
/*     */       return;
/*     */     } 
/* 414 */     this.blockIds = validateByteArray(abyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExtendedIdArray(NibbleArray nibblearray) {
/* 419 */     if (nibblearray == null) {
/* 420 */       this.compactExtId = 0;
/* 421 */       this.extBlockIds = null; return;
/*     */     } 
/* 423 */     if (canBeCompact(nibblearray.a)) {
/* 424 */       this.compactExtId = (byte)(nibblearray.a(0, 0, 0) & 0xF);
/*     */       
/*     */       return;
/*     */     } 
/* 428 */     this.extBlockIds = validateNibbleArray(nibblearray);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDataArray(NibbleArray nibblearray) {
/* 433 */     if (nibblearray == null) {
/* 434 */       this.compactData = 0;
/* 435 */       this.blockData = null; return;
/*     */     } 
/* 437 */     if (canBeCompact(nibblearray.a)) {
/* 438 */       this.compactData = (byte)(nibblearray.a(0, 0, 0) & 0xF);
/*     */       
/*     */       return;
/*     */     } 
/* 442 */     this.blockData = validateNibbleArray(nibblearray);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEmittedLightArray(NibbleArray nibblearray) {
/* 447 */     if (nibblearray == null) {
/* 448 */       this.compactEmitted = 0;
/* 449 */       this.emittedLight = null; return;
/*     */     } 
/* 451 */     if (canBeCompact(nibblearray.a)) {
/* 452 */       this.compactEmitted = (byte)(nibblearray.a(0, 0, 0) & 0xF);
/*     */       
/*     */       return;
/*     */     } 
/* 456 */     this.emittedLight = validateNibbleArray(nibblearray);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkyLightArray(NibbleArray nibblearray) {
/* 461 */     if (nibblearray == null) {
/* 462 */       this.compactSky = -1;
/* 463 */       this.skyLight = null; return;
/*     */     } 
/* 465 */     if (canBeCompact(nibblearray.a)) {
/* 466 */       this.compactSky = (byte)(nibblearray.a(0, 0, 0) & 0xF);
/*     */       
/*     */       return;
/*     */     } 
/* 470 */     this.skyLight = validateNibbleArray(nibblearray);
/*     */   }
/*     */ 
/*     */   
/*     */   private NibbleArray validateNibbleArray(NibbleArray nibbleArray) {
/* 475 */     if (nibbleArray != null && nibbleArray.a.length < 2048) {
/* 476 */       byte[] newArray = new byte[2048];
/* 477 */       System.arraycopy(nibbleArray.a, 0, newArray, 0, nibbleArray.a.length);
/* 478 */       nibbleArray = new NibbleArray(newArray, 4);
/*     */     } 
/*     */     
/* 481 */     return nibbleArray;
/*     */   }
/*     */   
/*     */   private byte[] validateByteArray(byte[] byteArray) {
/* 485 */     if (byteArray != null && byteArray.length < 4096) {
/* 486 */       byte[] newArray = new byte[4096];
/* 487 */       System.arraycopy(byteArray, 0, newArray, 0, byteArray.length);
/* 488 */       byteArray = newArray;
/*     */     } 
/*     */     
/* 491 */     return byteArray;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */