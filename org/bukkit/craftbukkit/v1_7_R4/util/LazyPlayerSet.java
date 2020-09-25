/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*    */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class LazyPlayerSet
/*    */   extends LazyHashSet<Player>
/*    */ {
/*    */   HashSet<Player> makeReference() {
/* 14 */     if (this.reference != null) {
/* 15 */       throw new IllegalStateException("Reference already created!");
/*    */     }
/* 17 */     List<EntityPlayer> players = (MinecraftServer.getServer().getPlayerList()).players;
/* 18 */     HashSet<Player> reference = new HashSet<Player>(players.size());
/* 19 */     for (EntityPlayer player : players) {
/* 20 */       reference.add(player.getBukkitEntity());
/*    */     }
/* 22 */     return reference;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\LazyPlayerSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */