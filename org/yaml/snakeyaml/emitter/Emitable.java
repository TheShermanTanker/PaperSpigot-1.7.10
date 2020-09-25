package org.yaml.snakeyaml.emitter;

import java.io.IOException;
import org.yaml.snakeyaml.events.Event;

public interface Emitable {
  void emit(Event paramEvent) throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\emitter\Emitable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */