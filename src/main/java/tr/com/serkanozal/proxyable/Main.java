package tr.com.serkanozal.proxyable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import tr.com.serkanozal.proxyable.proxy.ProxyHelper;
import tr.com.serkanozal.proxyable.proxy.ProxyObjectLoader;
import tr.com.serkanozal.proxyable.util.JvmUtil;

public class Main {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InterruptedException, InstantiationException, IllegalAccessException {
		long address = JvmUtil.addressOfClass(String.class);
		System.out.println(JvmUtil.isCompressedRef());
		//JvmUtil.dump(address, 32 * JvmUtil.WORD);
		int modifiers = JvmUtil.getUnsafe().getInt(address + 152);
		int acessFlags = JvmUtil.getUnsafe().getInt(address + 156);
		JvmUtil.getUnsafe().putInt(address + 152, 1);
		JvmUtil.getUnsafe().putInt(address + 156, acessFlags & 0xFFFFFFEF);
		modifiers = JvmUtil.getUnsafe().getInt(address + 152);
		acessFlags = JvmUtil.getUnsafe().getInt(address + 156);
		String str = ProxyHelper.proxyObject(String.class, new StringProxyObjectLoader());
		String str2 = str.getClass().newInstance();
		str.isEmpty();
		str2.isEmpty();
	}
	
	private static class StringProxyObjectLoader extends ProxyObjectLoader<String> {

		@Override
		public String load() {
			System.out.println("load");
			return "xx";
		}
		
	}

}
