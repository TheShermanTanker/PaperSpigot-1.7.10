/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileReader;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.StringTokenizer;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetUtil
/*     */ {
/*     */   public static final Inet4Address LOCALHOST4;
/*     */   public static final Inet6Address LOCALHOST6;
/*     */   public static final InetAddress LOCALHOST;
/*     */   public static final NetworkInterface LOOPBACK_IF;
/*     */   public static final int SOMAXCONN;
/*  72 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtil.class);
/*     */ 
/*     */   
/*     */   static {
/*  76 */     NetworkInterface loopbackIface = null;
/*     */     try {
/*  78 */       Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
/*  79 */       while (ifaces.hasMoreElements()) {
/*     */         
/*  81 */         NetworkInterface iface = ifaces.nextElement();
/*  82 */         if (iface.isLoopback()) {
/*     */           
/*  84 */           loopbackIface = iface;
/*     */           break;
/*     */         } 
/*     */       } 
/*  88 */       if (loopbackIface == null) {
/*  89 */         logger.warn("Failed to find the loopback interface");
/*     */       }
/*  91 */     } catch (SocketException e) {
/*  92 */       logger.warn("Failed to find the loopback interface", e);
/*     */     } 
/*     */     
/*  95 */     LOOPBACK_IF = loopbackIface;
/*     */ 
/*     */     
/*  98 */     InetAddress localhost = null;
/*  99 */     if (LOOPBACK_IF != null) {
/* 100 */       logger.debug("Loopback interface: {}", LOOPBACK_IF.getDisplayName());
/* 101 */       Enumeration<InetAddress> addrs = LOOPBACK_IF.getInetAddresses();
/* 102 */       while (addrs.hasMoreElements()) {
/* 103 */         InetAddress a = addrs.nextElement();
/* 104 */         if (localhost == null) {
/* 105 */           logger.debug("Loopback address: {} (primary)", a);
/* 106 */           localhost = a; continue;
/*     */         } 
/* 108 */         logger.debug("Loopback address: {}", a);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 114 */     Inet4Address localhost4 = null;
/*     */     try {
/* 116 */       localhost4 = (Inet4Address)InetAddress.getByAddress(new byte[] { Byte.MAX_VALUE, 0, 0, 1 });
/* 117 */     } catch (Exception e) {
/*     */       
/* 119 */       PlatformDependent.throwException(e);
/*     */     } 
/* 121 */     LOCALHOST4 = localhost4;
/*     */ 
/*     */     
/* 124 */     Inet6Address localhost6 = null;
/*     */     try {
/* 126 */       localhost6 = (Inet6Address)InetAddress.getByAddress(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 });
/*     */     }
/* 128 */     catch (Exception e) {
/*     */       
/* 130 */       PlatformDependent.throwException(e);
/*     */     } 
/* 132 */     LOCALHOST6 = localhost6;
/*     */ 
/*     */     
/* 135 */     if (localhost == null) {
/*     */       try {
/* 137 */         if (NetworkInterface.getByInetAddress(LOCALHOST6) != null) {
/* 138 */           logger.debug("Using hard-coded IPv6 localhost address: {}", localhost6);
/* 139 */           localhost = localhost6;
/*     */         } 
/* 141 */       } catch (Exception e) {
/*     */       
/*     */       } finally {
/* 144 */         if (localhost == null) {
/* 145 */           logger.debug("Using hard-coded IPv4 localhost address: {}", localhost4);
/* 146 */           localhost = localhost4;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 151 */     LOCALHOST = localhost;
/*     */     
/* 153 */     int somaxconn = 3072;
/* 154 */     BufferedReader in = null;
/*     */     try {
/* 156 */       in = new BufferedReader(new FileReader("/proc/sys/net/core/somaxconn"));
/* 157 */       somaxconn = Integer.parseInt(in.readLine());
/* 158 */       logger.debug("/proc/sys/net/core/somaxconn: {}", Integer.valueOf(somaxconn));
/* 159 */     } catch (Exception e) {
/*     */     
/*     */     } finally {
/* 162 */       if (in != null) {
/*     */         try {
/* 164 */           in.close();
/* 165 */         } catch (Exception e) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 171 */     SOMAXCONN = somaxconn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] createByteArrayFromIpAddressString(String ipAddressString) {
/* 180 */     if (isValidIpV4Address(ipAddressString)) {
/* 181 */       StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ".");
/*     */ 
/*     */       
/* 184 */       byte[] byteAddress = new byte[4];
/* 185 */       for (int i = 0; i < 4; i++) {
/* 186 */         String token = tokenizer.nextToken();
/* 187 */         int tempInt = Integer.parseInt(token);
/* 188 */         byteAddress[i] = (byte)tempInt;
/*     */       } 
/*     */       
/* 191 */       return byteAddress;
/*     */     } 
/*     */     
/* 194 */     if (isValidIpV6Address(ipAddressString)) {
/* 195 */       if (ipAddressString.charAt(0) == '[') {
/* 196 */         ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
/*     */       }
/*     */       
/* 199 */       StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ":.", true);
/* 200 */       ArrayList<String> hexStrings = new ArrayList<String>();
/* 201 */       ArrayList<String> decStrings = new ArrayList<String>();
/* 202 */       String token = "";
/* 203 */       String prevToken = "";
/* 204 */       int doubleColonIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       while (tokenizer.hasMoreTokens()) {
/* 213 */         prevToken = token;
/* 214 */         token = tokenizer.nextToken();
/*     */         
/* 216 */         if (":".equals(token)) {
/* 217 */           if (":".equals(prevToken)) {
/* 218 */             doubleColonIndex = hexStrings.size(); continue;
/* 219 */           }  if (!prevToken.isEmpty())
/* 220 */             hexStrings.add(prevToken);  continue;
/*     */         } 
/* 222 */         if (".".equals(token)) {
/* 223 */           decStrings.add(prevToken);
/*     */         }
/*     */       } 
/*     */       
/* 227 */       if (":".equals(prevToken)) {
/* 228 */         if (":".equals(token)) {
/* 229 */           doubleColonIndex = hexStrings.size();
/*     */         } else {
/* 231 */           hexStrings.add(token);
/*     */         } 
/* 233 */       } else if (".".equals(prevToken)) {
/* 234 */         decStrings.add(token);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 239 */       int hexStringsLength = 8;
/*     */ 
/*     */ 
/*     */       
/* 243 */       if (!decStrings.isEmpty()) {
/* 244 */         hexStringsLength -= 2;
/*     */       }
/*     */ 
/*     */       
/* 248 */       if (doubleColonIndex != -1) {
/* 249 */         int numberToInsert = hexStringsLength - hexStrings.size();
/* 250 */         for (int j = 0; j < numberToInsert; j++) {
/* 251 */           hexStrings.add(doubleColonIndex, "0");
/*     */         }
/*     */       } 
/*     */       
/* 255 */       byte[] ipByteArray = new byte[16];
/*     */       
/*     */       int i;
/* 258 */       for (i = 0; i < hexStrings.size(); i++) {
/* 259 */         convertToBytes(hexStrings.get(i), ipByteArray, i * 2);
/*     */       }
/*     */ 
/*     */       
/* 263 */       for (i = 0; i < decStrings.size(); i++) {
/* 264 */         ipByteArray[i + 12] = (byte)(Integer.parseInt((String)decStrings.get(i)) & 0xFF);
/*     */       }
/* 266 */       return ipByteArray;
/*     */     } 
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void convertToBytes(String hexWord, byte[] ipByteArray, int byteIndex) {
/* 276 */     int hexWordLength = hexWord.length();
/* 277 */     int hexWordIndex = 0;
/* 278 */     ipByteArray[byteIndex] = 0;
/* 279 */     ipByteArray[byteIndex + 1] = 0;
/*     */ 
/*     */ 
/*     */     
/* 283 */     if (hexWordLength > 3) {
/* 284 */       int i = getIntValue(hexWord.charAt(hexWordIndex++));
/* 285 */       ipByteArray[byteIndex] = (byte)(ipByteArray[byteIndex] | i << 4);
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (hexWordLength > 2) {
/* 290 */       int i = getIntValue(hexWord.charAt(hexWordIndex++));
/* 291 */       ipByteArray[byteIndex] = (byte)(ipByteArray[byteIndex] | i);
/*     */     } 
/*     */ 
/*     */     
/* 295 */     if (hexWordLength > 1) {
/* 296 */       int i = getIntValue(hexWord.charAt(hexWordIndex++));
/* 297 */       ipByteArray[byteIndex + 1] = (byte)(ipByteArray[byteIndex + 1] | i << 4);
/*     */     } 
/*     */ 
/*     */     
/* 301 */     int charValue = getIntValue(hexWord.charAt(hexWordIndex));
/* 302 */     ipByteArray[byteIndex + 1] = (byte)(ipByteArray[byteIndex + 1] | charValue & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   static int getIntValue(char c) {
/* 307 */     switch (c) {
/*     */       case '0':
/* 309 */         return 0;
/*     */       case '1':
/* 311 */         return 1;
/*     */       case '2':
/* 313 */         return 2;
/*     */       case '3':
/* 315 */         return 3;
/*     */       case '4':
/* 317 */         return 4;
/*     */       case '5':
/* 319 */         return 5;
/*     */       case '6':
/* 321 */         return 6;
/*     */       case '7':
/* 323 */         return 7;
/*     */       case '8':
/* 325 */         return 8;
/*     */       case '9':
/* 327 */         return 9;
/*     */     } 
/*     */     
/* 330 */     c = Character.toLowerCase(c);
/* 331 */     switch (c) {
/*     */       case 'a':
/* 333 */         return 10;
/*     */       case 'b':
/* 335 */         return 11;
/*     */       case 'c':
/* 337 */         return 12;
/*     */       case 'd':
/* 339 */         return 13;
/*     */       case 'e':
/* 341 */         return 14;
/*     */       case 'f':
/* 343 */         return 15;
/*     */     } 
/* 345 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isValidIpV6Address(String ipAddress) {
/* 349 */     int length = ipAddress.length();
/* 350 */     boolean doubleColon = false;
/* 351 */     int numberOfColons = 0;
/* 352 */     int numberOfPeriods = 0;
/* 353 */     int numberOfPercent = 0;
/* 354 */     StringBuilder word = new StringBuilder();
/* 355 */     char c = Character.MIN_VALUE;
/*     */     
/* 357 */     int offset = 0;
/*     */     
/* 359 */     if (length < 2) {
/* 360 */       return false;
/*     */     }
/*     */     
/* 363 */     for (int i = 0; i < length; i++) {
/* 364 */       char prevChar = c;
/* 365 */       c = ipAddress.charAt(i);
/* 366 */       switch (c) {
/*     */ 
/*     */         
/*     */         case '[':
/* 370 */           if (i != 0) {
/* 371 */             return false;
/*     */           }
/* 373 */           if (ipAddress.charAt(length - 1) != ']') {
/* 374 */             return false;
/*     */           }
/* 376 */           offset = 1;
/* 377 */           if (length < 4) {
/* 378 */             return false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case ']':
/* 384 */           if (i != length - 1) {
/* 385 */             return false;
/*     */           }
/* 387 */           if (ipAddress.charAt(0) != '[') {
/* 388 */             return false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case '.':
/* 394 */           numberOfPeriods++;
/* 395 */           if (numberOfPeriods > 3) {
/* 396 */             return false;
/*     */           }
/* 398 */           if (!isValidIp4Word(word.toString())) {
/* 399 */             return false;
/*     */           }
/* 401 */           if (numberOfColons != 6 && !doubleColon) {
/* 402 */             return false;
/*     */           }
/*     */ 
/*     */           
/* 406 */           if (numberOfColons == 7 && ipAddress.charAt(offset) != ':' && ipAddress.charAt(1 + offset) != ':')
/*     */           {
/* 408 */             return false;
/*     */           }
/* 410 */           word.delete(0, word.length());
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case ':':
/* 417 */           if (i == offset && (ipAddress.length() <= i || ipAddress.charAt(i + 1) != ':')) {
/* 418 */             return false;
/*     */           }
/*     */           
/* 421 */           numberOfColons++;
/* 422 */           if (numberOfColons > 7) {
/* 423 */             return false;
/*     */           }
/* 425 */           if (numberOfPeriods > 0) {
/* 426 */             return false;
/*     */           }
/* 428 */           if (prevChar == ':') {
/* 429 */             if (doubleColon) {
/* 430 */               return false;
/*     */             }
/* 432 */             doubleColon = true;
/*     */           } 
/* 434 */           word.delete(0, word.length());
/*     */           break;
/*     */         case '%':
/* 437 */           if (numberOfColons == 0) {
/* 438 */             return false;
/*     */           }
/* 440 */           numberOfPercent++;
/*     */ 
/*     */           
/* 443 */           if (i + 1 >= length)
/*     */           {
/*     */             
/* 446 */             return false;
/*     */           }
/*     */           try {
/* 449 */             Integer.parseInt(ipAddress.substring(i + 1));
/* 450 */           } catch (NumberFormatException e) {
/*     */ 
/*     */ 
/*     */             
/* 454 */             return false;
/*     */           } 
/*     */           break;
/*     */         
/*     */         default:
/* 459 */           if (numberOfPercent == 0) {
/* 460 */             if (word != null && word.length() > 3) {
/* 461 */               return false;
/*     */             }
/* 463 */             if (!isValidHexChar(c)) {
/* 464 */               return false;
/*     */             }
/*     */           } 
/* 467 */           word.append(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 472 */     if (numberOfPeriods > 0) {
/*     */       
/* 474 */       if (numberOfPeriods != 3 || !isValidIp4Word(word.toString()) || numberOfColons >= 7) {
/* 475 */         return false;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 480 */       if (numberOfColons != 7 && !doubleColon) {
/* 481 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 487 */       if (numberOfPercent == 0 && 
/* 488 */         word.length() == 0 && ipAddress.charAt(length - 1 - offset) == ':' && ipAddress.charAt(length - 2 - offset) != ':')
/*     */       {
/* 490 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 495 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidIp4Word(String word) {
/* 500 */     if (word.length() < 1 || word.length() > 3) {
/* 501 */       return false;
/*     */     }
/* 503 */     for (int i = 0; i < word.length(); i++) {
/* 504 */       char c = word.charAt(i);
/* 505 */       if (c < '0' || c > '9') {
/* 506 */         return false;
/*     */       }
/*     */     } 
/* 509 */     if (Integer.parseInt(word) > 255) {
/* 510 */       return false;
/*     */     }
/* 512 */     return true;
/*     */   }
/*     */   
/*     */   static boolean isValidHexChar(char c) {
/* 516 */     return ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidIpV4Address(String value) {
/* 527 */     int periods = 0;
/*     */     
/* 529 */     int length = value.length();
/*     */     
/* 531 */     if (length > 15) {
/* 532 */       return false;
/*     */     }
/*     */     
/* 535 */     StringBuilder word = new StringBuilder();
/* 536 */     for (int i = 0; i < length; i++) {
/* 537 */       char c = value.charAt(i);
/* 538 */       if (c == '.')
/* 539 */       { periods++;
/* 540 */         if (periods > 3) {
/* 541 */           return false;
/*     */         }
/* 543 */         if (word.length() == 0) {
/* 544 */           return false;
/*     */         }
/* 546 */         if (Integer.parseInt(word.toString()) > 255) {
/* 547 */           return false;
/*     */         }
/* 549 */         word.delete(0, word.length()); }
/* 550 */       else { if (!Character.isDigit(c)) {
/* 551 */           return false;
/*     */         }
/* 553 */         if (word.length() > 2) {
/* 554 */           return false;
/*     */         }
/* 556 */         word.append(c); }
/*     */     
/*     */     } 
/*     */     
/* 560 */     if (word.length() == 0 || Integer.parseInt(word.toString()) > 255) {
/* 561 */       return false;
/*     */     }
/* 563 */     if (periods != 3) {
/* 564 */       return false;
/*     */     }
/* 566 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\NetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */