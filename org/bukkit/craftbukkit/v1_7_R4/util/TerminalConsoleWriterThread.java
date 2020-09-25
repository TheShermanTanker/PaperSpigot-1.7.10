/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import net.minecraft.util.com.mojang.util.QueueLogAppender;
/*    */ import org.bukkit.craftbukkit.Main;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*    */ 
/*    */ public class TerminalConsoleWriterThread implements Runnable {
/*    */   private final ConsoleReader reader;
/*    */   private final OutputStream output;
/*    */   
/*    */   public TerminalConsoleWriterThread(OutputStream output, ConsoleReader reader) {
/* 16 */     this.output = output;
/* 17 */     this.reader = reader;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/* 25 */       String message = QueueLogAppender.getNextLogEvent("TerminalConsole");
/* 26 */       if (message == null) {
/*    */         continue;
/*    */       }
/*    */       
/*    */       try {
/* 31 */         if (Main.useJline) {
/* 32 */           this.reader.print("\r");
/* 33 */           this.reader.flush();
/* 34 */           this.output.write(message.getBytes());
/* 35 */           this.output.flush();
/*    */           
/*    */           try {
/* 38 */             this.reader.drawLine();
/* 39 */           } catch (Throwable ex) {
/* 40 */             this.reader.getCursorBuffer().clear();
/*    */           } 
/* 42 */           this.reader.flush(); continue;
/*    */         } 
/* 44 */         this.output.write(message.getBytes());
/* 45 */         this.output.flush();
/*    */       }
/* 47 */       catch (IOException ex) {
/* 48 */         Logger.getLogger(TerminalConsoleWriterThread.class.getName()).log(Level.SEVERE, (String)null, ex);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\TerminalConsoleWriterThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */