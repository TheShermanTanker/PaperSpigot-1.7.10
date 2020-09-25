package org.json.simple.parser;

import java.util.List;
import java.util.Map;

public interface ContainerFactory {
  Map createObjectContainer();
  
  List creatArrayContainer();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\json\simple\parser\ContainerFactory.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */