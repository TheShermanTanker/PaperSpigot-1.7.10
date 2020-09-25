/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class CraftingManager
/*     */ {
/*  12 */   private static final CraftingManager a = new CraftingManager();
/*     */   
/*  14 */   public List recipes = new ArrayList();
/*     */   
/*     */   public IRecipe lastRecipe;
/*     */   public InventoryView lastCraftView;
/*     */   
/*     */   public static final CraftingManager getInstance() {
/*  20 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftingManager() {
/*  25 */     (new RecipesTools()).a(this);
/*  26 */     (new RecipesWeapons()).a(this);
/*  27 */     (new RecipeIngots()).a(this);
/*  28 */     (new RecipesFood()).a(this);
/*  29 */     (new RecipesCrafting()).a(this);
/*  30 */     (new RecipesArmor()).a(this);
/*  31 */     (new RecipesDyes()).a(this);
/*  32 */     this.recipes.add(new RecipeArmorDye());
/*  33 */     this.recipes.add(new RecipeBookClone());
/*  34 */     this.recipes.add(new RecipeMapClone());
/*  35 */     this.recipes.add(new RecipeMapExtend());
/*  36 */     this.recipes.add(new RecipeFireworks());
/*  37 */     registerShapedRecipe(new ItemStack(Items.PAPER, 3), new Object[] { "###", Character.valueOf('#'), Items.SUGAR_CANE });
/*  38 */     registerShapelessRecipe(new ItemStack(Items.BOOK, 1), new Object[] { Items.PAPER, Items.PAPER, Items.PAPER, Items.LEATHER });
/*  39 */     registerShapelessRecipe(new ItemStack(Items.BOOK_AND_QUILL, 1), new Object[] { Items.BOOK, new ItemStack(Items.INK_SACK, 1, 0), Items.FEATHER });
/*  40 */     registerShapedRecipe(new ItemStack(Blocks.FENCE, 2), new Object[] { "###", "###", Character.valueOf('#'), Items.STICK });
/*  41 */     registerShapedRecipe(new ItemStack(Blocks.COBBLE_WALL, 6, 0), new Object[] { "###", "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/*  42 */     registerShapedRecipe(new ItemStack(Blocks.COBBLE_WALL, 6, 1), new Object[] { "###", "###", Character.valueOf('#'), Blocks.MOSSY_COBBLESTONE });
/*  43 */     registerShapedRecipe(new ItemStack(Blocks.NETHER_FENCE, 6), new Object[] { "###", "###", Character.valueOf('#'), Blocks.NETHER_BRICK });
/*  44 */     registerShapedRecipe(new ItemStack(Blocks.FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), Blocks.WOOD });
/*  45 */     registerShapedRecipe(new ItemStack(Blocks.JUKEBOX, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.WOOD, Character.valueOf('X'), Items.DIAMOND });
/*  46 */     registerShapedRecipe(new ItemStack(Items.LEASH, 2), new Object[] { "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.STRING, Character.valueOf('O'), Items.SLIME_BALL });
/*  47 */     registerShapedRecipe(new ItemStack(Blocks.NOTE_BLOCK, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.WOOD, Character.valueOf('X'), Items.REDSTONE });
/*  48 */     registerShapedRecipe(new ItemStack(Blocks.BOOKSHELF, 1), new Object[] { "###", "XXX", "###", Character.valueOf('#'), Blocks.WOOD, Character.valueOf('X'), Items.BOOK });
/*  49 */     registerShapedRecipe(new ItemStack(Blocks.SNOW_BLOCK, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.SNOW_BALL });
/*  50 */     registerShapedRecipe(new ItemStack(Blocks.SNOW, 6), new Object[] { "###", Character.valueOf('#'), Blocks.SNOW_BLOCK });
/*  51 */     registerShapedRecipe(new ItemStack(Blocks.CLAY, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.CLAY_BALL });
/*  52 */     registerShapedRecipe(new ItemStack(Blocks.BRICK, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.CLAY_BRICK });
/*  53 */     registerShapedRecipe(new ItemStack(Blocks.GLOWSTONE, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.GLOWSTONE_DUST });
/*  54 */     registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.QUARTZ });
/*  55 */     registerShapedRecipe(new ItemStack(Blocks.WOOL, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.STRING });
/*  56 */     registerShapedRecipe(new ItemStack(Blocks.TNT, 1), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), Items.SULPHUR, Character.valueOf('#'), Blocks.SAND });
/*  57 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 3), new Object[] { "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/*  58 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 0), new Object[] { "###", Character.valueOf('#'), Blocks.STONE });
/*  59 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 1), new Object[] { "###", Character.valueOf('#'), Blocks.SANDSTONE });
/*  60 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 4), new Object[] { "###", Character.valueOf('#'), Blocks.BRICK });
/*  61 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 5), new Object[] { "###", Character.valueOf('#'), Blocks.SMOOTH_BRICK });
/*  62 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 6), new Object[] { "###", Character.valueOf('#'), Blocks.NETHER_BRICK });
/*  63 */     registerShapedRecipe(new ItemStack(Blocks.STEP, 6, 7), new Object[] { "###", Character.valueOf('#'), Blocks.QUARTZ_BLOCK });
/*  64 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STEP, 6, 0), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 0) });
/*  65 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STEP, 6, 2), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 2) });
/*  66 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STEP, 6, 1), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 1) });
/*  67 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STEP, 6, 3), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 3) });
/*  68 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STEP, 6, 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 4) });
/*  69 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STEP, 6, 5), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 5) });
/*  70 */     registerShapedRecipe(new ItemStack(Blocks.LADDER, 3), new Object[] { "# #", "###", "# #", Character.valueOf('#'), Items.STICK });
/*  71 */     registerShapedRecipe(new ItemStack(Items.WOOD_DOOR, 1), new Object[] { "##", "##", "##", Character.valueOf('#'), Blocks.WOOD });
/*  72 */     registerShapedRecipe(new ItemStack(Blocks.TRAP_DOOR, 2), new Object[] { "###", "###", Character.valueOf('#'), Blocks.WOOD });
/*  73 */     registerShapedRecipe(new ItemStack(Items.IRON_DOOR, 1), new Object[] { "##", "##", "##", Character.valueOf('#'), Items.IRON_INGOT });
/*  74 */     registerShapedRecipe(new ItemStack(Items.SIGN, 3), new Object[] { "###", "###", " X ", Character.valueOf('#'), Blocks.WOOD, Character.valueOf('X'), Items.STICK });
/*  75 */     registerShapedRecipe(new ItemStack(Items.CAKE, 1), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), Items.MILK_BUCKET, Character.valueOf('B'), Items.SUGAR, Character.valueOf('C'), Items.WHEAT, Character.valueOf('E'), Items.EGG });
/*  76 */     registerShapedRecipe(new ItemStack(Items.SUGAR, 1), new Object[] { "#", Character.valueOf('#'), Items.SUGAR_CANE });
/*  77 */     registerShapedRecipe(new ItemStack(Blocks.WOOD, 4, 0), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, 0) });
/*  78 */     registerShapedRecipe(new ItemStack(Blocks.WOOD, 4, 1), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, 1) });
/*  79 */     registerShapedRecipe(new ItemStack(Blocks.WOOD, 4, 2), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, 2) });
/*  80 */     registerShapedRecipe(new ItemStack(Blocks.WOOD, 4, 3), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, 3) });
/*  81 */     registerShapedRecipe(new ItemStack(Blocks.WOOD, 4, 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG2, 1, 0) });
/*  82 */     registerShapedRecipe(new ItemStack(Blocks.WOOD, 4, 5), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG2, 1, 1) });
/*  83 */     registerShapedRecipe(new ItemStack(Items.STICK, 4), new Object[] { "#", "#", Character.valueOf('#'), Blocks.WOOD });
/*  84 */     registerShapedRecipe(new ItemStack(Blocks.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), Items.COAL, Character.valueOf('#'), Items.STICK });
/*  85 */     registerShapedRecipe(new ItemStack(Blocks.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(Items.COAL, 1, 1), Character.valueOf('#'), Items.STICK });
/*  86 */     registerShapedRecipe(new ItemStack(Items.BOWL, 4), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.WOOD });
/*  87 */     registerShapedRecipe(new ItemStack(Items.GLASS_BOTTLE, 3), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.GLASS });
/*  88 */     registerShapedRecipe(new ItemStack(Blocks.RAILS, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('#'), Items.STICK });
/*  89 */     registerShapedRecipe(new ItemStack(Blocks.GOLDEN_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.GOLD_INGOT, Character.valueOf('R'), Items.REDSTONE, Character.valueOf('#'), Items.STICK });
/*  90 */     registerShapedRecipe(new ItemStack(Blocks.ACTIVATOR_RAIL, 6), new Object[] { "XSX", "X#X", "XSX", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('#'), Blocks.REDSTONE_TORCH_ON, Character.valueOf('S'), Items.STICK });
/*  91 */     registerShapedRecipe(new ItemStack(Blocks.DETECTOR_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('R'), Items.REDSTONE, Character.valueOf('#'), Blocks.STONE_PLATE });
/*  92 */     registerShapedRecipe(new ItemStack(Items.MINECART, 1), new Object[] { "# #", "###", Character.valueOf('#'), Items.IRON_INGOT });
/*  93 */     registerShapedRecipe(new ItemStack(Items.CAULDRON, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Items.IRON_INGOT });
/*  94 */     registerShapedRecipe(new ItemStack(Items.BREWING_STAND, 1), new Object[] { " B ", "###", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('B'), Items.BLAZE_ROD });
/*  95 */     registerShapedRecipe(new ItemStack(Blocks.JACK_O_LANTERN, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.PUMPKIN, Character.valueOf('B'), Blocks.TORCH });
/*  96 */     registerShapedRecipe(new ItemStack(Items.STORAGE_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.CHEST, Character.valueOf('B'), Items.MINECART });
/*  97 */     registerShapedRecipe(new ItemStack(Items.POWERED_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.FURNACE, Character.valueOf('B'), Items.MINECART });
/*  98 */     registerShapedRecipe(new ItemStack(Items.MINECART_TNT, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.TNT, Character.valueOf('B'), Items.MINECART });
/*  99 */     registerShapedRecipe(new ItemStack(Items.MINECART_HOPPER, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.HOPPER, Character.valueOf('B'), Items.MINECART });
/* 100 */     registerShapedRecipe(new ItemStack(Items.BOAT, 1), new Object[] { "# #", "###", Character.valueOf('#'), Blocks.WOOD });
/* 101 */     registerShapedRecipe(new ItemStack(Items.BUCKET, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.IRON_INGOT });
/* 102 */     registerShapedRecipe(new ItemStack(Items.FLOWER_POT, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.CLAY_BRICK });
/* 103 */     registerShapelessRecipe(new ItemStack(Items.FLINT_AND_STEEL, 1), new Object[] { new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.FLINT, 1) });
/* 104 */     registerShapedRecipe(new ItemStack(Items.BREAD, 1), new Object[] { "###", Character.valueOf('#'), Items.WHEAT });
/* 105 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 0) });
/* 106 */     registerShapedRecipe(new ItemStack(Blocks.BIRCH_WOOD_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 2) });
/* 107 */     registerShapedRecipe(new ItemStack(Blocks.SPRUCE_WOOD_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 1) });
/* 108 */     registerShapedRecipe(new ItemStack(Blocks.JUNGLE_WOOD_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 3) });
/* 109 */     registerShapedRecipe(new ItemStack(Blocks.ACACIA_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 4) });
/* 110 */     registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.WOOD, 1, 5) });
/* 111 */     registerShapedRecipe(new ItemStack(Items.FISHING_ROD, 1), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Items.STRING });
/* 112 */     registerShapedRecipe(new ItemStack(Items.CARROT_STICK, 1), new Object[] { "# ", " X", Character.valueOf('#'), Items.FISHING_ROD, Character.valueOf('X'), Items.CARROT }).c();
/* 113 */     registerShapedRecipe(new ItemStack(Blocks.COBBLESTONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/* 114 */     registerShapedRecipe(new ItemStack(Blocks.BRICK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.BRICK });
/* 115 */     registerShapedRecipe(new ItemStack(Blocks.STONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.SMOOTH_BRICK });
/* 116 */     registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.NETHER_BRICK });
/* 117 */     registerShapedRecipe(new ItemStack(Blocks.SANDSTONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.SANDSTONE });
/* 118 */     registerShapedRecipe(new ItemStack(Blocks.QUARTZ_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.QUARTZ_BLOCK });
/* 119 */     registerShapedRecipe(new ItemStack(Items.PAINTING, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Blocks.WOOL });
/* 120 */     registerShapedRecipe(new ItemStack(Items.ITEM_FRAME, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Items.LEATHER });
/* 121 */     registerShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.GOLD_INGOT, Character.valueOf('X'), Items.APPLE });
/* 122 */     registerShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.GOLD_BLOCK, Character.valueOf('X'), Items.APPLE });
/* 123 */     registerShapedRecipe(new ItemStack(Items.CARROT_GOLDEN, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.GOLD_NUGGET, Character.valueOf('X'), Items.CARROT });
/* 124 */     registerShapedRecipe(new ItemStack(Items.SPECKLED_MELON, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.GOLD_NUGGET, Character.valueOf('X'), Items.MELON });
/* 125 */     registerShapedRecipe(new ItemStack(Blocks.LEVER, 1), new Object[] { "X", "#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Items.STICK });
/* 126 */     registerShapedRecipe(new ItemStack(Blocks.TRIPWIRE_SOURCE, 2), new Object[] { "I", "S", "#", Character.valueOf('#'), Blocks.WOOD, Character.valueOf('S'), Items.STICK, Character.valueOf('I'), Items.IRON_INGOT });
/* 127 */     registerShapedRecipe(new ItemStack(Blocks.REDSTONE_TORCH_ON, 1), new Object[] { "X", "#", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Items.REDSTONE });
/* 128 */     registerShapedRecipe(new ItemStack(Items.DIODE, 1), new Object[] { "#X#", "III", Character.valueOf('#'), Blocks.REDSTONE_TORCH_ON, Character.valueOf('X'), Items.REDSTONE, Character.valueOf('I'), Blocks.STONE });
/* 129 */     registerShapedRecipe(new ItemStack(Items.REDSTONE_COMPARATOR, 1), new Object[] { " # ", "#X#", "III", Character.valueOf('#'), Blocks.REDSTONE_TORCH_ON, Character.valueOf('X'), Items.QUARTZ, Character.valueOf('I'), Blocks.STONE });
/* 130 */     registerShapedRecipe(new ItemStack(Items.WATCH, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.GOLD_INGOT, Character.valueOf('X'), Items.REDSTONE });
/* 131 */     registerShapedRecipe(new ItemStack(Items.COMPASS, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.IRON_INGOT, Character.valueOf('X'), Items.REDSTONE });
/* 132 */     registerShapedRecipe(new ItemStack(Items.MAP_EMPTY, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.PAPER, Character.valueOf('X'), Items.COMPASS });
/* 133 */     registerShapedRecipe(new ItemStack(Blocks.STONE_BUTTON, 1), new Object[] { "#", Character.valueOf('#'), Blocks.STONE });
/* 134 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_BUTTON, 1), new Object[] { "#", Character.valueOf('#'), Blocks.WOOD });
/* 135 */     registerShapedRecipe(new ItemStack(Blocks.STONE_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Blocks.STONE });
/* 136 */     registerShapedRecipe(new ItemStack(Blocks.WOOD_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Blocks.WOOD });
/* 137 */     registerShapedRecipe(new ItemStack(Blocks.IRON_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Items.IRON_INGOT });
/* 138 */     registerShapedRecipe(new ItemStack(Blocks.GOLD_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Items.GOLD_INGOT });
/* 139 */     registerShapedRecipe(new ItemStack(Blocks.DISPENSER, 1), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Items.BOW, Character.valueOf('R'), Items.REDSTONE });
/* 140 */     registerShapedRecipe(new ItemStack(Blocks.DROPPER, 1), new Object[] { "###", "# #", "#R#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('R'), Items.REDSTONE });
/* 141 */     registerShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('R'), Items.REDSTONE, Character.valueOf('T'), Blocks.WOOD });
/* 142 */     registerShapedRecipe(new ItemStack(Blocks.PISTON_STICKY, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.SLIME_BALL, Character.valueOf('P'), Blocks.PISTON });
/* 143 */     registerShapedRecipe(new ItemStack(Items.BED, 1), new Object[] { "###", "XXX", Character.valueOf('#'), Blocks.WOOL, Character.valueOf('X'), Blocks.WOOD });
/* 144 */     registerShapedRecipe(new ItemStack(Blocks.ENCHANTMENT_TABLE, 1), new Object[] { " B ", "D#D", "###", Character.valueOf('#'), Blocks.OBSIDIAN, Character.valueOf('B'), Items.BOOK, Character.valueOf('D'), Items.DIAMOND });
/* 145 */     registerShapedRecipe(new ItemStack(Blocks.ANVIL, 1), new Object[] { "III", " i ", "iii", Character.valueOf('I'), Blocks.IRON_BLOCK, Character.valueOf('i'), Items.IRON_INGOT });
/* 146 */     registerShapelessRecipe(new ItemStack(Items.EYE_OF_ENDER, 1), new Object[] { Items.ENDER_PEARL, Items.BLAZE_POWDER });
/* 147 */     registerShapelessRecipe(new ItemStack(Items.FIREBALL, 3), new Object[] { Items.SULPHUR, Items.BLAZE_POWDER, Items.COAL });
/* 148 */     registerShapelessRecipe(new ItemStack(Items.FIREBALL, 3), new Object[] { Items.SULPHUR, Items.BLAZE_POWDER, new ItemStack(Items.COAL, 1, 1) });
/* 149 */     registerShapedRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR), new Object[] { "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.GLASS, Character.valueOf('Q'), Items.QUARTZ, Character.valueOf('W'), Blocks.WOOD_STEP });
/* 150 */     registerShapedRecipe(new ItemStack(Blocks.HOPPER), new Object[] { "I I", "ICI", " I ", Character.valueOf('I'), Items.IRON_INGOT, Character.valueOf('C'), Blocks.CHEST });
/*     */     
/* 152 */     registerShapelessRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE), new Object[] { Blocks.VINE, Blocks.COBBLESTONE });
/* 153 */     registerShapelessRecipe(new ItemStack(Blocks.SMOOTH_BRICK, 1, 1), new Object[] { Blocks.VINE, Blocks.SMOOTH_BRICK });
/* 154 */     registerShapelessRecipe(new ItemStack(Blocks.SMOOTH_BRICK, 1, 3), new Object[] { new ItemStack(Blocks.STEP, 1, 5), new ItemStack(Blocks.STEP, 1, 5) });
/*     */ 
/*     */     
/* 157 */     sort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sort() {
/* 162 */     Collections.sort(this.recipes, new RecipeSorter(this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipes registerShapedRecipe(ItemStack itemstack, Object... aobject) {
/* 168 */     String s = "";
/* 169 */     int i = 0;
/* 170 */     int j = 0;
/* 171 */     int k = 0;
/*     */     
/* 173 */     if (aobject[i] instanceof String[]) {
/* 174 */       String[] astring = (String[])aobject[i++];
/*     */       
/* 176 */       for (int l = 0; l < astring.length; l++) {
/* 177 */         String s1 = astring[l];
/*     */         
/* 179 */         k++;
/* 180 */         j = s1.length();
/* 181 */         s = s + s1;
/*     */       } 
/*     */     } else {
/* 184 */       while (aobject[i] instanceof String) {
/* 185 */         String s2 = (String)aobject[i++];
/*     */         
/* 187 */         k++;
/* 188 */         j = s2.length();
/* 189 */         s = s + s2;
/*     */       } 
/*     */     } 
/*     */     
/*     */     HashMap<Object, Object> hashmap;
/*     */     
/* 195 */     for (hashmap = new HashMap<Object, Object>(); i < aobject.length; i += 2) {
/* 196 */       Character character = (Character)aobject[i];
/* 197 */       ItemStack itemstack1 = null;
/*     */       
/* 199 */       if (aobject[i + 1] instanceof Item) {
/* 200 */         itemstack1 = new ItemStack((Item)aobject[i + 1]);
/* 201 */       } else if (aobject[i + 1] instanceof Block) {
/* 202 */         itemstack1 = new ItemStack((Block)aobject[i + 1], 1, 32767);
/* 203 */       } else if (aobject[i + 1] instanceof ItemStack) {
/* 204 */         itemstack1 = (ItemStack)aobject[i + 1];
/*     */       } 
/*     */       
/* 207 */       hashmap.put(character, itemstack1);
/*     */     } 
/*     */     
/* 210 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*     */     
/* 212 */     for (int i1 = 0; i1 < j * k; i1++) {
/* 213 */       char c0 = s.charAt(i1);
/*     */       
/* 215 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 216 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).cloneItemStack();
/*     */       } else {
/* 218 */         aitemstack[i1] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 222 */     ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, itemstack);
/*     */     
/* 224 */     this.recipes.add(shapedrecipes);
/* 225 */     return shapedrecipes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerShapelessRecipe(ItemStack itemstack, Object... aobject) {
/* 230 */     ArrayList<ItemStack> arraylist = new ArrayList();
/* 231 */     Object[] aobject1 = aobject;
/* 232 */     int i = aobject.length;
/*     */     
/* 234 */     for (int j = 0; j < i; j++) {
/* 235 */       Object object = aobject1[j];
/*     */       
/* 237 */       if (object instanceof ItemStack) {
/* 238 */         arraylist.add(((ItemStack)object).cloneItemStack());
/* 239 */       } else if (object instanceof Item) {
/* 240 */         arraylist.add(new ItemStack((Item)object));
/*     */       } else {
/* 242 */         if (!(object instanceof Block)) {
/* 243 */           throw new RuntimeException("Invalid shapeless recipy!");
/*     */         }
/*     */         
/* 246 */         arraylist.add(new ItemStack((Block)object));
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     this.recipes.add(new ShapelessRecipes(itemstack, arraylist));
/*     */   }
/*     */   
/*     */   public ItemStack craft(InventoryCrafting inventorycrafting, World world) {
/* 254 */     int i = 0;
/* 255 */     ItemStack itemstack = null;
/* 256 */     ItemStack itemstack1 = null;
/*     */     
/*     */     int j;
/*     */     
/* 260 */     for (j = 0; j < inventorycrafting.getSize(); j++) {
/* 261 */       ItemStack itemstack2 = inventorycrafting.getItem(j);
/*     */       
/* 263 */       if (itemstack2 != null) {
/* 264 */         if (i == 0) {
/* 265 */           itemstack = itemstack2;
/*     */         }
/*     */         
/* 268 */         if (i == 1) {
/* 269 */           itemstack1 = itemstack2;
/*     */         }
/*     */         
/* 272 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.count == 1 && itemstack1.count == 1 && itemstack.getItem().usesDurability()) {
/* 277 */       Item item = itemstack.getItem();
/* 278 */       int k = item.getMaxDurability() - itemstack.j();
/* 279 */       int l = item.getMaxDurability() - itemstack1.j();
/* 280 */       int i1 = k + l + item.getMaxDurability() * 5 / 100;
/* 281 */       int j1 = item.getMaxDurability() - i1;
/*     */       
/* 283 */       if (j1 < 0) {
/* 284 */         j1 = 0;
/*     */       }
/*     */ 
/*     */       
/* 288 */       ItemStack result = new ItemStack(itemstack.getItem(), 1, j1);
/* 289 */       List<ItemStack> ingredients = new ArrayList<ItemStack>();
/* 290 */       ingredients.add(itemstack.cloneItemStack());
/* 291 */       ingredients.add(itemstack1.cloneItemStack());
/* 292 */       ShapelessRecipes recipe = new ShapelessRecipes(result.cloneItemStack(), ingredients);
/* 293 */       inventorycrafting.currentRecipe = recipe;
/* 294 */       result = CraftEventFactory.callPreCraftEvent(inventorycrafting, result, this.lastCraftView, true);
/* 295 */       return result;
/*     */     } 
/*     */     
/* 298 */     for (j = 0; j < this.recipes.size(); j++) {
/* 299 */       IRecipe irecipe = this.recipes.get(j);
/*     */       
/* 301 */       if (irecipe.a(inventorycrafting, world)) {
/*     */         
/* 303 */         inventorycrafting.currentRecipe = irecipe;
/* 304 */         ItemStack result = irecipe.a(inventorycrafting);
/* 305 */         return CraftEventFactory.callPreCraftEvent(inventorycrafting, result, this.lastCraftView, false);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 310 */     inventorycrafting.currentRecipe = null;
/* 311 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getRecipes() {
/* 316 */     return this.recipes;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CraftingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */