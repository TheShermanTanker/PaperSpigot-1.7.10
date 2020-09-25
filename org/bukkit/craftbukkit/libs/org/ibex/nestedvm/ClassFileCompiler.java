/*      */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;
/*      */ 
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.CGConst;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.ClassFile;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.MethodGen;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.Type;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassFileCompiler
/*      */   extends Compiler
/*      */   implements CGConst
/*      */ {
/*      */   private static final boolean OPTIMIZE_CP = true;
/*      */   private OutputStream os;
/*      */   private File outDir;
/*   45 */   private PrintStream warn = System.err; private final Type.Class me; private ClassFile cg; private MethodGen clinit; private MethodGen init; private static int initDataCount; private int startOfMethod; private int endOfMethod; private MethodGen.PhantomTarget returnTarget; private MethodGen.PhantomTarget defaultTarget; private MethodGen.PhantomTarget[] insnTargets;
/*      */   private MethodGen mg;
/*      */   private static final int UNREACHABLE = 1;
/*      */   private static final int SKIP_NEXT = 2;
/*      */   private boolean textDone;
/*      */   
/*      */   public ClassFileCompiler(String paramString1, String paramString2, OutputStream paramOutputStream) throws IOException {
/*   52 */     this((Seekable)new Seekable.File(paramString1), paramString2, paramOutputStream);
/*      */   } public ClassFileCompiler(Seekable paramSeekable, String paramString, OutputStream paramOutputStream) throws IOException {
/*   54 */     this(paramSeekable, paramString);
/*   55 */     if (paramOutputStream == null) throw new NullPointerException(); 
/*   56 */     this.os = paramOutputStream;
/*      */   }
/*      */   public ClassFileCompiler(Seekable paramSeekable, String paramString, File paramFile) throws IOException {
/*   59 */     this(paramSeekable, paramString);
/*   60 */     if (paramFile == null) throw new NullPointerException(); 
/*   61 */     this.outDir = paramFile;
/*      */   }
/*      */   
/*   64 */   private ClassFileCompiler(Seekable paramSeekable, String paramString) throws IOException { super(paramSeekable, paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  441 */     this.startOfMethod = 0;
/*  442 */     this.endOfMethod = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1749 */     this.regLocalMapping = new int[67];
/* 1750 */     this.regLocalWritten = new boolean[67];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1831 */     this.preSetRegStack = new int[8]; this.me = Type.Class.instance(this.fullClassName); }
/*      */   public void setWarnWriter(PrintStream paramPrintStream) { this.warn = paramPrintStream; }
/*      */   protected void _go() throws Compiler.Exn, IOException { try { __go(); } catch (org.bukkit.craftbukkit.libs.org.ibex.classgen.ClassFile.Exn exn) { exn.printStackTrace(this.warn); throw new Compiler.Exn("Class generation exception: " + exn.toString()); }  }
/*      */   private void __go() throws Compiler.Exn, IOException { if (!this.pruneCases) throw new Compiler.Exn("-o prunecases MUST be enabled for ClassFileCompiler");  Type.Class clazz1 = Type.Class.instance(this.runtimeClass); this.cg = new ClassFile(this.me, clazz1, 49); if (this.source != null) this.cg.setSourceFile(this.source);  this.cg.addField("pc", Type.INT, 2); this.cg.addField("hi", Type.INT, 2); this.cg.addField("lo", Type.INT, 2); this.cg.addField("fcsr", Type.INT, 2); int i; for (i = 1; i < 32; ) { this.cg.addField("r" + i, Type.INT, 2); i++; }  for (i = 0; i < 32; ) { this.cg.addField("f" + i, this.singleFloat ? Type.FLOAT : Type.INT, 2); i++; }  this.clinit = this.cg.addMethod("<clinit>", Type.VOID, Type.NO_ARGS, 10); this.init = this.cg.addMethod("<init>", Type.VOID, Type.NO_ARGS, 1); this.init.add((byte)42); this.init.add((byte)18, this.pageSize); this.init.add((byte)18, this.totalPages); this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT })); this.init.add((byte)-79); this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.BOOLEAN }, 1); this.init.add((byte)42); this.init.add((byte)18, this.pageSize); this.init.add((byte)18, this.totalPages); this.init.add((byte)27); this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN })); this.init.add((byte)-79); this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT }, 1); this.init.add((byte)42); this.init.add((byte)27); this.init.add((byte)28); this.init.add((byte)3); this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN })); this.init.add((byte)-79); this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }, 1); this.init.add((byte)42); this.init.add((byte)27); this.init.add((byte)28); this.init.add((byte)29); this.init.add((byte)-73, clazz1.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN })); if (this.onePage) { this.cg.addField("page", (Type)Type.INT.makeArray(), 18); this.init.add((byte)42); this.init.add((byte)89); this.init.add((byte)-76, this.me.field("readPages", (Type)Type.INT.makeArray(2))); this.init.add((byte)18, 0); this.init.add((byte)50); this.init.add((byte)-75, this.me.field("page", (Type)Type.INT.makeArray())); }  if (this.supportCall) this.cg.addField("symbols", (Type)Type.Class.instance(this.hashClass), 26);  i = 0; for (byte b1 = 0; b1 < this.elf.sheaders.length; b1++) { ELF.SHeader sHeader1 = this.elf.sheaders[b1]; String str = sHeader1.name; if (sHeader1.addr != 0) { i = Math.max(i, sHeader1.addr + sHeader1.size); if (str.equals(".text")) { emitText(sHeader1.addr, new DataInputStream(sHeader1.getInputStream()), sHeader1.size); } else if (str.equals(".data") || str.equals(".sdata") || str.equals(".rodata") || str.equals(".ctors") || str.equals(".dtors")) { emitData(sHeader1.addr, new DataInputStream(sHeader1.getInputStream()), sHeader1.size, str.equals(".rodata")); } else if (str.equals(".bss") || str.equals(".sbss")) { emitBSS(sHeader1.addr, sHeader1.size); } else { throw new Compiler.Exn("Unknown segment: " + str); }  }  }  this.init.add((byte)-79); if (this.supportCall) { Type.Class clazz = Type.Class.instance(this.hashClass); this.clinit.add((byte)-69, clazz); this.clinit.add((byte)89); this.clinit.add((byte)89); this.clinit.add((byte)-73, clazz.method("<init>", Type.VOID, Type.NO_ARGS)); this.clinit.add((byte)-77, this.me.field("symbols", (Type)clazz)); ELF.Symbol[] arrayOfSymbol = (this.elf.getSymtab()).symbols; for (byte b = 0; b < arrayOfSymbol.length; b++) { ELF.Symbol symbol = arrayOfSymbol[b]; if (symbol.type == 2 && symbol.binding == 1 && (symbol.name.equals("_call_helper") || !symbol.name.startsWith("_"))) { this.clinit.add((byte)89); this.clinit.add((byte)18, symbol.name); this.clinit.add((byte)-69, Type.INTEGER_OBJECT); this.clinit.add((byte)89); this.clinit.add((byte)18, symbol.addr); this.clinit.add((byte)-73, Type.INTEGER_OBJECT.method("<init>", Type.VOID, new Type[] { Type.INT })); this.clinit.add((byte)-74, clazz.method("put", (Type)Type.OBJECT, new Type[] { (Type)Type.OBJECT, (Type)Type.OBJECT })); this.clinit.add((byte)87); }  }  this.clinit.add((byte)87); }  this.clinit.add((byte)-79); ELF.SHeader sHeader = this.elf.sectionWithName(".text"); MethodGen methodGen1 = this.cg.addMethod("trampoline", Type.VOID, Type.NO_ARGS, 2); int j = methodGen1.size(); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("state", Type.INT)); methodGen1.add((byte)-103, methodGen1.size() + 2); methodGen1.add((byte)-79); methodGen1.add((byte)42); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("pc", Type.INT)); methodGen1.add((byte)18, this.methodShift); methodGen1.add((byte)124); int k = sHeader.addr >>> this.methodShift; int m = sHeader.addr + sHeader.size + this.maxBytesPerMethod - 1 >>> this.methodShift; MethodGen.Switch.Table table = new MethodGen.Switch.Table(k, m - 1); methodGen1.add((byte)-86, table); for (int n = k; n < m; n++) { table.setTargetForVal(n, methodGen1.size()); methodGen1.add((byte)-73, this.me.method("run_" + toHex(n << this.methodShift), Type.VOID, Type.NO_ARGS)); methodGen1.add((byte)-89, j); }  table.setDefaultTarget(methodGen1.size()); methodGen1.add((byte)87); methodGen1.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException")); methodGen1.add((byte)89); methodGen1.add((byte)-69, Type.STRINGBUFFER); methodGen1.add((byte)89); methodGen1.add((byte)18, "Jumped to invalid address in trampoline (r2: "); methodGen1.add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { (Type)Type.STRING })); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("r2", Type.INT)); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", (Type)Type.STRINGBUFFER, new Type[] { Type.INT })); methodGen1.add((byte)18, " pc: "); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", (Type)Type.STRINGBUFFER, new Type[] { (Type)Type.STRING })); methodGen1.add((byte)42); methodGen1.add((byte)-76, this.me.field("pc", Type.INT)); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", (Type)Type.STRINGBUFFER, new Type[] { Type.INT })); methodGen1.add((byte)18, ")"); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("append", (Type)Type.STRINGBUFFER, new Type[] { (Type)Type.STRING })); methodGen1.add((byte)-74, Type.STRINGBUFFER.method("toString", (Type)Type.STRING, Type.NO_ARGS)); methodGen1.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { (Type)Type.STRING })); methodGen1.add((byte)-65); addConstReturnMethod("gp", this.gp.addr); addConstReturnMethod("entryPoint", this.elf.header.entry); addConstReturnMethod("heapStart", i); if (this.userInfo != null) { addConstReturnMethod("userInfoBase", this.userInfo.addr); addConstReturnMethod("userInfoSize", this.userInfo.size); }  if (this.supportCall) { Type.Class clazz = Type.Class.instance(this.hashClass); MethodGen methodGen = this.cg.addMethod("lookupSymbol", Type.INT, new Type[] { (Type)Type.STRING }, 4); methodGen.add((byte)-78, this.me.field("symbols", (Type)clazz)); methodGen.add((byte)43); methodGen.add((byte)-74, clazz.method("get", (Type)Type.OBJECT, new Type[] { (Type)Type.OBJECT })); methodGen.add((byte)89); int i4 = methodGen.add((byte)-58); methodGen.add((byte)-64, Type.INTEGER_OBJECT); methodGen.add((byte)-74, Type.INTEGER_OBJECT.method("intValue", Type.INT, Type.NO_ARGS)); methodGen.add((byte)-84); methodGen.setArg(i4, methodGen.size()); methodGen.add((byte)87); methodGen.add((byte)2); methodGen.add((byte)-84); }  Type.Class clazz2 = Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$CPUState"); MethodGen methodGen2 = this.cg.addMethod("setCPUState", Type.VOID, new Type[] { (Type)clazz2 }, 4); MethodGen methodGen3 = this.cg.addMethod("getCPUState", Type.VOID, new Type[] { (Type)clazz2 }, 4); methodGen2.add((byte)43); methodGen3.add((byte)43); methodGen2.add((byte)-76, clazz2.field("r", (Type)Type.INT.makeArray())); methodGen3.add((byte)-76, clazz2.field("r", (Type)Type.INT.makeArray())); methodGen2.add((byte)77); methodGen3.add((byte)77); byte b2; for (b2 = 1; b2 < 32; b2++) { methodGen2.add((byte)42); methodGen2.add((byte)44); methodGen2.add((byte)18, b2); methodGen2.add((byte)46); methodGen2.add((byte)-75, this.me.field("r" + b2, Type.INT)); methodGen3.add((byte)44); methodGen3.add((byte)18, b2); methodGen3.add((byte)42); methodGen3.add((byte)-76, this.me.field("r" + b2, Type.INT)); methodGen3.add((byte)79); }  methodGen2.add((byte)43); methodGen3.add((byte)43); methodGen2.add((byte)-76, clazz2.field("f", (Type)Type.INT.makeArray())); methodGen3.add((byte)-76, clazz2.field("f", (Type)Type.INT.makeArray())); methodGen2.add((byte)77); methodGen3.add((byte)77); for (b2 = 0; b2 < 32; b2++) { methodGen2.add((byte)42); methodGen2.add((byte)44); methodGen2.add((byte)18, b2); methodGen2.add((byte)46); if (this.singleFloat) methodGen2.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));  methodGen2.add((byte)-75, this.me.field("f" + b2, this.singleFloat ? Type.FLOAT : Type.INT)); methodGen3.add((byte)44); methodGen3.add((byte)18, b2); methodGen3.add((byte)42); methodGen3.add((byte)-76, this.me.field("f" + b2, this.singleFloat ? Type.FLOAT : Type.INT)); if (this.singleFloat) methodGen3.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT }));  methodGen3.add((byte)79); }  String[] arrayOfString = { "hi", "lo", "fcsr", "pc" }; for (byte b3 = 0; b3 < arrayOfString.length; b3++) { methodGen2.add((byte)42); methodGen2.add((byte)43); methodGen2.add((byte)-76, clazz2.field(arrayOfString[b3], Type.INT)); methodGen2.add((byte)-75, this.me.field(arrayOfString[b3], Type.INT)); methodGen3.add((byte)43); methodGen3.add((byte)42); methodGen3.add((byte)-76, this.me.field(arrayOfString[b3], Type.INT)); methodGen3.add((byte)-75, clazz2.field(arrayOfString[b3], Type.INT)); }  methodGen2.add((byte)-79); methodGen3.add((byte)-79); MethodGen methodGen4 = this.cg.addMethod("_execute", Type.VOID, Type.NO_ARGS, 4); int i1 = methodGen4.size(); methodGen4.add((byte)42); methodGen4.add((byte)-73, this.me.method("trampoline", Type.VOID, Type.NO_ARGS)); int i2 = methodGen4.size(); methodGen4.add((byte)-79); int i3 = methodGen4.size(); methodGen4.add((byte)76); methodGen4.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$FaultException")); methodGen4.add((byte)89); methodGen4.add((byte)43); methodGen4.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$FaultException").method("<init>", Type.VOID, new Type[] { (Type)Type.Class.instance("java.lang.RuntimeException") })); methodGen4.add((byte)-65); methodGen4.addExceptionHandler(i1, i2, i3, Type.Class.instance("java.lang.RuntimeException")); methodGen4.addThrow(Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException")); MethodGen methodGen5 = this.cg.addMethod("main", Type.VOID, new Type[] { (Type)Type.STRING.makeArray() }, 9); methodGen5.add((byte)-69, this.me); methodGen5.add((byte)89); methodGen5.add((byte)-73, this.me.method("<init>", Type.VOID, Type.NO_ARGS)); methodGen5.add((byte)18, this.fullClassName); methodGen5.add((byte)42); if (this.unixRuntime) { Type.Class clazz = Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.UnixRuntime"); methodGen5.add((byte)-72, clazz.method("runAndExec", Type.INT, new Type[] { (Type)clazz, (Type)Type.STRING, (Type)Type.STRING.makeArray() })); } else { methodGen5.add((byte)-74, this.me.method("run", Type.INT, new Type[] { (Type)Type.STRING, (Type)Type.STRING.makeArray() })); }  methodGen5.add((byte)-72, Type.Class.instance("java.lang.System").method("exit", Type.VOID, new Type[] { Type.INT })); methodGen5.add((byte)-79); if (this.outDir != null) { if (!this.outDir.isDirectory()) throw new IOException("" + this.outDir + " isn't a directory");  this.cg.dump(this.outDir); } else { this.cg.dump(this.os); }  }
/* 1835 */   private void addConstReturnMethod(String paramString, int paramInt) { MethodGen methodGen = this.cg.addMethod(paramString, Type.INT, Type.NO_ARGS, 4); methodGen.add((byte)18, paramInt); methodGen.add((byte)-84); } private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException { if ((paramInt1 & 0x3) != 0 || (paramInt2 & 0x3) != 0) throw new Compiler.Exn("Data section on weird boundaries");  int i = paramInt1 + paramInt2; while (paramInt1 < i) { int j = Math.min(paramInt2, 28000); StringBuffer stringBuffer = new StringBuffer(); for (byte b = 0; b < j; b += 7) { long l = 0L; byte b1; for (b1 = 0; b1 < 7; b1++) { l <<= 8L; boolean bool = (b + b1 < paramInt2) ? paramDataInputStream.readByte() : true; l |= bool & 0xFFL; }  for (b1 = 0; b1 < 8; b1++) stringBuffer.append((char)(int)(l >>> 7 * (7 - b1) & 0x7FL));  }  String str = "_data" + ++initDataCount; this.cg.addField(str, (Type)Type.INT.makeArray(), 26); this.clinit.add((byte)18, stringBuffer.toString()); this.clinit.add((byte)18, j / 4); this.clinit.add((byte)-72, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime").method("decodeData", (Type)Type.INT.makeArray(), new Type[] { (Type)Type.STRING, Type.INT })); this.clinit.add((byte)-77, this.me.field(str, (Type)Type.INT.makeArray())); this.init.add((byte)42); this.init.add((byte)-78, this.me.field(str, (Type)Type.INT.makeArray())); this.init.add((byte)18, paramInt1); this.init.add((byte)18, paramBoolean ? 1 : 0); this.init.add((byte)-74, this.me.method("initPages", Type.VOID, new Type[] { (Type)Type.INT.makeArray(), Type.INT, Type.BOOLEAN })); paramInt1 += j; paramInt2 -= j; }  paramDataInputStream.close(); } private void emitBSS(int paramInt1, int paramInt2) throws Compiler.Exn { if ((paramInt1 & 0x3) != 0) throw new Compiler.Exn("BSS section on weird boundaries");  paramInt2 = paramInt2 + 3 & 0xFFFFFFFC; int i = paramInt2 / 4; this.init.add((byte)42); this.init.add((byte)18, paramInt1); this.init.add((byte)18, i); this.init.add((byte)-74, this.me.method("clearPages", Type.VOID, new Type[] { Type.INT, Type.INT })); } private boolean jumpable(int paramInt) { return (this.jumpableAddresses.get(new Integer(paramInt)) != null); } private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException { if (this.textDone) throw new Compiler.Exn("Multiple text segments");  this.textDone = true; if ((paramInt1 & 0x3) != 0 || (paramInt2 & 0x3) != 0) throw new Compiler.Exn("Section on weird boundaries");  int i = paramInt2 / 4; byte b = -1; boolean bool1 = true; boolean bool2 = false; for (byte b1 = 0; b1 < i; b1++, paramInt1 += 4) { boolean bool = bool1 ? paramDataInputStream.readInt() : b; b = (b1 == i - 1) ? -1 : paramDataInputStream.readInt(); if (paramInt1 >= this.endOfMethod) { endMethod(paramInt1, bool2); startMethod(paramInt1); }  if (this.insnTargets[b1 % this.maxInsnPerMethod] != null) { this.insnTargets[b1 % this.maxInsnPerMethod].setTarget(this.mg.size()); bool2 = false; } else if (bool2) { continue; }  try { int j = emitInstruction(paramInt1, bool, b); bool2 = ((j & 0x1) != 0) ? true : false; bool1 = ((j & 0x2) != 0) ? true : false; } catch (Exn exn) { exn.printStackTrace(this.warn); this.warn.println("Exception at " + toHex(paramInt1)); throw exn; } catch (RuntimeException runtimeException) { this.warn.println("Exception at " + toHex(paramInt1)); throw runtimeException; }  if (bool1) { paramInt1 += 4; b1++; }  continue; }  endMethod(0, bool2); paramDataInputStream.close(); } private void startMethod(int paramInt) { this.startOfMethod = paramInt & this.methodMask; this.endOfMethod = this.startOfMethod + this.maxBytesPerMethod; this.mg = this.cg.addMethod("run_" + toHex(this.startOfMethod), Type.VOID, Type.NO_ARGS, 18); if (this.onePage) { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("page", (Type)Type.INT.makeArray())); this.mg.add((byte)77); } else { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("readPages", (Type)Type.INT.makeArray(2))); this.mg.add((byte)77); this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("writePages", (Type)Type.INT.makeArray(2))); this.mg.add((byte)78); }  this.returnTarget = new MethodGen.PhantomTarget(); this.insnTargets = new MethodGen.PhantomTarget[this.maxBytesPerMethod / 4]; int[] arrayOfInt = new int[this.maxBytesPerMethod / 4]; Object[] arrayOfObject = new Object[this.maxBytesPerMethod / 4]; byte b = 0; for (int i = paramInt; i < this.endOfMethod; i += 4) { if (jumpable(i)) { this.insnTargets[(i - this.startOfMethod) / 4] = new MethodGen.PhantomTarget(); arrayOfObject[b] = new MethodGen.PhantomTarget(); arrayOfInt[b] = i; b++; }  }  MethodGen.Switch.Lookup lookup = new MethodGen.Switch.Lookup(b); System.arraycopy(arrayOfInt, 0, lookup.vals, 0, b); System.arraycopy(arrayOfObject, 0, lookup.targets, 0, b); lookup.setDefaultTarget(this.defaultTarget = new MethodGen.PhantomTarget()); fixupRegsStart(); this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("pc", Type.INT)); this.mg.add((byte)-85, lookup); } private void endMethod(int paramInt, boolean paramBoolean) { if (this.startOfMethod == 0) return;  if (!paramBoolean) { preSetPC(); this.mg.add((byte)18, paramInt); setPC(); this.jumpableAddresses.put(new Integer(paramInt), Boolean.TRUE); }  this.returnTarget.setTarget(this.mg.size()); fixupRegsEnd(); this.mg.add((byte)-79); this.defaultTarget.setTarget(this.mg.size()); if (this.debugCompiler) { this.mg.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException")); this.mg.add((byte)89); this.mg.add((byte)-69, Type.STRINGBUFFER); this.mg.add((byte)89); this.mg.add((byte)18, "Jumped to invalid address: "); this.mg.add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { (Type)Type.STRING })); this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field("pc", Type.INT)); this.mg.add((byte)-74, Type.STRINGBUFFER.method("append", (Type)Type.STRINGBUFFER, new Type[] { Type.INT })); this.mg.add((byte)-74, Type.STRINGBUFFER.method("toString", (Type)Type.STRING, Type.NO_ARGS)); this.mg.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { (Type)Type.STRING })); this.mg.add((byte)-65); } else { this.mg.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException")); this.mg.add((byte)89); this.mg.add((byte)18, "Jumped to invalid address"); this.mg.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { (Type)Type.STRING })); this.mg.add((byte)-65); }  this.endOfMethod = this.startOfMethod = 0; } private void leaveMethod() { this.mg.add((byte)-89, this.returnTarget); } private void link(int paramInt) { preSetReg(31); if (this.lessConstants) { int i = paramInt + 8 + 32768 & 0xFFFF0000; int j = paramInt + 8 - i; if (j < -32768 || j > 32767) throw new Error("should never happen " + j);  this.mg.add((byte)18, i); this.mg.add((byte)18, j); this.mg.add((byte)96); } else { this.mg.add((byte)18, paramInt + 8); }  setReg(); } private void branch(int paramInt1, int paramInt2) { if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) { this.mg.add((byte)-89, this.insnTargets[(paramInt2 - this.startOfMethod) / 4]); } else { preSetPC(); this.mg.add((byte)18, paramInt2); setPC(); leaveMethod(); }  } private int doIfInstruction(byte paramByte, int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn { emitInstruction(-1, paramInt3, -1); if ((paramInt2 & this.methodMask) == (paramInt1 & this.methodMask)) { this.mg.add(paramByte, this.insnTargets[(paramInt2 - this.startOfMethod) / 4]); } else { int j = this.mg.add(MethodGen.negate(paramByte)); branch(paramInt1, paramInt2); this.mg.setArg(j, this.mg.size()); }  if (!jumpable(paramInt1 + 4)) return 2;  if (paramInt1 + 4 == this.endOfMethod) { this.jumpableAddresses.put(new Integer(paramInt1 + 8), Boolean.TRUE); branch(paramInt1, paramInt1 + 8); return 1; }  int i = this.mg.add((byte)-89); this.insnTargets[(paramInt1 + 4 - this.startOfMethod) / 4].setTarget(this.mg.size()); emitInstruction(-1, paramInt3, 1); this.mg.setArg(i, this.mg.size()); return 2; } private static final Float POINT_5_F = new Float(0.5F); private static final Double POINT_5_D = new Double(0.5D); private static final Long FFFFFFFF = new Long(4294967295L); private static final int R = 0; private static final int F = 32; private static final int HI = 64; private static final int LO = 65; private static final int FCSR = 66; private boolean preSetReg(int paramInt) { this.preSetRegStack[this.preSetRegStackPos] = paramInt;
/* 1836 */     this.preSetRegStackPos++;
/* 1837 */     if (doLocal(paramInt)) {
/* 1838 */       return false;
/*      */     }
/* 1840 */     this.mg.add((byte)42);
/* 1841 */     return true; }
/*      */   private static final int REG_COUNT = 67;
/*      */   private int emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn { int i11, i12, i13; MethodGen.Switch.Table table; MethodGen methodGen = this.mg; if (paramInt2 == -1) throw new Compiler.Exn("insn is -1");  int i = 0; int j = paramInt2 >>> 26 & 0xFF; int k = paramInt2 >>> 21 & 0x1F; int m = paramInt2 >>> 16 & 0x1F; int n = paramInt2 >>> 16 & 0x1F; int i1 = paramInt2 >>> 11 & 0x1F; int i2 = paramInt2 >>> 11 & 0x1F; int i3 = paramInt2 >>> 6 & 0x1F; int i4 = paramInt2 >>> 6 & 0x1F; int i5 = paramInt2 & 0x3F; int i6 = paramInt2 >>> 6 & 0xFFFFF; int i7 = paramInt2 & 0x3FFFFFF; int i8 = paramInt2 & 0xFFFF; int i9 = paramInt2 << 16 >> 16; int i10 = i9; switch (j) { case 0: switch (i5) { case 0: if (paramInt2 != 0) { preSetReg(0 + i1); pushRegWZ(0 + m); methodGen.add((byte)18, i3); methodGen.add((byte)120); setReg(); }  return i;case 2: preSetReg(0 + i1); pushRegWZ(0 + m); methodGen.add((byte)18, i3); methodGen.add((byte)124); setReg(); return i;case 3: preSetReg(0 + i1); pushRegWZ(0 + m); methodGen.add((byte)18, i3); methodGen.add((byte)122); setReg(); return i;case 4: preSetReg(0 + i1); pushRegWZ(0 + m); pushRegWZ(0 + k); methodGen.add((byte)120); setReg(); return i;case 6: preSetReg(0 + i1); pushRegWZ(0 + m); pushRegWZ(0 + k); methodGen.add((byte)124); setReg(); return i;case 7: preSetReg(0 + i1); pushRegWZ(0 + m); pushRegWZ(0 + k); methodGen.add((byte)122); setReg(); return i;case 8: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  emitInstruction(-1, paramInt3, -1); preSetPC(); pushRegWZ(0 + k); setPC(); leaveMethod(); i |= 0x1; return i;case 9: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  emitInstruction(-1, paramInt3, -1); link(paramInt1); preSetPC(); pushRegWZ(0 + k); setPC(); leaveMethod(); i |= 0x1; return i;case 12: preSetPC(); methodGen.add((byte)18, paramInt1); setPC(); restoreChangedRegs(); preSetReg(2); methodGen.add((byte)42); pushRegZ(2); pushRegZ(4); pushRegZ(5); pushRegZ(6); pushRegZ(7); pushRegZ(8); pushRegZ(9); methodGen.add((byte)-74, this.me.method("syscall", Type.INT, new Type[] { Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT })); setReg(); methodGen.add((byte)42); methodGen.add((byte)-76, this.me.field("state", Type.INT)); i11 = methodGen.add((byte)-103); preSetPC(); methodGen.add((byte)18, paramInt1 + 4); setPC(); leaveMethod(); methodGen.setArg(i11, methodGen.size()); return i;case 13: methodGen.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException")); methodGen.add((byte)89); methodGen.add((byte)18, "BREAK Code " + toHex(i6)); methodGen.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { (Type)Type.STRING })); methodGen.add((byte)-65); i |= 0x1; return i;case 16: preSetReg(0 + i1); pushReg(64); setReg(); return i;case 17: preSetReg(64); pushRegZ(0 + k); setReg(); return i;case 18: preSetReg(0 + i1); pushReg(65); setReg(); return i;case 19: preSetReg(65); pushRegZ(0 + k); setReg(); return i;case 24: pushRegWZ(0 + k); methodGen.add((byte)-123); pushRegWZ(0 + m); methodGen.add((byte)-123); methodGen.add((byte)105); methodGen.add((byte)92); methodGen.add((byte)-120); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)18, 32); methodGen.add((byte)125); methodGen.add((byte)-120); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); return i;case 25: pushRegWZ(0 + k); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); pushRegWZ(0 + m); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)105); methodGen.add((byte)92); methodGen.add((byte)-120); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)18, 32); methodGen.add((byte)125); methodGen.add((byte)-120); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); return i;case 26: pushRegWZ(0 + k); pushRegWZ(0 + m); methodGen.add((byte)92); methodGen.add((byte)108); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)112); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); return i;case 27: pushRegWZ(0 + m); methodGen.add((byte)89); setTmp(); i11 = methodGen.add((byte)-103); pushRegWZ(0 + k); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)92); pushTmp(); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)94); methodGen.add((byte)109); methodGen.add((byte)-120); if (preSetReg(65)) methodGen.add((byte)95);  setReg(); methodGen.add((byte)113); methodGen.add((byte)-120); if (preSetReg(64)) methodGen.add((byte)95);  setReg(); methodGen.setArg(i11, methodGen.size()); return i;case 32: throw new Compiler.Exn("ADD (add with oveflow trap) not suported");case 33: preSetReg(0 + i1); if (m != 0 && k != 0) { pushReg(0 + k); pushReg(0 + m); methodGen.add((byte)96); } else if (k != 0) { pushReg(0 + k); } else { pushRegZ(0 + m); }  setReg(); return i;case 34: throw new Compiler.Exn("SUB (add with oveflow trap) not suported");case 35: preSetReg(0 + i1); if (m != 0 && k != 0) { pushReg(0 + k); pushReg(0 + m); methodGen.add((byte)100); } else if (m != 0) { pushReg(0 + m); methodGen.add((byte)116); } else { pushRegZ(0 + k); }  setReg(); return i;case 36: preSetReg(0 + i1); pushRegWZ(0 + k); pushRegWZ(0 + m); methodGen.add((byte)126); setReg(); return i;case 37: preSetReg(0 + i1); pushRegWZ(0 + k); pushRegWZ(0 + m); methodGen.add(-128); setReg(); return i;case 38: preSetReg(0 + i1); pushRegWZ(0 + k); pushRegWZ(0 + m); methodGen.add((byte)-126); setReg(); return i;case 39: preSetReg(0 + i1); if (k != 0 || m != 0) { if (k != 0 && m != 0) { pushReg(0 + k); pushReg(0 + m); methodGen.add(-128); } else if (k != 0) { pushReg(0 + k); } else { pushReg(0 + m); }  methodGen.add((byte)2); methodGen.add((byte)-126); } else { methodGen.add((byte)18, -1); }  setReg(); return i;case 42: preSetReg(0 + i1); if (k != m) { pushRegZ(0 + k); pushRegZ(0 + m); i11 = methodGen.add((byte)-95); methodGen.add((byte)3); int i14 = methodGen.add((byte)-89); methodGen.setArg(i11, methodGen.add((byte)4)); methodGen.setArg(i14, methodGen.size()); } else { methodGen.add((byte)18, 0); }  setReg(); return i;case 43: preSetReg(0 + i1); if (k != m) { if (k != 0) { pushReg(0 + k); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); pushReg(0 + m); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)-108); i11 = methodGen.add((byte)-101); } else { pushReg(0 + m); i11 = methodGen.add((byte)-102); }  methodGen.add((byte)3); int i14 = methodGen.add((byte)-89); methodGen.setArg(i11, methodGen.add((byte)4)); methodGen.setArg(i14, methodGen.size()); } else { methodGen.add((byte)18, 0); }  setReg(); return i; }  throw new Compiler.Exn("Illegal instruction 0/" + i5);case 1: switch (m) { case 0: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + k); return doIfInstruction((byte)-101, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);case 1: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + k); return doIfInstruction((byte)-100, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);case 16: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + k); i11 = methodGen.add((byte)-100); emitInstruction(-1, paramInt3, -1); link(paramInt1); branch(paramInt1, paramInt1 + i10 * 4 + 4); methodGen.setArg(i11, methodGen.size()); return i;case 17: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  i11 = -1; if (k != 0) { pushRegWZ(0 + k); i11 = methodGen.add((byte)-101); }  emitInstruction(-1, paramInt3, -1); link(paramInt1); branch(paramInt1, paramInt1 + i10 * 4 + 4); if (i11 != -1) methodGen.setArg(i11, methodGen.size());  if (i11 == -1) i |= 0x1;  return i; }  throw new Compiler.Exn("Illegal Instruction 1/" + m);case 2: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  emitInstruction(-1, paramInt3, -1); branch(paramInt1, paramInt1 & 0xF0000000 | i7 << 2); i |= 0x1; return i;case 3: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  i13 = paramInt1 & 0xF0000000 | i7 << 2; emitInstruction(-1, paramInt3, -1); link(paramInt1); branch(paramInt1, i13); i |= 0x1; return i;case 4: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  if (k == m) { emitInstruction(-1, paramInt3, -1); branch(paramInt1, paramInt1 + i10 * 4 + 4); i |= 0x1; } else { if (k == 0 || m == 0) { pushReg((m == 0) ? (0 + k) : (0 + m)); return doIfInstruction((byte)-103, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3); }  pushReg(0 + k); pushReg(0 + m); return doIfInstruction((byte)-97, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3); }  return i;case 5: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + k); if (m == 0) return doIfInstruction((byte)-102, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);  pushReg(0 + m); return doIfInstruction((byte)-96, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);case 6: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + k); return doIfInstruction((byte)-98, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);case 7: if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");  pushRegWZ(0 + k); return doIfInstruction((byte)-99, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);case 8: throw new Compiler.Exn("ADDI (add immediate with oveflow trap) not suported");case 9: if (k != 0 && i9 != 0 && k == m && doLocal(m) && i9 >= -32768 && i9 <= 32767) { this.regLocalWritten[m] = true; methodGen.add((byte)-124, new MethodGen.Pair(getLocalForReg(m), i9)); } else { preSetReg(0 + m); addiu(k, i9); setReg(); }  return i;case 10: preSetReg(0 + m); pushRegWZ(0 + k); methodGen.add((byte)18, i9); i11 = methodGen.add((byte)-95); methodGen.add((byte)3); i12 = methodGen.add((byte)-89); methodGen.setArg(i11, methodGen.add((byte)4)); methodGen.setArg(i12, methodGen.size()); setReg(); return i;case 11: preSetReg(0 + m); pushRegWZ(0 + k); methodGen.add((byte)-123); methodGen.add((byte)18, FFFFFFFF); methodGen.add(127); methodGen.add((byte)18, new Long(i9 & 0xFFFFFFFFL)); methodGen.add((byte)-108); i11 = methodGen.add((byte)-101); methodGen.add((byte)3); i12 = methodGen.add((byte)-89); methodGen.setArg(i11, methodGen.add((byte)4)); methodGen.setArg(i12, methodGen.size()); setReg(); return i;case 12: preSetReg(0 + m); pushRegWZ(0 + k); methodGen.add((byte)18, i8); methodGen.add((byte)126); setReg(); return i;case 13: preSetReg(0 + m); if (k != 0 && i8 != 0) { pushReg(0 + k); methodGen.add((byte)18, i8); methodGen.add(-128); } else if (k != 0) { pushReg(0 + k); } else { methodGen.add((byte)18, i8); }  setReg(); return i;case 14: preSetReg(0 + m); pushRegWZ(0 + k); methodGen.add((byte)18, i8); methodGen.add((byte)-126); setReg(); return i;case 15: preSetReg(0 + m); methodGen.add((byte)18, i8 << 16); setReg(); return i;case 16: throw new Compiler.Exn("TLB/Exception support not implemented");case 17: switch (k) { case 0: preSetReg(0 + m); pushReg(32 + i1); setReg(); return i;case 2: if (i2 != 31) throw new Compiler.Exn("FCR " + i2 + " unavailable");  preSetReg(0 + m); pushReg(66); setReg(); return i;case 4: preSetReg(32 + i1); if (m != 0) { pushReg(0 + m); } else { methodGen.add((byte)3); }  setReg(); return i;case 6: if (i2 != 31) throw new Compiler.Exn("FCR " + i2 + " unavailable");  preSetReg(66); pushReg(0 + m); setReg(); return i;case 8: pushReg(66); methodGen.add((byte)18, 8388608); methodGen.add((byte)126); return doIfInstruction(((paramInt2 >>> 16 & 0x1) == 0) ? -103 : -102, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);case 16: case 17: i13 = (k == 17) ? 1 : 0; switch (i5) { case 0: preSetDouble(32 + i4, i13); pushDouble(32 + i2, i13); pushDouble(32 + n, i13); methodGen.add((i13 != 0) ? 99 : 98); setDouble(i13); return i;case 1: preSetDouble(32 + i4, i13); pushDouble(32 + i2, i13); pushDouble(32 + n, i13); methodGen.add((i13 != 0) ? 103 : 102); setDouble(i13); return i;case 2: preSetDouble(32 + i4, i13); pushDouble(32 + i2, i13); pushDouble(32 + n, i13); methodGen.add((i13 != 0) ? 107 : 106); setDouble(i13); return i;case 3: preSetDouble(32 + i4, i13); pushDouble(32 + i2, i13); pushDouble(32 + n, i13); methodGen.add((i13 != 0) ? 111 : 110); setDouble(i13); return i;case 5: preSetDouble(32 + i4, i13); pushDouble(32 + i2, i13); methodGen.add((i13 != 0) ? 92 : 89); methodGen.add((i13 != 0) ? 14 : 11); methodGen.add((i13 != 0) ? -104 : -106); i11 = methodGen.add((byte)-99); methodGen.add((i13 != 0) ? 14 : 11); if (i13 != 0) { methodGen.add((byte)94); methodGen.add((byte)88); } else { methodGen.add((byte)95); }  methodGen.add((i13 != 0) ? 103 : 102); methodGen.setArg(i11, methodGen.size()); setDouble(i13); return i;case 6: preSetReg(32 + i4); pushReg(32 + i2); setReg(); if (i13 != 0) { preSetReg(32 + i4 + 1); pushReg(32 + i2 + 1); setReg(); }  return i;case 7: preSetDouble(32 + i4, i13); pushDouble(32 + i2, i13); methodGen.add((i13 != 0) ? 119 : 118); setDouble(i13); return i;case 32: preSetFloat(32 + i4); pushDouble(32 + i2, i13); if (i13 != 0) methodGen.add((byte)-112);  setFloat(); return i;case 33: preSetDouble(32 + i4); pushDouble(32 + i2, i13); if (i13 == 0) methodGen.add((byte)-115);  setDouble(); return i;case 36: table = new MethodGen.Switch.Table(0, 3); preSetReg(32 + i4); pushDouble(32 + i2, i13); pushReg(66); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)-86, table); table.setTarget(2, methodGen.size()); if (i13 == 0) methodGen.add((byte)-115);  methodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("ceil", Type.DOUBLE, new Type[] { Type.DOUBLE })); if (i13 == 0) methodGen.add((byte)-112);  i11 = methodGen.add((byte)-89); table.setTarget(0, methodGen.size()); methodGen.add((byte)18, (i13 != 0) ? POINT_5_D : POINT_5_F); methodGen.add((i13 != 0) ? 99 : 98); table.setTarget(3, methodGen.size()); if (i13 == 0) methodGen.add((byte)-115);  methodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("floor", Type.DOUBLE, new Type[] { Type.DOUBLE })); if (i13 == 0) methodGen.add((byte)-112);  table.setTarget(1, methodGen.size()); table.setDefaultTarget(methodGen.size()); methodGen.setArg(i11, methodGen.size()); methodGen.add((i13 != 0) ? -114 : -117); setReg(); return i;case 50: case 60: case 62: preSetReg(66); pushReg(66); methodGen.add((byte)18, -8388609); methodGen.add((byte)126); pushDouble(32 + i2, i13); pushDouble(32 + n, i13); methodGen.add((i13 != 0) ? -104 : -106); switch (i5) { case 50: i11 = methodGen.add((byte)-102); break;case 60: i11 = methodGen.add((byte)-100); break;case 62: i11 = methodGen.add((byte)-99); break;default: i11 = -1; break; }  methodGen.add((byte)18, 8388608); methodGen.add(-128); methodGen.setArg(i11, methodGen.size()); setReg(); return i; }  throw new Compiler.Exn("Invalid Instruction 17/" + k + "/" + i5);case 20: switch (i5) { case 32: preSetFloat(32 + i4); pushReg(32 + i2); methodGen.add((byte)-122); setFloat(); return i;case 33: preSetDouble(32 + i4); pushReg(32 + i2); methodGen.add((byte)-121); setDouble(); return i; }  throw new Compiler.Exn("Invalid Instruction 17/" + k + "/" + i5); }  throw new Compiler.Exn("Invalid Instruction 17/" + k);case 18: case 19: throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");case 32: preSetReg(0 + m); addiu(0 + k, i9); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)-111); setReg(); return i;case 33: preSetReg(0 + m); addiu(0 + k, i9); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)-109); setReg(); return i;case 34: preSetReg(0 + m); addiu(0 + k, i9); setTmp(); pushRegWZ(0 + m); methodGen.add((byte)18, 16777215); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)126); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); setReg(); return i;case 35: preSetReg(0 + m); memRead(0 + k, i9); setReg(); return i;case 36: preSetReg(0 + m); addiu(0 + k, i9); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)18, 255); methodGen.add((byte)126); setReg(); return i;case 37: preSetReg(0 + m); addiu(0 + k, i9); setTmp(); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)-110); setReg(); return i;case 38: preSetReg(0 + m); addiu(0 + k, i9); setTmp(); pushRegWZ(0 + m); methodGen.add((byte)18, -256); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add((byte)126); preMemRead(); pushTmp(); memRead(true); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add(-128); setReg(); return i;case 40: addiu(0 + k, i9); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, -16777216); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)126); if (m != 0) { pushReg(0 + m); methodGen.add((byte)18, 255); methodGen.add((byte)126); } else { methodGen.add((byte)18, 0); }  pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); memWrite(); return i;case 41: addiu(0 + k, i9); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, 65535); pushTmp(); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add((byte)126); if (m != 0) { pushReg(0 + m); methodGen.add((byte)18, 65535); methodGen.add((byte)126); } else { methodGen.add((byte)18, 0); }  pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)5); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); memWrite(); return i;case 42: addiu(0 + k, i9); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, -256); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add((byte)126); pushRegWZ(0 + m); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add(-128); memWrite(); return i;case 43: preMemWrite1(); preMemWrite2(0 + k, i9); pushRegZ(0 + m); memWrite(); return i;case 46: addiu(0 + k, i9); setTmp(); preMemRead(true); pushTmp(); memRead(true); methodGen.add((byte)18, 16777215); pushTmp(); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)124); methodGen.add((byte)126); pushRegWZ(0 + m); pushTmp(); methodGen.add((byte)2); methodGen.add((byte)-126); methodGen.add((byte)6); methodGen.add((byte)126); methodGen.add((byte)6); methodGen.add((byte)120); methodGen.add((byte)120); methodGen.add(-128); memWrite(); return i;case 48: preSetReg(0 + m); memRead(0 + k, i9); setReg(); return i;case 49: preSetReg(32 + m); memRead(0 + k, i9); setReg(); return i;case 56: preSetReg(0 + m); preMemWrite1(); preMemWrite2(0 + k, i9); pushReg(0 + m); memWrite(); methodGen.add((byte)18, 1); setReg(); return i;case 57: preMemWrite1(); preMemWrite2(0 + k, i9); pushReg(32 + m); memWrite(); return i; }  throw new Compiler.Exn("Invalid Instruction: " + j + " at " + toHex(paramInt1)); }
/*      */   private static final String[] regField = new String[] { "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9", "r10", "r11", "r12", "r13", "r14", "r15", "r16", "r17", "r18", "r19", "r20", "r21", "r22", "r23", "r24", "r25", "r26", "r27", "r28", "r29", "r30", "r31", "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20", "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30", "f31", "hi", "lo", "fcsr" };
/*      */   private static final int MAX_LOCALS = 4;
/* 1846 */   private static final int LOAD_LENGTH = 3; private int[] regLocalMapping; private boolean[] regLocalWritten; private int nextAvailLocal; private int loadsStart; private int preSetRegStackPos; private int[] preSetRegStack; private int memWriteStage; private boolean didPreMemRead; private boolean preMemReadDoPreWrite; private boolean doLocal(int paramInt) { return (paramInt == 2 || paramInt == 3 || paramInt == 4 || paramInt == 29); } private int getLocalForReg(int paramInt) { if (this.regLocalMapping[paramInt] != 0) return this.regLocalMapping[paramInt];  this.regLocalMapping[paramInt] = this.nextAvailLocal++; return this.regLocalMapping[paramInt]; } private void fixupRegsStart() { byte b; for (b = 0; b < 67; b++) { this.regLocalMapping[b] = 0; this.regLocalWritten[b] = false; }  this.nextAvailLocal = this.onePage ? 4 : 5; this.loadsStart = this.mg.size(); for (b = 0; b < 12; b++) this.mg.add((byte)0);  } private void fixupRegsEnd() { int i = this.loadsStart; for (byte b = 0; b < 67; b++) { if (this.regLocalMapping[b] != 0) { this.mg.set(i++, (byte)42); this.mg.set(i++, (byte)-76, this.me.field(regField[b], Type.INT)); this.mg.set(i++, (byte)54, this.regLocalMapping[b]); if (this.regLocalWritten[b]) { this.mg.add((byte)42); this.mg.add((byte)21, this.regLocalMapping[b]); this.mg.add((byte)-75, this.me.field(regField[b], Type.INT)); }  }  }  } private void restoreChangedRegs() { for (byte b = 0; b < 67; b++) { if (this.regLocalWritten[b]) { this.mg.add((byte)42); this.mg.add((byte)21, this.regLocalMapping[b]); this.mg.add((byte)-75, this.me.field(regField[b], Type.INT)); }  }  } private int pushRegWZ(int paramInt) { if (paramInt == 0) { this.warn.println("Warning: Pushing r0!"); (new Exception()).printStackTrace(this.warn); }  return pushRegZ(paramInt); } private int pushRegZ(int paramInt) { if (paramInt == 0) return this.mg.add((byte)3);  return pushReg(paramInt); } private int pushReg(int paramInt) { int i = this.mg.size(); if (doLocal(paramInt)) { this.mg.add((byte)21, getLocalForReg(paramInt)); } else if (paramInt >= 32 && paramInt <= 63 && this.singleFloat) { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT)); this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT })); } else { this.mg.add((byte)42); this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.INT)); }  return i; } private int setReg() { if (this.preSetRegStackPos == 0) throw new RuntimeException("didn't do preSetReg"); 
/* 1847 */     this.preSetRegStackPos--;
/* 1848 */     int i = this.preSetRegStack[this.preSetRegStackPos];
/* 1849 */     int j = this.mg.size();
/* 1850 */     if (doLocal(i)) {
/* 1851 */       this.mg.add((byte)54, getLocalForReg(i));
/* 1852 */       this.regLocalWritten[i] = true;
/* 1853 */     } else if (i >= 32 && i <= 63 && this.singleFloat) {
/* 1854 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/* 1855 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
/*      */     } else {
/* 1857 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.INT));
/*      */     } 
/* 1859 */     return j; }
/*      */   
/*      */   private int preSetPC() {
/* 1862 */     return this.mg.add((byte)42);
/*      */   } private int setPC() {
/* 1864 */     return this.mg.add((byte)-75, this.me.field("pc", Type.INT));
/*      */   }
/*      */   
/*      */   private int pushFloat(int paramInt) throws Compiler.Exn {
/* 1868 */     return pushDouble(paramInt, false);
/*      */   } private int pushDouble(int paramInt, boolean paramBoolean) throws Compiler.Exn {
/* 1870 */     if (paramInt < 32 || paramInt >= 64) throw new IllegalArgumentException("" + paramInt); 
/* 1871 */     int i = this.mg.size();
/* 1872 */     if (paramBoolean) {
/* 1873 */       if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled"); 
/* 1874 */       if (paramInt == 63) throw new Compiler.Exn("Tried to use a double in f31"); 
/* 1875 */       pushReg(paramInt + 1);
/* 1876 */       this.mg.add((byte)-123);
/* 1877 */       this.mg.add((byte)18, 32);
/* 1878 */       this.mg.add((byte)121);
/* 1879 */       pushReg(paramInt);
/* 1880 */       this.mg.add((byte)-123);
/* 1881 */       this.mg.add((byte)18, FFFFFFFF);
/* 1882 */       this.mg.add(127);
/* 1883 */       this.mg.add((byte)-127);
/* 1884 */       this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("longBitsToDouble", Type.DOUBLE, new Type[] { Type.LONG }));
/* 1885 */     } else if (this.singleFloat) {
/* 1886 */       this.mg.add((byte)42);
/* 1887 */       this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT));
/*      */     } else {
/* 1889 */       pushReg(paramInt);
/* 1890 */       this.mg.add((byte)-72, Type.Class.instance("java.lang.Float").method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/*      */     } 
/* 1892 */     return i;
/*      */   }
/*      */   
/* 1895 */   private void preSetFloat(int paramInt) { preSetDouble(paramInt, false); }
/* 1896 */   private void preSetDouble(int paramInt) { preSetDouble(paramInt, true); } private void preSetDouble(int paramInt, boolean paramBoolean) {
/* 1897 */     preSetReg(paramInt);
/*      */   }
/* 1899 */   private int setFloat() throws Compiler.Exn { return setDouble(false); } private int setDouble() throws Compiler.Exn {
/* 1900 */     return setDouble(true);
/*      */   } private int setDouble(boolean paramBoolean) throws Compiler.Exn {
/* 1902 */     int i = this.preSetRegStack[this.preSetRegStackPos - 1];
/* 1903 */     if (i < 32 || i >= 64) throw new IllegalArgumentException("" + i); 
/* 1904 */     int j = this.mg.size();
/* 1905 */     if (paramBoolean) {
/* 1906 */       if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled"); 
/* 1907 */       if (i == 63) throw new Compiler.Exn("Tried to use a double in f31"); 
/* 1908 */       this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("doubleToLongBits", Type.LONG, new Type[] { Type.DOUBLE }));
/* 1909 */       this.mg.add((byte)92);
/* 1910 */       this.mg.add((byte)18, 32);
/* 1911 */       this.mg.add((byte)125);
/* 1912 */       this.mg.add((byte)-120);
/* 1913 */       if (preSetReg(i + 1))
/* 1914 */         this.mg.add((byte)95); 
/* 1915 */       setReg();
/* 1916 */       this.mg.add((byte)-120);
/* 1917 */       setReg();
/* 1918 */     } else if (this.singleFloat) {
/*      */       
/* 1920 */       this.preSetRegStackPos--;
/* 1921 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
/*      */     } else {
/*      */       
/* 1924 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToRawIntBits", Type.INT, new Type[] { Type.FLOAT }));
/* 1925 */       setReg();
/*      */     } 
/* 1927 */     return j;
/*      */   }
/*      */   
/* 1930 */   private void pushTmp() { this.mg.add((byte)27); } private void setTmp() {
/* 1931 */     this.mg.add((byte)60);
/*      */   }
/*      */   private void addiu(int paramInt1, int paramInt2) {
/* 1934 */     if (paramInt1 != 0 && paramInt2 != 0) {
/* 1935 */       pushReg(paramInt1);
/* 1936 */       this.mg.add((byte)18, paramInt2);
/* 1937 */       this.mg.add((byte)96);
/* 1938 */     } else if (paramInt1 != 0) {
/* 1939 */       pushReg(paramInt1);
/*      */     } else {
/* 1941 */       this.mg.add((byte)18, paramInt2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void preMemWrite1() {
/* 1946 */     if (this.memWriteStage != 0) throw new Error("pending preMemWrite1/2"); 
/* 1947 */     this.memWriteStage = 1;
/* 1948 */     if (this.onePage) {
/* 1949 */       this.mg.add((byte)44);
/* 1950 */     } else if (this.fastMem) {
/* 1951 */       this.mg.add((byte)25, 3);
/*      */     } else {
/* 1953 */       this.mg.add((byte)42);
/*      */     } 
/*      */   }
/*      */   private void preMemWrite2(int paramInt1, int paramInt2) {
/* 1957 */     addiu(paramInt1, paramInt2);
/* 1958 */     preMemWrite2();
/*      */   }
/*      */   private void preMemWrite2() {
/* 1961 */     preMemWrite2(false);
/*      */   } private void preMemWrite2(boolean paramBoolean) {
/* 1963 */     if (this.memWriteStage != 1) throw new Error("pending preMemWrite2 or no preMemWrite1"); 
/* 1964 */     this.memWriteStage = 2;
/*      */     
/* 1966 */     if (this.nullPointerCheck) {
/* 1967 */       this.mg.add((byte)89);
/* 1968 */       this.mg.add((byte)42);
/* 1969 */       this.mg.add((byte)95);
/*      */       
/* 1971 */       this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
/*      */     } 
/*      */     
/* 1974 */     if (this.onePage) {
/* 1975 */       this.mg.add((byte)5);
/* 1976 */       this.mg.add((byte)124);
/* 1977 */     } else if (this.fastMem) {
/* 1978 */       if (!paramBoolean)
/* 1979 */         this.mg.add((byte)90); 
/* 1980 */       this.mg.add((byte)18, this.pageShift);
/* 1981 */       this.mg.add((byte)124);
/* 1982 */       this.mg.add((byte)50);
/* 1983 */       if (paramBoolean) {
/* 1984 */         pushTmp();
/*      */       } else {
/* 1986 */         this.mg.add((byte)95);
/* 1987 */       }  this.mg.add((byte)5);
/* 1988 */       this.mg.add((byte)124);
/* 1989 */       this.mg.add((byte)18, (this.pageSize >> 2) - 1);
/* 1990 */       this.mg.add((byte)126);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void memWrite() {
/* 1996 */     if (this.memWriteStage != 2) throw new Error("didn't do preMemWrite1 or preMemWrite2"); 
/* 1997 */     this.memWriteStage = 0;
/*      */     
/* 1999 */     if (this.onePage) {
/* 2000 */       this.mg.add((byte)79);
/* 2001 */     } else if (this.fastMem) {
/* 2002 */       this.mg.add((byte)79);
/*      */     } else {
/*      */       
/* 2005 */       this.mg.add((byte)-74, this.me.method("unsafeMemWrite", Type.VOID, new Type[] { Type.INT, Type.INT }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void memRead(int paramInt1, int paramInt2) {
/* 2012 */     preMemRead();
/* 2013 */     addiu(paramInt1, paramInt2);
/* 2014 */     memRead();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void preMemRead() {
/* 2020 */     preMemRead(false);
/*      */   } private void preMemRead(boolean paramBoolean) {
/* 2022 */     if (this.didPreMemRead) throw new Error("pending preMemRead"); 
/* 2023 */     this.didPreMemRead = true;
/* 2024 */     this.preMemReadDoPreWrite = paramBoolean;
/* 2025 */     if (this.onePage) {
/* 2026 */       this.mg.add((byte)44);
/* 2027 */     } else if (this.fastMem) {
/* 2028 */       this.mg.add((byte)25, paramBoolean ? 3 : 2);
/*      */     } else {
/* 2030 */       this.mg.add((byte)42);
/*      */     } 
/*      */   }
/*      */   private void memRead() {
/* 2034 */     memRead(false);
/*      */   }
/*      */   private void memRead(boolean paramBoolean) {
/* 2037 */     if (!this.didPreMemRead) throw new Error("didn't do preMemRead"); 
/* 2038 */     this.didPreMemRead = false;
/* 2039 */     if (this.preMemReadDoPreWrite) {
/* 2040 */       this.memWriteStage = 2;
/*      */     }
/* 2042 */     if (this.nullPointerCheck) {
/* 2043 */       this.mg.add((byte)89);
/* 2044 */       this.mg.add((byte)42);
/* 2045 */       this.mg.add((byte)95);
/* 2046 */       this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
/*      */     } 
/*      */     
/* 2049 */     if (this.onePage) {
/* 2050 */       this.mg.add((byte)5);
/* 2051 */       this.mg.add((byte)124);
/* 2052 */       if (this.preMemReadDoPreWrite)
/* 2053 */         this.mg.add((byte)92); 
/* 2054 */       this.mg.add((byte)46);
/* 2055 */     } else if (this.fastMem) {
/* 2056 */       if (!paramBoolean)
/* 2057 */         this.mg.add((byte)90); 
/* 2058 */       this.mg.add((byte)18, this.pageShift);
/* 2059 */       this.mg.add((byte)124);
/* 2060 */       this.mg.add((byte)50);
/* 2061 */       if (paramBoolean) {
/* 2062 */         pushTmp();
/*      */       } else {
/* 2064 */         this.mg.add((byte)95);
/* 2065 */       }  this.mg.add((byte)5);
/* 2066 */       this.mg.add((byte)124);
/* 2067 */       this.mg.add((byte)18, (this.pageSize >> 2) - 1);
/* 2068 */       this.mg.add((byte)126);
/* 2069 */       if (this.preMemReadDoPreWrite)
/* 2070 */         this.mg.add((byte)92); 
/* 2071 */       this.mg.add((byte)46);
/*      */     } else {
/*      */       
/* 2074 */       if (this.preMemReadDoPreWrite) {
/* 2075 */         this.mg.add((byte)92);
/*      */       }
/* 2077 */       this.mg.add((byte)-74, this.me.method("unsafeMemRead", Type.INT, new Type[] { Type.INT }));
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\ClassFileCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */