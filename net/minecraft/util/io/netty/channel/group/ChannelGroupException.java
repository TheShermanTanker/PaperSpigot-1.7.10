/*    */ package net.minecraft.util.io.netty.channel.group;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.io.netty.channel.Channel;
/*    */ import net.minecraft.util.io.netty.channel.ChannelException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelGroupException
/*    */   extends ChannelException
/*    */   implements Iterable<Map.Entry<Channel, Throwable>>
/*    */ {
/*    */   private static final long serialVersionUID = -4093064295562629453L;
/*    */   private final Collection<Map.Entry<Channel, Throwable>> failed;
/*    */   
/*    */   public ChannelGroupException(Collection<Map.Entry<Channel, Throwable>> causes) {
/* 35 */     if (causes == null) {
/* 36 */       throw new NullPointerException("causes");
/*    */     }
/* 38 */     if (causes.isEmpty()) {
/* 39 */       throw new IllegalArgumentException("causes must be non empty");
/*    */     }
/* 41 */     this.failed = Collections.unmodifiableCollection(causes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<Map.Entry<Channel, Throwable>> iterator() {
/* 50 */     return this.failed.iterator();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\group\ChannelGroupException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */