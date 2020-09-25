/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Statistic
/*     */ {
/*     */   public final String name;
/*     */   private final IChatBaseComponent a;
/*     */   public boolean f;
/*     */   private final Counter b;
/*     */   private final IScoreboardCriteria c;
/*     */   private Class d;
/*     */   
/*     */   public Statistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Counter paramCounter) {
/*  23 */     this.name = paramString;
/*  24 */     this.a = paramIChatBaseComponent;
/*  25 */     this.b = paramCounter;
/*  26 */     this.c = new ScoreboardStatisticCriteria(this);
/*     */     
/*  28 */     IScoreboardCriteria.criteria.put(this.c.getName(), this.c);
/*     */   }
/*     */   
/*     */   public Statistic(String paramString, IChatBaseComponent paramIChatBaseComponent) {
/*  32 */     this(paramString, paramIChatBaseComponent, g);
/*     */   }
/*     */   
/*     */   public Statistic i() {
/*  36 */     this.f = true;
/*  37 */     return this;
/*     */   }
/*     */   
/*     */   public Statistic h() {
/*  41 */     if (StatisticList.a.containsKey(this.name)) {
/*  42 */       throw new RuntimeException("Duplicate stat id: \"" + ((Statistic)StatisticList.a.get(this.name)).a + "\" and \"" + this.a + "\" at id " + this.name);
/*     */     }
/*  44 */     StatisticList.stats.add(this);
/*  45 */     StatisticList.a.put(this.name, this);
/*     */     
/*  47 */     return this;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static NumberFormat k = NumberFormat.getIntegerInstance(Locale.US);
/*  59 */   public static Counter g = new UnknownCounter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static DecimalFormat l = new DecimalFormat("########0.00");
/*  67 */   public static Counter h = new TimeCounter();
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
/*  90 */   public static Counter i = new DistancesCounter();
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
/* 106 */   public static Counter j = new DamageCounter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatBaseComponent e() {
/* 114 */     IChatBaseComponent iChatBaseComponent = this.a.f();
/* 115 */     iChatBaseComponent.getChatModifier().setColor(EnumChatFormat.GRAY);
/* 116 */     iChatBaseComponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_ACHIEVEMENT, new ChatComponentText(this.name)));
/* 117 */     return iChatBaseComponent;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent j() {
/* 121 */     IChatBaseComponent iChatBaseComponent1 = e();
/* 122 */     IChatBaseComponent iChatBaseComponent2 = (new ChatComponentText("[")).addSibling(iChatBaseComponent1).a("]");
/* 123 */     iChatBaseComponent2.setChatModifier(iChatBaseComponent1.getChatModifier());
/* 124 */     return iChatBaseComponent2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 129 */     if (this == paramObject) return true; 
/* 130 */     if (paramObject == null || getClass() != paramObject.getClass()) return false;
/*     */     
/* 132 */     Statistic statistic = (Statistic)paramObject;
/*     */     
/* 134 */     return this.name.equals(statistic.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return "Stat{id=" + this.name + ", nameId=" + this.a + ", awardLocallyOnly=" + this.f + ", formatter=" + this.b + ", objectiveCriteria=" + this.c + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IScoreboardCriteria k() {
/* 154 */     return this.c;
/*     */   }
/*     */   
/*     */   public Class l() {
/* 158 */     return this.d;
/*     */   }
/*     */   
/*     */   public Statistic b(Class paramClass) {
/* 162 */     this.d = paramClass;
/* 163 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Statistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */