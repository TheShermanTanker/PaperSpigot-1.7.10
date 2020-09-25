package com.avaje.ebean;

public interface AdminAutofetch {
  boolean isProfiling();
  
  void setProfiling(boolean paramBoolean);
  
  boolean isQueryTuning();
  
  void setQueryTuning(boolean paramBoolean);
  
  double getProfilingRate();
  
  void setProfilingRate(double paramDouble);
  
  int getProfilingBase();
  
  void setProfilingBase(int paramInt);
  
  int getProfilingMin();
  
  void setProfilingMin(int paramInt);
  
  String collectUsageViaGC();
  
  String updateTunedQueryInfo();
  
  int clearTunedQueryInfo();
  
  int clearProfilingInfo();
  
  void clearQueryStatistics();
  
  int getTotalTunedQueryCount();
  
  int getTotalTunedQuerySize();
  
  int getTotalProfileSize();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\AdminAutofetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */