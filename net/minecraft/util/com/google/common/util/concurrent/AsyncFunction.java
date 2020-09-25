package net.minecraft.util.com.google.common.util.concurrent;

public interface AsyncFunction<I, O> {
  ListenableFuture<O> apply(I paramI) throws Exception;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\AsyncFunction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */