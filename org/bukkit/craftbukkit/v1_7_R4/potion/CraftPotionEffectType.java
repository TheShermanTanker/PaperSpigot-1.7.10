/*    */ package org.bukkit.craftbukkit.v1_7_R4.potion;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.MobEffectList;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class CraftPotionEffectType
/*    */   extends PotionEffectType {
/*    */   private final MobEffectList handle;
/*    */   
/*    */   public CraftPotionEffectType(MobEffectList handle) {
/* 11 */     super(handle.id);
/* 12 */     this.handle = handle;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurationModifier() {
/* 17 */     return this.handle.getDurationModifier();
/*    */   }
/*    */   
/*    */   public MobEffectList getHandle() {
/* 21 */     return this.handle;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     switch (this.handle.id) {
/*    */       case 1:
/* 28 */         return "SPEED";
/*    */       case 2:
/* 30 */         return "SLOW";
/*    */       case 3:
/* 32 */         return "FAST_DIGGING";
/*    */       case 4:
/* 34 */         return "SLOW_DIGGING";
/*    */       case 5:
/* 36 */         return "INCREASE_DAMAGE";
/*    */       case 6:
/* 38 */         return "HEAL";
/*    */       case 7:
/* 40 */         return "HARM";
/*    */       case 8:
/* 42 */         return "JUMP";
/*    */       case 9:
/* 44 */         return "CONFUSION";
/*    */       case 10:
/* 46 */         return "REGENERATION";
/*    */       case 11:
/* 48 */         return "DAMAGE_RESISTANCE";
/*    */       case 12:
/* 50 */         return "FIRE_RESISTANCE";
/*    */       case 13:
/* 52 */         return "WATER_BREATHING";
/*    */       case 14:
/* 54 */         return "INVISIBILITY";
/*    */       case 15:
/* 56 */         return "BLINDNESS";
/*    */       case 16:
/* 58 */         return "NIGHT_VISION";
/*    */       case 17:
/* 60 */         return "HUNGER";
/*    */       case 18:
/* 62 */         return "WEAKNESS";
/*    */       case 19:
/* 64 */         return "POISON";
/*    */       case 20:
/* 66 */         return "WITHER";
/*    */       case 21:
/* 68 */         return "HEALTH_BOOST";
/*    */       case 22:
/* 70 */         return "ABSORPTION";
/*    */       case 23:
/* 72 */         return "SATURATION";
/*    */     } 
/* 74 */     return "UNKNOWN_EFFECT_TYPE_" + this.handle.id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInstant() {
/* 80 */     return this.handle.isInstant();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\potion\CraftPotionEffectType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */