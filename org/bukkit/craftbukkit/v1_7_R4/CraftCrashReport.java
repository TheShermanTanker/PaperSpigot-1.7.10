/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ 
/*    */ 
/*    */ public class CraftCrashReport
/*    */   implements Callable<Object>
/*    */ {
/*    */   public Object call() throws Exception {
/* 18 */     StringWriter value = new StringWriter();
/*    */     try {
/* 20 */       value.append("\n   Running: ").append(Bukkit.getName()).append(" version ").append(Bukkit.getVersion()).append(" (Implementing API version ").append(Bukkit.getBukkitVersion()).append(") ").append(String.valueOf(MinecraftServer.getServer().getOnlineMode()));
/* 21 */       value.append("\n   Plugins: {");
/* 22 */       for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
/* 23 */         PluginDescriptionFile description = plugin.getDescription();
/* 24 */         value.append(' ').append(description.getFullName()).append(' ').append(description.getMain()).append(' ').append(Arrays.toString(description.getAuthors().toArray())).append(',');
/*    */       } 
/* 26 */       value.append("}\n   Warnings: ").append(Bukkit.getWarningState().name());
/* 27 */       value.append("\n   Threads: {");
/* 28 */       for (Map.Entry<Thread, ? extends Object[]> entry : Thread.getAllStackTraces().entrySet()) {
/* 29 */         value.append(' ').append(((Thread)entry.getKey()).getState().name()).append(' ').append(((Thread)entry.getKey()).getName()).append(": ").append(Arrays.toString(entry.getValue())).append(',');
/*    */       }
/* 31 */       value.append("}\n   ").append(Bukkit.getScheduler().toString());
/* 32 */     } catch (Throwable t) {
/* 33 */       value.append("\n   Failed to handle CraftCrashReport:\n");
/* 34 */       PrintWriter writer = new PrintWriter(value);
/* 35 */       t.printStackTrace(writer);
/* 36 */       writer.flush();
/*    */     } 
/* 38 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\CraftCrashReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */