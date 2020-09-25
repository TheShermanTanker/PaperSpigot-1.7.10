/*    */ package net.minecraft.util.io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.util.Attribute;
/*    */ import net.minecraft.util.io.netty.util.AttributeKey;
/*    */ import org.jboss.marshalling.MarshallerFactory;
/*    */ import org.jboss.marshalling.MarshallingConfiguration;
/*    */ import org.jboss.marshalling.Unmarshaller;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContextBoundUnmarshallerProvider
/*    */   extends DefaultUnmarshallerProvider
/*    */ {
/* 38 */   private static final AttributeKey<Unmarshaller> UNMARSHALLER = new AttributeKey(ContextBoundUnmarshallerProvider.class.getName() + ".unmarshaller");
/*    */ 
/*    */   
/*    */   public ContextBoundUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
/* 42 */     super(factory, config);
/*    */   }
/*    */ 
/*    */   
/*    */   public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
/* 47 */     Attribute<Unmarshaller> attr = ctx.attr(UNMARSHALLER);
/* 48 */     Unmarshaller unmarshaller = (Unmarshaller)attr.get();
/* 49 */     if (unmarshaller == null) {
/* 50 */       unmarshaller = super.getUnmarshaller(ctx);
/* 51 */       attr.set(unmarshaller);
/*    */     } 
/* 53 */     return unmarshaller;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\marshalling\ContextBoundUnmarshallerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */