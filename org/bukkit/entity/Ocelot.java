/*    */ package org.bukkit.entity;
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
/*    */ public interface Ocelot
/*    */   extends Animals, Tameable
/*    */ {
/*    */   Type getCatType();
/*    */   
/*    */   void setCatType(Type paramType);
/*    */   
/*    */   boolean isSitting();
/*    */   
/*    */   void setSitting(boolean paramBoolean);
/*    */   
/*    */   public enum Type
/*    */   {
/* 42 */     WILD_OCELOT(0),
/* 43 */     BLACK_CAT(1),
/* 44 */     RED_CAT(2),
/* 45 */     SIAMESE_CAT(3);
/*    */     
/* 47 */     private static final Type[] types = new Type[(values()).length];
/*    */     private final int id;
/*    */     
/*    */     static {
/* 51 */       for (Type type : values()) {
/* 52 */         types[type.getId()] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     Type(int id) {
/* 57 */       this.id = id;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Deprecated
/*    */     public int getId() {
/* 68 */       return this.id;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Deprecated
/*    */     public static Type getType(int id) {
/* 80 */       return (id >= types.length) ? null : types[id];
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Ocelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */