package org.apache.logging.log4j.core.impl;

import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.message.Message;

public interface LogEventFactory {
  LogEvent createEvent(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, List<Property> paramList, Throwable paramThrowable);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\LogEventFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */