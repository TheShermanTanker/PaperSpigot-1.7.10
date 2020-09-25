/*    */ package com.avaje.ebeaninternal.server.deploy;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import scala.collection.mutable.Buffer;
/*    */ import scala.collection.mutable.Map;
/*    */ import scala.collection.mutable.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DetermineManyType
/*    */ {
/*    */   private final boolean withScalaSupport;
/*    */   private final ManyType scalaBufMany;
/*    */   private final ManyType scalaSetMany;
/*    */   private final ManyType scalaMapMany;
/*    */   
/*    */   public DetermineManyType(boolean withScalaSupport) {
/* 22 */     this.withScalaSupport = withScalaSupport;
/* 23 */     if (withScalaSupport) {
/*    */       
/* 25 */       CollectionTypeConverter bufConverter = new ScalaBufferConverter();
/* 26 */       CollectionTypeConverter setConverter = new ScalaSetConverter();
/* 27 */       CollectionTypeConverter mapConverter = new ScalaMapConverter();
/*    */       
/* 29 */       this.scalaBufMany = new ManyType(ManyType.Underlying.LIST, bufConverter);
/* 30 */       this.scalaSetMany = new ManyType(ManyType.Underlying.SET, setConverter);
/* 31 */       this.scalaMapMany = new ManyType(ManyType.Underlying.MAP, mapConverter);
/*    */     } else {
/*    */       
/* 34 */       this.scalaBufMany = null;
/* 35 */       this.scalaSetMany = null;
/* 36 */       this.scalaMapMany = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public ManyType getManyType(Class<?> type) {
/* 41 */     if (type.equals(List.class)) {
/* 42 */       return ManyType.JAVA_LIST;
/*    */     }
/* 44 */     if (type.equals(Set.class)) {
/* 45 */       return ManyType.JAVA_SET;
/*    */     }
/* 47 */     if (type.equals(Map.class)) {
/* 48 */       return ManyType.JAVA_MAP;
/*    */     }
/* 50 */     if (this.withScalaSupport) {
/*    */       
/* 52 */       if (type.equals(Buffer.class)) {
/* 53 */         return this.scalaBufMany;
/*    */       }
/* 55 */       if (type.equals(Set.class)) {
/* 56 */         return this.scalaSetMany;
/*    */       }
/* 58 */       if (type.equals(Map.class)) {
/* 59 */         return this.scalaMapMany;
/*    */       }
/*    */     } 
/* 62 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DetermineManyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */