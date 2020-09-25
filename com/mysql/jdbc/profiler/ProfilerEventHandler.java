package com.mysql.jdbc.profiler;

import com.mysql.jdbc.Extension;

public interface ProfilerEventHandler extends Extension {
  void consumeEvent(ProfilerEvent paramProfilerEvent);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\profiler\ProfilerEventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */