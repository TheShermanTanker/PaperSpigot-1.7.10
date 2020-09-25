/*    */ package net.minecraft.util.com.google.gson.internal.bind;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.sql.Date;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import net.minecraft.util.com.google.gson.Gson;
/*    */ import net.minecraft.util.com.google.gson.JsonSyntaxException;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapter;
/*    */ import net.minecraft.util.com.google.gson.TypeAdapterFactory;
/*    */ import net.minecraft.util.com.google.gson.reflect.TypeToken;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonReader;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonToken;
/*    */ import net.minecraft.util.com.google.gson.stream.JsonWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SqlDateTypeAdapter
/*    */   extends TypeAdapter<Date>
/*    */ {
/* 39 */   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 42 */         return (typeToken.getRawType() == Date.class) ? new SqlDateTypeAdapter() : null;
/*    */       }
/*    */     };
/*    */ 
/*    */   
/* 47 */   private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");
/*    */ 
/*    */   
/*    */   public synchronized Date read(JsonReader in) throws IOException {
/* 51 */     if (in.peek() == JsonToken.NULL) {
/* 52 */       in.nextNull();
/* 53 */       return null;
/*    */     } 
/*    */     try {
/* 56 */       long utilDate = this.format.parse(in.nextString()).getTime();
/* 57 */       return new Date(utilDate);
/* 58 */     } catch (ParseException e) {
/* 59 */       throw new JsonSyntaxException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void write(JsonWriter out, Date value) throws IOException {
/* 65 */     out.value((value == null) ? null : this.format.format(value));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\gson\internal\bind\SqlDateTypeAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */