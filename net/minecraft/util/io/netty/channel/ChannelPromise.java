package net.minecraft.util.io.netty.channel;

import net.minecraft.util.io.netty.util.concurrent.Future;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.util.io.netty.util.concurrent.Promise;

public interface ChannelPromise extends ChannelFuture, Promise<Void> {
  Channel channel();
  
  ChannelPromise setSuccess(Void paramVoid);
  
  ChannelPromise setSuccess();
  
  boolean trySuccess();
  
  ChannelPromise setFailure(Throwable paramThrowable);
  
  ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelPromise sync() throws InterruptedException;
  
  ChannelPromise syncUninterruptibly();
  
  ChannelPromise await() throws InterruptedException;
  
  ChannelPromise awaitUninterruptibly();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */