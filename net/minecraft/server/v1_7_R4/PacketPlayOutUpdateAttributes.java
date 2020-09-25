/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class PacketPlayOutUpdateAttributes
/*    */   extends Packet {
/*    */   private int a;
/* 13 */   private final List b = new ArrayList();
/*    */   
/*    */   public PacketPlayOutUpdateAttributes() {}
/*    */   
/*    */   public PacketPlayOutUpdateAttributes(int i, Collection collection) {
/* 18 */     this.a = i;
/* 19 */     Iterator<AttributeInstance> iterator = collection.iterator();
/*    */     
/* 21 */     while (iterator.hasNext()) {
/* 22 */       AttributeInstance attributeinstance = iterator.next();
/*    */       
/* 24 */       this.b.add(new AttributeSnapshot(this, attributeinstance.getAttribute().getName(), attributeinstance.b(), attributeinstance.c()));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 29 */     this.a = packetdataserializer.readInt();
/* 30 */     int i = packetdataserializer.readInt();
/*    */     
/* 32 */     for (int j = 0; j < i; j++) {
/* 33 */       String s = packetdataserializer.c(64);
/* 34 */       double d0 = packetdataserializer.readDouble();
/* 35 */       ArrayList<AttributeModifier> arraylist = new ArrayList();
/* 36 */       short short1 = packetdataserializer.readShort();
/*    */       
/* 38 */       for (int k = 0; k < short1; k++) {
/* 39 */         UUID uuid = new UUID(packetdataserializer.readLong(), packetdataserializer.readLong());
/*    */         
/* 41 */         arraylist.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", packetdataserializer.readDouble(), packetdataserializer.readByte()));
/*    */       } 
/*    */       
/* 44 */       this.b.add(new AttributeSnapshot(this, s, d0, arraylist));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 50 */     if (packetdataserializer.version < 16) {
/*    */       
/* 52 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/*    */       
/* 55 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */     
/* 58 */     packetdataserializer.writeInt(this.b.size());
/* 59 */     Iterator<AttributeSnapshot> iterator = this.b.iterator();
/*    */     
/* 61 */     while (iterator.hasNext()) {
/* 62 */       AttributeSnapshot attributesnapshot = iterator.next();
/*    */       
/* 64 */       packetdataserializer.a(attributesnapshot.a());
/* 65 */       packetdataserializer.writeDouble(attributesnapshot.b());
/*    */       
/* 67 */       if (packetdataserializer.version < 16) {
/*    */         
/* 69 */         packetdataserializer.writeShort(attributesnapshot.c().size());
/*    */       } else {
/* 71 */         packetdataserializer.b(attributesnapshot.c().size());
/*    */       } 
/*    */       
/* 74 */       Iterator<AttributeModifier> iterator1 = attributesnapshot.c().iterator();
/*    */       
/* 76 */       while (iterator1.hasNext()) {
/* 77 */         AttributeModifier attributemodifier = iterator1.next();
/*    */         
/* 79 */         packetdataserializer.writeLong(attributemodifier.a().getMostSignificantBits());
/* 80 */         packetdataserializer.writeLong(attributemodifier.a().getLeastSignificantBits());
/* 81 */         packetdataserializer.writeDouble(attributemodifier.d());
/* 82 */         packetdataserializer.writeByte(attributemodifier.c());
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 88 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 92 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutUpdateAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */