package net.minecraft.server.v1_7_R4;

import java.util.Collection;
import java.util.UUID;

public interface AttributeInstance {
  IAttribute getAttribute();
  
  double b();
  
  void setValue(double paramDouble);
  
  Collection c();
  
  AttributeModifier a(UUID paramUUID);
  
  void a(AttributeModifier paramAttributeModifier);
  
  void b(AttributeModifier paramAttributeModifier);
  
  double getValue();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AttributeInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */