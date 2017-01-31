package framework;


import java.io.*;
import java.util.Properties;

public class Settings {

    private static final String KME_APP_PATH = "kme.app.path";
    private static final String PROPERTIES = "properties.properties";
    private static final String AZURE_SERVER_NAME = "azure.server.name";
    private static final String AZURE_SERVER_DB_NAME = "azure.server.db.name";
    private static final String AZURE_SERVER_USER_LOGIN = "azure.server.user.login";
    private static final String AZURE_SERVER_USER_PASSWORD = "azure.server.user.password";

    private String azureServerName;
    private String azureServerDBName;
    private String azureServerUserLogin;
    private String azureServerUserPassword;

    private String appPath;

    private Properties properties = new Properties();

    public Settings() {
        loadSettings();
    }

    private void loadSettings() {
        properties = loadPropertiesFile();
        appPath = getPropertyOrThrowException(KME_APP_PATH);

        azureServerName = getPropertyOrThrowException(AZURE_SERVER_NAME);
        azureServerDBName = getPropertyOrThrowException(AZURE_SERVER_DB_NAME);
        azureServerUserLogin = getPropertyOrThrowException(AZURE_SERVER_USER_LOGIN);
        azureServerUserPassword = getPropertyOrThrowException(AZURE_SERVER_USER_PASSWORD);
    }

    private Properties loadPropertiesFile() {
        try {
            // get specified property file
            String filename = getPropertyOrNull(PROPERTIES);
            // it is not defined, use default
            if (filename == null) {
                filename = PROPERTIES;
            }
            // try to load from classpath
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
            // no file in classpath, look on disk
            if (stream == null) {
                stream = new FileInputStream(new File(filename));
            }
            Properties result = new Properties();
            result.load(stream);
            return result;
        } catch (IOException e) {
            throw new UnknownPropertyException("Property file is not found");
        }
    }

    public String getPropertyOrNull(String name) {
        return getProperty(name, false);
    }

    public String getPropertyOrThrowException(String name) {
        return getProperty(name, true);
    }

    private String getProperty(String name, boolean forceExceptionIfNotDefined) {
        String result;
        if ((result = System.getProperty(name, null)) != null && result.length() > 0) {
            return result;
        } else if ((result = getPropertyFromPropertiesFile(name)) != null && result.length() > 0) {
            return result;
        } else if (forceExceptionIfNotDefined) {
            throw new UnknownPropertyException("Unknown property: [" + name + "]");
        }
        return result;
    }

    private String getPropertyFromPropertiesFile(String name) {
        Object result = properties.get(name);
        if (result == null) {
            return null;
        } else {
            return result.toString();
        }
    }

    public String getAzureServerName(){
        return azureServerName;
    }

    public String getAzureServerDBName(){
        return azureServerDBName;
    }

    public String getAzureServerUserLogin(){
        return azureServerUserLogin;
    }

    public String getAzureServerUserPassword(){
        return azureServerUserPassword;
    }

    public String getApplicationPath(){
        return appPath;
    }
}
