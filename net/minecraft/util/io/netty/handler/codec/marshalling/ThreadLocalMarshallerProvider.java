/*    */ package net.minecraft.util.io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import org.jboss.marshalling.Marshaller;
/*    */ import org.jboss.marshalling.MarshallerFactory;
/*    */ import org.jboss.marshalling.MarshallingConfiguration;
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
/*    */ public class ThreadLocalMarshallerProvider
/*    */   implements MarshallerProvider
/*    */ {
/* 30 */   private final ThreadLocal<Marshaller> marshallers = new ThreadLocal<Marshaller>();
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
/*    */   public ThreadLocalMarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
/* 42 */     this.factory = factory;
/* 43 */     this.config = config;
/*    */   }
/*    */ 
/*    */   
/*    */   public Marshaller getMarshaller(ChannelHandlerContext ctx) throws Exception {
/* 48 */     Marshaller marshaller = this.marshallers.get();
/* 49 */     if (marshaller == null) {
/* 50 */       marshaller = this.factory.createMarshaller(this.config);
/* 51 */       this.marshallers.set(marshaller);
/*    */     } 
/* 53 */     return marshaller;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\marshalling\ThreadLocalMarshallerProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */