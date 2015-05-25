package asm;
import java.lang.reflect.Method;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
public class Hello {
@SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // 类访问开始：必须
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "my/Foo", null, "java/lang/Object", null);
        // 至少提供一个构造函数
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "", "()V", null, null);
        // 代码开始：必须
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "", "()V");
        mv.visitInsn(Opcodes.RETURN);
        // 计算栈和局部变量最大空间：必须
        mv.visitMaxs(0, 0);
        // 代码结束：必须
        mv.visitEnd();
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "main",
                        "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                        "(Ljava/lang/String;)V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        // 类结束：必须
        cw.visitEnd();
        final byte[] bs = cw.toByteArray();
        Class clazz = new ClassLoader() {
                protected Class findClass(String name) throws ClassNotFoundException {
                        return defineClass(name, bs, 0, bs.length);
                }
        }.loadClass("my.Foo");
        Method method = clazz.getMethod("main", new Class[] { String[].class });
        // 数组参数的方法，反射调用方式看起来比较古怪
        method.invoke(null, (Object) new String[0]);
        for (int i = 0; i < bs.length; i++)
                System.out.printf("%d:/t%02X/t%c/n", i, bs[i], (char) bs[i]);
        // OutputStream out = new FileOutputStream("d:/my/Foo.class");
        // out.write(bs);
        // out.close();
        }
}