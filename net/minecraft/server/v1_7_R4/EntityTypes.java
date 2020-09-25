/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTypes
/*     */ {
/*  20 */   private static final Logger b = LogManager.getLogger();
/*  21 */   private static Map c = new HashMap<Object, Object>();
/*  22 */   private static Map d = new HashMap<Object, Object>();
/*  23 */   private static Map e = new HashMap<Object, Object>();
/*  24 */   private static Map f = new HashMap<Object, Object>();
/*  25 */   private static Map g = new HashMap<Object, Object>();
/*     */   
/*  27 */   public static HashMap eggInfo = new LinkedHashMap<Object, Object>();
/*     */   
/*     */   private static void a(Class<?> paramClass, String paramString, int paramInt) {
/*  30 */     if (c.containsKey(paramString)) throw new IllegalArgumentException("ID is already registered: " + paramString); 
/*  31 */     if (e.containsKey(Integer.valueOf(paramInt))) throw new IllegalArgumentException("ID is already registered: " + paramInt); 
/*  32 */     c.put(paramString, paramClass);
/*  33 */     d.put(paramClass, paramString);
/*  34 */     e.put(Integer.valueOf(paramInt), paramClass);
/*  35 */     f.put(paramClass, Integer.valueOf(paramInt));
/*  36 */     g.put(paramString, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   private static void a(Class paramClass, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  40 */     a(paramClass, paramString, paramInt1);
/*     */     
/*  42 */     eggInfo.put(Integer.valueOf(paramInt1), new MonsterEggInfo(paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */   
/*     */   static {
/*  46 */     a(EntityItem.class, "Item", 1);
/*  47 */     a(EntityExperienceOrb.class, "XPOrb", 2);
/*     */     
/*  49 */     a(EntityLeash.class, "LeashKnot", 8);
/*  50 */     a(EntityPainting.class, "Painting", 9);
/*  51 */     a(EntityArrow.class, "Arrow", 10);
/*  52 */     a(EntitySnowball.class, "Snowball", 11);
/*  53 */     a(EntityLargeFireball.class, "Fireball", 12);
/*  54 */     a(EntitySmallFireball.class, "SmallFireball", 13);
/*  55 */     a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
/*  56 */     a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
/*  57 */     a(EntityPotion.class, "ThrownPotion", 16);
/*  58 */     a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
/*  59 */     a(EntityItemFrame.class, "ItemFrame", 18);
/*  60 */     a(EntityWitherSkull.class, "WitherSkull", 19);
/*     */     
/*  62 */     a(EntityTNTPrimed.class, "PrimedTnt", 20);
/*  63 */     a(EntityFallingBlock.class, "FallingSand", 21);
/*     */     
/*  65 */     a(EntityFireworks.class, "FireworksRocketEntity", 22);
/*     */     
/*  67 */     a(EntityBoat.class, "Boat", 41);
/*  68 */     a(EntityMinecartRideable.class, "MinecartRideable", 42);
/*  69 */     a(EntityMinecartChest.class, "MinecartChest", 43);
/*  70 */     a(EntityMinecartFurnace.class, "MinecartFurnace", 44);
/*  71 */     a(EntityMinecartTNT.class, "MinecartTNT", 45);
/*  72 */     a(EntityMinecartHopper.class, "MinecartHopper", 46);
/*  73 */     a(EntityMinecartMobSpawner.class, "MinecartSpawner", 47);
/*  74 */     a(EntityMinecartCommandBlock.class, "MinecartCommandBlock", 40);
/*     */     
/*  76 */     a(EntityInsentient.class, "Mob", 48);
/*  77 */     a(EntityMonster.class, "Monster", 49);
/*     */     
/*  79 */     a(EntityCreeper.class, "Creeper", 50, 894731, 0);
/*  80 */     a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
/*  81 */     a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
/*  82 */     a(EntityGiantZombie.class, "Giant", 53);
/*  83 */     a(EntityZombie.class, "Zombie", 54, 44975, 7969893);
/*  84 */     a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
/*  85 */     a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
/*  86 */     a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
/*  87 */     a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
/*  88 */     a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
/*  89 */     a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
/*  90 */     a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
/*  91 */     a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
/*  92 */     a(EntityEnderDragon.class, "EnderDragon", 63);
/*  93 */     a(EntityWither.class, "WitherBoss", 64);
/*  94 */     a(EntityBat.class, "Bat", 65, 4996656, 986895);
/*  95 */     a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
/*     */     
/*  97 */     a(EntityPig.class, "Pig", 90, 15771042, 14377823);
/*  98 */     a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
/*  99 */     a(EntityCow.class, "Cow", 92, 4470310, 10592673);
/* 100 */     a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
/* 101 */     a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
/* 102 */     a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
/* 103 */     a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
/* 104 */     a(EntitySnowman.class, "SnowMan", 97);
/* 105 */     a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
/* 106 */     a(EntityIronGolem.class, "VillagerGolem", 99);
/* 107 */     a(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
/*     */     
/* 109 */     a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
/*     */     
/* 111 */     a(EntityEnderCrystal.class, "EnderCrystal", 200);
/*     */   }
/*     */   
/*     */   public static Entity createEntityByName(String paramString, World paramWorld) {
/* 115 */     Entity entity = null;
/*     */     try {
/* 117 */       Class<Entity> clazz = (Class)c.get(paramString);
/* 118 */       if (clazz != null) entity = clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */     
/* 120 */     } catch (Exception exception) {
/* 121 */       exception.printStackTrace();
/*     */     } 
/* 123 */     return entity;
/*     */   }
/*     */   
/*     */   public static Entity a(NBTTagCompound paramNBTTagCompound, World paramWorld) {
/* 127 */     Entity entity = null;
/*     */     
/* 129 */     if ("Minecart".equals(paramNBTTagCompound.getString("id"))) {
/*     */ 
/*     */       
/* 132 */       switch (paramNBTTagCompound.getInt("Type")) {
/*     */         case 1:
/* 134 */           paramNBTTagCompound.setString("id", "MinecartChest");
/*     */           break;
/*     */         case 2:
/* 137 */           paramNBTTagCompound.setString("id", "MinecartFurnace");
/*     */           break;
/*     */         case 0:
/* 140 */           paramNBTTagCompound.setString("id", "MinecartRideable");
/*     */           break;
/*     */       } 
/*     */       
/* 144 */       paramNBTTagCompound.remove("Type");
/*     */     } 
/*     */     
/*     */     try {
/* 148 */       Class<Entity> clazz = (Class)c.get(paramNBTTagCompound.getString("id"));
/* 149 */       if (clazz != null) entity = clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */     
/* 151 */     } catch (Exception exception) {
/* 152 */       exception.printStackTrace();
/*     */     } 
/* 154 */     if (entity != null) {
/* 155 */       entity.f(paramNBTTagCompound);
/*     */     } else {
/* 157 */       b.warn("Skipping Entity with id " + paramNBTTagCompound.getString("id"));
/*     */     } 
/* 159 */     return entity;
/*     */   }
/*     */   
/*     */   public static Entity a(int paramInt, World paramWorld) {
/* 163 */     Entity entity = null;
/*     */     try {
/* 165 */       Class<Entity> clazz = a(paramInt);
/* 166 */       if (clazz != null) entity = clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */     
/* 168 */     } catch (Exception exception) {
/* 169 */       exception.printStackTrace();
/*     */     } 
/* 171 */     if (entity == null) {
/* 172 */       b.warn("Skipping Entity with id " + paramInt);
/*     */     }
/* 174 */     return entity;
/*     */   }
/*     */   
/*     */   public static int a(Entity paramEntity) {
/* 178 */     Class<?> clazz = paramEntity.getClass();
/*     */     
/* 180 */     return f.containsKey(clazz) ? ((Integer)f.get(clazz)).intValue() : 0;
/*     */   }
/*     */   
/*     */   public static Class a(int paramInt) {
/* 184 */     return (Class)e.get(Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public static String b(Entity paramEntity) {
/* 188 */     return (String)d.get(paramEntity.getClass());
/*     */   }
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
/*     */   public static String b(int paramInt) {
/* 201 */     Class clazz = a(paramInt);
/*     */     
/* 203 */     if (clazz != null) {
/* 204 */       return (String)d.get(clazz);
/*     */     }
/*     */     
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void a() {}
/*     */   
/*     */   public static Set b() {
/* 214 */     return Collections.unmodifiableSet(g.keySet());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */