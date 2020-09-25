package org.bukkit.craftbukkit.libs.jline.console.completer;

import java.io.IOException;
import java.util.List;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

public interface CompletionHandler {
  boolean complete(ConsoleReader paramConsoleReader, List<CharSequence> paramList, int paramInt) throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\CompletionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */