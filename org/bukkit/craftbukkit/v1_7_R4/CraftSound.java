/*     */ package org.bukkit.craftbukkit.v1_7_R4;
/*     */ 
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Sound;
/*     */ 
/*     */ 
/*     */ public class CraftSound
/*     */ {
/*   9 */   private static final String[] sounds = new String[(Sound.values()).length];
/*     */ 
/*     */   
/*     */   static {
/*  13 */     set(Sound.AMBIENCE_CAVE, "ambient.cave.cave");
/*  14 */     set(Sound.AMBIENCE_RAIN, "ambient.weather.rain");
/*  15 */     set(Sound.AMBIENCE_THUNDER, "ambient.weather.thunder");
/*     */     
/*  17 */     set(Sound.HURT_FLESH, "game.neutral.hurt");
/*  18 */     set(Sound.FALL_BIG, "game.neutral.hurt.fall.big");
/*  19 */     set(Sound.FALL_SMALL, "game.neutral.hurt.fall.small");
/*     */     
/*  21 */     set(Sound.DIG_WOOL, "dig.cloth");
/*  22 */     set(Sound.DIG_GRASS, "dig.grass");
/*  23 */     set(Sound.DIG_GRAVEL, "dig.gravel");
/*  24 */     set(Sound.DIG_SAND, "dig.sand");
/*  25 */     set(Sound.DIG_SNOW, "dig.snow");
/*  26 */     set(Sound.DIG_STONE, "dig.stone");
/*  27 */     set(Sound.DIG_WOOD, "dig.wood");
/*     */     
/*  29 */     set(Sound.FIRE, "fire.fire");
/*  30 */     set(Sound.FIRE_IGNITE, "fire.ignite");
/*     */     
/*  32 */     set(Sound.FIREWORK_BLAST, "fireworks.blast");
/*  33 */     set(Sound.FIREWORK_BLAST2, "fireworks.blast_far");
/*  34 */     set(Sound.FIREWORK_LARGE_BLAST, "fireworks.largeBlast");
/*  35 */     set(Sound.FIREWORK_LARGE_BLAST2, "fireworks.largeBlast_far");
/*  36 */     set(Sound.FIREWORK_TWINKLE, "fireworks.twinkle");
/*  37 */     set(Sound.FIREWORK_TWINKLE2, "fireworks.twinkle_far");
/*  38 */     set(Sound.FIREWORK_LAUNCH, "fireworks.launch");
/*     */     
/*  40 */     set(Sound.SPLASH2, "game.neutral.swim.splash");
/*  41 */     set(Sound.SWIM, "game.neutral.swim");
/*  42 */     set(Sound.WATER, "liquid.water");
/*  43 */     set(Sound.LAVA, "liquid.lava");
/*  44 */     set(Sound.LAVA_POP, "liquid.lavapop");
/*     */     
/*  46 */     set(Sound.MINECART_BASE, "minecart.base");
/*  47 */     set(Sound.MINECART_INSIDE, "minecart.inside");
/*     */     
/*  49 */     set(Sound.BAT_DEATH, "mob.bat.death");
/*  50 */     set(Sound.BAT_HURT, "mob.bat.hurt");
/*  51 */     set(Sound.BAT_IDLE, "mob.bat.idle");
/*  52 */     set(Sound.BAT_LOOP, "mob.bat.loop");
/*  53 */     set(Sound.BAT_TAKEOFF, "mob.bat.takeoff");
/*  54 */     set(Sound.BLAZE_BREATH, "mob.blaze.breathe");
/*  55 */     set(Sound.BLAZE_DEATH, "mob.blaze.death");
/*  56 */     set(Sound.BLAZE_HIT, "mob.blaze.hit");
/*  57 */     set(Sound.CAT_HISS, "mob.cat.hiss");
/*  58 */     set(Sound.CAT_HIT, "mob.cat.hitt");
/*  59 */     set(Sound.CAT_MEOW, "mob.cat.meow");
/*  60 */     set(Sound.CAT_PURR, "mob.cat.purr");
/*  61 */     set(Sound.CAT_PURREOW, "mob.cat.purreow");
/*  62 */     set(Sound.CHICKEN_IDLE, "mob.chicken.say");
/*  63 */     set(Sound.CHICKEN_HURT, "mob.chicken.hurt");
/*  64 */     set(Sound.CHICKEN_EGG_POP, "mob.chicken.plop");
/*  65 */     set(Sound.CHICKEN_WALK, "mob.chicken.step");
/*  66 */     set(Sound.COW_HURT, "mob.cow.hurt");
/*  67 */     set(Sound.COW_IDLE, "mob.cow.say");
/*  68 */     set(Sound.COW_WALK, "mob.cow.step");
/*  69 */     set(Sound.CREEPER_DEATH, "mob.creeper.death");
/*  70 */     set(Sound.CREEPER_HISS, "mob.creeper.say");
/*  71 */     set(Sound.ENDERDRAGON_DEATH, "mob.enderdragon.end");
/*  72 */     set(Sound.ENDERDRAGON_GROWL, "mob.enderdragon.growl");
/*  73 */     set(Sound.ENDERDRAGON_HIT, "mob.enderdragon.hit");
/*  74 */     set(Sound.ENDERDRAGON_WINGS, "mob.enderdragon.wings");
/*  75 */     set(Sound.ENDERMAN_DEATH, "mob.endermen.death");
/*  76 */     set(Sound.ENDERMAN_HIT, "mob.endermen.hit");
/*  77 */     set(Sound.ENDERMAN_IDLE, "mob.endermen.idle");
/*  78 */     set(Sound.ENDERMAN_TELEPORT, "mob.endermen.portal");
/*  79 */     set(Sound.ENDERMAN_SCREAM, "mob.endermen.scream");
/*  80 */     set(Sound.ENDERMAN_STARE, "mob.endermen.stare");
/*  81 */     set(Sound.GHAST_SCREAM2, "mob.ghast.affectionate_scream");
/*  82 */     set(Sound.GHAST_CHARGE, "mob.ghast.charge");
/*  83 */     set(Sound.GHAST_DEATH, "mob.ghast.death");
/*  84 */     set(Sound.GHAST_FIREBALL, "mob.ghast.fireball");
/*  85 */     set(Sound.GHAST_MOAN, "mob.ghast.moan");
/*  86 */     set(Sound.GHAST_SCREAM, "mob.ghast.scream");
/*  87 */     set(Sound.HORSE_ANGRY, "mob.horse.angry");
/*  88 */     set(Sound.HORSE_ARMOR, "mob.horse.armor");
/*  89 */     set(Sound.HORSE_BREATHE, "mob.horse.breathe");
/*  90 */     set(Sound.HORSE_DEATH, "mob.horse.death");
/*  91 */     set(Sound.HORSE_GALLOP, "mob.horse.gallop");
/*  92 */     set(Sound.HORSE_HIT, "mob.horse.hit");
/*  93 */     set(Sound.HORSE_IDLE, "mob.horse.idle");
/*  94 */     set(Sound.HORSE_JUMP, "mob.horse.jump");
/*  95 */     set(Sound.HORSE_LAND, "mob.horse.land");
/*  96 */     set(Sound.HORSE_SADDLE, "mob.horse.leather");
/*  97 */     set(Sound.HORSE_SOFT, "mob.horse.soft");
/*  98 */     set(Sound.HORSE_WOOD, "mob.horse.wood");
/*  99 */     set(Sound.DONKEY_ANGRY, "mob.horse.donkey.angry");
/* 100 */     set(Sound.DONKEY_DEATH, "mob.horse.donkey.death");
/* 101 */     set(Sound.DONKEY_HIT, "mob.horse.donkey.hit");
/* 102 */     set(Sound.DONKEY_IDLE, "mob.horse.donkey.idle");
/* 103 */     set(Sound.HORSE_SKELETON_DEATH, "mob.horse.skeleton.death");
/* 104 */     set(Sound.HORSE_SKELETON_HIT, "mob.horse.skeleton.hit");
/* 105 */     set(Sound.HORSE_SKELETON_IDLE, "mob.horse.skeleton.idle");
/* 106 */     set(Sound.HORSE_ZOMBIE_DEATH, "mob.horse.zombie.death");
/* 107 */     set(Sound.HORSE_ZOMBIE_HIT, "mob.horse.zombie.hit");
/* 108 */     set(Sound.HORSE_ZOMBIE_IDLE, "mob.horse.zombie.idle");
/* 109 */     set(Sound.IRONGOLEM_DEATH, "mob.irongolem.death");
/* 110 */     set(Sound.IRONGOLEM_HIT, "mob.irongolem.hit");
/* 111 */     set(Sound.IRONGOLEM_THROW, "mob.irongolem.throw");
/* 112 */     set(Sound.IRONGOLEM_WALK, "mob.irongolem.walk");
/* 113 */     set(Sound.MAGMACUBE_WALK, "mob.magmacube.small");
/* 114 */     set(Sound.MAGMACUBE_WALK2, "mob.magmacube.big");
/* 115 */     set(Sound.MAGMACUBE_JUMP, "mob.magmacube.jump");
/* 116 */     set(Sound.PIG_IDLE, "mob.pig.say");
/* 117 */     set(Sound.PIG_DEATH, "mob.pig.death");
/* 118 */     set(Sound.PIG_WALK, "mob.pig.step");
/* 119 */     set(Sound.SHEEP_IDLE, "mob.sheep.say");
/* 120 */     set(Sound.SHEEP_SHEAR, "mob.sheep.shear");
/* 121 */     set(Sound.SHEEP_WALK, "mob.sheep.step");
/* 122 */     set(Sound.SILVERFISH_HIT, "mob.silverfish.hit");
/* 123 */     set(Sound.SILVERFISH_KILL, "mob.silverfish.kill");
/* 124 */     set(Sound.SILVERFISH_IDLE, "mob.silverfish.say");
/* 125 */     set(Sound.SILVERFISH_WALK, "mob.silverfish.step");
/* 126 */     set(Sound.SKELETON_IDLE, "mob.skeleton.say");
/* 127 */     set(Sound.SKELETON_DEATH, "mob.skeleton.death");
/* 128 */     set(Sound.SKELETON_HURT, "mob.skeleton.hurt");
/* 129 */     set(Sound.SKELETON_WALK, "mob.skeleton.step");
/* 130 */     set(Sound.SLIME_ATTACK, "mob.slime.attack");
/* 131 */     set(Sound.SLIME_WALK, "mob.slime.small");
/* 132 */     set(Sound.SLIME_WALK2, "mob.slime.big");
/* 133 */     set(Sound.SPIDER_IDLE, "mob.spider.say");
/* 134 */     set(Sound.SPIDER_DEATH, "mob.spider.death");
/* 135 */     set(Sound.SPIDER_WALK, "mob.spider.step");
/* 136 */     set(Sound.VILLAGER_DEATH, "mob.villager.death");
/* 137 */     set(Sound.VILLAGER_HAGGLE, "mob.villager.haggle");
/* 138 */     set(Sound.VILLAGER_HIT, "mob.villager.hit");
/* 139 */     set(Sound.VILLAGER_IDLE, "mob.villager.idle");
/* 140 */     set(Sound.VILLAGER_NO, "mob.villager.no");
/* 141 */     set(Sound.VILLAGER_YES, "mob.villager.yes");
/* 142 */     set(Sound.WITHER_DEATH, "mob.wither.death");
/* 143 */     set(Sound.WITHER_HURT, "mob.wither.hurt");
/* 144 */     set(Sound.WITHER_IDLE, "mob.wither.idle");
/* 145 */     set(Sound.WITHER_SHOOT, "mob.wither.shoot");
/* 146 */     set(Sound.WITHER_SPAWN, "mob.wither.spawn");
/* 147 */     set(Sound.WOLF_BARK, "mob.wolf.bark");
/* 148 */     set(Sound.WOLF_DEATH, "mob.wolf.death");
/* 149 */     set(Sound.WOLF_GROWL, "mob.wolf.growl");
/* 150 */     set(Sound.WOLF_HOWL, "mob.wolf.howl");
/* 151 */     set(Sound.WOLF_HURT, "mob.wolf.hurt");
/* 152 */     set(Sound.WOLF_PANT, "mob.wolf.panting");
/* 153 */     set(Sound.WOLF_SHAKE, "mob.wolf.shake");
/* 154 */     set(Sound.WOLF_WALK, "mob.wolf.step");
/* 155 */     set(Sound.WOLF_WHINE, "mob.wolf.whine");
/* 156 */     set(Sound.ZOMBIE_METAL, "mob.zombie.metal");
/* 157 */     set(Sound.ZOMBIE_WOOD, "mob.zombie.wood");
/* 158 */     set(Sound.ZOMBIE_WOODBREAK, "mob.zombie.woodbreak");
/* 159 */     set(Sound.ZOMBIE_IDLE, "mob.zombie.say");
/* 160 */     set(Sound.ZOMBIE_DEATH, "mob.zombie.death");
/* 161 */     set(Sound.ZOMBIE_HURT, "mob.zombie.hurt");
/* 162 */     set(Sound.ZOMBIE_INFECT, "mob.zombie.infect");
/* 163 */     set(Sound.ZOMBIE_UNFECT, "mob.zombie.unfect");
/* 164 */     set(Sound.ZOMBIE_REMEDY, "mob.zombie.remedy");
/* 165 */     set(Sound.ZOMBIE_WALK, "mob.zombie.step");
/* 166 */     set(Sound.ZOMBIE_PIG_IDLE, "mob.zombiepig.zpig");
/* 167 */     set(Sound.ZOMBIE_PIG_ANGRY, "mob.zombiepig.zpigangry");
/* 168 */     set(Sound.ZOMBIE_PIG_DEATH, "mob.zombiepig.zpigdeath");
/* 169 */     set(Sound.ZOMBIE_PIG_HURT, "mob.zombiepig.zpighurt");
/*     */     
/* 171 */     set(Sound.NOTE_BASS_GUITAR, "note.bassattack");
/* 172 */     set(Sound.NOTE_SNARE_DRUM, "note.snare");
/* 173 */     set(Sound.NOTE_PLING, "note.pling");
/* 174 */     set(Sound.NOTE_BASS, "note.bass");
/* 175 */     set(Sound.NOTE_PIANO, "note.harp");
/* 176 */     set(Sound.NOTE_BASS_DRUM, "note.bd");
/* 177 */     set(Sound.NOTE_STICKS, "note.hat");
/*     */     
/* 179 */     set(Sound.PORTAL, "portal.portal");
/* 180 */     set(Sound.PORTAL_TRAVEL, "portal.travel");
/* 181 */     set(Sound.PORTAL_TRIGGER, "portal.trigger");
/*     */     
/* 183 */     set(Sound.ANVIL_BREAK, "random.anvil_break");
/* 184 */     set(Sound.ANVIL_LAND, "random.anvil_land");
/* 185 */     set(Sound.ANVIL_USE, "random.anvil_use");
/* 186 */     set(Sound.SHOOT_ARROW, "random.bow");
/* 187 */     set(Sound.ARROW_HIT, "random.bowhit");
/* 188 */     set(Sound.ITEM_BREAK, "random.break");
/* 189 */     set(Sound.BURP, "random.burp");
/* 190 */     set(Sound.CHEST_CLOSE, "random.chestclosed");
/* 191 */     set(Sound.CHEST_OPEN, "random.chestopen");
/* 192 */     set(Sound.CLICK, "random.click");
/* 193 */     set(Sound.DOOR_CLOSE, "random.door_close");
/* 194 */     set(Sound.DOOR_OPEN, "random.door_open");
/* 195 */     set(Sound.DRINK, "random.drink");
/* 196 */     set(Sound.EAT, "random.eat");
/* 197 */     set(Sound.EXPLODE, "random.explode");
/* 198 */     set(Sound.FIZZ, "random.fizz");
/* 199 */     set(Sound.FUSE, "creeper.primed");
/* 200 */     set(Sound.GLASS, "dig.glass");
/* 201 */     set(Sound.LEVEL_UP, "random.levelup");
/* 202 */     set(Sound.ORB_PICKUP, "random.orb");
/* 203 */     set(Sound.ITEM_PICKUP, "random.pop");
/* 204 */     set(Sound.SPLASH, "random.splash");
/* 205 */     set(Sound.SUCCESSFUL_HIT, "random.successful_hit");
/* 206 */     set(Sound.WOOD_CLICK, "random.wood_click");
/*     */     
/* 208 */     set(Sound.STEP_WOOL, "step.cloth");
/* 209 */     set(Sound.STEP_GRASS, "step.grass");
/* 210 */     set(Sound.STEP_GRAVEL, "step.gravel");
/* 211 */     set(Sound.STEP_LADDER, "step.ladder");
/* 212 */     set(Sound.STEP_SAND, "step.sand");
/* 213 */     set(Sound.STEP_SNOW, "step.snow");
/* 214 */     set(Sound.STEP_STONE, "step.stone");
/* 215 */     set(Sound.STEP_WOOD, "step.wood");
/*     */     
/* 217 */     set(Sound.PISTON_EXTEND, "tile.piston.out");
/* 218 */     set(Sound.PISTON_RETRACT, "tile.piston.in");
/*     */   }
/*     */   
/*     */   private static void set(Sound sound, String key) {
/* 222 */     sounds[sound.ordinal()] = key;
/*     */   }
/*     */   
/*     */   public static String getSound(Sound sound) {
/* 226 */     Validate.notNull(sound, "Sound cannot be null");
/* 227 */     return sounds[sound.ordinal()];
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftSound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */