package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebean.bean.NodeUsageListener;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.config.AutofetchMode;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;
import java.util.Iterator;

public interface AutoFetchManager extends NodeUsageListener {
  void setOwner(SpiEbeanServer paramSpiEbeanServer, ServerConfig paramServerConfig);
  
  void clearQueryStatistics();
  
  int clearTunedQueryInfo();
  
  int clearProfilingInfo();
  
  void shutdown();
  
  TunedQueryInfo getTunedQueryInfo(String paramString);
  
  Statistics getStatistics(String paramString);
  
  Iterator<TunedQueryInfo> iterateTunedQueryInfo();
  
  Iterator<Statistics> iterateStatistics();
  
  boolean isProfiling();
  
  void setProfiling(boolean paramBoolean);
  
  boolean isQueryTuning();
  
  void setQueryTuning(boolean paramBoolean);
  
  AutofetchMode getMode();
  
  void setMode(AutofetchMode paramAutofetchMode);
  
  double getProfilingRate();
  
  void setProfilingRate(double paramDouble);
  
  int getProfilingBase();
  
  void setProfilingBase(int paramInt);
  
  int getProfilingMin();
  
  void setProfilingMin(int paramInt);
  
  String collectUsageViaGC(long paramLong);
  
  String updateTunedQueryInfo();
  
  boolean tuneQuery(SpiQuery<?> paramSpiQuery);
  
  void collectQueryInfo(ObjectGraphNode paramObjectGraphNode, int paramInt1, int paramInt2);
  
  int getTotalTunedQueryCount();
  
  int getTotalTunedQuerySize();
  
  int getTotalProfileSize();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\autofetch\AutoFetchManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */