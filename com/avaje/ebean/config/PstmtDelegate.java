package com.avaje.ebean.config;

import java.sql.PreparedStatement;

public interface PstmtDelegate {
  PreparedStatement unwrap(PreparedStatement paramPreparedStatement);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\PstmtDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */