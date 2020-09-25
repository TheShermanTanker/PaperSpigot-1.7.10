/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.Maps;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RegistrySimple
/*    */   implements IRegistry
/*    */ {
/* 13 */   private static final Logger a = LogManager.getLogger();
/* 14 */   protected final Map c = a();
/*    */   
/*    */   protected Map a() {
/* 17 */     return Maps.newHashMap();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object paramObject) {
/* 22 */     return this.c.get(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(Object paramObject1, Object paramObject2) {
/* 27 */     if (this.c.containsKey(paramObject1)) {
/* 28 */       a.debug("Adding duplicate key '" + paramObject1 + "' to registry");
/*    */     }
/* 30 */     this.c.put(paramObject1, paramObject2);
/*    */   }
/*    */   
/*    */   public Set keySet() {
/* 34 */     return Collections.unmodifiableSet(this.c.keySet());
/*    */   }
/*    */   
/*    */   public boolean d(Object paramObject) {
/* 38 */     return this.c.containsKey(paramObject);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RegistrySimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */