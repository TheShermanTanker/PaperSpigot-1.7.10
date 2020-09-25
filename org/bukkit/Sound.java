/*     */ package org.bukkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Sound
/*     */ {
/*  12 */   AMBIENCE_CAVE,
/*  13 */   AMBIENCE_RAIN,
/*  14 */   AMBIENCE_THUNDER,
/*  15 */   ANVIL_BREAK,
/*  16 */   ANVIL_LAND,
/*  17 */   ANVIL_USE,
/*  18 */   ARROW_HIT,
/*  19 */   BURP,
/*  20 */   CHEST_CLOSE,
/*  21 */   CHEST_OPEN,
/*  22 */   CLICK,
/*  23 */   DOOR_CLOSE,
/*  24 */   DOOR_OPEN,
/*  25 */   DRINK,
/*  26 */   EAT,
/*  27 */   EXPLODE,
/*  28 */   FALL_BIG,
/*  29 */   FALL_SMALL,
/*  30 */   FIRE,
/*  31 */   FIRE_IGNITE,
/*  32 */   FIZZ,
/*  33 */   FUSE,
/*  34 */   GLASS,
/*  35 */   HURT_FLESH,
/*  36 */   ITEM_BREAK,
/*  37 */   ITEM_PICKUP,
/*  38 */   LAVA,
/*  39 */   LAVA_POP,
/*  40 */   LEVEL_UP,
/*  41 */   MINECART_BASE,
/*  42 */   MINECART_INSIDE,
/*  43 */   NOTE_BASS,
/*  44 */   NOTE_PIANO,
/*  45 */   NOTE_BASS_DRUM,
/*  46 */   NOTE_STICKS,
/*  47 */   NOTE_BASS_GUITAR,
/*  48 */   NOTE_SNARE_DRUM,
/*  49 */   NOTE_PLING,
/*  50 */   ORB_PICKUP,
/*  51 */   PISTON_EXTEND,
/*  52 */   PISTON_RETRACT,
/*  53 */   PORTAL,
/*  54 */   PORTAL_TRAVEL,
/*  55 */   PORTAL_TRIGGER,
/*  56 */   SHOOT_ARROW,
/*  57 */   SPLASH,
/*  58 */   SPLASH2,
/*  59 */   STEP_GRASS,
/*  60 */   STEP_GRAVEL,
/*  61 */   STEP_LADDER,
/*  62 */   STEP_SAND,
/*  63 */   STEP_SNOW,
/*  64 */   STEP_STONE,
/*  65 */   STEP_WOOD,
/*  66 */   STEP_WOOL,
/*  67 */   SWIM,
/*  68 */   WATER,
/*  69 */   WOOD_CLICK,
/*     */   
/*  71 */   BAT_DEATH,
/*  72 */   BAT_HURT,
/*  73 */   BAT_IDLE,
/*  74 */   BAT_LOOP,
/*  75 */   BAT_TAKEOFF,
/*  76 */   BLAZE_BREATH,
/*  77 */   BLAZE_DEATH,
/*  78 */   BLAZE_HIT,
/*  79 */   CAT_HISS,
/*  80 */   CAT_HIT,
/*  81 */   CAT_MEOW,
/*  82 */   CAT_PURR,
/*  83 */   CAT_PURREOW,
/*  84 */   CHICKEN_IDLE,
/*  85 */   CHICKEN_HURT,
/*  86 */   CHICKEN_EGG_POP,
/*  87 */   CHICKEN_WALK,
/*  88 */   COW_IDLE,
/*  89 */   COW_HURT,
/*  90 */   COW_WALK,
/*  91 */   CREEPER_HISS,
/*  92 */   CREEPER_DEATH,
/*  93 */   ENDERDRAGON_DEATH,
/*  94 */   ENDERDRAGON_GROWL,
/*  95 */   ENDERDRAGON_HIT,
/*  96 */   ENDERDRAGON_WINGS,
/*  97 */   ENDERMAN_DEATH,
/*  98 */   ENDERMAN_HIT,
/*  99 */   ENDERMAN_IDLE,
/* 100 */   ENDERMAN_TELEPORT,
/* 101 */   ENDERMAN_SCREAM,
/* 102 */   ENDERMAN_STARE,
/* 103 */   GHAST_SCREAM,
/* 104 */   GHAST_SCREAM2,
/* 105 */   GHAST_CHARGE,
/* 106 */   GHAST_DEATH,
/* 107 */   GHAST_FIREBALL,
/* 108 */   GHAST_MOAN,
/* 109 */   IRONGOLEM_DEATH,
/* 110 */   IRONGOLEM_HIT,
/* 111 */   IRONGOLEM_THROW,
/* 112 */   IRONGOLEM_WALK,
/* 113 */   MAGMACUBE_WALK,
/* 114 */   MAGMACUBE_WALK2,
/* 115 */   MAGMACUBE_JUMP,
/* 116 */   PIG_IDLE,
/* 117 */   PIG_DEATH,
/* 118 */   PIG_WALK,
/* 119 */   SHEEP_IDLE,
/* 120 */   SHEEP_SHEAR,
/* 121 */   SHEEP_WALK,
/* 122 */   SILVERFISH_HIT,
/* 123 */   SILVERFISH_KILL,
/* 124 */   SILVERFISH_IDLE,
/* 125 */   SILVERFISH_WALK,
/* 126 */   SKELETON_IDLE,
/* 127 */   SKELETON_DEATH,
/* 128 */   SKELETON_HURT,
/* 129 */   SKELETON_WALK,
/* 130 */   SLIME_ATTACK,
/* 131 */   SLIME_WALK,
/* 132 */   SLIME_WALK2,
/* 133 */   SPIDER_IDLE,
/* 134 */   SPIDER_DEATH,
/* 135 */   SPIDER_WALK,
/* 136 */   WITHER_DEATH,
/* 137 */   WITHER_HURT,
/* 138 */   WITHER_IDLE,
/* 139 */   WITHER_SHOOT,
/* 140 */   WITHER_SPAWN,
/* 141 */   WOLF_BARK,
/* 142 */   WOLF_DEATH,
/* 143 */   WOLF_GROWL,
/* 144 */   WOLF_HOWL,
/* 145 */   WOLF_HURT,
/* 146 */   WOLF_PANT,
/* 147 */   WOLF_SHAKE,
/* 148 */   WOLF_WALK,
/* 149 */   WOLF_WHINE,
/* 150 */   ZOMBIE_METAL,
/* 151 */   ZOMBIE_WOOD,
/* 152 */   ZOMBIE_WOODBREAK,
/* 153 */   ZOMBIE_IDLE,
/* 154 */   ZOMBIE_DEATH,
/* 155 */   ZOMBIE_HURT,
/* 156 */   ZOMBIE_INFECT,
/* 157 */   ZOMBIE_UNFECT,
/* 158 */   ZOMBIE_REMEDY,
/* 159 */   ZOMBIE_WALK,
/* 160 */   ZOMBIE_PIG_IDLE,
/* 161 */   ZOMBIE_PIG_ANGRY,
/* 162 */   ZOMBIE_PIG_DEATH,
/* 163 */   ZOMBIE_PIG_HURT,
/*     */   
/* 165 */   DIG_WOOL,
/* 166 */   DIG_GRASS,
/* 167 */   DIG_GRAVEL,
/* 168 */   DIG_SAND,
/* 169 */   DIG_SNOW,
/* 170 */   DIG_STONE,
/* 171 */   DIG_WOOD,
/*     */   
/* 173 */   FIREWORK_BLAST,
/* 174 */   FIREWORK_BLAST2,
/* 175 */   FIREWORK_LARGE_BLAST,
/* 176 */   FIREWORK_LARGE_BLAST2,
/* 177 */   FIREWORK_TWINKLE,
/* 178 */   FIREWORK_TWINKLE2,
/* 179 */   FIREWORK_LAUNCH,
/* 180 */   SUCCESSFUL_HIT,
/*     */   
/* 182 */   HORSE_ANGRY,
/* 183 */   HORSE_ARMOR,
/* 184 */   HORSE_BREATHE,
/* 185 */   HORSE_DEATH,
/* 186 */   HORSE_GALLOP,
/* 187 */   HORSE_HIT,
/* 188 */   HORSE_IDLE,
/* 189 */   HORSE_JUMP,
/* 190 */   HORSE_LAND,
/* 191 */   HORSE_SADDLE,
/* 192 */   HORSE_SOFT,
/* 193 */   HORSE_WOOD,
/* 194 */   DONKEY_ANGRY,
/* 195 */   DONKEY_DEATH,
/* 196 */   DONKEY_HIT,
/* 197 */   DONKEY_IDLE,
/* 198 */   HORSE_SKELETON_DEATH,
/* 199 */   HORSE_SKELETON_HIT,
/* 200 */   HORSE_SKELETON_IDLE,
/* 201 */   HORSE_ZOMBIE_DEATH,
/* 202 */   HORSE_ZOMBIE_HIT,
/* 203 */   HORSE_ZOMBIE_IDLE,
/*     */   
/* 205 */   VILLAGER_DEATH,
/* 206 */   VILLAGER_HAGGLE,
/* 207 */   VILLAGER_HIT,
/* 208 */   VILLAGER_IDLE,
/* 209 */   VILLAGER_NO,
/* 210 */   VILLAGER_YES;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Sound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */