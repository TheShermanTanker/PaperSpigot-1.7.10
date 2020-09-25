/*    */ package net.minecraft.util.org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ public class NullWriter
/*    */   extends Writer
/*    */ {
/* 34 */   public static final NullWriter NULL_WRITER = new NullWriter();
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
/*    */   public Writer append(char c) {
/* 51 */     return this;
/*    */   }
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
/*    */   public Writer append(CharSequence csq, int start, int end) {
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Writer append(CharSequence csq) {
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public void write(int idx) {}
/*    */   
/*    */   public void write(char[] chr) {}
/*    */   
/*    */   public void write(char[] chr, int st, int end) {}
/*    */   
/*    */   public void write(String str) {}
/*    */   
/*    */   public void write(String str, int st, int end) {}
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\io\output\NullWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */