package org.bukkit.block;

import org.bukkit.projectiles.BlockProjectileSource;

public interface Dispenser extends BlockState, ContainerBlock {
  BlockProjectileSource getBlockProjectileSource();
  
  boolean dispense();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Dispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */