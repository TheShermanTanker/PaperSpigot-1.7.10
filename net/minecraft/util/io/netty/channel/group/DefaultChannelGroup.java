/*     */ package net.minecraft.util.io.netty.channel.group;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.io.netty.util.internal.ConcurrentSet;
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
/*     */ public class DefaultChannelGroup
/*     */   extends AbstractSet<Channel>
/*     */   implements ChannelGroup
/*     */ {
/*  41 */   private static final AtomicInteger nextId = new AtomicInteger();
/*     */   private final String name;
/*     */   private final EventExecutor executor;
/*  44 */   private final ConcurrentSet<Channel> serverChannels = new ConcurrentSet();
/*  45 */   private final ConcurrentSet<Channel> nonServerChannels = new ConcurrentSet();
/*  46 */   private final ChannelFutureListener remover = new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture future) throws Exception {
/*  49 */         DefaultChannelGroup.this.remove(future.channel());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultChannelGroup(EventExecutor executor) {
/*  58 */     this("group-0x" + Integer.toHexString(nextId.incrementAndGet()), executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultChannelGroup(String name, EventExecutor executor) {
/*  67 */     if (name == null) {
/*  68 */       throw new NullPointerException("name");
/*     */     }
/*  70 */     this.name = name;
/*  71 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  76 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  81 */     return (this.nonServerChannels.isEmpty() && this.serverChannels.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  86 */     return this.nonServerChannels.size() + this.serverChannels.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  91 */     if (o instanceof Channel) {
/*  92 */       Channel c = (Channel)o;
/*  93 */       if (o instanceof net.minecraft.util.io.netty.channel.ServerChannel) {
/*  94 */         return this.serverChannels.contains(c);
/*     */       }
/*  96 */       return this.nonServerChannels.contains(c);
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(Channel channel) {
/* 105 */     ConcurrentSet<Channel> set = (channel instanceof net.minecraft.util.io.netty.channel.ServerChannel) ? this.serverChannels : this.nonServerChannels;
/*     */ 
/*     */     
/* 108 */     boolean added = set.add(channel);
/* 109 */     if (added) {
/* 110 */       channel.closeFuture().addListener((GenericFutureListener)this.remover);
/*     */     }
/* 112 */     return added;
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/*     */     boolean removed;
/* 117 */     if (!(o instanceof Channel)) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     Channel c = (Channel)o;
/* 122 */     if (c instanceof net.minecraft.util.io.netty.channel.ServerChannel) {
/* 123 */       removed = this.serverChannels.remove(c);
/*     */     } else {
/* 125 */       removed = this.nonServerChannels.remove(c);
/*     */     } 
/* 127 */     if (!removed) {
/* 128 */       return false;
/*     */     }
/*     */     
/* 131 */     c.closeFuture().removeListener((GenericFutureListener)this.remover);
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 137 */     this.nonServerChannels.clear();
/* 138 */     this.serverChannels.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Channel> iterator() {
/* 143 */     return new CombinedIterator<Channel>(this.serverChannels.iterator(), this.nonServerChannels.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 150 */     Collection<Channel> channels = new ArrayList<Channel>(size());
/* 151 */     channels.addAll((Collection<? extends Channel>)this.serverChannels);
/* 152 */     channels.addAll((Collection<? extends Channel>)this.nonServerChannels);
/* 153 */     return channels.toArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 158 */     Collection<Channel> channels = new ArrayList<Channel>(size());
/* 159 */     channels.addAll((Collection<? extends Channel>)this.serverChannels);
/* 160 */     channels.addAll((Collection<? extends Channel>)this.nonServerChannels);
/* 161 */     return channels.toArray(a);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture close() {
/* 166 */     return close(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture disconnect() {
/* 171 */     return disconnect(ChannelMatchers.all());
/*     */   }
/*     */   
/*     */   public ChannelGroupFuture deregister() {
/* 175 */     return deregister(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture write(Object message) {
/* 180 */     return write(message, ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object safeDuplicate(Object message) {
/* 186 */     if (message instanceof ByteBuf)
/* 187 */       return ((ByteBuf)message).duplicate().retain(); 
/* 188 */     if (message instanceof ByteBufHolder) {
/* 189 */       return ((ByteBufHolder)message).duplicate().retain();
/*     */     }
/* 191 */     return ReferenceCountUtil.retain(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture write(Object message, ChannelMatcher matcher) {
/* 197 */     if (message == null) {
/* 198 */       throw new NullPointerException("message");
/*     */     }
/* 200 */     if (matcher == null) {
/* 201 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 204 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/* 205 */     for (Channel c : this.nonServerChannels) {
/* 206 */       if (matcher.matches(c)) {
/* 207 */         futures.put(c, c.write(safeDuplicate(message)));
/*     */       }
/*     */     } 
/*     */     
/* 211 */     ReferenceCountUtil.release(message);
/* 212 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroup flush() {
/* 217 */     return flush(ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture flushAndWrite(Object message) {
/* 222 */     return flushAndWrite(message, ChannelMatchers.all());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
/* 227 */     if (matcher == null) {
/* 228 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 231 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */ 
/*     */     
/* 234 */     for (Channel c : this.serverChannels) {
/* 235 */       if (matcher.matches(c)) {
/* 236 */         futures.put(c, c.disconnect());
/*     */       }
/*     */     } 
/* 239 */     for (Channel c : this.nonServerChannels) {
/* 240 */       if (matcher.matches(c)) {
/* 241 */         futures.put(c, c.disconnect());
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture close(ChannelMatcher matcher) {
/* 250 */     if (matcher == null) {
/* 251 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 254 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */ 
/*     */     
/* 257 */     for (Channel c : this.serverChannels) {
/* 258 */       if (matcher.matches(c)) {
/* 259 */         futures.put(c, c.close());
/*     */       }
/*     */     } 
/* 262 */     for (Channel c : this.nonServerChannels) {
/* 263 */       if (matcher.matches(c)) {
/* 264 */         futures.put(c, c.close());
/*     */       }
/*     */     } 
/*     */     
/* 268 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture deregister(ChannelMatcher matcher) {
/* 273 */     if (matcher == null) {
/* 274 */       throw new NullPointerException("matcher");
/*     */     }
/*     */     
/* 277 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */ 
/*     */     
/* 280 */     for (Channel c : this.serverChannels) {
/* 281 */       if (matcher.matches(c)) {
/* 282 */         futures.put(c, c.deregister());
/*     */       }
/*     */     } 
/* 285 */     for (Channel c : this.nonServerChannels) {
/* 286 */       if (matcher.matches(c)) {
/* 287 */         futures.put(c, c.deregister());
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroup flush(ChannelMatcher matcher) {
/* 296 */     for (Channel c : this.nonServerChannels) {
/* 297 */       if (matcher.matches(c)) {
/* 298 */         c.flush();
/*     */       }
/*     */     } 
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelGroupFuture flushAndWrite(Object message, ChannelMatcher matcher) {
/* 306 */     if (message == null) {
/* 307 */       throw new NullPointerException("message");
/*     */     }
/*     */     
/* 310 */     Map<Channel, ChannelFuture> futures = new LinkedHashMap<Channel, ChannelFuture>(size());
/*     */     
/* 312 */     for (Channel c : this.nonServerChannels) {
/* 313 */       if (matcher.matches(c)) {
/* 314 */         futures.put(c, c.writeAndFlush(safeDuplicate(message)));
/*     */       }
/*     */     } 
/*     */     
/* 318 */     ReferenceCountUtil.release(message);
/*     */     
/* 320 */     return new DefaultChannelGroupFuture(this, futures, this.executor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 325 */     return System.identityHashCode(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 330 */     return (this == o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ChannelGroup o) {
/* 335 */     int v = name().compareTo(o.name());
/* 336 */     if (v != 0) {
/* 337 */       return v;
/*     */     }
/*     */     
/* 340 */     return System.identityHashCode(this) - System.identityHashCode(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 345 */     return getClass().getSimpleName() + "(name: " + name() + ", size: " + size() + ')';
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\group\DefaultChannelGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */