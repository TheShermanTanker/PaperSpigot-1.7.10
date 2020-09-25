/*      */ package org.bukkit;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.material.Bed;
/*      */ import org.bukkit.material.Button;
/*      */ import org.bukkit.material.Cake;
/*      */ import org.bukkit.material.Cauldron;
/*      */ import org.bukkit.material.Chest;
/*      */ import org.bukkit.material.Coal;
/*      */ import org.bukkit.material.CocoaPlant;
/*      */ import org.bukkit.material.Command;
/*      */ import org.bukkit.material.Crops;
/*      */ import org.bukkit.material.DetectorRail;
/*      */ import org.bukkit.material.Diode;
/*      */ import org.bukkit.material.Dispenser;
/*      */ import org.bukkit.material.Door;
/*      */ import org.bukkit.material.Dye;
/*      */ import org.bukkit.material.EnderChest;
/*      */ import org.bukkit.material.FlowerPot;
/*      */ import org.bukkit.material.Furnace;
/*      */ import org.bukkit.material.Gate;
/*      */ import org.bukkit.material.Ladder;
/*      */ import org.bukkit.material.Lever;
/*      */ import org.bukkit.material.LongGrass;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.material.MonsterEggs;
/*      */ import org.bukkit.material.Mushroom;
/*      */ import org.bukkit.material.NetherWarts;
/*      */ import org.bukkit.material.PistonBaseMaterial;
/*      */ import org.bukkit.material.PistonExtensionMaterial;
/*      */ import org.bukkit.material.PoweredRail;
/*      */ import org.bukkit.material.PressurePlate;
/*      */ import org.bukkit.material.Pumpkin;
/*      */ import org.bukkit.material.Rails;
/*      */ import org.bukkit.material.RedstoneTorch;
/*      */ import org.bukkit.material.RedstoneWire;
/*      */ import org.bukkit.material.Sandstone;
/*      */ import org.bukkit.material.Sign;
/*      */ import org.bukkit.material.Skull;
/*      */ import org.bukkit.material.SmoothBrick;
/*      */ import org.bukkit.material.SpawnEgg;
/*      */ import org.bukkit.material.Stairs;
/*      */ import org.bukkit.material.Step;
/*      */ import org.bukkit.material.Torch;
/*      */ import org.bukkit.material.TrapDoor;
/*      */ import org.bukkit.material.Tree;
/*      */ import org.bukkit.material.Tripwire;
/*      */ import org.bukkit.material.TripwireHook;
/*      */ import org.bukkit.material.Vine;
/*      */ import org.bukkit.material.WoodenStep;
/*      */ import org.bukkit.material.Wool;
/*      */ import org.bukkit.util.Java15Compat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public enum Material
/*      */ {
/*   65 */   AIR(0, 0),
/*   66 */   STONE(1),
/*   67 */   GRASS(2),
/*   68 */   DIRT(3),
/*   69 */   COBBLESTONE(4),
/*   70 */   WOOD(5, (Class)Tree.class),
/*   71 */   SAPLING(6, (Class)Tree.class),
/*   72 */   BEDROCK(7),
/*   73 */   WATER(8, MaterialData.class),
/*   74 */   STATIONARY_WATER(9, MaterialData.class),
/*   75 */   LAVA(10, MaterialData.class),
/*   76 */   STATIONARY_LAVA(11, MaterialData.class),
/*   77 */   SAND(12),
/*   78 */   GRAVEL(13),
/*   79 */   GOLD_ORE(14),
/*   80 */   IRON_ORE(15),
/*   81 */   COAL_ORE(16),
/*   82 */   LOG(17, (Class)Tree.class),
/*   83 */   LEAVES(18, (Class)Tree.class),
/*   84 */   SPONGE(19),
/*   85 */   GLASS(20),
/*   86 */   LAPIS_ORE(21),
/*   87 */   LAPIS_BLOCK(22),
/*   88 */   DISPENSER(23, (Class)Dispenser.class),
/*   89 */   SANDSTONE(24, (Class)Sandstone.class),
/*   90 */   NOTE_BLOCK(25),
/*   91 */   BED_BLOCK(26, (Class)Bed.class),
/*   92 */   POWERED_RAIL(27, (Class)PoweredRail.class),
/*   93 */   DETECTOR_RAIL(28, (Class)DetectorRail.class),
/*   94 */   PISTON_STICKY_BASE(29, (Class)PistonBaseMaterial.class),
/*   95 */   WEB(30),
/*   96 */   LONG_GRASS(31, (Class)LongGrass.class),
/*   97 */   DEAD_BUSH(32),
/*   98 */   PISTON_BASE(33, (Class)PistonBaseMaterial.class),
/*   99 */   PISTON_EXTENSION(34, (Class)PistonExtensionMaterial.class),
/*  100 */   WOOL(35, (Class)Wool.class),
/*  101 */   PISTON_MOVING_PIECE(36),
/*  102 */   YELLOW_FLOWER(37),
/*  103 */   RED_ROSE(38),
/*  104 */   BROWN_MUSHROOM(39),
/*  105 */   RED_MUSHROOM(40),
/*  106 */   GOLD_BLOCK(41),
/*  107 */   IRON_BLOCK(42),
/*  108 */   DOUBLE_STEP(43, (Class)Step.class),
/*  109 */   STEP(44, (Class)Step.class),
/*  110 */   BRICK(45),
/*  111 */   TNT(46),
/*  112 */   BOOKSHELF(47),
/*  113 */   MOSSY_COBBLESTONE(48),
/*  114 */   OBSIDIAN(49),
/*  115 */   TORCH(50, (Class)Torch.class),
/*  116 */   FIRE(51),
/*  117 */   MOB_SPAWNER(52),
/*  118 */   WOOD_STAIRS(53, (Class)Stairs.class),
/*  119 */   CHEST(54, (Class)Chest.class),
/*  120 */   REDSTONE_WIRE(55, (Class)RedstoneWire.class),
/*  121 */   DIAMOND_ORE(56),
/*  122 */   DIAMOND_BLOCK(57),
/*  123 */   WORKBENCH(58),
/*  124 */   CROPS(59, (Class)Crops.class),
/*  125 */   SOIL(60, MaterialData.class),
/*  126 */   FURNACE(61, (Class)Furnace.class),
/*  127 */   BURNING_FURNACE(62, (Class)Furnace.class),
/*  128 */   SIGN_POST(63, 64, (Class)Sign.class),
/*  129 */   WOODEN_DOOR(64, (Class)Door.class),
/*  130 */   LADDER(65, (Class)Ladder.class),
/*  131 */   RAILS(66, (Class)Rails.class),
/*  132 */   COBBLESTONE_STAIRS(67, (Class)Stairs.class),
/*  133 */   WALL_SIGN(68, 64, (Class)Sign.class),
/*  134 */   LEVER(69, (Class)Lever.class),
/*  135 */   STONE_PLATE(70, (Class)PressurePlate.class),
/*  136 */   IRON_DOOR_BLOCK(71, (Class)Door.class),
/*  137 */   WOOD_PLATE(72, (Class)PressurePlate.class),
/*  138 */   REDSTONE_ORE(73),
/*  139 */   GLOWING_REDSTONE_ORE(74),
/*  140 */   REDSTONE_TORCH_OFF(75, (Class)RedstoneTorch.class),
/*  141 */   REDSTONE_TORCH_ON(76, (Class)RedstoneTorch.class),
/*  142 */   STONE_BUTTON(77, (Class)Button.class),
/*  143 */   SNOW(78),
/*  144 */   ICE(79),
/*  145 */   SNOW_BLOCK(80),
/*  146 */   CACTUS(81, MaterialData.class),
/*  147 */   CLAY(82),
/*  148 */   SUGAR_CANE_BLOCK(83, MaterialData.class),
/*  149 */   JUKEBOX(84),
/*  150 */   FENCE(85),
/*  151 */   PUMPKIN(86, (Class)Pumpkin.class),
/*  152 */   NETHERRACK(87),
/*  153 */   SOUL_SAND(88),
/*  154 */   GLOWSTONE(89),
/*  155 */   PORTAL(90),
/*  156 */   JACK_O_LANTERN(91, (Class)Pumpkin.class),
/*  157 */   CAKE_BLOCK(92, 64, (Class)Cake.class),
/*  158 */   DIODE_BLOCK_OFF(93, (Class)Diode.class),
/*  159 */   DIODE_BLOCK_ON(94, (Class)Diode.class),
/*  160 */   LOCKED_CHEST(95),
/*      */   
/*  162 */   STAINED_GLASS(95),
/*  163 */   TRAP_DOOR(96, (Class)TrapDoor.class),
/*  164 */   MONSTER_EGGS(97, (Class)MonsterEggs.class),
/*  165 */   SMOOTH_BRICK(98, (Class)SmoothBrick.class),
/*  166 */   HUGE_MUSHROOM_1(99, (Class)Mushroom.class),
/*  167 */   HUGE_MUSHROOM_2(100, (Class)Mushroom.class),
/*  168 */   IRON_FENCE(101),
/*  169 */   THIN_GLASS(102),
/*  170 */   MELON_BLOCK(103),
/*  171 */   PUMPKIN_STEM(104, MaterialData.class),
/*  172 */   MELON_STEM(105, MaterialData.class),
/*  173 */   VINE(106, (Class)Vine.class),
/*  174 */   FENCE_GATE(107, (Class)Gate.class),
/*  175 */   BRICK_STAIRS(108, (Class)Stairs.class),
/*  176 */   SMOOTH_STAIRS(109, (Class)Stairs.class),
/*  177 */   MYCEL(110),
/*  178 */   WATER_LILY(111),
/*  179 */   NETHER_BRICK(112),
/*  180 */   NETHER_FENCE(113),
/*  181 */   NETHER_BRICK_STAIRS(114, (Class)Stairs.class),
/*  182 */   NETHER_WARTS(115, (Class)NetherWarts.class),
/*  183 */   ENCHANTMENT_TABLE(116),
/*  184 */   BREWING_STAND(117, MaterialData.class),
/*  185 */   CAULDRON(118, (Class)Cauldron.class),
/*  186 */   ENDER_PORTAL(119),
/*  187 */   ENDER_PORTAL_FRAME(120),
/*  188 */   ENDER_STONE(121),
/*  189 */   DRAGON_EGG(122),
/*  190 */   REDSTONE_LAMP_OFF(123),
/*  191 */   REDSTONE_LAMP_ON(124),
/*  192 */   WOOD_DOUBLE_STEP(125, (Class)WoodenStep.class),
/*  193 */   WOOD_STEP(126, (Class)WoodenStep.class),
/*  194 */   COCOA(127, (Class)CocoaPlant.class),
/*  195 */   SANDSTONE_STAIRS(128, (Class)Stairs.class),
/*  196 */   EMERALD_ORE(129),
/*  197 */   ENDER_CHEST(130, (Class)EnderChest.class),
/*  198 */   TRIPWIRE_HOOK(131, (Class)TripwireHook.class),
/*  199 */   TRIPWIRE(132, (Class)Tripwire.class),
/*  200 */   EMERALD_BLOCK(133),
/*  201 */   SPRUCE_WOOD_STAIRS(134, (Class)Stairs.class),
/*  202 */   BIRCH_WOOD_STAIRS(135, (Class)Stairs.class),
/*  203 */   JUNGLE_WOOD_STAIRS(136, (Class)Stairs.class),
/*  204 */   COMMAND(137, (Class)Command.class),
/*  205 */   BEACON(138),
/*  206 */   COBBLE_WALL(139),
/*  207 */   FLOWER_POT(140, (Class)FlowerPot.class),
/*  208 */   CARROT(141),
/*  209 */   POTATO(142),
/*  210 */   WOOD_BUTTON(143, (Class)Button.class),
/*  211 */   SKULL(144, (Class)Skull.class),
/*  212 */   ANVIL(145),
/*  213 */   TRAPPED_CHEST(146),
/*  214 */   GOLD_PLATE(147),
/*  215 */   IRON_PLATE(148),
/*  216 */   REDSTONE_COMPARATOR_OFF(149),
/*  217 */   REDSTONE_COMPARATOR_ON(150),
/*  218 */   DAYLIGHT_DETECTOR(151),
/*  219 */   REDSTONE_BLOCK(152),
/*  220 */   QUARTZ_ORE(153),
/*  221 */   HOPPER(154),
/*  222 */   QUARTZ_BLOCK(155),
/*  223 */   QUARTZ_STAIRS(156, (Class)Stairs.class),
/*  224 */   ACTIVATOR_RAIL(157, (Class)PoweredRail.class),
/*  225 */   DROPPER(158, (Class)Dispenser.class),
/*  226 */   STAINED_CLAY(159),
/*  227 */   STAINED_GLASS_PANE(160),
/*  228 */   LEAVES_2(161),
/*  229 */   LOG_2(162),
/*  230 */   ACACIA_STAIRS(163, (Class)Stairs.class),
/*  231 */   DARK_OAK_STAIRS(164, (Class)Stairs.class),
/*  232 */   HAY_BLOCK(170),
/*  233 */   CARPET(171),
/*  234 */   HARD_CLAY(172),
/*  235 */   COAL_BLOCK(173),
/*  236 */   PACKED_ICE(174),
/*  237 */   DOUBLE_PLANT(175),
/*      */   
/*  239 */   IRON_SPADE(256, 1, 250),
/*  240 */   IRON_PICKAXE(257, 1, 250),
/*  241 */   IRON_AXE(258, 1, 250),
/*  242 */   FLINT_AND_STEEL(259, 1, 64),
/*  243 */   APPLE(260),
/*  244 */   BOW(261, 1, 384),
/*  245 */   ARROW(262),
/*  246 */   COAL(263, (Class)Coal.class),
/*  247 */   DIAMOND(264),
/*  248 */   IRON_INGOT(265),
/*  249 */   GOLD_INGOT(266),
/*  250 */   IRON_SWORD(267, 1, 250),
/*  251 */   WOOD_SWORD(268, 1, 59),
/*  252 */   WOOD_SPADE(269, 1, 59),
/*  253 */   WOOD_PICKAXE(270, 1, 59),
/*  254 */   WOOD_AXE(271, 1, 59),
/*  255 */   STONE_SWORD(272, 1, 131),
/*  256 */   STONE_SPADE(273, 1, 131),
/*  257 */   STONE_PICKAXE(274, 1, 131),
/*  258 */   STONE_AXE(275, 1, 131),
/*  259 */   DIAMOND_SWORD(276, 1, 1561),
/*  260 */   DIAMOND_SPADE(277, 1, 1561),
/*  261 */   DIAMOND_PICKAXE(278, 1, 1561),
/*  262 */   DIAMOND_AXE(279, 1, 1561),
/*  263 */   STICK(280),
/*  264 */   BOWL(281),
/*  265 */   MUSHROOM_SOUP(282, 1),
/*  266 */   GOLD_SWORD(283, 1, 32),
/*  267 */   GOLD_SPADE(284, 1, 32),
/*  268 */   GOLD_PICKAXE(285, 1, 32),
/*  269 */   GOLD_AXE(286, 1, 32),
/*  270 */   STRING(287),
/*  271 */   FEATHER(288),
/*  272 */   SULPHUR(289),
/*  273 */   WOOD_HOE(290, 1, 59),
/*  274 */   STONE_HOE(291, 1, 131),
/*  275 */   IRON_HOE(292, 1, 250),
/*  276 */   DIAMOND_HOE(293, 1, 1561),
/*  277 */   GOLD_HOE(294, 1, 32),
/*  278 */   SEEDS(295),
/*  279 */   WHEAT(296),
/*  280 */   BREAD(297),
/*  281 */   LEATHER_HELMET(298, 1, 55),
/*  282 */   LEATHER_CHESTPLATE(299, 1, 80),
/*  283 */   LEATHER_LEGGINGS(300, 1, 75),
/*  284 */   LEATHER_BOOTS(301, 1, 65),
/*  285 */   CHAINMAIL_HELMET(302, 1, 165),
/*  286 */   CHAINMAIL_CHESTPLATE(303, 1, 240),
/*  287 */   CHAINMAIL_LEGGINGS(304, 1, 225),
/*  288 */   CHAINMAIL_BOOTS(305, 1, 195),
/*  289 */   IRON_HELMET(306, 1, 165),
/*  290 */   IRON_CHESTPLATE(307, 1, 240),
/*  291 */   IRON_LEGGINGS(308, 1, 225),
/*  292 */   IRON_BOOTS(309, 1, 195),
/*  293 */   DIAMOND_HELMET(310, 1, 363),
/*  294 */   DIAMOND_CHESTPLATE(311, 1, 528),
/*  295 */   DIAMOND_LEGGINGS(312, 1, 495),
/*  296 */   DIAMOND_BOOTS(313, 1, 429),
/*  297 */   GOLD_HELMET(314, 1, 77),
/*  298 */   GOLD_CHESTPLATE(315, 1, 112),
/*  299 */   GOLD_LEGGINGS(316, 1, 105),
/*  300 */   GOLD_BOOTS(317, 1, 91),
/*  301 */   FLINT(318),
/*  302 */   PORK(319),
/*  303 */   GRILLED_PORK(320),
/*  304 */   PAINTING(321),
/*  305 */   GOLDEN_APPLE(322),
/*  306 */   SIGN(323, 16),
/*  307 */   WOOD_DOOR(324, 1),
/*  308 */   BUCKET(325, 16),
/*  309 */   WATER_BUCKET(326, 1),
/*  310 */   LAVA_BUCKET(327, 1),
/*  311 */   MINECART(328, 1),
/*  312 */   SADDLE(329, 1),
/*  313 */   IRON_DOOR(330, 1),
/*  314 */   REDSTONE(331),
/*  315 */   SNOW_BALL(332, 16),
/*  316 */   BOAT(333, 1),
/*  317 */   LEATHER(334),
/*  318 */   MILK_BUCKET(335, 1),
/*  319 */   CLAY_BRICK(336),
/*  320 */   CLAY_BALL(337),
/*  321 */   SUGAR_CANE(338),
/*  322 */   PAPER(339),
/*  323 */   BOOK(340),
/*  324 */   SLIME_BALL(341),
/*  325 */   STORAGE_MINECART(342, 1),
/*  326 */   POWERED_MINECART(343, 1),
/*  327 */   EGG(344, 16),
/*  328 */   COMPASS(345),
/*  329 */   FISHING_ROD(346, 1, 64),
/*  330 */   WATCH(347),
/*  331 */   GLOWSTONE_DUST(348),
/*  332 */   RAW_FISH(349),
/*  333 */   COOKED_FISH(350),
/*  334 */   INK_SACK(351, (Class)Dye.class),
/*  335 */   BONE(352),
/*  336 */   SUGAR(353),
/*  337 */   CAKE(354, 1),
/*  338 */   BED(355, 1),
/*  339 */   DIODE(356),
/*  340 */   COOKIE(357),
/*      */ 
/*      */ 
/*      */   
/*  344 */   MAP(358, MaterialData.class),
/*  345 */   SHEARS(359, 1, 238),
/*  346 */   MELON(360),
/*  347 */   PUMPKIN_SEEDS(361),
/*  348 */   MELON_SEEDS(362),
/*  349 */   RAW_BEEF(363),
/*  350 */   COOKED_BEEF(364),
/*  351 */   RAW_CHICKEN(365),
/*  352 */   COOKED_CHICKEN(366),
/*  353 */   ROTTEN_FLESH(367),
/*  354 */   ENDER_PEARL(368, 16),
/*  355 */   BLAZE_ROD(369),
/*  356 */   GHAST_TEAR(370),
/*  357 */   GOLD_NUGGET(371),
/*  358 */   NETHER_STALK(372),
/*      */ 
/*      */ 
/*      */   
/*  362 */   POTION(373, 1, MaterialData.class),
/*  363 */   GLASS_BOTTLE(374),
/*  364 */   SPIDER_EYE(375),
/*  365 */   FERMENTED_SPIDER_EYE(376),
/*  366 */   BLAZE_POWDER(377),
/*  367 */   MAGMA_CREAM(378),
/*  368 */   BREWING_STAND_ITEM(379),
/*  369 */   CAULDRON_ITEM(380),
/*  370 */   EYE_OF_ENDER(381),
/*  371 */   SPECKLED_MELON(382),
/*  372 */   MONSTER_EGG(383, 64, (Class)SpawnEgg.class),
/*  373 */   EXP_BOTTLE(384, 64),
/*  374 */   FIREBALL(385, 64),
/*  375 */   BOOK_AND_QUILL(386, 1),
/*  376 */   WRITTEN_BOOK(387, 16),
/*  377 */   EMERALD(388, 64),
/*  378 */   ITEM_FRAME(389),
/*  379 */   FLOWER_POT_ITEM(390),
/*  380 */   CARROT_ITEM(391),
/*  381 */   POTATO_ITEM(392),
/*  382 */   BAKED_POTATO(393),
/*  383 */   POISONOUS_POTATO(394),
/*  384 */   EMPTY_MAP(395),
/*  385 */   GOLDEN_CARROT(396),
/*  386 */   SKULL_ITEM(397),
/*  387 */   CARROT_STICK(398, 1, 25),
/*  388 */   NETHER_STAR(399),
/*  389 */   PUMPKIN_PIE(400),
/*  390 */   FIREWORK(401),
/*  391 */   FIREWORK_CHARGE(402),
/*  392 */   ENCHANTED_BOOK(403, 1),
/*  393 */   REDSTONE_COMPARATOR(404),
/*  394 */   NETHER_BRICK_ITEM(405),
/*  395 */   QUARTZ(406),
/*  396 */   EXPLOSIVE_MINECART(407, 1),
/*  397 */   HOPPER_MINECART(408, 1),
/*  398 */   IRON_BARDING(417, 1),
/*  399 */   GOLD_BARDING(418, 1),
/*  400 */   DIAMOND_BARDING(419, 1),
/*  401 */   LEASH(420),
/*  402 */   NAME_TAG(421),
/*  403 */   COMMAND_MINECART(422, 1),
/*  404 */   GOLD_RECORD(2256, 1),
/*  405 */   GREEN_RECORD(2257, 1),
/*  406 */   RECORD_3(2258, 1),
/*  407 */   RECORD_4(2259, 1),
/*  408 */   RECORD_5(2260, 1),
/*  409 */   RECORD_6(2261, 1),
/*  410 */   RECORD_7(2262, 1),
/*  411 */   RECORD_8(2263, 1),
/*  412 */   RECORD_9(2264, 1),
/*  413 */   RECORD_10(2265, 1),
/*  414 */   RECORD_11(2266, 1),
/*  415 */   RECORD_12(2267, 1); private final int id; private final Constructor<? extends MaterialData> ctor; private static Material[] byId;
/*      */   private static final Map<String, Material> BY_NAME;
/*      */   private final int maxStack;
/*      */   private final short durability;
/*      */   
/*  420 */   static { byId = new Material[383];
/*  421 */     BY_NAME = Maps.newHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  628 */     for (Material material : values())
/*  629 */     { if (byId.length > material.id) {
/*  630 */         byId[material.id] = material;
/*      */       } else {
/*  632 */         byId = (Material[])Java15Compat.Arrays_copyOfRange((Object[])byId, 0, material.id + 2);
/*  633 */         byId[material.id] = material;
/*      */       } 
/*  635 */       BY_NAME.put(material.name(), material); }  } Material(int id, int stack, int durability, Class<? extends MaterialData> data) { this.id = id; this.durability = (short)durability; this.maxStack = stack; try { this.ctor = data.getConstructor(new Class[] { int.class, byte.class }); }
/*      */     catch (NoSuchMethodException ex) { throw new AssertionError(ex); }
/*      */     catch (SecurityException ex) { throw new AssertionError(ex); }
/*      */      }
/*      */   @Deprecated public int getId() { return this.id; }
/*      */   public int getMaxStackSize() { return this.maxStack; }
/*      */   public short getMaxDurability() { return this.durability; }
/*      */   public Class<? extends MaterialData> getData() { return this.ctor.getDeclaringClass(); }
/*  643 */   public boolean isRecord() { return (this.id >= GOLD_RECORD.id && this.id <= RECORD_12.id); }
/*      */   @Deprecated public MaterialData getNewData(byte raw) { try {
/*      */       return this.ctor.newInstance(new Object[] { Integer.valueOf(this.id), Byte.valueOf(raw) });
/*      */     } catch (InstantiationException ex) {
/*      */       Throwable t = ex.getCause(); if (t instanceof RuntimeException)
/*      */         throw (RuntimeException)t;  if (t instanceof Error)
/*      */         throw (Error)t; 
/*      */       throw new AssertionError(t);
/*      */     } catch (Throwable t) {
/*      */       throw new AssertionError(t);
/*  653 */     }  } public boolean isSolid() { if (!isBlock() || this.id == 0) {
/*  654 */       return false;
/*      */     }
/*  656 */     switch (this) {
/*      */       case STONE:
/*      */       case GRASS:
/*      */       case DIRT:
/*      */       case COBBLESTONE:
/*      */       case WOOD:
/*      */       case BEDROCK:
/*      */       case SAND:
/*      */       case GRAVEL:
/*      */       case GOLD_ORE:
/*      */       case IRON_ORE:
/*      */       case COAL_ORE:
/*      */       case LOG:
/*      */       case LEAVES:
/*      */       case SPONGE:
/*      */       case GLASS:
/*      */       case LAPIS_ORE:
/*      */       case LAPIS_BLOCK:
/*      */       case DISPENSER:
/*      */       case SANDSTONE:
/*      */       case NOTE_BLOCK:
/*      */       case BED_BLOCK:
/*      */       case PISTON_STICKY_BASE:
/*      */       case PISTON_BASE:
/*      */       case PISTON_EXTENSION:
/*      */       case WOOL:
/*      */       case PISTON_MOVING_PIECE:
/*      */       case GOLD_BLOCK:
/*      */       case IRON_BLOCK:
/*      */       case DOUBLE_STEP:
/*      */       case STEP:
/*      */       case BRICK:
/*      */       case TNT:
/*      */       case BOOKSHELF:
/*      */       case MOSSY_COBBLESTONE:
/*      */       case OBSIDIAN:
/*      */       case MOB_SPAWNER:
/*      */       case WOOD_STAIRS:
/*      */       case CHEST:
/*      */       case DIAMOND_ORE:
/*      */       case DIAMOND_BLOCK:
/*      */       case WORKBENCH:
/*      */       case SOIL:
/*      */       case FURNACE:
/*      */       case BURNING_FURNACE:
/*      */       case SIGN_POST:
/*      */       case WOODEN_DOOR:
/*      */       case COBBLESTONE_STAIRS:
/*      */       case WALL_SIGN:
/*      */       case STONE_PLATE:
/*      */       case IRON_DOOR_BLOCK:
/*      */       case WOOD_PLATE:
/*      */       case REDSTONE_ORE:
/*      */       case GLOWING_REDSTONE_ORE:
/*      */       case ICE:
/*      */       case SNOW_BLOCK:
/*      */       case CACTUS:
/*      */       case CLAY:
/*      */       case JUKEBOX:
/*      */       case FENCE:
/*      */       case PUMPKIN:
/*      */       case NETHERRACK:
/*      */       case SOUL_SAND:
/*      */       case GLOWSTONE:
/*      */       case JACK_O_LANTERN:
/*      */       case CAKE_BLOCK:
/*      */       case LOCKED_CHEST:
/*      */       case STAINED_GLASS:
/*      */       case TRAP_DOOR:
/*      */       case MONSTER_EGGS:
/*      */       case SMOOTH_BRICK:
/*      */       case HUGE_MUSHROOM_1:
/*      */       case HUGE_MUSHROOM_2:
/*      */       case IRON_FENCE:
/*      */       case THIN_GLASS:
/*      */       case MELON_BLOCK:
/*      */       case FENCE_GATE:
/*      */       case BRICK_STAIRS:
/*      */       case SMOOTH_STAIRS:
/*      */       case MYCEL:
/*      */       case NETHER_BRICK:
/*      */       case NETHER_FENCE:
/*      */       case NETHER_BRICK_STAIRS:
/*      */       case ENCHANTMENT_TABLE:
/*      */       case BREWING_STAND:
/*      */       case CAULDRON:
/*      */       case ENDER_PORTAL_FRAME:
/*      */       case ENDER_STONE:
/*      */       case DRAGON_EGG:
/*      */       case REDSTONE_LAMP_OFF:
/*      */       case REDSTONE_LAMP_ON:
/*      */       case WOOD_DOUBLE_STEP:
/*      */       case WOOD_STEP:
/*      */       case SANDSTONE_STAIRS:
/*      */       case EMERALD_ORE:
/*      */       case ENDER_CHEST:
/*      */       case EMERALD_BLOCK:
/*      */       case SPRUCE_WOOD_STAIRS:
/*      */       case BIRCH_WOOD_STAIRS:
/*      */       case JUNGLE_WOOD_STAIRS:
/*      */       case COMMAND:
/*      */       case BEACON:
/*      */       case COBBLE_WALL:
/*      */       case ANVIL:
/*      */       case TRAPPED_CHEST:
/*      */       case GOLD_PLATE:
/*      */       case IRON_PLATE:
/*      */       case DAYLIGHT_DETECTOR:
/*      */       case REDSTONE_BLOCK:
/*      */       case QUARTZ_ORE:
/*      */       case HOPPER:
/*      */       case QUARTZ_BLOCK:
/*      */       case QUARTZ_STAIRS:
/*      */       case DROPPER:
/*      */       case STAINED_CLAY:
/*      */       case HAY_BLOCK:
/*      */       case HARD_CLAY:
/*      */       case COAL_BLOCK:
/*      */       case STAINED_GLASS_PANE:
/*      */       case LEAVES_2:
/*      */       case LOG_2:
/*      */       case ACACIA_STAIRS:
/*      */       case DARK_OAK_STAIRS:
/*      */       case PACKED_ICE:
/*  780 */         return true;
/*      */     } 
/*  782 */     return false; }
/*      */   public boolean isBlock() { return (this.id < 256); }
/*      */   public boolean isEdible() { switch (this) { case BREAD: case CARROT_ITEM: case BAKED_POTATO: case POTATO_ITEM: case POISONOUS_POTATO: case GOLDEN_CARROT: case PUMPKIN_PIE: case COOKIE: case MELON: case MUSHROOM_SOUP: case RAW_CHICKEN: case COOKED_CHICKEN: case RAW_BEEF: case COOKED_BEEF: case RAW_FISH: case COOKED_FISH: case PORK: case GRILLED_PORK: case APPLE:
/*      */       case GOLDEN_APPLE:
/*      */       case ROTTEN_FLESH:
/*      */       case SPIDER_EYE:
/*      */         return true; }  return false; }
/*      */   @Deprecated public static Material getMaterial(int id) { if (byId.length > id && id >= 0)
/*      */       return byId[id];  return null; }
/*      */   public static Material getMaterial(String name) { return BY_NAME.get(name); }
/*  792 */   public static Material matchMaterial(String name) { Validate.notNull(name, "Name cannot be null"); Material result = null; try { result = getMaterial(Integer.parseInt(name)); } catch (NumberFormatException ex) {} if (result == null) { String filtered = name.toUpperCase(); filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", ""); result = BY_NAME.get(filtered); }  return result; } public boolean isTransparent() { if (!isBlock()) {
/*  793 */       return false;
/*      */     }
/*  795 */     switch (this) {
/*      */       case AIR:
/*      */       case SAPLING:
/*      */       case POWERED_RAIL:
/*      */       case DETECTOR_RAIL:
/*      */       case LONG_GRASS:
/*      */       case DEAD_BUSH:
/*      */       case YELLOW_FLOWER:
/*      */       case RED_ROSE:
/*      */       case BROWN_MUSHROOM:
/*      */       case RED_MUSHROOM:
/*      */       case TORCH:
/*      */       case FIRE:
/*      */       case REDSTONE_WIRE:
/*      */       case CROPS:
/*      */       case LADDER:
/*      */       case RAILS:
/*      */       case LEVER:
/*      */       case REDSTONE_TORCH_OFF:
/*      */       case REDSTONE_TORCH_ON:
/*      */       case STONE_BUTTON:
/*      */       case SNOW:
/*      */       case SUGAR_CANE_BLOCK:
/*      */       case PORTAL:
/*      */       case DIODE_BLOCK_OFF:
/*      */       case DIODE_BLOCK_ON:
/*      */       case PUMPKIN_STEM:
/*      */       case MELON_STEM:
/*      */       case VINE:
/*      */       case WATER_LILY:
/*      */       case NETHER_WARTS:
/*      */       case ENDER_PORTAL:
/*      */       case COCOA:
/*      */       case TRIPWIRE_HOOK:
/*      */       case TRIPWIRE:
/*      */       case FLOWER_POT:
/*      */       case CARROT:
/*      */       case POTATO:
/*      */       case WOOD_BUTTON:
/*      */       case SKULL:
/*      */       case REDSTONE_COMPARATOR_OFF:
/*      */       case REDSTONE_COMPARATOR_ON:
/*      */       case ACTIVATOR_RAIL:
/*      */       case CARPET:
/*      */       case DOUBLE_PLANT:
/*  840 */         return true;
/*      */     } 
/*  842 */     return false; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlammable() {
/*  852 */     if (!isBlock()) {
/*  853 */       return false;
/*      */     }
/*  855 */     switch (this) {
/*      */       case WOOD:
/*      */       case LOG:
/*      */       case LEAVES:
/*      */       case NOTE_BLOCK:
/*      */       case BED_BLOCK:
/*      */       case WOOL:
/*      */       case TNT:
/*      */       case BOOKSHELF:
/*      */       case WOOD_STAIRS:
/*      */       case CHEST:
/*      */       case WORKBENCH:
/*      */       case SIGN_POST:
/*      */       case WOODEN_DOOR:
/*      */       case WALL_SIGN:
/*      */       case WOOD_PLATE:
/*      */       case JUKEBOX:
/*      */       case FENCE:
/*      */       case TRAP_DOOR:
/*      */       case HUGE_MUSHROOM_1:
/*      */       case HUGE_MUSHROOM_2:
/*      */       case FENCE_GATE:
/*      */       case WOOD_DOUBLE_STEP:
/*      */       case WOOD_STEP:
/*      */       case SPRUCE_WOOD_STAIRS:
/*      */       case BIRCH_WOOD_STAIRS:
/*      */       case JUNGLE_WOOD_STAIRS:
/*      */       case TRAPPED_CHEST:
/*      */       case DAYLIGHT_DETECTOR:
/*      */       case LEAVES_2:
/*      */       case LOG_2:
/*      */       case ACACIA_STAIRS:
/*      */       case DARK_OAK_STAIRS:
/*      */       case LONG_GRASS:
/*      */       case DEAD_BUSH:
/*      */       case VINE:
/*      */       case CARPET:
/*  892 */         return true;
/*      */     } 
/*  894 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBurnable() {
/*  904 */     if (!isBlock()) {
/*  905 */       return false;
/*      */     }
/*  907 */     switch (this) {
/*      */       case WOOD:
/*      */       case LOG:
/*      */       case LEAVES:
/*      */       case WOOL:
/*      */       case TNT:
/*      */       case BOOKSHELF:
/*      */       case WOOD_STAIRS:
/*      */       case FENCE:
/*      */       case WOOD_DOUBLE_STEP:
/*      */       case WOOD_STEP:
/*      */       case SPRUCE_WOOD_STAIRS:
/*      */       case BIRCH_WOOD_STAIRS:
/*      */       case JUNGLE_WOOD_STAIRS:
/*      */       case HAY_BLOCK:
/*      */       case COAL_BLOCK:
/*      */       case LEAVES_2:
/*      */       case LOG_2:
/*      */       case LONG_GRASS:
/*      */       case YELLOW_FLOWER:
/*      */       case RED_ROSE:
/*      */       case VINE:
/*      */       case CARPET:
/*      */       case DOUBLE_PLANT:
/*  931 */         return true;
/*      */     } 
/*  933 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOccluding() {
/*  943 */     if (!isBlock()) {
/*  944 */       return false;
/*      */     }
/*  946 */     switch (this) {
/*      */       case STONE:
/*      */       case GRASS:
/*      */       case DIRT:
/*      */       case COBBLESTONE:
/*      */       case WOOD:
/*      */       case BEDROCK:
/*      */       case SAND:
/*      */       case GRAVEL:
/*      */       case GOLD_ORE:
/*      */       case IRON_ORE:
/*      */       case COAL_ORE:
/*      */       case LOG:
/*      */       case SPONGE:
/*      */       case LAPIS_ORE:
/*      */       case LAPIS_BLOCK:
/*      */       case DISPENSER:
/*      */       case SANDSTONE:
/*      */       case NOTE_BLOCK:
/*      */       case WOOL:
/*      */       case GOLD_BLOCK:
/*      */       case IRON_BLOCK:
/*      */       case DOUBLE_STEP:
/*      */       case BRICK:
/*      */       case BOOKSHELF:
/*      */       case MOSSY_COBBLESTONE:
/*      */       case OBSIDIAN:
/*      */       case MOB_SPAWNER:
/*      */       case DIAMOND_ORE:
/*      */       case DIAMOND_BLOCK:
/*      */       case WORKBENCH:
/*      */       case FURNACE:
/*      */       case BURNING_FURNACE:
/*      */       case REDSTONE_ORE:
/*      */       case GLOWING_REDSTONE_ORE:
/*      */       case SNOW_BLOCK:
/*      */       case CLAY:
/*      */       case JUKEBOX:
/*      */       case PUMPKIN:
/*      */       case NETHERRACK:
/*      */       case SOUL_SAND:
/*      */       case JACK_O_LANTERN:
/*      */       case MONSTER_EGGS:
/*      */       case SMOOTH_BRICK:
/*      */       case HUGE_MUSHROOM_1:
/*      */       case HUGE_MUSHROOM_2:
/*      */       case MELON_BLOCK:
/*      */       case MYCEL:
/*      */       case NETHER_BRICK:
/*      */       case ENDER_PORTAL_FRAME:
/*      */       case ENDER_STONE:
/*      */       case REDSTONE_LAMP_OFF:
/*      */       case REDSTONE_LAMP_ON:
/*      */       case WOOD_DOUBLE_STEP:
/*      */       case EMERALD_ORE:
/*      */       case EMERALD_BLOCK:
/*      */       case COMMAND:
/*      */       case QUARTZ_ORE:
/*      */       case QUARTZ_BLOCK:
/*      */       case DROPPER:
/*      */       case STAINED_CLAY:
/*      */       case HAY_BLOCK:
/*      */       case HARD_CLAY:
/*      */       case COAL_BLOCK:
/*      */       case LOG_2:
/*      */       case PACKED_ICE:
/* 1012 */         return true;
/*      */     } 
/* 1014 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasGravity() {
/* 1022 */     if (!isBlock()) {
/* 1023 */       return false;
/*      */     }
/* 1025 */     switch (this) {
/*      */       case SAND:
/*      */       case GRAVEL:
/*      */       case ANVIL:
/* 1029 */         return true;
/*      */     } 
/* 1031 */     return false;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Material.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */