/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenPyramidPiece
/*     */   extends WorldGenScatteredPiece
/*     */ {
/*  99 */   private boolean[] e = new boolean[4];
/*     */ 
/*     */   
/* 102 */   private static final StructurePieceTreasure[] i = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
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
/*     */   public WorldGenPyramidPiece() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenPyramidPiece(Random paramRandom, int paramInt1, int paramInt2) {
/* 124 */     super(paramRandom, paramInt1, 64, paramInt2, 21, 15, 21);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 129 */     super.a(paramNBTTagCompound);
/* 130 */     paramNBTTagCompound.setBoolean("hasPlacedChest0", this.e[0]);
/* 131 */     paramNBTTagCompound.setBoolean("hasPlacedChest1", this.e[1]);
/* 132 */     paramNBTTagCompound.setBoolean("hasPlacedChest2", this.e[2]);
/* 133 */     paramNBTTagCompound.setBoolean("hasPlacedChest3", this.e[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 138 */     super.b(paramNBTTagCompound);
/* 139 */     this.e[0] = paramNBTTagCompound.getBoolean("hasPlacedChest0");
/* 140 */     this.e[1] = paramNBTTagCompound.getBoolean("hasPlacedChest1");
/* 141 */     this.e[2] = paramNBTTagCompound.getBoolean("hasPlacedChest2");
/* 142 */     this.e[3] = paramNBTTagCompound.getBoolean("hasPlacedChest3");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 149 */     a(paramWorld, paramStructureBoundingBox, 0, -4, 0, this.a - 1, 0, this.c - 1, Blocks.SANDSTONE, Blocks.SANDSTONE, false); int i;
/* 150 */     for (i = 1; i <= 9; i++) {
/* 151 */       a(paramWorld, paramStructureBoundingBox, i, i, i, this.a - 1 - i, i, this.c - 1 - i, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 152 */       a(paramWorld, paramStructureBoundingBox, i + 1, i, i + 1, this.a - 2 - i, i, this.c - 2 - i, Blocks.AIR, Blocks.AIR, false);
/*     */     } 
/* 154 */     for (i = 0; i < this.a; i++) {
/* 155 */       for (byte b1 = 0; b1 < this.c; b1++) {
/* 156 */         byte b2 = -5;
/* 157 */         b(paramWorld, Blocks.SANDSTONE, 0, i, b2, b1, paramStructureBoundingBox);
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     i = a(Blocks.SANDSTONE_STAIRS, 3);
/* 162 */     int j = a(Blocks.SANDSTONE_STAIRS, 2);
/* 163 */     int k = a(Blocks.SANDSTONE_STAIRS, 0);
/* 164 */     int m = a(Blocks.SANDSTONE_STAIRS, 1);
/* 165 */     boolean bool = true;
/* 166 */     byte b = 11;
/*     */ 
/*     */     
/* 169 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE, Blocks.AIR, false);
/* 170 */     a(paramWorld, paramStructureBoundingBox, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 171 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, i, 2, 10, 0, paramStructureBoundingBox);
/* 172 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, j, 2, 10, 4, paramStructureBoundingBox);
/* 173 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, k, 0, 10, 2, paramStructureBoundingBox);
/* 174 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, m, 4, 10, 2, paramStructureBoundingBox);
/* 175 */     a(paramWorld, paramStructureBoundingBox, this.a - 5, 0, 0, this.a - 1, 9, 4, Blocks.SANDSTONE, Blocks.AIR, false);
/* 176 */     a(paramWorld, paramStructureBoundingBox, this.a - 4, 10, 1, this.a - 2, 10, 3, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 177 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, i, this.a - 3, 10, 0, paramStructureBoundingBox);
/* 178 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, j, this.a - 3, 10, 4, paramStructureBoundingBox);
/* 179 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, k, this.a - 5, 10, 2, paramStructureBoundingBox);
/* 180 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, m, this.a - 1, 10, 2, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 183 */     a(paramWorld, paramStructureBoundingBox, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE, Blocks.AIR, false);
/* 184 */     a(paramWorld, paramStructureBoundingBox, 9, 1, 0, 11, 3, 4, Blocks.AIR, Blocks.AIR, false);
/* 185 */     a(paramWorld, Blocks.SANDSTONE, 2, 9, 1, 1, paramStructureBoundingBox);
/* 186 */     a(paramWorld, Blocks.SANDSTONE, 2, 9, 2, 1, paramStructureBoundingBox);
/* 187 */     a(paramWorld, Blocks.SANDSTONE, 2, 9, 3, 1, paramStructureBoundingBox);
/* 188 */     a(paramWorld, Blocks.SANDSTONE, 2, 10, 3, 1, paramStructureBoundingBox);
/* 189 */     a(paramWorld, Blocks.SANDSTONE, 2, 11, 3, 1, paramStructureBoundingBox);
/* 190 */     a(paramWorld, Blocks.SANDSTONE, 2, 11, 2, 1, paramStructureBoundingBox);
/* 191 */     a(paramWorld, Blocks.SANDSTONE, 2, 11, 1, 1, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 194 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE, Blocks.AIR, false);
/* 195 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 8, 2, 2, Blocks.AIR, Blocks.AIR, false);
/* 196 */     a(paramWorld, paramStructureBoundingBox, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE, Blocks.AIR, false);
/* 197 */     a(paramWorld, paramStructureBoundingBox, 12, 1, 2, 16, 2, 2, Blocks.AIR, Blocks.AIR, false);
/*     */ 
/*     */     
/* 200 */     a(paramWorld, paramStructureBoundingBox, 5, 4, 5, this.a - 6, 4, this.c - 6, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 201 */     a(paramWorld, paramStructureBoundingBox, 9, 4, 9, 11, 4, 11, Blocks.AIR, Blocks.AIR, false);
/* 202 */     a(paramWorld, paramStructureBoundingBox, 8, 1, 8, 8, 3, 8, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 203 */     a(paramWorld, paramStructureBoundingBox, 12, 1, 8, 12, 3, 8, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 204 */     a(paramWorld, paramStructureBoundingBox, 8, 1, 12, 8, 3, 12, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 205 */     a(paramWorld, paramStructureBoundingBox, 12, 1, 12, 12, 3, 12, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/*     */ 
/*     */     
/* 208 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 209 */     a(paramWorld, paramStructureBoundingBox, this.a - 5, 1, 5, this.a - 2, 4, 11, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 210 */     a(paramWorld, paramStructureBoundingBox, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 211 */     a(paramWorld, paramStructureBoundingBox, this.a - 7, 7, 9, this.a - 7, 7, 11, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 212 */     a(paramWorld, paramStructureBoundingBox, 5, 5, 9, 5, 7, 11, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 213 */     a(paramWorld, paramStructureBoundingBox, this.a - 6, 5, 9, this.a - 6, 7, 11, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 214 */     a(paramWorld, Blocks.AIR, 0, 5, 5, 10, paramStructureBoundingBox);
/* 215 */     a(paramWorld, Blocks.AIR, 0, 5, 6, 10, paramStructureBoundingBox);
/* 216 */     a(paramWorld, Blocks.AIR, 0, 6, 6, 10, paramStructureBoundingBox);
/* 217 */     a(paramWorld, Blocks.AIR, 0, this.a - 6, 5, 10, paramStructureBoundingBox);
/* 218 */     a(paramWorld, Blocks.AIR, 0, this.a - 6, 6, 10, paramStructureBoundingBox);
/* 219 */     a(paramWorld, Blocks.AIR, 0, this.a - 7, 6, 10, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 222 */     a(paramWorld, paramStructureBoundingBox, 2, 4, 4, 2, 6, 4, Blocks.AIR, Blocks.AIR, false);
/* 223 */     a(paramWorld, paramStructureBoundingBox, this.a - 3, 4, 4, this.a - 3, 6, 4, Blocks.AIR, Blocks.AIR, false);
/* 224 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, i, 2, 4, 5, paramStructureBoundingBox);
/* 225 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, i, 2, 3, 4, paramStructureBoundingBox);
/* 226 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, i, this.a - 3, 4, 5, paramStructureBoundingBox);
/* 227 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, i, this.a - 3, 3, 4, paramStructureBoundingBox);
/* 228 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 229 */     a(paramWorld, paramStructureBoundingBox, this.a - 3, 1, 3, this.a - 2, 2, 3, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 230 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, 0, 1, 1, 2, paramStructureBoundingBox);
/* 231 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, 0, this.a - 2, 1, 2, paramStructureBoundingBox);
/* 232 */     a(paramWorld, Blocks.STEP, 1, 1, 2, 2, paramStructureBoundingBox);
/* 233 */     a(paramWorld, Blocks.STEP, 1, this.a - 2, 2, 2, paramStructureBoundingBox);
/* 234 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, m, 2, 1, 2, paramStructureBoundingBox);
/* 235 */     a(paramWorld, Blocks.SANDSTONE_STAIRS, k, this.a - 3, 1, 2, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 238 */     a(paramWorld, paramStructureBoundingBox, 4, 3, 5, 4, 3, 18, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 239 */     a(paramWorld, paramStructureBoundingBox, this.a - 5, 3, 5, this.a - 5, 3, 17, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 240 */     a(paramWorld, paramStructureBoundingBox, 3, 1, 5, 4, 2, 16, Blocks.AIR, Blocks.AIR, false);
/* 241 */     a(paramWorld, paramStructureBoundingBox, this.a - 6, 1, 5, this.a - 5, 2, 16, Blocks.AIR, Blocks.AIR, false); int n;
/* 242 */     for (n = 5; n <= 17; n += 2) {
/* 243 */       a(paramWorld, Blocks.SANDSTONE, 2, 4, 1, n, paramStructureBoundingBox);
/* 244 */       a(paramWorld, Blocks.SANDSTONE, 1, 4, 2, n, paramStructureBoundingBox);
/* 245 */       a(paramWorld, Blocks.SANDSTONE, 2, this.a - 5, 1, n, paramStructureBoundingBox);
/* 246 */       a(paramWorld, Blocks.SANDSTONE, 1, this.a - 5, 2, n, paramStructureBoundingBox);
/*     */     } 
/* 248 */     a(paramWorld, Blocks.WOOL, bool, 10, 0, 7, paramStructureBoundingBox);
/* 249 */     a(paramWorld, Blocks.WOOL, bool, 10, 0, 8, paramStructureBoundingBox);
/* 250 */     a(paramWorld, Blocks.WOOL, bool, 9, 0, 9, paramStructureBoundingBox);
/* 251 */     a(paramWorld, Blocks.WOOL, bool, 11, 0, 9, paramStructureBoundingBox);
/* 252 */     a(paramWorld, Blocks.WOOL, bool, 8, 0, 10, paramStructureBoundingBox);
/* 253 */     a(paramWorld, Blocks.WOOL, bool, 12, 0, 10, paramStructureBoundingBox);
/* 254 */     a(paramWorld, Blocks.WOOL, bool, 7, 0, 10, paramStructureBoundingBox);
/* 255 */     a(paramWorld, Blocks.WOOL, bool, 13, 0, 10, paramStructureBoundingBox);
/* 256 */     a(paramWorld, Blocks.WOOL, bool, 9, 0, 11, paramStructureBoundingBox);
/* 257 */     a(paramWorld, Blocks.WOOL, bool, 11, 0, 11, paramStructureBoundingBox);
/* 258 */     a(paramWorld, Blocks.WOOL, bool, 10, 0, 12, paramStructureBoundingBox);
/* 259 */     a(paramWorld, Blocks.WOOL, bool, 10, 0, 13, paramStructureBoundingBox);
/* 260 */     a(paramWorld, Blocks.WOOL, b, 10, 0, 10, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 263 */     for (n = 0; n <= this.a - 1; n += this.a - 1) {
/* 264 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 2, 1, paramStructureBoundingBox);
/* 265 */       a(paramWorld, Blocks.WOOL, bool, n, 2, 2, paramStructureBoundingBox);
/* 266 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 2, 3, paramStructureBoundingBox);
/* 267 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 3, 1, paramStructureBoundingBox);
/* 268 */       a(paramWorld, Blocks.WOOL, bool, n, 3, 2, paramStructureBoundingBox);
/* 269 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 3, 3, paramStructureBoundingBox);
/* 270 */       a(paramWorld, Blocks.WOOL, bool, n, 4, 1, paramStructureBoundingBox);
/* 271 */       a(paramWorld, Blocks.SANDSTONE, 1, n, 4, 2, paramStructureBoundingBox);
/* 272 */       a(paramWorld, Blocks.WOOL, bool, n, 4, 3, paramStructureBoundingBox);
/* 273 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 5, 1, paramStructureBoundingBox);
/* 274 */       a(paramWorld, Blocks.WOOL, bool, n, 5, 2, paramStructureBoundingBox);
/* 275 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 5, 3, paramStructureBoundingBox);
/* 276 */       a(paramWorld, Blocks.WOOL, bool, n, 6, 1, paramStructureBoundingBox);
/* 277 */       a(paramWorld, Blocks.SANDSTONE, 1, n, 6, 2, paramStructureBoundingBox);
/* 278 */       a(paramWorld, Blocks.WOOL, bool, n, 6, 3, paramStructureBoundingBox);
/* 279 */       a(paramWorld, Blocks.WOOL, bool, n, 7, 1, paramStructureBoundingBox);
/* 280 */       a(paramWorld, Blocks.WOOL, bool, n, 7, 2, paramStructureBoundingBox);
/* 281 */       a(paramWorld, Blocks.WOOL, bool, n, 7, 3, paramStructureBoundingBox);
/* 282 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 8, 1, paramStructureBoundingBox);
/* 283 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 8, 2, paramStructureBoundingBox);
/* 284 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 8, 3, paramStructureBoundingBox);
/*     */     } 
/* 286 */     for (n = 2; n <= this.a - 3; n += this.a - 3 - 2) {
/* 287 */       a(paramWorld, Blocks.SANDSTONE, 2, n - 1, 2, 0, paramStructureBoundingBox);
/* 288 */       a(paramWorld, Blocks.WOOL, bool, n, 2, 0, paramStructureBoundingBox);
/* 289 */       a(paramWorld, Blocks.SANDSTONE, 2, n + 1, 2, 0, paramStructureBoundingBox);
/* 290 */       a(paramWorld, Blocks.SANDSTONE, 2, n - 1, 3, 0, paramStructureBoundingBox);
/* 291 */       a(paramWorld, Blocks.WOOL, bool, n, 3, 0, paramStructureBoundingBox);
/* 292 */       a(paramWorld, Blocks.SANDSTONE, 2, n + 1, 3, 0, paramStructureBoundingBox);
/* 293 */       a(paramWorld, Blocks.WOOL, bool, n - 1, 4, 0, paramStructureBoundingBox);
/* 294 */       a(paramWorld, Blocks.SANDSTONE, 1, n, 4, 0, paramStructureBoundingBox);
/* 295 */       a(paramWorld, Blocks.WOOL, bool, n + 1, 4, 0, paramStructureBoundingBox);
/* 296 */       a(paramWorld, Blocks.SANDSTONE, 2, n - 1, 5, 0, paramStructureBoundingBox);
/* 297 */       a(paramWorld, Blocks.WOOL, bool, n, 5, 0, paramStructureBoundingBox);
/* 298 */       a(paramWorld, Blocks.SANDSTONE, 2, n + 1, 5, 0, paramStructureBoundingBox);
/* 299 */       a(paramWorld, Blocks.WOOL, bool, n - 1, 6, 0, paramStructureBoundingBox);
/* 300 */       a(paramWorld, Blocks.SANDSTONE, 1, n, 6, 0, paramStructureBoundingBox);
/* 301 */       a(paramWorld, Blocks.WOOL, bool, n + 1, 6, 0, paramStructureBoundingBox);
/* 302 */       a(paramWorld, Blocks.WOOL, bool, n - 1, 7, 0, paramStructureBoundingBox);
/* 303 */       a(paramWorld, Blocks.WOOL, bool, n, 7, 0, paramStructureBoundingBox);
/* 304 */       a(paramWorld, Blocks.WOOL, bool, n + 1, 7, 0, paramStructureBoundingBox);
/* 305 */       a(paramWorld, Blocks.SANDSTONE, 2, n - 1, 8, 0, paramStructureBoundingBox);
/* 306 */       a(paramWorld, Blocks.SANDSTONE, 2, n, 8, 0, paramStructureBoundingBox);
/* 307 */       a(paramWorld, Blocks.SANDSTONE, 2, n + 1, 8, 0, paramStructureBoundingBox);
/*     */     } 
/* 309 */     a(paramWorld, paramStructureBoundingBox, 8, 4, 0, 12, 6, 0, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 310 */     a(paramWorld, Blocks.AIR, 0, 8, 6, 0, paramStructureBoundingBox);
/* 311 */     a(paramWorld, Blocks.AIR, 0, 12, 6, 0, paramStructureBoundingBox);
/* 312 */     a(paramWorld, Blocks.WOOL, bool, 9, 5, 0, paramStructureBoundingBox);
/* 313 */     a(paramWorld, Blocks.SANDSTONE, 1, 10, 5, 0, paramStructureBoundingBox);
/* 314 */     a(paramWorld, Blocks.WOOL, bool, 11, 5, 0, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 317 */     a(paramWorld, paramStructureBoundingBox, 8, -14, 8, 12, -11, 12, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 318 */     a(paramWorld, paramStructureBoundingBox, 8, -10, 8, 12, -10, 12, Blocks.SANDSTONE, 1, Blocks.SANDSTONE, 1, false);
/* 319 */     a(paramWorld, paramStructureBoundingBox, 8, -9, 8, 12, -9, 12, Blocks.SANDSTONE, 2, Blocks.SANDSTONE, 2, false);
/* 320 */     a(paramWorld, paramStructureBoundingBox, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE, Blocks.SANDSTONE, false);
/* 321 */     a(paramWorld, paramStructureBoundingBox, 9, -11, 9, 11, -1, 11, Blocks.AIR, Blocks.AIR, false);
/* 322 */     a(paramWorld, Blocks.STONE_PLATE, 0, 10, -11, 10, paramStructureBoundingBox);
/* 323 */     a(paramWorld, paramStructureBoundingBox, 9, -13, 9, 11, -13, 11, Blocks.TNT, Blocks.AIR, false);
/* 324 */     a(paramWorld, Blocks.AIR, 0, 8, -11, 10, paramStructureBoundingBox);
/* 325 */     a(paramWorld, Blocks.AIR, 0, 8, -10, 10, paramStructureBoundingBox);
/* 326 */     a(paramWorld, Blocks.SANDSTONE, 1, 7, -10, 10, paramStructureBoundingBox);
/* 327 */     a(paramWorld, Blocks.SANDSTONE, 2, 7, -11, 10, paramStructureBoundingBox);
/* 328 */     a(paramWorld, Blocks.AIR, 0, 12, -11, 10, paramStructureBoundingBox);
/* 329 */     a(paramWorld, Blocks.AIR, 0, 12, -10, 10, paramStructureBoundingBox);
/* 330 */     a(paramWorld, Blocks.SANDSTONE, 1, 13, -10, 10, paramStructureBoundingBox);
/* 331 */     a(paramWorld, Blocks.SANDSTONE, 2, 13, -11, 10, paramStructureBoundingBox);
/* 332 */     a(paramWorld, Blocks.AIR, 0, 10, -11, 8, paramStructureBoundingBox);
/* 333 */     a(paramWorld, Blocks.AIR, 0, 10, -10, 8, paramStructureBoundingBox);
/* 334 */     a(paramWorld, Blocks.SANDSTONE, 1, 10, -10, 7, paramStructureBoundingBox);
/* 335 */     a(paramWorld, Blocks.SANDSTONE, 2, 10, -11, 7, paramStructureBoundingBox);
/* 336 */     a(paramWorld, Blocks.AIR, 0, 10, -11, 12, paramStructureBoundingBox);
/* 337 */     a(paramWorld, Blocks.AIR, 0, 10, -10, 12, paramStructureBoundingBox);
/* 338 */     a(paramWorld, Blocks.SANDSTONE, 1, 10, -10, 13, paramStructureBoundingBox);
/* 339 */     a(paramWorld, Blocks.SANDSTONE, 2, 10, -11, 13, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 342 */     for (n = 0; n < 4; n++) {
/* 343 */       if (!this.e[n]) {
/* 344 */         int i1 = Direction.a[n] * 2;
/* 345 */         int i2 = Direction.b[n] * 2;
/* 346 */         this.e[n] = a(paramWorld, paramStructureBoundingBox, paramRandom, 10 + i1, -11, 10 + i2, StructurePieceTreasure.a(i, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(5));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 351 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenPyramidPiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */