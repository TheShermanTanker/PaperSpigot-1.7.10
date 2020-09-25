package com.avaje.ebean.text.json;

import java.util.Map;

public interface JsonReadBeanVisitor<T> {
  void visit(T paramT, Map<String, JsonElement> paramMap);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonReadBeanVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */