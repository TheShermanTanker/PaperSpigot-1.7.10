/*     */ package com.google.common.net;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class HostAndPort
/*     */ {
/*     */   private static final int NO_PORT = -1;
/*     */   private final String host;
/*     */   private final int port;
/*     */   private final boolean hasBracketlessColons;
/*     */   
/*     */   private HostAndPort(String host, int port, boolean hasBracketlessColons) {
/*  72 */     this.host = host;
/*  73 */     this.port = port;
/*  74 */     this.hasBracketlessColons = hasBracketlessColons;
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
/*  85 */     return this.host;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPort() {
/*  90 */     return (this.port >= 0);
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
/* 101 */     Preconditions.checkState(hasPort());
/* 102 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPortOrDefault(int defaultPort) {
/* 109 */     return hasPort() ? this.port : defaultPort;
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
/* 125 */     Preconditions.checkArgument(isValidPort(port));
/* 126 */     HostAndPort parsedHost = fromString(host);
/* 127 */     Preconditions.checkArgument(!parsedHost.hasPort());
/* 128 */     return new HostAndPort(parsedHost.host, port, parsedHost.hasBracketlessColons);
/*     */   }
/*     */   
/* 131 */   private static final Pattern BRACKET_PATTERN = Pattern.compile("^\\[(.*:.*)\\](?::(\\d*))?$");
/*     */ 
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
/* 144 */     Preconditions.checkNotNull(hostPortString);
/*     */     
/* 146 */     String portString = null;
/* 147 */     boolean hasBracketlessColons = false;
/*     */     
/* 149 */     if (hostPortString.startsWith("[")) {
/*     */       
/* 151 */       Matcher matcher = BRACKET_PATTERN.matcher(hostPortString);
/* 152 */       Preconditions.checkArgument(matcher.matches(), "Invalid bracketed host/port: %s", new Object[] { hostPortString });
/*     */       
/* 154 */       host = matcher.group(1);
/* 155 */       portString = matcher.group(2);
/*     */     } else {
/* 157 */       int colonPos = hostPortString.indexOf(':');
/* 158 */       if (colonPos >= 0 && hostPortString.indexOf(':', colonPos + 1) == -1) {
/*     */         
/* 160 */         host = hostPortString.substring(0, colonPos);
/* 161 */         portString = hostPortString.substring(colonPos + 1);
/*     */       } else {
/*     */         
/* 164 */         host = hostPortString;
/* 165 */         hasBracketlessColons = (colonPos >= 0);
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     int port = -1;
/* 170 */     if (portString != null) {
/*     */       
/*     */       try {
/* 173 */         port = Integer.parseInt(portString);
/* 174 */       } catch (NumberFormatException e) {
/* 175 */         throw new IllegalArgumentException("Unparseable port number: " + hostPortString);
/*     */       } 
/* 177 */       Preconditions.checkArgument(isValidPort(port), "Port number out of range: %s", new Object[] { hostPortString });
/*     */     } 
/*     */ 
/*     */     
/* 181 */     return new HostAndPort(host, port, hasBracketlessColons);
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
/*     */   public HostAndPort withDefaultPort(int defaultPort) {
/* 195 */     Preconditions.checkArgument(isValidPort(defaultPort));
/* 196 */     if (hasPort() || this.port == defaultPort) {
/* 197 */       return this;
/*     */     }
/* 199 */     return new HostAndPort(this.host, defaultPort, this.hasBracketlessColons);
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
/* 218 */     Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", new Object[] { this.host });
/*     */     
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 225 */     if (this == other) {
/* 226 */       return true;
/*     */     }
/* 228 */     if (other instanceof HostAndPort) {
/* 229 */       HostAndPort that = (HostAndPort)other;
/* 230 */       return (Objects.equal(this.host, that.host) && this.port == that.port && this.hasBracketlessColons == that.hasBracketlessColons);
/*     */     } 
/*     */ 
/*     */     
/* 234 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 239 */     return Objects.hashCode(new Object[] { this.host, Integer.valueOf(this.port), Boolean.valueOf(this.hasBracketlessColons) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 245 */     StringBuilder builder = new StringBuilder(this.host.length() + 7);
/* 246 */     if (this.host.indexOf(':') >= 0) {
/* 247 */       builder.append('[').append(this.host).append(']');
/*     */     } else {
/* 249 */       builder.append(this.host);
/*     */     } 
/* 251 */     if (hasPort()) {
/* 252 */       builder.append(':').append(this.port);
/*     */     }
/* 254 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidPort(int port) {
/* 259 */     return (port >= 0 && port <= 65535);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\net\HostAndPort.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */