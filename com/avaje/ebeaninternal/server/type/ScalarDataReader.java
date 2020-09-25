package com.avaje.ebeaninternal.server.type;

import java.sql.SQLException;

public interface ScalarDataReader<T> {
  T read(DataReader paramDataReader) throws SQLException;
  
  void loadIgnore(DataReader paramDataReader);
  
  void bind(DataBind paramDataBind, T paramT) throws SQLException;
  
  void accumulateScalarTypes(String paramString, CtCompoundTypeScalarList paramCtCompoundTypeScalarList);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarDataReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */