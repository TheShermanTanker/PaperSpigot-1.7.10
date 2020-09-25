package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.Label;

public interface TableSwitchGenerator {
  void generateCase(int paramInt, Label paramLabel);
  
  void generateDefault();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\commons\TableSwitchGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */