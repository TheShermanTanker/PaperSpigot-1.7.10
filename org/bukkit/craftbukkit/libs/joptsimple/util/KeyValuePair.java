/*    */ package org.bukkit.craftbukkit.libs.joptsimple.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class KeyValuePair
/*    */ {
/*    */   public final String key;
/*    */   public final String value;
/*    */   
/*    */   private KeyValuePair(String key, String value) {
/* 44 */     this.key = key;
/* 45 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeyValuePair valueOf(String asString) {
/* 56 */     int equalsIndex = asString.indexOf('=');
/* 57 */     if (equalsIndex == -1) {
/* 58 */       return new KeyValuePair(asString, "");
/*    */     }
/* 60 */     String aKey = asString.substring(0, equalsIndex);
/* 61 */     String aValue = (equalsIndex == asString.length() - 1) ? "" : asString.substring(equalsIndex + 1);
/*    */     
/* 63 */     return new KeyValuePair(aKey, aValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 68 */     if (!(that instanceof KeyValuePair)) {
/* 69 */       return false;
/*    */     }
/* 71 */     KeyValuePair other = (KeyValuePair)that;
/* 72 */     return (this.key.equals(other.key) && this.value.equals(other.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.key.hashCode() ^ this.value.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return this.key + '=' + this.value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimpl\\util\KeyValuePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */