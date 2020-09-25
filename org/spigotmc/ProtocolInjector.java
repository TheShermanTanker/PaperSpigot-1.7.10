/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.ChatSerializer;
/*     */ import net.minecraft.server.v1_7_R4.EnumProtocol;
/*     */ import net.minecraft.server.v1_7_R4.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_7_R4.Packet;
/*     */ import net.minecraft.server.v1_7_R4.PacketDataSerializer;
/*     */ import net.minecraft.server.v1_7_R4.PacketListener;
/*     */ import net.minecraft.util.com.google.common.collect.BiMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtocolInjector
/*     */ {
/*     */   public static void inject() {
/*     */     try {
/*  21 */       addPacket(EnumProtocol.LOGIN, true, 3, (Class)PacketLoginCompression.class);
/*     */       
/*  23 */       addPacket(EnumProtocol.PLAY, true, 69, (Class)PacketTitle.class);
/*  24 */       addPacket(EnumProtocol.PLAY, true, 71, (Class)PacketTabHeader.class);
/*  25 */       addPacket(EnumProtocol.PLAY, true, 72, (Class)PacketPlayResourcePackSend.class);
/*  26 */       addPacket(EnumProtocol.PLAY, false, 25, (Class)PacketPlayResourcePackStatus.class);
/*  27 */     } catch (NoSuchFieldException e) {
/*     */       
/*  29 */       e.printStackTrace();
/*  30 */     } catch (IllegalAccessException e) {
/*     */       
/*  32 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addPacket(EnumProtocol protocol, boolean clientbound, int id, Class<? extends Packet> packet) throws NoSuchFieldException, IllegalAccessException {
/*     */     Field packets;
/*  39 */     if (!clientbound) {
/*  40 */       packets = EnumProtocol.class.getDeclaredField("h");
/*     */     } else {
/*  42 */       packets = EnumProtocol.class.getDeclaredField("i");
/*     */     } 
/*  44 */     packets.setAccessible(true);
/*  45 */     BiMap<Integer, Class<? extends Packet>> pMap = (BiMap<Integer, Class<? extends Packet>>)packets.get(protocol);
/*  46 */     pMap.put(Integer.valueOf(id), packet);
/*  47 */     Field map = EnumProtocol.class.getDeclaredField("f");
/*  48 */     map.setAccessible(true);
/*  49 */     Map<Class<? extends Packet>, EnumProtocol> protocolMap = (Map<Class<? extends Packet>, EnumProtocol>)map.get(null);
/*  50 */     protocolMap.put(packet, protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PacketPlayResourcePackStatus
/*     */     extends Packet
/*     */   {
/*     */     public void a(PacketDataSerializer packetdataserializer) throws IOException {
/*  58 */       packetdataserializer.c(255);
/*  59 */       packetdataserializer.a();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void b(PacketDataSerializer packetdataserializer) throws IOException {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(PacketListener packetlistener) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PacketPlayResourcePackSend
/*     */     extends Packet
/*     */   {
/*     */     private String url;
/*     */     
/*     */     private String hash;
/*     */ 
/*     */     
/*     */     public PacketPlayResourcePackSend(String url, String hash) {
/*  82 */       this.url = url;
/*  83 */       this.hash = hash;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void a(PacketDataSerializer packetdataserializer) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void b(PacketDataSerializer packetdataserializer) throws IOException {
/*  95 */       packetdataserializer.a(this.url);
/*  96 */       packetdataserializer.a(this.hash);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(PacketListener packetlistener) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PacketLoginCompression
/*     */     extends Packet
/*     */   {
/*     */     private int threshold;
/*     */ 
/*     */     
/*     */     public PacketLoginCompression(int threshold) {
/* 112 */       this.threshold = threshold;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void a(PacketDataSerializer packetdataserializer) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 124 */       packetdataserializer.b(this.threshold);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(PacketListener packetlistener) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PacketTabHeader
/*     */     extends Packet
/*     */   {
/*     */     private IChatBaseComponent header;
/*     */     
/*     */     private IChatBaseComponent footer;
/*     */ 
/*     */     
/*     */     public PacketTabHeader() {}
/*     */ 
/*     */     
/*     */     public PacketTabHeader(IChatBaseComponent header, IChatBaseComponent footer) {
/* 146 */       this.header = header;
/* 147 */       this.footer = footer;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 153 */       this.header = ChatSerializer.a(packetdataserializer.c(32767));
/* 154 */       this.footer = ChatSerializer.a(packetdataserializer.c(32767));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 160 */       packetdataserializer.a(ChatSerializer.a(this.header));
/* 161 */       packetdataserializer.a(ChatSerializer.a(this.footer));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(PacketListener packetlistener) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PacketTitle
/*     */     extends Packet
/*     */   {
/*     */     private Action action;
/*     */     
/*     */     private IChatBaseComponent text;
/*     */     
/* 178 */     private int fadeIn = -1;
/* 179 */     private int stay = -1;
/* 180 */     private int fadeOut = -1;
/*     */ 
/*     */     
/*     */     public PacketTitle() {}
/*     */     
/*     */     public PacketTitle(Action action) {
/* 186 */       this.action = action;
/*     */     }
/*     */ 
/*     */     
/*     */     public PacketTitle(Action action, IChatBaseComponent text) {
/* 191 */       this(action);
/* 192 */       this.text = text;
/*     */     }
/*     */ 
/*     */     
/*     */     public PacketTitle(Action action, int fadeIn, int stay, int fadeOut) {
/* 197 */       this(action);
/* 198 */       this.fadeIn = fadeIn;
/* 199 */       this.stay = stay;
/* 200 */       this.fadeOut = fadeOut;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 207 */       this.action = Action.values()[packetdataserializer.a()];
/* 208 */       switch (this.action) {
/*     */         
/*     */         case TITLE:
/*     */         case SUBTITLE:
/* 212 */           this.text = ChatSerializer.a(packetdataserializer.c(32767));
/*     */           break;
/*     */         case TIMES:
/* 215 */           this.fadeIn = packetdataserializer.readInt();
/* 216 */           this.stay = packetdataserializer.readInt();
/* 217 */           this.fadeOut = packetdataserializer.readInt();
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 225 */       packetdataserializer.b(this.action.ordinal());
/* 226 */       switch (this.action) {
/*     */         
/*     */         case TITLE:
/*     */         case SUBTITLE:
/* 230 */           packetdataserializer.a(ChatSerializer.a(this.text));
/*     */           break;
/*     */         case TIMES:
/* 233 */           packetdataserializer.writeInt(this.fadeIn);
/* 234 */           packetdataserializer.writeInt(this.stay);
/* 235 */           packetdataserializer.writeInt(this.fadeOut);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(PacketListener packetlistener) {}
/*     */ 
/*     */     
/*     */     public enum Action
/*     */     {
/* 246 */       TITLE,
/* 247 */       SUBTITLE,
/* 248 */       TIMES,
/* 249 */       CLEAR,
/* 250 */       RESET;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\ProtocolInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */