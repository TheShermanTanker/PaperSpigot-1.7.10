package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
interface BstModifier<K, N extends BstNode<K, N>> {
  BstModificationResult<N> modify(K paramK, @Nullable N paramN);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstModifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */