/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.OverlappingFileLockException;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public abstract class Platform
/*     */ {
/*     */   private static final Platform p;
/*     */   
/*     */   static {
/*     */     float f;
/*     */     String str;
/*     */     try {
/*  28 */       if (getProperty("java.vm.name").equals("SableVM"))
/*  29 */       { f = 1.2F; }
/*     */       else
/*  31 */       { f = Float.valueOf(getProperty("java.specification.version")).floatValue(); } 
/*  32 */     } catch (Exception exception) {
/*  33 */       System.err.println("WARNING: " + exception + " while trying to find jvm version -  assuming 1.1");
/*  34 */       f = 1.1F;
/*     */     } 
/*     */     
/*  37 */     if (f >= 1.4F) { str = "Jdk14"; }
/*  38 */     else if (f >= 1.3F) { str = "Jdk13"; }
/*  39 */     else if (f >= 1.2F) { str = "Jdk12"; }
/*  40 */     else if (f >= 1.1F) { str = "Jdk11"; }
/*  41 */     else { throw new Error("JVM Specification version: " + f + " is too old. (see org.ibex.util.Platform to add support)"); }
/*     */     
/*     */     try {
/*  44 */       p = (Platform)Class.forName(Platform.class.getName() + "$" + str).newInstance();
/*  45 */     } catch (Exception exception) {
/*  46 */       exception.printStackTrace();
/*  47 */       throw new Error("Error instansiating platform class");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getProperty(String paramString) {
/*     */     try {
/*  53 */       return System.getProperty(paramString);
/*  54 */     } catch (SecurityException securityException) {
/*  55 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean atomicCreateFile(File paramFile) throws IOException {
/*  61 */     return p._atomicCreateFile(paramFile);
/*     */   }
/*     */   
/*     */   public static Seekable.Lock lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/*  65 */     return p._lockFile(paramSeekable, paramRandomAccessFile, paramLong1, paramLong2, paramBoolean);
/*     */   }
/*     */   public static void socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException {
/*  68 */     p._socketHalfClose(paramSocket, paramBoolean);
/*     */   }
/*     */   public static void socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException {
/*  71 */     p._socketSetKeepAlive(paramSocket, paramBoolean);
/*     */   }
/*     */   public static InetAddress inetAddressFromBytes(byte[] paramArrayOfbyte) throws UnknownHostException {
/*  74 */     return p._inetAddressFromBytes(paramArrayOfbyte);
/*     */   }
/*     */   
/*  77 */   public static String timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) { return p._timeZoneGetDisplayName(paramTimeZone, paramBoolean1, paramBoolean2, paramLocale); } public static String timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2) {
/*  78 */     return timeZoneGetDisplayName(paramTimeZone, paramBoolean1, paramBoolean2, Locale.getDefault());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException {
/*  83 */     p._setFileLength(paramRandomAccessFile, paramInt);
/*     */   }
/*     */   public static File[] listRoots() {
/*  86 */     return p._listRoots();
/*     */   } abstract boolean _atomicCreateFile(File paramFile) throws IOException; abstract Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException; abstract void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException; abstract void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException; abstract InetAddress _inetAddressFromBytes(byte[] paramArrayOfbyte) throws UnknownHostException;
/*     */   public static File getRoot(File paramFile) {
/*  89 */     return p._getRoot(paramFile);
/*     */   } abstract String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale); abstract void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException;
/*     */   abstract File[] _listRoots();
/*     */   abstract File _getRoot(File paramFile);
/*     */   static class Jdk11 extends Platform { boolean _atomicCreateFile(File param1File) throws IOException {
/*  94 */       if (param1File.exists()) return false; 
/*  95 */       (new FileOutputStream(param1File)).close();
/*  96 */       return true;
/*     */     }
/*     */     Seekable.Lock _lockFile(Seekable param1Seekable, RandomAccessFile param1RandomAccessFile, long param1Long1, long param1Long2, boolean param1Boolean) throws IOException {
/*  99 */       throw new IOException("file locking requires jdk 1.4+");
/*     */     }
/*     */     void _socketHalfClose(Socket param1Socket, boolean param1Boolean) throws IOException {
/* 102 */       throw new IOException("half closing sockets not supported");
/*     */     }
/*     */     InetAddress _inetAddressFromBytes(byte[] param1ArrayOfbyte) throws UnknownHostException {
/* 105 */       if (param1ArrayOfbyte.length != 4) throw new UnknownHostException("only ipv4 addrs supported"); 
/* 106 */       return InetAddress.getByName("" + (param1ArrayOfbyte[0] & 0xFF) + "." + (param1ArrayOfbyte[1] & 0xFF) + "." + (param1ArrayOfbyte[2] & 0xFF) + "." + (param1ArrayOfbyte[3] & 0xFF));
/*     */     }
/*     */     void _socketSetKeepAlive(Socket param1Socket, boolean param1Boolean) throws SocketException {
/* 109 */       if (param1Boolean) throw new SocketException("keepalive not supported"); 
/*     */     }
/*     */     String _timeZoneGetDisplayName(TimeZone param1TimeZone, boolean param1Boolean1, boolean param1Boolean2, Locale param1Locale) {
/* 112 */       String[][] arrayOfString = (new DateFormatSymbols(param1Locale)).getZoneStrings();
/* 113 */       String str = param1TimeZone.getID();
/* 114 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 115 */         if (arrayOfString[b][0].equals(str))
/* 116 */           return arrayOfString[b][param1Boolean1 ? (param1Boolean2 ? 3 : 4) : (param1Boolean2 ? 1 : 2)]; 
/* 117 */       }  StringBuffer stringBuffer = new StringBuffer("GMT");
/* 118 */       int i = param1TimeZone.getRawOffset() / 1000;
/* 119 */       if (i < 0) { stringBuffer.append("-"); i = -i; }
/* 120 */       else { stringBuffer.append("+"); }
/* 121 */        stringBuffer.append(i / 3600); i %= 3600;
/* 122 */       if (i > 0) stringBuffer.append(":").append(i / 60);  i %= 60;
/* 123 */       if (i > 0) stringBuffer.append(":").append(i); 
/* 124 */       return stringBuffer.toString();
/*     */     }
/*     */     
/*     */     void _setFileLength(RandomAccessFile param1RandomAccessFile, int param1Int) throws IOException {
/* 128 */       FileInputStream fileInputStream = new FileInputStream(param1RandomAccessFile.getFD());
/* 129 */       FileOutputStream fileOutputStream = new FileOutputStream(param1RandomAccessFile.getFD());
/*     */       
/* 131 */       byte[] arrayOfByte = new byte[1024];
/* 132 */       for (; param1Int > 0; param1Int -= i) {
/* 133 */         int i = fileInputStream.read(arrayOfByte, 0, Math.min(param1Int, arrayOfByte.length));
/* 134 */         if (i == -1)
/* 135 */           break;  fileOutputStream.write(arrayOfByte, 0, i);
/*     */       } 
/* 137 */       if (param1Int == 0) {
/*     */         return;
/*     */       }
/* 140 */       for (byte b = 0; b < arrayOfByte.length; ) { arrayOfByte[b] = 0; b++; }
/* 141 */        while (param1Int > 0) {
/* 142 */         fileOutputStream.write(arrayOfByte, 0, Math.min(param1Int, arrayOfByte.length));
/* 143 */         param1Int -= arrayOfByte.length;
/*     */       } 
/*     */     }
/*     */     
/*     */     RandomAccessFile _truncatedRandomAccessFile(File param1File, String param1String) throws IOException {
/* 148 */       (new FileOutputStream(param1File)).close();
/* 149 */       return new RandomAccessFile(param1File, param1String);
/*     */     }
/*     */     
/*     */     File[] _listRoots() {
/* 153 */       String[] arrayOfString = { "java.home", "java.class.path", "java.library.path", "java.io.tmpdir", "java.ext.dirs", "user.home", "user.dir" };
/* 154 */       Hashtable hashtable = new Hashtable();
/* 155 */       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 156 */         String str = getProperty(arrayOfString[b1]);
/* 157 */         if (str != null) {
/*     */           int i; do {
/* 159 */             String str1 = str;
/*     */             
/* 161 */             if ((i = str.indexOf(File.pathSeparatorChar)) != -1) {
/* 162 */               str1 = str.substring(0, i);
/* 163 */               str = str.substring(i + 1);
/*     */             } 
/* 165 */             File file = getRoot(new File(str1));
/*     */             
/* 167 */             hashtable.put(file, Boolean.TRUE);
/* 168 */           } while (i != -1);
/*     */         } 
/*     */       } 
/* 171 */       File[] arrayOfFile = new File[hashtable.size()];
/* 172 */       byte b2 = 0;
/* 173 */       for (Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();)
/* 174 */         arrayOfFile[b2++] = enumeration.nextElement(); 
/* 175 */       return arrayOfFile;
/*     */     }
/*     */     
/*     */     File _getRoot(File param1File) {
/* 179 */       if (!param1File.isAbsolute()) param1File = new File(param1File.getAbsolutePath()); 
/*     */       String str;
/* 181 */       for (; (str = param1File.getParent()) != null; param1File = new File(str));
/* 182 */       if (param1File.getPath().length() == 0) param1File = new File("/"); 
/* 183 */       return param1File;
/*     */     } }
/*     */ 
/*     */   
/*     */   static class Jdk12 extends Jdk11 {
/*     */     boolean _atomicCreateFile(File param1File) throws IOException {
/* 189 */       return param1File.createNewFile();
/*     */     }
/*     */     
/*     */     String _timeZoneGetDisplayName(TimeZone param1TimeZone, boolean param1Boolean1, boolean param1Boolean2, Locale param1Locale) {
/* 193 */       return param1TimeZone.getDisplayName(param1Boolean1, param1Boolean2 ? 1 : 0, param1Locale);
/*     */     }
/*     */     
/*     */     void _setFileLength(RandomAccessFile param1RandomAccessFile, int param1Int) throws IOException {
/* 197 */       param1RandomAccessFile.setLength(param1Int);
/*     */     }
/*     */     File[] _listRoots() {
/* 200 */       return File.listRoots();
/*     */     }
/*     */   }
/*     */   
/*     */   static class Jdk13 extends Jdk12 { void _socketHalfClose(Socket param1Socket, boolean param1Boolean) throws IOException {
/* 205 */       if (param1Boolean) { param1Socket.shutdownOutput(); }
/* 206 */       else { param1Socket.shutdownInput(); }
/*     */     
/*     */     }
/*     */     void _socketSetKeepAlive(Socket param1Socket, boolean param1Boolean) throws SocketException {
/* 210 */       param1Socket.setKeepAlive(param1Boolean);
/*     */     } }
/*     */   
/*     */   static class Jdk14 extends Jdk13 {
/*     */     InetAddress _inetAddressFromBytes(byte[] param1ArrayOfbyte) throws UnknownHostException {
/* 215 */       return InetAddress.getByAddress(param1ArrayOfbyte);
/*     */     }
/*     */     Seekable.Lock _lockFile(Seekable param1Seekable, RandomAccessFile param1RandomAccessFile, long param1Long1, long param1Long2, boolean param1Boolean) throws IOException {
/*     */       FileLock fileLock;
/*     */       try {
/* 220 */         fileLock = (param1Long1 == 0L && param1Long2 == 0L) ? param1RandomAccessFile.getChannel().lock() : param1RandomAccessFile.getChannel().tryLock(param1Long1, param1Long2, param1Boolean);
/*     */       } catch (OverlappingFileLockException overlappingFileLockException) {
/* 222 */         fileLock = null;
/* 223 */       }  if (fileLock == null) return null; 
/* 224 */       return new Platform.Jdk14FileLock(param1Seekable, fileLock);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Jdk14FileLock extends Seekable.Lock {
/*     */     private final Seekable s;
/*     */     private final FileLock l;
/*     */     
/* 232 */     Jdk14FileLock(Seekable param1Seekable, FileLock param1FileLock) { this.s = param1Seekable; this.l = param1FileLock; }
/* 233 */     public Seekable seekable() { return this.s; }
/* 234 */     public boolean isShared() { return this.l.isShared(); }
/* 235 */     public boolean isValid() { return this.l.isValid(); }
/* 236 */     public void release() throws IOException { this.l.release(); }
/* 237 */     public long position() { return this.l.position(); }
/* 238 */     public long size() { return this.l.size(); } public String toString() {
/* 239 */       return this.l.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedv\\util\Platform.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */