/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.CoalType;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class Coal
/*    */   extends MaterialData
/*    */ {
/*    */   public Coal() {
/* 11 */     super(Material.COAL);
/*    */   }
/*    */   
/*    */   public Coal(CoalType type) {
/* 15 */     this();
/* 16 */     setType(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Coal(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Coal(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Coal(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Coal(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CoalType getType() {
/* 56 */     return CoalType.getByData(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setType(CoalType type) {
/* 65 */     setData(type.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getType() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Coal clone() {
/* 75 */     return (Coal)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Coal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */