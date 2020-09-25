/*     */ package net.minecraft.util.org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import net.minecraft.util.org.apache.commons.lang3.Validate;
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
/*     */ 
/*     */ 
/*     */ public abstract class TypeLiteral<T>
/*     */   implements Typed<T>
/*     */ {
/*  77 */   private static final TypeVariable<Class<TypeLiteral>> T = (TypeVariable)TypeLiteral.class.getTypeParameters()[0];
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
/*  90 */   public final Type value = (Type)Validate.notNull(TypeUtils.getTypeArguments(getClass(), TypeLiteral.class).get(T), "%s does not assign type parameter %s", new Object[] { getClass(), TypeUtils.toLongString(T) });
/*     */ 
/*     */ 
/*     */   
/*  94 */   private final String toString = String.format("%s<%s>", new Object[] { TypeLiteral.class.getSimpleName(), TypeUtils.toString(this.value) });
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object obj) {
/*  99 */     if (obj == this) {
/* 100 */       return true;
/*     */     }
/* 102 */     if (!(obj instanceof TypeLiteral)) {
/* 103 */       return false;
/*     */     }
/* 105 */     TypeLiteral<?> other = (TypeLiteral)obj;
/* 106 */     return TypeUtils.equals(this.value, other.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return 0x250 | this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return this.toString;
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 121 */     return this.value;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\reflect\TypeLiteral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */