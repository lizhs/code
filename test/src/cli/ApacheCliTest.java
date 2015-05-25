package cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.junit.Test;

public class ApacheCliTest {

	@Test
	public void testException2() {
		parser("".split("\\s+"));
	}
	
	@Test
	public void testException() {
		parser("-a1 aa -a2".split("\\s+"));
	}
	
	@Test
	public void testOption() {
		parser("-a1 aa -a2 1 2 3".split("\\s+"));
	}

	@Test
	public void testHelp() {
		parser(new String[] { "-h" });
	}

	public static void parser(String[] args) {
		Options opts = new Options();
		opts.addOption("h", "help", false, "帮助信息");
		opts.addOption("a1", "args1", true, "普通参数");
		Option op2 = OptionBuilder.withArgName("args-list").withLongOpt("args2").hasArgs().withDescription("参数列表")
				.create("a2");
		opts.addOption(op2);

		BasicParser parser = new BasicParser();
		CommandLine cl;
		try {
			cl = parser.parse(opts, args);
			if (cl.getOptions().length > 0) {
				if (cl.hasOption('h')) {
					HelpFormatter hf = new HelpFormatter();
					hf.printHelp("Options", opts);
				} else {
					String a1 = cl.getOptionValue("a1");
					System.out.println("a1:" + a1);

					String[] a2 = cl.getOptionValues("a2");
					for (String s : a2) {
						System.out.println("a2:" + s);
					}
				}
			} else {
				System.err.println("没有参数");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
