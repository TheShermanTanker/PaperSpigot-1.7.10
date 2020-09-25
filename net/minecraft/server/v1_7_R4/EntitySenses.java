/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySenses
/*    */ {
/*    */   EntityInsentient entity;
/* 11 */   List seenEntities = new ArrayList();
/* 12 */   List unseenEntities = new ArrayList();
/*    */   
/*    */   public EntitySenses(EntityInsentient paramEntityInsentient) {
/* 15 */     this.entity = paramEntityInsentient;
/*    */   }
/*    */   
/*    */   public void a() {
/* 19 */     this.seenEntities.clear();
/* 20 */     this.unseenEntities.clear();
/*    */   }
/*    */   
/*    */   public boolean canSee(Entity paramEntity) {
/* 24 */     if (this.seenEntities.contains(paramEntity)) return true; 
/* 25 */     if (this.unseenEntities.contains(paramEntity)) return false;
/*    */     
/* 27 */     this.entity.world.methodProfiler.a("canSee");
/* 28 */     boolean bool = this.entity.hasLineOfSight(paramEntity);
/* 29 */     this.entity.world.methodProfiler.b();
/* 30 */     if (bool) { this.seenEntities.add(paramEntity); }
/* 31 */     else { this.unseenEntities.add(paramEntity); }
/* 32 */      return bool;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySenses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */