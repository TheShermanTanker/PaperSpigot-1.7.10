/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.MalformedInputException;
/*     */ import java.nio.charset.UnmappableCharacterException;
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
/*     */ public class InputStreamReader
/*     */   extends Reader
/*     */ {
/*     */   private InputStream in;
/*     */   private static final int BUFFER_SIZE = 8192;
/*     */   private boolean endOfInput = false;
/*     */   String encoding;
/*     */   CharsetDecoder decoder;
/*  63 */   ByteBuffer bytes = ByteBuffer.allocate(8192);
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
/*     */   public InputStreamReader(InputStream in) {
/*  75 */     super(in);
/*  76 */     this.in = in;
/*  77 */     this.encoding = System.getProperty("file.encoding", "ISO8859_1");
/*  78 */     this.decoder = Charset.forName(this.encoding).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */ 
/*     */     
/*  81 */     this.bytes.limit(0);
/*     */   }
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
/*     */   public InputStreamReader(InputStream in, String enc) throws UnsupportedEncodingException {
/* 101 */     super(in);
/* 102 */     if (enc == null) {
/* 103 */       throw new NullPointerException();
/*     */     }
/* 105 */     this.in = in;
/*     */     try {
/* 107 */       this.decoder = Charset.forName(enc).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */     
/*     */     }
/* 110 */     catch (IllegalArgumentException e) {
/* 111 */       throw (UnsupportedEncodingException)(new UnsupportedEncodingException(enc)).initCause(e);
/*     */     } 
/*     */     
/* 114 */     this.bytes.limit(0);
/*     */   }
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
/*     */   public InputStreamReader(InputStream in, CharsetDecoder dec) {
/* 127 */     super(in);
/* 128 */     dec.averageCharsPerByte();
/* 129 */     this.in = in;
/* 130 */     this.decoder = dec;
/* 131 */     this.bytes.limit(0);
/*     */   }
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
/*     */   public InputStreamReader(InputStream in, Charset charset) {
/* 144 */     super(in);
/* 145 */     this.in = in;
/* 146 */     this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */ 
/*     */     
/* 149 */     this.bytes.limit(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 161 */     synchronized (this.lock) {
/* 162 */       this.decoder = null;
/* 163 */       if (this.in != null) {
/* 164 */         this.in.close();
/* 165 */         this.in = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 178 */     if (!isOpen()) {
/* 179 */       return null;
/*     */     }
/* 181 */     return this.encoding;
/*     */   }
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
/*     */   public int read() throws IOException {
/* 198 */     synchronized (this.lock) {
/* 199 */       if (!isOpen()) {
/* 200 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/*     */       
/* 203 */       char[] buf = new char[1];
/* 204 */       return (read(buf, 0, 1) != -1) ? buf[0] : -1;
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(char[] buf, int offset, int length) throws IOException {
/* 234 */     synchronized (this.lock) {
/* 235 */       if (!isOpen()) {
/* 236 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/* 238 */       if (offset < 0 || offset > buf.length - length || length < 0) {
/* 239 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 241 */       if (length == 0) {
/* 242 */         return 0;
/*     */       }
/*     */       
/* 245 */       CharBuffer out = CharBuffer.wrap(buf, offset, length);
/* 246 */       CoderResult result = CoderResult.UNDERFLOW;
/*     */ 
/*     */ 
/*     */       
/* 250 */       boolean needInput = !this.bytes.hasRemaining();
/*     */       
/* 252 */       while (out.hasRemaining()) {
/*     */         
/* 254 */         if (needInput) {
/*     */           try {
/* 256 */             if (this.in.available() == 0 && out.position() > offset)
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/* 261 */           catch (IOException e) {}
/*     */ 
/*     */ 
/*     */           
/* 265 */           int to_read = this.bytes.capacity() - this.bytes.limit();
/* 266 */           int off = this.bytes.arrayOffset() + this.bytes.limit();
/* 267 */           int was_red = this.in.read(this.bytes.array(), off, to_read);
/*     */           
/* 269 */           if (was_red == -1) {
/* 270 */             this.endOfInput = true; break;
/*     */           } 
/* 272 */           if (was_red == 0) {
/*     */             break;
/*     */           }
/* 275 */           this.bytes.limit(this.bytes.limit() + was_red);
/* 276 */           needInput = false;
/*     */         } 
/*     */ 
/*     */         
/* 280 */         result = this.decoder.decode(this.bytes, out, false);
/*     */         
/* 282 */         if (result.isUnderflow()) {
/*     */           
/* 284 */           if (this.bytes.limit() == this.bytes.capacity()) {
/* 285 */             this.bytes.compact();
/* 286 */             this.bytes.limit(this.bytes.position());
/* 287 */             this.bytes.position(0);
/*     */           } 
/* 289 */           needInput = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 295 */       if (result == CoderResult.UNDERFLOW && this.endOfInput) {
/* 296 */         result = this.decoder.decode(this.bytes, out, true);
/* 297 */         this.decoder.flush(out);
/* 298 */         this.decoder.reset();
/*     */       } 
/* 300 */       if (result.isMalformed())
/* 301 */         throw new MalformedInputException(result.length()); 
/* 302 */       if (result.isUnmappable()) {
/* 303 */         throw new UnmappableCharacterException(result.length());
/*     */       }
/*     */       
/* 306 */       return (out.position() - offset == 0) ? -1 : (out.position() - offset);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isOpen() {
/* 315 */     return (this.in != null);
/*     */   }
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
/*     */   public boolean ready() throws IOException {
/* 333 */     synchronized (this.lock) {
/* 334 */       if (this.in == null) {
/* 335 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/*     */       try {
/* 338 */         return (this.bytes.hasRemaining() || this.in.available() > 0);
/* 339 */       } catch (IOException e) {
/* 340 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\InputStreamReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */