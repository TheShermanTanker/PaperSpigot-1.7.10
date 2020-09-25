package org.bukkit.inventory.meta;

import java.util.List;

public interface BookMeta extends ItemMeta {
  boolean hasTitle();
  
  String getTitle();
  
  boolean setTitle(String paramString);
  
  boolean hasAuthor();
  
  String getAuthor();
  
  void setAuthor(String paramString);
  
  boolean hasPages();
  
  String getPage(int paramInt);
  
  void setPage(int paramInt, String paramString);
  
  List<String> getPages();
  
  void setPages(List<String> paramList);
  
  void setPages(String... paramVarArgs);
  
  void addPage(String... paramVarArgs);
  
  int getPageCount();
  
  BookMeta clone();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\BookMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */