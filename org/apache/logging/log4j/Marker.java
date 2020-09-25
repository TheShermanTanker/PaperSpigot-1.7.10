package org.apache.logging.log4j;

import java.io.Serializable;

public interface Marker extends Serializable {
  String getName();
  
  Marker getParent();
  
  boolean isInstanceOf(Marker paramMarker);
  
  boolean isInstanceOf(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\Marker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */