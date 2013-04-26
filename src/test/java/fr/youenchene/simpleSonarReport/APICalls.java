package fr.youenchene.simpleSonarReport;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import fr.youenchene.simpleSonarReport.model.SonarProject;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;
import org.fest.assertions.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.logging.Logger;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class APICalls {

    static Logger logger = Logger.getLogger("APICalls");

    private int port;

    @Before
    public void setup() {
        port = Integer.getInteger("test.http.port", 9999);
        SimpleSonarReportController.startServer(port, false);
    }

    @After
    public void tearDown() {
        stop();
    }


    @Test
    public void get_all_projects()
    {
        String response= HttpRequest.get("http://localhost:"+port+"/projects").body();
        logger.info(response);
        SonarProject[] projects= new Gson().fromJson(response, SonarProject[].class);
        logger.info(projects[0].toString());
        Assertions.assertThat(projects).isNotEmpty();

    }

    @Test
    public void get_project_details()
    {
        String response= HttpRequest.get("http://localhost:"+port+"/projectDetails/com.masternaut.synaps.middleware:devicemanagementapi-aggregator").body();
        logger.info(response);
        SonarProjectDetails projectDetails= new Gson().fromJson(response, SonarProjectDetails.class);
        logger.info(projectDetails.toString());
        Assertions.assertThat(projectDetails).isNotNull();

    }
}
