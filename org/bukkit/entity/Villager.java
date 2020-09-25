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
/*    */ public interface Villager
/*    */   extends Ageable, NPC
/*    */ {
/*    */   Profession getProfession();
/*    */   
/*    */   void setProfession(Profession paramProfession);
/*    */   
/*    */   public enum Profession
/*    */   {
/* 27 */     FARMER(0),
/* 28 */     LIBRARIAN(1),
/* 29 */     PRIEST(2),
/* 30 */     BLACKSMITH(3),
/* 31 */     BUTCHER(4);
/*    */     
/* 33 */     private static final Profession[] professions = new Profession[(values()).length];
/*    */     private final int id;
/*    */     
/*    */     static {
/* 37 */       for (Profession type : values()) {
/* 38 */         professions[type.getId()] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     Profession(int id) {
/* 43 */       this.id = id;
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
/* 54 */       return this.id;
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
/*    */     public static Profession getProfession(int id) {
/* 66 */       return (id >= professions.length) ? null : professions[id];
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Villager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */