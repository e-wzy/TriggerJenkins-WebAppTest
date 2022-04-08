import java.nio.file.Path;

public class Configuration {

    //public static String url = "https://compute-3.dastc.stee.com:8080/";
    public static String mlflow_url = "http://dasec-jenkins.dastc.stee.com:5003/";
    public static String airflow_url = "https://dasec-airflow.dastc.stee.com/home";

    public static String workingDir = Path.of("").toAbsolutePath().toString();
    public static  String testDataPath = workingDir + "\\test-data\\";
    public static String testRsltPath = workingDir + "\\test-result\\";
    public static String pathToChromeDriver = workingDir + "\\chromedriver.exe";
    public static String pathToFireFoxDriver = workingDir + "\\geckodriver.exe";

}
