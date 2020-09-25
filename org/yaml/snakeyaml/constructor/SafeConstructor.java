/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
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
/*     */ public class SafeConstructor
/*     */   extends BaseConstructor
/*     */ {
/*  47 */   public static final ConstructUndefined undefinedConstructor = new ConstructUndefined();
/*     */   
/*     */   public SafeConstructor() {
/*  50 */     this.yamlConstructors.put(Tag.NULL, new ConstructYamlNull());
/*  51 */     this.yamlConstructors.put(Tag.BOOL, new ConstructYamlBool());
/*  52 */     this.yamlConstructors.put(Tag.INT, new ConstructYamlInt());
/*  53 */     this.yamlConstructors.put(Tag.FLOAT, new ConstructYamlFloat());
/*  54 */     this.yamlConstructors.put(Tag.BINARY, new ConstructYamlBinary());
/*  55 */     this.yamlConstructors.put(Tag.TIMESTAMP, new ConstructYamlTimestamp());
/*  56 */     this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
/*  57 */     this.yamlConstructors.put(Tag.PAIRS, new ConstructYamlPairs());
/*  58 */     this.yamlConstructors.put(Tag.SET, new ConstructYamlSet());
/*  59 */     this.yamlConstructors.put(Tag.STR, new ConstructYamlStr());
/*  60 */     this.yamlConstructors.put(Tag.SEQ, new ConstructYamlSeq());
/*  61 */     this.yamlConstructors.put(Tag.MAP, new ConstructYamlMap());
/*  62 */     this.yamlConstructors.put(null, undefinedConstructor);
/*  63 */     this.yamlClassConstructors.put(NodeId.scalar, undefinedConstructor);
/*  64 */     this.yamlClassConstructors.put(NodeId.sequence, undefinedConstructor);
/*  65 */     this.yamlClassConstructors.put(NodeId.mapping, undefinedConstructor);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void flattenMapping(MappingNode node) {
/*  70 */     if (node.isMerged()) {
/*  71 */       node.setValue(mergeNode(node, true, new HashMap<Object, Integer>(), new ArrayList<NodeTuple>()));
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<NodeTuple> mergeNode(MappingNode node, boolean isPreffered, Map<Object, Integer> key2index, List<NodeTuple> values) {
/*  92 */     List<NodeTuple> nodeValue = node.getValue();
/*     */     
/*  94 */     Collections.reverse(nodeValue);
/*  95 */     for (Iterator<NodeTuple> iter = nodeValue.iterator(); iter.hasNext(); ) {
/*  96 */       NodeTuple nodeTuple = iter.next();
/*  97 */       Node keyNode = nodeTuple.getKeyNode();
/*  98 */       Node valueNode = nodeTuple.getValueNode();
/*  99 */       if (keyNode.getTag().equals(Tag.MERGE)) {
/* 100 */         MappingNode mn; SequenceNode sn; List<Node> vals; iter.remove();
/* 101 */         switch (valueNode.getNodeId()) {
/*     */           case mapping:
/* 103 */             mn = (MappingNode)valueNode;
/* 104 */             mergeNode(mn, false, key2index, values);
/*     */             continue;
/*     */           case sequence:
/* 107 */             sn = (SequenceNode)valueNode;
/* 108 */             vals = sn.getValue();
/* 109 */             for (Node subnode : vals) {
/* 110 */               if (!(subnode instanceof MappingNode)) {
/* 111 */                 throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping for merging, but found " + subnode.getNodeId(), subnode.getStartMark());
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 116 */               MappingNode mnode = (MappingNode)subnode;
/* 117 */               mergeNode(mnode, false, key2index, values);
/*     */             } 
/*     */             continue;
/*     */         } 
/* 121 */         throw new ConstructorException("while constructing a mapping", node.getStartMark(), "expected a mapping or list of mappings for merging, but found " + valueNode.getNodeId(), valueNode.getStartMark());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       Object key = constructObject(keyNode);
/* 129 */       if (!key2index.containsKey(key)) {
/* 130 */         values.add(nodeTuple);
/*     */         
/* 132 */         key2index.put(key, Integer.valueOf(values.size() - 1)); continue;
/* 133 */       }  if (isPreffered)
/*     */       {
/*     */         
/* 136 */         values.set(((Integer)key2index.get(key)).intValue(), nodeTuple);
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return values;
/*     */   }
/*     */   
/*     */   protected void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping) {
/* 144 */     flattenMapping(node);
/* 145 */     super.constructMapping2ndStep(node, mapping);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void constructSet2ndStep(MappingNode node, Set<Object> set) {
/* 150 */     flattenMapping(node);
/* 151 */     super.constructSet2ndStep(node, set);
/*     */   }
/*     */   
/*     */   public class ConstructYamlNull extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 156 */       SafeConstructor.this.constructScalar((ScalarNode)node);
/* 157 */       return null;
/*     */     }
/*     */   }
/*     */   
/* 161 */   private static final Map<String, Boolean> BOOL_VALUES = new HashMap<String, Boolean>();
/*     */   static {
/* 163 */     BOOL_VALUES.put("yes", Boolean.TRUE);
/* 164 */     BOOL_VALUES.put("no", Boolean.FALSE);
/* 165 */     BOOL_VALUES.put("true", Boolean.TRUE);
/* 166 */     BOOL_VALUES.put("false", Boolean.FALSE);
/* 167 */     BOOL_VALUES.put("on", Boolean.TRUE);
/* 168 */     BOOL_VALUES.put("off", Boolean.FALSE);
/*     */   }
/*     */   
/*     */   public class ConstructYamlBool extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 173 */       String val = (String)SafeConstructor.this.constructScalar((ScalarNode)node);
/* 174 */       return SafeConstructor.BOOL_VALUES.get(val.toLowerCase());
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlInt extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 180 */       String value = SafeConstructor.this.constructScalar((ScalarNode)node).toString().replaceAll("_", "");
/* 181 */       int sign = 1;
/* 182 */       char first = value.charAt(0);
/* 183 */       if (first == '-') {
/* 184 */         sign = -1;
/* 185 */         value = value.substring(1);
/* 186 */       } else if (first == '+') {
/* 187 */         value = value.substring(1);
/*     */       } 
/* 189 */       int base = 10;
/* 190 */       if ("0".equals(value))
/* 191 */         return Integer.valueOf(0); 
/* 192 */       if (value.startsWith("0b"))
/* 193 */       { value = value.substring(2);
/* 194 */         base = 2; }
/* 195 */       else if (value.startsWith("0x"))
/* 196 */       { value = value.substring(2);
/* 197 */         base = 16; }
/* 198 */       else if (value.startsWith("0"))
/* 199 */       { value = value.substring(1);
/* 200 */         base = 8; }
/* 201 */       else { if (value.indexOf(':') != -1) {
/* 202 */           String[] digits = value.split(":");
/* 203 */           int bes = 1;
/* 204 */           int val = 0;
/* 205 */           for (int i = 0, j = digits.length; i < j; i++) {
/* 206 */             val = (int)(val + Long.parseLong(digits[j - i - 1]) * bes);
/* 207 */             bes *= 60;
/*     */           } 
/* 209 */           return SafeConstructor.this.createNumber(sign, String.valueOf(val), 10);
/*     */         } 
/* 211 */         return SafeConstructor.this.createNumber(sign, value, 10); }
/*     */       
/* 213 */       return SafeConstructor.this.createNumber(sign, value, base);
/*     */     }
/*     */   }
/*     */   
/*     */   private Number createNumber(int sign, String number, int radix) {
/*     */     Number number1;
/* 219 */     if (sign < 0) {
/* 220 */       number = "-" + number;
/*     */     }
/*     */     try {
/* 223 */       number1 = Integer.valueOf(number, radix);
/* 224 */     } catch (NumberFormatException e) {
/*     */       try {
/* 226 */         number1 = Long.valueOf(number, radix);
/* 227 */       } catch (NumberFormatException e1) {
/* 228 */         number1 = new BigInteger(number, radix);
/*     */       } 
/*     */     } 
/* 231 */     return number1;
/*     */   }
/*     */   
/*     */   public class ConstructYamlFloat extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 236 */       String value = SafeConstructor.this.constructScalar((ScalarNode)node).toString().replaceAll("_", "");
/* 237 */       int sign = 1;
/* 238 */       char first = value.charAt(0);
/* 239 */       if (first == '-') {
/* 240 */         sign = -1;
/* 241 */         value = value.substring(1);
/* 242 */       } else if (first == '+') {
/* 243 */         value = value.substring(1);
/*     */       } 
/* 245 */       String valLower = value.toLowerCase();
/* 246 */       if (".inf".equals(valLower))
/* 247 */         return new Double((sign == -1) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY); 
/* 248 */       if (".nan".equals(valLower))
/* 249 */         return new Double(Double.NaN); 
/* 250 */       if (value.indexOf(':') != -1) {
/* 251 */         String[] digits = value.split(":");
/* 252 */         int bes = 1;
/* 253 */         double val = 0.0D;
/* 254 */         for (int i = 0, j = digits.length; i < j; i++) {
/* 255 */           val += Double.parseDouble(digits[j - i - 1]) * bes;
/* 256 */           bes *= 60;
/*     */         } 
/* 258 */         return new Double(sign * val);
/*     */       } 
/* 260 */       Double d = Double.valueOf(value);
/* 261 */       return new Double(d.doubleValue() * sign);
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlBinary
/*     */     extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 268 */       byte[] decoded = Base64Coder.decode(SafeConstructor.this.constructScalar((ScalarNode)node).toString().toCharArray());
/*     */       
/* 270 */       return decoded;
/*     */     }
/*     */   }
/*     */   
/* 274 */   private static final Pattern TIMESTAMP_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)(?:(?:[Tt]|[ \t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(?:\\.([0-9]*))?(?:[ \t]*(?:Z|([-+][0-9][0-9]?)(?::([0-9][0-9])?)?))?)?$");
/*     */   
/* 276 */   private static final Pattern YMD_REGEXP = Pattern.compile("^([0-9][0-9][0-9][0-9])-([0-9][0-9]?)-([0-9][0-9]?)$");
/*     */   
/*     */   public static class ConstructYamlTimestamp
/*     */     extends AbstractConstruct {
/*     */     private Calendar calendar;
/*     */     
/*     */     public Calendar getCalendar() {
/* 283 */       return this.calendar;
/*     */     }
/*     */     public Object construct(Node node) {
/*     */       TimeZone timeZone;
/* 287 */       ScalarNode scalar = (ScalarNode)node;
/* 288 */       String nodeValue = scalar.getValue();
/* 289 */       Matcher match = SafeConstructor.YMD_REGEXP.matcher(nodeValue);
/* 290 */       if (match.matches()) {
/* 291 */         String str1 = match.group(1);
/* 292 */         String str2 = match.group(2);
/* 293 */         String str3 = match.group(3);
/* 294 */         this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
/* 295 */         this.calendar.clear();
/* 296 */         this.calendar.set(1, Integer.parseInt(str1));
/*     */         
/* 298 */         this.calendar.set(2, Integer.parseInt(str2) - 1);
/* 299 */         this.calendar.set(5, Integer.parseInt(str3));
/* 300 */         return this.calendar.getTime();
/*     */       } 
/* 302 */       match = SafeConstructor.TIMESTAMP_REGEXP.matcher(nodeValue);
/* 303 */       if (!match.matches()) {
/* 304 */         throw new YAMLException("Unexpected timestamp: " + nodeValue);
/*     */       }
/* 306 */       String year_s = match.group(1);
/* 307 */       String month_s = match.group(2);
/* 308 */       String day_s = match.group(3);
/* 309 */       String hour_s = match.group(4);
/* 310 */       String min_s = match.group(5);
/*     */       
/* 312 */       String seconds = match.group(6);
/* 313 */       String millis = match.group(7);
/* 314 */       if (millis != null) {
/* 315 */         seconds = seconds + "." + millis;
/*     */       }
/* 317 */       double fractions = Double.parseDouble(seconds);
/* 318 */       int sec_s = (int)Math.round(Math.floor(fractions));
/* 319 */       int usec = (int)Math.round((fractions - sec_s) * 1000.0D);
/*     */       
/* 321 */       String timezoneh_s = match.group(8);
/* 322 */       String timezonem_s = match.group(9);
/*     */       
/* 324 */       if (timezoneh_s != null) {
/* 325 */         String time = (timezonem_s != null) ? (":" + timezonem_s) : "00";
/* 326 */         timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
/*     */       } else {
/*     */         
/* 329 */         timeZone = TimeZone.getTimeZone("UTC");
/*     */       } 
/* 331 */       this.calendar = Calendar.getInstance(timeZone);
/* 332 */       this.calendar.set(1, Integer.parseInt(year_s));
/*     */       
/* 334 */       this.calendar.set(2, Integer.parseInt(month_s) - 1);
/* 335 */       this.calendar.set(5, Integer.parseInt(day_s));
/* 336 */       this.calendar.set(11, Integer.parseInt(hour_s));
/* 337 */       this.calendar.set(12, Integer.parseInt(min_s));
/* 338 */       this.calendar.set(13, sec_s);
/* 339 */       this.calendar.set(14, usec);
/* 340 */       return this.calendar.getTime();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class ConstructYamlOmap
/*     */     extends AbstractConstruct
/*     */   {
/*     */     public Object construct(Node node) {
/* 349 */       Map<Object, Object> omap = new LinkedHashMap<Object, Object>();
/* 350 */       if (!(node instanceof SequenceNode)) {
/* 351 */         throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
/*     */       }
/*     */ 
/*     */       
/* 355 */       SequenceNode snode = (SequenceNode)node;
/* 356 */       for (Node subnode : snode.getValue()) {
/* 357 */         if (!(subnode instanceof MappingNode)) {
/* 358 */           throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 362 */         MappingNode mnode = (MappingNode)subnode;
/* 363 */         if (mnode.getValue().size() != 1) {
/* 364 */           throw new ConstructorException("while constructing an ordered map", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 368 */         Node keyNode = ((NodeTuple)mnode.getValue().get(0)).getKeyNode();
/* 369 */         Node valueNode = ((NodeTuple)mnode.getValue().get(0)).getValueNode();
/* 370 */         Object key = SafeConstructor.this.constructObject(keyNode);
/* 371 */         Object value = SafeConstructor.this.constructObject(valueNode);
/* 372 */         omap.put(key, value);
/*     */       } 
/* 374 */       return omap;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class ConstructYamlPairs
/*     */     extends AbstractConstruct
/*     */   {
/*     */     public Object construct(Node node) {
/* 383 */       if (!(node instanceof SequenceNode)) {
/* 384 */         throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
/*     */       }
/*     */       
/* 387 */       SequenceNode snode = (SequenceNode)node;
/* 388 */       List<Object[]> pairs = new ArrayList(snode.getValue().size());
/* 389 */       for (Node subnode : snode.getValue()) {
/* 390 */         if (!(subnode instanceof MappingNode)) {
/* 391 */           throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 395 */         MappingNode mnode = (MappingNode)subnode;
/* 396 */         if (mnode.getValue().size() != 1) {
/* 397 */           throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
/*     */         }
/*     */ 
/*     */         
/* 401 */         Node keyNode = ((NodeTuple)mnode.getValue().get(0)).getKeyNode();
/* 402 */         Node valueNode = ((NodeTuple)mnode.getValue().get(0)).getValueNode();
/* 403 */         Object key = SafeConstructor.this.constructObject(keyNode);
/* 404 */         Object value = SafeConstructor.this.constructObject(valueNode);
/* 405 */         pairs.add(new Object[] { key, value });
/*     */       } 
/* 407 */       return pairs;
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlSet implements Construct {
/*     */     public Object construct(Node node) {
/* 413 */       if (node.isTwoStepsConstruction()) {
/* 414 */         return SafeConstructor.this.createDefaultSet();
/*     */       }
/* 416 */       return SafeConstructor.this.constructSet((MappingNode)node);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 422 */       if (node.isTwoStepsConstruction()) {
/* 423 */         SafeConstructor.this.constructSet2ndStep((MappingNode)node, (Set<Object>)object);
/*     */       } else {
/* 425 */         throw new YAMLException("Unexpected recursive set structure. Node: " + node);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlStr extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 432 */       return SafeConstructor.this.constructScalar((ScalarNode)node);
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlSeq implements Construct {
/*     */     public Object construct(Node node) {
/* 438 */       SequenceNode seqNode = (SequenceNode)node;
/* 439 */       if (node.isTwoStepsConstruction()) {
/* 440 */         return SafeConstructor.this.createDefaultList(seqNode.getValue().size());
/*     */       }
/* 442 */       return SafeConstructor.this.constructSequence(seqNode);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object data) {
/* 448 */       if (node.isTwoStepsConstruction()) {
/* 449 */         SafeConstructor.this.constructSequenceStep2((SequenceNode)node, (List)data);
/*     */       } else {
/* 451 */         throw new YAMLException("Unexpected recursive sequence structure. Node: " + node);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public class ConstructYamlMap implements Construct {
/*     */     public Object construct(Node node) {
/* 458 */       if (node.isTwoStepsConstruction()) {
/* 459 */         return SafeConstructor.this.createDefaultMap();
/*     */       }
/* 461 */       return SafeConstructor.this.constructMapping((MappingNode)node);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/* 467 */       if (node.isTwoStepsConstruction()) {
/* 468 */         SafeConstructor.this.constructMapping2ndStep((MappingNode)node, (Map<Object, Object>)object);
/*     */       } else {
/* 470 */         throw new YAMLException("Unexpected recursive mapping structure. Node: " + node);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class ConstructUndefined extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/* 477 */       throw new ConstructorException(null, null, "could not determine a constructor for the tag " + node.getTag(), node.getStartMark());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\constructor\SafeConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */