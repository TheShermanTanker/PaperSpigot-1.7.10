/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
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
/*    */ @GwtIncompatible("unnecessary")
/*    */ abstract class ImmutableMapEntry<K, V>
/*    */   extends ImmutableEntry<K, V>
/*    */ {
/*    */   ImmutableMapEntry(K key, V value) {
/* 36 */     super(key, value);
/* 37 */     CollectPreconditions.checkEntryNotNull(key, value);
/*    */   }
/*    */   
/*    */   ImmutableMapEntry(ImmutableMapEntry<K, V> contents) {
/* 41 */     super(contents.getKey(), contents.getValue());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   abstract ImmutableMapEntry<K, V> getNextInKeyBucket();
/*    */   
/*    */   @Nullable
/*    */   abstract ImmutableMapEntry<K, V> getNextInValueBucket();
/*    */   
/*    */   static final class TerminalEntry<K, V>
/*    */     extends ImmutableMapEntry<K, V> {
/*    */     TerminalEntry(ImmutableMapEntry<K, V> contents) {
/* 53 */       super(contents);
/*    */     }
/*    */     
/*    */     TerminalEntry(K key, V value) {
/* 57 */       super(key, value);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     ImmutableMapEntry<K, V> getNextInKeyBucket() {
/* 63 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     ImmutableMapEntry<K, V> getNextInValueBucket() {
/* 69 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ImmutableMapEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */