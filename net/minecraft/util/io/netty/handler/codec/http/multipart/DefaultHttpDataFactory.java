/*     */ package net.minecraft.util.io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpDataFactory
/*     */   implements HttpDataFactory
/*     */ {
/*     */   public static final long MINSIZE = 16384L;
/*     */   private final boolean useDisk;
/*     */   private final boolean checkSize;
/*     */   private long minSize;
/*  50 */   private final ConcurrentMap<HttpRequest, List<HttpData>> requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory() {
/*  58 */     this.useDisk = false;
/*  59 */     this.checkSize = true;
/*  60 */     this.minSize = 16384L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory(boolean useDisk) {
/*  67 */     this.useDisk = useDisk;
/*  68 */     this.checkSize = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory(long minSize) {
/*  76 */     this.useDisk = false;
/*  77 */     this.checkSize = true;
/*  78 */     this.minSize = minSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<HttpData> getList(HttpRequest request) {
/*  85 */     List<HttpData> list = this.requestFileDeleteMap.get(request);
/*  86 */     if (list == null) {
/*  87 */       list = new ArrayList<HttpData>();
/*  88 */       this.requestFileDeleteMap.put(request, list);
/*     */     } 
/*  90 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name) {
/*  95 */     if (this.useDisk) {
/*  96 */       Attribute attribute = new DiskAttribute(name);
/*  97 */       List<HttpData> fileToDelete = getList(request);
/*  98 */       fileToDelete.add(attribute);
/*  99 */       return attribute;
/*     */     } 
/* 101 */     if (this.checkSize) {
/* 102 */       Attribute attribute = new MixedAttribute(name, this.minSize);
/* 103 */       List<HttpData> fileToDelete = getList(request);
/* 104 */       fileToDelete.add(attribute);
/* 105 */       return attribute;
/*     */     } 
/* 107 */     return new MemoryAttribute(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name, String value) {
/* 112 */     if (this.useDisk) {
/*     */       Attribute attribute;
/*     */       try {
/* 115 */         attribute = new DiskAttribute(name, value);
/* 116 */       } catch (IOException e) {
/*     */         
/* 118 */         attribute = new MixedAttribute(name, value, this.minSize);
/*     */       } 
/* 120 */       List<HttpData> fileToDelete = getList(request);
/* 121 */       fileToDelete.add(attribute);
/* 122 */       return attribute;
/*     */     } 
/* 124 */     if (this.checkSize) {
/* 125 */       Attribute attribute = new MixedAttribute(name, value, this.minSize);
/* 126 */       List<HttpData> fileToDelete = getList(request);
/* 127 */       fileToDelete.add(attribute);
/* 128 */       return attribute;
/*     */     } 
/*     */     try {
/* 131 */       return new MemoryAttribute(name, value);
/* 132 */     } catch (IOException e) {
/* 133 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload createFileUpload(HttpRequest request, String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/* 141 */     if (this.useDisk) {
/* 142 */       FileUpload fileUpload = new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */       
/* 144 */       List<HttpData> fileToDelete = getList(request);
/* 145 */       fileToDelete.add(fileUpload);
/* 146 */       return fileUpload;
/*     */     } 
/* 148 */     if (this.checkSize) {
/* 149 */       FileUpload fileUpload = new MixedFileUpload(name, filename, contentType, contentTransferEncoding, charset, size, this.minSize);
/*     */       
/* 151 */       List<HttpData> fileToDelete = getList(request);
/* 152 */       fileToDelete.add(fileUpload);
/* 153 */       return fileUpload;
/*     */     } 
/* 155 */     return new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHttpDataFromClean(HttpRequest request, InterfaceHttpData data) {
/* 161 */     if (data instanceof HttpData) {
/* 162 */       List<HttpData> fileToDelete = getList(request);
/* 163 */       fileToDelete.remove(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanRequestHttpDatas(HttpRequest request) {
/* 169 */     List<HttpData> fileToDelete = this.requestFileDeleteMap.remove(request);
/* 170 */     if (fileToDelete != null) {
/* 171 */       for (HttpData data : fileToDelete) {
/* 172 */         data.delete();
/*     */       }
/* 174 */       fileToDelete.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanAllHttpDatas() {
/* 180 */     for (HttpRequest request : this.requestFileDeleteMap.keySet()) {
/* 181 */       List<HttpData> fileToDelete = this.requestFileDeleteMap.get(request);
/* 182 */       if (fileToDelete != null) {
/* 183 */         for (HttpData data : fileToDelete) {
/* 184 */           data.delete();
/*     */         }
/* 186 */         fileToDelete.clear();
/*     */       } 
/* 188 */       this.requestFileDeleteMap.remove(request);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\multipart\DefaultHttpDataFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */