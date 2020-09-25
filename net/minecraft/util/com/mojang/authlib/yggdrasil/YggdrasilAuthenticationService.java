/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.util.com.google.gson.GsonBuilder;
/*    */ import net.minecraft.util.com.google.gson.JsonDeserializationContext;
/*    */ import net.minecraft.util.com.google.gson.JsonElement;
/*    */ import net.minecraft.util.com.google.gson.JsonObject;
/*    */ import net.minecraft.util.com.google.gson.JsonParseException;
/*    */ import net.minecraft.util.com.google.gson.JsonSerializationContext;
/*    */ import net.minecraft.util.com.mojang.authlib.Agent;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.util.com.mojang.authlib.UserAuthentication;
/*    */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*    */ import net.minecraft.util.com.mojang.authlib.minecraft.MinecraftSessionService;
/*    */ import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;
/*    */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
/*    */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.Response;
/*    */ 
/*    */ public class YggdrasilAuthenticationService extends HttpAuthenticationService {
/*    */   private final String clientToken;
/*    */   
/*    */   public YggdrasilAuthenticationService(Proxy proxy, String clientToken) {
/* 27 */     super(proxy);
/* 28 */     this.clientToken = clientToken;
/* 29 */     GsonBuilder builder = new GsonBuilder();
/* 30 */     builder.registerTypeAdapter(GameProfile.class, new GameProfileSerializer());
/* 31 */     builder.registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer());
/* 32 */     builder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
/* 33 */     builder.registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer());
/* 34 */     this.gson = builder.create();
/*    */   }
/*    */   private final Gson gson;
/*    */   
/*    */   public UserAuthentication createUserAuthentication(Agent agent) {
/* 39 */     return (UserAuthentication)new YggdrasilUserAuthentication(this, agent);
/*    */   }
/*    */ 
/*    */   
/*    */   public MinecraftSessionService createMinecraftSessionService() {
/* 44 */     return (MinecraftSessionService)new YggdrasilMinecraftSessionService(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfileRepository createProfileRepository() {
/* 49 */     return new YggdrasilGameProfileRepository(this);
/*    */   }
/*    */   
/*    */   protected <T extends Response> T makeRequest(URL url, Object input, Class<T> classOfT) throws AuthenticationException {
/*    */     try {
/* 54 */       String jsonResult = (input == null) ? performGetRequest(url) : performPostRequest(url, this.gson.toJson(input), "application/json");
/* 55 */       Response response = (Response)this.gson.fromJson(jsonResult, classOfT);
/*    */       
/* 57 */       if (response == null) return null;
/*    */       
/* 59 */       if (StringUtils.isNotBlank(response.getError())) {
/* 60 */         if ("UserMigratedException".equals(response.getCause()))
/* 61 */           throw new UserMigratedException(response.getErrorMessage()); 
/* 62 */         if (response.getError().equals("ForbiddenOperationException")) {
/* 63 */           throw new InvalidCredentialsException(response.getErrorMessage());
/*    */         }
/* 65 */         throw new AuthenticationException(response.getErrorMessage());
/*    */       } 
/*    */ 
/*    */       
/* 69 */       return (T)response;
/* 70 */     } catch (IOException e) {
/* 71 */       throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
/* 72 */     } catch (IllegalStateException e) {
/* 73 */       throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
/* 74 */     } catch (JsonParseException e) {
/* 75 */       throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getClientToken() {
/* 80 */     return this.clientToken;
/*    */   }
/*    */   
/*    */   private static class GameProfileSerializer implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile> { private GameProfileSerializer() {}
/*    */     
/*    */     public GameProfile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 86 */       JsonObject object = (JsonObject)json;
/* 87 */       UUID id = object.has("id") ? (UUID)context.deserialize(object.get("id"), UUID.class) : null;
/* 88 */       String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
/* 89 */       return new GameProfile(id, name);
/*    */     }
/*    */ 
/*    */     
/*    */     public JsonElement serialize(GameProfile src, Type typeOfSrc, JsonSerializationContext context) {
/* 94 */       JsonObject result = new JsonObject();
/* 95 */       if (src.getId() != null) result.add("id", context.serialize(src.getId())); 
/* 96 */       if (src.getName() != null) result.addProperty("name", src.getName()); 
/* 97 */       return (JsonElement)result;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\YggdrasilAuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */