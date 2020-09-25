/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PacketPlayOutWorldParticles
/*     */   extends Packet {
/*     */   private String a;
/*     */   private float b;
/*     */   private float c;
/*     */   private float d;
/*     */   private float e;
/*     */   private float f;
/*     */   private float g;
/*     */   private float h;
/*     */   private int i;
/*     */   
/*     */   public PacketPlayOutWorldParticles() {}
/*     */   
/*     */   public PacketPlayOutWorldParticles(String s, float f, float f1, float f2, float f3, float f4, float f5, float f6, int i) {
/*  21 */     this.a = s;
/*  22 */     this.b = f;
/*  23 */     this.c = f1;
/*  24 */     this.d = f2;
/*  25 */     this.e = f3;
/*  26 */     this.f = f4;
/*  27 */     this.g = f5;
/*  28 */     this.h = f6;
/*  29 */     this.i = i;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/*  33 */     this.a = packetdataserializer.c(64);
/*  34 */     this.b = packetdataserializer.readFloat();
/*  35 */     this.c = packetdataserializer.readFloat();
/*  36 */     this.d = packetdataserializer.readFloat();
/*  37 */     this.e = packetdataserializer.readFloat();
/*  38 */     this.f = packetdataserializer.readFloat();
/*  39 */     this.g = packetdataserializer.readFloat();
/*  40 */     this.h = packetdataserializer.readFloat();
/*  41 */     this.i = packetdataserializer.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/*  46 */     String[] parts = this.a.split("_");
/*  47 */     Particle particle = Particle.find(parts[0]);
/*  48 */     if (particle == null) particle = Particle.CRIT; 
/*  49 */     if (packetdataserializer.version < 17) {
/*     */       
/*  51 */       packetdataserializer.a(this.a);
/*     */     } else {
/*     */       
/*  54 */       packetdataserializer.writeInt(particle.ordinal());
/*  55 */       packetdataserializer.writeBoolean(false);
/*     */     } 
/*  57 */     packetdataserializer.writeFloat(this.b);
/*  58 */     packetdataserializer.writeFloat(this.c);
/*  59 */     packetdataserializer.writeFloat(this.d);
/*  60 */     packetdataserializer.writeFloat(this.e);
/*  61 */     packetdataserializer.writeFloat(this.f);
/*  62 */     packetdataserializer.writeFloat(this.g);
/*  63 */     packetdataserializer.writeFloat(this.h);
/*  64 */     packetdataserializer.writeInt(this.i);
/*  65 */     if (packetdataserializer.version >= 17)
/*     */     {
/*  67 */       for (int i = 0; i < particle.extra; i++) {
/*     */         
/*  69 */         int toWrite = 0;
/*  70 */         if (parts.length - 1 > i) {
/*     */           
/*     */           try {
/*     */             
/*  74 */             toWrite = Integer.parseInt(parts[i + 1]);
/*  75 */             if (particle.extra == 1 && parts.length == 3) {
/*     */               
/*  77 */               i++;
/*  78 */               toWrite |= Integer.parseInt(parts[i + 1]) << 12;
/*     */             } 
/*  80 */           } catch (NumberFormatException e) {}
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  85 */         packetdataserializer.b(toWrite);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/*  92 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/*  96 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ 
/*     */   
/*     */   private enum Particle
/*     */   {
/* 102 */     EXPLOSION_NORMAL("explode"),
/* 103 */     EXPLOSION_LARGE("largeexplode"),
/* 104 */     EXPLOSION_HUGE("hugeexplosion"),
/* 105 */     FIREWORKS_SPARK("fireworksSpark"),
/* 106 */     WATER_BUBBLE("bubble"),
/* 107 */     WATER_SPLASH("splash"),
/* 108 */     WATER_WAKE("wake"),
/* 109 */     SUSPENDED("suspended"),
/* 110 */     SUSPENDED_DEPTH("depthsuspend"),
/* 111 */     CRIT("crit"),
/* 112 */     CRIT_MAGIC("magicCrit"),
/* 113 */     SMOKE_NORMAL("smoke"),
/* 114 */     SMOKE_LARGE("largesmoke"),
/* 115 */     SPELL("spell"),
/* 116 */     SPELL_INSTANT("instantSpell"),
/* 117 */     SPELL_MOB("mobSpell"),
/* 118 */     SPELL_MOB_AMBIENT("mobSpellAmbient"),
/* 119 */     SPELL_WITCH("witchMagic"),
/* 120 */     DRIP_WATER("dripWater"),
/* 121 */     DRIP_LAVA("dripLava"),
/* 122 */     VILLAGER_ANGRY("angryVillager"),
/* 123 */     VILLAGER_HAPPY("happyVillager"),
/* 124 */     TOWN_AURA("townaura"),
/* 125 */     NOTE("note"),
/* 126 */     PORTAL("portal"),
/* 127 */     ENCHANTMENT_TABLE("enchantmenttable"),
/* 128 */     FLAME("flame"),
/* 129 */     LAVA("lava"),
/* 130 */     FOOTSTEP("footstep"),
/* 131 */     CLOUD("cloud"),
/* 132 */     REDSTONE("reddust"),
/* 133 */     SNOWBALL("snowballpoof"),
/* 134 */     SNOW_SHOVEL("snowshovel"),
/* 135 */     SLIME("slime"),
/* 136 */     HEART("heart"),
/* 137 */     BARRIER("barrier"),
/* 138 */     ICON_CRACK("iconcrack", 2),
/* 139 */     BLOCK_CRACK("blockcrack", 1),
/* 140 */     BLOCK_DUST("blockdust", 1),
/* 141 */     WATER_DROP("droplet"),
/* 142 */     ITEM_TAKE("take"),
/* 143 */     MOB_APPEARANCE("mobappearance");
/*     */     
/*     */     public final String name;
/*     */     public final int extra;
/* 147 */     private static final HashMap<String, Particle> particleMap = new HashMap<String, Particle>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 167 */       for (Particle particle : values())
/*     */       {
/* 169 */         particleMap.put(particle.name, particle);
/*     */       }
/*     */     }
/*     */     
/*     */     Particle(String name, int extra) {
/*     */       this.name = name;
/*     */       this.extra = extra;
/*     */     }
/*     */     
/*     */     public static Particle find(String part) {
/*     */       return particleMap.get(part);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutWorldParticles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */