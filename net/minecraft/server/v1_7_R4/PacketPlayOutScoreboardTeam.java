/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class PacketPlayOutScoreboardTeam
/*     */   extends Packet {
/*  10 */   private String a = "";
/*  11 */   private String b = "";
/*  12 */   private String c = "";
/*  13 */   private String d = "";
/*  14 */   private Collection e = new ArrayList();
/*     */   private int f;
/*     */   private int g;
/*     */   
/*     */   public PacketPlayOutScoreboardTeam() {}
/*     */   
/*     */   public PacketPlayOutScoreboardTeam(ScoreboardTeam scoreboardteam, int i) {
/*  21 */     this.a = scoreboardteam.getName();
/*  22 */     this.f = i;
/*  23 */     if (i == 0 || i == 2) {
/*  24 */       this.b = scoreboardteam.getDisplayName();
/*  25 */       this.c = scoreboardteam.getPrefix();
/*  26 */       this.d = scoreboardteam.getSuffix();
/*  27 */       this.g = scoreboardteam.packOptionData();
/*     */     } 
/*     */     
/*  30 */     if (i == 0) {
/*  31 */       this.e.addAll(scoreboardteam.getPlayerNameSet());
/*     */     }
/*     */   }
/*     */   
/*     */   public PacketPlayOutScoreboardTeam(ScoreboardTeam scoreboardteam, Collection collection, int i) {
/*  36 */     if (i != 3 && i != 4)
/*  37 */       throw new IllegalArgumentException("Method must be join or leave for player constructor"); 
/*  38 */     if (collection != null && !collection.isEmpty()) {
/*  39 */       this.f = i;
/*  40 */       this.a = scoreboardteam.getName();
/*  41 */       this.e.addAll(collection);
/*     */     } else {
/*  43 */       throw new IllegalArgumentException("Players cannot be null/empty");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/*  48 */     this.a = packetdataserializer.c(16);
/*  49 */     this.f = packetdataserializer.readByte();
/*  50 */     if (this.f == 0 || this.f == 2) {
/*  51 */       this.b = packetdataserializer.c(32);
/*  52 */       this.c = packetdataserializer.c(16);
/*  53 */       this.d = packetdataserializer.c(16);
/*  54 */       this.g = packetdataserializer.readByte();
/*     */     } 
/*     */     
/*  57 */     if (this.f == 0 || this.f == 3 || this.f == 4) {
/*  58 */       short short1 = packetdataserializer.readShort();
/*     */       
/*  60 */       for (int i = 0; i < short1; i++) {
/*  61 */         this.e.add(packetdataserializer.c(40));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/*  67 */     packetdataserializer.a(this.a);
/*  68 */     packetdataserializer.writeByte(this.f);
/*  69 */     if (this.f == 0 || this.f == 2) {
/*  70 */       packetdataserializer.a(this.b);
/*  71 */       packetdataserializer.a(this.c);
/*  72 */       packetdataserializer.a(this.d);
/*  73 */       packetdataserializer.writeByte(this.g);
/*     */       
/*  75 */       if (packetdataserializer.version >= 16) {
/*     */         
/*  77 */         packetdataserializer.a("always");
/*  78 */         packetdataserializer.writeByte(EnumChatFormat.WHITE.ordinal());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  83 */     if (this.f == 0 || this.f == 3 || this.f == 4) {
/*     */       
/*  85 */       if (packetdataserializer.version < 16) {
/*     */         
/*  87 */         packetdataserializer.writeShort(this.e.size());
/*     */       } else {
/*     */         
/*  90 */         packetdataserializer.b(this.e.size());
/*     */       } 
/*     */       
/*  93 */       Iterator<String> iterator = this.e.iterator();
/*     */       
/*  95 */       while (iterator.hasNext()) {
/*  96 */         String s = iterator.next();
/*     */         
/*  98 */         packetdataserializer.a(s);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 104 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 108 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutScoreboardTeam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */