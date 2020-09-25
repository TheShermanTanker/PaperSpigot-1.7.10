package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
interface BstBalancePolicy<N extends BstNode<?, N>> {
  N balance(BstNodeFactory<N> paramBstNodeFactory, N paramN1, @Nullable N paramN2, @Nullable N paramN3);
  
  @Nullable
  N combine(BstNodeFactory<N> paramBstNodeFactory, @Nullable N paramN1, @Nullable N paramN2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstBalancePolicy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */