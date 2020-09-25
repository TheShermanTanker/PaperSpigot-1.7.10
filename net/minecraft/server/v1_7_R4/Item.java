/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.common.collect.HashMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.Multimap;
/*     */ import net.minecraft.util.com.google.common.collect.Sets;
/*     */ 
/*     */ 
/*     */ public class Item
/*     */ {
/*  14 */   public static final RegistryMaterials REGISTRY = new RegistryMaterials();
/*  15 */   protected static final UUID f = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*     */   private CreativeModeTab a;
/*  17 */   protected static Random g = new Random();
/*  18 */   protected int maxStackSize = 64;
/*     */   
/*     */   private int durability;
/*     */   
/*     */   protected boolean i;
/*     */   protected boolean j;
/*     */   private Item craftingResult;
/*     */   private String d;
/*     */   private String name;
/*     */   protected String l;
/*     */   
/*     */   public static int getId(Item item) {
/*  30 */     return (item == null) ? 0 : REGISTRY.b(item);
/*     */   }
/*     */   
/*     */   public static Item getById(int i) {
/*  34 */     return (Item)REGISTRY.a(i);
/*     */   }
/*     */   
/*     */   public static Item getItemOf(Block block) {
/*  38 */     return getById(Block.getId(block));
/*     */   }
/*     */   
/*     */   public static void l() {
/*  42 */     REGISTRY.a(256, "iron_shovel", (new ItemSpade(EnumToolMaterial.IRON)).c("shovelIron").f("iron_shovel"));
/*  43 */     REGISTRY.a(257, "iron_pickaxe", (new ItemPickaxe(EnumToolMaterial.IRON)).c("pickaxeIron").f("iron_pickaxe"));
/*  44 */     REGISTRY.a(258, "iron_axe", (new ItemAxe(EnumToolMaterial.IRON)).c("hatchetIron").f("iron_axe"));
/*  45 */     REGISTRY.a(259, "flint_and_steel", (new ItemFlintAndSteel()).c("flintAndSteel").f("flint_and_steel"));
/*  46 */     REGISTRY.a(260, "apple", (new ItemFood(4, 0.3F, false)).c("apple").f("apple"));
/*  47 */     REGISTRY.a(261, "bow", (new ItemBow()).c("bow").f("bow"));
/*  48 */     REGISTRY.a(262, "arrow", (new Item()).c("arrow").a(CreativeModeTab.j).f("arrow"));
/*  49 */     REGISTRY.a(263, "coal", (new ItemCoal()).c("coal").f("coal"));
/*  50 */     REGISTRY.a(264, "diamond", (new Item()).c("diamond").a(CreativeModeTab.l).f("diamond"));
/*  51 */     REGISTRY.a(265, "iron_ingot", (new Item()).c("ingotIron").a(CreativeModeTab.l).f("iron_ingot"));
/*  52 */     REGISTRY.a(266, "gold_ingot", (new Item()).c("ingotGold").a(CreativeModeTab.l).f("gold_ingot"));
/*  53 */     REGISTRY.a(267, "iron_sword", (new ItemSword(EnumToolMaterial.IRON)).c("swordIron").f("iron_sword"));
/*  54 */     REGISTRY.a(268, "wooden_sword", (new ItemSword(EnumToolMaterial.WOOD)).c("swordWood").f("wood_sword"));
/*  55 */     REGISTRY.a(269, "wooden_shovel", (new ItemSpade(EnumToolMaterial.WOOD)).c("shovelWood").f("wood_shovel"));
/*  56 */     REGISTRY.a(270, "wooden_pickaxe", (new ItemPickaxe(EnumToolMaterial.WOOD)).c("pickaxeWood").f("wood_pickaxe"));
/*  57 */     REGISTRY.a(271, "wooden_axe", (new ItemAxe(EnumToolMaterial.WOOD)).c("hatchetWood").f("wood_axe"));
/*  58 */     REGISTRY.a(272, "stone_sword", (new ItemSword(EnumToolMaterial.STONE)).c("swordStone").f("stone_sword"));
/*  59 */     REGISTRY.a(273, "stone_shovel", (new ItemSpade(EnumToolMaterial.STONE)).c("shovelStone").f("stone_shovel"));
/*  60 */     REGISTRY.a(274, "stone_pickaxe", (new ItemPickaxe(EnumToolMaterial.STONE)).c("pickaxeStone").f("stone_pickaxe"));
/*  61 */     REGISTRY.a(275, "stone_axe", (new ItemAxe(EnumToolMaterial.STONE)).c("hatchetStone").f("stone_axe"));
/*  62 */     REGISTRY.a(276, "diamond_sword", (new ItemSword(EnumToolMaterial.DIAMOND)).c("swordDiamond").f("diamond_sword"));
/*  63 */     REGISTRY.a(277, "diamond_shovel", (new ItemSpade(EnumToolMaterial.DIAMOND)).c("shovelDiamond").f("diamond_shovel"));
/*  64 */     REGISTRY.a(278, "diamond_pickaxe", (new ItemPickaxe(EnumToolMaterial.DIAMOND)).c("pickaxeDiamond").f("diamond_pickaxe"));
/*  65 */     REGISTRY.a(279, "diamond_axe", (new ItemAxe(EnumToolMaterial.DIAMOND)).c("hatchetDiamond").f("diamond_axe"));
/*  66 */     REGISTRY.a(280, "stick", (new Item()).q().c("stick").a(CreativeModeTab.l).f("stick"));
/*  67 */     REGISTRY.a(281, "bowl", (new Item()).c("bowl").a(CreativeModeTab.l).f("bowl"));
/*  68 */     REGISTRY.a(282, "mushroom_stew", (new ItemSoup(6)).c("mushroomStew").f("mushroom_stew"));
/*  69 */     REGISTRY.a(283, "golden_sword", (new ItemSword(EnumToolMaterial.GOLD)).c("swordGold").f("gold_sword"));
/*  70 */     REGISTRY.a(284, "golden_shovel", (new ItemSpade(EnumToolMaterial.GOLD)).c("shovelGold").f("gold_shovel"));
/*  71 */     REGISTRY.a(285, "golden_pickaxe", (new ItemPickaxe(EnumToolMaterial.GOLD)).c("pickaxeGold").f("gold_pickaxe"));
/*  72 */     REGISTRY.a(286, "golden_axe", (new ItemAxe(EnumToolMaterial.GOLD)).c("hatchetGold").f("gold_axe"));
/*  73 */     REGISTRY.a(287, "string", (new ItemReed(Blocks.TRIPWIRE)).c("string").a(CreativeModeTab.l).f("string"));
/*  74 */     REGISTRY.a(288, "feather", (new Item()).c("feather").a(CreativeModeTab.l).f("feather"));
/*  75 */     REGISTRY.a(289, "gunpowder", (new Item()).c("sulphur").e(PotionBrewer.k).a(CreativeModeTab.l).f("gunpowder"));
/*  76 */     REGISTRY.a(290, "wooden_hoe", (new ItemHoe(EnumToolMaterial.WOOD)).c("hoeWood").f("wood_hoe"));
/*  77 */     REGISTRY.a(291, "stone_hoe", (new ItemHoe(EnumToolMaterial.STONE)).c("hoeStone").f("stone_hoe"));
/*  78 */     REGISTRY.a(292, "iron_hoe", (new ItemHoe(EnumToolMaterial.IRON)).c("hoeIron").f("iron_hoe"));
/*  79 */     REGISTRY.a(293, "diamond_hoe", (new ItemHoe(EnumToolMaterial.DIAMOND)).c("hoeDiamond").f("diamond_hoe"));
/*  80 */     REGISTRY.a(294, "golden_hoe", (new ItemHoe(EnumToolMaterial.GOLD)).c("hoeGold").f("gold_hoe"));
/*  81 */     REGISTRY.a(295, "wheat_seeds", (new ItemSeeds(Blocks.CROPS, Blocks.SOIL)).c("seeds").f("seeds_wheat"));
/*  82 */     REGISTRY.a(296, "wheat", (new Item()).c("wheat").a(CreativeModeTab.l).f("wheat"));
/*  83 */     REGISTRY.a(297, "bread", (new ItemFood(5, 0.6F, false)).c("bread").f("bread"));
/*  84 */     REGISTRY.a(298, "leather_helmet", (new ItemArmor(EnumArmorMaterial.CLOTH, 0, 0)).c("helmetCloth").f("leather_helmet"));
/*  85 */     REGISTRY.a(299, "leather_chestplate", (new ItemArmor(EnumArmorMaterial.CLOTH, 0, 1)).c("chestplateCloth").f("leather_chestplate"));
/*  86 */     REGISTRY.a(300, "leather_leggings", (new ItemArmor(EnumArmorMaterial.CLOTH, 0, 2)).c("leggingsCloth").f("leather_leggings"));
/*  87 */     REGISTRY.a(301, "leather_boots", (new ItemArmor(EnumArmorMaterial.CLOTH, 0, 3)).c("bootsCloth").f("leather_boots"));
/*  88 */     REGISTRY.a(302, "chainmail_helmet", (new ItemArmor(EnumArmorMaterial.CHAIN, 1, 0)).c("helmetChain").f("chainmail_helmet"));
/*  89 */     REGISTRY.a(303, "chainmail_chestplate", (new ItemArmor(EnumArmorMaterial.CHAIN, 1, 1)).c("chestplateChain").f("chainmail_chestplate"));
/*  90 */     REGISTRY.a(304, "chainmail_leggings", (new ItemArmor(EnumArmorMaterial.CHAIN, 1, 2)).c("leggingsChain").f("chainmail_leggings"));
/*  91 */     REGISTRY.a(305, "chainmail_boots", (new ItemArmor(EnumArmorMaterial.CHAIN, 1, 3)).c("bootsChain").f("chainmail_boots"));
/*  92 */     REGISTRY.a(306, "iron_helmet", (new ItemArmor(EnumArmorMaterial.IRON, 2, 0)).c("helmetIron").f("iron_helmet"));
/*  93 */     REGISTRY.a(307, "iron_chestplate", (new ItemArmor(EnumArmorMaterial.IRON, 2, 1)).c("chestplateIron").f("iron_chestplate"));
/*  94 */     REGISTRY.a(308, "iron_leggings", (new ItemArmor(EnumArmorMaterial.IRON, 2, 2)).c("leggingsIron").f("iron_leggings"));
/*  95 */     REGISTRY.a(309, "iron_boots", (new ItemArmor(EnumArmorMaterial.IRON, 2, 3)).c("bootsIron").f("iron_boots"));
/*  96 */     REGISTRY.a(310, "diamond_helmet", (new ItemArmor(EnumArmorMaterial.DIAMOND, 3, 0)).c("helmetDiamond").f("diamond_helmet"));
/*  97 */     REGISTRY.a(311, "diamond_chestplate", (new ItemArmor(EnumArmorMaterial.DIAMOND, 3, 1)).c("chestplateDiamond").f("diamond_chestplate"));
/*  98 */     REGISTRY.a(312, "diamond_leggings", (new ItemArmor(EnumArmorMaterial.DIAMOND, 3, 2)).c("leggingsDiamond").f("diamond_leggings"));
/*  99 */     REGISTRY.a(313, "diamond_boots", (new ItemArmor(EnumArmorMaterial.DIAMOND, 3, 3)).c("bootsDiamond").f("diamond_boots"));
/* 100 */     REGISTRY.a(314, "golden_helmet", (new ItemArmor(EnumArmorMaterial.GOLD, 4, 0)).c("helmetGold").f("gold_helmet"));
/* 101 */     REGISTRY.a(315, "golden_chestplate", (new ItemArmor(EnumArmorMaterial.GOLD, 4, 1)).c("chestplateGold").f("gold_chestplate"));
/* 102 */     REGISTRY.a(316, "golden_leggings", (new ItemArmor(EnumArmorMaterial.GOLD, 4, 2)).c("leggingsGold").f("gold_leggings"));
/* 103 */     REGISTRY.a(317, "golden_boots", (new ItemArmor(EnumArmorMaterial.GOLD, 4, 3)).c("bootsGold").f("gold_boots"));
/* 104 */     REGISTRY.a(318, "flint", (new Item()).c("flint").a(CreativeModeTab.l).f("flint"));
/* 105 */     REGISTRY.a(319, "porkchop", (new ItemFood(3, 0.3F, true)).c("porkchopRaw").f("porkchop_raw"));
/* 106 */     REGISTRY.a(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).c("porkchopCooked").f("porkchop_cooked"));
/* 107 */     REGISTRY.a(321, "painting", (new ItemHanging(EntityPainting.class)).c("painting").f("painting"));
/* 108 */     REGISTRY.a(322, "golden_apple", (new ItemGoldenApple(4, 1.2F, false)).j().a(MobEffectList.REGENERATION.id, 5, 1, 1.0F).c("appleGold").f("apple_golden"));
/* 109 */     REGISTRY.a(323, "sign", (new ItemSign()).c("sign").f("sign"));
/* 110 */     REGISTRY.a(324, "wooden_door", (new ItemDoor(Material.WOOD)).c("doorWood").f("door_wood"));
/* 111 */     Item item = (new ItemBucket(Blocks.AIR)).c("bucket").e(16).f("bucket_empty");
/*     */     
/* 113 */     REGISTRY.a(325, "bucket", item);
/* 114 */     REGISTRY.a(326, "water_bucket", (new ItemBucket(Blocks.WATER)).c("bucketWater").c(item).f("bucket_water"));
/* 115 */     REGISTRY.a(327, "lava_bucket", (new ItemBucket(Blocks.LAVA)).c("bucketLava").c(item).f("bucket_lava"));
/* 116 */     REGISTRY.a(328, "minecart", (new ItemMinecart(0)).c("minecart").f("minecart_normal"));
/* 117 */     REGISTRY.a(329, "saddle", (new ItemSaddle()).c("saddle").f("saddle"));
/* 118 */     REGISTRY.a(330, "iron_door", (new ItemDoor(Material.ORE)).c("doorIron").f("door_iron"));
/* 119 */     REGISTRY.a(331, "redstone", (new ItemRedstone()).c("redstone").e(PotionBrewer.i).f("redstone_dust"));
/* 120 */     REGISTRY.a(332, "snowball", (new ItemSnowball()).c("snowball").f("snowball"));
/* 121 */     REGISTRY.a(333, "boat", (new ItemBoat()).c("boat").f("boat"));
/* 122 */     REGISTRY.a(334, "leather", (new Item()).c("leather").a(CreativeModeTab.l).f("leather"));
/* 123 */     REGISTRY.a(335, "milk_bucket", (new ItemMilkBucket()).c("milk").c(item).f("bucket_milk"));
/* 124 */     REGISTRY.a(336, "brick", (new Item()).c("brick").a(CreativeModeTab.l).f("brick"));
/* 125 */     REGISTRY.a(337, "clay_ball", (new Item()).c("clay").a(CreativeModeTab.l).f("clay_ball"));
/* 126 */     REGISTRY.a(338, "reeds", (new ItemReed(Blocks.SUGAR_CANE_BLOCK)).c("reeds").a(CreativeModeTab.l).f("reeds"));
/* 127 */     REGISTRY.a(339, "paper", (new Item()).c("paper").a(CreativeModeTab.f).f("paper"));
/* 128 */     REGISTRY.a(340, "book", (new ItemBook()).c("book").a(CreativeModeTab.f).f("book_normal"));
/* 129 */     REGISTRY.a(341, "slime_ball", (new Item()).c("slimeball").a(CreativeModeTab.f).f("slimeball"));
/* 130 */     REGISTRY.a(342, "chest_minecart", (new ItemMinecart(1)).c("minecartChest").f("minecart_chest"));
/* 131 */     REGISTRY.a(343, "furnace_minecart", (new ItemMinecart(2)).c("minecartFurnace").f("minecart_furnace"));
/* 132 */     REGISTRY.a(344, "egg", (new ItemEgg()).c("egg").f("egg"));
/* 133 */     REGISTRY.a(345, "compass", (new Item()).c("compass").a(CreativeModeTab.i).f("compass"));
/* 134 */     REGISTRY.a(346, "fishing_rod", (new ItemFishingRod()).c("fishingRod").f("fishing_rod"));
/* 135 */     REGISTRY.a(347, "clock", (new Item()).c("clock").a(CreativeModeTab.i).f("clock"));
/* 136 */     REGISTRY.a(348, "glowstone_dust", (new Item()).c("yellowDust").e(PotionBrewer.j).a(CreativeModeTab.l).f("glowstone_dust"));
/* 137 */     REGISTRY.a(349, "fish", (new ItemFish(false)).c("fish").f("fish_raw").a(true));
/* 138 */     REGISTRY.a(350, "cooked_fished", (new ItemFish(true)).c("fish").f("fish_cooked").a(true));
/* 139 */     REGISTRY.a(351, "dye", (new ItemDye()).c("dyePowder").f("dye_powder"));
/* 140 */     REGISTRY.a(352, "bone", (new Item()).c("bone").q().a(CreativeModeTab.f).f("bone"));
/* 141 */     REGISTRY.a(353, "sugar", (new Item()).c("sugar").e(PotionBrewer.b).a(CreativeModeTab.l).f("sugar"));
/* 142 */     REGISTRY.a(354, "cake", (new ItemReed(Blocks.CAKE_BLOCK)).e(1).c("cake").a(CreativeModeTab.h).f("cake"));
/* 143 */     REGISTRY.a(355, "bed", (new ItemBed()).e(1).c("bed").f("bed"));
/* 144 */     REGISTRY.a(356, "repeater", (new ItemReed(Blocks.DIODE_OFF)).c("diode").a(CreativeModeTab.d).f("repeater"));
/* 145 */     REGISTRY.a(357, "cookie", (new ItemFood(2, 0.1F, false)).c("cookie").f("cookie"));
/* 146 */     REGISTRY.a(358, "filled_map", (new ItemWorldMap()).c("map").f("map_filled"));
/* 147 */     REGISTRY.a(359, "shears", (new ItemShears()).c("shears").f("shears"));
/* 148 */     REGISTRY.a(360, "melon", (new ItemFood(2, 0.3F, false)).c("melon").f("melon"));
/* 149 */     REGISTRY.a(361, "pumpkin_seeds", (new ItemSeeds(Blocks.PUMPKIN_STEM, Blocks.SOIL)).c("seeds_pumpkin").f("seeds_pumpkin"));
/* 150 */     REGISTRY.a(362, "melon_seeds", (new ItemSeeds(Blocks.MELON_STEM, Blocks.SOIL)).c("seeds_melon").f("seeds_melon"));
/* 151 */     REGISTRY.a(363, "beef", (new ItemFood(3, 0.3F, true)).c("beefRaw").f("beef_raw"));
/* 152 */     REGISTRY.a(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).c("beefCooked").f("beef_cooked"));
/* 153 */     REGISTRY.a(365, "chicken", (new ItemFood(2, 0.3F, true)).a(MobEffectList.HUNGER.id, 30, 0, 0.3F).c("chickenRaw").f("chicken_raw"));
/* 154 */     REGISTRY.a(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).c("chickenCooked").f("chicken_cooked"));
/* 155 */     REGISTRY.a(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).a(MobEffectList.HUNGER.id, 30, 0, 0.8F).c("rottenFlesh").f("rotten_flesh"));
/* 156 */     REGISTRY.a(368, "ender_pearl", (new ItemEnderPearl()).c("enderPearl").f("ender_pearl"));
/* 157 */     REGISTRY.a(369, "blaze_rod", (new Item()).c("blazeRod").a(CreativeModeTab.l).f("blaze_rod"));
/* 158 */     REGISTRY.a(370, "ghast_tear", (new Item()).c("ghastTear").e(PotionBrewer.c).a(CreativeModeTab.k).f("ghast_tear"));
/* 159 */     REGISTRY.a(371, "gold_nugget", (new Item()).c("goldNugget").a(CreativeModeTab.l).f("gold_nugget"));
/* 160 */     REGISTRY.a(372, "nether_wart", (new ItemSeeds(Blocks.NETHER_WART, Blocks.SOUL_SAND)).c("netherStalkSeeds").e("+4").f("nether_wart"));
/* 161 */     REGISTRY.a(373, "potion", (new ItemPotion()).c("potion").f("potion"));
/* 162 */     REGISTRY.a(374, "glass_bottle", (new ItemGlassBottle()).c("glassBottle").f("potion_bottle_empty"));
/* 163 */     REGISTRY.a(375, "spider_eye", (new ItemFood(2, 0.8F, false)).a(MobEffectList.POISON.id, 5, 0, 1.0F).c("spiderEye").e(PotionBrewer.d).f("spider_eye"));
/* 164 */     REGISTRY.a(376, "fermented_spider_eye", (new Item()).c("fermentedSpiderEye").e(PotionBrewer.e).a(CreativeModeTab.k).f("spider_eye_fermented"));
/* 165 */     REGISTRY.a(377, "blaze_powder", (new Item()).c("blazePowder").e(PotionBrewer.g).a(CreativeModeTab.k).f("blaze_powder"));
/* 166 */     REGISTRY.a(378, "magma_cream", (new Item()).c("magmaCream").e(PotionBrewer.h).a(CreativeModeTab.k).f("magma_cream"));
/* 167 */     REGISTRY.a(379, "brewing_stand", (new ItemReed(Blocks.BREWING_STAND)).c("brewingStand").a(CreativeModeTab.k).f("brewing_stand"));
/* 168 */     REGISTRY.a(380, "cauldron", (new ItemReed(Blocks.CAULDRON)).c("cauldron").a(CreativeModeTab.k).f("cauldron"));
/* 169 */     REGISTRY.a(381, "ender_eye", (new ItemEnderEye()).c("eyeOfEnder").f("ender_eye"));
/* 170 */     REGISTRY.a(382, "speckled_melon", (new Item()).c("speckledMelon").e(PotionBrewer.f).a(CreativeModeTab.k).f("melon_speckled"));
/* 171 */     REGISTRY.a(383, "spawn_egg", (new ItemMonsterEgg()).c("monsterPlacer").f("spawn_egg"));
/* 172 */     REGISTRY.a(384, "experience_bottle", (new ItemExpBottle()).c("expBottle").f("experience_bottle"));
/* 173 */     REGISTRY.a(385, "fire_charge", (new ItemFireball()).c("fireball").f("fireball"));
/* 174 */     REGISTRY.a(386, "writable_book", (new ItemBookAndQuill()).c("writingBook").a(CreativeModeTab.f).f("book_writable"));
/* 175 */     REGISTRY.a(387, "written_book", (new ItemWrittenBook()).c("writtenBook").f("book_written").e(16));
/* 176 */     REGISTRY.a(388, "emerald", (new Item()).c("emerald").a(CreativeModeTab.l).f("emerald"));
/* 177 */     REGISTRY.a(389, "item_frame", (new ItemHanging(EntityItemFrame.class)).c("frame").f("item_frame"));
/* 178 */     REGISTRY.a(390, "flower_pot", (new ItemReed(Blocks.FLOWER_POT)).c("flowerPot").a(CreativeModeTab.c).f("flower_pot"));
/* 179 */     REGISTRY.a(391, "carrot", (new ItemSeedFood(4, 0.6F, Blocks.CARROTS, Blocks.SOIL)).c("carrots").f("carrot"));
/* 180 */     REGISTRY.a(392, "potato", (new ItemSeedFood(1, 0.3F, Blocks.POTATOES, Blocks.SOIL)).c("potato").f("potato"));
/* 181 */     REGISTRY.a(393, "baked_potato", (new ItemFood(6, 0.6F, false)).c("potatoBaked").f("potato_baked"));
/* 182 */     REGISTRY.a(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).a(MobEffectList.POISON.id, 5, 0, 0.6F).c("potatoPoisonous").f("potato_poisonous"));
/* 183 */     REGISTRY.a(395, "map", (new ItemMapEmpty()).c("emptyMap").f("map_empty"));
/* 184 */     REGISTRY.a(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).c("carrotGolden").e(PotionBrewer.l).f("carrot_golden"));
/* 185 */     REGISTRY.a(397, "skull", (new ItemSkull()).c("skull").f("skull"));
/* 186 */     REGISTRY.a(398, "carrot_on_a_stick", (new ItemCarrotStick()).c("carrotOnAStick").f("carrot_on_a_stick"));
/* 187 */     REGISTRY.a(399, "nether_star", (new ItemNetherStar()).c("netherStar").a(CreativeModeTab.l).f("nether_star"));
/* 188 */     REGISTRY.a(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).c("pumpkinPie").a(CreativeModeTab.h).f("pumpkin_pie"));
/* 189 */     REGISTRY.a(401, "fireworks", (new ItemFireworks()).c("fireworks").f("fireworks"));
/* 190 */     REGISTRY.a(402, "firework_charge", (new ItemFireworksCharge()).c("fireworksCharge").a(CreativeModeTab.f).f("fireworks_charge"));
/* 191 */     REGISTRY.a(403, "enchanted_book", (new ItemEnchantedBook()).e(1).c("enchantedBook").f("book_enchanted"));
/* 192 */     REGISTRY.a(404, "comparator", (new ItemReed(Blocks.REDSTONE_COMPARATOR_OFF)).c("comparator").a(CreativeModeTab.d).f("comparator"));
/* 193 */     REGISTRY.a(405, "netherbrick", (new Item()).c("netherbrick").a(CreativeModeTab.l).f("netherbrick"));
/* 194 */     REGISTRY.a(406, "quartz", (new Item()).c("netherquartz").a(CreativeModeTab.l).f("quartz"));
/* 195 */     REGISTRY.a(407, "tnt_minecart", (new ItemMinecart(3)).c("minecartTnt").f("minecart_tnt"));
/* 196 */     REGISTRY.a(408, "hopper_minecart", (new ItemMinecart(5)).c("minecartHopper").f("minecart_hopper"));
/* 197 */     REGISTRY.a(417, "iron_horse_armor", (new Item()).c("horsearmormetal").e(1).a(CreativeModeTab.f).f("iron_horse_armor"));
/* 198 */     REGISTRY.a(418, "golden_horse_armor", (new Item()).c("horsearmorgold").e(1).a(CreativeModeTab.f).f("gold_horse_armor"));
/* 199 */     REGISTRY.a(419, "diamond_horse_armor", (new Item()).c("horsearmordiamond").e(1).a(CreativeModeTab.f).f("diamond_horse_armor"));
/* 200 */     REGISTRY.a(420, "lead", (new ItemLeash()).c("leash").f("lead"));
/* 201 */     REGISTRY.a(421, "name_tag", (new ItemNameTag()).c("nameTag").f("name_tag"));
/* 202 */     REGISTRY.a(422, "command_block_minecart", (new ItemMinecart(6)).c("minecartCommandBlock").f("minecart_command_block").a((CreativeModeTab)null));
/* 203 */     REGISTRY.a(2256, "record_13", (new ItemRecord("13")).c("record").f("record_13"));
/* 204 */     REGISTRY.a(2257, "record_cat", (new ItemRecord("cat")).c("record").f("record_cat"));
/* 205 */     REGISTRY.a(2258, "record_blocks", (new ItemRecord("blocks")).c("record").f("record_blocks"));
/* 206 */     REGISTRY.a(2259, "record_chirp", (new ItemRecord("chirp")).c("record").f("record_chirp"));
/* 207 */     REGISTRY.a(2260, "record_far", (new ItemRecord("far")).c("record").f("record_far"));
/* 208 */     REGISTRY.a(2261, "record_mall", (new ItemRecord("mall")).c("record").f("record_mall"));
/* 209 */     REGISTRY.a(2262, "record_mellohi", (new ItemRecord("mellohi")).c("record").f("record_mellohi"));
/* 210 */     REGISTRY.a(2263, "record_stal", (new ItemRecord("stal")).c("record").f("record_stal"));
/* 211 */     REGISTRY.a(2264, "record_strad", (new ItemRecord("strad")).c("record").f("record_strad"));
/* 212 */     REGISTRY.a(2265, "record_ward", (new ItemRecord("ward")).c("record").f("record_ward"));
/* 213 */     REGISTRY.a(2266, "record_11", (new ItemRecord("11")).c("record").f("record_11"));
/* 214 */     REGISTRY.a(2267, "record_wait", (new ItemRecord("wait")).c("record").f("record_wait"));
/* 215 */     HashSet hashset = Sets.newHashSet((Object[])new Block[] { Blocks.AIR, Blocks.BREWING_STAND, Blocks.BED, Blocks.NETHER_WART, Blocks.CAULDRON, Blocks.FLOWER_POT, Blocks.CROPS, Blocks.SUGAR_CANE_BLOCK, Blocks.CAKE_BLOCK, Blocks.SKULL, Blocks.PISTON_EXTENSION, Blocks.PISTON_MOVING, Blocks.GLOWING_REDSTONE_ORE, Blocks.DIODE_ON, Blocks.PUMPKIN_STEM, Blocks.SIGN_POST, Blocks.REDSTONE_COMPARATOR_ON, Blocks.TRIPWIRE, Blocks.REDSTONE_LAMP_ON, Blocks.MELON_STEM, Blocks.REDSTONE_TORCH_OFF, Blocks.REDSTONE_COMPARATOR_OFF, Blocks.REDSTONE_WIRE, Blocks.WALL_SIGN, Blocks.DIODE_OFF, Blocks.IRON_DOOR_BLOCK, Blocks.WOODEN_DOOR });
/* 216 */     Iterator<String> iterator = Block.REGISTRY.keySet().iterator();
/*     */     
/* 218 */     while (iterator.hasNext()) {
/* 219 */       Object object; String s = iterator.next();
/* 220 */       Block block = (Block)Block.REGISTRY.get(s);
/*     */ 
/*     */       
/* 223 */       if (block == Blocks.WOOL) {
/* 224 */         object = (new ItemCloth(Blocks.WOOL)).b("cloth");
/* 225 */       } else if (block == Blocks.STAINED_HARDENED_CLAY) {
/* 226 */         object = (new ItemCloth(Blocks.STAINED_HARDENED_CLAY)).b("clayHardenedStained");
/* 227 */       } else if (block == Blocks.STAINED_GLASS) {
/* 228 */         object = (new ItemCloth(Blocks.STAINED_GLASS)).b("stainedGlass");
/* 229 */       } else if (block == Blocks.STAINED_GLASS_PANE) {
/* 230 */         object = (new ItemCloth(Blocks.STAINED_GLASS_PANE)).b("stainedGlassPane");
/* 231 */       } else if (block == Blocks.WOOL_CARPET) {
/* 232 */         object = (new ItemCloth(Blocks.WOOL_CARPET)).b("woolCarpet");
/* 233 */       } else if (block == Blocks.DIRT) {
/* 234 */         object = (new ItemMultiTexture(Blocks.DIRT, Blocks.DIRT, BlockDirt.a)).b("dirt");
/* 235 */       } else if (block == Blocks.SAND) {
/* 236 */         object = (new ItemMultiTexture(Blocks.SAND, Blocks.SAND, BlockSand.a)).b("sand");
/* 237 */       } else if (block == Blocks.LOG) {
/* 238 */         object = (new ItemMultiTexture(Blocks.LOG, Blocks.LOG, BlockLog1.M)).b("log");
/* 239 */       } else if (block == Blocks.LOG2) {
/* 240 */         object = (new ItemMultiTexture(Blocks.LOG2, Blocks.LOG2, BlockLog2.M)).b("log");
/* 241 */       } else if (block == Blocks.WOOD) {
/* 242 */         object = (new ItemMultiTexture(Blocks.WOOD, Blocks.WOOD, BlockWood.a)).b("wood");
/* 243 */       } else if (block == Blocks.MONSTER_EGGS) {
/* 244 */         object = (new ItemMultiTexture(Blocks.MONSTER_EGGS, Blocks.MONSTER_EGGS, BlockMonsterEggs.a)).b("monsterStoneEgg");
/* 245 */       } else if (block == Blocks.SMOOTH_BRICK) {
/* 246 */         object = (new ItemMultiTexture(Blocks.SMOOTH_BRICK, Blocks.SMOOTH_BRICK, BlockSmoothBrick.a)).b("stonebricksmooth");
/* 247 */       } else if (block == Blocks.SANDSTONE) {
/* 248 */         object = (new ItemMultiTexture(Blocks.SANDSTONE, Blocks.SANDSTONE, BlockSandStone.a)).b("sandStone");
/* 249 */       } else if (block == Blocks.QUARTZ_BLOCK) {
/* 250 */         object = (new ItemMultiTexture(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, BlockQuartz.a)).b("quartzBlock");
/* 251 */       } else if (block == Blocks.STEP) {
/* 252 */         object = (new ItemStep(Blocks.STEP, Blocks.STEP, Blocks.DOUBLE_STEP, false)).b("stoneSlab");
/* 253 */       } else if (block == Blocks.DOUBLE_STEP) {
/* 254 */         object = (new ItemStep(Blocks.DOUBLE_STEP, Blocks.STEP, Blocks.DOUBLE_STEP, true)).b("stoneSlab");
/* 255 */       } else if (block == Blocks.WOOD_STEP) {
/* 256 */         object = (new ItemStep(Blocks.WOOD_STEP, Blocks.WOOD_STEP, Blocks.WOOD_DOUBLE_STEP, false)).b("woodSlab");
/* 257 */       } else if (block == Blocks.WOOD_DOUBLE_STEP) {
/* 258 */         object = (new ItemStep(Blocks.WOOD_DOUBLE_STEP, Blocks.WOOD_STEP, Blocks.WOOD_DOUBLE_STEP, true)).b("woodSlab");
/* 259 */       } else if (block == Blocks.SAPLING) {
/* 260 */         object = (new ItemMultiTexture(Blocks.SAPLING, Blocks.SAPLING, BlockSapling.a)).b("sapling");
/* 261 */       } else if (block == Blocks.LEAVES) {
/* 262 */         object = (new ItemLeaves(Blocks.LEAVES)).b("leaves");
/* 263 */       } else if (block == Blocks.LEAVES2) {
/* 264 */         object = (new ItemLeaves(Blocks.LEAVES2)).b("leaves");
/* 265 */       } else if (block == Blocks.VINE) {
/* 266 */         object = new ItemWithAuxData(Blocks.VINE, false);
/* 267 */       } else if (block == Blocks.LONG_GRASS) {
/* 268 */         object = (new ItemWithAuxData(Blocks.LONG_GRASS, true)).a(new String[] { "shrub", "grass", "fern" });
/* 269 */       } else if (block == Blocks.YELLOW_FLOWER) {
/* 270 */         object = (new ItemMultiTexture(Blocks.YELLOW_FLOWER, Blocks.YELLOW_FLOWER, BlockFlowers.b)).b("flower");
/* 271 */       } else if (block == Blocks.RED_ROSE) {
/* 272 */         object = (new ItemMultiTexture(Blocks.RED_ROSE, Blocks.RED_ROSE, BlockFlowers.a)).b("rose");
/* 273 */       } else if (block == Blocks.SNOW) {
/* 274 */         object = new ItemSnow(Blocks.SNOW, Blocks.SNOW);
/* 275 */       } else if (block == Blocks.WATER_LILY) {
/* 276 */         object = new ItemWaterLily(Blocks.WATER_LILY);
/* 277 */       } else if (block == Blocks.PISTON) {
/* 278 */         object = new ItemPiston(Blocks.PISTON);
/* 279 */       } else if (block == Blocks.PISTON_STICKY) {
/* 280 */         object = new ItemPiston(Blocks.PISTON_STICKY);
/* 281 */       } else if (block == Blocks.COBBLE_WALL) {
/* 282 */         object = (new ItemMultiTexture(Blocks.COBBLE_WALL, Blocks.COBBLE_WALL, BlockCobbleWall.a)).b("cobbleWall");
/* 283 */       } else if (block == Blocks.ANVIL) {
/* 284 */         object = (new ItemAnvil(Blocks.ANVIL)).b("anvil");
/* 285 */       } else if (block == Blocks.DOUBLE_PLANT) {
/* 286 */         object = (new ItemTallPlant(Blocks.DOUBLE_PLANT, Blocks.DOUBLE_PLANT, BlockTallPlant.a)).b("doublePlant");
/*     */       }
/* 288 */       else if (block == Blocks.MOB_SPAWNER || block == Blocks.BIG_MUSHROOM_1 || block == Blocks.BIG_MUSHROOM_2) {
/* 289 */         object = new ItemWithAuxData(block, true);
/*     */       } else {
/*     */         
/* 292 */         if (hashset.contains(block)) {
/*     */           continue;
/*     */         }
/*     */         
/* 296 */         object = new ItemBlock(block);
/*     */       } 
/*     */       
/* 299 */       REGISTRY.a(Block.getId(block), s, object);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item e(int i) {
/* 304 */     this.maxStackSize = i;
/* 305 */     return this;
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/* 309 */     return false;
/*     */   }
/*     */   
/*     */   public float getDestroySpeed(ItemStack itemstack, Block block) {
/* 313 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 317 */     return itemstack;
/*     */   }
/*     */   
/*     */   public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 321 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 325 */     return this.maxStackSize;
/*     */   }
/*     */   
/*     */   public int filterData(int i) {
/* 329 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 333 */     return this.j;
/*     */   }
/*     */   
/*     */   protected Item a(boolean flag) {
/* 337 */     this.j = flag;
/* 338 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxDurability() {
/* 342 */     return this.durability;
/*     */   }
/*     */   
/*     */   protected Item setMaxDurability(int i) {
/* 346 */     this.durability = i;
/* 347 */     return this;
/*     */   }
/*     */   
/*     */   public boolean usesDurability() {
/* 351 */     return (this.durability > 0 && !this.j);
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
/* 355 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, World world, Block block, int i, int j, int k, EntityLiving entityliving) {
/* 359 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canDestroySpecialBlock(Block block) {
/* 363 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving) {
/* 367 */     return false;
/*     */   }
/*     */   
/*     */   public Item q() {
/* 371 */     this.i = true;
/* 372 */     return this;
/*     */   }
/*     */   
/*     */   public Item c(String s) {
/* 376 */     this.name = s;
/* 377 */     return this;
/*     */   }
/*     */   
/*     */   public String k(ItemStack itemstack) {
/* 381 */     String s = a(itemstack);
/*     */     
/* 383 */     return (s == null) ? "" : LocaleI18n.get(s);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 387 */     return "item." + this.name;
/*     */   }
/*     */   
/*     */   public String a(ItemStack itemstack) {
/* 391 */     return "item." + this.name;
/*     */   }
/*     */   
/*     */   public Item c(Item item) {
/* 395 */     this.craftingResult = item;
/* 396 */     return this;
/*     */   }
/*     */   
/*     */   public boolean l(ItemStack itemstack) {
/* 400 */     return true;
/*     */   }
/*     */   
/*     */   public boolean s() {
/* 404 */     return true;
/*     */   }
/*     */   
/*     */   public Item t() {
/* 408 */     return this.craftingResult;
/*     */   }
/*     */   
/*     */   public boolean u() {
/* 412 */     return (this.craftingResult != null);
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {}
/*     */   
/*     */   public void d(ItemStack itemstack, World world, EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean h() {
/* 420 */     return false;
/*     */   }
/*     */   
/*     */   public EnumAnimation d(ItemStack itemstack) {
/* 424 */     return EnumAnimation.NONE;
/*     */   }
/*     */   
/*     */   public int d_(ItemStack itemstack) {
/* 428 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, EntityHuman entityhuman, int i) {}
/*     */   
/*     */   protected Item e(String s) {
/* 434 */     this.d = s;
/* 435 */     return this;
/*     */   }
/*     */   
/*     */   public String i(ItemStack itemstack) {
/* 439 */     return this.d;
/*     */   }
/*     */   
/*     */   public boolean m(ItemStack itemstack) {
/* 443 */     return (i(itemstack) != null);
/*     */   }
/*     */   
/*     */   public String n(ItemStack itemstack) {
/* 447 */     return ("" + LocaleI18n.get(k(itemstack) + ".name")).trim();
/*     */   }
/*     */   
/*     */   public EnumItemRarity f(ItemStack itemstack) {
/* 451 */     return itemstack.hasEnchantments() ? EnumItemRarity.RARE : EnumItemRarity.COMMON;
/*     */   }
/*     */   
/*     */   public boolean e_(ItemStack itemstack) {
/* 455 */     return (getMaxStackSize() == 1 && usesDurability());
/*     */   }
/*     */   
/*     */   protected MovingObjectPosition a(World world, EntityHuman entityhuman, boolean flag) {
/* 459 */     float f = 1.0F;
/* 460 */     float f1 = entityhuman.lastPitch + (entityhuman.pitch - entityhuman.lastPitch) * f;
/* 461 */     float f2 = entityhuman.lastYaw + (entityhuman.yaw - entityhuman.lastYaw) * f;
/* 462 */     double d0 = entityhuman.lastX + (entityhuman.locX - entityhuman.lastX) * f;
/* 463 */     double d1 = entityhuman.lastY + (entityhuman.locY - entityhuman.lastY) * f + 1.62D - entityhuman.height;
/* 464 */     double d2 = entityhuman.lastZ + (entityhuman.locZ - entityhuman.lastZ) * f;
/* 465 */     Vec3D vec3d = Vec3D.a(d0, d1, d2);
/* 466 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 467 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 468 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 469 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 470 */     float f7 = f4 * f5;
/* 471 */     float f8 = f3 * f5;
/* 472 */     double d3 = 5.0D;
/* 473 */     Vec3D vec3d1 = vec3d.add(f7 * d3, f6 * d3, f8 * d3);
/*     */     
/* 475 */     return world.rayTrace(vec3d, vec3d1, flag, !flag, false);
/*     */   }
/*     */   
/*     */   public int c() {
/* 479 */     return 0;
/*     */   }
/*     */   
/*     */   public Item a(CreativeModeTab creativemodetab) {
/* 483 */     this.a = creativemodetab;
/* 484 */     return this;
/*     */   }
/*     */   
/*     */   public boolean v() {
/* 488 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, ItemStack itemstack1) {
/* 492 */     return false;
/*     */   }
/*     */   
/*     */   public Multimap k() {
/* 496 */     return (Multimap)HashMultimap.create();
/*     */   }
/*     */   
/*     */   protected Item f(String s) {
/* 500 */     this.l = s;
/* 501 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */