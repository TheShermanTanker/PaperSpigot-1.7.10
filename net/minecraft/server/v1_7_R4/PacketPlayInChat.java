/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*    */ import java.io.IOException;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ 
/*    */ public class PacketPlayInChat extends Packet {
/*    */   private String message;
/*    */   
/*    */   public PacketPlayInChat(String s) {
/* 12 */     if (s.length() > 100) {
/* 13 */       s = s.substring(0, 100);
/*    */     }
/*    */     
/* 16 */     this.message = s;
/*    */   }
/*    */   public PacketPlayInChat() {}
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 20 */     this.message = packetdataserializer.c(100);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 24 */     packetdataserializer.a(this.message);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 28 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 32 */     return String.format("message='%s'", new Object[] { this.message });
/*    */   }
/*    */   
/*    */   public String c() {
/* 36 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 42 */     return !this.message.startsWith("/");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 47 */   private static final ExecutorService executors = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Async Chat Thread - #%d").build());
/*    */ 
/*    */   
/*    */   public void handle(final PacketListener packetlistener) {
/* 51 */     if (a()) {
/*    */       
/* 53 */       executors.submit(new Runnable()
/*    */           {
/*    */ 
/*    */             
/*    */             public void run()
/*    */             {
/* 59 */               PacketPlayInChat.this.a((PacketPlayInListener)packetlistener);
/*    */             }
/*    */           });
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */