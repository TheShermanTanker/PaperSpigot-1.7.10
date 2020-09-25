/*     */ package org.yaml.snakeyaml.resolver;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
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
/*     */ public class Resolver
/*     */ {
/*  32 */   public static final Pattern BOOL = Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   public static final Pattern FLOAT = Pattern.compile("^([-+]?(\\.[0-9]+|[0-9_]+(\\.[0-9_]*)?)([eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
/*     */   
/*  41 */   public static final Pattern INT = Pattern.compile("^(?:[-+]?0b[0-1_]+|[-+]?0[0-7_]+|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x[0-9a-fA-F_]+|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
/*     */   
/*  43 */   public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
/*  44 */   public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
/*  45 */   public static final Pattern EMPTY = Pattern.compile("^$");
/*  46 */   public static final Pattern TIMESTAMP = Pattern.compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
/*     */   
/*  48 */   public static final Pattern VALUE = Pattern.compile("^(?:=)$");
/*  49 */   public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");
/*     */   
/*  51 */   protected Map<Character, List<ResolverTuple>> yamlImplicitResolvers = new HashMap<Character, List<ResolverTuple>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Resolver(boolean respectDefaultImplicitScalars) {
/*  61 */     if (respectDefaultImplicitScalars) {
/*  62 */       addImplicitResolvers();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addImplicitResolvers() {
/*  67 */     addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     addImplicitResolver(Tag.INT, INT, "-+0123456789");
/*  74 */     addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
/*  75 */     addImplicitResolver(Tag.MERGE, MERGE, "<");
/*  76 */     addImplicitResolver(Tag.NULL, NULL, "~nN\000");
/*  77 */     addImplicitResolver(Tag.NULL, EMPTY, null);
/*  78 */     addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789");
/*  79 */     addImplicitResolver(Tag.VALUE, VALUE, "=");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     addImplicitResolver(Tag.YAML, YAML, "!&*");
/*     */   }
/*     */   
/*     */   public Resolver() {
/*  88 */     this(true);
/*     */   }
/*     */   
/*     */   public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
/*  92 */     if (first == null) {
/*  93 */       List<ResolverTuple> curr = this.yamlImplicitResolvers.get(null);
/*  94 */       if (curr == null) {
/*  95 */         curr = new ArrayList<ResolverTuple>();
/*  96 */         this.yamlImplicitResolvers.put(null, curr);
/*     */       } 
/*  98 */       curr.add(new ResolverTuple(tag, regexp));
/*     */     } else {
/* 100 */       char[] chrs = first.toCharArray();
/* 101 */       for (int i = 0, j = chrs.length; i < j; i++) {
/* 102 */         Character theC = Character.valueOf(chrs[i]);
/* 103 */         if (theC.charValue() == '\000')
/*     */         {
/* 105 */           theC = null;
/*     */         }
/* 107 */         List<ResolverTuple> curr = this.yamlImplicitResolvers.get(theC);
/* 108 */         if (curr == null) {
/* 109 */           curr = new ArrayList<ResolverTuple>();
/* 110 */           this.yamlImplicitResolvers.put(theC, curr);
/*     */         } 
/* 112 */         curr.add(new ResolverTuple(tag, regexp));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Tag resolve(NodeId kind, String value, boolean implicit) {
/* 118 */     if (kind == NodeId.scalar && implicit) {
/* 119 */       List<ResolverTuple> resolvers = null;
/* 120 */       if (value.length() == 0) {
/* 121 */         resolvers = this.yamlImplicitResolvers.get(Character.valueOf(false));
/*     */       } else {
/* 123 */         resolvers = this.yamlImplicitResolvers.get(Character.valueOf(value.charAt(0)));
/*     */       } 
/* 125 */       if (resolvers != null) {
/* 126 */         for (ResolverTuple v : resolvers) {
/* 127 */           Tag tag = v.getTag();
/* 128 */           Pattern regexp = v.getRegexp();
/* 129 */           if (regexp.matcher(value).matches()) {
/* 130 */             return tag;
/*     */           }
/*     */         } 
/*     */       }
/* 134 */       if (this.yamlImplicitResolvers.containsKey(null)) {
/* 135 */         for (ResolverTuple v : this.yamlImplicitResolvers.get(null)) {
/* 136 */           Tag tag = v.getTag();
/* 137 */           Pattern regexp = v.getRegexp();
/* 138 */           if (regexp.matcher(value).matches()) {
/* 139 */             return tag;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 144 */     switch (kind) {
/*     */       case scalar:
/* 146 */         return Tag.STR;
/*     */       case sequence:
/* 148 */         return Tag.SEQ;
/*     */     } 
/* 150 */     return Tag.MAP;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\resolver\Resolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */