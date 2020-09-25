package org.bukkit.craftbukkit.libs.jline.console.history;

import java.util.Iterator;
import java.util.ListIterator;

public interface History extends Iterable<History.Entry> {
  int size();
  
  boolean isEmpty();
  
  int index();
  
  void clear();
  
  CharSequence get(int paramInt);
  
  void add(CharSequence paramCharSequence);
  
  void replace(CharSequence paramCharSequence);
  
  ListIterator<Entry> entries(int paramInt);
  
  ListIterator<Entry> entries();
  
  Iterator<Entry> iterator();
  
  CharSequence current();
  
  boolean previous();
  
  boolean next();
  
  boolean moveToFirst();
  
  boolean moveToLast();
  
  boolean moveTo(int paramInt);
  
  void moveToEnd();
  
  public static interface Entry {
    int index();
    
    CharSequence value();
  }
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\History.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */