package org.bukkit.help;

public interface HelpTopicFactory<TCommand extends org.bukkit.command.Command> {
  HelpTopic createTopic(TCommand paramTCommand);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\HelpTopicFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */