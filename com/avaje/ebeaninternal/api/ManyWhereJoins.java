/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
/*    */ import com.avaje.ebeaninternal.server.query.SplitName;
/*    */ import java.io.Serializable;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
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
/*    */ public class ManyWhereJoins
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6490181101871795417L;
/* 39 */   private final TreeSet<String> joins = new TreeSet<String>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(ElPropertyDeploy elProp) {
/* 46 */     String join = elProp.getElPrefix();
/* 47 */     BeanProperty p = elProp.getBeanProperty();
/* 48 */     if (p instanceof com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany) {
/* 49 */       join = addManyToJoin(join, p.getName());
/*    */     }
/* 51 */     if (join != null) {
/* 52 */       this.joins.add(join);
/* 53 */       String secondaryTableJoinPrefix = p.getSecondaryTableJoinPrefix();
/* 54 */       if (secondaryTableJoinPrefix != null) {
/* 55 */         this.joins.add(join + "." + secondaryTableJoinPrefix);
/*    */       }
/* 57 */       addParentJoins(join);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String addManyToJoin(String join, String manyPropName) {
/* 66 */     if (join == null) {
/* 67 */       return manyPropName;
/*    */     }
/* 69 */     return join + "." + manyPropName;
/*    */   }
/*    */ 
/*    */   
/*    */   private void addParentJoins(String join) {
/* 74 */     String[] split = SplitName.split(join);
/* 75 */     if (split[0] != null) {
/* 76 */       this.joins.add(split[0]);
/* 77 */       addParentJoins(split[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 85 */     return this.joins.isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<String> getJoins() {
/* 92 */     return this.joins;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\ManyWhereJoins.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */