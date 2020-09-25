/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TerminalFactory
/*     */ {
/*     */   public static final String JLINE_TERMINAL = "org.bukkit.craftbukkit.libs.jline.terminal";
/*     */   public static final String AUTO = "auto";
/*     */   public static final String UNIX = "unix";
/*     */   public static final String WIN = "win";
/*     */   public static final String WINDOWS = "windows";
/*     */   public static final String NONE = "none";
/*     */   public static final String OFF = "off";
/*     */   public static final String FALSE = "false";
/*  41 */   private static final InheritableThreadLocal<Terminal> holder = new InheritableThreadLocal<Terminal>();
/*     */   public static synchronized Terminal create() {
/*     */     Terminal terminal;
/*  44 */     if (Log.TRACE)
/*     */     {
/*  46 */       Log.trace(new Object[] { new Throwable("CREATE MARKER") });
/*     */     }
/*     */     
/*  49 */     String type = Configuration.getString("org.bukkit.craftbukkit.libs.jline.terminal", "auto");
/*     */     
/*  51 */     Log.debug(new Object[] { "Creating terminal; type=", type });
/*     */ 
/*     */     
/*     */     try {
/*  55 */       String tmp = type.toLowerCase();
/*     */       
/*  57 */       if (tmp.equals("unix")) {
/*  58 */         terminal = getFlavor(Flavor.UNIX);
/*     */       }
/*  60 */       else if ((tmp.equals("win") | tmp.equals("windows")) != 0) {
/*  61 */         terminal = getFlavor(Flavor.WINDOWS);
/*     */       }
/*  63 */       else if (tmp.equals("none") || tmp.equals("off") || tmp.equals("false")) {
/*  64 */         terminal = new UnsupportedTerminal();
/*     */       
/*     */       }
/*  67 */       else if (tmp.equals("auto")) {
/*  68 */         String os = Configuration.getOsName();
/*  69 */         Flavor flavor = Flavor.UNIX;
/*  70 */         if (os.contains("windows")) {
/*  71 */           flavor = Flavor.WINDOWS;
/*     */         }
/*  73 */         terminal = getFlavor(flavor);
/*     */       } else {
/*     */         
/*     */         try {
/*  77 */           terminal = (Terminal)Thread.currentThread().getContextClassLoader().loadClass(type).newInstance();
/*     */         }
/*  79 */         catch (Exception e) {
/*  80 */           throw new IllegalArgumentException(MessageFormat.format("Invalid terminal type: {0}", new Object[] { type }), e);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*  85 */     } catch (Exception e) {
/*  86 */       Log.error(new Object[] { "Failed to construct terminal; falling back to unsupported", e });
/*  87 */       terminal = new UnsupportedTerminal();
/*     */     } 
/*     */     
/*  90 */     Log.debug(new Object[] { "Created Terminal: ", terminal });
/*     */     
/*     */     try {
/*  93 */       terminal.init();
/*     */     }
/*  95 */     catch (Exception e) {
/*  96 */       Log.error(new Object[] { "Terminal initialization failed; falling back to unsupported", e });
/*  97 */       return new UnsupportedTerminal();
/*     */     } 
/*     */     
/* 100 */     return terminal;
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/* 104 */     holder.remove();
/*     */   }
/*     */   
/*     */   public static synchronized void resetIf(Terminal t) {
/* 108 */     if (holder.get() == t) {
/* 109 */       reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 115 */     AUTO,
/* 116 */     WINDOWS,
/* 117 */     UNIX,
/* 118 */     NONE;
/*     */   }
/*     */   
/*     */   public static synchronized void configure(String type) {
/* 122 */     assert type != null;
/* 123 */     System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", type);
/*     */   }
/*     */   
/*     */   public static synchronized void configure(Type type) {
/* 127 */     assert type != null;
/* 128 */     configure(type.name().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Flavor
/*     */   {
/* 137 */     WINDOWS,
/* 138 */     UNIX;
/*     */   }
/*     */   
/* 141 */   private static final Map<Flavor, Class<? extends Terminal>> FLAVORS = new HashMap<Flavor, Class<? extends Terminal>>();
/*     */   
/*     */   static {
/* 144 */     registerFlavor(Flavor.WINDOWS, (Class)AnsiWindowsTerminal.class);
/* 145 */     registerFlavor(Flavor.UNIX, (Class)UnixTerminal.class);
/*     */   }
/*     */   
/*     */   public static synchronized Terminal get() {
/* 149 */     Terminal t = holder.get();
/* 150 */     if (t == null) {
/* 151 */       t = create();
/* 152 */       holder.set(t);
/*     */     } 
/* 154 */     return t;
/*     */   }
/*     */   
/*     */   public static Terminal getFlavor(Flavor flavor) throws Exception {
/* 158 */     Class<? extends Terminal> type = FLAVORS.get(flavor);
/* 159 */     if (type != null) {
/* 160 */       return type.newInstance();
/*     */     }
/*     */     
/* 163 */     throw new InternalError();
/*     */   }
/*     */   
/*     */   public static void registerFlavor(Flavor flavor, Class<? extends Terminal> type) {
/* 167 */     FLAVORS.put(flavor, type);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\TerminalFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */