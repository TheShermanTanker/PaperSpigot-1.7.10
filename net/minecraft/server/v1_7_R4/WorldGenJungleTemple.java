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
/*     */ public class WorldGenJungleTemple
/*     */   extends WorldGenScatteredPiece
/*     */ {
/*     */   private boolean e;
/*     */   private boolean i;
/*     */   private boolean j;
/*     */   private boolean k;
/* 364 */   private static final StructurePieceTreasure[] l = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
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
/* 378 */   private static final StructurePieceTreasure[] m = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.ARROW, 0, 2, 7, 30) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenJungleTemple() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenJungleTemple(Random paramRandom, int paramInt1, int paramInt2) {
/* 389 */     super(paramRandom, paramInt1, 64, paramInt2, 12, 10, 15);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 394 */     super.a(paramNBTTagCompound);
/* 395 */     paramNBTTagCompound.setBoolean("placedMainChest", this.e);
/* 396 */     paramNBTTagCompound.setBoolean("placedHiddenChest", this.i);
/* 397 */     paramNBTTagCompound.setBoolean("placedTrap1", this.j);
/* 398 */     paramNBTTagCompound.setBoolean("placedTrap2", this.k);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 403 */     super.b(paramNBTTagCompound);
/* 404 */     this.e = paramNBTTagCompound.getBoolean("placedMainChest");
/* 405 */     this.i = paramNBTTagCompound.getBoolean("placedHiddenChest");
/* 406 */     this.j = paramNBTTagCompound.getBoolean("placedTrap1");
/* 407 */     this.k = paramNBTTagCompound.getBoolean("placedTrap2");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 413 */     if (!a(paramWorld, paramStructureBoundingBox, 0)) {
/* 414 */       return false;
/*     */     }
/*     */     
/* 417 */     int i = a(Blocks.COBBLESTONE_STAIRS, 3);
/* 418 */     int j = a(Blocks.COBBLESTONE_STAIRS, 2);
/* 419 */     int k = a(Blocks.COBBLESTONE_STAIRS, 0);
/* 420 */     int m = a(Blocks.COBBLESTONE_STAIRS, 1);
/*     */ 
/*     */     
/* 423 */     a(paramWorld, paramStructureBoundingBox, 0, -4, 0, this.a - 1, 0, this.c - 1, false, paramRandom, n);
/*     */ 
/*     */     
/* 426 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 9, 2, 2, false, paramRandom, n);
/* 427 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 12, 9, 2, 12, false, paramRandom, n);
/* 428 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 3, 2, 2, 11, false, paramRandom, n);
/* 429 */     a(paramWorld, paramStructureBoundingBox, 9, 1, 3, 9, 2, 11, false, paramRandom, n);
/*     */ 
/*     */     
/* 432 */     a(paramWorld, paramStructureBoundingBox, 1, 3, 1, 10, 6, 1, false, paramRandom, n);
/* 433 */     a(paramWorld, paramStructureBoundingBox, 1, 3, 13, 10, 6, 13, false, paramRandom, n);
/* 434 */     a(paramWorld, paramStructureBoundingBox, 1, 3, 2, 1, 6, 12, false, paramRandom, n);
/* 435 */     a(paramWorld, paramStructureBoundingBox, 10, 3, 2, 10, 6, 12, false, paramRandom, n);
/*     */ 
/*     */     
/* 438 */     a(paramWorld, paramStructureBoundingBox, 2, 3, 2, 9, 3, 12, false, paramRandom, n);
/* 439 */     a(paramWorld, paramStructureBoundingBox, 2, 6, 2, 9, 6, 12, false, paramRandom, n);
/* 440 */     a(paramWorld, paramStructureBoundingBox, 3, 7, 3, 8, 7, 11, false, paramRandom, n);
/* 441 */     a(paramWorld, paramStructureBoundingBox, 4, 8, 4, 7, 8, 10, false, paramRandom, n);
/*     */ 
/*     */     
/* 444 */     a(paramWorld, paramStructureBoundingBox, 3, 1, 3, 8, 2, 11);
/* 445 */     a(paramWorld, paramStructureBoundingBox, 4, 3, 6, 7, 3, 9);
/* 446 */     a(paramWorld, paramStructureBoundingBox, 2, 4, 2, 9, 5, 12);
/* 447 */     a(paramWorld, paramStructureBoundingBox, 4, 6, 5, 7, 6, 9);
/* 448 */     a(paramWorld, paramStructureBoundingBox, 5, 7, 6, 6, 7, 8);
/*     */ 
/*     */     
/* 451 */     a(paramWorld, paramStructureBoundingBox, 5, 1, 2, 6, 2, 2);
/* 452 */     a(paramWorld, paramStructureBoundingBox, 5, 2, 12, 6, 2, 12);
/* 453 */     a(paramWorld, paramStructureBoundingBox, 5, 5, 1, 6, 5, 1);
/* 454 */     a(paramWorld, paramStructureBoundingBox, 5, 5, 13, 6, 5, 13);
/* 455 */     a(paramWorld, Blocks.AIR, 0, 1, 5, 5, paramStructureBoundingBox);
/* 456 */     a(paramWorld, Blocks.AIR, 0, 10, 5, 5, paramStructureBoundingBox);
/* 457 */     a(paramWorld, Blocks.AIR, 0, 1, 5, 9, paramStructureBoundingBox);
/* 458 */     a(paramWorld, Blocks.AIR, 0, 10, 5, 9, paramStructureBoundingBox);
/*     */     
/*     */     byte b;
/* 461 */     for (b = 0; b <= 14; b += 14) {
/* 462 */       a(paramWorld, paramStructureBoundingBox, 2, 4, b, 2, 5, b, false, paramRandom, n);
/* 463 */       a(paramWorld, paramStructureBoundingBox, 4, 4, b, 4, 5, b, false, paramRandom, n);
/* 464 */       a(paramWorld, paramStructureBoundingBox, 7, 4, b, 7, 5, b, false, paramRandom, n);
/* 465 */       a(paramWorld, paramStructureBoundingBox, 9, 4, b, 9, 5, b, false, paramRandom, n);
/*     */     } 
/* 467 */     a(paramWorld, paramStructureBoundingBox, 5, 6, 0, 6, 6, 0, false, paramRandom, n);
/* 468 */     for (b = 0; b <= 11; b += 11) {
/* 469 */       for (byte b1 = 2; b1 <= 12; b1 += 2) {
/* 470 */         a(paramWorld, paramStructureBoundingBox, b, 4, b1, b, 5, b1, false, paramRandom, n);
/*     */       }
/* 472 */       a(paramWorld, paramStructureBoundingBox, b, 6, 5, b, 6, 5, false, paramRandom, n);
/* 473 */       a(paramWorld, paramStructureBoundingBox, b, 6, 9, b, 6, 9, false, paramRandom, n);
/*     */     } 
/* 475 */     a(paramWorld, paramStructureBoundingBox, 2, 7, 2, 2, 9, 2, false, paramRandom, n);
/* 476 */     a(paramWorld, paramStructureBoundingBox, 9, 7, 2, 9, 9, 2, false, paramRandom, n);
/* 477 */     a(paramWorld, paramStructureBoundingBox, 2, 7, 12, 2, 9, 12, false, paramRandom, n);
/* 478 */     a(paramWorld, paramStructureBoundingBox, 9, 7, 12, 9, 9, 12, false, paramRandom, n);
/* 479 */     a(paramWorld, paramStructureBoundingBox, 4, 9, 4, 4, 9, 4, false, paramRandom, n);
/* 480 */     a(paramWorld, paramStructureBoundingBox, 7, 9, 4, 7, 9, 4, false, paramRandom, n);
/* 481 */     a(paramWorld, paramStructureBoundingBox, 4, 9, 10, 4, 9, 10, false, paramRandom, n);
/* 482 */     a(paramWorld, paramStructureBoundingBox, 7, 9, 10, 7, 9, 10, false, paramRandom, n);
/* 483 */     a(paramWorld, paramStructureBoundingBox, 5, 9, 7, 6, 9, 7, false, paramRandom, n);
/* 484 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 5, 9, 6, paramStructureBoundingBox);
/* 485 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 6, 9, 6, paramStructureBoundingBox);
/* 486 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, j, 5, 9, 8, paramStructureBoundingBox);
/* 487 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, j, 6, 9, 8, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 490 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 4, 0, 0, paramStructureBoundingBox);
/* 491 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 5, 0, 0, paramStructureBoundingBox);
/* 492 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 6, 0, 0, paramStructureBoundingBox);
/* 493 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 7, 0, 0, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 496 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 4, 1, 8, paramStructureBoundingBox);
/* 497 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 4, 2, 9, paramStructureBoundingBox);
/* 498 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 4, 3, 10, paramStructureBoundingBox);
/* 499 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 7, 1, 8, paramStructureBoundingBox);
/* 500 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 7, 2, 9, paramStructureBoundingBox);
/* 501 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, i, 7, 3, 10, paramStructureBoundingBox);
/* 502 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 9, 4, 1, 9, false, paramRandom, n);
/* 503 */     a(paramWorld, paramStructureBoundingBox, 7, 1, 9, 7, 1, 9, false, paramRandom, n);
/* 504 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 10, 7, 2, 10, false, paramRandom, n);
/*     */ 
/*     */     
/* 507 */     a(paramWorld, paramStructureBoundingBox, 5, 4, 5, 6, 4, 5, false, paramRandom, n);
/* 508 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, k, 4, 4, 5, paramStructureBoundingBox);
/* 509 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, m, 7, 4, 5, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 512 */     for (b = 0; b < 4; b++) {
/* 513 */       a(paramWorld, Blocks.COBBLESTONE_STAIRS, j, 5, 0 - b, 6 + b, paramStructureBoundingBox);
/* 514 */       a(paramWorld, Blocks.COBBLESTONE_STAIRS, j, 6, 0 - b, 6 + b, paramStructureBoundingBox);
/* 515 */       a(paramWorld, paramStructureBoundingBox, 5, 0 - b, 7 + b, 6, 0 - b, 9 + b);
/*     */     } 
/*     */ 
/*     */     
/* 519 */     a(paramWorld, paramStructureBoundingBox, 1, -3, 12, 10, -1, 13);
/* 520 */     a(paramWorld, paramStructureBoundingBox, 1, -3, 1, 3, -1, 13);
/* 521 */     a(paramWorld, paramStructureBoundingBox, 1, -3, 1, 9, -1, 5);
/* 522 */     for (b = 1; b <= 13; b += 2) {
/* 523 */       a(paramWorld, paramStructureBoundingBox, 1, -3, b, 1, -2, b, false, paramRandom, n);
/*     */     }
/* 525 */     for (b = 2; b <= 12; b += 2) {
/* 526 */       a(paramWorld, paramStructureBoundingBox, 1, -1, b, 3, -1, b, false, paramRandom, n);
/*     */     }
/* 528 */     a(paramWorld, paramStructureBoundingBox, 2, -2, 1, 5, -2, 1, false, paramRandom, n);
/* 529 */     a(paramWorld, paramStructureBoundingBox, 7, -2, 1, 9, -2, 1, false, paramRandom, n);
/* 530 */     a(paramWorld, paramStructureBoundingBox, 6, -3, 1, 6, -3, 1, false, paramRandom, n);
/* 531 */     a(paramWorld, paramStructureBoundingBox, 6, -1, 1, 6, -1, 1, false, paramRandom, n);
/*     */ 
/*     */     
/* 534 */     a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 3) | 0x4, 1, -3, 8, paramStructureBoundingBox);
/* 535 */     a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 1) | 0x4, 4, -3, 8, paramStructureBoundingBox);
/* 536 */     a(paramWorld, Blocks.TRIPWIRE, 4, 2, -3, 8, paramStructureBoundingBox);
/* 537 */     a(paramWorld, Blocks.TRIPWIRE, 4, 3, -3, 8, paramStructureBoundingBox);
/* 538 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 7, paramStructureBoundingBox);
/* 539 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 6, paramStructureBoundingBox);
/* 540 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 5, paramStructureBoundingBox);
/* 541 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 4, paramStructureBoundingBox);
/* 542 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 3, paramStructureBoundingBox);
/* 543 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 2, paramStructureBoundingBox);
/* 544 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 5, -3, 1, paramStructureBoundingBox);
/* 545 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 4, -3, 1, paramStructureBoundingBox);
/* 546 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 3, -3, 1, paramStructureBoundingBox);
/* 547 */     if (!this.j) {
/* 548 */       this.j = a(paramWorld, paramStructureBoundingBox, paramRandom, 3, -2, 1, 2, m, 2);
/*     */     }
/* 550 */     a(paramWorld, Blocks.VINE, 15, 3, -2, 2, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 553 */     a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 2) | 0x4, 7, -3, 1, paramStructureBoundingBox);
/* 554 */     a(paramWorld, Blocks.TRIPWIRE_SOURCE, a(Blocks.TRIPWIRE_SOURCE, 0) | 0x4, 7, -3, 5, paramStructureBoundingBox);
/* 555 */     a(paramWorld, Blocks.TRIPWIRE, 4, 7, -3, 2, paramStructureBoundingBox);
/* 556 */     a(paramWorld, Blocks.TRIPWIRE, 4, 7, -3, 3, paramStructureBoundingBox);
/* 557 */     a(paramWorld, Blocks.TRIPWIRE, 4, 7, -3, 4, paramStructureBoundingBox);
/* 558 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 8, -3, 6, paramStructureBoundingBox);
/* 559 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 9, -3, 6, paramStructureBoundingBox);
/* 560 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 9, -3, 5, paramStructureBoundingBox);
/* 561 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 9, -3, 4, paramStructureBoundingBox);
/* 562 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 9, -2, 4, paramStructureBoundingBox);
/* 563 */     if (!this.k) {
/* 564 */       this.k = a(paramWorld, paramStructureBoundingBox, paramRandom, 9, -2, 3, 4, m, 2);
/*     */     }
/* 566 */     a(paramWorld, Blocks.VINE, 15, 8, -1, 3, paramStructureBoundingBox);
/* 567 */     a(paramWorld, Blocks.VINE, 15, 8, -2, 3, paramStructureBoundingBox);
/* 568 */     if (!this.e) {
/* 569 */       this.e = a(paramWorld, paramStructureBoundingBox, paramRandom, 8, -3, 3, StructurePieceTreasure.a(l, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(5));
/*     */     }
/* 571 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 9, -3, 2, paramStructureBoundingBox);
/* 572 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 8, -3, 1, paramStructureBoundingBox);
/* 573 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 4, -3, 5, paramStructureBoundingBox);
/* 574 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 5, -2, 5, paramStructureBoundingBox);
/* 575 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 5, -1, 5, paramStructureBoundingBox);
/* 576 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 6, -3, 5, paramStructureBoundingBox);
/* 577 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 7, -2, 5, paramStructureBoundingBox);
/* 578 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 7, -1, 5, paramStructureBoundingBox);
/* 579 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 8, -3, 5, paramStructureBoundingBox);
/* 580 */     a(paramWorld, paramStructureBoundingBox, 9, -1, 1, 9, -1, 5, false, paramRandom, n);
/*     */ 
/*     */     
/* 583 */     a(paramWorld, paramStructureBoundingBox, 8, -3, 8, 10, -1, 10);
/* 584 */     a(paramWorld, Blocks.SMOOTH_BRICK, 3, 8, -2, 11, paramStructureBoundingBox);
/* 585 */     a(paramWorld, Blocks.SMOOTH_BRICK, 3, 9, -2, 11, paramStructureBoundingBox);
/* 586 */     a(paramWorld, Blocks.SMOOTH_BRICK, 3, 10, -2, 11, paramStructureBoundingBox);
/* 587 */     a(paramWorld, Blocks.LEVER, BlockLever.b(a(Blocks.LEVER, 2)), 8, -2, 12, paramStructureBoundingBox);
/* 588 */     a(paramWorld, Blocks.LEVER, BlockLever.b(a(Blocks.LEVER, 2)), 9, -2, 12, paramStructureBoundingBox);
/* 589 */     a(paramWorld, Blocks.LEVER, BlockLever.b(a(Blocks.LEVER, 2)), 10, -2, 12, paramStructureBoundingBox);
/* 590 */     a(paramWorld, paramStructureBoundingBox, 8, -3, 8, 8, -3, 10, false, paramRandom, n);
/* 591 */     a(paramWorld, paramStructureBoundingBox, 10, -3, 8, 10, -3, 10, false, paramRandom, n);
/* 592 */     a(paramWorld, Blocks.MOSSY_COBBLESTONE, 0, 10, -2, 9, paramStructureBoundingBox);
/* 593 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 8, -2, 9, paramStructureBoundingBox);
/* 594 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 8, -2, 10, paramStructureBoundingBox);
/* 595 */     a(paramWorld, Blocks.REDSTONE_WIRE, 0, 10, -1, 9, paramStructureBoundingBox);
/* 596 */     a(paramWorld, Blocks.PISTON_STICKY, 1, 9, -2, 8, paramStructureBoundingBox);
/* 597 */     a(paramWorld, Blocks.PISTON_STICKY, a(Blocks.PISTON_STICKY, 4), 10, -2, 8, paramStructureBoundingBox);
/* 598 */     a(paramWorld, Blocks.PISTON_STICKY, a(Blocks.PISTON_STICKY, 4), 10, -1, 8, paramStructureBoundingBox);
/* 599 */     a(paramWorld, Blocks.DIODE_OFF, a(Blocks.DIODE_OFF, 2), 10, -2, 10, paramStructureBoundingBox);
/* 600 */     if (!this.i) {
/* 601 */       this.i = a(paramWorld, paramStructureBoundingBox, paramRandom, 9, -3, 10, StructurePieceTreasure.a(l, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 2 + paramRandom.nextInt(5));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 606 */     return true;
/*     */   }
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
/* 620 */   private static WorldGenJungleTemplePiece n = new WorldGenJungleTemplePiece(null);
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenJungleTemple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */