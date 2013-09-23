package fr.youenchene.simpleSonarReport;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import fr.youenchene.simpleSonarReport.model.SonarProject;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;
import org.junit.Test;
import org.fest.assertions.api.Assertions;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class SonarAPICall {

    static Logger logger = Logger.getLogger("SonarAPICall");

    @Test
    public void get_all_projects()
    {
        String response=  HttpRequest.get("http://10.31.0.92:9000/api/resources").body();
        logger.info(response);
        SonarProject[] projects= new Gson().fromJson(response, SonarProject[].class);
        logger.info(projects[0].toString());
        Assertions.assertThat(projects).isNotEmpty();
    }

    @Test
    public void get_project_details()
    {
        String response=  HttpRequest.get("http://10.31.0.92:9000/api/resources?metrics=line_coverage,tests,test_execution_time,test_success_density,lines,weighted_violations&resource=com.masternaut.platform.gateway:collector-calamp&includetrends=true").body();
        logger.info(response);
        SonarProjectDetails[] projectDetails= new Gson().fromJson(response, SonarProjectDetails[].class);
        logger.info(projectDetails[0].toString());
        Assertions.assertThat(projectDetails).isNotEmpty();

    }
}
