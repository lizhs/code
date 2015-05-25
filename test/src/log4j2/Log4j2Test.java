package log4j2;

import java.util.UUID;

import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.junit.Test;

import ws.client.User;

public class Log4j2Test {
	static {
		System.setProperty("log4j.configurationFile", "log4j2/log4j2.xml");
	}
	Logger logger1 = LogManager.getLogger(Log4j2Test.class);
	Logger logger = logger1;
	private final Marker SQL_MARKER = MarkerManager.getMarker("SQL");
	private final Marker UPDATE_MARKER = MarkerManager.getMarker("SQL_UPDATE").setParents(SQL_MARKER);

	/**
	 * <RegexFilter regex=".* filtertest .*" onMatch="ACCEPT"
				onMismatch="DENY" />
	 */
	@Test
	public void testFilter() {
		logger.debug("xxx filtertest xx");
		logger.debug("xxx");
	}
	@Test
	public void testCustomLevel() {
		// This creates the "VERBOSE" level if it does not exist yet.
		final Level VERBOSE = Level.forName("VERBOSE", 550);

		final Logger logger = LogManager.getLogger();
		logger.log(VERBOSE, "a verbose message"); // use the custom VERBOSE
													// level

		// Create and use a new custom level "DIAG".
		logger.log(Level.forName("DIAG", 350), "a diagnostic message");

		// Use (don't create) the "DIAG" custom level.
		// Only do this *after* the custom level is created!
		logger.log(Level.getLevel("DIAG"), "another diagnostic message");

		// Using an undefined level results in an error: Level.getLevel()
		// returns null,
		// and logger.log(null, "message") throws an exception.
		logger.log(Level.getLevel("FORGOT_TO_DEFINE"), "some message"); // throws
																		// exception!
	}

	@Test
	public void testThreadContext() {
		ThreadContext.push("11"); // Add the fishtag;
		ThreadContext.push("22"); // Add the fishtag;
		ThreadContext.put("id1", "id11111");
		ThreadContext.put("CONSUMER_SEQ_NO","213233333333333333333333333");

		logger.debug("Message 1");
		logger.debug("Message 2");
		ThreadContext.pop();
		ThreadContext.pop();
	}

	@Test
	public void testEventLog() {
		String confirm = UUID.randomUUID().toString();
		confirm = confirm.substring(0, 30);
		ThreadContext.put("hostname", "111");

		StructuredDataMessage msg = new StructuredDataMessage(confirm, null, "transfer");
		msg.put("toAccount", "xxx");
		msg.put("fromAccount", "xx");
		msg.put("amount", "xx");
		EventLogger.logEvent(msg);
		logger1.debug("xx");
	}

	@Test
	public void testTrace() {
		logger1.entry();
		logger1.debug("xx");
		logger1.exit();
	}

	@Test
	public void test() {
		User u = new User();
		u.setAge(10);
		u.setName("x");

		logger1.entry(u);
		logger1.debug("xx");

		logger1.debug(UPDATE_MARKER, "SELECT * FROM {}", u);
		_test();
		logger1.exit();
	}

	private void _test() {
		logger1.debug("xx222");
	}

}
