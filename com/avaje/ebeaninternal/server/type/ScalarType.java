package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.StringFormatter;
import com.avaje.ebean.text.StringParser;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;

public interface ScalarType<T> extends StringParser, StringFormatter, ScalarDataReader<T> {
  int getLength();
  
  boolean isJdbcNative();
  
  int getJdbcType();
  
  Class<T> getType();
  
  T read(DataReader paramDataReader) throws SQLException;
  
  void loadIgnore(DataReader paramDataReader);
  
  void bind(DataBind paramDataBind, T paramT) throws SQLException;
  
  Object toJdbcType(Object paramObject);
  
  T toBeanType(Object paramObject);
  
  String formatValue(T paramT);
  
  String format(Object paramObject);
  
  T parse(String paramString);
  
  T parseDateTime(long paramLong);
  
  boolean isDateTimeCapable();
  
  void jsonWrite(WriteJsonBuffer paramWriteJsonBuffer, T paramT, JsonValueAdapter paramJsonValueAdapter);
  
  String jsonToString(T paramT, JsonValueAdapter paramJsonValueAdapter);
  
  T jsonFromString(String paramString, JsonValueAdapter paramJsonValueAdapter);
  
  Object readData(DataInput paramDataInput) throws IOException;
  
  void writeData(DataOutput paramDataOutput, Object paramObject) throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */