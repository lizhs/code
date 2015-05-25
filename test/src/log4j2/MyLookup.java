package log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "mylookup", category = StrLookup.CATEGORY)
public class MyLookup implements StrLookup {
	@Override
	public String lookup(String key) {
		return key+"extend!!";
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return lookup(key);
	}

}
