package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface BstPathFactory<N extends BstNode<?, N>, P extends BstPath<N, P>> {
  P extension(P paramP, BstSide paramBstSide);
  
  P initialPath(N paramN);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstPathFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */