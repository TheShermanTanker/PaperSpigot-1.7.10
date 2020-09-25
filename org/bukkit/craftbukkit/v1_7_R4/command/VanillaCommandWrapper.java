/*     */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.ChatMessage;
/*     */ import net.minecraft.server.v1_7_R4.CommandAbstract;
/*     */ import net.minecraft.server.v1_7_R4.CommandBlockListenerAbstract;
/*     */ import net.minecraft.server.v1_7_R4.CommandException;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartCommandBlock;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartCommandBlockListener;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.EnumChatFormat;
/*     */ import net.minecraft.server.v1_7_R4.ExceptionUsage;
/*     */ import net.minecraft.server.v1_7_R4.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_7_R4.ICommandListener;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.server.v1_7_R4.PlayerSelector;
/*     */ import net.minecraft.server.v1_7_R4.RemoteControlCommandListener;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityCommandListener;
/*     */ import net.minecraft.server.v1_7_R4.WorldServer;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.defaults.VanillaCommand;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftMinecartCommand;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VanillaCommandWrapper
/*     */   extends VanillaCommand
/*     */ {
/*     */   protected final CommandAbstract vanillaCommand;
/*     */   
/*     */   public VanillaCommandWrapper(CommandAbstract vanillaCommand) {
/*  38 */     super(vanillaCommand.getCommand());
/*  39 */     this.vanillaCommand = vanillaCommand;
/*     */   }
/*     */   
/*     */   public VanillaCommandWrapper(CommandAbstract vanillaCommand, String usage) {
/*  43 */     super(vanillaCommand.getCommand());
/*  44 */     this.vanillaCommand = vanillaCommand;
/*  45 */     this.description = "A Mojang provided command.";
/*  46 */     this.usageMessage = usage;
/*  47 */     setPermission("minecraft.command." + vanillaCommand.getCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/*  52 */     if (!testPermission(sender)) return true;
/*     */     
/*  54 */     ICommandListener icommandlistener = getListener(sender);
/*     */ 
/*     */     
/*  57 */     WorldServer[] prev = (MinecraftServer.getServer()).worldServer;
/*  58 */     (MinecraftServer.getServer()).worldServer = new WorldServer[] { (WorldServer)icommandlistener.getWorld() };
/*     */     try {
/*  60 */       this.vanillaCommand.execute(icommandlistener, args);
/*  61 */     } catch (ExceptionUsage exceptionusage) {
/*  62 */       ChatMessage chatmessage = new ChatMessage("commands.generic.usage", new Object[] { new ChatMessage(exceptionusage.getMessage(), exceptionusage.getArgs()) });
/*  63 */       chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  64 */       icommandlistener.sendMessage((IChatBaseComponent)chatmessage);
/*  65 */     } catch (CommandException commandexception) {
/*  66 */       ChatMessage chatmessage = new ChatMessage(commandexception.getMessage(), commandexception.getArgs());
/*  67 */       chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  68 */       icommandlistener.sendMessage((IChatBaseComponent)chatmessage);
/*     */     } finally {
/*  70 */       (MinecraftServer.getServer()).worldServer = prev;
/*     */     } 
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/*  77 */     Validate.notNull(sender, "Sender cannot be null");
/*  78 */     Validate.notNull(args, "Arguments cannot be null");
/*  79 */     Validate.notNull(alias, "Alias cannot be null");
/*  80 */     return this.vanillaCommand.tabComplete(getListener(sender), args);
/*     */   }
/*     */ 
/*     */   
/*     */   public final int dispatchVanillaCommandBlock(CommandBlockListenerAbstract icommandlistener, String s) {
/*  85 */     s = s.trim();
/*  86 */     if (s.startsWith("/")) {
/*  87 */       s = s.substring(1);
/*     */     }
/*  89 */     String[] as = s.split(" ");
/*  90 */     as = dropFirstArgument(as);
/*  91 */     int i = getPlayerListSize(as);
/*  92 */     int j = 0;
/*     */ 
/*     */     
/*  95 */     WorldServer[] prev = (MinecraftServer.getServer()).worldServer;
/*  96 */     (MinecraftServer.getServer()).worldServer = new WorldServer[] { (WorldServer)icommandlistener.getWorld() };
/*     */     try {
/*  98 */       if (this.vanillaCommand.canUse((ICommandListener)icommandlistener)) {
/*  99 */         if (i > -1) {
/* 100 */           EntityPlayer[] aentityplayer = PlayerSelector.getPlayers((ICommandListener)icommandlistener, as[i]);
/* 101 */           String s2 = as[i];
/* 102 */           EntityPlayer[] aentityplayer1 = aentityplayer;
/* 103 */           int k = aentityplayer1.length;
/* 104 */           for (int l = 0; l < k; l++) {
/* 105 */             EntityPlayer entityplayer = aentityplayer1[l];
/* 106 */             as[i] = entityplayer.getName();
/*     */             try {
/* 108 */               this.vanillaCommand.execute((ICommandListener)icommandlistener, as);
/* 109 */               j++;
/*     */             }
/* 111 */             catch (CommandException commandexception1) {
/* 112 */               ChatMessage chatmessage4 = new ChatMessage(commandexception1.getMessage(), commandexception1.getArgs());
/* 113 */               chatmessage4.getChatModifier().setColor(EnumChatFormat.RED);
/* 114 */               icommandlistener.sendMessage((IChatBaseComponent)chatmessage4);
/*     */             } 
/*     */           } 
/*     */           
/* 118 */           as[i] = s2;
/*     */         } else {
/* 120 */           this.vanillaCommand.execute((ICommandListener)icommandlistener, as);
/* 121 */           j++;
/*     */         } 
/*     */       } else {
/* 124 */         ChatMessage chatmessage = new ChatMessage("commands.generic.permission", new Object[0]);
/* 125 */         chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
/* 126 */         icommandlistener.sendMessage((IChatBaseComponent)chatmessage);
/*     */       } 
/* 128 */     } catch (ExceptionUsage exceptionusage) {
/* 129 */       ChatMessage chatmessage1 = new ChatMessage("commands.generic.usage", new Object[] { new ChatMessage(exceptionusage.getMessage(), exceptionusage.getArgs()) });
/* 130 */       chatmessage1.getChatModifier().setColor(EnumChatFormat.RED);
/* 131 */       icommandlistener.sendMessage((IChatBaseComponent)chatmessage1);
/* 132 */     } catch (CommandException commandexception) {
/* 133 */       ChatMessage chatmessage2 = new ChatMessage(commandexception.getMessage(), commandexception.getArgs());
/* 134 */       chatmessage2.getChatModifier().setColor(EnumChatFormat.RED);
/* 135 */       icommandlistener.sendMessage((IChatBaseComponent)chatmessage2);
/* 136 */     } catch (Throwable throwable) {
/* 137 */       ChatMessage chatmessage3 = new ChatMessage("commands.generic.exception", new Object[0]);
/* 138 */       chatmessage3.getChatModifier().setColor(EnumChatFormat.RED);
/* 139 */       icommandlistener.sendMessage((IChatBaseComponent)chatmessage3);
/* 140 */       if (icommandlistener instanceof TileEntityCommandListener) {
/* 141 */         TileEntityCommandListener listener = (TileEntityCommandListener)icommandlistener;
/* 142 */         MinecraftServer.getLogger().log(Level.WARN, String.format("CommandBlock at (%d,%d,%d) failed to handle command", new Object[] { Integer.valueOf((listener.getChunkCoordinates()).x), Integer.valueOf((listener.getChunkCoordinates()).y), Integer.valueOf((listener.getChunkCoordinates()).z) }), throwable);
/* 143 */       } else if (icommandlistener instanceof EntityMinecartCommandBlockListener) {
/* 144 */         EntityMinecartCommandBlockListener listener = (EntityMinecartCommandBlockListener)icommandlistener;
/* 145 */         MinecraftServer.getLogger().log(Level.WARN, String.format("MinecartCommandBlock at (%d,%d,%d) failed to handle command", new Object[] { Integer.valueOf((listener.getChunkCoordinates()).x), Integer.valueOf((listener.getChunkCoordinates()).y), Integer.valueOf((listener.getChunkCoordinates()).z) }), throwable);
/*     */       } else {
/* 147 */         MinecraftServer.getLogger().log(Level.WARN, String.format("Unknown CommandBlock failed to handle command", new Object[0]), throwable);
/*     */       } 
/*     */     } finally {
/* 150 */       (MinecraftServer.getServer()).worldServer = prev;
/*     */     } 
/* 152 */     return j;
/*     */   }
/*     */   
/*     */   private ICommandListener getListener(CommandSender sender) {
/* 156 */     if (sender instanceof org.bukkit.entity.Player) {
/* 157 */       return (ICommandListener)((CraftPlayer)sender).getHandle();
/*     */     }
/* 159 */     if (sender instanceof org.bukkit.command.BlockCommandSender) {
/* 160 */       return ((CraftBlockCommandSender)sender).getTileEntity();
/*     */     }
/* 162 */     if (sender instanceof org.bukkit.entity.minecart.CommandMinecart) {
/* 163 */       return (ICommandListener)((EntityMinecartCommandBlock)((CraftMinecartCommand)sender).getHandle()).getCommandBlock();
/*     */     }
/* 165 */     if (sender instanceof org.bukkit.command.RemoteConsoleCommandSender) {
/* 166 */       return (ICommandListener)RemoteControlCommandListener.instance;
/*     */     }
/* 168 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/* 169 */       return (ICommandListener)((CraftServer)sender.getServer()).getServer();
/*     */     }
/* 171 */     return null;
/*     */   }
/*     */   
/*     */   private int getPlayerListSize(String[] as) {
/* 175 */     for (int i = 0; i < as.length; i++) {
/* 176 */       if (this.vanillaCommand.isListStart(as, i) && PlayerSelector.isList(as[i])) {
/* 177 */         return i;
/*     */       }
/*     */     } 
/* 180 */     return -1;
/*     */   }
/*     */   
/*     */   private String[] dropFirstArgument(String[] as) {
/* 184 */     String[] as1 = new String[as.length - 1];
/* 185 */     for (int i = 1; i < as.length; i++) {
/* 186 */       as1[i - 1] = as[i];
/*     */     }
/*     */     
/* 189 */     return as1;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\VanillaCommandWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */