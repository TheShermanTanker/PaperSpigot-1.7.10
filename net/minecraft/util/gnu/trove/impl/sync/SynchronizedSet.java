/*    */ package net.minecraft.util.gnu.trove.impl.sync;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ class SynchronizedSet<E>
/*    */   extends SynchronizedCollection<E>
/*    */   implements Set<E>
/*    */ {
/*    */   private static final long serialVersionUID = 487447009682186044L;
/*    */   
/*    */   SynchronizedSet(Set<E> s, Object mutex) {
/* 28 */     super(s, mutex);
/* 29 */   } public boolean equals(Object o) { synchronized (this.mutex) { return this.c.equals(o); }
/* 30 */      } public int hashCode() { synchronized (this.mutex) { return this.c.hashCode(); }
/*    */      }
/*    */ 
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\impl\sync\SynchronizedSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */