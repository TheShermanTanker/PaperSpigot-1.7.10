/*     */ package net.minecraft.util.com.mojang.authlib;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.builder.ToStringBuilder;
/*     */ 
/*     */ public class GameProfile
/*     */ {
/*     */   private final UUID id;
/*     */   private final String name;
/*  12 */   private final PropertyMap properties = new PropertyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean legacy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile(UUID id, String name) {
/*  25 */     if (id == null && StringUtils.isBlank(name)) throw new IllegalArgumentException("Name and ID cannot both be blank");
/*     */     
/*  27 */     this.id = id;
/*  28 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getId() {
/*  39 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyMap getProperties() {
/*  59 */     return this.properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  70 */     return (this.id != null && StringUtils.isNotBlank(getName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  75 */     if (this == o) return true; 
/*  76 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  78 */     GameProfile that = (GameProfile)o;
/*     */     
/*  80 */     if ((this.id != null) ? !this.id.equals(that.id) : (that.id != null)) return false; 
/*  81 */     if ((this.name != null) ? !this.name.equals(that.name) : (that.name != null)) return false;
/*     */     
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  88 */     int result = (this.id != null) ? this.id.hashCode() : 0;
/*  89 */     result = 31 * result + ((this.name != null) ? this.name.hashCode() : 0);
/*  90 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  95 */     return (new ToStringBuilder(this)).append("id", this.id).append("name", this.name).append("properties", this.properties).append("legacy", this.legacy).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLegacy() {
/* 104 */     return this.legacy;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\GameProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */