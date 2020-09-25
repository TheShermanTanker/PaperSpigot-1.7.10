/*    */ package org.bukkit.metadata;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.util.NumberConversions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MetadataValueAdapter
/*    */   implements MetadataValue
/*    */ {
/*    */   protected final WeakReference<Plugin> owningPlugin;
/*    */   
/*    */   protected MetadataValueAdapter(Plugin owningPlugin) {
/* 20 */     Validate.notNull(owningPlugin, "owningPlugin cannot be null");
/* 21 */     this.owningPlugin = new WeakReference<Plugin>(owningPlugin);
/*    */   }
/*    */   
/*    */   public Plugin getOwningPlugin() {
/* 25 */     return this.owningPlugin.get();
/*    */   }
/*    */   
/*    */   public int asInt() {
/* 29 */     return NumberConversions.toInt(value());
/*    */   }
/*    */   
/*    */   public float asFloat() {
/* 33 */     return NumberConversions.toFloat(value());
/*    */   }
/*    */   
/*    */   public double asDouble() {
/* 37 */     return NumberConversions.toDouble(value());
/*    */   }
/*    */   
/*    */   public long asLong() {
/* 41 */     return NumberConversions.toLong(value());
/*    */   }
/*    */   
/*    */   public short asShort() {
/* 45 */     return NumberConversions.toShort(value());
/*    */   }
/*    */   
/*    */   public byte asByte() {
/* 49 */     return NumberConversions.toByte(value());
/*    */   }
/*    */   
/*    */   public boolean asBoolean() {
/* 53 */     Object value = value();
/* 54 */     if (value instanceof Boolean) {
/* 55 */       return ((Boolean)value).booleanValue();
/*    */     }
/*    */     
/* 58 */     if (value instanceof Number) {
/* 59 */       return (((Number)value).intValue() != 0);
/*    */     }
/*    */     
/* 62 */     if (value instanceof String) {
/* 63 */       return Boolean.parseBoolean((String)value);
/*    */     }
/*    */     
/* 66 */     return (value != null);
/*    */   }
/*    */   
/*    */   public String asString() {
/* 70 */     Object value = value();
/*    */     
/* 72 */     if (value == null) {
/* 73 */       return "";
/*    */     }
/* 75 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\MetadataValueAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */