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
/*    */ public interface Skeleton
/*    */   extends Monster
/*    */ {
/*    */   SkeletonType getSkeletonType();
/*    */   
/*    */   void setSkeletonType(SkeletonType paramSkeletonType);
/*    */   
/*    */   public enum SkeletonType
/*    */   {
/* 26 */     NORMAL(0),
/* 27 */     WITHER(1);
/*    */     
/* 29 */     private static final SkeletonType[] types = new SkeletonType[(values()).length];
/*    */     private final int id;
/*    */     
/*    */     static {
/* 33 */       for (SkeletonType type : values()) {
/* 34 */         types[type.getId()] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     SkeletonType(int id) {
/* 39 */       this.id = id;
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
/* 50 */       return this.id;
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
/*    */     public static SkeletonType getType(int id) {
/* 62 */       return (id >= types.length) ? null : types[id];
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Skeleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */