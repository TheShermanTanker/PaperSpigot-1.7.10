package net.minecraft.util.io.netty.handler.codec.http.multipart;

import java.nio.charset.Charset;
import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;

public interface HttpDataFactory {
  Attribute createAttribute(HttpRequest paramHttpRequest, String paramString);
  
  Attribute createAttribute(HttpRequest paramHttpRequest, String paramString1, String paramString2);
  
  FileUpload createFileUpload(HttpRequest paramHttpRequest, String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong);
  
  void removeHttpDataFromClean(HttpRequest paramHttpRequest, InterfaceHttpData paramInterfaceHttpData);
  
  void cleanRequestHttpDatas(HttpRequest paramHttpRequest);
  
  void cleanAllHttpDatas();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\HttpDataFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */