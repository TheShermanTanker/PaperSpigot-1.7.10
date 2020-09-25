/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.util.Properties;
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
/*     */ public class NamedPipeSocketFactory
/*     */   implements SocketFactory
/*     */ {
/*     */   public static final String NAMED_PIPE_PROP_NAME = "namedPipePath";
/*     */   private Socket namedPipeSocket;
/*     */   
/*     */   class NamedPipeSocket
/*     */     extends Socket
/*     */   {
/*     */     private boolean isClosed = false;
/*     */     private RandomAccessFile namedPipeFile;
/*     */     
/*     */     NamedPipeSocket(String filePath) throws IOException {
/*  51 */       if (filePath == null || filePath.length() == 0) {
/*  52 */         throw new IOException(Messages.getString("NamedPipeSocketFactory.4"));
/*     */       }
/*     */ 
/*     */       
/*  56 */       this.namedPipeFile = new RandomAccessFile(filePath, "rw");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void close() throws IOException {
/*  63 */       this.namedPipeFile.close();
/*  64 */       this.isClosed = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getInputStream() throws IOException {
/*  71 */       return new NamedPipeSocketFactory.RandomAccessFileInputStream(this.namedPipeFile);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OutputStream getOutputStream() throws IOException {
/*  78 */       return new NamedPipeSocketFactory.RandomAccessFileOutputStream(this.namedPipeFile);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isClosed() {
/*  85 */       return this.isClosed;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class RandomAccessFileInputStream
/*     */     extends InputStream
/*     */   {
/*     */     RandomAccessFile raFile;
/*     */     
/*     */     RandomAccessFileInputStream(RandomAccessFile file) {
/*  96 */       this.raFile = file;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int available() throws IOException {
/* 103 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 110 */       this.raFile.close();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 117 */       return this.raFile.read();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] b) throws IOException {
/* 124 */       return this.raFile.read(b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int len) throws IOException {
/* 131 */       return this.raFile.read(b, off, len);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class RandomAccessFileOutputStream
/*     */     extends OutputStream
/*     */   {
/*     */     RandomAccessFile raFile;
/*     */     
/*     */     RandomAccessFileOutputStream(RandomAccessFile file) {
/* 142 */       this.raFile = file;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 149 */       this.raFile.close();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(byte[] b) throws IOException {
/* 156 */       this.raFile.write(b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(byte[] b, int off, int len) throws IOException {
/* 163 */       this.raFile.write(b, off, len);
/*     */     }
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
/*     */     public void write(int b) throws IOException {}
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
/*     */   public Socket afterHandshake() throws SocketException, IOException {
/* 188 */     return this.namedPipeSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket beforeHandshake() throws SocketException, IOException {
/* 195 */     return this.namedPipeSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket connect(String host, int portNumber, Properties props) throws SocketException, IOException {
/* 203 */     String namedPipePath = props.getProperty("namedPipePath");
/*     */     
/* 205 */     if (namedPipePath == null) {
/* 206 */       namedPipePath = "\\\\.\\pipe\\MySQL";
/* 207 */     } else if (namedPipePath.length() == 0) {
/* 208 */       throw new SocketException(Messages.getString("NamedPipeSocketFactory.2") + "namedPipePath" + Messages.getString("NamedPipeSocketFactory.3"));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     this.namedPipeSocket = new NamedPipeSocket(namedPipePath);
/*     */     
/* 216 */     return this.namedPipeSocket;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\NamedPipeSocketFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */