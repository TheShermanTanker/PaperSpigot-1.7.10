/*     */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.CompositeByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpConstants;
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
/*     */ 
/*     */ public abstract class AbstractMemoryHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*     */   private ByteBuf byteBuf;
/*     */   private int chunkPosition;
/*     */   protected boolean isRenamed;
/*     */   
/*     */   protected AbstractMemoryHttpData(String name, Charset charset, long size) {
/*  43 */     super(name, charset, size);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/*  48 */     if (buffer == null) {
/*  49 */       throw new NullPointerException("buffer");
/*     */     }
/*  51 */     long localsize = buffer.readableBytes();
/*  52 */     if (this.definedSize > 0L && this.definedSize < localsize) {
/*  53 */       throw new IOException("Out of size: " + localsize + " > " + this.definedSize);
/*     */     }
/*     */     
/*  56 */     if (this.byteBuf != null) {
/*  57 */       this.byteBuf.release();
/*     */     }
/*  59 */     this.byteBuf = buffer;
/*  60 */     this.size = localsize;
/*  61 */     this.completed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/*  66 */     if (inputStream == null) {
/*  67 */       throw new NullPointerException("inputStream");
/*     */     }
/*  69 */     ByteBuf buffer = Unpooled.buffer();
/*  70 */     byte[] bytes = new byte[16384];
/*  71 */     int read = inputStream.read(bytes);
/*  72 */     int written = 0;
/*  73 */     while (read > 0) {
/*  74 */       buffer.writeBytes(bytes, 0, read);
/*  75 */       written += read;
/*  76 */       read = inputStream.read(bytes);
/*     */     } 
/*  78 */     this.size = written;
/*  79 */     if (this.definedSize > 0L && this.definedSize < this.size) {
/*  80 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     }
/*  82 */     if (this.byteBuf != null) {
/*  83 */       this.byteBuf.release();
/*     */     }
/*  85 */     this.byteBuf = buffer;
/*  86 */     this.completed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  92 */     if (buffer != null) {
/*  93 */       long localsize = buffer.readableBytes();
/*  94 */       if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/*  95 */         throw new IOException("Out of size: " + (this.size + localsize) + " > " + this.definedSize);
/*     */       }
/*     */       
/*  98 */       this.size += localsize;
/*  99 */       if (this.byteBuf == null) {
/* 100 */         this.byteBuf = buffer;
/* 101 */       } else if (this.byteBuf instanceof CompositeByteBuf) {
/* 102 */         CompositeByteBuf cbb = (CompositeByteBuf)this.byteBuf;
/* 103 */         cbb.addComponent(buffer);
/* 104 */         cbb.writerIndex(cbb.writerIndex() + buffer.readableBytes());
/*     */       } else {
/* 106 */         CompositeByteBuf cbb = Unpooled.compositeBuffer(2147483647);
/* 107 */         cbb.addComponents(new ByteBuf[] { this.byteBuf, buffer });
/* 108 */         cbb.writerIndex(this.byteBuf.readableBytes() + buffer.readableBytes());
/* 109 */         this.byteBuf = (ByteBuf)cbb;
/*     */       } 
/*     */     } 
/* 112 */     if (last) {
/* 113 */       this.completed = true;
/*     */     }
/* 115 */     else if (buffer == null) {
/* 116 */       throw new NullPointerException("buffer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 123 */     if (file == null) {
/* 124 */       throw new NullPointerException("file");
/*     */     }
/* 126 */     long newsize = file.length();
/* 127 */     if (newsize > 2147483647L) {
/* 128 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/*     */     
/* 131 */     FileInputStream inputStream = new FileInputStream(file);
/* 132 */     FileChannel fileChannel = inputStream.getChannel();
/* 133 */     byte[] array = new byte[(int)newsize];
/* 134 */     ByteBuffer byteBuffer = ByteBuffer.wrap(array);
/* 135 */     int read = 0;
/* 136 */     while (read < newsize) {
/* 137 */       read += fileChannel.read(byteBuffer);
/*     */     }
/* 139 */     fileChannel.close();
/* 140 */     inputStream.close();
/* 141 */     byteBuffer.flip();
/* 142 */     if (this.byteBuf != null) {
/* 143 */       this.byteBuf.release();
/*     */     }
/* 145 */     this.byteBuf = Unpooled.wrappedBuffer(2147483647, new ByteBuffer[] { byteBuffer });
/* 146 */     this.size = newsize;
/* 147 */     this.completed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get() {
/* 157 */     if (this.byteBuf == null) {
/* 158 */       return Unpooled.EMPTY_BUFFER.array();
/*     */     }
/* 160 */     byte[] array = new byte[this.byteBuf.readableBytes()];
/* 161 */     this.byteBuf.getBytes(this.byteBuf.readerIndex(), array);
/* 162 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/* 167 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) {
/* 172 */     if (this.byteBuf == null) {
/* 173 */       return "";
/*     */     }
/* 175 */     if (encoding == null) {
/* 176 */       encoding = HttpConstants.DEFAULT_CHARSET;
/*     */     }
/* 178 */     return this.byteBuf.toString(encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() {
/* 188 */     return this.byteBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 193 */     if (this.byteBuf == null || length == 0 || this.byteBuf.readableBytes() == 0) {
/* 194 */       this.chunkPosition = 0;
/* 195 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/* 197 */     int sizeLeft = this.byteBuf.readableBytes() - this.chunkPosition;
/* 198 */     if (sizeLeft == 0) {
/* 199 */       this.chunkPosition = 0;
/* 200 */       return Unpooled.EMPTY_BUFFER;
/*     */     } 
/* 202 */     int sliceLength = length;
/* 203 */     if (sizeLeft < length) {
/* 204 */       sliceLength = sizeLeft;
/*     */     }
/* 206 */     ByteBuf chunk = this.byteBuf.slice(this.chunkPosition, sliceLength);
/* 207 */     this.chunkPosition += sliceLength;
/* 208 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 218 */     if (dest == null) {
/* 219 */       throw new NullPointerException("dest");
/*     */     }
/* 221 */     if (this.byteBuf == null) {
/*     */       
/* 223 */       dest.createNewFile();
/* 224 */       this.isRenamed = true;
/* 225 */       return true;
/*     */     } 
/* 227 */     int length = this.byteBuf.readableBytes();
/* 228 */     FileOutputStream outputStream = new FileOutputStream(dest);
/* 229 */     FileChannel fileChannel = outputStream.getChannel();
/* 230 */     int written = 0;
/* 231 */     if (this.byteBuf.nioBufferCount() == 1) {
/* 232 */       ByteBuffer byteBuffer = this.byteBuf.nioBuffer();
/* 233 */       while (written < length) {
/* 234 */         written += fileChannel.write(byteBuffer);
/*     */       }
/*     */     } else {
/* 237 */       ByteBuffer[] byteBuffers = this.byteBuf.nioBuffers();
/* 238 */       while (written < length) {
/* 239 */         written = (int)(written + fileChannel.write(byteBuffers));
/*     */       }
/*     */     } 
/*     */     
/* 243 */     fileChannel.force(false);
/* 244 */     fileChannel.close();
/* 245 */     outputStream.close();
/* 246 */     this.isRenamed = true;
/* 247 */     return (written == length);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 252 */     throw new IOException("Not represented by a file");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\AbstractMemoryHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */