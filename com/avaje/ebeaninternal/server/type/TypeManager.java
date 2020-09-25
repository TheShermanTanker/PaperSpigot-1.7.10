package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;

public interface TypeManager {
  CheckImmutableResponse checkImmutable(Class<?> paramClass);
  
  ScalarDataReader<?> recursiveCreateScalarDataReader(Class<?> paramClass);
  
  ScalarType<?> recursiveCreateScalarTypes(Class<?> paramClass);
  
  void add(ScalarType<?> paramScalarType);
  
  CtCompoundType<?> getCompoundType(Class<?> paramClass);
  
  ScalarType<?> getScalarType(int paramInt);
  
  <T> ScalarType<T> getScalarType(Class<T> paramClass);
  
  <T> ScalarType<T> getScalarType(Class<T> paramClass, int paramInt);
  
  ScalarType<?> createEnumScalarType(Class<?> paramClass);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\TypeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */