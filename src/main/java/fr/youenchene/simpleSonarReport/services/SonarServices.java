package fr.youenchene.simpleSonarReport.services;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import fr.youenchene.simpleSonarReport.model.SonarProject;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class SonarServices {

    static Logger logger = Logger.getLogger("APICalls");

    public static String  sonarAPIUrl="http://localhost:9000/api/";

    public static String metrics="line_coverage,tests,test_execution_time,test_success_density,lines,weighted_violations";

    public SonarServices(String sonarAPIUrl) {
        this.sonarAPIUrl=sonarAPIUrl;
    }

    public List<SonarProject>  getSonarProjects()
    {
        String response=  HttpRequest.get(sonarAPIUrl+"resources").body();
        logger.info(response);
        SonarProject[] projects= new Gson().fromJson(response, SonarProject[].class);
        return new ArrayList<SonarProject>(Arrays.asList(projects));
    }

    public SonarProjectDetails getSonarProjectDetails(String key)
    {
        HttpRequest request=HttpRequest.get(sonarAPIUrl+"resources?resource="+key+"&metrics="+metrics+"&includetrends=true");
        if (request.notFound())
                return null;
        else
        {
            String response=request.body();

            logger.info(response);
            SonarProjectDetails[] projects= new Gson().fromJson(response, SonarProjectDetails[].class);
            if ((projects!=null)&&(projects.length>0))
            {
                return projects[0];
            }
            else
            {
               return null;
            }
        }
    }
}
