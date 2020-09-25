/*     */ package com.avaje.ebeaninternal.server.cluster.socket;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class SocketConnection
/*     */ {
/*     */   ObjectOutputStream oos;
/*     */   ObjectInputStream ois;
/*     */   InputStream is;
/*     */   OutputStream os;
/*     */   Socket socket;
/*     */   
/*     */   public SocketConnection(Socket socket) throws IOException {
/*  61 */     this.is = socket.getInputStream();
/*  62 */     this.os = socket.getOutputStream();
/*  63 */     this.socket = socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() throws IOException {
/*  70 */     this.os.flush();
/*  71 */     this.socket.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/*  78 */     this.os.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readObject() throws IOException, ClassNotFoundException {
/*  85 */     return getObjectInputStream().readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutputStream writeObject(Object object) throws IOException {
/*  92 */     ObjectOutputStream oos = getObjectOutputStream();
/*  93 */     oos.writeObject(object);
/*  94 */     return oos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutputStream getObjectOutputStream() throws IOException {
/* 101 */     if (this.oos == null) {
/* 102 */       this.oos = new ObjectOutputStream(this.os);
/*     */     }
/* 104 */     return this.oos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInputStream getObjectInputStream() throws IOException {
/* 111 */     if (this.ois == null) {
/* 112 */       this.ois = new ObjectInputStream(this.is);
/*     */     }
/* 114 */     return this.ois;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectInputStream(ObjectInputStream ois) {
/* 122 */     this.ois = ois;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectOutputStream(ObjectOutputStream oos) {
/* 129 */     this.oos = oos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 136 */     return this.is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/* 143 */     return this.os;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\socket\SocketConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */