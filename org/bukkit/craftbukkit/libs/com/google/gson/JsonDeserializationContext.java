package org.bukkit.craftbukkit.libs.com.google.gson;

import java.lang.reflect.Type;

public interface JsonDeserializationContext {
  <T> T deserialize(JsonElement paramJsonElement, Type paramType) throws JsonParseException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\JsonDeserializationContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */