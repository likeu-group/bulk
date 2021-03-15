package cool.likeu.bulk.generator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.ShellException;

public class MybatisGeneratorApplication {

	public static void main(String[] args) throws Exception {
		List<String> configurations = new ArrayList<>();
		InputStream generatorConfigInputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("mybatis-generator-config.xml");
		ConfigurationParser configurationParser = new ConfigurationParser(configurations);
		Configuration configuration = configurationParser.parseConfiguration(generatorConfigInputStream);
		MyBatisGenerator generator = new MyBatisGenerator(configuration, new DefaultShellCallback(), configurations);
		generator.generate(new DefaultProgressCallback());
	}

	static class DefaultShellCallback implements ShellCallback {

		@Override
		public File getDirectory(String s, String s1) throws ShellException {
			return null;
		}

		@Override
		public String mergeJavaFile(String s, File file, String[] strings, String s1) throws ShellException {
			return null;
		}

		@Override
		public void refreshProject(String s) {

		}

		@Override
		public boolean isMergeSupported() {
			return false;
		}

		@Override
		public boolean isOverwriteEnabled() {
			return false;
		}
	}

	static class DefaultProgressCallback implements ProgressCallback {

		@Override
		public void introspectionStarted(int totalTasks) {

		}

		@Override
		public void generationStarted(int totalTasks) {

		}

		@Override
		public void saveStarted(int totalTasks) {

		}

		@Override
		public void startTask(String taskName) {

		}

		@Override
		public void done() {

		}

		@Override
		public void checkCancel() throws InterruptedException {

		}
	}
}
