/*    */ package org.bukkit.plugin.messaging;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelNameTooLongException
/*    */   extends RuntimeException
/*    */ {
/*    */   public ChannelNameTooLongException() {
/*  9 */     super("Attempted to send a Plugin Message to a channel that was too large. The maximum length a channel may be is 16 chars.");
/*    */   }
/*    */   
/*    */   public ChannelNameTooLongException(String channel) {
/* 13 */     super("Attempted to send a Plugin Message to a channel that was too large. The maximum length a channel may be is 16 chars (attempted " + channel.length() + " - '" + channel + ".");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\ChannelNameTooLongException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */