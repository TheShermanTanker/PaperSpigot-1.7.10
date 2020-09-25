/*    */ package net.minecraft.util.io.netty.channel.udt.nio;
/*    */ 
/*    */ import com.barchart.udt.TypeUDT;
/*    */ import com.barchart.udt.nio.SocketChannelUDT;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.channel.Channel;
/*    */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
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
/*    */ public class NioUdtByteAcceptorChannel
/*    */   extends NioUdtAcceptorChannel
/*    */ {
/* 29 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*    */   
/*    */   public NioUdtByteAcceptorChannel() {
/* 32 */     super(TypeUDT.STREAM);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 37 */     SocketChannelUDT channelUDT = javaChannel().accept();
/* 38 */     if (channelUDT == null) {
/* 39 */       return 0;
/*    */     }
/* 41 */     buf.add(new NioUdtByteConnectorChannel((Channel)this, channelUDT));
/* 42 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelMetadata metadata() {
/* 48 */     return METADATA;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channe\\udt\nio\NioUdtByteAcceptorChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */