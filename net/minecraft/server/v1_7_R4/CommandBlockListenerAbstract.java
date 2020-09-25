/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.command.VanillaCommandWrapper;
/*     */ 
/*     */ public abstract class CommandBlockListenerAbstract implements ICommandListener {
/*  15 */   private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
/*     */   private int b;
/*     */   private boolean c = true;
/*  18 */   private IChatBaseComponent d = null;
/*  19 */   public String e = "";
/*  20 */   private String f = "@";
/*     */   
/*     */   protected CommandSender sender;
/*     */ 
/*     */   
/*     */   public int g() {
/*  26 */     return this.b;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent h() {
/*  30 */     return this.d;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  34 */     nbttagcompound.setString("Command", this.e);
/*  35 */     nbttagcompound.setInt("SuccessCount", this.b);
/*  36 */     nbttagcompound.setString("CustomName", this.f);
/*  37 */     if (this.d != null) {
/*  38 */       nbttagcompound.setString("LastOutput", ChatSerializer.a(this.d));
/*     */     }
/*     */     
/*  41 */     nbttagcompound.setBoolean("TrackOutput", this.c);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  45 */     this.e = nbttagcompound.getString("Command");
/*  46 */     this.b = nbttagcompound.getInt("SuccessCount");
/*  47 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/*  48 */       this.f = nbttagcompound.getString("CustomName");
/*     */     }
/*     */     
/*  51 */     if (nbttagcompound.hasKeyOfType("LastOutput", 8)) {
/*  52 */       this.d = ChatSerializer.a(nbttagcompound.getString("LastOutput"));
/*     */     }
/*     */     
/*  55 */     if (nbttagcompound.hasKeyOfType("TrackOutput", 1)) {
/*  56 */       this.c = nbttagcompound.getBoolean("TrackOutput");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(int i, String s) {
/*  61 */     return (i <= 2);
/*     */   }
/*     */   
/*     */   public void setCommand(String s) {
/*  65 */     this.e = s;
/*     */   }
/*     */   
/*     */   public String getCommand() {
/*  69 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(World world) {
/*  73 */     if (world.isStatic) {
/*  74 */       this.b = 0;
/*     */     }
/*     */     
/*  77 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  79 */     if (minecraftserver != null && minecraftserver.getEnableCommandBlock()) {
/*     */       
/*  81 */       SimpleCommandMap commandMap = minecraftserver.server.getCommandMap();
/*  82 */       Joiner joiner = Joiner.on(" ");
/*  83 */       String command = this.e;
/*  84 */       if (this.e.startsWith("/")) {
/*  85 */         command = this.e.substring(1);
/*     */       }
/*  87 */       String[] args = command.split(" ");
/*  88 */       ArrayList<String[]> commands = (ArrayList)new ArrayList<String>();
/*     */ 
/*     */       
/*  91 */       if (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("op") || args[0].equalsIgnoreCase("deop") || args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("ban-ip") || args[0].equalsIgnoreCase("pardon") || args[0].equalsIgnoreCase("pardon-ip") || args[0].equalsIgnoreCase("reload")) {
/*     */ 
/*     */         
/*  94 */         this.b = 0;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  99 */       if ((getWorld()).players.isEmpty()) {
/* 100 */         this.b = 0;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 105 */       if (minecraftserver.server.getCommandBlockOverride(args[0])) {
/* 106 */         Command command1 = commandMap.getCommand("minecraft:" + args[0]);
/* 107 */         if (command1 instanceof VanillaCommandWrapper) {
/* 108 */           this.b = ((VanillaCommandWrapper)command1).dispatchVanillaCommandBlock(this, this.e);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 114 */       Command commandBlockCommand = commandMap.getCommand(args[0]);
/* 115 */       if (commandBlockCommand instanceof VanillaCommandWrapper) {
/* 116 */         this.b = ((VanillaCommandWrapper)commandBlockCommand).dispatchVanillaCommandBlock(this, this.e);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 122 */       if (commandMap.getCommand(args[0]) == null) {
/* 123 */         this.b = 0;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 128 */       if (args[0].equalsIgnoreCase("testfor")) {
/* 129 */         if (args.length < 2) {
/* 130 */           this.b = 0;
/*     */           
/*     */           return;
/*     */         } 
/* 134 */         EntityPlayer[] players = PlayerSelector.getPlayers(this, args[1]);
/*     */         
/* 136 */         if (players != null && players.length > 0) {
/* 137 */           this.b = players.length;
/*     */           return;
/*     */         } 
/* 140 */         EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(args[1]);
/* 141 */         if (player == null) {
/* 142 */           this.b = 0;
/*     */           return;
/*     */         } 
/* 145 */         this.b = 1;
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 151 */       commands.add(args);
/*     */ 
/*     */       
/* 154 */       ArrayList<String[]> newCommands = (ArrayList)new ArrayList<String>();
/* 155 */       for (int i = 0; i < args.length; i++) {
/* 156 */         if (PlayerSelector.isPattern(args[i])) {
/* 157 */           for (int k = 0; k < commands.size(); k++) {
/* 158 */             newCommands.addAll(buildCommands(commands.get(k), i));
/*     */           }
/* 160 */           ArrayList<String[]> temp = commands;
/* 161 */           commands = newCommands;
/* 162 */           newCommands = temp;
/* 163 */           newCommands.clear();
/*     */         } 
/*     */       } 
/*     */       
/* 167 */       int completed = 0;
/*     */ 
/*     */       
/* 170 */       for (int j = 0; j < commands.size(); j++) {
/*     */         try {
/* 172 */           if (commandMap.dispatch(this.sender, joiner.join(Arrays.asList((Object[])commands.get(j))))) {
/* 173 */             completed++;
/*     */           }
/* 175 */         } catch (Throwable exception) {
/* 176 */           if (this instanceof TileEntityCommandListener) {
/* 177 */             TileEntityCommandListener listener = (TileEntityCommandListener)this;
/* 178 */             MinecraftServer.getLogger().log(Level.WARN, String.format("CommandBlock at (%d,%d,%d) failed to handle command", new Object[] { Integer.valueOf((listener.getChunkCoordinates()).x), Integer.valueOf((listener.getChunkCoordinates()).y), Integer.valueOf((listener.getChunkCoordinates()).z) }), exception);
/* 179 */           } else if (this instanceof EntityMinecartCommandBlockListener) {
/* 180 */             EntityMinecartCommandBlockListener listener = (EntityMinecartCommandBlockListener)this;
/* 181 */             MinecraftServer.getLogger().log(Level.WARN, String.format("MinecartCommandBlock at (%d,%d,%d) failed to handle command", new Object[] { Integer.valueOf((listener.getChunkCoordinates()).x), Integer.valueOf((listener.getChunkCoordinates()).y), Integer.valueOf((listener.getChunkCoordinates()).z) }), exception);
/*     */           } else {
/* 183 */             MinecraftServer.getLogger().log(Level.WARN, String.format("Unknown CommandBlock failed to handle command", new Object[0]), exception);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 188 */       this.b = completed;
/*     */     } else {
/*     */       
/* 191 */       this.b = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ArrayList<String[]> buildCommands(String[] args, int pos) {
/* 197 */     ArrayList<String[]> commands = (ArrayList)new ArrayList<String>();
/* 198 */     EntityPlayer[] players = PlayerSelector.getPlayers(this, args[pos]);
/* 199 */     if (players != null) {
/* 200 */       for (EntityPlayer player : players) {
/* 201 */         if (player.world == getWorld()) {
/*     */ 
/*     */           
/* 204 */           String[] command = (String[])args.clone();
/* 205 */           command[pos] = player.getName();
/* 206 */           commands.add(command);
/*     */         } 
/*     */       } 
/*     */     }
/* 210 */     return commands;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 215 */     return this.f;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/* 219 */     return new ChatComponentText(getName());
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/* 223 */     this.f = s;
/*     */   }
/*     */   
/*     */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/* 227 */     if (this.c && getWorld() != null && !(getWorld()).isStatic) {
/* 228 */       this.d = (new ChatComponentText("[" + a.format(new Date()) + "] ")).addSibling(ichatbasecomponent);
/* 229 */       e();
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void e();
/*     */   
/*     */   public void b(IChatBaseComponent ichatbasecomponent) {
/* 236 */     this.d = ichatbasecomponent;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandBlockListenerAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */