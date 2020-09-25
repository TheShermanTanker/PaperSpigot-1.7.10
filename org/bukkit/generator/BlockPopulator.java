package org.bukkit.generator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;

public abstract class BlockPopulator {
  public abstract void populate(World paramWorld, Random paramRandom, Chunk paramChunk);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\generator\BlockPopulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */