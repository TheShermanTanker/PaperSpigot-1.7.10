/*    */ package com.avaje.ebeaninternal.server.ddl;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*    */ import com.avaje.ebeaninternal.server.deploy.InheritInfoVisitor;
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
/*    */ public abstract class AbstractBeanVisitor
/*    */   implements BeanVisitor
/*    */ {
/*    */   public void visitInheritanceProperties(BeanDescriptor<?> descriptor, PropertyVisitor pv) {
/* 39 */     InheritInfo inheritInfo = descriptor.getInheritInfo();
/* 40 */     if (inheritInfo != null && inheritInfo.isRoot()) {
/*    */       
/* 42 */       InheritChildVisitor childVisitor = new InheritChildVisitor(pv);
/* 43 */       inheritInfo.visitChildren(childVisitor);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected static class InheritChildVisitor
/*    */     implements InheritInfoVisitor
/*    */   {
/*    */     final PropertyVisitor pv;
/*    */ 
/*    */ 
/*    */     
/*    */     protected InheritChildVisitor(PropertyVisitor pv) {
/* 57 */       this.pv = pv;
/*    */     }
/*    */     
/*    */     public void visit(InheritInfo inheritInfo) {
/* 61 */       BeanProperty[] propertiesLocal = inheritInfo.getBeanDescriptor().propertiesLocal();
/* 62 */       VisitorUtil.visit(propertiesLocal, this.pv);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\AbstractBeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */