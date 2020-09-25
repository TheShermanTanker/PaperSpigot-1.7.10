/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.com.google.common.primitives.Doubles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CommandAbstract
/*     */   implements ICommand
/*     */ {
/*     */   private static ICommandDispatcher a;
/*     */   
/*     */   public int a() {
/*  27 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List b() {
/*  32 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse(ICommandListener paramICommandListener) {
/*  37 */     return paramICommandListener.a(a(), getCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   public static int a(ICommandListener paramICommandListener, String paramString) {
/*     */     try {
/*  47 */       return Integer.parseInt(paramString);
/*  48 */     } catch (NumberFormatException numberFormatException) {
/*  49 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int a(ICommandListener paramICommandListener, String paramString, int paramInt) {
/*  54 */     return a(paramICommandListener, paramString, paramInt, 2147483647);
/*     */   }
/*     */   
/*     */   public static int a(ICommandListener paramICommandListener, String paramString, int paramInt1, int paramInt2) {
/*  58 */     int i = a(paramICommandListener, paramString);
/*     */     
/*  60 */     if (i < paramInt1)
/*  61 */       throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt1) }); 
/*  62 */     if (i > paramInt2) {
/*  63 */       throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt2) });
/*     */     }
/*     */     
/*  66 */     return i;
/*     */   }
/*     */   
/*     */   public static double b(ICommandListener paramICommandListener, String paramString) {
/*     */     try {
/*  71 */       double d = Double.parseDouble(paramString);
/*  72 */       if (!Doubles.isFinite(d)) throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString }); 
/*  73 */       return d;
/*  74 */     } catch (NumberFormatException numberFormatException) {
/*  75 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double a(ICommandListener paramICommandListener, String paramString, double paramDouble) {
/*  80 */     return a(paramICommandListener, paramString, paramDouble, Double.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public static double a(ICommandListener paramICommandListener, String paramString, double paramDouble1, double paramDouble2) {
/*  84 */     double d = b(paramICommandListener, paramString);
/*     */     
/*  86 */     if (d < paramDouble1)
/*  87 */       throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Double.valueOf(paramDouble1) }); 
/*  88 */     if (d > paramDouble2) {
/*  89 */       throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Double.valueOf(paramDouble2) });
/*     */     }
/*     */     
/*  92 */     return d;
/*     */   }
/*     */   
/*     */   public static boolean c(ICommandListener paramICommandListener, String paramString) {
/*  96 */     if (paramString.equals("true") || paramString.equals("1"))
/*  97 */       return true; 
/*  98 */     if (paramString.equals("false") || paramString.equals("0")) {
/*  99 */       return false;
/*     */     }
/* 101 */     throw new CommandException("commands.generic.boolean.invalid", new Object[] { paramString });
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayer b(ICommandListener paramICommandListener) {
/* 106 */     if (paramICommandListener instanceof EntityPlayer) {
/* 107 */       return (EntityPlayer)paramICommandListener;
/*     */     }
/* 109 */     throw new ExceptionPlayerNotFound("You must specify which player you wish to perform this action on.", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayer d(ICommandListener paramICommandListener, String paramString) {
/* 114 */     EntityPlayer entityPlayer = PlayerSelector.getPlayer(paramICommandListener, paramString);
/* 115 */     if (entityPlayer != null) return entityPlayer;
/*     */     
/* 117 */     entityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramString);
/*     */     
/* 119 */     if (entityPlayer == null) {
/* 120 */       throw new ExceptionPlayerNotFound();
/*     */     }
/* 122 */     return entityPlayer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String e(ICommandListener paramICommandListener, String paramString) {
/* 127 */     EntityPlayer entityPlayer = PlayerSelector.getPlayer(paramICommandListener, paramString);
/*     */     
/* 129 */     if (entityPlayer != null)
/* 130 */       return entityPlayer.getName(); 
/* 131 */     if (PlayerSelector.isPattern(paramString)) {
/* 132 */       throw new ExceptionPlayerNotFound();
/*     */     }
/*     */     
/* 135 */     return paramString;
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent a(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 139 */     return a(paramICommandListener, paramArrayOfString, paramInt, false);
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent a(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt, boolean paramBoolean) {
/* 143 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 145 */     for (int i = paramInt; i < paramArrayOfString.length; i++) {
/* 146 */       if (i > paramInt) chatComponentText.a(" "); 
/* 147 */       IChatBaseComponent iChatBaseComponent = new ChatComponentText(paramArrayOfString[i]);
/*     */       
/* 149 */       if (paramBoolean) {
/* 150 */         IChatBaseComponent iChatBaseComponent1 = PlayerSelector.getPlayerNames(paramICommandListener, paramArrayOfString[i]);
/*     */         
/* 152 */         if (iChatBaseComponent1 != null) {
/* 153 */           iChatBaseComponent = iChatBaseComponent1;
/* 154 */         } else if (PlayerSelector.isPattern(paramArrayOfString[i])) {
/* 155 */           throw new ExceptionPlayerNotFound();
/*     */         } 
/*     */       } 
/*     */       
/* 159 */       chatComponentText.addSibling(iChatBaseComponent);
/*     */     } 
/*     */     
/* 162 */     return chatComponentText;
/*     */   }
/*     */   
/*     */   public static String b(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) {
/* 166 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 168 */     for (int i = paramInt; i < paramArrayOfString.length; i++) {
/* 169 */       if (i > paramInt) stringBuilder.append(" "); 
/* 170 */       String str = paramArrayOfString[i];
/*     */       
/* 172 */       stringBuilder.append(str);
/*     */     } 
/*     */     
/* 175 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static double a(ICommandListener paramICommandListener, double paramDouble, String paramString) {
/* 179 */     return a(paramICommandListener, paramDouble, paramString, -30000000, 30000000);
/*     */   }
/*     */   
/*     */   public static double a(ICommandListener paramICommandListener, double paramDouble, String paramString, int paramInt1, int paramInt2) {
/* 183 */     boolean bool = paramString.startsWith("~");
/* 184 */     if (bool && Double.isNaN(paramDouble)) throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { Double.valueOf(paramDouble) }); 
/* 185 */     double d = bool ? paramDouble : 0.0D;
/*     */     
/* 187 */     if (!bool || paramString.length() > 1) {
/* 188 */       boolean bool1 = paramString.contains(".");
/* 189 */       if (bool) paramString = paramString.substring(1);
/*     */       
/* 191 */       d += b(paramICommandListener, paramString);
/*     */       
/* 193 */       if (!bool1 && !bool) {
/* 194 */         d += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 198 */     if (paramInt1 != 0 || paramInt2 != 0) {
/* 199 */       if (d < paramInt1)
/* 200 */         throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt1) }); 
/* 201 */       if (d > paramInt2) {
/* 202 */         throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt2) });
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return d;
/*     */   }
/*     */   
/*     */   public static Item f(ICommandListener paramICommandListener, String paramString) {
/* 210 */     Item item = (Item)Item.REGISTRY.get(paramString);
/*     */     
/* 212 */     if (item == null) {
/*     */       try {
/* 214 */         Item item1 = Item.getById(Integer.parseInt(paramString));
/*     */         
/* 216 */         if (item1 != null) {
/* 217 */           ChatMessage chatMessage = new ChatMessage("commands.generic.deprecatedId", new Object[] { Item.REGISTRY.c(item1) });
/* 218 */           chatMessage.getChatModifier().setColor(EnumChatFormat.GRAY);
/* 219 */           paramICommandListener.sendMessage(chatMessage);
/*     */         } 
/*     */         
/* 222 */         item = item1;
/* 223 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */     
/* 227 */     if (item == null) {
/* 228 */       throw new ExceptionInvalidNumber("commands.give.notFound", new Object[] { paramString });
/*     */     }
/*     */     
/* 231 */     return item;
/*     */   }
/*     */   
/*     */   public static Block g(ICommandListener paramICommandListener, String paramString) {
/* 235 */     if (Block.REGISTRY.b(paramString)) {
/* 236 */       return (Block)Block.REGISTRY.get(paramString);
/*     */     }
/*     */     
/*     */     try {
/* 240 */       int i = Integer.parseInt(paramString);
/*     */       
/* 242 */       if (Block.REGISTRY.b(i)) {
/* 243 */         Block block = Block.getById(i);
/*     */         
/* 245 */         ChatMessage chatMessage = new ChatMessage("commands.generic.deprecatedId", new Object[] { Block.REGISTRY.c(block) });
/* 246 */         chatMessage.getChatModifier().setColor(EnumChatFormat.GRAY);
/* 247 */         paramICommandListener.sendMessage(chatMessage);
/*     */         
/* 249 */         return block;
/*     */       } 
/* 251 */     } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */     
/* 254 */     throw new ExceptionInvalidNumber("commands.give.notFound", new Object[] { paramString });
/*     */   }
/*     */   
/*     */   public static String a(Object[] paramArrayOfObject) {
/* 258 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 260 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 261 */       String str = paramArrayOfObject[b].toString();
/*     */       
/* 263 */       if (b > 0) {
/* 264 */         if (b == paramArrayOfObject.length - 1) {
/* 265 */           stringBuilder.append(" and ");
/*     */         } else {
/* 267 */           stringBuilder.append(", ");
/*     */         } 
/*     */       }
/*     */       
/* 271 */       stringBuilder.append(str);
/*     */     } 
/*     */     
/* 274 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent a(IChatBaseComponent[] paramArrayOfIChatBaseComponent) {
/* 278 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 280 */     for (byte b = 0; b < paramArrayOfIChatBaseComponent.length; b++) {
/* 281 */       if (b > 0) {
/* 282 */         if (b == paramArrayOfIChatBaseComponent.length - 1) {
/* 283 */           chatComponentText.a(" and ");
/* 284 */         } else if (b > 0) {
/* 285 */           chatComponentText.a(", ");
/*     */         } 
/*     */       }
/*     */       
/* 289 */       chatComponentText.addSibling(paramArrayOfIChatBaseComponent[b]);
/*     */     } 
/*     */     
/* 292 */     return chatComponentText;
/*     */   }
/*     */   
/*     */   public static String a(Collection paramCollection) {
/* 296 */     return a(paramCollection.toArray((Object[])new String[paramCollection.size()]));
/*     */   }
/*     */   
/*     */   public static boolean a(String paramString1, String paramString2) {
/* 300 */     return paramString2.regionMatches(true, 0, paramString1, 0, paramString1.length());
/*     */   }
/*     */   
/*     */   public static List a(String[] paramArrayOfString1, String... paramVarArgs1) {
/* 304 */     String str = paramArrayOfString1[paramArrayOfString1.length - 1];
/* 305 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 307 */     for (String str1 : paramVarArgs1) {
/* 308 */       if (a(str, str1)) {
/* 309 */         arrayList.add(str1);
/*     */       }
/*     */     } 
/*     */     
/* 313 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static List a(String[] paramArrayOfString, Iterable paramIterable) {
/* 317 */     String str = paramArrayOfString[paramArrayOfString.length - 1];
/* 318 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 320 */     for (String str1 : paramIterable) {
/* 321 */       if (a(str, str1)) {
/* 322 */         arrayList.add(str1);
/*     */       }
/*     */     } 
/*     */     
/* 326 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 331 */     return false;
/*     */   }
/*     */   
/*     */   public static void a(ICommandListener paramICommandListener, ICommand paramICommand, String paramString, Object... paramVarArgs) {
/* 335 */     a(paramICommandListener, paramICommand, 0, paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public static void a(ICommandListener paramICommandListener, ICommand paramICommand, int paramInt, String paramString, Object... paramVarArgs) {
/* 339 */     if (a != null) {
/* 340 */       a.a(paramICommandListener, paramICommand, paramInt, paramString, paramVarArgs);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void a(ICommandDispatcher paramICommandDispatcher) {
/* 345 */     a = paramICommandDispatcher;
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(ICommand paramICommand) {
/* 350 */     return getCommand().compareTo(paramICommand.getCommand());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */