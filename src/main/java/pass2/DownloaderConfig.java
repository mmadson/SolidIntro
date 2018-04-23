package pass2;

import java.net.URL;
import java.nio.file.Path;

public class DownloaderConfig {

    public DownloaderConfig(final URL resource) {

    }

    public String getUsername() {
        return null;
    }

    public char[] getPassword() {
        return new char[0];
    }

    public String getServiceUri() {
        return null;
    }

    public Path getOutputFileLocation() {
        return null;
    }
}
