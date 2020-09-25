package net.minecraft.util.com.google.common.eventbus;

import net.minecraft.util.com.google.common.collect.Multimap;

interface SubscriberFindingStrategy {
  Multimap<Class<?>, EventSubscriber> findAllSubscribers(Object paramObject);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\eventbus\SubscriberFindingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */