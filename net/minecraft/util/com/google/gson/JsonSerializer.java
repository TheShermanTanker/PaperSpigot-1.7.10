package net.minecraft.util.com.google.gson;

import java.lang.reflect.Type;

public interface JsonSerializer<T> {
  JsonElement serialize(T paramT, Type paramType, JsonSerializationContext paramJsonSerializationContext);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\gson\JsonSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */