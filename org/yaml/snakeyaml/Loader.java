/*    */ package org.yaml.snakeyaml;
/*    */ 
/*    */ import org.yaml.snakeyaml.constructor.BaseConstructor;
/*    */ import org.yaml.snakeyaml.constructor.Constructor;
/*    */ import org.yaml.snakeyaml.resolver.Resolver;
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
/*    */ public final class Loader
/*    */ {
/*    */   protected final BaseConstructor constructor;
/*    */   protected Resolver resolver;
/*    */   
/*    */   public Loader(BaseConstructor constructor) {
/* 31 */     this.constructor = constructor;
/*    */   }
/*    */   
/*    */   public Loader() {
/* 35 */     this((BaseConstructor)new Constructor());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\Loader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */