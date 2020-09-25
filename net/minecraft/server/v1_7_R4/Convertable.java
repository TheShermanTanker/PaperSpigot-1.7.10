package net.minecraft.server.v1_7_R4;

public interface Convertable {
  IDataManager a(String paramString, boolean paramBoolean);
  
  void d();
  
  boolean e(String paramString);
  
  boolean isConvertable(String paramString);
  
  boolean convert(String paramString, IProgressUpdate paramIProgressUpdate);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Convertable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */