/*     */ package org.spigotmc;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.server.v1_7_R4.Block;
/*     */ import net.minecraft.server.v1_7_R4.Blocks;
/*     */ import net.minecraft.server.v1_7_R4.Item;
/*     */ import net.minecraft.server.v1_7_R4.Items;
/*     */ import net.minecraft.util.gnu.trove.map.hash.TIntIntHashMap;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.JsonParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpigotDebreakifier
/*     */ {
/*  21 */   private static final boolean[] validBlocks = new boolean[3168];
/*  22 */   private static final int[] correctedValues = new int[198];
/*     */ 
/*     */   
/*     */   static {
/*  26 */     Arrays.fill(correctedValues, -1);
/*  27 */     InputStream in = SpigotDebreakifier.class.getResourceAsStream("/blocks.json");
/*     */     
/*     */     try {
/*  30 */       JsonArray e = (new JsonParser()).parse(new InputStreamReader(in, Charsets.UTF_8)).getAsJsonArray();
/*  31 */       for (JsonElement entry : e) {
/*     */         
/*  33 */         String[] parts = entry.getAsString().split(":");
/*  34 */         int id = Integer.parseInt(parts[0]);
/*  35 */         int data = Integer.parseInt(parts[1]);
/*  36 */         validBlocks[id << 4 | data] = true;
/*  37 */         if (correctedValues[id] == -1 || data < correctedValues[id])
/*     */         {
/*  39 */           correctedValues[id] = data;
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*  46 */         in.close();
/*  47 */       } catch (IOException e) {
/*     */         
/*  49 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCorrectedData(int id, int data) {
/*  56 */     if (id > 197) return data; 
/*  57 */     if (id == 175 && data > 8)
/*     */     {
/*  59 */       data = 8;
/*     */     }
/*  61 */     if (validBlocks[id << 4 | data])
/*     */     {
/*  63 */       return data;
/*     */     }
/*     */     
/*  66 */     return correctedValues[id] & 0xF;
/*     */   }
/*     */ 
/*     */   
/*  70 */   private static TIntIntHashMap invalidItems = new TIntIntHashMap();
/*     */   static {
/*  72 */     replace((Block)Blocks.WATER, Items.WATER_BUCKET);
/*  73 */     replace(Blocks.STATIONARY_WATER, Items.WATER_BUCKET);
/*  74 */     replace((Block)Blocks.LAVA, Items.LAVA_BUCKET);
/*  75 */     replace(Blocks.STATIONARY_LAVA, Items.LAVA_BUCKET);
/*  76 */     replace((Block)Blocks.PORTAL, Items.NETHER_BRICK);
/*  77 */     replace((Block)Blocks.DOUBLE_STEP, (Block)Blocks.STEP);
/*  78 */     replace((Block)Blocks.FIRE, Items.FLINT_AND_STEEL);
/*  79 */     replace(Blocks.ENDER_PORTAL, Blocks.ENDER_PORTAL_FRAME);
/*  80 */     replace((Block)Blocks.WOOD_DOUBLE_STEP, (Block)Blocks.WOOD_STEP);
/*  81 */     replace(Blocks.COCOA, Items.SEEDS);
/*  82 */     replace(Blocks.CARROTS, Items.CARROT);
/*  83 */     replace(Blocks.POTATOES, Items.POTATO);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getItemId(int id) {
/*  88 */     return invalidItems.containsKey(id) ? invalidItems.get(id) : id;
/*     */   }
/*     */   
/*     */   private static void replace(Block block, Block other) {
/*  92 */     replace(Block.getId(block), Block.getId(other));
/*     */   }
/*     */   
/*     */   private static void replace(Block block, Item other) {
/*  96 */     replace(Block.getId(block), Item.getId(other));
/*     */   }
/*     */   
/*     */   private static void replace(int block, int other) {
/* 100 */     invalidItems.put(block, other);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotDebreakifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */