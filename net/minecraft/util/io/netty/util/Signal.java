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
/*    */ public final class Signal
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = -221145131122459977L;
/* 31 */   private static final ConcurrentMap<String, Boolean> map = PlatformDependent.newConcurrentHashMap();
/*    */ 
/*    */ 
/*    */   
/*    */   private final UniqueName uname;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Signal(String name) {
/* 41 */     super(name);
/* 42 */     this.uname = new UniqueName(map, name, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void expect(Signal signal) {
/* 50 */     if (this != signal) {
/* 51 */       throw new IllegalStateException("unexpected signal: " + signal);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable initCause(Throwable cause) {
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return this.uname.name();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\Signal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */