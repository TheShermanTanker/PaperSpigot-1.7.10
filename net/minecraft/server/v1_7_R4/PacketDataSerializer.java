/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.com.google.common.base.Charsets;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufInputStream;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufOutputStream;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufProcessor;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCounted;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftChatMessage;
/*     */ import org.spigotmc.SpigotComponentReverter;
/*     */ import org.spigotmc.SpigotDebreakifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketDataSerializer
/*     */   extends ByteBuf
/*     */ {
/*     */   private final ByteBuf a;
/*     */   public final int version;
/*     */   
/*     */   public PacketDataSerializer(ByteBuf bytebuf) {
/*  37 */     this(bytebuf, 5);
/*     */   }
/*     */   
/*     */   public PacketDataSerializer(ByteBuf bytebuf, int version) {
/*  41 */     this.a = bytebuf;
/*  42 */     this.version = version;
/*     */   }
/*     */   
/*     */   public void writePosition(int x, int y, int z) {
/*  46 */     writeLong((x & 0x3FFFFFFL) << 38L | (y & 0xFFFL) << 26L | z & 0x3FFFFFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readPositionX(long val) {
/*  53 */     return (int)(val >> 38L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readPositionY(long val) {
/*  58 */     return (int)(val << 26L >> 52L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readPositionZ(long val) {
/*  63 */     return (int)(val << 38L >> 38L);
/*     */   }
/*     */   
/*     */   public void writeUUID(UUID uuid) {
/*  67 */     writeLong(uuid.getMostSignificantBits());
/*  68 */     writeLong(uuid.getLeastSignificantBits());
/*     */   }
/*     */ 
/*     */   
/*     */   public static int a(int i) {
/*  73 */     return ((i & 0xFFFFFF80) == 0) ? 1 : (((i & 0xFFFFC000) == 0) ? 2 : (((i & 0xFFE00000) == 0) ? 3 : (((i & 0xF0000000) == 0) ? 4 : 5)));
/*     */   }
/*     */   public int a() {
/*     */     byte b0;
/*  77 */     int i = 0;
/*  78 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     do {
/*  83 */       b0 = readByte();
/*  84 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*  85 */       if (j > 5) {
/*  86 */         throw new RuntimeException("VarInt too big");
/*     */       }
/*  88 */     } while ((b0 & 0x80) == 128);
/*     */     
/*  90 */     return i;
/*     */   }
/*     */   
/*     */   public void b(int i) {
/*  94 */     while ((i & 0xFFFFFF80) != 0) {
/*  95 */       writeByte(i & 0x7F | 0x80);
/*  96 */       i >>>= 7;
/*     */     } 
/*     */     
/*  99 */     writeByte(i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 105 */     if (this.version < 28) {
/*     */       
/* 107 */       if (nbttagcompound == null) {
/*     */         
/* 109 */         writeShort(-1);
/*     */       } else {
/*     */         
/* 112 */         byte[] abyte = NBTCompressedStreamTools.a(nbttagcompound);
/*     */         
/* 114 */         writeShort((short)abyte.length);
/* 115 */         writeBytes(abyte);
/*     */       }
/*     */     
/*     */     }
/* 119 */     else if (nbttagcompound == null) {
/*     */       
/* 121 */       writeByte(0);
/*     */     } else {
/*     */       
/* 124 */       ByteBufOutputStream out = new ByteBufOutputStream(Unpooled.buffer());
/* 125 */       NBTCompressedStreamTools.a(nbttagcompound, new DataOutputStream((OutputStream)out));
/* 126 */       writeBytes(out.buffer());
/* 127 */       out.buffer().release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound b() {
/* 133 */     if (this.version < 28) {
/*     */       
/* 135 */       short short1 = readShort();
/*     */       
/* 137 */       if (short1 < 0)
/*     */       {
/* 139 */         return null;
/*     */       }
/*     */       
/* 142 */       byte[] abyte = new byte[short1];
/*     */       
/* 144 */       readBytes(abyte);
/* 145 */       return NBTCompressedStreamTools.a(abyte, new NBTReadLimiter(2097152L));
/*     */     } 
/*     */     
/* 148 */     int index = readerIndex();
/* 149 */     if (readByte() == 0) {
/* 150 */       return null;
/*     */     }
/* 152 */     readerIndex(index);
/* 153 */     return NBTCompressedStreamTools.a((DataInput)new ByteBufInputStream(this.a), new NBTReadLimiter(2097152L));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(ItemStack itemstack) {
/* 159 */     if (itemstack == null || itemstack.getItem() == null) {
/* 160 */       writeShort(-1);
/*     */     } else {
/*     */       
/* 163 */       if (this.version >= 47) {
/*     */         
/* 165 */         writeShort(SpigotDebreakifier.getItemId(Item.getId(itemstack.getItem())));
/*     */       } else {
/*     */         
/* 168 */         writeShort(Item.getId(itemstack.getItem()));
/*     */       } 
/*     */       
/* 171 */       writeByte(itemstack.count);
/* 172 */       writeShort(itemstack.getData());
/* 173 */       NBTTagCompound nbttagcompound = null;
/*     */       
/* 175 */       if (itemstack.getItem().usesDurability() || itemstack.getItem().s()) {
/*     */         
/* 177 */         itemstack = itemstack.cloneItemStack();
/* 178 */         CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
/*     */         
/* 180 */         nbttagcompound = itemstack.tag;
/*     */       } 
/*     */ 
/*     */       
/* 184 */       if (nbttagcompound != null && this.version >= 29)
/*     */       {
/* 186 */         if (itemstack.getItem() == Items.WRITTEN_BOOK && nbttagcompound.hasKeyOfType("pages", 9)) {
/*     */           
/* 188 */           nbttagcompound = (NBTTagCompound)nbttagcompound.clone();
/* 189 */           NBTTagList nbttaglist = nbttagcompound.getList("pages", 8);
/* 190 */           NBTTagList newList = new NBTTagList();
/* 191 */           for (int i = 0; i < nbttaglist.size(); i++) {
/*     */             
/* 193 */             IChatBaseComponent[] parts = CraftChatMessage.fromString(nbttaglist.getString(i));
/* 194 */             IChatBaseComponent root = parts[0];
/* 195 */             for (int i1 = 1; i1 < parts.length; i1++) {
/*     */               
/* 197 */               IChatBaseComponent c = parts[i1];
/* 198 */               root.a("\n");
/* 199 */               root.addSibling(c);
/*     */             } 
/* 201 */             newList.add(new NBTTagString(ChatSerializer.a(root)));
/*     */           } 
/* 203 */           nbttagcompound.set("pages", newList);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 208 */       a(nbttagcompound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack c() {
/* 213 */     ItemStack itemstack = null;
/* 214 */     short short1 = readShort();
/*     */     
/* 216 */     if (short1 >= 0) {
/* 217 */       byte b0 = readByte();
/* 218 */       short short2 = readShort();
/*     */       
/* 220 */       itemstack = new ItemStack(Item.getById(short1), b0, short2);
/* 221 */       itemstack.tag = b();
/*     */       
/* 223 */       if (itemstack.tag != null) {
/*     */ 
/*     */         
/* 226 */         if (this.version >= 29 && itemstack.getItem() == Items.WRITTEN_BOOK && itemstack.tag.hasKeyOfType("pages", 9)) {
/*     */ 
/*     */ 
/*     */           
/* 230 */           NBTTagList nbttaglist = itemstack.tag.getList("pages", 8);
/* 231 */           NBTTagList newList = new NBTTagList();
/* 232 */           for (int i = 0; i < nbttaglist.size(); i++) {
/*     */             
/* 234 */             IChatBaseComponent s = ChatSerializer.a(nbttaglist.getString(i));
/* 235 */             String newString = SpigotComponentReverter.toLegacy(s);
/* 236 */             newList.add(new NBTTagString(newString));
/*     */           } 
/* 238 */           itemstack.tag.set("pages", newList);
/*     */         } 
/*     */ 
/*     */         
/* 242 */         CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 247 */     return itemstack;
/*     */   }
/*     */   
/*     */   public String c(int i) throws IOException {
/* 251 */     int j = a();
/*     */     
/* 253 */     if (j > i * 4)
/* 254 */       throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + (i * 4) + ")"); 
/* 255 */     if (j < 0) {
/* 256 */       throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
/*     */     }
/* 258 */     String s = new String(readBytes(j).array(), Charsets.UTF_8);
/*     */     
/* 260 */     if (s.length() > i) {
/* 261 */       throw new IOException("The received string length is longer than maximum allowed (" + j + " > " + i + ")");
/*     */     }
/* 263 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(String s) throws IOException {
/* 269 */     byte[] abyte = s.getBytes(Charsets.UTF_8);
/*     */     
/* 271 */     if (abyte.length > 32767) {
/* 272 */       throw new IOException("String too big (was " + s.length() + " bytes encoded, max " + '翿' + ")");
/*     */     }
/* 274 */     b(abyte.length);
/* 275 */     writeBytes(abyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 280 */     return this.a.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int i) {
/* 284 */     return this.a.capacity(i);
/*     */   }
/*     */   
/*     */   public int maxCapacity() {
/* 288 */     return this.a.maxCapacity();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 292 */     return this.a.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order() {
/* 296 */     return this.a.order();
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder byteorder) {
/* 300 */     return this.a.order(byteorder);
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap() {
/* 304 */     return this.a.unwrap();
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 308 */     return this.a.isDirect();
/*     */   }
/*     */   
/*     */   public int readerIndex() {
/* 312 */     return this.a.readerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf readerIndex(int i) {
/* 316 */     return this.a.readerIndex(i);
/*     */   }
/*     */   
/*     */   public int writerIndex() {
/* 320 */     return this.a.writerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf writerIndex(int i) {
/* 324 */     return this.a.writerIndex(i);
/*     */   }
/*     */   
/*     */   public ByteBuf setIndex(int i, int j) {
/* 328 */     return this.a.setIndex(i, j);
/*     */   }
/*     */   
/*     */   public int readableBytes() {
/* 332 */     return this.a.readableBytes();
/*     */   }
/*     */   
/*     */   public int writableBytes() {
/* 336 */     return this.a.writableBytes();
/*     */   }
/*     */   
/*     */   public int maxWritableBytes() {
/* 340 */     return this.a.maxWritableBytes();
/*     */   }
/*     */   
/*     */   public boolean isReadable() {
/* 344 */     return this.a.isReadable();
/*     */   }
/*     */   
/*     */   public boolean isReadable(int i) {
/* 348 */     return this.a.isReadable(i);
/*     */   }
/*     */   
/*     */   public boolean isWritable() {
/* 352 */     return this.a.isWritable();
/*     */   }
/*     */   
/*     */   public boolean isWritable(int i) {
/* 356 */     return this.a.isWritable(i);
/*     */   }
/*     */   
/*     */   public ByteBuf clear() {
/* 360 */     return this.a.clear();
/*     */   }
/*     */   
/*     */   public ByteBuf markReaderIndex() {
/* 364 */     return this.a.markReaderIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf resetReaderIndex() {
/* 368 */     return this.a.resetReaderIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf markWriterIndex() {
/* 372 */     return this.a.markWriterIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf resetWriterIndex() {
/* 376 */     return this.a.resetWriterIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 380 */     return this.a.discardReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 384 */     return this.a.discardSomeReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int i) {
/* 388 */     return this.a.ensureWritable(i);
/*     */   }
/*     */   
/*     */   public int ensureWritable(int i, boolean flag) {
/* 392 */     return this.a.ensureWritable(i, flag);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int i) {
/* 396 */     return this.a.getBoolean(i);
/*     */   }
/*     */   
/*     */   public byte getByte(int i) {
/* 400 */     return this.a.getByte(i);
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int i) {
/* 404 */     return this.a.getUnsignedByte(i);
/*     */   }
/*     */   
/*     */   public short getShort(int i) {
/* 408 */     return this.a.getShort(i);
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int i) {
/* 412 */     return this.a.getUnsignedShort(i);
/*     */   }
/*     */   
/*     */   public int getMedium(int i) {
/* 416 */     return this.a.getMedium(i);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int i) {
/* 420 */     return this.a.getUnsignedMedium(i);
/*     */   }
/*     */   
/*     */   public int getInt(int i) {
/* 424 */     return this.a.getInt(i);
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int i) {
/* 428 */     return this.a.getUnsignedInt(i);
/*     */   }
/*     */   
/*     */   public long getLong(int i) {
/* 432 */     return this.a.getLong(i);
/*     */   }
/*     */   
/*     */   public char getChar(int i) {
/* 436 */     return this.a.getChar(i);
/*     */   }
/*     */   
/*     */   public float getFloat(int i) {
/* 440 */     return this.a.getFloat(i);
/*     */   }
/*     */   
/*     */   public double getDouble(int i) {
/* 444 */     return this.a.getDouble(i);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuf bytebuf) {
/* 448 */     return this.a.getBytes(i, bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
/* 452 */     return this.a.getBytes(i, bytebuf, j);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
/* 456 */     return this.a.getBytes(i, bytebuf, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, byte[] abyte) {
/* 460 */     return this.a.getBytes(i, abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
/* 464 */     return this.a.getBytes(i, abyte, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
/* 468 */     return this.a.getBytes(i, bytebuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
/* 472 */     return this.a.getBytes(i, outputstream, j);
/*     */   }
/*     */   
/*     */   public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
/* 476 */     return this.a.getBytes(i, gatheringbytechannel, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int i, boolean flag) {
/* 480 */     return this.a.setBoolean(i, flag);
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int i, int j) {
/* 484 */     return this.a.setByte(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int i, int j) {
/* 488 */     return this.a.setShort(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int i, int j) {
/* 492 */     return this.a.setMedium(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int i, int j) {
/* 496 */     return this.a.setInt(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int i, long j) {
/* 500 */     return this.a.setLong(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int i, int j) {
/* 504 */     return this.a.setChar(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int i, float f) {
/* 508 */     return this.a.setFloat(i, f);
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int i, double d0) {
/* 512 */     return this.a.setDouble(i, d0);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuf bytebuf) {
/* 516 */     return this.a.setBytes(i, bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
/* 520 */     return this.a.setBytes(i, bytebuf, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
/* 524 */     return this.a.setBytes(i, bytebuf, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, byte[] abyte) {
/* 528 */     return this.a.setBytes(i, abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
/* 532 */     return this.a.setBytes(i, abyte, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
/* 536 */     return this.a.setBytes(i, bytebuffer);
/*     */   }
/*     */   
/*     */   public int setBytes(int i, InputStream inputstream, int j) throws IOException {
/* 540 */     return this.a.setBytes(i, inputstream, j);
/*     */   }
/*     */   
/*     */   public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
/* 544 */     return this.a.setBytes(i, scatteringbytechannel, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int i, int j) {
/* 548 */     return this.a.setZero(i, j);
/*     */   }
/*     */   
/*     */   public boolean readBoolean() {
/* 552 */     return this.a.readBoolean();
/*     */   }
/*     */   
/*     */   public byte readByte() {
/* 556 */     return this.a.readByte();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte() {
/* 560 */     return this.a.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public short readShort() {
/* 564 */     return this.a.readShort();
/*     */   }
/*     */   
/*     */   public int readUnsignedShort() {
/* 568 */     return this.a.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public int readMedium() {
/* 572 */     return this.a.readMedium();
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium() {
/* 576 */     return this.a.readUnsignedMedium();
/*     */   }
/*     */   
/*     */   public int readInt() {
/* 580 */     return this.a.readInt();
/*     */   }
/*     */   
/*     */   public long readUnsignedInt() {
/* 584 */     return this.a.readUnsignedInt();
/*     */   }
/*     */   
/*     */   public long readLong() {
/* 588 */     return this.a.readLong();
/*     */   }
/*     */   
/*     */   public char readChar() {
/* 592 */     return this.a.readChar();
/*     */   }
/*     */   
/*     */   public float readFloat() {
/* 596 */     return this.a.readFloat();
/*     */   }
/*     */   
/*     */   public double readDouble() {
/* 600 */     return this.a.readDouble();
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int i) {
/* 604 */     return this.a.readBytes(i);
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int i) {
/* 608 */     return this.a.readSlice(i);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf bytebuf) {
/* 612 */     return this.a.readBytes(bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf bytebuf, int i) {
/* 616 */     return this.a.readBytes(bytebuf, i);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
/* 620 */     return this.a.readBytes(bytebuf, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] abyte) {
/* 624 */     return this.a.readBytes(abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] abyte, int i, int j) {
/* 628 */     return this.a.readBytes(abyte, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer bytebuffer) {
/* 632 */     return this.a.readBytes(bytebuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
/* 636 */     return this.a.readBytes(outputstream, i);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
/* 640 */     return this.a.readBytes(gatheringbytechannel, i);
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int i) {
/* 644 */     return this.a.skipBytes(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean flag) {
/* 648 */     return this.a.writeBoolean(flag);
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int i) {
/* 652 */     return this.a.writeByte(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int i) {
/* 656 */     return this.a.writeShort(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int i) {
/* 660 */     return this.a.writeMedium(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int i) {
/* 664 */     return this.a.writeInt(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long i) {
/* 668 */     return this.a.writeLong(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int i) {
/* 672 */     return this.a.writeChar(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float f) {
/* 676 */     return this.a.writeFloat(f);
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double d0) {
/* 680 */     return this.a.writeDouble(d0);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf bytebuf) {
/* 684 */     return this.a.writeBytes(bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
/* 688 */     return this.a.writeBytes(bytebuf, i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
/* 692 */     return this.a.writeBytes(bytebuf, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] abyte) {
/* 696 */     return this.a.writeBytes(abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] abyte, int i, int j) {
/* 700 */     return this.a.writeBytes(abyte, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer bytebuffer) {
/* 704 */     return this.a.writeBytes(bytebuffer);
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream inputstream, int i) throws IOException {
/* 708 */     return this.a.writeBytes(inputstream, i);
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
/* 712 */     return this.a.writeBytes(scatteringbytechannel, i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int i) {
/* 716 */     return this.a.writeZero(i);
/*     */   }
/*     */   
/*     */   public int indexOf(int i, int j, byte b0) {
/* 720 */     return this.a.indexOf(i, j, b0);
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte b0) {
/* 724 */     return this.a.bytesBefore(b0);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int i, byte b0) {
/* 728 */     return this.a.bytesBefore(i, b0);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int i, int j, byte b0) {
/* 732 */     return this.a.bytesBefore(i, j, b0);
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor bytebufprocessor) {
/* 736 */     return this.a.forEachByte(bytebufprocessor);
/*     */   }
/*     */   
/*     */   public int forEachByte(int i, int j, ByteBufProcessor bytebufprocessor) {
/* 740 */     return this.a.forEachByte(i, j, bytebufprocessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor bytebufprocessor) {
/* 744 */     return this.a.forEachByteDesc(bytebufprocessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int i, int j, ByteBufProcessor bytebufprocessor) {
/* 748 */     return this.a.forEachByteDesc(i, j, bytebufprocessor);
/*     */   }
/*     */   
/*     */   public ByteBuf copy() {
/* 752 */     return this.a.copy();
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int i, int j) {
/* 756 */     return this.a.copy(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf slice() {
/* 760 */     return this.a.slice();
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int i, int j) {
/* 764 */     return this.a.slice(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate() {
/* 768 */     return this.a.duplicate();
/*     */   }
/*     */   
/*     */   public int nioBufferCount() {
/* 772 */     return this.a.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 776 */     return this.a.nioBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int i, int j) {
/* 780 */     return this.a.nioBuffer(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int i, int j) {
/* 784 */     return this.a.internalNioBuffer(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 788 */     return this.a.nioBuffers();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int i, int j) {
/* 792 */     return this.a.nioBuffers(i, j);
/*     */   }
/*     */   
/*     */   public boolean hasArray() {
/* 796 */     return this.a.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array() {
/* 800 */     return this.a.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset() {
/* 804 */     return this.a.arrayOffset();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 808 */     return this.a.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress() {
/* 812 */     return this.a.memoryAddress();
/*     */   }
/*     */   
/*     */   public String toString(Charset charset) {
/* 816 */     return this.a.toString(charset);
/*     */   }
/*     */   
/*     */   public String toString(int i, int j, Charset charset) {
/* 820 */     return this.a.toString(i, j, charset);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 824 */     return this.a.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 828 */     return this.a.equals(object);
/*     */   }
/*     */   
/*     */   public int compareTo(ByteBuf bytebuf) {
/* 832 */     return this.a.compareTo(bytebuf);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 836 */     return this.a.toString();
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int i) {
/* 840 */     return this.a.retain(i);
/*     */   }
/*     */   
/*     */   public ByteBuf retain() {
/* 844 */     return this.a.retain();
/*     */   }
/*     */   
/*     */   public int refCnt() {
/* 848 */     return this.a.refCnt();
/*     */   }
/*     */   
/*     */   public boolean release() {
/* 852 */     return this.a.release();
/*     */   }
/*     */   
/*     */   public boolean release(int i) {
/* 856 */     return this.a.release(i);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketDataSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */