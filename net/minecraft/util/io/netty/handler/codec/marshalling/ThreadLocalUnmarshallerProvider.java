/*    */ package net.minecraft.util.io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
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
/*    */ public class ThreadLocalUnmarshallerProvider
/*    */   implements UnmarshallerProvider
/*    */ {
/* 30 */   private final ThreadLocal<Unmarshaller> unmarshallers = new ThreadLocal<Unmarshaller>();
/*    */ 
/*    */ 
/*    */   
/*    */   private final MarshallerFactory factory;
/*    */ 
/*    */   
/*    */   private final MarshallingConfiguration config;
/*    */ 
/*    */ 
/*    */   
/*    */   public ThreadLocalUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
/* 42 */     this.factory = factory;
/* 43 */     this.config = config;
/*    */   }
/*    */ 
/*    */   
/*    */   public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
/* 48 */     Unmarshaller unmarshaller = this.unmarshallers.get();
/* 49 */     if (unmarshaller == null) {
/* 50 */       unmarshaller = this.factory.createUnmarshaller(this.config);
/* 51 */       this.unmarshallers.set(unmarshaller);
/*    */     } 
/* 53 */     return unmarshaller;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\marshalling\ThreadLocalUnmarshallerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */