/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ 
/*     */ public class Block {
/*   9 */   public static final RegistryMaterials REGISTRY = new RegistryBlocks("air");
/*     */   private CreativeModeTab creativeTab;
/*     */   protected String d;
/*  12 */   public static final StepSound e = new StepSound("stone", 1.0F, 1.0F);
/*  13 */   public static final StepSound f = new StepSound("wood", 1.0F, 1.0F);
/*  14 */   public static final StepSound g = new StepSound("gravel", 1.0F, 1.0F);
/*  15 */   public static final StepSound h = new StepSound("grass", 1.0F, 1.0F);
/*  16 */   public static final StepSound i = new StepSound("stone", 1.0F, 1.0F);
/*  17 */   public static final StepSound j = new StepSound("stone", 1.0F, 1.5F);
/*  18 */   public static final StepSound k = new StepSoundStone("stone", 1.0F, 1.0F);
/*  19 */   public static final StepSound l = new StepSound("cloth", 1.0F, 1.0F);
/*  20 */   public static final StepSound m = new StepSound("sand", 1.0F, 1.0F);
/*  21 */   public static final StepSound n = new StepSound("snow", 1.0F, 1.0F);
/*  22 */   public static final StepSound o = new StepSoundLadder("ladder", 1.0F, 1.0F);
/*  23 */   public static final StepSound p = new StepSoundAnvil("anvil", 0.3F, 1.0F);
/*     */   protected boolean q;
/*     */   protected int r;
/*     */   protected boolean s;
/*     */   protected int t;
/*     */   protected boolean u;
/*     */   protected float strength;
/*     */   protected float durability;
/*     */   protected boolean x = true;
/*     */   protected boolean y = true;
/*     */   protected boolean z;
/*     */   protected boolean isTileEntity;
/*     */   protected double minX;
/*     */   protected double minY;
/*     */   protected double minZ;
/*     */   protected double maxX;
/*     */   protected double maxY;
/*     */   protected double maxZ;
/*     */   public StepSound stepSound;
/*     */   public float I;
/*     */   protected final Material material;
/*     */   public float frictionFactor;
/*     */   private String name;
/*     */   
/*     */   public static int getId(Block block) {
/*  48 */     return REGISTRY.b(block);
/*     */   }
/*     */   
/*     */   public static Block getById(int i) {
/*  52 */     return (Block)REGISTRY.a(i);
/*     */   }
/*     */   
/*     */   public static Block a(Item item) {
/*  56 */     return getById(Item.getId(item));
/*     */   }
/*     */   
/*     */   public static Block b(String s) {
/*  60 */     if (REGISTRY.b(s)) {
/*  61 */       return (Block)REGISTRY.get(s);
/*     */     }
/*     */     try {
/*  64 */       return (Block)REGISTRY.a(Integer.parseInt(s));
/*  65 */     } catch (NumberFormatException numberformatexception) {
/*  66 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean j() {
/*  72 */     return this.q;
/*     */   }
/*     */   
/*     */   public int k() {
/*  76 */     return this.r;
/*     */   }
/*     */   
/*     */   public int m() {
/*  80 */     return this.t;
/*     */   }
/*     */   
/*     */   public boolean n() {
/*  84 */     return this.u;
/*     */   }
/*     */   
/*     */   public Material getMaterial() {
/*  88 */     return this.material;
/*     */   }
/*     */   
/*     */   public MaterialMapColor f(int i) {
/*  92 */     return getMaterial().r();
/*     */   }
/*     */   
/*     */   public static void p() {
/*  96 */     REGISTRY.a(0, "air", (new BlockAir()).c("air"));
/*  97 */     REGISTRY.a(1, "stone", (new BlockStone()).c(1.5F).b(10.0F).a(i).c("stone").d("stone"));
/*  98 */     REGISTRY.a(2, "grass", (new BlockGrass()).c(0.6F).a(h).c("grass").d("grass"));
/*  99 */     REGISTRY.a(3, "dirt", (new BlockDirt()).c(0.5F).a(g).c("dirt").d("dirt"));
/* 100 */     Block block = (new Block(Material.STONE)).c(2.0F).b(10.0F).a(i).c("stonebrick").a(CreativeModeTab.b).d("cobblestone");
/*     */     
/* 102 */     REGISTRY.a(4, "cobblestone", block);
/* 103 */     Block block1 = (new BlockWood()).c(2.0F).b(5.0F).a(f).c("wood").d("planks");
/*     */     
/* 105 */     REGISTRY.a(5, "planks", block1);
/* 106 */     REGISTRY.a(6, "sapling", (new BlockSapling()).c(0.0F).a(h).c("sapling").d("sapling"));
/* 107 */     REGISTRY.a(7, "bedrock", (new Block(Material.STONE)).s().b(6000000.0F).a(i).c("bedrock").H().a(CreativeModeTab.b).d("bedrock"));
/* 108 */     REGISTRY.a(8, "flowing_water", (new BlockFlowing(Material.WATER)).c(100.0F).g(3).c("water").H().d("water_flow"));
/* 109 */     REGISTRY.a(9, "water", (new BlockStationary(Material.WATER)).c(100.0F).g(3).c("water").H().d("water_still"));
/* 110 */     REGISTRY.a(10, "flowing_lava", (new BlockFlowing(Material.LAVA)).c(100.0F).a(1.0F).c("lava").H().d("lava_flow"));
/* 111 */     REGISTRY.a(11, "lava", (new BlockStationary(Material.LAVA)).c(100.0F).a(1.0F).c("lava").H().d("lava_still"));
/* 112 */     REGISTRY.a(12, "sand", (new BlockSand()).c(0.5F).a(m).c("sand").d("sand"));
/* 113 */     REGISTRY.a(13, "gravel", (new BlockGravel()).c(0.6F).a(g).c("gravel").d("gravel"));
/* 114 */     REGISTRY.a(14, "gold_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreGold").d("gold_ore"));
/* 115 */     REGISTRY.a(15, "iron_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreIron").d("iron_ore"));
/* 116 */     REGISTRY.a(16, "coal_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreCoal").d("coal_ore"));
/* 117 */     REGISTRY.a(17, "log", (new BlockLog1()).c("log").d("log"));
/* 118 */     REGISTRY.a(18, "leaves", (new BlockLeaves1()).c("leaves").d("leaves"));
/* 119 */     REGISTRY.a(19, "sponge", (new BlockSponge()).c(0.6F).a(h).c("sponge").d("sponge"));
/* 120 */     REGISTRY.a(20, "glass", (new BlockGlass(Material.SHATTERABLE, false)).c(0.3F).a(k).c("glass").d("glass"));
/* 121 */     REGISTRY.a(21, "lapis_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreLapis").d("lapis_ore"));
/* 122 */     REGISTRY.a(22, "lapis_block", (new BlockOreBlock(MaterialMapColor.H)).c(3.0F).b(5.0F).a(i).c("blockLapis").a(CreativeModeTab.b).d("lapis_block"));
/* 123 */     REGISTRY.a(23, "dispenser", (new BlockDispenser()).c(3.5F).a(i).c("dispenser").d("dispenser"));
/* 124 */     Block block2 = (new BlockSandStone()).a(i).c(0.8F).c("sandStone").d("sandstone");
/*     */     
/* 126 */     REGISTRY.a(24, "sandstone", block2);
/* 127 */     REGISTRY.a(25, "noteblock", (new BlockNote()).c(0.8F).c("musicBlock").d("noteblock"));
/* 128 */     REGISTRY.a(26, "bed", (new BlockBed()).c(0.2F).c("bed").H().d("bed"));
/* 129 */     REGISTRY.a(27, "golden_rail", (new BlockPoweredRail()).c(0.7F).a(j).c("goldenRail").d("rail_golden"));
/* 130 */     REGISTRY.a(28, "detector_rail", (new BlockMinecartDetector()).c(0.7F).a(j).c("detectorRail").d("rail_detector"));
/* 131 */     REGISTRY.a(29, "sticky_piston", (new BlockPiston(true)).c("pistonStickyBase"));
/* 132 */     REGISTRY.a(30, "web", (new BlockWeb()).g(1).c(4.0F).c("web").d("web"));
/* 133 */     REGISTRY.a(31, "tallgrass", (new BlockLongGrass()).c(0.0F).a(h).c("tallgrass"));
/* 134 */     REGISTRY.a(32, "deadbush", (new BlockDeadBush()).c(0.0F).a(h).c("deadbush").d("deadbush"));
/* 135 */     REGISTRY.a(33, "piston", (new BlockPiston(false)).c("pistonBase"));
/* 136 */     REGISTRY.a(34, "piston_head", new BlockPistonExtension());
/* 137 */     REGISTRY.a(35, "wool", (new BlockCloth(Material.CLOTH)).c(0.8F).a(l).c("cloth").d("wool_colored"));
/* 138 */     REGISTRY.a(36, "piston_extension", new BlockPistonMoving());
/* 139 */     REGISTRY.a(37, "yellow_flower", (new BlockFlowers(0)).c(0.0F).a(h).c("flower1").d("flower_dandelion"));
/* 140 */     REGISTRY.a(38, "red_flower", (new BlockFlowers(1)).c(0.0F).a(h).c("flower2").d("flower_rose"));
/* 141 */     REGISTRY.a(39, "brown_mushroom", (new BlockMushroom()).c(0.0F).a(h).a(0.125F).c("mushroom").d("mushroom_brown"));
/* 142 */     REGISTRY.a(40, "red_mushroom", (new BlockMushroom()).c(0.0F).a(h).c("mushroom").d("mushroom_red"));
/* 143 */     REGISTRY.a(41, "gold_block", (new BlockOreBlock(MaterialMapColor.F)).c(3.0F).b(10.0F).a(j).c("blockGold").d("gold_block"));
/* 144 */     REGISTRY.a(42, "iron_block", (new BlockOreBlock(MaterialMapColor.h)).c(5.0F).b(10.0F).a(j).c("blockIron").d("iron_block"));
/* 145 */     REGISTRY.a(43, "double_stone_slab", (new BlockStep(true)).c(2.0F).b(10.0F).a(i).c("stoneSlab"));
/* 146 */     REGISTRY.a(44, "stone_slab", (new BlockStep(false)).c(2.0F).b(10.0F).a(i).c("stoneSlab"));
/* 147 */     Block block3 = (new Block(Material.STONE)).c(2.0F).b(10.0F).a(i).c("brick").a(CreativeModeTab.b).d("brick");
/*     */     
/* 149 */     REGISTRY.a(45, "brick_block", block3);
/* 150 */     REGISTRY.a(46, "tnt", (new BlockTNT()).c(0.0F).a(h).c("tnt").d("tnt"));
/* 151 */     REGISTRY.a(47, "bookshelf", (new BlockBookshelf()).c(1.5F).a(f).c("bookshelf").d("bookshelf"));
/* 152 */     REGISTRY.a(48, "mossy_cobblestone", (new Block(Material.STONE)).c(2.0F).b(10.0F).a(i).c("stoneMoss").a(CreativeModeTab.b).d("cobblestone_mossy"));
/* 153 */     REGISTRY.a(49, "obsidian", (new BlockObsidian()).c(50.0F).b(2000.0F).a(i).c("obsidian").d("obsidian"));
/* 154 */     REGISTRY.a(50, "torch", (new BlockTorch()).c(0.0F).a(0.9375F).a(f).c("torch").d("torch_on"));
/* 155 */     REGISTRY.a(51, "fire", (new BlockFire()).c(0.0F).a(1.0F).a(f).c("fire").H().d("fire"));
/* 156 */     REGISTRY.a(52, "mob_spawner", (new BlockMobSpawner()).c(5.0F).a(j).c("mobSpawner").H().d("mob_spawner"));
/* 157 */     REGISTRY.a(53, "oak_stairs", (new BlockStairs(block1, 0)).c("stairsWood"));
/* 158 */     REGISTRY.a(54, "chest", (new BlockChest(0)).c(2.5F).a(f).c("chest"));
/* 159 */     REGISTRY.a(55, "redstone_wire", (new BlockRedstoneWire()).c(0.0F).a(e).c("redstoneDust").H().d("redstone_dust"));
/* 160 */     REGISTRY.a(56, "diamond_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreDiamond").d("diamond_ore"));
/* 161 */     REGISTRY.a(57, "diamond_block", (new BlockOreBlock(MaterialMapColor.G)).c(5.0F).b(10.0F).a(j).c("blockDiamond").d("diamond_block"));
/* 162 */     REGISTRY.a(58, "crafting_table", (new BlockWorkbench()).c(2.5F).a(f).c("workbench").d("crafting_table"));
/* 163 */     REGISTRY.a(59, "wheat", (new BlockCrops()).c("crops").d("wheat"));
/* 164 */     Block block4 = (new BlockSoil()).c(0.6F).a(g).c("farmland").d("farmland");
/*     */     
/* 166 */     REGISTRY.a(60, "farmland", block4);
/* 167 */     REGISTRY.a(61, "furnace", (new BlockFurnace(false)).c(3.5F).a(i).c("furnace").a(CreativeModeTab.c));
/* 168 */     REGISTRY.a(62, "lit_furnace", (new BlockFurnace(true)).c(3.5F).a(i).a(0.875F).c("furnace"));
/* 169 */     REGISTRY.a(63, "standing_sign", (new BlockSign(TileEntitySign.class, true)).c(1.0F).a(f).c("sign").H());
/* 170 */     REGISTRY.a(64, "wooden_door", (new BlockDoor(Material.WOOD)).c(3.0F).a(f).c("doorWood").H().d("door_wood"));
/* 171 */     REGISTRY.a(65, "ladder", (new BlockLadder()).c(0.4F).a(o).c("ladder").d("ladder"));
/* 172 */     REGISTRY.a(66, "rail", (new BlockMinecartTrack()).c(0.7F).a(j).c("rail").d("rail_normal"));
/* 173 */     REGISTRY.a(67, "stone_stairs", (new BlockStairs(block, 0)).c("stairsStone"));
/* 174 */     REGISTRY.a(68, "wall_sign", (new BlockSign(TileEntitySign.class, false)).c(1.0F).a(f).c("sign").H());
/* 175 */     REGISTRY.a(69, "lever", (new BlockLever()).c(0.5F).a(f).c("lever").d("lever"));
/* 176 */     REGISTRY.a(70, "stone_pressure_plate", (new BlockPressurePlateBinary("stone", Material.STONE, EnumMobType.MOBS)).c(0.5F).a(i).c("pressurePlate"));
/* 177 */     REGISTRY.a(71, "iron_door", (new BlockDoor(Material.ORE)).c(5.0F).a(j).c("doorIron").H().d("door_iron"));
/* 178 */     REGISTRY.a(72, "wooden_pressure_plate", (new BlockPressurePlateBinary("planks_oak", Material.WOOD, EnumMobType.EVERYTHING)).c(0.5F).a(f).c("pressurePlate"));
/* 179 */     REGISTRY.a(73, "redstone_ore", (new BlockRedstoneOre(false)).c(3.0F).b(5.0F).a(i).c("oreRedstone").a(CreativeModeTab.b).d("redstone_ore"));
/* 180 */     REGISTRY.a(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).a(0.625F).c(3.0F).b(5.0F).a(i).c("oreRedstone").d("redstone_ore"));
/* 181 */     REGISTRY.a(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).c(0.0F).a(f).c("notGate").d("redstone_torch_off"));
/* 182 */     REGISTRY.a(76, "redstone_torch", (new BlockRedstoneTorch(true)).c(0.0F).a(0.5F).a(f).c("notGate").a(CreativeModeTab.d).d("redstone_torch_on"));
/* 183 */     REGISTRY.a(77, "stone_button", (new BlockStoneButton()).c(0.5F).a(i).c("button"));
/* 184 */     REGISTRY.a(78, "snow_layer", (new BlockSnow()).c(0.1F).a(n).c("snow").g(0).d("snow"));
/* 185 */     REGISTRY.a(79, "ice", (new BlockIce()).c(0.5F).g(3).a(k).c("ice").d("ice"));
/* 186 */     REGISTRY.a(80, "snow", (new BlockSnowBlock()).c(0.2F).a(n).c("snow").d("snow"));
/* 187 */     REGISTRY.a(81, "cactus", (new BlockCactus()).c(0.4F).a(l).c("cactus").d("cactus"));
/* 188 */     REGISTRY.a(82, "clay", (new BlockClay()).c(0.6F).a(g).c("clay").d("clay"));
/* 189 */     REGISTRY.a(83, "reeds", (new BlockReed()).c(0.0F).a(h).c("reeds").H().d("reeds"));
/* 190 */     REGISTRY.a(84, "jukebox", (new BlockJukeBox()).c(2.0F).b(10.0F).a(i).c("jukebox").d("jukebox"));
/* 191 */     REGISTRY.a(85, "fence", (new BlockFence("planks_oak", Material.WOOD)).c(2.0F).b(5.0F).a(f).c("fence"));
/* 192 */     Block block5 = (new BlockPumpkin(false)).c(1.0F).a(f).c("pumpkin").d("pumpkin");
/*     */     
/* 194 */     REGISTRY.a(86, "pumpkin", block5);
/* 195 */     REGISTRY.a(87, "netherrack", (new BlockBloodStone()).c(0.4F).a(i).c("hellrock").d("netherrack"));
/* 196 */     REGISTRY.a(88, "soul_sand", (new BlockSlowSand()).c(0.5F).a(m).c("hellsand").d("soul_sand"));
/* 197 */     REGISTRY.a(89, "glowstone", (new BlockLightStone(Material.SHATTERABLE)).c(0.3F).a(k).a(1.0F).c("lightgem").d("glowstone"));
/* 198 */     REGISTRY.a(90, "portal", (new BlockPortal()).c(-1.0F).a(k).a(0.75F).c("portal").d("portal"));
/* 199 */     REGISTRY.a(91, "lit_pumpkin", (new BlockPumpkin(true)).c(1.0F).a(f).a(1.0F).c("litpumpkin").d("pumpkin"));
/* 200 */     REGISTRY.a(92, "cake", (new BlockCake()).c(0.5F).a(l).c("cake").H().d("cake"));
/* 201 */     REGISTRY.a(93, "unpowered_repeater", (new BlockRepeater(false)).c(0.0F).a(f).c("diode").H().d("repeater_off"));
/* 202 */     REGISTRY.a(94, "powered_repeater", (new BlockRepeater(true)).c(0.0F).a(0.625F).a(f).c("diode").H().d("repeater_on"));
/* 203 */     REGISTRY.a(95, "stained_glass", (new BlockStainedGlass(Material.SHATTERABLE)).c(0.3F).a(k).c("stainedGlass").d("glass"));
/* 204 */     REGISTRY.a(96, "trapdoor", (new BlockTrapdoor(Material.WOOD)).c(3.0F).a(f).c("trapdoor").H().d("trapdoor"));
/* 205 */     REGISTRY.a(97, "monster_egg", (new BlockMonsterEggs()).c(0.75F).c("monsterStoneEgg"));
/* 206 */     Block block6 = (new BlockSmoothBrick()).c(1.5F).b(10.0F).a(i).c("stonebricksmooth").d("stonebrick");
/*     */     
/* 208 */     REGISTRY.a(98, "stonebrick", block6);
/* 209 */     REGISTRY.a(99, "brown_mushroom_block", (new BlockHugeMushroom(Material.WOOD, 0)).c(0.2F).a(f).c("mushroom").d("mushroom_block"));
/* 210 */     REGISTRY.a(100, "red_mushroom_block", (new BlockHugeMushroom(Material.WOOD, 1)).c(0.2F).a(f).c("mushroom").d("mushroom_block"));
/* 211 */     REGISTRY.a(101, "iron_bars", (new BlockThin("iron_bars", "iron_bars", Material.ORE, true)).c(5.0F).b(10.0F).a(j).c("fenceIron"));
/* 212 */     REGISTRY.a(102, "glass_pane", (new BlockThin("glass", "glass_pane_top", Material.SHATTERABLE, false)).c(0.3F).a(k).c("thinGlass"));
/* 213 */     Block block7 = (new BlockMelon()).c(1.0F).a(f).c("melon").d("melon");
/*     */     
/* 215 */     REGISTRY.a(103, "melon_block", block7);
/* 216 */     REGISTRY.a(104, "pumpkin_stem", (new BlockStem(block5)).c(0.0F).a(f).c("pumpkinStem").d("pumpkin_stem"));
/* 217 */     REGISTRY.a(105, "melon_stem", (new BlockStem(block7)).c(0.0F).a(f).c("pumpkinStem").d("melon_stem"));
/* 218 */     REGISTRY.a(106, "vine", (new BlockVine()).c(0.2F).a(h).c("vine").d("vine"));
/* 219 */     REGISTRY.a(107, "fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("fenceGate"));
/* 220 */     REGISTRY.a(108, "brick_stairs", (new BlockStairs(block3, 0)).c("stairsBrick"));
/* 221 */     REGISTRY.a(109, "stone_brick_stairs", (new BlockStairs(block6, 0)).c("stairsStoneBrickSmooth"));
/* 222 */     REGISTRY.a(110, "mycelium", (new BlockMycel()).c(0.6F).a(h).c("mycel").d("mycelium"));
/* 223 */     REGISTRY.a(111, "waterlily", (new BlockWaterLily()).c(0.0F).a(h).c("waterlily").d("waterlily"));
/* 224 */     Block block8 = (new Block(Material.STONE)).c(2.0F).b(10.0F).a(i).c("netherBrick").a(CreativeModeTab.b).d("nether_brick");
/*     */     
/* 226 */     REGISTRY.a(112, "nether_brick", block8);
/* 227 */     REGISTRY.a(113, "nether_brick_fence", (new BlockFence("nether_brick", Material.STONE)).c(2.0F).b(10.0F).a(i).c("netherFence"));
/* 228 */     REGISTRY.a(114, "nether_brick_stairs", (new BlockStairs(block8, 0)).c("stairsNetherBrick"));
/* 229 */     REGISTRY.a(115, "nether_wart", (new BlockNetherWart()).c("netherStalk").d("nether_wart"));
/* 230 */     REGISTRY.a(116, "enchanting_table", (new BlockEnchantmentTable()).c(5.0F).b(2000.0F).c("enchantmentTable").d("enchanting_table"));
/* 231 */     REGISTRY.a(117, "brewing_stand", (new BlockBrewingStand()).c(0.5F).a(0.125F).c("brewingStand").d("brewing_stand"));
/* 232 */     REGISTRY.a(118, "cauldron", (new BlockCauldron()).c(2.0F).c("cauldron").d("cauldron"));
/* 233 */     REGISTRY.a(119, "end_portal", (new BlockEnderPortal(Material.PORTAL)).c(-1.0F).b(6000000.0F));
/* 234 */     REGISTRY.a(120, "end_portal_frame", (new BlockEnderPortalFrame()).a(k).a(0.125F).c(-1.0F).c("endPortalFrame").b(6000000.0F).a(CreativeModeTab.c).d("endframe"));
/* 235 */     REGISTRY.a(121, "end_stone", (new Block(Material.STONE)).c(3.0F).b(15.0F).a(i).c("whiteStone").a(CreativeModeTab.b).d("end_stone"));
/* 236 */     REGISTRY.a(122, "dragon_egg", (new BlockDragonEgg()).c(3.0F).b(15.0F).a(i).a(0.125F).c("dragonEgg").d("dragon_egg"));
/* 237 */     REGISTRY.a(123, "redstone_lamp", (new BlockRedstoneLamp(false)).c(0.3F).a(k).c("redstoneLight").a(CreativeModeTab.d).d("redstone_lamp_off"));
/* 238 */     REGISTRY.a(124, "lit_redstone_lamp", (new BlockRedstoneLamp(true)).c(0.3F).a(k).c("redstoneLight").d("redstone_lamp_on"));
/* 239 */     REGISTRY.a(125, "double_wooden_slab", (new BlockWoodStep(true)).c(2.0F).b(5.0F).a(f).c("woodSlab"));
/* 240 */     REGISTRY.a(126, "wooden_slab", (new BlockWoodStep(false)).c(2.0F).b(5.0F).a(f).c("woodSlab"));
/* 241 */     REGISTRY.a(127, "cocoa", (new BlockCocoa()).c(0.2F).b(5.0F).a(f).c("cocoa").d("cocoa"));
/* 242 */     REGISTRY.a(128, "sandstone_stairs", (new BlockStairs(block2, 0)).c("stairsSandStone"));
/* 243 */     REGISTRY.a(129, "emerald_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreEmerald").d("emerald_ore"));
/* 244 */     REGISTRY.a(130, "ender_chest", (new BlockEnderChest()).c(22.5F).b(1000.0F).a(i).c("enderChest").a(0.5F));
/* 245 */     REGISTRY.a(131, "tripwire_hook", (new BlockTripwireHook()).c("tripWireSource").d("trip_wire_source"));
/* 246 */     REGISTRY.a(132, "tripwire", (new BlockTripwire()).c("tripWire").d("trip_wire"));
/* 247 */     REGISTRY.a(133, "emerald_block", (new BlockOreBlock(MaterialMapColor.I)).c(5.0F).b(10.0F).a(j).c("blockEmerald").d("emerald_block"));
/* 248 */     REGISTRY.a(134, "spruce_stairs", (new BlockStairs(block1, 1)).c("stairsWoodSpruce"));
/* 249 */     REGISTRY.a(135, "birch_stairs", (new BlockStairs(block1, 2)).c("stairsWoodBirch"));
/* 250 */     REGISTRY.a(136, "jungle_stairs", (new BlockStairs(block1, 3)).c("stairsWoodJungle"));
/* 251 */     REGISTRY.a(137, "command_block", (new BlockCommand()).s().b(6000000.0F).c("commandBlock").d("command_block"));
/* 252 */     REGISTRY.a(138, "beacon", (new BlockBeacon()).c("beacon").a(1.0F).d("beacon"));
/* 253 */     REGISTRY.a(139, "cobblestone_wall", (new BlockCobbleWall(block)).c("cobbleWall"));
/* 254 */     REGISTRY.a(140, "flower_pot", (new BlockFlowerPot()).c(0.0F).a(e).c("flowerPot").d("flower_pot"));
/* 255 */     REGISTRY.a(141, "carrots", (new BlockCarrots()).c("carrots").d("carrots"));
/* 256 */     REGISTRY.a(142, "potatoes", (new BlockPotatoes()).c("potatoes").d("potatoes"));
/* 257 */     REGISTRY.a(143, "wooden_button", (new BlockWoodButton()).c(0.5F).a(f).c("button"));
/* 258 */     REGISTRY.a(144, "skull", (new BlockSkull()).c(1.0F).a(i).c("skull").d("skull"));
/* 259 */     REGISTRY.a(145, "anvil", (new BlockAnvil()).c(5.0F).a(p).b(2000.0F).c("anvil"));
/* 260 */     REGISTRY.a(146, "trapped_chest", (new BlockChest(1)).c(2.5F).a(f).c("chestTrap"));
/* 261 */     REGISTRY.a(147, "light_weighted_pressure_plate", (new BlockPressurePlateWeighted("gold_block", Material.ORE, 15)).c(0.5F).a(f).c("weightedPlate_light"));
/* 262 */     REGISTRY.a(148, "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted("iron_block", Material.ORE, 150)).c(0.5F).a(f).c("weightedPlate_heavy"));
/* 263 */     REGISTRY.a(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).c(0.0F).a(f).c("comparator").H().d("comparator_off"));
/* 264 */     REGISTRY.a(150, "powered_comparator", (new BlockRedstoneComparator(true)).c(0.0F).a(0.625F).a(f).c("comparator").H().d("comparator_on"));
/* 265 */     REGISTRY.a(151, "daylight_detector", (new BlockDaylightDetector()).c(0.2F).a(f).c("daylightDetector").d("daylight_detector"));
/* 266 */     REGISTRY.a(152, "redstone_block", (new BlockRedstone(MaterialMapColor.f)).c(5.0F).b(10.0F).a(j).c("blockRedstone").d("redstone_block"));
/* 267 */     REGISTRY.a(153, "quartz_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("netherquartz").d("quartz_ore"));
/* 268 */     REGISTRY.a(154, "hopper", (new BlockHopper()).c(3.0F).b(8.0F).a(f).c("hopper").d("hopper"));
/* 269 */     Block block9 = (new BlockQuartz()).a(i).c(0.8F).c("quartzBlock").d("quartz_block");
/*     */     
/* 271 */     REGISTRY.a(155, "quartz_block", block9);
/* 272 */     REGISTRY.a(156, "quartz_stairs", (new BlockStairs(block9, 0)).c("stairsQuartz"));
/* 273 */     REGISTRY.a(157, "activator_rail", (new BlockPoweredRail()).c(0.7F).a(j).c("activatorRail").d("rail_activator"));
/* 274 */     REGISTRY.a(158, "dropper", (new BlockDropper()).c(3.5F).a(i).c("dropper").d("dropper"));
/* 275 */     REGISTRY.a(159, "stained_hardened_clay", (new BlockCloth(Material.STONE)).c(1.25F).b(7.0F).a(i).c("clayHardenedStained").d("hardened_clay_stained"));
/* 276 */     REGISTRY.a(160, "stained_glass_pane", (new BlockStainedGlassPane()).c(0.3F).a(k).c("thinStainedGlass").d("glass"));
/* 277 */     REGISTRY.a(161, "leaves2", (new BlockLeaves2()).c("leaves").d("leaves"));
/* 278 */     REGISTRY.a(162, "log2", (new BlockLog2()).c("log").d("log"));
/* 279 */     REGISTRY.a(163, "acacia_stairs", (new BlockStairs(block1, 4)).c("stairsWoodAcacia"));
/* 280 */     REGISTRY.a(164, "dark_oak_stairs", (new BlockStairs(block1, 5)).c("stairsWoodDarkOak"));
/* 281 */     REGISTRY.a(170, "hay_block", (new BlockHay()).c(0.5F).a(h).c("hayBlock").a(CreativeModeTab.b).d("hay_block"));
/* 282 */     REGISTRY.a(171, "carpet", (new BlockCarpet()).c(0.1F).a(l).c("woolCarpet").g(0));
/* 283 */     REGISTRY.a(172, "hardened_clay", (new BlockHardenedClay()).c(1.25F).b(7.0F).a(i).c("clayHardened").d("hardened_clay"));
/* 284 */     REGISTRY.a(173, "coal_block", (new Block(Material.STONE)).c(5.0F).b(10.0F).a(i).c("blockCoal").a(CreativeModeTab.b).d("coal_block"));
/* 285 */     REGISTRY.a(174, "packed_ice", (new BlockPackedIce()).c(0.5F).a(k).c("icePacked").d("ice_packed"));
/* 286 */     REGISTRY.a(175, "double_plant", new BlockTallPlant());
/* 287 */     Iterator<Block> iterator = REGISTRY.iterator();
/*     */     
/* 289 */     while (iterator.hasNext()) {
/* 290 */       Block block10 = iterator.next();
/*     */       
/* 292 */       if (block10.material == Material.AIR) {
/* 293 */         block10.u = false; continue;
/*     */       } 
/* 295 */       boolean flag = false;
/* 296 */       boolean flag1 = (block10.b() == 10);
/* 297 */       boolean flag2 = block10 instanceof BlockStepAbstract;
/* 298 */       boolean flag3 = (block10 == block4);
/* 299 */       boolean flag4 = block10.s;
/* 300 */       boolean flag5 = (block10.r == 0);
/*     */       
/* 302 */       if (flag1 || flag2 || flag3 || flag4 || flag5) {
/* 303 */         flag = true;
/*     */       }
/*     */       
/* 306 */       block10.u = flag;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Block(Material material) {
/* 312 */     this.stepSound = e;
/* 313 */     this.I = 1.0F;
/* 314 */     this.frictionFactor = 0.6F;
/* 315 */     this.material = material;
/* 316 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 317 */     this.q = c();
/* 318 */     this.r = c() ? 255 : 0;
/* 319 */     this.s = !material.blocksLight();
/*     */   }
/*     */   
/*     */   protected Block a(StepSound stepsound) {
/* 323 */     this.stepSound = stepsound;
/* 324 */     return this;
/*     */   }
/*     */   
/*     */   protected Block g(int i) {
/* 328 */     this.r = i;
/* 329 */     return this;
/*     */   }
/*     */   
/*     */   protected Block a(float f) {
/* 333 */     this.t = (int)(15.0F * f);
/* 334 */     return this;
/*     */   }
/*     */   
/*     */   protected Block b(float f) {
/* 338 */     this.durability = f * 3.0F;
/* 339 */     return this;
/*     */   }
/*     */   
/*     */   public boolean r() {
/* 343 */     return (this.material.k() && d() && !isPowerSource());
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 347 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
/* 351 */     return !this.material.isSolid();
/*     */   }
/*     */   
/*     */   public int b() {
/* 355 */     return 0;
/*     */   }
/*     */   
/*     */   protected Block c(float f) {
/* 359 */     this.strength = f;
/* 360 */     if (this.durability < f * 5.0F) {
/* 361 */       this.durability = f * 5.0F;
/*     */     }
/*     */     
/* 364 */     return this;
/*     */   }
/*     */   
/*     */   protected Block s() {
/* 368 */     c(-1.0F);
/* 369 */     return this;
/*     */   }
/*     */   
/*     */   public float f(World world, int i, int j, int k) {
/* 373 */     return this.strength;
/*     */   }
/*     */   
/*     */   protected Block a(boolean flag) {
/* 377 */     this.z = flag;
/* 378 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isTicking() {
/* 382 */     return this.z;
/*     */   }
/*     */   
/*     */   public boolean isTileEntity() {
/* 386 */     return this.isTileEntity;
/*     */   }
/*     */   
/*     */   protected final void a(float f, float f1, float f2, float f3, float f4, float f5) {
/* 390 */     this.minX = f;
/* 391 */     this.minY = f1;
/* 392 */     this.minZ = f2;
/* 393 */     this.maxX = f3;
/* 394 */     this.maxY = f4;
/* 395 */     this.maxZ = f5;
/*     */   }
/*     */   
/*     */   public boolean d(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 399 */     return iblockaccess.getType(i, j, k).getMaterial().isBuildable();
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
/* 403 */     AxisAlignedBB axisalignedbb1 = a(world, i, j, k);
/*     */     
/* 405 */     if (axisalignedbb1 != null && axisalignedbb.b(axisalignedbb1)) {
/* 406 */       list.add(axisalignedbb1);
/*     */     }
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 411 */     return AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ);
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 415 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(int i, boolean flag) {
/* 419 */     return v();
/*     */   }
/*     */   
/*     */   public boolean v() {
/* 423 */     return true;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {}
/*     */   
/*     */   public void postBreak(World world, int i, int j, int k, int l) {}
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {}
/*     */   
/*     */   public int a(World world) {
/* 433 */     return 10;
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/* 437 */     AsyncCatcher.catchOp("block onPlace");
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 441 */     AsyncCatcher.catchOp("block remove");
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 445 */     return 1;
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 449 */     return Item.getItemOf(this);
/*     */   }
/*     */   
/*     */   public float getDamage(EntityHuman entityhuman, World world, int i, int j, int k) {
/* 453 */     float f = f(world, i, j, k);
/*     */     
/* 455 */     return (f < 0.0F) ? 0.0F : (!entityhuman.a(this) ? (entityhuman.a(this, false) / f / 100.0F) : (entityhuman.a(this, true) / f / 30.0F));
/*     */   }
/*     */   
/*     */   public final void b(World world, int i, int j, int k, int l, int i1) {
/* 459 */     dropNaturally(world, i, j, k, l, 1.0F, i1);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/* 463 */     if (!world.isStatic) {
/* 464 */       int j1 = getDropCount(i1, world.random);
/*     */       
/* 466 */       for (int k1 = 0; k1 < j1; k1++) {
/*     */         
/* 468 */         if (world.random.nextFloat() < f) {
/* 469 */           Item item = getDropType(l, world.random, i1);
/*     */           
/* 471 */           if (item != null) {
/* 472 */             a(world, i, j, k, new ItemStack(item, 1, getDropData(l)));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void a(World world, int i, int j, int k, ItemStack itemstack) {
/* 480 */     if (!world.isStatic && world.getGameRules().getBoolean("doTileDrops")) {
/* 481 */       float f = 0.7F;
/* 482 */       double d0 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 483 */       double d1 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 484 */       double d2 = (world.random.nextFloat() * f) + (1.0F - f) * 0.5D;
/* 485 */       EntityItem entityitem = new EntityItem(world, i + d0, j + d1, k + d2, itemstack);
/*     */       
/* 487 */       entityitem.pickupDelay = 10;
/* 488 */       world.addEntity(entityitem);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void dropExperience(World world, int i, int j, int k, int l) {
/* 493 */     if (!world.isStatic) {
/* 494 */       while (l > 0) {
/* 495 */         int i1 = EntityExperienceOrb.getOrbValue(l);
/*     */         
/* 497 */         l -= i1;
/* 498 */         world.addEntity(new EntityExperienceOrb(world, i + 0.5D, j + 0.5D, k + 0.5D, i1));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDropData(int i) {
/* 504 */     return 0;
/*     */   }
/*     */   
/*     */   public float a(Entity entity) {
/* 508 */     return this.durability / 5.0F;
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
/* 512 */     updateShape(world, i, j, k);
/* 513 */     vec3d = vec3d.add(-i, -j, -k);
/* 514 */     vec3d1 = vec3d1.add(-i, -j, -k);
/* 515 */     Vec3D vec3d2 = vec3d.b(vec3d1, this.minX);
/* 516 */     Vec3D vec3d3 = vec3d.b(vec3d1, this.maxX);
/* 517 */     Vec3D vec3d4 = vec3d.c(vec3d1, this.minY);
/* 518 */     Vec3D vec3d5 = vec3d.c(vec3d1, this.maxY);
/* 519 */     Vec3D vec3d6 = vec3d.d(vec3d1, this.minZ);
/* 520 */     Vec3D vec3d7 = vec3d.d(vec3d1, this.maxZ);
/*     */     
/* 522 */     if (!a(vec3d2)) {
/* 523 */       vec3d2 = null;
/*     */     }
/*     */     
/* 526 */     if (!a(vec3d3)) {
/* 527 */       vec3d3 = null;
/*     */     }
/*     */     
/* 530 */     if (!b(vec3d4)) {
/* 531 */       vec3d4 = null;
/*     */     }
/*     */     
/* 534 */     if (!b(vec3d5)) {
/* 535 */       vec3d5 = null;
/*     */     }
/*     */     
/* 538 */     if (!c(vec3d6)) {
/* 539 */       vec3d6 = null;
/*     */     }
/*     */     
/* 542 */     if (!c(vec3d7)) {
/* 543 */       vec3d7 = null;
/*     */     }
/*     */     
/* 546 */     Vec3D vec3d8 = null;
/*     */     
/* 548 */     if (vec3d2 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d2) < vec3d.distanceSquared(vec3d8))) {
/* 549 */       vec3d8 = vec3d2;
/*     */     }
/*     */     
/* 552 */     if (vec3d3 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d3) < vec3d.distanceSquared(vec3d8))) {
/* 553 */       vec3d8 = vec3d3;
/*     */     }
/*     */     
/* 556 */     if (vec3d4 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d4) < vec3d.distanceSquared(vec3d8))) {
/* 557 */       vec3d8 = vec3d4;
/*     */     }
/*     */     
/* 560 */     if (vec3d5 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d5) < vec3d.distanceSquared(vec3d8))) {
/* 561 */       vec3d8 = vec3d5;
/*     */     }
/*     */     
/* 564 */     if (vec3d6 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d6) < vec3d.distanceSquared(vec3d8))) {
/* 565 */       vec3d8 = vec3d6;
/*     */     }
/*     */     
/* 568 */     if (vec3d7 != null && (vec3d8 == null || vec3d.distanceSquared(vec3d7) < vec3d.distanceSquared(vec3d8))) {
/* 569 */       vec3d8 = vec3d7;
/*     */     }
/*     */     
/* 572 */     if (vec3d8 == null) {
/* 573 */       return null;
/*     */     }
/* 575 */     byte b0 = -1;
/*     */     
/* 577 */     if (vec3d8 == vec3d2) {
/* 578 */       b0 = 4;
/*     */     }
/*     */     
/* 581 */     if (vec3d8 == vec3d3) {
/* 582 */       b0 = 5;
/*     */     }
/*     */     
/* 585 */     if (vec3d8 == vec3d4) {
/* 586 */       b0 = 0;
/*     */     }
/*     */     
/* 589 */     if (vec3d8 == vec3d5) {
/* 590 */       b0 = 1;
/*     */     }
/*     */     
/* 593 */     if (vec3d8 == vec3d6) {
/* 594 */       b0 = 2;
/*     */     }
/*     */     
/* 597 */     if (vec3d8 == vec3d7) {
/* 598 */       b0 = 3;
/*     */     }
/*     */     
/* 601 */     return new MovingObjectPosition(i, j, k, b0, vec3d8.add(i, j, k));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean a(Vec3D vec3d) {
/* 606 */     return (vec3d == null) ? false : ((vec3d.b >= this.minY && vec3d.b <= this.maxY && vec3d.c >= this.minZ && vec3d.c <= this.maxZ));
/*     */   }
/*     */   
/*     */   private boolean b(Vec3D vec3d) {
/* 610 */     return (vec3d == null) ? false : ((vec3d.a >= this.minX && vec3d.a <= this.maxX && vec3d.c >= this.minZ && vec3d.c <= this.maxZ));
/*     */   }
/*     */   
/*     */   private boolean c(Vec3D vec3d) {
/* 614 */     return (vec3d == null) ? false : ((vec3d.a >= this.minX && vec3d.a <= this.maxX && vec3d.b >= this.minY && vec3d.b <= this.maxY));
/*     */   }
/*     */   
/*     */   public void wasExploded(World world, int i, int j, int k, Explosion explosion) {}
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l, ItemStack itemstack) {
/* 620 */     return canPlace(world, i, j, k, l);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k, int l) {
/* 624 */     return canPlace(world, i, j, k);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/* 628 */     return (world.getType(i, j, k)).material.isReplaceable();
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 632 */     return false;
/*     */   }
/*     */   
/*     */   public void b(World world, int i, int j, int k, Entity entity) {}
/*     */   
/*     */   public int getPlacedData(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1) {
/* 638 */     return i1;
/*     */   }
/*     */   
/*     */   public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {}
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {}
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {}
/*     */   
/*     */   public final double x() {
/* 648 */     return this.minX;
/*     */   }
/*     */   
/*     */   public final double y() {
/* 652 */     return this.maxX;
/*     */   }
/*     */   
/*     */   public final double z() {
/* 656 */     return this.minY;
/*     */   }
/*     */   
/*     */   public final double A() {
/* 660 */     return this.maxY;
/*     */   }
/*     */   
/*     */   public final double B() {
/* 664 */     return this.minZ;
/*     */   }
/*     */   
/*     */   public final double C() {
/* 668 */     return this.maxZ;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 672 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 676 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {}
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 682 */     return 0;
/*     */   }
/*     */   
/*     */   public void g() {}
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 688 */     entityhuman.a(StatisticList.MINE_BLOCK_COUNT[getId(this)], 1);
/* 689 */     entityhuman.applyExhaustion(world.paperSpigotConfig.blockBreakExhaustion);
/* 690 */     if (E() && EnchantmentManager.hasSilkTouchEnchantment(entityhuman)) {
/* 691 */       ItemStack itemstack = j(l);
/*     */       
/* 693 */       if (itemstack != null) {
/* 694 */         a(world, i, j, k, itemstack);
/*     */       }
/*     */     } else {
/* 697 */       int i1 = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
/*     */       
/* 699 */       b(world, i, j, k, l, i1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean E() {
/* 704 */     return (d() && !this.isTileEntity);
/*     */   }
/*     */   
/*     */   protected ItemStack j(int i) {
/* 708 */     int j = 0;
/* 709 */     Item item = Item.getItemOf(this);
/*     */     
/* 711 */     if (item != null && item.n()) {
/* 712 */       j = i;
/*     */     }
/*     */     
/* 715 */     return new ItemStack(item, 1, j);
/*     */   }
/*     */   
/*     */   public int getDropCount(int i, Random random) {
/* 719 */     return a(random);
/*     */   }
/*     */   
/*     */   public boolean j(World world, int i, int j, int k) {
/* 723 */     return true;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {}
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, int l) {}
/*     */   
/*     */   public Block c(String s) {
/* 731 */     this.name = s;
/* 732 */     return this;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 736 */     return LocaleI18n.get(a() + ".name");
/*     */   }
/*     */   
/*     */   public String a() {
/* 740 */     return "tile." + this.name;
/*     */   }
/*     */   
/*     */   public boolean a(World world, int i, int j, int k, int l, int i1) {
/* 744 */     return false;
/*     */   }
/*     */   
/*     */   public boolean G() {
/* 748 */     return this.y;
/*     */   }
/*     */   
/*     */   protected Block H() {
/* 752 */     this.y = false;
/* 753 */     return this;
/*     */   }
/*     */   
/*     */   public int h() {
/* 757 */     return this.material.getPushReaction();
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity, float f) {}
/*     */   
/*     */   public int getDropData(World world, int i, int j, int k) {
/* 763 */     return getDropData(world.getData(i, j, k));
/*     */   }
/*     */   
/*     */   public Block a(CreativeModeTab creativemodetab) {
/* 767 */     this.creativeTab = creativemodetab;
/* 768 */     return this;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {}
/*     */   
/*     */   public void f(World world, int i, int j, int k, int l) {}
/*     */   
/*     */   public void l(World world, int i, int j, int k) {}
/*     */   
/*     */   public boolean L() {
/* 778 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(Explosion explosion) {
/* 782 */     return true;
/*     */   }
/*     */   
/*     */   public boolean c(Block block) {
/* 786 */     return (this == block);
/*     */   }
/*     */   
/*     */   public static boolean a(Block block, Block block1) {
/* 790 */     return (block != null && block1 != null) ? ((block == block1) ? true : block.c(block1)) : false;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 794 */     return false;
/*     */   }
/*     */   
/*     */   public int g(World world, int i, int j, int k, int l) {
/* 798 */     return 0;
/*     */   }
/*     */   
/*     */   protected Block d(String s) {
/* 802 */     this.d = s;
/* 803 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExpDrop(World world, int data, int enchantmentLevel) {
/* 808 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float range(float min, float value, float max) {
/* 814 */     if (value < min) {
/* 815 */       return min;
/*     */     }
/* 817 */     if (value > max) {
/* 818 */       return max;
/*     */     }
/* 820 */     return value;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Block.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */