/*     */ package net.minecraft.util.com.google.common.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Strings;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ @Immutable
/*     */ @GwtCompatible
/*     */ public final class HostAndPort
/*     */   implements Serializable
/*     */ {
/*     */   private static final int NO_PORT = -1;
/*     */   private final String host;
/*     */   private final int port;
/*     */   private final boolean hasBracketlessColons;
/*     */   private static final long serialVersionUID = 0L;
/*     */   
/*     */   private HostAndPort(String host, int port, boolean hasBracketlessColons) {
/*  81 */     this.host = host;
/*  82 */     this.port = port;
/*  83 */     this.hasBracketlessColons = hasBracketlessColons;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostText() {
/*  94 */     return this.host;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPort() {
/*  99 */     return (this.port >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 110 */     Preconditions.checkState(hasPort());
/* 111 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPortOrDefault(int defaultPort) {
/* 118 */     return hasPort() ? this.port : defaultPort;
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
/*     */   public static HostAndPort fromParts(String host, int port) {
/* 134 */     Preconditions.checkArgument(isValidPort(port), "Port out of range: %s", new Object[] { Integer.valueOf(port) });
/* 135 */     HostAndPort parsedHost = fromString(host);
/* 136 */     Preconditions.checkArgument(!parsedHost.hasPort(), "Host has a port: %s", new Object[] { host });
/* 137 */     return new HostAndPort(parsedHost.host, port, parsedHost.hasBracketlessColons);
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
/*     */   public static HostAndPort fromString(String hostPortString) {
/*     */     String host;
/* 151 */     Preconditions.checkNotNull(hostPortString);
/*     */     
/* 153 */     String portString = null;
/* 154 */     boolean hasBracketlessColons = false;
/*     */     
/* 156 */     if (hostPortString.startsWith("[")) {
/* 157 */       String[] hostAndPort = getHostAndPortFromBracketedHost(hostPortString);
/* 158 */       host = hostAndPort[0];
/* 159 */       portString = hostAndPort[1];
/*     */     } else {
/* 161 */       int colonPos = hostPortString.indexOf(':');
/* 162 */       if (colonPos >= 0 && hostPortString.indexOf(':', colonPos + 1) == -1) {
/*     */         
/* 164 */         host = hostPortString.substring(0, colonPos);
/* 165 */         portString = hostPortString.substring(colonPos + 1);
/*     */       } else {
/*     */         
/* 168 */         host = hostPortString;
/* 169 */         hasBracketlessColons = (colonPos >= 0);
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     int port = -1;
/* 174 */     if (!Strings.isNullOrEmpty(portString)) {
/*     */ 
/*     */       
/* 177 */       Preconditions.checkArgument(!portString.startsWith("+"), "Unparseable port number: %s", new Object[] { hostPortString });
/*     */       try {
/* 179 */         port = Integer.parseInt(portString);
/* 180 */       } catch (NumberFormatException e) {
/* 181 */         throw new IllegalArgumentException("Unparseable port number: " + hostPortString);
/*     */       } 
/* 183 */       Preconditions.checkArgument(isValidPort(port), "Port number out of range: %s", new Object[] { hostPortString });
/*     */     } 
/*     */     
/* 186 */     return new HostAndPort(host, port, hasBracketlessColons);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getHostAndPortFromBracketedHost(String hostPortString) {
/* 197 */     int colonIndex = 0;
/* 198 */     int closeBracketIndex = 0;
/* 199 */     boolean hasPort = false;
/* 200 */     Preconditions.checkArgument((hostPortString.charAt(0) == '['), "Bracketed host-port string must start with a bracket: %s", new Object[] { hostPortString });
/*     */     
/* 202 */     colonIndex = hostPortString.indexOf(':');
/* 203 */     closeBracketIndex = hostPortString.lastIndexOf(']');
/* 204 */     Preconditions.checkArgument((colonIndex > -1 && closeBracketIndex > colonIndex), "Invalid bracketed host/port: %s", new Object[] { hostPortString });
/*     */ 
/*     */     
/* 207 */     String host = hostPortString.substring(1, closeBracketIndex);
/* 208 */     if (closeBracketIndex + 1 == hostPortString.length()) {
/* 209 */       return new String[] { host, "" };
/*     */     }
/* 211 */     Preconditions.checkArgument((hostPortString.charAt(closeBracketIndex + 1) == ':'), "Only a colon may follow a close bracket: %s", new Object[] { hostPortString });
/*     */     
/* 213 */     for (int i = closeBracketIndex + 2; i < hostPortString.length(); i++) {
/* 214 */       Preconditions.checkArgument(Character.isDigit(hostPortString.charAt(i)), "Port must be numeric: %s", new Object[] { hostPortString });
/*     */     } 
/*     */     
/* 217 */     return new String[] { host, hostPortString.substring(closeBracketIndex + 2) };
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
/*     */   public HostAndPort withDefaultPort(int defaultPort) {
/* 232 */     Preconditions.checkArgument(isValidPort(defaultPort));
/* 233 */     if (hasPort() || this.port == defaultPort) {
/* 234 */       return this;
/*     */     }
/* 236 */     return new HostAndPort(this.host, defaultPort, this.hasBracketlessColons);
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
/*     */   public HostAndPort requireBracketsForIPv6() {
/* 255 */     Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", new Object[] { this.host });
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object other) {
/* 261 */     if (this == other) {
/* 262 */       return true;
/*     */     }
/* 264 */     if (other instanceof HostAndPort) {
/* 265 */       HostAndPort that = (HostAndPort)other;
/* 266 */       return (Objects.equal(this.host, that.host) && this.port == that.port && this.hasBracketlessColons == that.hasBracketlessColons);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 275 */     return Objects.hashCode(new Object[] { this.host, Integer.valueOf(this.port), Boolean.valueOf(this.hasBracketlessColons) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 281 */     StringBuilder builder = new StringBuilder(this.host.length() + 7);
/* 282 */     if (this.host.indexOf(':') >= 0) {
/* 283 */       builder.append('[').append(this.host).append(']');
/*     */     } else {
/* 285 */       builder.append(this.host);
/*     */     } 
/* 287 */     if (hasPort()) {
/* 288 */       builder.append(':').append(this.port);
/*     */     }
/* 290 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidPort(int port) {
/* 295 */     return (port >= 0 && port <= 65535);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\net\HostAndPort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */