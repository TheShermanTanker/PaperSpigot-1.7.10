/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "SerializedLayout", category = "Core", elementType = "layout", printObject = true)
/*     */ public final class SerializedLayout
/*     */   extends AbstractLayout<LogEvent>
/*     */ {
/*     */   private static byte[] header;
/*     */   
/*     */   static {
/*  39 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     try {
/*  41 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/*  42 */       oos.close();
/*  43 */       header = baos.toByteArray();
/*  44 */     } catch (Exception ex) {
/*  45 */       LOGGER.error("Unable to generate Object stream header", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray(LogEvent event) {
/*  60 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     try {
/*  62 */       ObjectOutputStream oos = new PrivateObjectOutputStream(baos);
/*     */       try {
/*  64 */         oos.writeObject(event);
/*  65 */         oos.reset();
/*     */       } finally {
/*  67 */         oos.close();
/*     */       } 
/*  69 */     } catch (IOException ioe) {
/*  70 */       LOGGER.error("Serialization of LogEvent failed.", ioe);
/*     */     } 
/*  72 */     return baos.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogEvent toSerializable(LogEvent event) {
/*  83 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static SerializedLayout createLayout() {
/*  93 */     return new SerializedLayout();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getHeader() {
/*  98 */     return header;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 107 */     return new HashMap<String, String>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 116 */     return "application/octet-stream";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class PrivateObjectOutputStream
/*     */     extends ObjectOutputStream
/*     */   {
/*     */     public PrivateObjectOutputStream(OutputStream os) throws IOException {
/* 125 */       super(os);
/*     */     }
/*     */     
/*     */     protected void writeStreamHeader() {}
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\SerializedLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */