/*    */ package org.bukkit.util.io;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
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
/*    */ public class BukkitObjectOutputStream
/*    */   extends ObjectOutputStream
/*    */ {
/*    */   protected BukkitObjectOutputStream() throws IOException, SecurityException {
/* 30 */     enableReplaceObject(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BukkitObjectOutputStream(OutputStream out) throws IOException {
/* 41 */     super(out);
/* 42 */     enableReplaceObject(true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object replaceObject(Object<ImmutableMap<String, ?>> obj) throws IOException {
/* 47 */     if (!(obj instanceof java.io.Serializable) && obj instanceof ConfigurationSerializable) {
/* 48 */       obj = (Object<ImmutableMap<String, ?>>)Wrapper.newWrapper((ConfigurationSerializable)obj);
/*    */     }
/*    */     
/* 51 */     return super.replaceObject(obj);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\io\BukkitObjectOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */