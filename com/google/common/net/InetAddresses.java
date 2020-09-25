/*      */ package com.google.common.net;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.io.ByteStreams;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.net.Inet4Address;
/*      */ import java.net.Inet6Address;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.Arrays;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Beta
/*      */ public final class InetAddresses
/*      */ {
/*      */   private static final int IPV4_PART_COUNT = 4;
/*      */   private static final int IPV6_PART_COUNT = 8;
/*  122 */   private static final Inet4Address LOOPBACK4 = (Inet4Address)forString("127.0.0.1");
/*      */   
/*  124 */   private static final Inet4Address ANY4 = (Inet4Address)forString("0.0.0.0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Inet4Address getInet4Address(byte[] bytes) {
/*  141 */     Preconditions.checkArgument((bytes.length == 4), "Byte array has invalid length for an IPv4 address: %s != 4.", new Object[] { Integer.valueOf(bytes.length) });
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  146 */       InetAddress ipv4 = InetAddress.getByAddress(bytes);
/*  147 */       if (!(ipv4 instanceof Inet4Address)) {
/*  148 */         throw new UnknownHostException(String.format("'%s' is not an IPv4 address.", new Object[] { ipv4.getHostAddress() }));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  153 */       return (Inet4Address)ipv4;
/*  154 */     } catch (UnknownHostException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  166 */       throw new IllegalArgumentException(String.format("Host address '%s' is not a valid IPv4 address.", new Object[] { Arrays.toString(bytes) }), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InetAddress forString(String ipString) {
/*  186 */     byte[] addr = ipStringToBytes(ipString);
/*      */ 
/*      */     
/*  189 */     if (addr == null) {
/*  190 */       throw new IllegalArgumentException(String.format("'%s' is not an IP string literal.", new Object[] { ipString }));
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  195 */       return InetAddress.getByAddress(addr);
/*  196 */     } catch (UnknownHostException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  210 */       throw new IllegalArgumentException(String.format("'%s' is extremely broken.", new Object[] { ipString }), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInetAddress(String ipString) {
/*  223 */     return (ipStringToBytes(ipString) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   private static byte[] ipStringToBytes(String ipString) {
/*  228 */     boolean hasColon = false;
/*  229 */     boolean hasDot = false;
/*  230 */     for (int i = 0; i < ipString.length(); i++) {
/*  231 */       char c = ipString.charAt(i);
/*  232 */       if (c == '.') {
/*  233 */         hasDot = true;
/*  234 */       } else if (c == ':') {
/*  235 */         if (hasDot) {
/*  236 */           return null;
/*      */         }
/*  238 */         hasColon = true;
/*  239 */       } else if (Character.digit(c, 16) == -1) {
/*  240 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  245 */     if (hasColon) {
/*  246 */       if (hasDot) {
/*  247 */         ipString = convertDottedQuadToHex(ipString);
/*  248 */         if (ipString == null) {
/*  249 */           return null;
/*      */         }
/*      */       } 
/*  252 */       return textToNumericFormatV6(ipString);
/*  253 */     }  if (hasDot) {
/*  254 */       return textToNumericFormatV4(ipString);
/*      */     }
/*  256 */     return null;
/*      */   }
/*      */   
/*      */   private static byte[] textToNumericFormatV4(String ipString) {
/*  260 */     String[] address = ipString.split("\\.", 5);
/*  261 */     if (address.length != 4) {
/*  262 */       return null;
/*      */     }
/*      */     
/*  265 */     byte[] bytes = new byte[4];
/*      */     try {
/*  267 */       for (int i = 0; i < bytes.length; i++) {
/*  268 */         bytes[i] = parseOctet(address[i]);
/*      */       }
/*  270 */     } catch (NumberFormatException ex) {
/*  271 */       return null;
/*      */     } 
/*      */     
/*  274 */     return bytes;
/*      */   }
/*      */   
/*      */   private static byte[] textToNumericFormatV6(String ipString) {
/*      */     int partsHi, partsLo;
/*  279 */     String[] parts = ipString.split(":", 10);
/*  280 */     if (parts.length < 3 || parts.length > 9) {
/*  281 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  286 */     int skipIndex = -1;
/*  287 */     for (int i = 1; i < parts.length - 1; i++) {
/*  288 */       if (parts[i].length() == 0) {
/*  289 */         if (skipIndex >= 0) {
/*  290 */           return null;
/*      */         }
/*  292 */         skipIndex = i;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  298 */     if (skipIndex >= 0) {
/*      */       
/*  300 */       partsHi = skipIndex;
/*  301 */       partsLo = parts.length - skipIndex - 1;
/*  302 */       if (parts[0].length() == 0 && --partsHi != 0) {
/*  303 */         return null;
/*      */       }
/*  305 */       if (parts[parts.length - 1].length() == 0 && --partsLo != 0) {
/*  306 */         return null;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  311 */       partsHi = parts.length;
/*  312 */       partsLo = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  317 */     int partsSkipped = 8 - partsHi + partsLo;
/*  318 */     if ((skipIndex >= 0) ? (partsSkipped >= 1) : (partsSkipped == 0)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  323 */       ByteBuffer rawBytes = ByteBuffer.allocate(16); try {
/*      */         int j;
/*  325 */         for (j = 0; j < partsHi; j++) {
/*  326 */           rawBytes.putShort(parseHextet(parts[j]));
/*      */         }
/*  328 */         for (j = 0; j < partsSkipped; j++) {
/*  329 */           rawBytes.putShort((short)0);
/*      */         }
/*  331 */         for (j = partsLo; j > 0; j--) {
/*  332 */           rawBytes.putShort(parseHextet(parts[parts.length - j]));
/*      */         }
/*  334 */       } catch (NumberFormatException ex) {
/*  335 */         return null;
/*      */       } 
/*  337 */       return rawBytes.array();
/*      */     } 
/*      */     return null;
/*      */   } private static String convertDottedQuadToHex(String ipString) {
/*  341 */     int lastColon = ipString.lastIndexOf(':');
/*  342 */     String initialPart = ipString.substring(0, lastColon + 1);
/*  343 */     String dottedQuad = ipString.substring(lastColon + 1);
/*  344 */     byte[] quad = textToNumericFormatV4(dottedQuad);
/*  345 */     if (quad == null) {
/*  346 */       return null;
/*      */     }
/*  348 */     String penultimate = Integer.toHexString((quad[0] & 0xFF) << 8 | quad[1] & 0xFF);
/*  349 */     String ultimate = Integer.toHexString((quad[2] & 0xFF) << 8 | quad[3] & 0xFF);
/*  350 */     return initialPart + penultimate + ":" + ultimate;
/*      */   }
/*      */ 
/*      */   
/*      */   private static byte parseOctet(String ipPart) {
/*  355 */     int octet = Integer.parseInt(ipPart);
/*      */ 
/*      */     
/*  358 */     if (octet > 255 || (ipPart.startsWith("0") && ipPart.length() > 1)) {
/*  359 */       throw new NumberFormatException();
/*      */     }
/*  361 */     return (byte)octet;
/*      */   }
/*      */ 
/*      */   
/*      */   private static short parseHextet(String ipPart) {
/*  366 */     int hextet = Integer.parseInt(ipPart, 16);
/*  367 */     if (hextet > 65535) {
/*  368 */       throw new NumberFormatException();
/*      */     }
/*  370 */     return (short)hextet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toAddrString(InetAddress ip) {
/*  391 */     Preconditions.checkNotNull(ip);
/*  392 */     if (ip instanceof Inet4Address)
/*      */     {
/*  394 */       return ip.getHostAddress();
/*      */     }
/*  396 */     Preconditions.checkArgument(ip instanceof Inet6Address);
/*  397 */     byte[] bytes = ip.getAddress();
/*  398 */     int[] hextets = new int[8];
/*  399 */     for (int i = 0; i < hextets.length; i++) {
/*  400 */       hextets[i] = Ints.fromBytes((byte)0, (byte)0, bytes[2 * i], bytes[2 * i + 1]);
/*      */     }
/*      */     
/*  403 */     compressLongestRunOfZeroes(hextets);
/*  404 */     return hextetsToIPv6String(hextets);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void compressLongestRunOfZeroes(int[] hextets) {
/*  417 */     int bestRunStart = -1;
/*  418 */     int bestRunLength = -1;
/*  419 */     int runStart = -1;
/*  420 */     for (int i = 0; i < hextets.length + 1; i++) {
/*  421 */       if (i < hextets.length && hextets[i] == 0) {
/*  422 */         if (runStart < 0) {
/*  423 */           runStart = i;
/*      */         }
/*  425 */       } else if (runStart >= 0) {
/*  426 */         int runLength = i - runStart;
/*  427 */         if (runLength > bestRunLength) {
/*  428 */           bestRunStart = runStart;
/*  429 */           bestRunLength = runLength;
/*      */         } 
/*  431 */         runStart = -1;
/*      */       } 
/*      */     } 
/*  434 */     if (bestRunLength >= 2) {
/*  435 */       Arrays.fill(hextets, bestRunStart, bestRunStart + bestRunLength, -1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String hextetsToIPv6String(int[] hextets) {
/*  454 */     StringBuilder buf = new StringBuilder(39);
/*  455 */     boolean lastWasNumber = false;
/*  456 */     for (int i = 0; i < hextets.length; i++) {
/*  457 */       boolean thisIsNumber = (hextets[i] >= 0);
/*  458 */       if (thisIsNumber) {
/*  459 */         if (lastWasNumber) {
/*  460 */           buf.append(':');
/*      */         }
/*  462 */         buf.append(Integer.toHexString(hextets[i]));
/*      */       }
/*  464 */       else if (i == 0 || lastWasNumber) {
/*  465 */         buf.append("::");
/*      */       } 
/*      */       
/*  468 */       lastWasNumber = thisIsNumber;
/*      */     } 
/*  470 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toUriString(InetAddress ip) {
/*  499 */     if (ip instanceof Inet6Address) {
/*  500 */       return "[" + toAddrString(ip) + "]";
/*      */     }
/*  502 */     return toAddrString(ip);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InetAddress forUriString(String hostAddr) {
/*  521 */     Preconditions.checkNotNull(hostAddr);
/*  522 */     Preconditions.checkArgument((hostAddr.length() > 0), "host string is empty");
/*  523 */     InetAddress retval = null;
/*      */ 
/*      */     
/*      */     try {
/*  527 */       retval = forString(hostAddr);
/*  528 */       if (retval instanceof Inet4Address) {
/*  529 */         return retval;
/*      */       }
/*  531 */     } catch (IllegalArgumentException e) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  536 */     if (!hostAddr.startsWith("[") || !hostAddr.endsWith("]")) {
/*  537 */       throw new IllegalArgumentException("Not a valid address: \"" + hostAddr + '"');
/*      */     }
/*      */     
/*  540 */     retval = forString(hostAddr.substring(1, hostAddr.length() - 1));
/*  541 */     if (retval instanceof Inet6Address) {
/*  542 */       return retval;
/*      */     }
/*      */     
/*  545 */     throw new IllegalArgumentException("Not a valid address: \"" + hostAddr + '"');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUriInetAddress(String ipString) {
/*      */     try {
/*  557 */       forUriString(ipString);
/*  558 */       return true;
/*  559 */     } catch (IllegalArgumentException e) {
/*  560 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCompatIPv4Address(Inet6Address ip) {
/*  590 */     if (!ip.isIPv4CompatibleAddress()) {
/*  591 */       return false;
/*      */     }
/*      */     
/*  594 */     byte[] bytes = ip.getAddress();
/*  595 */     if (bytes[12] == 0 && bytes[13] == 0 && bytes[14] == 0 && (bytes[15] == 0 || bytes[15] == 1))
/*      */     {
/*  597 */       return false;
/*      */     }
/*      */     
/*  600 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet4Address getCompatIPv4Address(Inet6Address ip) {
/*  613 */     Preconditions.checkArgument(isCompatIPv4Address(ip), "Address '%s' is not IPv4-compatible.", new Object[] { toAddrString(ip) });
/*      */ 
/*      */     
/*  616 */     return getInet4Address(copyOfRange(ip.getAddress(), 12, 16));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean is6to4Address(Inet6Address ip) {
/*  635 */     byte[] bytes = ip.getAddress();
/*  636 */     return (bytes[0] == 32 && bytes[1] == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet4Address get6to4IPv4Address(Inet6Address ip) {
/*  649 */     Preconditions.checkArgument(is6to4Address(ip), "Address '%s' is not a 6to4 address.", new Object[] { toAddrString(ip) });
/*      */ 
/*      */     
/*  652 */     return getInet4Address(copyOfRange(ip.getAddress(), 2, 6));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static final class TeredoInfo
/*      */   {
/*      */     private final Inet4Address server;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Inet4Address client;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int port;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int flags;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TeredoInfo(@Nullable Inet4Address server, @Nullable Inet4Address client, int port, int flags) {
/*  692 */       Preconditions.checkArgument((port >= 0 && port <= 65535), "port '%s' is out of range (0 <= port <= 0xffff)", new Object[] { Integer.valueOf(port) });
/*      */       
/*  694 */       Preconditions.checkArgument((flags >= 0 && flags <= 65535), "flags '%s' is out of range (0 <= flags <= 0xffff)", new Object[] { Integer.valueOf(flags) });
/*      */ 
/*      */       
/*  697 */       if (server != null) {
/*  698 */         this.server = server;
/*      */       } else {
/*  700 */         this.server = InetAddresses.ANY4;
/*      */       } 
/*      */       
/*  703 */       if (client != null) {
/*  704 */         this.client = client;
/*      */       } else {
/*  706 */         this.client = InetAddresses.ANY4;
/*      */       } 
/*      */       
/*  709 */       this.port = port;
/*  710 */       this.flags = flags;
/*      */     }
/*      */     
/*      */     public Inet4Address getServer() {
/*  714 */       return this.server;
/*      */     }
/*      */     
/*      */     public Inet4Address getClient() {
/*  718 */       return this.client;
/*      */     }
/*      */     
/*      */     public int getPort() {
/*  722 */       return this.port;
/*      */     }
/*      */     
/*      */     public int getFlags() {
/*  726 */       return this.flags;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTeredoAddress(Inet6Address ip) {
/*  740 */     byte[] bytes = ip.getAddress();
/*  741 */     return (bytes[0] == 32 && bytes[1] == 1 && bytes[2] == 0 && bytes[3] == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TeredoInfo getTeredoInfo(Inet6Address ip) {
/*  755 */     Preconditions.checkArgument(isTeredoAddress(ip), "Address '%s' is not a Teredo address.", new Object[] { toAddrString(ip) });
/*      */ 
/*      */     
/*  758 */     byte[] bytes = ip.getAddress();
/*  759 */     Inet4Address server = getInet4Address(copyOfRange(bytes, 4, 8));
/*      */     
/*  761 */     int flags = ByteStreams.newDataInput(bytes, 8).readShort() & 0xFFFF;
/*      */ 
/*      */     
/*  764 */     int port = (ByteStreams.newDataInput(bytes, 10).readShort() ^ 0xFFFFFFFF) & 0xFFFF;
/*      */     
/*  766 */     byte[] clientBytes = copyOfRange(bytes, 12, 16);
/*  767 */     for (int i = 0; i < clientBytes.length; i++)
/*      */     {
/*  769 */       clientBytes[i] = (byte)(clientBytes[i] ^ 0xFFFFFFFF);
/*      */     }
/*  771 */     Inet4Address client = getInet4Address(clientBytes);
/*      */     
/*  773 */     return new TeredoInfo(server, client, port, flags);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isIsatapAddress(Inet6Address ip) {
/*  796 */     if (isTeredoAddress(ip)) {
/*  797 */       return false;
/*      */     }
/*      */     
/*  800 */     byte[] bytes = ip.getAddress();
/*      */     
/*  802 */     if ((bytes[8] | 0x3) != 3)
/*      */     {
/*      */ 
/*      */       
/*  806 */       return false;
/*      */     }
/*      */     
/*  809 */     return (bytes[9] == 0 && bytes[10] == 94 && bytes[11] == -2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet4Address getIsatapIPv4Address(Inet6Address ip) {
/*  823 */     Preconditions.checkArgument(isIsatapAddress(ip), "Address '%s' is not an ISATAP address.", new Object[] { toAddrString(ip) });
/*      */ 
/*      */     
/*  826 */     return getInet4Address(copyOfRange(ip.getAddress(), 12, 16));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address ip) {
/*  843 */     return (isCompatIPv4Address(ip) || is6to4Address(ip) || isTeredoAddress(ip));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address ip) {
/*  863 */     if (isCompatIPv4Address(ip)) {
/*  864 */       return getCompatIPv4Address(ip);
/*      */     }
/*      */     
/*  867 */     if (is6to4Address(ip)) {
/*  868 */       return get6to4IPv4Address(ip);
/*      */     }
/*      */     
/*  871 */     if (isTeredoAddress(ip)) {
/*  872 */       return getTeredoInfo(ip).getClient();
/*      */     }
/*      */     
/*  875 */     throw new IllegalArgumentException(String.format("'%s' has no embedded IPv4 address.", new Object[] { toAddrString(ip) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMappedIPv4Address(String ipString) {
/*  904 */     byte[] bytes = ipStringToBytes(ipString);
/*  905 */     if (bytes != null && bytes.length == 16) {
/*  906 */       int i; for (i = 0; i < 10; i++) {
/*  907 */         if (bytes[i] != 0) {
/*  908 */           return false;
/*      */         }
/*      */       } 
/*  911 */       for (i = 10; i < 12; i++) {
/*  912 */         if (bytes[i] != -1) {
/*  913 */           return false;
/*      */         }
/*      */       } 
/*  916 */       return true;
/*      */     } 
/*  918 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet4Address getCoercedIPv4Address(InetAddress ip) {
/*  942 */     if (ip instanceof Inet4Address) {
/*  943 */       return (Inet4Address)ip;
/*      */     }
/*      */ 
/*      */     
/*  947 */     byte[] bytes = ip.getAddress();
/*  948 */     boolean leadingBytesOfZero = true;
/*  949 */     for (int i = 0; i < 15; i++) {
/*  950 */       if (bytes[i] != 0) {
/*  951 */         leadingBytesOfZero = false;
/*      */         break;
/*      */       } 
/*      */     } 
/*  955 */     if (leadingBytesOfZero && bytes[15] == 1)
/*  956 */       return LOOPBACK4; 
/*  957 */     if (leadingBytesOfZero && bytes[15] == 0) {
/*  958 */       return ANY4;
/*      */     }
/*      */     
/*  961 */     Inet6Address ip6 = (Inet6Address)ip;
/*  962 */     long addressAsLong = 0L;
/*  963 */     if (hasEmbeddedIPv4ClientAddress(ip6)) {
/*  964 */       addressAsLong = getEmbeddedIPv4ClientAddress(ip6).hashCode();
/*      */     }
/*      */     else {
/*      */       
/*  968 */       addressAsLong = ByteBuffer.wrap(ip6.getAddress(), 0, 8).getLong();
/*      */     } 
/*      */ 
/*      */     
/*  972 */     int coercedHash = hash64To32(addressAsLong);
/*      */ 
/*      */     
/*  975 */     coercedHash |= 0xE0000000;
/*      */ 
/*      */ 
/*      */     
/*  979 */     if (coercedHash == -1) {
/*  980 */       coercedHash = -2;
/*      */     }
/*      */     
/*  983 */     return getInet4Address(Ints.toByteArray(coercedHash));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @VisibleForTesting
/*      */   static int hash64To32(long key) {
/*  998 */     key = (key ^ 0xFFFFFFFFFFFFFFFFL) + (key << 18L);
/*  999 */     key ^= key >>> 31L;
/* 1000 */     key *= 21L;
/* 1001 */     key ^= key >>> 11L;
/* 1002 */     key += key << 6L;
/* 1003 */     key ^= key >>> 22L;
/* 1004 */     return (int)key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int coerceToInteger(InetAddress ip) {
/* 1029 */     return ByteStreams.newDataInput(getCoercedIPv4Address(ip).getAddress()).readInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Inet4Address fromInteger(int address) {
/* 1040 */     return getInet4Address(Ints.toByteArray(address));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InetAddress fromLittleEndianByteArray(byte[] addr) throws UnknownHostException {
/* 1056 */     byte[] reversed = new byte[addr.length];
/* 1057 */     for (int i = 0; i < addr.length; i++) {
/* 1058 */       reversed[i] = addr[addr.length - i - 1];
/*      */     }
/* 1060 */     return InetAddress.getByAddress(reversed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static InetAddress increment(InetAddress address) {
/* 1074 */     byte[] addr = address.getAddress();
/* 1075 */     int i = addr.length - 1;
/* 1076 */     while (i >= 0 && addr[i] == -1) {
/* 1077 */       addr[i] = 0;
/* 1078 */       i--;
/*      */     } 
/*      */     
/* 1081 */     Preconditions.checkArgument((i >= 0), "Incrementing " + address + " would wrap.");
/*      */ 
/*      */     
/* 1084 */     addr[i] = (byte)(addr[i] + 1);
/*      */     try {
/* 1086 */       return InetAddress.getByAddress(addr);
/* 1087 */     } catch (UnknownHostException e) {
/* 1088 */       throw new AssertionError(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMaximum(InetAddress address) {
/* 1101 */     byte[] addr = address.getAddress();
/* 1102 */     for (int i = 0; i < addr.length; i++) {
/* 1103 */       if (addr[i] != -1) {
/* 1104 */         return false;
/*      */       }
/*      */     } 
/* 1107 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] copyOfRange(byte[] original, int from, int to) {
/* 1116 */     Preconditions.checkNotNull(original);
/*      */     
/* 1118 */     int end = Math.min(to, original.length);
/* 1119 */     byte[] result = new byte[to - from];
/*      */     
/* 1121 */     System.arraycopy(original, from, result, 0, end - from);
/* 1122 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\net\InetAddresses.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */