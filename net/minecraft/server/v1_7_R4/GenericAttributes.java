/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class GenericAttributes
/*     */ {
/*  12 */   private static final Logger f = LogManager.getLogger();
/*     */   
/*  14 */   public static final IAttribute maxHealth = (new AttributeRanged("generic.maxHealth", 20.0D, 0.1D, SpigotConfig.maxHealth)).a("Max Health").a(true);
/*  15 */   public static final IAttribute b = (new AttributeRanged("generic.followRange", 32.0D, 0.0D, 2048.0D)).a("Follow Range");
/*  16 */   public static final IAttribute c = (new AttributeRanged("generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).a("Knockback Resistance");
/*  17 */   public static final IAttribute d = (new AttributeRanged("generic.movementSpeed", 0.699999988079071D, 0.0D, SpigotConfig.movementSpeed)).a("Movement Speed").a(true);
/*  18 */   public static final IAttribute e = new AttributeRanged("generic.attackDamage", 2.0D, 0.0D, SpigotConfig.attackDamage);
/*     */ 
/*     */   
/*     */   public static NBTTagList a(AttributeMapBase attributemapbase) {
/*  22 */     NBTTagList nbttaglist = new NBTTagList();
/*  23 */     Iterator<AttributeInstance> iterator = attributemapbase.a().iterator();
/*     */     
/*  25 */     while (iterator.hasNext()) {
/*  26 */       AttributeInstance attributeinstance = iterator.next();
/*     */       
/*  28 */       nbttaglist.add(a(attributeinstance));
/*     */     } 
/*     */     
/*  31 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   private static NBTTagCompound a(AttributeInstance attributeinstance) {
/*  35 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  36 */     IAttribute iattribute = attributeinstance.getAttribute();
/*     */     
/*  38 */     nbttagcompound.setString("Name", iattribute.getName());
/*  39 */     nbttagcompound.setDouble("Base", attributeinstance.b());
/*  40 */     Collection collection = attributeinstance.c();
/*     */     
/*  42 */     if (collection != null && !collection.isEmpty()) {
/*  43 */       NBTTagList nbttaglist = new NBTTagList();
/*  44 */       Iterator<AttributeModifier> iterator = collection.iterator();
/*     */       
/*  46 */       while (iterator.hasNext()) {
/*  47 */         AttributeModifier attributemodifier = iterator.next();
/*     */         
/*  49 */         if (attributemodifier.e()) {
/*  50 */           nbttaglist.add(a(attributemodifier));
/*     */         }
/*     */       } 
/*     */       
/*  54 */       nbttagcompound.set("Modifiers", nbttaglist);
/*     */     } 
/*     */     
/*  57 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   private static NBTTagCompound a(AttributeModifier attributemodifier) {
/*  61 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  63 */     nbttagcompound.setString("Name", attributemodifier.b());
/*  64 */     nbttagcompound.setDouble("Amount", attributemodifier.d());
/*  65 */     nbttagcompound.setInt("Operation", attributemodifier.c());
/*  66 */     nbttagcompound.setLong("UUIDMost", attributemodifier.a().getMostSignificantBits());
/*  67 */     nbttagcompound.setLong("UUIDLeast", attributemodifier.a().getLeastSignificantBits());
/*  68 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public static void a(AttributeMapBase attributemapbase, NBTTagList nbttaglist) {
/*  72 */     for (int i = 0; i < nbttaglist.size(); i++) {
/*  73 */       NBTTagCompound nbttagcompound = nbttaglist.get(i);
/*  74 */       AttributeInstance attributeinstance = attributemapbase.a(nbttagcompound.getString("Name"));
/*     */       
/*  76 */       if (attributeinstance != null) {
/*  77 */         a(attributeinstance, nbttagcompound);
/*     */       } else {
/*  79 */         f.warn("Ignoring unknown attribute '" + nbttagcompound.getString("Name") + "'");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void a(AttributeInstance attributeinstance, NBTTagCompound nbttagcompound) {
/*  85 */     attributeinstance.setValue(nbttagcompound.getDouble("Base"));
/*  86 */     if (nbttagcompound.hasKeyOfType("Modifiers", 9)) {
/*  87 */       NBTTagList nbttaglist = nbttagcompound.getList("Modifiers", 10);
/*     */       
/*  89 */       for (int i = 0; i < nbttaglist.size(); i++) {
/*  90 */         AttributeModifier attributemodifier = a(nbttaglist.get(i));
/*  91 */         AttributeModifier attributemodifier1 = attributeinstance.a(attributemodifier.a());
/*     */         
/*  93 */         if (attributemodifier1 != null) {
/*  94 */           attributeinstance.b(attributemodifier1);
/*     */         }
/*     */         
/*  97 */         attributeinstance.a(attributemodifier);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static AttributeModifier a(NBTTagCompound nbttagcompound) {
/* 103 */     UUID uuid = new UUID(nbttagcompound.getLong("UUIDMost"), nbttagcompound.getLong("UUIDLeast"));
/*     */     
/* 105 */     return new AttributeModifier(uuid, nbttagcompound.getString("Name"), nbttagcompound.getDouble("Amount"), nbttagcompound.getInt("Operation"));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenericAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */