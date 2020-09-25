/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
/*     */ 
/*     */ public class PacketPlayOutOpenWindow
/*     */   extends Packet
/*     */ {
/*     */   private int a;
/*     */   private int b;
/*     */   private String c;
/*     */   private int d;
/*     */   private boolean e;
/*     */   private int f;
/*     */   
/*     */   public PacketPlayOutOpenWindow() {}
/*     */   
/*     */   public PacketPlayOutOpenWindow(int i, int j, String s, int k, boolean flag) {
/*  19 */     if (s.length() > 32) s = s.substring(0, 32); 
/*  20 */     this.a = i;
/*  21 */     this.b = j;
/*  22 */     this.c = s;
/*  23 */     this.d = k;
/*  24 */     this.e = flag;
/*     */   }
/*     */   
/*     */   public PacketPlayOutOpenWindow(int i, int j, String s, int k, boolean flag, int l) {
/*  28 */     this(i, j, s, k, flag);
/*  29 */     this.f = l;
/*     */   }
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/*  33 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/*  37 */     this.a = packetdataserializer.readUnsignedByte();
/*  38 */     this.b = packetdataserializer.readUnsignedByte();
/*  39 */     this.c = packetdataserializer.c(32);
/*  40 */     this.d = packetdataserializer.readUnsignedByte();
/*  41 */     this.e = packetdataserializer.readBoolean();
/*  42 */     if (this.b == 11) {
/*  43 */       this.f = packetdataserializer.readInt();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/*  48 */     if (packetdataserializer.version < 16) {
/*     */       
/*  50 */       packetdataserializer.writeByte(this.a);
/*  51 */       packetdataserializer.writeByte(this.b);
/*  52 */       packetdataserializer.a(this.c);
/*  53 */       packetdataserializer.writeByte(this.d);
/*  54 */       packetdataserializer.writeBoolean(this.e);
/*  55 */       if (this.b == 11)
/*     */       {
/*  57 */         packetdataserializer.writeInt(this.f);
/*     */       }
/*     */     } else {
/*     */       
/*  61 */       packetdataserializer.writeByte(this.a);
/*  62 */       packetdataserializer.a(getInventoryString(this.b));
/*  63 */       if (this.e) {
/*     */         
/*  65 */         packetdataserializer.a(ChatSerializer.a(CraftChatMessage.fromString(this.c)[0]));
/*     */       } else {
/*     */         
/*  68 */         packetdataserializer.a(ChatSerializer.a(new ChatMessage(this.c, new Object[0])));
/*     */       } 
/*  70 */       packetdataserializer.writeByte(this.d);
/*  71 */       if (this.b == 11)
/*     */       {
/*  73 */         packetdataserializer.writeInt(this.f);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getInventoryString(int b) {
/*  81 */     switch (b) {
/*     */       case 0:
/*  83 */         return "minecraft:chest";
/*     */       case 1:
/*  85 */         return "minecraft:crafting_table";
/*     */       case 2:
/*  87 */         return "minecraft:furnace";
/*     */       case 3:
/*  89 */         return "minecraft:dispenser";
/*     */       case 4:
/*  91 */         return "minecraft:enchanting_table";
/*     */       case 5:
/*  93 */         return "minecraft:brewing_stand";
/*     */       case 6:
/*  95 */         return "minecraft:villager";
/*     */       case 7:
/*  97 */         return "minecraft:beacon";
/*     */       case 8:
/*  99 */         return "minecraft:anvil";
/*     */       case 9:
/* 101 */         return "minecraft:hopper";
/*     */       case 10:
/* 103 */         return "minecraft:dropper";
/*     */       case 11:
/* 105 */         return "EntityHorse";
/*     */     } 
/* 107 */     throw new IllegalArgumentException("Unknown type " + b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 112 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutOpenWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */