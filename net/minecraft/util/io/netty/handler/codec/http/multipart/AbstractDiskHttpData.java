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
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpConstants;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
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
/*     */ public abstract class AbstractDiskHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*     */   protected File file;
/*     */   private boolean isRenamed;
/*     */   private FileChannel fileChannel;
/*     */   
/*     */   protected AbstractDiskHttpData(String name, Charset charset, long size) {
/*  43 */     super(name, charset, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getDiskFilename();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getPrefix();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getBaseDirectory();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getPostfix();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean deleteOnExit();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private File tempFile() throws IOException {
/*     */     String newpostfix;
/*     */     File tmpFile;
/*  77 */     String diskFilename = getDiskFilename();
/*  78 */     if (diskFilename != null) {
/*  79 */       newpostfix = '_' + diskFilename;
/*     */     } else {
/*  81 */       newpostfix = getPostfix();
/*     */     } 
/*     */     
/*  84 */     if (getBaseDirectory() == null) {
/*     */       
/*  86 */       tmpFile = File.createTempFile(getPrefix(), newpostfix);
/*     */     } else {
/*  88 */       tmpFile = File.createTempFile(getPrefix(), newpostfix, new File(getBaseDirectory()));
/*     */     } 
/*     */     
/*  91 */     if (deleteOnExit()) {
/*  92 */       tmpFile.deleteOnExit();
/*     */     }
/*  94 */     return tmpFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/*  99 */     if (buffer == null) {
/* 100 */       throw new NullPointerException("buffer");
/*     */     }
/*     */     try {
/* 103 */       this.size = buffer.readableBytes();
/* 104 */       if (this.definedSize > 0L && this.definedSize < this.size) {
/* 105 */         throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */       }
/* 107 */       if (this.file == null) {
/* 108 */         this.file = tempFile();
/*     */       }
/* 110 */       if (buffer.readableBytes() == 0) {
/*     */         
/* 112 */         this.file.createNewFile();
/*     */         return;
/*     */       } 
/* 115 */       FileOutputStream outputStream = new FileOutputStream(this.file);
/* 116 */       FileChannel localfileChannel = outputStream.getChannel();
/* 117 */       ByteBuffer byteBuffer = buffer.nioBuffer();
/* 118 */       int written = 0;
/* 119 */       while (written < this.size) {
/* 120 */         written += localfileChannel.write(byteBuffer);
/*     */       }
/* 122 */       buffer.readerIndex(buffer.readerIndex() + written);
/* 123 */       localfileChannel.force(false);
/* 124 */       localfileChannel.close();
/* 125 */       outputStream.close();
/* 126 */       this.completed = true;
/*     */     }
/*     */     finally {
/*     */       
/* 130 */       buffer.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/* 137 */     if (buffer != null) {
/*     */       try {
/* 139 */         int localsize = buffer.readableBytes();
/* 140 */         if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/* 141 */           throw new IOException("Out of size: " + (this.size + localsize) + " > " + this.definedSize);
/*     */         }
/*     */         
/* 144 */         ByteBuffer byteBuffer = (buffer.nioBufferCount() == 1) ? buffer.nioBuffer() : buffer.copy().nioBuffer();
/* 145 */         int written = 0;
/* 146 */         if (this.file == null) {
/* 147 */           this.file = tempFile();
/*     */         }
/* 149 */         if (this.fileChannel == null) {
/* 150 */           FileOutputStream outputStream = new FileOutputStream(this.file);
/* 151 */           this.fileChannel = outputStream.getChannel();
/*     */         } 
/* 153 */         while (written < localsize) {
/* 154 */           written += this.fileChannel.write(byteBuffer);
/*     */         }
/* 156 */         this.size += localsize;
/* 157 */         buffer.readerIndex(buffer.readerIndex() + written);
/*     */       }
/*     */       finally {
/*     */         
/* 161 */         buffer.release();
/*     */       } 
/*     */     }
/* 164 */     if (last) {
/* 165 */       if (this.file == null) {
/* 166 */         this.file = tempFile();
/*     */       }
/* 168 */       if (this.fileChannel == null) {
/* 169 */         FileOutputStream outputStream = new FileOutputStream(this.file);
/* 170 */         this.fileChannel = outputStream.getChannel();
/*     */       } 
/* 172 */       this.fileChannel.force(false);
/* 173 */       this.fileChannel.close();
/* 174 */       this.fileChannel = null;
/* 175 */       this.completed = true;
/*     */     }
/* 177 */     else if (buffer == null) {
/* 178 */       throw new NullPointerException("buffer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 185 */     if (this.file != null) {
/* 186 */       delete();
/*     */     }
/* 188 */     this.file = file;
/* 189 */     this.size = file.length();
/* 190 */     this.isRenamed = true;
/* 191 */     this.completed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/* 196 */     if (inputStream == null) {
/* 197 */       throw new NullPointerException("inputStream");
/*     */     }
/* 199 */     if (this.file != null) {
/* 200 */       delete();
/*     */     }
/* 202 */     this.file = tempFile();
/* 203 */     FileOutputStream outputStream = new FileOutputStream(this.file);
/* 204 */     FileChannel localfileChannel = outputStream.getChannel();
/* 205 */     byte[] bytes = new byte[16384];
/* 206 */     ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
/* 207 */     int read = inputStream.read(bytes);
/* 208 */     int written = 0;
/* 209 */     while (read > 0) {
/* 210 */       byteBuffer.position(read).flip();
/* 211 */       written += localfileChannel.write(byteBuffer);
/* 212 */       read = inputStream.read(bytes);
/*     */     } 
/* 214 */     localfileChannel.force(false);
/* 215 */     localfileChannel.close();
/* 216 */     this.size = written;
/* 217 */     if (this.definedSize > 0L && this.definedSize < this.size) {
/* 218 */       this.file.delete();
/* 219 */       this.file = null;
/* 220 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     } 
/* 222 */     this.isRenamed = true;
/* 223 */     this.completed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 228 */     if (!this.isRenamed && 
/* 229 */       this.file != null) {
/* 230 */       this.file.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get() throws IOException {
/* 237 */     if (this.file == null) {
/* 238 */       return EmptyArrays.EMPTY_BYTES;
/*     */     }
/* 240 */     return readFrom(this.file);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException {
/* 245 */     if (this.file == null) {
/* 246 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 248 */     byte[] array = readFrom(this.file);
/* 249 */     return Unpooled.wrappedBuffer(array);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 254 */     if (this.file == null || length == 0) {
/* 255 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 257 */     if (this.fileChannel == null) {
/* 258 */       FileInputStream inputStream = new FileInputStream(this.file);
/* 259 */       this.fileChannel = inputStream.getChannel();
/*     */     } 
/* 261 */     int read = 0;
/* 262 */     ByteBuffer byteBuffer = ByteBuffer.allocate(length);
/* 263 */     while (read < length) {
/* 264 */       int readnow = this.fileChannel.read(byteBuffer);
/* 265 */       if (readnow == -1) {
/* 266 */         this.fileChannel.close();
/* 267 */         this.fileChannel = null;
/*     */         break;
/*     */       } 
/* 270 */       read += readnow;
/*     */     } 
/*     */     
/* 273 */     if (read == 0) {
/* 274 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 276 */     byteBuffer.flip();
/* 277 */     ByteBuf buffer = Unpooled.wrappedBuffer(byteBuffer);
/* 278 */     buffer.readerIndex(0);
/* 279 */     buffer.writerIndex(read);
/* 280 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws IOException {
/* 285 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) throws IOException {
/* 290 */     if (this.file == null) {
/* 291 */       return "";
/*     */     }
/* 293 */     if (encoding == null) {
/* 294 */       byte[] arrayOfByte = readFrom(this.file);
/* 295 */       return new String(arrayOfByte, HttpConstants.DEFAULT_CHARSET.name());
/*     */     } 
/* 297 */     byte[] array = readFrom(this.file);
/* 298 */     return new String(array, encoding.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 308 */     if (dest == null) {
/* 309 */       throw new NullPointerException("dest");
/*     */     }
/* 311 */     if (!this.file.renameTo(dest)) {
/*     */       
/* 313 */       FileInputStream inputStream = new FileInputStream(this.file);
/* 314 */       FileOutputStream outputStream = new FileOutputStream(dest);
/* 315 */       FileChannel in = inputStream.getChannel();
/* 316 */       FileChannel out = outputStream.getChannel();
/* 317 */       int chunkSize = 8196;
/* 318 */       long position = 0L;
/* 319 */       while (position < this.size) {
/* 320 */         if (chunkSize < this.size - position) {
/* 321 */           chunkSize = (int)(this.size - position);
/*     */         }
/* 323 */         position += in.transferTo(position, chunkSize, out);
/*     */       } 
/* 325 */       in.close();
/* 326 */       out.close();
/* 327 */       if (position == this.size) {
/* 328 */         this.file.delete();
/* 329 */         this.file = dest;
/* 330 */         this.isRenamed = true;
/* 331 */         return true;
/*     */       } 
/* 333 */       dest.delete();
/* 334 */       return false;
/*     */     } 
/*     */     
/* 337 */     this.file = dest;
/* 338 */     this.isRenamed = true;
/* 339 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readFrom(File src) throws IOException {
/* 347 */     long srcsize = src.length();
/* 348 */     if (srcsize > 2147483647L) {
/* 349 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/*     */     
/* 352 */     FileInputStream inputStream = new FileInputStream(src);
/* 353 */     FileChannel fileChannel = inputStream.getChannel();
/* 354 */     byte[] array = new byte[(int)srcsize];
/* 355 */     ByteBuffer byteBuffer = ByteBuffer.wrap(array);
/* 356 */     int read = 0;
/* 357 */     while (read < srcsize) {
/* 358 */       read += fileChannel.read(byteBuffer);
/*     */     }
/* 360 */     fileChannel.close();
/* 361 */     return array;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 366 */     return this.file;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\AbstractDiskHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */