/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.com.google.common.collect.Iterables;
/*     */ import net.minecraft.util.com.mojang.authlib.Agent;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntitySkull
/*     */   extends TileEntity
/*     */ {
/*     */   private int a;
/*     */   private int i;
/*  25 */   private GameProfile j = null;
/*     */   
/*  27 */   public static final Executor executor = Executors.newFixedThreadPool(3, (new ThreadFactoryBuilder()).setNameFormat("Head Conversion Thread - %1$d").build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   public static final Cache<String, GameProfile> skinCache = CacheBuilder.newBuilder().maximumSize(5000).expireAfterAccess(60L, TimeUnit.MINUTES).build(new CacheLoader<String, GameProfile>()
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public GameProfile load(String key) throws Exception
/*     */         {
/*  40 */           GameProfile[] profiles = new GameProfile[1];
/*  41 */           GameProfileLookup gameProfileLookup = new GameProfileLookup(profiles);
/*     */           
/*  43 */           MinecraftServer.getServer().getGameProfileRepository().findProfilesByNames(new String[] { key }, Agent.MINECRAFT, gameProfileLookup);
/*     */           
/*  45 */           GameProfile profile = profiles[0];
/*  46 */           if (profile == null) {
/*  47 */             UUID uuid = EntityHuman.a(new GameProfile(null, key));
/*  48 */             profile = new GameProfile(uuid, key);
/*     */             
/*  50 */             gameProfileLookup.onProfileLookupSucceeded(profile);
/*     */           }
/*     */           else {
/*     */             
/*  54 */             Property property = (Property)Iterables.getFirst(profile.getProperties().get("textures"), null);
/*     */             
/*  56 */             if (property == null)
/*     */             {
/*  58 */               profile = MinecraftServer.getServer().av().fillProfileProperties(profile, true);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*  63 */           return profile;
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  71 */     super.b(nbttagcompound);
/*  72 */     nbttagcompound.setByte("SkullType", (byte)(this.a & 0xFF));
/*  73 */     nbttagcompound.setByte("Rot", (byte)(this.i & 0xFF));
/*  74 */     if (this.j != null) {
/*  75 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/*  77 */       GameProfileSerializer.serialize(nbttagcompound1, this.j);
/*  78 */       nbttagcompound.set("Owner", nbttagcompound1);
/*  79 */       nbttagcompound.setString("ExtraType", nbttagcompound1.getString("Name"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  84 */     super.a(nbttagcompound);
/*  85 */     this.a = nbttagcompound.getByte("SkullType");
/*  86 */     this.i = nbttagcompound.getByte("Rot");
/*  87 */     if (this.a == 3) {
/*  88 */       if (nbttagcompound.hasKeyOfType("Owner", 10)) {
/*  89 */         this.j = GameProfileSerializer.deserialize(nbttagcompound.getCompound("Owner"));
/*  90 */       } else if (nbttagcompound.hasKeyOfType("ExtraType", 8) && !UtilColor.b(nbttagcompound.getString("ExtraType"))) {
/*  91 */         this.j = new GameProfile((UUID)null, nbttagcompound.getString("ExtraType"));
/*  92 */         d();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public GameProfile getGameProfile() {
/*  98 */     return this.j;
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/* 102 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 104 */     b(nbttagcompound);
/* 105 */     return new PacketPlayOutTileEntityData(this.x, this.y, this.z, 4, nbttagcompound);
/*     */   }
/*     */   
/*     */   public void setSkullType(int i) {
/* 109 */     this.a = i;
/* 110 */     this.j = null;
/*     */   }
/*     */   
/*     */   public void setGameProfile(GameProfile gameprofile) {
/* 114 */     this.a = 3;
/* 115 */     this.j = gameprofile;
/* 116 */     d();
/*     */   }
/*     */   
/*     */   private void d() {
/* 120 */     if (this.j != null && !UtilColor.b(this.j.getName()) && (
/* 121 */       !this.j.isComplete() || !this.j.getProperties().containsKey("textures"))) {
/*     */       
/* 123 */       final String name = this.j.getName();
/* 124 */       setSkullType(0);
/* 125 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 129 */               GameProfile profile = (GameProfile)TileEntitySkull.skinCache.getUnchecked(name.toLowerCase());
/*     */               
/* 131 */               if (profile != null) {
/* 132 */                 final GameProfile finalProfile = profile;
/* 133 */                 (MinecraftServer.getServer()).processQueue.add(new Runnable()
/*     */                     {
/*     */                       public void run() {
/* 136 */                         TileEntitySkull.this.a = 3;
/* 137 */                         TileEntitySkull.this.j = finalProfile;
/* 138 */                         TileEntitySkull.this.world.notify(TileEntitySkull.this.x, TileEntitySkull.this.y, TileEntitySkull.this.z);
/*     */                       }
/*     */                     });
/*     */               } else {
/* 142 */                 (MinecraftServer.getServer()).processQueue.add(new Runnable()
/*     */                     {
/*     */                       public void run() {
/* 145 */                         TileEntitySkull.this.a = 3;
/* 146 */                         TileEntitySkull.this.j = new GameProfile(null, name);
/* 147 */                         TileEntitySkull.this.world.notify(TileEntitySkull.this.x, TileEntitySkull.this.y, TileEntitySkull.this.z);
/*     */                       }
/*     */                     });
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkullType() {
/* 159 */     return this.a;
/*     */   }
/*     */   
/*     */   public void setRotation(int i) {
/* 163 */     this.i = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRotation() {
/* 168 */     return this.i;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntitySkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */