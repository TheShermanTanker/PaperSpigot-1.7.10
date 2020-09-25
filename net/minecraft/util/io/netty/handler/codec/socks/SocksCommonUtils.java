/*    */ package net.minecraft.util.io.netty.handler.codec.socks;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SocksCommonUtils
/*    */ {
/* 19 */   public static final SocksRequest UNKNOWN_SOCKS_REQUEST = new UnknownSocksRequest();
/* 20 */   public static final SocksResponse UNKNOWN_SOCKS_RESPONSE = new UnknownSocksResponse();
/*    */ 
/*    */   
/*    */   private static final int SECOND_ADDRESS_OCTET_SHIFT = 16;
/*    */ 
/*    */   
/*    */   private static final int FIRST_ADDRESS_OCTET_SHIFT = 24;
/*    */ 
/*    */   
/*    */   private static final int THIRD_ADDRESS_OCTET_SHIFT = 8;
/*    */   
/*    */   private static final int XOR_DEFAULT_VALUE = 255;
/*    */ 
/*    */   
/*    */   public static String intToIp(int i) {
/* 35 */     return String.valueOf(i >> 24 & 0xFF) + '.' + (i >> 16 & 0xFF) + '.' + (i >> 8 & 0xFF) + '.' + (i & 0xFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   private static final char[] ipv6conseqZeroFiller = new char[] { ':', ':' };
/*    */ 
/*    */   
/*    */   private static final char ipv6hextetSeparator = ':';
/*    */ 
/*    */ 
/*    */   
/*    */   public static String ipv6toCompressedForm(byte[] src) {
/* 49 */     assert src.length == 16;
/*    */ 
/*    */     
/* 52 */     int cmprHextet = -1;
/*    */     
/* 54 */     int cmprSize = 0;
/* 55 */     for (int hextet = 0; hextet < 8; ) {
/* 56 */       int curByte = hextet * 2;
/* 57 */       int size = 0;
/*    */       
/* 59 */       while (curByte < src.length && src[curByte] == 0 && src[curByte + 1] == 0) {
/* 60 */         curByte += 2;
/* 61 */         size++;
/*    */       } 
/* 63 */       if (size > cmprSize) {
/* 64 */         cmprHextet = hextet;
/* 65 */         cmprSize = size;
/*    */       } 
/* 67 */       hextet = curByte / 2 + 1;
/*    */     } 
/* 69 */     if (cmprHextet == -1 || cmprSize < 2)
/*    */     {
/* 71 */       return ipv6toStr(src);
/*    */     }
/* 73 */     StringBuilder sb = new StringBuilder(39);
/* 74 */     ipv6toStr(sb, src, 0, cmprHextet);
/* 75 */     sb.append(ipv6conseqZeroFiller);
/* 76 */     ipv6toStr(sb, src, cmprHextet + cmprSize, 8);
/* 77 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String ipv6toStr(byte[] src) {
/* 87 */     assert src.length == 16;
/* 88 */     StringBuilder sb = new StringBuilder(39);
/* 89 */     ipv6toStr(sb, src, 0, 8);
/* 90 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   private static void ipv6toStr(StringBuilder sb, byte[] src, int fromHextet, int toHextet) {
/* 95 */     for (int i = fromHextet; i < toHextet; i++) {
/* 96 */       sb.append(Integer.toHexString(src[i << 1] << 8 & 0xFF00 | src[(i << 1) + 1] & 0xFF));
/*    */       
/* 98 */       if (i < toHextet - 1)
/* 99 */         sb.append(':'); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\socks\SocksCommonUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */