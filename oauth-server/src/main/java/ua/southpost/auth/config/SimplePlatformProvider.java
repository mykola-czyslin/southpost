package ua.southpost.auth.config;

import org.keycloak.Config;
import org.keycloak.platform.PlatformProvider;
import org.keycloak.services.ServicesLogger;

import java.io.File;

public class SimplePlatformProvider implements PlatformProvider {
    Runnable shutdownHook;

    @Override
    public void onStartup(Runnable startupHook) {
        startupHook.run();
    }

    @Override
    public void onShutdown(Runnable shutdownHook) {
        this.shutdownHook = shutdownHook;
    }

    @Override
    public void exit(Throwable cause) {
        ServicesLogger.LOGGER.fatal(cause);
        exit(1);
    }

    @SuppressWarnings("SameParameterValue")
    private void exit(int status) {
        new Thread(() -> System.exit(status)).start();
    }

    @Override
    public File getTmpDirectory() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    @Override
    public ClassLoader getScriptEngineClassLoader(Config.Scope scriptProviderConfig) {
        return null;
    }
}
