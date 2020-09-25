/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public final class UUIDUtil
/*     */ {
/*     */   public static final String UUID_SEQUENCE = "org.apache.logging.log4j.uuidSequence";
/*     */   private static final String ASSIGNED_SEQUENCES = "org.apache.logging.log4j.assignedSequences";
/*  44 */   private static AtomicInteger count = new AtomicInteger(0);
/*     */   
/*     */   private static final long TYPE1 = 4096L;
/*     */   
/*     */   private static final byte VARIANT = -128;
/*     */   
/*     */   private static final int SEQUENCE_MASK = 16383;
/*     */   
/*     */   private static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 122192928000000000L;
/*     */   
/*  54 */   private static long uuidSequence = PropertiesUtil.getProperties().getLongProperty("org.apache.logging.log4j.uuidSequence", 0L);
/*     */   
/*     */   private static long least;
/*     */   
/*     */   private static final long LOW_MASK = 4294967295L;
/*     */   private static final long MID_MASK = 281470681743360L;
/*     */   private static final long HIGH_MASK = 1152640029630136320L;
/*     */   private static final int NODE_SIZE = 8;
/*     */   private static final int SHIFT_2 = 16;
/*     */   private static final int SHIFT_4 = 32;
/*     */   private static final int SHIFT_6 = 48;
/*     */   private static final int HUNDRED_NANOS_PER_MILLI = 10000;
/*     */   
/*     */   static {
/*  68 */     byte[] mac = null;
/*     */     try {
/*  70 */       InetAddress address = InetAddress.getLocalHost();
/*     */       try {
/*  72 */         NetworkInterface ni = NetworkInterface.getByInetAddress(address);
/*  73 */         if (ni != null && !ni.isLoopback() && ni.isUp()) {
/*  74 */           Method method = ni.getClass().getMethod("getHardwareAddress", new Class[0]);
/*  75 */           if (method != null) {
/*  76 */             mac = (byte[])method.invoke(ni, new Object[0]);
/*     */           }
/*     */         } 
/*     */         
/*  80 */         if (mac == null) {
/*  81 */           Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
/*  82 */           while (enumeration.hasMoreElements() && mac == null) {
/*  83 */             ni = enumeration.nextElement();
/*  84 */             if (ni != null && ni.isUp() && !ni.isLoopback()) {
/*  85 */               Method method = ni.getClass().getMethod("getHardwareAddress", new Class[0]);
/*  86 */               if (method != null) {
/*  87 */                 mac = (byte[])method.invoke(ni, new Object[0]);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*  92 */       } catch (Exception ex) {
/*  93 */         ex.printStackTrace();
/*     */       } 
/*     */       
/*  96 */       if (mac == null || mac.length == 0) {
/*  97 */         mac = address.getAddress();
/*     */       }
/*  99 */     } catch (UnknownHostException e) {}
/*     */ 
/*     */     
/* 102 */     Random randomGenerator = new SecureRandom();
/* 103 */     if (mac == null || mac.length == 0) {
/* 104 */       mac = new byte[6];
/* 105 */       randomGenerator.nextBytes(mac);
/*     */     } 
/* 107 */     int length = (mac.length >= 6) ? 6 : mac.length;
/* 108 */     int index = (mac.length >= 6) ? (mac.length - 6) : 0;
/* 109 */     byte[] node = new byte[8];
/* 110 */     node[0] = Byte.MIN_VALUE;
/* 111 */     node[1] = 0;
/* 112 */     for (int i = 2; i < 8; i++) {
/* 113 */       node[i] = 0;
/*     */     }
/* 115 */     System.arraycopy(mac, index, node, index + 2, length);
/* 116 */     ByteBuffer buf = ByteBuffer.wrap(node);
/* 117 */     long rand = uuidSequence;
/* 118 */     Runtime runtime = Runtime.getRuntime();
/* 119 */     synchronized (runtime) {
/* 120 */       long[] sequences; String assigned = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.assignedSequences");
/*     */       
/* 122 */       if (assigned == null) {
/* 123 */         sequences = new long[0];
/*     */       } else {
/* 125 */         String[] array = assigned.split(",");
/* 126 */         sequences = new long[array.length];
/* 127 */         int j = 0;
/* 128 */         for (String value : array) {
/* 129 */           sequences[j] = Long.parseLong(value);
/* 130 */           j++;
/*     */         } 
/*     */       } 
/* 133 */       if (rand == 0L) {
/* 134 */         rand = randomGenerator.nextLong();
/*     */       }
/* 136 */       rand &= 0x3FFFL;
/*     */       
/*     */       while (true) {
/* 139 */         boolean duplicate = false;
/* 140 */         for (long sequence : sequences) {
/* 141 */           if (sequence == rand) {
/* 142 */             duplicate = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 146 */         if (duplicate) {
/* 147 */           rand = rand + 1L & 0x3FFFL;
/*     */         }
/* 149 */         if (!duplicate) {
/* 150 */           assigned = (assigned == null) ? Long.toString(rand) : (assigned + "," + Long.toString(rand));
/* 151 */           System.setProperty("org.apache.logging.log4j.assignedSequences", assigned);
/*     */ 
/*     */           
/* 154 */           least = buf.getLong() | rand << 48L;
/*     */           return;
/*     */         } 
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
/*     */   public static UUID getTimeBasedUUID() {
/* 180 */     long time = System.currentTimeMillis() * 10000L + 122192928000000000L + (count.incrementAndGet() % 10000);
/*     */     
/* 182 */     long timeLow = (time & 0xFFFFFFFFL) << 32L;
/* 183 */     long timeMid = (time & 0xFFFF00000000L) >> 16L;
/* 184 */     long timeHi = (time & 0xFFF000000000000L) >> 48L;
/* 185 */     long most = timeLow | timeMid | 0x1000L | timeHi;
/* 186 */     return new UUID(most, least);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\UUIDUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */