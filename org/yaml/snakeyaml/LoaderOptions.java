/*    */ package org.yaml.snakeyaml;
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
/*    */ public class LoaderOptions
/*    */ {
/*    */   private TypeDescription rootTypeDescription;
/*    */   
/*    */   public LoaderOptions() {
/* 25 */     this(new TypeDescription(Object.class));
/*    */   }
/*    */   
/*    */   public LoaderOptions(TypeDescription rootTypeDescription) {
/* 29 */     this.rootTypeDescription = rootTypeDescription;
/*    */   }
/*    */   
/*    */   public TypeDescription getRootTypeDescription() {
/* 33 */     return this.rootTypeDescription;
/*    */   }
/*    */   
/*    */   public void setRootTypeDescription(TypeDescription rootTypeDescription) {
/* 37 */     this.rootTypeDescription = rootTypeDescription;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\LoaderOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */