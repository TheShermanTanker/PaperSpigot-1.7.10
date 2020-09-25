/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandHandler
/*     */   implements ICommandHandler
/*     */ {
/*  15 */   private static final Logger a = LogManager.getLogger();
/*  16 */   private final Map b = new HashMap<Object, Object>();
/*  17 */   private final Set c = new HashSet();
/*     */ 
/*     */   
/*     */   public int a(ICommandListener paramICommandListener, String paramString) {
/*  21 */     paramString = paramString.trim();
/*  22 */     if (paramString.startsWith("/")) paramString = paramString.substring(1);
/*     */     
/*  24 */     String[] arrayOfString = paramString.split(" ");
/*  25 */     String str = arrayOfString[0];
/*     */     
/*  27 */     arrayOfString = a(arrayOfString);
/*     */     
/*  29 */     ICommand iCommand = (ICommand)this.b.get(str);
/*  30 */     int i = a(iCommand, arrayOfString);
/*  31 */     byte b = 0;
/*     */     
/*     */     try {
/*  34 */       if (iCommand == null) {
/*  35 */         throw new ExceptionUnknownCommand();
/*     */       }
/*  37 */       if (iCommand.canUse(paramICommandListener)) {
/*  38 */         if (i > -1) {
/*     */           
/*  40 */           EntityPlayer[] arrayOfEntityPlayer = PlayerSelector.getPlayers(paramICommandListener, arrayOfString[i]);
/*  41 */           String str1 = arrayOfString[i];
/*     */           
/*  43 */           for (EntityPlayer entityPlayer : arrayOfEntityPlayer) {
/*  44 */             arrayOfString[i] = entityPlayer.getName();
/*     */             
/*     */             try {
/*  47 */               iCommand.execute(paramICommandListener, arrayOfString);
/*  48 */               b++;
/*  49 */             } catch (CommandException commandException) {
/*  50 */               ChatMessage chatMessage = new ChatMessage(commandException.getMessage(), commandException.getArgs());
/*  51 */               chatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  52 */               paramICommandListener.sendMessage(chatMessage);
/*     */             } 
/*     */           } 
/*     */           
/*  56 */           arrayOfString[i] = str1;
/*     */         } else {
/*     */           try {
/*  59 */             iCommand.execute(paramICommandListener, arrayOfString);
/*  60 */             b++;
/*  61 */           } catch (CommandException commandException) {
/*  62 */             ChatMessage chatMessage = new ChatMessage(commandException.getMessage(), commandException.getArgs());
/*  63 */             chatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  64 */             paramICommandListener.sendMessage(chatMessage);
/*     */           } 
/*     */         } 
/*     */       } else {
/*  68 */         ChatMessage chatMessage = new ChatMessage("commands.generic.permission", new Object[0]);
/*  69 */         chatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  70 */         paramICommandListener.sendMessage(chatMessage);
/*     */       }
/*     */     
/*  73 */     } catch (ExceptionUsage exceptionUsage) {
/*  74 */       ChatMessage chatMessage = new ChatMessage("commands.generic.usage", new Object[] { new ChatMessage(exceptionUsage.getMessage(), exceptionUsage.getArgs()) });
/*  75 */       chatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  76 */       paramICommandListener.sendMessage(chatMessage);
/*  77 */     } catch (CommandException commandException) {
/*  78 */       ChatMessage chatMessage = new ChatMessage(commandException.getMessage(), commandException.getArgs());
/*  79 */       chatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  80 */       paramICommandListener.sendMessage(chatMessage);
/*  81 */     } catch (Throwable throwable) {
/*  82 */       ChatMessage chatMessage = new ChatMessage("commands.generic.exception", new Object[0]);
/*  83 */       chatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  84 */       paramICommandListener.sendMessage(chatMessage);
/*  85 */       a.error("Couldn't process command: '" + paramString + "'", throwable);
/*     */     } 
/*     */     
/*  88 */     return b;
/*     */   }
/*     */   
/*     */   public ICommand a(ICommand paramICommand) {
/*  92 */     List list = paramICommand.b();
/*     */     
/*  94 */     this.b.put(paramICommand.getCommand(), paramICommand);
/*  95 */     this.c.add(paramICommand);
/*     */     
/*  97 */     if (list != null) {
/*  98 */       for (String str : list) {
/*  99 */         ICommand iCommand = (ICommand)this.b.get(str);
/*     */         
/* 101 */         if (iCommand == null || !iCommand.getCommand().equals(str)) {
/* 102 */           this.b.put(str, paramICommand);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 107 */     return paramICommand;
/*     */   }
/*     */   
/*     */   private static String[] a(String[] paramArrayOfString) {
/* 111 */     String[] arrayOfString = new String[paramArrayOfString.length - 1];
/*     */     
/* 113 */     for (byte b = 1; b < paramArrayOfString.length; b++) {
/* 114 */       arrayOfString[b - 1] = paramArrayOfString[b];
/*     */     }
/*     */     
/* 117 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   public List b(ICommandListener paramICommandListener, String paramString) {
/* 122 */     String[] arrayOfString = paramString.split(" ", -1);
/* 123 */     String str = arrayOfString[0];
/*     */     
/* 125 */     if (arrayOfString.length == 1) {
/*     */       
/* 127 */       ArrayList arrayList = new ArrayList();
/*     */       
/* 129 */       for (Map.Entry entry : this.b.entrySet()) {
/* 130 */         if (CommandAbstract.a(str, (String)entry.getKey()) && ((ICommand)entry.getValue()).canUse(paramICommandListener)) {
/* 131 */           arrayList.add(entry.getKey());
/*     */         }
/*     */       } 
/*     */       
/* 135 */       return arrayList;
/* 136 */     }  if (arrayOfString.length > 1) {
/*     */ 
/*     */       
/* 139 */       ICommand iCommand = (ICommand)this.b.get(str);
/*     */       
/* 141 */       if (iCommand != null) {
/* 142 */         return iCommand.tabComplete(paramICommandListener, a(arrayOfString));
/*     */       }
/*     */     } 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List a(ICommandListener paramICommandListener) {
/* 151 */     ArrayList<ICommand> arrayList = new ArrayList();
/*     */     
/* 153 */     for (ICommand iCommand : this.c) {
/* 154 */       if (iCommand.canUse(paramICommandListener)) {
/* 155 */         arrayList.add(iCommand);
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map a() {
/* 164 */     return this.b;
/*     */   }
/*     */   
/*     */   private int a(ICommand paramICommand, String[] paramArrayOfString) {
/* 168 */     if (paramICommand == null) {
/* 169 */       return -1;
/*     */     }
/*     */     
/* 172 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 173 */       if (paramICommand.isListStart(paramArrayOfString, b) && PlayerSelector.isList(paramArrayOfString[b])) {
/* 174 */         return b;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */