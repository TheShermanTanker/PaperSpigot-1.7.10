/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StructurePieceTreasure
/*    */   extends WeightedRandomChoice
/*    */ {
/*    */   private ItemStack b;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public StructurePieceTreasure(Item paramItem, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 15 */     super(paramInt4);
/* 16 */     this.b = new ItemStack(paramItem, 1, paramInt1);
/* 17 */     this.c = paramInt2;
/* 18 */     this.d = paramInt3;
/*    */   }
/*    */   
/*    */   public StructurePieceTreasure(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3) {
/* 22 */     super(paramInt3);
/* 23 */     this.b = paramItemStack;
/* 24 */     this.c = paramInt1;
/* 25 */     this.d = paramInt2;
/*    */   }
/*    */   
/*    */   public static void a(Random paramRandom, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, IInventory paramIInventory, int paramInt) {
/* 29 */     for (byte b = 0; b < paramInt; b++) {
/* 30 */       StructurePieceTreasure structurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, (WeightedRandomChoice[])paramArrayOfStructurePieceTreasure);
/* 31 */       int i = structurePieceTreasure.c + paramRandom.nextInt(structurePieceTreasure.d - structurePieceTreasure.c + 1);
/*    */       
/* 33 */       if (structurePieceTreasure.b.getMaxStackSize() >= i) {
/* 34 */         ItemStack itemStack = structurePieceTreasure.b.cloneItemStack();
/* 35 */         itemStack.count = i;
/* 36 */         paramIInventory.setItem(paramRandom.nextInt(paramIInventory.getSize()), itemStack);
/*    */       } else {
/*    */         
/* 39 */         for (byte b1 = 0; b1 < i; b1++) {
/* 40 */           ItemStack itemStack = structurePieceTreasure.b.cloneItemStack();
/* 41 */           itemStack.count = 1;
/* 42 */           paramIInventory.setItem(paramRandom.nextInt(paramIInventory.getSize()), itemStack);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void a(Random paramRandom, StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, TileEntityDispenser paramTileEntityDispenser, int paramInt) {
/* 49 */     for (byte b = 0; b < paramInt; b++) {
/* 50 */       StructurePieceTreasure structurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, (WeightedRandomChoice[])paramArrayOfStructurePieceTreasure);
/* 51 */       int i = structurePieceTreasure.c + paramRandom.nextInt(structurePieceTreasure.d - structurePieceTreasure.c + 1);
/*    */       
/* 53 */       if (structurePieceTreasure.b.getMaxStackSize() >= i) {
/* 54 */         ItemStack itemStack = structurePieceTreasure.b.cloneItemStack();
/* 55 */         itemStack.count = i;
/* 56 */         paramTileEntityDispenser.setItem(paramRandom.nextInt(paramTileEntityDispenser.getSize()), itemStack);
/*    */       } else {
/*    */         
/* 59 */         for (byte b1 = 0; b1 < i; b1++) {
/* 60 */           ItemStack itemStack = structurePieceTreasure.b.cloneItemStack();
/* 61 */           itemStack.count = 1;
/* 62 */           paramTileEntityDispenser.setItem(paramRandom.nextInt(paramTileEntityDispenser.getSize()), itemStack);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static StructurePieceTreasure[] a(StructurePieceTreasure[] paramArrayOfStructurePieceTreasure1, StructurePieceTreasure... paramVarArgs1) {
/* 69 */     StructurePieceTreasure[] arrayOfStructurePieceTreasure = new StructurePieceTreasure[paramArrayOfStructurePieceTreasure1.length + paramVarArgs1.length];
/* 70 */     byte b1 = 0;
/*    */     
/* 72 */     for (byte b2 = 0; b2 < paramArrayOfStructurePieceTreasure1.length; b2++) {
/* 73 */       arrayOfStructurePieceTreasure[b1++] = paramArrayOfStructurePieceTreasure1[b2];
/*    */     }
/*    */     
/* 76 */     for (StructurePieceTreasure structurePieceTreasure : paramVarArgs1) {
/* 77 */       arrayOfStructurePieceTreasure[b1++] = structurePieceTreasure;
/*    */     }
/*    */     
/* 80 */     return arrayOfStructurePieceTreasure;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StructurePieceTreasure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */