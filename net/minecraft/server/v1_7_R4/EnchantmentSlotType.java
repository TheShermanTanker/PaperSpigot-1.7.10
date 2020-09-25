/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public enum EnchantmentSlotType
/*    */ {
/*  6 */   ALL,
/*    */   
/*  8 */   ARMOR,
/*  9 */   ARMOR_FEET,
/* 10 */   ARMOR_LEGS,
/* 11 */   ARMOR_TORSO,
/* 12 */   ARMOR_HEAD,
/*    */   
/* 14 */   WEAPON,
/* 15 */   DIGGER,
/* 16 */   FISHING_ROD,
/* 17 */   BREAKABLE,
/* 18 */   BOW;
/*    */   
/*    */   public boolean canEnchant(Item paramItem) {
/* 21 */     if (this == ALL) return true; 
/* 22 */     if (this == BREAKABLE && paramItem.usesDurability()) return true;
/*    */     
/* 24 */     if (paramItem instanceof ItemArmor) {
/* 25 */       if (this == ARMOR) return true; 
/* 26 */       ItemArmor itemArmor = (ItemArmor)paramItem;
/* 27 */       if (itemArmor.b == 0) return (this == ARMOR_HEAD); 
/* 28 */       if (itemArmor.b == 2) return (this == ARMOR_LEGS); 
/* 29 */       if (itemArmor.b == 1) return (this == ARMOR_TORSO); 
/* 30 */       if (itemArmor.b == 3) return (this == ARMOR_FEET); 
/* 31 */       return false;
/* 32 */     }  if (paramItem instanceof ItemSword)
/* 33 */       return (this == WEAPON); 
/* 34 */     if (paramItem instanceof ItemTool)
/* 35 */       return (this == DIGGER); 
/* 36 */     if (paramItem instanceof ItemBow)
/* 37 */       return (this == BOW); 
/* 38 */     if (paramItem instanceof ItemFishingRod) {
/* 39 */       return (this == FISHING_ROD);
/*    */     }
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnchantmentSlotType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */