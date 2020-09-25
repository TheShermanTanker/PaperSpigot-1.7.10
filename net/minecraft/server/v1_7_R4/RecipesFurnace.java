/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class RecipesFurnace
/*     */ {
/*  10 */   private static final RecipesFurnace a = new RecipesFurnace();
/*  11 */   public Map recipes = new HashMap<Object, Object>();
/*  12 */   private Map c = new HashMap<Object, Object>();
/*  13 */   public Map customRecipes = new HashMap<Object, Object>();
/*     */   
/*     */   public static RecipesFurnace getInstance() {
/*  16 */     return a;
/*     */   }
/*     */   
/*     */   public RecipesFurnace() {
/*  20 */     registerRecipe(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
/*  21 */     registerRecipe(Blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1.0F);
/*  22 */     registerRecipe(Blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1.0F);
/*  23 */     registerRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1F);
/*  24 */     a(Items.PORK, new ItemStack(Items.GRILLED_PORK), 0.35F);
/*  25 */     a(Items.RAW_BEEF, new ItemStack(Items.COOKED_BEEF), 0.35F);
/*  26 */     a(Items.RAW_CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.35F);
/*  27 */     registerRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.STONE), 0.1F);
/*  28 */     a(Items.CLAY_BALL, new ItemStack(Items.CLAY_BRICK), 0.3F);
/*  29 */     registerRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35F);
/*  30 */     registerRecipe(Blocks.CACTUS, new ItemStack(Items.INK_SACK, 1, 2), 0.2F);
/*  31 */     registerRecipe(Blocks.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
/*  32 */     registerRecipe(Blocks.LOG2, new ItemStack(Items.COAL, 1, 1), 0.15F);
/*  33 */     registerRecipe(Blocks.EMERALD_ORE, new ItemStack(Items.EMERALD), 1.0F);
/*  34 */     a(Items.POTATO, new ItemStack(Items.POTATO_BAKED), 0.35F);
/*  35 */     registerRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHER_BRICK), 0.1F);
/*  36 */     registerRecipe(Blocks.SMOOTH_BRICK, new ItemStack(Blocks.SMOOTH_BRICK, 1, 2), 0.5F);
/*  37 */     EnumFish[] aenumfish = EnumFish.values();
/*  38 */     int i = aenumfish.length;
/*     */     
/*  40 */     for (int j = 0; j < i; j++) {
/*  41 */       EnumFish enumfish = aenumfish[j];
/*     */       
/*  43 */       if (enumfish.i()) {
/*  44 */         a(new ItemStack(Items.RAW_FISH, 1, enumfish.a()), new ItemStack(Items.COOKED_FISH, 1, enumfish.a()), 0.35F);
/*     */       }
/*     */     } 
/*     */     
/*  48 */     registerRecipe(Blocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
/*  49 */     registerRecipe(Blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
/*  50 */     registerRecipe(Blocks.LAPIS_ORE, new ItemStack(Items.INK_SACK, 1, 4), 0.2F);
/*  51 */     registerRecipe(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2F);
/*     */   }
/*     */   
/*     */   public void registerRecipe(Block block, ItemStack itemstack, float f) {
/*  55 */     a(Item.getItemOf(block), itemstack, f);
/*     */   }
/*     */   
/*     */   public void a(Item item, ItemStack itemstack, float f) {
/*  59 */     a(new ItemStack(item, 1, 32767), itemstack, f);
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, ItemStack itemstack1, float f) {
/*  63 */     this.recipes.put(itemstack, itemstack1);
/*  64 */     this.c.put(itemstack1, Float.valueOf(f));
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRecipe(ItemStack itemstack, ItemStack itemstack1) {
/*  69 */     this.customRecipes.put(itemstack, itemstack1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getResult(ItemStack itemstack) {
/*     */     Map.Entry entry;
/*  75 */     boolean vanilla = false;
/*  76 */     Iterator<Map.Entry> iterator = this.customRecipes.entrySet().iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/*  82 */       if (!iterator.hasNext())
/*     */       {
/*  84 */         if (!vanilla && this.recipes.size() != 0) {
/*  85 */           iterator = this.recipes.entrySet().iterator();
/*  86 */           vanilla = true;
/*     */         } else {
/*  88 */           return null;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  93 */       entry = iterator.next();
/*  94 */     } while (!a(itemstack, (ItemStack)entry.getKey()));
/*     */     
/*  96 */     return (ItemStack)entry.getValue();
/*     */   }
/*     */   
/*     */   private boolean a(ItemStack itemstack, ItemStack itemstack1) {
/* 100 */     return (itemstack1.getItem() == itemstack.getItem() && (itemstack1.getData() == 32767 || itemstack1.getData() == itemstack.getData()));
/*     */   }
/*     */   
/*     */   public Map getRecipes() {
/* 104 */     return this.recipes;
/*     */   }
/*     */   public float b(ItemStack itemstack) {
/*     */     Map.Entry entry;
/* 108 */     Iterator<Map.Entry> iterator = this.c.entrySet().iterator();
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/* 113 */       if (!iterator.hasNext()) {
/* 114 */         return 0.0F;
/*     */       }
/*     */       
/* 117 */       entry = iterator.next();
/* 118 */     } while (!a(itemstack, (ItemStack)entry.getKey()));
/*     */     
/* 120 */     return ((Float)entry.getValue()).floatValue();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */