/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.com.google.common.collect.Sets;
/*    */ 
/*    */ public class AttributeMapServer
/*    */   extends AttributeMapBase {
/* 11 */   private final Set d = Sets.newHashSet();
/* 12 */   protected final Map c = new InsensitiveStringMap();
/*    */ 
/*    */   
/*    */   public AttributeModifiable c(IAttribute paramIAttribute) {
/* 16 */     return (AttributeModifiable)super.a(paramIAttribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public AttributeModifiable b(String paramString) {
/* 21 */     AttributeInstance attributeInstance = super.a(paramString);
/* 22 */     if (attributeInstance == null) attributeInstance = (AttributeInstance)this.c.get(paramString); 
/* 23 */     return (AttributeModifiable)attributeInstance;
/*    */   }
/*    */ 
/*    */   
/*    */   public AttributeInstance b(IAttribute paramIAttribute) {
/* 28 */     if (this.b.containsKey(paramIAttribute.getName())) throw new IllegalArgumentException("Attribute is already registered!");
/*    */     
/* 30 */     AttributeModifiable attributeModifiable = new AttributeModifiable(this, paramIAttribute);
/* 31 */     this.b.put(paramIAttribute.getName(), attributeModifiable);
/* 32 */     if (paramIAttribute instanceof AttributeRanged && ((AttributeRanged)paramIAttribute).f() != null) {
/* 33 */       this.c.put(((AttributeRanged)paramIAttribute).f(), attributeModifiable);
/*    */     }
/* 35 */     this.a.put(paramIAttribute, attributeModifiable);
/*    */     
/* 37 */     return attributeModifiable;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(AttributeModifiable paramAttributeModifiable) {
/* 42 */     if (paramAttributeModifiable.getAttribute().c()) {
/* 43 */       this.d.add(paramAttributeModifiable);
/*    */     }
/*    */   }
/*    */   
/*    */   public Set getAttributes() {
/* 48 */     return this.d;
/*    */   }
/*    */   
/*    */   public Collection c() {
/* 52 */     HashSet<AttributeInstance> hashSet = Sets.newHashSet();
/*    */     
/* 54 */     for (AttributeInstance attributeInstance : a()) {
/* 55 */       if (attributeInstance.getAttribute().c()) {
/* 56 */         hashSet.add(attributeInstance);
/*    */       }
/*    */     } 
/*    */     
/* 60 */     return hashSet;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AttributeMapServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */