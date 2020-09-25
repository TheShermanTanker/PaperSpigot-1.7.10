/*     */ package net.minecraft.util.com.mojang.authlib.yggdrasil;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.mojang.authlib.Agent;
/*     */ import net.minecraft.util.com.mojang.authlib.AuthenticationService;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.HttpAuthenticationService;
/*     */ import net.minecraft.util.com.mojang.authlib.UserType;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.request.AuthenticationRequest;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.request.RefreshRequest;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.RefreshResponse;
/*     */ import net.minecraft.util.com.mojang.authlib.yggdrasil.response.User;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class YggdrasilUserAuthentication extends HttpUserAuthentication {
/*  21 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private static final String BASE_URL = "https://authserver.mojang.com/";
/*  23 */   private static final URL ROUTE_AUTHENTICATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/authenticate");
/*  24 */   private static final URL ROUTE_REFRESH = HttpAuthenticationService.constantURL("https://authserver.mojang.com/refresh");
/*  25 */   private static final URL ROUTE_VALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/validate");
/*  26 */   private static final URL ROUTE_INVALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/invalidate");
/*  27 */   private static final URL ROUTE_SIGNOUT = HttpAuthenticationService.constantURL("https://authserver.mojang.com/signout");
/*     */   
/*     */   private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";
/*     */   
/*     */   private final Agent agent;
/*     */   private GameProfile[] profiles;
/*     */   private String accessToken;
/*     */   private boolean isOnline;
/*     */   
/*     */   public YggdrasilUserAuthentication(YggdrasilAuthenticationService authenticationService, Agent agent) {
/*  37 */     super(authenticationService);
/*  38 */     this.agent = agent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLogIn() {
/*  43 */     return (!canPlayOnline() && StringUtils.isNotBlank(getUsername()) && (StringUtils.isNotBlank(getPassword()) || StringUtils.isNotBlank(getAuthenticatedToken())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void logIn() throws AuthenticationException {
/*  48 */     if (StringUtils.isBlank(getUsername())) {
/*  49 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*     */     
/*  52 */     if (StringUtils.isNotBlank(getAuthenticatedToken())) {
/*  53 */       logInWithToken();
/*  54 */     } else if (StringUtils.isNotBlank(getPassword())) {
/*  55 */       logInWithPassword();
/*     */     } else {
/*  57 */       throw new InvalidCredentialsException("Invalid password");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void logInWithPassword() throws AuthenticationException {
/*  62 */     if (StringUtils.isBlank(getUsername())) {
/*  63 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*  65 */     if (StringUtils.isBlank(getPassword())) {
/*  66 */       throw new InvalidCredentialsException("Invalid password");
/*     */     }
/*     */     
/*  69 */     LOGGER.info("Logging in with username & password");
/*     */     
/*  71 */     AuthenticationRequest request = new AuthenticationRequest(this, getUsername(), getPassword());
/*  72 */     AuthenticationResponse response = getAuthenticationService().<AuthenticationResponse>makeRequest(ROUTE_AUTHENTICATE, request, AuthenticationResponse.class);
/*     */     
/*  74 */     if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
/*  75 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/*  78 */     if (response.getSelectedProfile() != null) {
/*  79 */       setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*  80 */     } else if (ArrayUtils.isNotEmpty((Object[])response.getAvailableProfiles())) {
/*  81 */       setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*     */     } 
/*     */     
/*  84 */     User user = response.getUser();
/*     */     
/*  86 */     if (user != null && user.getId() != null) {
/*  87 */       setUserid(user.getId());
/*     */     } else {
/*  89 */       setUserid(getUsername());
/*     */     } 
/*     */     
/*  92 */     this.isOnline = true;
/*  93 */     this.accessToken = response.getAccessToken();
/*  94 */     this.profiles = response.getAvailableProfiles();
/*  95 */     setSelectedProfile(response.getSelectedProfile());
/*  96 */     getModifiableUserProperties().clear();
/*     */     
/*  98 */     updateUserProperties(user);
/*     */   }
/*     */   
/*     */   protected void updateUserProperties(User user) {
/* 102 */     if (user == null)
/*     */       return; 
/* 104 */     if (user.getProperties() != null) {
/* 105 */       getModifiableUserProperties().putAll((Multimap)user.getProperties());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void logInWithToken() throws AuthenticationException {
/* 110 */     if (StringUtils.isBlank(getUserID())) {
/* 111 */       if (StringUtils.isBlank(getUsername())) {
/* 112 */         setUserid(getUsername());
/*     */       } else {
/* 114 */         throw new InvalidCredentialsException("Invalid uuid & username");
/*     */       } 
/*     */     }
/* 117 */     if (StringUtils.isBlank(getAuthenticatedToken())) {
/* 118 */       throw new InvalidCredentialsException("Invalid access token");
/*     */     }
/*     */     
/* 121 */     LOGGER.info("Logging in with access token");
/*     */     
/* 123 */     RefreshRequest request = new RefreshRequest(this);
/* 124 */     RefreshResponse response = getAuthenticationService().<RefreshResponse>makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);
/*     */     
/* 126 */     if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
/* 127 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/* 130 */     if (response.getSelectedProfile() != null) {
/* 131 */       setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/* 132 */     } else if (ArrayUtils.isNotEmpty((Object[])response.getAvailableProfiles())) {
/* 133 */       setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*     */     } 
/*     */     
/* 136 */     if (response.getUser() != null && response.getUser().getId() != null) {
/* 137 */       setUserid(response.getUser().getId());
/*     */     } else {
/* 139 */       setUserid(getUsername());
/*     */     } 
/*     */     
/* 142 */     this.isOnline = true;
/* 143 */     this.accessToken = response.getAccessToken();
/* 144 */     this.profiles = response.getAvailableProfiles();
/* 145 */     setSelectedProfile(response.getSelectedProfile());
/* 146 */     getModifiableUserProperties().clear();
/*     */     
/* 148 */     updateUserProperties(response.getUser());
/*     */   }
/*     */ 
/*     */   
/*     */   public void logOut() {
/* 153 */     super.logOut();
/*     */     
/* 155 */     this.accessToken = null;
/* 156 */     this.profiles = null;
/* 157 */     this.isOnline = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile[] getAvailableProfiles() {
/* 162 */     return this.profiles;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoggedIn() {
/* 167 */     return StringUtils.isNotBlank(this.accessToken);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlayOnline() {
/* 172 */     return (isLoggedIn() && getSelectedProfile() != null && this.isOnline);
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectGameProfile(GameProfile profile) throws AuthenticationException {
/* 177 */     if (!isLoggedIn()) {
/* 178 */       throw new AuthenticationException("Cannot change game profile whilst not logged in");
/*     */     }
/* 180 */     if (getSelectedProfile() != null) {
/* 181 */       throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
/*     */     }
/* 183 */     if (profile == null || !ArrayUtils.contains((Object[])this.profiles, profile)) {
/* 184 */       throw new IllegalArgumentException("Invalid profile '" + profile + "'");
/*     */     }
/*     */     
/* 187 */     RefreshRequest request = new RefreshRequest(this, profile);
/* 188 */     RefreshResponse response = getAuthenticationService().<RefreshResponse>makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);
/*     */     
/* 190 */     if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
/* 191 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/* 194 */     this.isOnline = true;
/* 195 */     this.accessToken = response.getAccessToken();
/* 196 */     setSelectedProfile(response.getSelectedProfile());
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadFromStorage(Map<String, Object> credentials) {
/* 201 */     super.loadFromStorage(credentials);
/*     */     
/* 203 */     this.accessToken = String.valueOf(credentials.get("accessToken"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> saveForStorage() {
/* 208 */     Map<String, Object> result = super.saveForStorage();
/*     */     
/* 210 */     if (StringUtils.isNotBlank(getAuthenticatedToken())) {
/* 211 */       result.put("accessToken", getAuthenticatedToken());
/*     */     }
/*     */     
/* 214 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getSessionToken() {
/* 222 */     if (isLoggedIn() && getSelectedProfile() != null && canPlayOnline()) {
/* 223 */       return String.format("token:%s:%s", new Object[] { getAuthenticatedToken(), getSelectedProfile().getId() });
/*     */     }
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthenticatedToken() {
/* 231 */     return this.accessToken;
/*     */   }
/*     */   
/*     */   public Agent getAgent() {
/* 235 */     return this.agent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 240 */     return "YggdrasilAuthenticationService{agent=" + this.agent + ", profiles=" + Arrays.toString((Object[])this.profiles) + ", selectedProfile=" + getSelectedProfile() + ", username='" + getUsername() + '\'' + ", isLoggedIn=" + isLoggedIn() + ", userType=" + getUserType() + ", canPlayOnline=" + canPlayOnline() + ", accessToken='" + this.accessToken + '\'' + ", clientToken='" + getAuthenticationService().getClientToken() + '\'' + '}';
/*     */   }
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
/*     */   public YggdrasilAuthenticationService getAuthenticationService() {
/* 255 */     return (YggdrasilAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\mojang\authlib\yggdrasil\YggdrasilUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */