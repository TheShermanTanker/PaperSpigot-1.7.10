/*    */ package net.minecraft.util.com.mojang.authlib.yggdrasil;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.base.Strings;
/*    */ import net.minecraft.util.com.google.common.collect.Sets;
/*    */ import net.minecraft.util.com.mojang.authlib.Agent;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.util.com.mojang.authlib.HttpAuthenticationService;
/*    */ import net.minecraft.util.com.mojang.authlib.ProfileLookupCallback;
/*    */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class YggdrasilGameProfileRepository implements GameProfileRepository {
/* 16 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private static final String BASE_URL = "https://api.mojang.com/";
/*    */   private static final String SEARCH_PAGE_URL = "https://api.mojang.com/profiles/";
/*    */   private static final int ENTRIES_PER_PAGE = 2;
/*    */   private static final int MAX_FAIL_COUNT = 3;
/*    */   private static final int DELAY_BETWEEN_PAGES = 100;
/*    */   private static final int DELAY_BETWEEN_FAILURES = 750;
/*    */   private final YggdrasilAuthenticationService authenticationService;
/*    */   
/*    */   public YggdrasilGameProfileRepository(YggdrasilAuthenticationService authenticationService) {
/* 27 */     this.authenticationService = authenticationService;
/*    */   }
/*    */ 
/*    */   
/*    */   public void findProfilesByNames(String[] names, Agent agent, ProfileLookupCallback callback) {
/* 32 */     Set<String> criteria = Sets.newHashSet();
/*    */     
/* 34 */     for (String name : names) {
/* 35 */       if (!Strings.isNullOrEmpty(name)) {
/* 36 */         criteria.add(name.toLowerCase());
/*    */       }
/*    */     } 
/*    */     
/* 40 */     int page = 0;
/*    */     
/* 42 */     label48: for (List<String> request : (Iterable<List<String>>)Iterables.partition(criteria, 2)) {
/* 43 */       int failCount = 0;
/*    */ 
/*    */       
/*    */       while (true) {
/* 47 */         boolean failed = false;
/*    */         
/*    */         try {
/* 50 */           ProfileSearchResultsResponse response = this.authenticationService.<ProfileSearchResultsResponse>makeRequest(HttpAuthenticationService.constantURL("https://api.mojang.com/profiles/" + agent.getName().toLowerCase()), request, ProfileSearchResultsResponse.class);
/* 51 */           failCount = 0;
/*    */           
/* 53 */           LOGGER.debug("Page {} returned {} results, parsing", new Object[] { Integer.valueOf(page), Integer.valueOf((response.getProfiles()).length) });
/*    */           
/* 55 */           Set<String> missing = Sets.newHashSet(request);
/* 56 */           for (GameProfile profile : response.getProfiles()) {
/* 57 */             LOGGER.debug("Successfully looked up profile {}", new Object[] { profile });
/* 58 */             missing.remove(profile.getName().toLowerCase());
/* 59 */             callback.onProfileLookupSucceeded(profile);
/*    */           } 
/*    */           
/* 62 */           for (String name : missing) {
/* 63 */             LOGGER.debug("Couldn't find profile {}", new Object[] { name });
/* 64 */             callback.onProfileLookupFailed(new GameProfile(null, name), new ProfileNotFoundException("Server did not find the requested profile"));
/*    */           } 
/*    */           
/*    */           try {
/* 68 */             Thread.sleep(100L);
/* 69 */           } catch (InterruptedException ignored) {}
/*    */         }
/* 71 */         catch (AuthenticationException e) {
/* 72 */           failCount++;
/*    */           
/* 74 */           if (failCount == 3) {
/* 75 */             for (String name : request) {
/* 76 */               LOGGER.debug("Couldn't find profile {} because of a server error", new Object[] { name });
/* 77 */               callback.onProfileLookupFailed(new GameProfile(null, name), (Exception)e);
/*    */             } 
/*    */           } else {
/*    */             try {
/* 81 */               Thread.sleep(750L);
/* 82 */             } catch (InterruptedException ignored) {}
/*    */             
/* 84 */             failed = true;
/*    */           } 
/*    */         } 
/* 87 */         if (!failed)
/*    */           continue label48; 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\YggdrasilGameProfileRepository.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */