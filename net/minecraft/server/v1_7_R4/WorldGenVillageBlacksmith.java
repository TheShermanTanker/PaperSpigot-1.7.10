/*      */ package net.minecraft.server.v1_7_R4;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WorldGenVillageBlacksmith
/*      */   extends WorldGenVillagePiece
/*      */ {
/* 1497 */   private static final StructurePieceTreasure[] a = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_HELMET, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_LEGGINGS, 0, 1, 1, 5), new StructurePieceTreasure(Items.IRON_BOOTS, 0, 1, 1, 5), new StructurePieceTreasure(Item.getItemOf(Blocks.OBSIDIAN), 0, 3, 7, 5), new StructurePieceTreasure(Item.getItemOf(Blocks.SAPLING), 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 1), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGenVillageBlacksmith() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGenVillageBlacksmith(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 1532 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*      */     
/* 1534 */     this.g = paramInt2;
/* 1535 */     this.f = paramStructureBoundingBox;
/*      */   }
/*      */ 
/*      */   
/*      */   public static WorldGenVillageBlacksmith a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1540 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 10, 6, 7, paramInt4);
/*      */     
/* 1542 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 1543 */       return null;
/*      */     }
/*      */     
/* 1546 */     return new WorldGenVillageBlacksmith(paramWorldGenVillageStartPiece, paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 1551 */     super.a(paramNBTTagCompound);
/* 1552 */     paramNBTTagCompound.setBoolean("Chest", this.b);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 1557 */     super.b(paramNBTTagCompound);
/* 1558 */     this.b = paramNBTTagCompound.getBoolean("Chest");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 1564 */     if (this.k < 0) {
/* 1565 */       this.k = b(paramWorld, paramStructureBoundingBox);
/* 1566 */       if (this.k < 0) {
/* 1567 */         return true;
/*      */       }
/* 1569 */       this.f.a(0, this.k - this.f.e + 6 - 1, 0);
/*      */     } 
/*      */ 
/*      */     
/* 1573 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 9, 4, 6, Blocks.AIR, Blocks.AIR, false);
/*      */ 
/*      */     
/* 1576 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 9, 0, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*      */ 
/*      */     
/* 1579 */     a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 9, 4, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/* 1580 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 9, 5, 6, Blocks.STEP, Blocks.STEP, false);
/* 1581 */     a(paramWorld, paramStructureBoundingBox, 1, 5, 1, 8, 5, 5, Blocks.AIR, Blocks.AIR, false);
/*      */ 
/*      */     
/* 1584 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.WOOD, Blocks.WOOD, false);
/* 1585 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 4, 0, Blocks.LOG, Blocks.LOG, false);
/* 1586 */     a(paramWorld, paramStructureBoundingBox, 3, 1, 0, 3, 4, 0, Blocks.LOG, Blocks.LOG, false);
/* 1587 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 6, 0, 4, 6, Blocks.LOG, Blocks.LOG, false);
/* 1588 */     a(paramWorld, Blocks.WOOD, 0, 3, 3, 1, paramStructureBoundingBox);
/* 1589 */     a(paramWorld, paramStructureBoundingBox, 3, 1, 2, 3, 3, 2, Blocks.WOOD, Blocks.WOOD, false);
/* 1590 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 3, 5, 3, 3, Blocks.WOOD, Blocks.WOOD, false);
/* 1591 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 5, Blocks.WOOD, Blocks.WOOD, false);
/* 1592 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 6, 5, 3, 6, Blocks.WOOD, Blocks.WOOD, false);
/*      */ 
/*      */     
/* 1595 */     a(paramWorld, paramStructureBoundingBox, 5, 1, 0, 5, 3, 0, Blocks.FENCE, Blocks.FENCE, false);
/* 1596 */     a(paramWorld, paramStructureBoundingBox, 9, 1, 0, 9, 3, 0, Blocks.FENCE, Blocks.FENCE, false);
/*      */ 
/*      */     
/* 1599 */     a(paramWorld, paramStructureBoundingBox, 6, 1, 4, 9, 4, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/* 1600 */     a(paramWorld, Blocks.LAVA, 0, 7, 1, 5, paramStructureBoundingBox);
/* 1601 */     a(paramWorld, Blocks.LAVA, 0, 8, 1, 5, paramStructureBoundingBox);
/* 1602 */     a(paramWorld, Blocks.IRON_FENCE, 0, 9, 2, 5, paramStructureBoundingBox);
/* 1603 */     a(paramWorld, Blocks.IRON_FENCE, 0, 9, 2, 4, paramStructureBoundingBox);
/* 1604 */     a(paramWorld, paramStructureBoundingBox, 7, 2, 4, 8, 2, 5, Blocks.AIR, Blocks.AIR, false);
/* 1605 */     a(paramWorld, Blocks.COBBLESTONE, 0, 6, 1, 3, paramStructureBoundingBox);
/* 1606 */     a(paramWorld, Blocks.FURNACE, 0, 6, 2, 3, paramStructureBoundingBox);
/* 1607 */     a(paramWorld, Blocks.FURNACE, 0, 6, 3, 3, paramStructureBoundingBox);
/* 1608 */     a(paramWorld, Blocks.DOUBLE_STEP, 0, 8, 1, 1, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1611 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
/* 1612 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 4, paramStructureBoundingBox);
/* 1613 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 2, 6, paramStructureBoundingBox);
/* 1614 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 2, 6, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1617 */     a(paramWorld, Blocks.FENCE, 0, 2, 1, 4, paramStructureBoundingBox);
/* 1618 */     a(paramWorld, Blocks.WOOD_PLATE, 0, 2, 2, 4, paramStructureBoundingBox);
/* 1619 */     a(paramWorld, Blocks.WOOD, 0, 1, 1, 5, paramStructureBoundingBox);
/* 1620 */     a(paramWorld, Blocks.WOOD_STAIRS, a(Blocks.WOOD_STAIRS, 3), 2, 1, 5, paramStructureBoundingBox);
/* 1621 */     a(paramWorld, Blocks.WOOD_STAIRS, a(Blocks.WOOD_STAIRS, 1), 1, 1, 4, paramStructureBoundingBox);
/*      */     
/* 1623 */     if (!this.b) {
/* 1624 */       int i = a(1);
/* 1625 */       int j = a(5, 5), k = b(5, 5);
/* 1626 */       if (paramStructureBoundingBox.b(j, i, k)) {
/* 1627 */         this.b = true;
/* 1628 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 5, 1, 5, a, 3 + paramRandom.nextInt(6));
/*      */       } 
/*      */     } 
/*      */     
/*      */     byte b;
/* 1633 */     for (b = 6; b <= 8; b++) {
/* 1634 */       if (a(paramWorld, b, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR && a(paramWorld, b, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/* 1635 */         a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), b, 0, -1, paramStructureBoundingBox);
/*      */       }
/*      */     } 
/*      */     
/* 1639 */     for (b = 0; b < 7; b++) {
/* 1640 */       for (byte b1 = 0; b1 < 10; b1++) {
/* 1641 */         b(paramWorld, b1, 6, b, paramStructureBoundingBox);
/* 1642 */         b(paramWorld, Blocks.COBBLESTONE, 0, b1, -1, b, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/*      */     
/* 1646 */     a(paramWorld, paramStructureBoundingBox, 7, 1, 1, 1);
/*      */     
/* 1648 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int b(int paramInt) {
/* 1653 */     return 3;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageBlacksmith.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */