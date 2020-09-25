/*    */ package org.bukkit.craftbukkit.libs.jline.console.completer;
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
/*    */ public class EnumCompleter
/*    */   extends StringsCompleter
/*    */ {
/*    */   public EnumCompleter(Class<? extends Enum> source) {
/* 29 */     assert source != null;
/*    */     
/* 31 */     for (Enum<?> n : (Enum[])source.getEnumConstants())
/* 32 */       getStrings().add(n.name().toLowerCase()); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\EnumCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */