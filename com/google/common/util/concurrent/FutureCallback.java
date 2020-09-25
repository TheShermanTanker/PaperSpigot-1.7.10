package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;

@Beta
public interface FutureCallback<V> {
  void onSuccess(V paramV);
  
  void onFailure(Throwable paramThrowable);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\FutureCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */