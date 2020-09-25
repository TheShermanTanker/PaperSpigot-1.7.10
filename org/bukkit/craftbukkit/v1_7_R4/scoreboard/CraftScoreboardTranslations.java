/*    */ package org.bukkit.craftbukkit.v1_7_R4.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.ImmutableBiMap;
/*    */ import net.minecraft.server.v1_7_R4.Scoreboard;
/*    */ import org.bukkit.scoreboard.DisplaySlot;
/*    */ 
/*    */ 
/*    */ class CraftScoreboardTranslations
/*    */ {
/*    */   static final int MAX_DISPLAY_SLOT = 3;
/* 11 */   static ImmutableBiMap<DisplaySlot, String> SLOTS = ImmutableBiMap.of(DisplaySlot.BELOW_NAME, "belowName", DisplaySlot.PLAYER_LIST, "list", DisplaySlot.SIDEBAR, "sidebar");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static DisplaySlot toBukkitSlot(int i) {
/* 19 */     return (DisplaySlot)SLOTS.inverse().get(Scoreboard.getSlotName(i));
/*    */   }
/*    */   
/*    */   static int fromBukkitSlot(DisplaySlot slot) {
/* 23 */     return Scoreboard.getSlotForName((String)SLOTS.get(slot));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scoreboard\CraftScoreboardTranslations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */