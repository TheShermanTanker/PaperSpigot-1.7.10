/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticList
/*     */ {
/*  18 */   protected static Map a = new HashMap<Object, Object>();
/*     */   
/*  20 */   public static List stats = new ArrayList();
/*  21 */   public static List c = new ArrayList();
/*  22 */   public static List d = new ArrayList();
/*  23 */   public static List e = new ArrayList();
/*     */   
/*  25 */   public static Statistic f = (new CounterStatistic("stat.leaveGame", new ChatMessage("stat.leaveGame", new Object[0]))).i().h();
/*     */   
/*  27 */   public static Statistic g = (new CounterStatistic("stat.playOneMinute", new ChatMessage("stat.playOneMinute", new Object[0]), Statistic.h)).i().h();
/*     */   
/*  29 */   public static Statistic h = (new CounterStatistic("stat.walkOneCm", new ChatMessage("stat.walkOneCm", new Object[0]), Statistic.i)).i().h();
/*  30 */   public static Statistic i = (new CounterStatistic("stat.swimOneCm", new ChatMessage("stat.swimOneCm", new Object[0]), Statistic.i)).i().h();
/*  31 */   public static Statistic j = (new CounterStatistic("stat.fallOneCm", new ChatMessage("stat.fallOneCm", new Object[0]), Statistic.i)).i().h();
/*  32 */   public static Statistic k = (new CounterStatistic("stat.climbOneCm", new ChatMessage("stat.climbOneCm", new Object[0]), Statistic.i)).i().h();
/*  33 */   public static Statistic l = (new CounterStatistic("stat.flyOneCm", new ChatMessage("stat.flyOneCm", new Object[0]), Statistic.i)).i().h();
/*  34 */   public static Statistic m = (new CounterStatistic("stat.diveOneCm", new ChatMessage("stat.diveOneCm", new Object[0]), Statistic.i)).i().h();
/*  35 */   public static Statistic n = (new CounterStatistic("stat.minecartOneCm", new ChatMessage("stat.minecartOneCm", new Object[0]), Statistic.i)).i().h();
/*  36 */   public static Statistic o = (new CounterStatistic("stat.boatOneCm", new ChatMessage("stat.boatOneCm", new Object[0]), Statistic.i)).i().h();
/*  37 */   public static Statistic p = (new CounterStatistic("stat.pigOneCm", new ChatMessage("stat.pigOneCm", new Object[0]), Statistic.i)).i().h();
/*  38 */   public static Statistic q = (new CounterStatistic("stat.horseOneCm", new ChatMessage("stat.horseOneCm", new Object[0]), Statistic.i)).i().h();
/*     */   
/*  40 */   public static Statistic r = (new CounterStatistic("stat.jump", new ChatMessage("stat.jump", new Object[0]))).i().h();
/*  41 */   public static Statistic s = (new CounterStatistic("stat.drop", new ChatMessage("stat.drop", new Object[0]))).i().h();
/*     */   
/*  43 */   public static Statistic t = (new CounterStatistic("stat.damageDealt", new ChatMessage("stat.damageDealt", new Object[0]), Statistic.j)).h();
/*  44 */   public static Statistic u = (new CounterStatistic("stat.damageTaken", new ChatMessage("stat.damageTaken", new Object[0]), Statistic.j)).h();
/*  45 */   public static Statistic v = (new CounterStatistic("stat.deaths", new ChatMessage("stat.deaths", new Object[0]))).h();
/*  46 */   public static Statistic w = (new CounterStatistic("stat.mobKills", new ChatMessage("stat.mobKills", new Object[0]))).h();
/*  47 */   public static Statistic x = (new CounterStatistic("stat.animalsBred", new ChatMessage("stat.animalsBred", new Object[0]))).h();
/*  48 */   public static Statistic y = (new CounterStatistic("stat.playerKills", new ChatMessage("stat.playerKills", new Object[0]))).h();
/*  49 */   public static Statistic z = (new CounterStatistic("stat.fishCaught", new ChatMessage("stat.fishCaught", new Object[0]))).h();
/*  50 */   public static Statistic A = (new CounterStatistic("stat.junkFished", new ChatMessage("stat.junkFished", new Object[0]))).h();
/*  51 */   public static Statistic B = (new CounterStatistic("stat.treasureFished", new ChatMessage("stat.treasureFished", new Object[0]))).h();
/*     */   
/*  53 */   public static final Statistic[] MINE_BLOCK_COUNT = new Statistic[4096];
/*  54 */   public static final Statistic[] CRAFT_BLOCK_COUNT = new Statistic[32000];
/*  55 */   public static final Statistic[] USE_ITEM_COUNT = new Statistic[32000];
/*  56 */   public static final Statistic[] BREAK_ITEM_COUNT = new Statistic[32000];
/*     */   
/*     */   public static void a() {
/*  59 */     c();
/*  60 */     d();
/*  61 */     e();
/*  62 */     b();
/*     */     
/*  64 */     AchievementList.a();
/*  65 */     EntityTypes.a();
/*     */   }
/*     */   
/*     */   private static void b() {
/*  69 */     HashSet<Item> hashSet = new HashSet();
/*     */     
/*  71 */     for (IRecipe iRecipe : CraftingManager.getInstance().getRecipes()) {
/*  72 */       if (iRecipe.b() == null)
/*  73 */         continue;  hashSet.add(iRecipe.b().getItem());
/*     */     } 
/*  75 */     for (ItemStack itemStack : RecipesFurnace.getInstance().getRecipes().values()) {
/*  76 */       hashSet.add(itemStack.getItem());
/*     */     }
/*     */     
/*  79 */     for (Item item : hashSet) {
/*  80 */       if (item == null)
/*     */         continue; 
/*  82 */       int i = Item.getId(item);
/*  83 */       CRAFT_BLOCK_COUNT[i] = (new CraftingStatistic("stat.craftItem." + i, new ChatMessage("stat.craftItem", new Object[] { (new ItemStack(item)).E() }), item)).h();
/*     */     } 
/*     */     
/*  86 */     a(CRAFT_BLOCK_COUNT);
/*     */   }
/*     */   
/*     */   private static void c() {
/*  90 */     for (Block block : Block.REGISTRY) {
/*  91 */       if (Item.getItemOf(block) == null)
/*     */         continue; 
/*  93 */       int i = Block.getId(block);
/*  94 */       if (block.G()) {
/*  95 */         MINE_BLOCK_COUNT[i] = (new CraftingStatistic("stat.mineBlock." + i, new ChatMessage("stat.mineBlock", new Object[] { (new ItemStack(block)).E() }), Item.getItemOf(block))).h();
/*  96 */         e.add((CraftingStatistic)MINE_BLOCK_COUNT[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     a(MINE_BLOCK_COUNT);
/*     */   }
/*     */   
/*     */   private static void d() {
/* 104 */     for (Item item : Item.REGISTRY) {
/* 105 */       if (item == null)
/*     */         continue; 
/* 107 */       int i = Item.getId(item);
/*     */       
/* 109 */       USE_ITEM_COUNT[i] = (new CraftingStatistic("stat.useItem." + i, new ChatMessage("stat.useItem", new Object[] { (new ItemStack(item)).E() }), item)).h();
/*     */       
/* 111 */       if (!(item instanceof ItemBlock)) {
/* 112 */         d.add((CraftingStatistic)USE_ITEM_COUNT[i]);
/*     */       }
/*     */     } 
/*     */     
/* 116 */     a(USE_ITEM_COUNT);
/*     */   }
/*     */   
/*     */   private static void e() {
/* 120 */     for (Item item : Item.REGISTRY) {
/* 121 */       if (item == null)
/*     */         continue; 
/* 123 */       int i = Item.getId(item);
/*     */       
/* 125 */       if (item.usesDurability()) {
/* 126 */         BREAK_ITEM_COUNT[i] = (new CraftingStatistic("stat.breakItem." + i, new ChatMessage("stat.breakItem", new Object[] { (new ItemStack(item)).E() }), item)).h();
/*     */       }
/*     */     } 
/*     */     
/* 130 */     a(BREAK_ITEM_COUNT);
/*     */   }
/*     */   
/*     */   private static void a(Statistic[] paramArrayOfStatistic) {
/* 134 */     a(paramArrayOfStatistic, Blocks.STATIONARY_WATER, Blocks.WATER);
/* 135 */     a(paramArrayOfStatistic, Blocks.STATIONARY_LAVA, Blocks.LAVA);
/*     */     
/* 137 */     a(paramArrayOfStatistic, Blocks.JACK_O_LANTERN, Blocks.PUMPKIN);
/* 138 */     a(paramArrayOfStatistic, Blocks.BURNING_FURNACE, Blocks.FURNACE);
/* 139 */     a(paramArrayOfStatistic, Blocks.GLOWING_REDSTONE_ORE, Blocks.REDSTONE_ORE);
/*     */     
/* 141 */     a(paramArrayOfStatistic, Blocks.DIODE_ON, Blocks.DIODE_OFF);
/* 142 */     a(paramArrayOfStatistic, Blocks.REDSTONE_COMPARATOR_ON, Blocks.REDSTONE_COMPARATOR_OFF);
/* 143 */     a(paramArrayOfStatistic, Blocks.REDSTONE_TORCH_ON, Blocks.REDSTONE_TORCH_OFF);
/* 144 */     a(paramArrayOfStatistic, Blocks.REDSTONE_LAMP_ON, Blocks.REDSTONE_LAMP_OFF);
/*     */     
/* 146 */     a(paramArrayOfStatistic, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM);
/* 147 */     a(paramArrayOfStatistic, Blocks.DOUBLE_STEP, Blocks.STEP);
/* 148 */     a(paramArrayOfStatistic, Blocks.WOOD_DOUBLE_STEP, Blocks.WOOD_STEP);
/*     */     
/* 150 */     a(paramArrayOfStatistic, Blocks.GRASS, Blocks.DIRT);
/* 151 */     a(paramArrayOfStatistic, Blocks.SOIL, Blocks.DIRT);
/*     */   }
/*     */   
/*     */   private static void a(Statistic[] paramArrayOfStatistic, Block paramBlock1, Block paramBlock2) {
/* 155 */     int i = Block.getId(paramBlock1);
/* 156 */     int j = Block.getId(paramBlock2);
/*     */     
/* 158 */     if (paramArrayOfStatistic[i] != null && paramArrayOfStatistic[j] == null) {
/*     */       
/* 160 */       paramArrayOfStatistic[j] = paramArrayOfStatistic[i];
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     stats.remove(paramArrayOfStatistic[i]);
/* 165 */     e.remove(paramArrayOfStatistic[i]);
/* 166 */     c.remove(paramArrayOfStatistic[i]);
/* 167 */     paramArrayOfStatistic[i] = paramArrayOfStatistic[j];
/*     */   }
/*     */   
/*     */   public static Statistic a(MonsterEggInfo paramMonsterEggInfo) {
/* 171 */     String str = EntityTypes.b(paramMonsterEggInfo.a);
/* 172 */     if (str == null) return null; 
/* 173 */     return (new Statistic("stat.killEntity." + str, new ChatMessage("stat.entityKill", new Object[] { new ChatMessage("entity." + str + ".name", new Object[0]) }))).h();
/*     */   }
/*     */   
/*     */   public static Statistic b(MonsterEggInfo paramMonsterEggInfo) {
/* 177 */     String str = EntityTypes.b(paramMonsterEggInfo.a);
/* 178 */     if (str == null) return null; 
/* 179 */     return (new Statistic("stat.entityKilledBy." + str, new ChatMessage("stat.entityKilledBy", new Object[] { new ChatMessage("entity." + str + ".name", new Object[0]) }))).h();
/*     */   }
/*     */   
/*     */   public static Statistic getStatistic(String paramString) {
/* 183 */     return (Statistic)a.get(paramString);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StatisticList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */