/*    */ package net.minecraft.util.io.netty.util;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*    */ public final class AttributeKey<T>
/*    */   extends UniqueName
/*    */ {
/* 32 */   private static final ConcurrentMap<String, Boolean> names = PlatformDependent.newConcurrentHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttributeKey(String name) {
/* 40 */     super(names, name, new Object[0]);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\AttributeKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */