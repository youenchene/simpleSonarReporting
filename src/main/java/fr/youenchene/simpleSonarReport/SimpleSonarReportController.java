package fr.youenchene.simpleSonarReport;

import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;
import fr.youenchene.simpleSonarReport.model.SonarProject;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;
import fr.youenchene.simpleSonarReport.services.SonarServices;

import java.util.List;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.*;

/**
 * Main class.
 */
public class SimpleSonarReportController {


      private static SonarServices sonarServices=new SonarServices("http://10.31.0.92:9000/api/");

    /**
     * Start the server.
     * @param port http port to listen.
     * @param waitStop true to wait the stop.
     */
    public static void startServer(int port, boolean waitStop) {
        // Set the http port.
        setPort(port);
        // Set the path to static resources.
        setPublicResourcesPath("/fr/youenchene/simpleSonarReport/public");

        get(new Route<Void, List<SonarProject>>("/projects", Void.class) {
            @Override
            public Response<List<SonarProject>> handle(Void param, RouteParameters routeParams) throws HttpErrorException {

                return new Response<>(sonarServices.getSonarProjects());
            }
        });


        get(new Route<Void, SonarProjectDetails>("/projectDetails/:key", Void.class) {
            @Override
            public Response<SonarProjectDetails> handle(Void param, RouteParameters routeParams) throws HttpErrorException {
                String key=routeParams.getParam("key");
                SonarProjectDetails projectDetails=sonarServices.getSonarProjectDetails(key);
                if (projectDetails==null)
                    throw new HttpErrorException(404);
                else
                    return new Response<>(projectDetails);
            }
        });

        // Start the server.
        start(waitStop);
    }

    /**
     * Get the port.
     * <ul>
     *     <li>Heroku : System.getenv("PORT")</li>
     *     <li>Cloudbees : System.getProperty("app.port")</li>
     *     <li>default : 9999</li>
     * </ul>
     * @return port to use
     */
    private static int getPort() {
        // Heroku
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
            return Integer.parseInt(herokuPort);
        }

        // Cloudbees
        String cloudbeesPort = System.getProperty("app.port");
        if (cloudbeesPort != null) {
            return Integer.parseInt(cloudbeesPort);
        }

        // Default port;
        return 9999;
    }

    public static void main(String[] args) {
        // For main, we want to wait the stop.
        startServer(getPort(), true);
    }
}
