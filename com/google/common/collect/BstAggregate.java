package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
interface BstAggregate<N extends BstNode<?, N>> {
  int treeValue(@Nullable N paramN);
  
  int entryValue(N paramN);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstAggregate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */