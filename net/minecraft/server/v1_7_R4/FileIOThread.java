/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ 
/*    */ public class FileIOThread implements Runnable {
/*  6 */   public static final FileIOThread a = new FileIOThread();
/*    */   
/*  8 */   private List b = Collections.synchronizedList(new ArrayList());
/*    */   
/*    */   private volatile long c;
/*    */   private volatile long d;
/*    */   private volatile boolean e;
/*    */   
/*    */   private FileIOThread() {
/* 15 */     Thread thread = new Thread(this, "File IO Thread");
/* 16 */     thread.setPriority(1);
/* 17 */     thread.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/* 23 */       b();
/*    */     }
/*    */   }
/*    */   
/*    */   private void b() {
/* 28 */     for (byte b = 0; b < this.b.size(); b++) {
/* 29 */       IAsyncChunkSaver iAsyncChunkSaver = this.b.get(b);
/* 30 */       boolean bool = iAsyncChunkSaver.c();
/* 31 */       if (!bool) {
/* 32 */         this.b.remove(b--);
/* 33 */         this.d++;
/*    */       } 
/*    */       
/*    */       try {
/* 37 */         Thread.sleep(this.e ? 0L : 10L);
/* 38 */       } catch (InterruptedException interruptedException) {
/* 39 */         interruptedException.printStackTrace();
/*    */       } 
/*    */     } 
/* 42 */     if (this.b.isEmpty()) {
/*    */       try {
/* 44 */         Thread.sleep(25L);
/* 45 */       } catch (InterruptedException interruptedException) {
/* 46 */         interruptedException.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(IAsyncChunkSaver paramIAsyncChunkSaver) {
/* 52 */     if (this.b.contains(paramIAsyncChunkSaver))
/* 53 */       return;  this.c++;
/* 54 */     this.b.add(paramIAsyncChunkSaver);
/*    */   }
/*    */   
/*    */   public void a() {
/* 58 */     this.e = true;
/* 59 */     while (this.c != this.d) {
/* 60 */       Thread.sleep(10L);
/*    */     }
/* 62 */     this.e = false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\FileIOThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */