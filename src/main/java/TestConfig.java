public class TestConfig {
    private String browser;
    private boolean mobile;
    private boolean fullScreen;
    private boolean headless;

    public String getBrowser() {
        return browser;
    }

    public boolean isMobile(){
        return mobile;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public boolean isHeadless() {
        return headless;
    }
}
