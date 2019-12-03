package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class AppResource {
    private static final AppResource SINGLETON = new AppResource();
    private static Properties FILE_PROPERTIES;
    private static final String APP_FILES_LOCATION = ".\\src" +
            "\\main\\resources\\app-files.properties";

    private AppResource() { }

    public static AppResource getInstance() {
        return SINGLETON;
    }

    public File getFileOf(String fileName) {
        String loc = appProperties().getProperty(fileName);
        checkNotNull(loc, String.format("Resource [%s] isn't defined", fileName));
        Path p = Paths.get(loc);
        checkArgument(p.toFile().exists(), "File Resource [%s] cannot find.", fileName);
        return p.toFile();
    }

    private Properties appProperties() {
        if (FILE_PROPERTIES == null) {
            try (FileInputStream in = new FileInputStream(new File(APP_FILES_LOCATION))) {
                FILE_PROPERTIES = new Properties();
                FILE_PROPERTIES.load(in);
            } catch (IOException e) {
                throw new IllegalStateException();
            }
        }
        return new Properties(FILE_PROPERTIES);
    }
}
