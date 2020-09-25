/*     */ package net.minecraft.util.io.netty.bootstrap;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInitializer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.EventLoopGroup;
/*     */ import net.minecraft.util.io.netty.channel.ServerChannel;
/*     */ import net.minecraft.util.io.netty.util.AttributeKey;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServerBootstrap
/*     */   extends AbstractBootstrap<ServerBootstrap, ServerChannel>
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
/*     */   
/*  46 */   private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap<ChannelOption<?>, Object>();
/*  47 */   private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap<AttributeKey<?>, Object>();
/*     */   
/*     */   private volatile EventLoopGroup childGroup;
/*     */   
/*     */   private volatile ChannelHandler childHandler;
/*     */   
/*     */   private ServerBootstrap(ServerBootstrap bootstrap) {
/*  54 */     super(bootstrap);
/*  55 */     this.childGroup = bootstrap.childGroup;
/*  56 */     this.childHandler = bootstrap.childHandler;
/*  57 */     synchronized (bootstrap.childOptions) {
/*  58 */       this.childOptions.putAll(bootstrap.childOptions);
/*     */     } 
/*  60 */     synchronized (bootstrap.childAttrs) {
/*  61 */       this.childAttrs.putAll(bootstrap.childAttrs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap group(EventLoopGroup group) {
/*  70 */     return group(group, group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
/*  79 */     super.group(parentGroup);
/*  80 */     if (childGroup == null) {
/*  81 */       throw new NullPointerException("childGroup");
/*     */     }
/*  83 */     if (this.childGroup != null) {
/*  84 */       throw new IllegalStateException("childGroup set already");
/*     */     }
/*  86 */     this.childGroup = childGroup;
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value) {
/*  96 */     if (childOption == null) {
/*  97 */       throw new NullPointerException("childOption");
/*     */     }
/*  99 */     if (value == null) {
/* 100 */       synchronized (this.childOptions) {
/* 101 */         this.childOptions.remove(childOption);
/*     */       } 
/*     */     } else {
/* 104 */       synchronized (this.childOptions) {
/* 105 */         this.childOptions.put(childOption, value);
/*     */       } 
/*     */     } 
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ServerBootstrap childAttr(AttributeKey<T> childKey, T value) {
/* 116 */     if (childKey == null) {
/* 117 */       throw new NullPointerException("childKey");
/*     */     }
/* 119 */     if (value == null) {
/* 120 */       this.childAttrs.remove(childKey);
/*     */     } else {
/* 122 */       this.childAttrs.put(childKey, value);
/*     */     } 
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap childHandler(ChannelHandler childHandler) {
/* 131 */     if (childHandler == null) {
/* 132 */       throw new NullPointerException("childHandler");
/*     */     }
/* 134 */     this.childHandler = childHandler;
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventLoopGroup childGroup() {
/* 143 */     return this.childGroup;
/*     */   }
/*     */   
/*     */   void init(Channel channel) throws Exception {
/*     */     final Map.Entry[] currentChildOptions, currentChildAttrs;
/* 148 */     Map<ChannelOption<?>, Object> options = options();
/* 149 */     synchronized (options) {
/* 150 */       channel.config().setOptions(options);
/*     */     } 
/*     */     
/* 153 */     Map<AttributeKey<?>, Object> attrs = attrs();
/* 154 */     synchronized (attrs) {
/* 155 */       for (Map.Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
/*     */         
/* 157 */         AttributeKey<Object> key = (AttributeKey<Object>)e.getKey();
/* 158 */         channel.attr(key).set(e.getValue());
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     ChannelPipeline p = channel.pipeline();
/* 163 */     if (handler() != null) {
/* 164 */       p.addLast(new ChannelHandler[] { handler() });
/*     */     }
/*     */     
/* 167 */     final EventLoopGroup currentChildGroup = this.childGroup;
/* 168 */     final ChannelHandler currentChildHandler = this.childHandler;
/*     */ 
/*     */     
/* 171 */     synchronized (this.childOptions) {
/* 172 */       arrayOfEntry1 = (Map.Entry[])this.childOptions.entrySet().toArray((Object[])newOptionArray(this.childOptions.size()));
/*     */     } 
/* 174 */     synchronized (this.childAttrs) {
/* 175 */       arrayOfEntry2 = (Map.Entry[])this.childAttrs.entrySet().toArray((Object[])newAttrArray(this.childAttrs.size()));
/*     */     } 
/*     */     
/* 178 */     p.addLast(new ChannelHandler[] { (ChannelHandler)new ChannelInitializer<Channel>()
/*     */           {
/*     */             public void initChannel(Channel ch) throws Exception {
/* 181 */               ch.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new ServerBootstrap.ServerBootstrapAcceptor(this.val$currentChildGroup, this.val$currentChildHandler, (Map.Entry<ChannelOption<?>, Object>[])this.val$currentChildOptions, (Map.Entry<AttributeKey<?>, Object>[])this.val$currentChildAttrs) });
/*     */             }
/*     */           } });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap validate() {
/* 189 */     super.validate();
/* 190 */     if (this.childHandler == null) {
/* 191 */       throw new IllegalStateException("childHandler not set");
/*     */     }
/* 193 */     if (this.childGroup == null) {
/* 194 */       logger.warn("childGroup is not set. Using parentGroup instead.");
/* 195 */       this.childGroup = group();
/*     */     } 
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map.Entry<ChannelOption<?>, Object>[] newOptionArray(int size) {
/* 202 */     return (Map.Entry<ChannelOption<?>, Object>[])new Map.Entry[size];
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map.Entry<AttributeKey<?>, Object>[] newAttrArray(int size) {
/* 207 */     return (Map.Entry<AttributeKey<?>, Object>[])new Map.Entry[size];
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ServerBootstrapAcceptor
/*     */     extends ChannelInboundHandlerAdapter
/*     */   {
/*     */     private final EventLoopGroup childGroup;
/*     */     
/*     */     private final ChannelHandler childHandler;
/*     */     private final Map.Entry<ChannelOption<?>, Object>[] childOptions;
/*     */     private final Map.Entry<AttributeKey<?>, Object>[] childAttrs;
/*     */     
/*     */     ServerBootstrapAcceptor(EventLoopGroup childGroup, ChannelHandler childHandler, Map.Entry<ChannelOption<?>, Object>[] childOptions, Map.Entry<AttributeKey<?>, Object>[] childAttrs) {
/* 221 */       this.childGroup = childGroup;
/* 222 */       this.childHandler = childHandler;
/* 223 */       this.childOptions = childOptions;
/* 224 */       this.childAttrs = childAttrs;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void channelRead(ChannelHandlerContext ctx, Object msg) {
/* 230 */       Channel child = (Channel)msg;
/*     */       
/* 232 */       child.pipeline().addLast(new ChannelHandler[] { this.childHandler });
/*     */       
/* 234 */       for (Map.Entry<ChannelOption<?>, Object> e : this.childOptions) {
/*     */         try {
/* 236 */           if (!child.config().setOption(e.getKey(), e.getValue())) {
/* 237 */             ServerBootstrap.logger.warn("Unknown channel option: " + e);
/*     */           }
/* 239 */         } catch (Throwable t) {
/* 240 */           ServerBootstrap.logger.warn("Failed to set a channel option: " + child, t);
/*     */         } 
/*     */       } 
/*     */       
/* 244 */       for (Map.Entry<AttributeKey<?>, Object> e : this.childAttrs) {
/* 245 */         child.attr(e.getKey()).set(e.getValue());
/*     */       }
/*     */       
/*     */       try {
/* 249 */         this.childGroup.register(child);
/* 250 */       } catch (Throwable t) {
/* 251 */         child.unsafe().closeForcibly();
/* 252 */         ServerBootstrap.logger.warn("Failed to register an accepted channel: " + child, t);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 258 */       final ChannelConfig config = ctx.channel().config();
/* 259 */       if (config.isAutoRead()) {
/*     */ 
/*     */         
/* 262 */         config.setAutoRead(false);
/* 263 */         ctx.channel().eventLoop().schedule(new Runnable()
/*     */             {
/*     */               public void run() {
/* 266 */                 config.setAutoRead(true);
/*     */               }
/*     */             },  1L, TimeUnit.SECONDS);
/*     */       } 
/*     */ 
/*     */       
/* 272 */       ctx.fireExceptionCaught(cause);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerBootstrap clone() {
/* 279 */     return new ServerBootstrap(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 284 */     StringBuilder buf = new StringBuilder(super.toString());
/* 285 */     buf.setLength(buf.length() - 1);
/* 286 */     buf.append(", ");
/* 287 */     if (this.childGroup != null) {
/* 288 */       buf.append("childGroup: ");
/* 289 */       buf.append(this.childGroup.getClass().getSimpleName());
/* 290 */       buf.append(", ");
/*     */     } 
/* 292 */     synchronized (this.childOptions) {
/* 293 */       if (!this.childOptions.isEmpty()) {
/* 294 */         buf.append("childOptions: ");
/* 295 */         buf.append(this.childOptions);
/* 296 */         buf.append(", ");
/*     */       } 
/*     */     } 
/* 299 */     synchronized (this.childAttrs) {
/* 300 */       if (!this.childAttrs.isEmpty()) {
/* 301 */         buf.append("childAttrs: ");
/* 302 */         buf.append(this.childAttrs);
/* 303 */         buf.append(", ");
/*     */       } 
/*     */     } 
/* 306 */     if (this.childHandler != null) {
/* 307 */       buf.append("childHandler: ");
/* 308 */       buf.append(this.childHandler);
/* 309 */       buf.append(", ");
/*     */     } 
/* 311 */     if (buf.charAt(buf.length() - 1) == '(') {
/* 312 */       buf.append(')');
/*     */     } else {
/* 314 */       buf.setCharAt(buf.length() - 2, ')');
/* 315 */       buf.setLength(buf.length() - 1);
/*     */     } 
/*     */     
/* 318 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public ServerBootstrap() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\bootstrap\ServerBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */