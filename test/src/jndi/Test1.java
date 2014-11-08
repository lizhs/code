package jndi;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Test1 {

    /**
     * @param args
     * @throws NamingException 
     */
    public static void main(String[] args) throws NamingException {

        String fileName="jquery-1.10.2.js";
        String dirName="jvm";
        Hashtable  env=new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:/F:/opensource");
        Context ctx=new InitialContext(env);
        System.out.println(ctx);
        Object file=ctx.lookup(fileName);
        System.out.println(fileName+"名称被绑定到："+file);
        
        Object dir=ctx.lookup(dirName);
        System.out.println(dirName+"名称被绑定到："+dir);
        
        System.out.println("dir的类型是："+dir.getClass());
        ctx.close();

    }

}