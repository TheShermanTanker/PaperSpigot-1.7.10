package com.avaje.ebeaninternal.server.jmx;

public interface MAdminAutofetchMBean {
  boolean isProfiling();
  
  void setProfiling(boolean paramBoolean);
  
  boolean isQueryTuning();
  
  void setQueryTuning(boolean paramBoolean);
  
  String getMode();
  
  String getModeOptions();
  
  void setMode(String paramString);
  
  int getProfilingBase();
  
  void setProfilingBase(int paramInt);
  
  double getProfilingRate();
  
  void setProfilingRate(double paramDouble);
  
  int getProfilingMin();
  
  void setProfilingMin(int paramInt);
  
  String collectUsageViaGC();
  
  String updateTunedQueryInfo();
  
  int clearTunedQueryInfo();
  
  int clearProfilingInfo();
  
  int getTotalTunedQueryCount();
  
  int getTotalTunedQuerySize();
  
  int getTotalProfileSize();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\jmx\MAdminAutofetchMBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */