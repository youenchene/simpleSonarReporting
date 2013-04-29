package fr.youenchene.simpleSonarReport;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import fr.youenchene.simpleSonarReport.model.SonarProject;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;
import fr.youenchene.simpleSonarReport.model.View;
import org.fest.assertions.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.stop;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class APICallsItgTest {

    static Logger logger = Logger.getLogger("APICallsItgTest");

    private int port;

    @Before
    public void setup() throws UnknownHostException {
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

    @Test
    public void should_return_a_404_on_get_project_details()
    {
        Assertions.assertThat(HttpRequest.get("http://localhost:"+port+"/projectDetails/notfound").code()).isEqualTo(404);

    }

    @Test
       public void add_view()
    {
        //Given
        View v=new View();
        v.name="Test";
        // When
        int status=HttpRequest.post("http://localhost:"+port+"/view").send(new Gson().toJson(v)).code();
        //Then
        Assertions.assertThat(status).isEqualTo(201);
    }

    @Test
    public void should_get_all_and_then_get_and_then_update_and_then_delete_view()
    {
        //Given
        View v=new View();
        v.name="Test1";
        int status=HttpRequest.post("http://localhost:" + port + "/view/").send(new Gson().toJson(v)).code();

        View v2=new View();
        v2.name="Test2";
        //When
        logger.info(new Gson().toJson(v2));
        HttpRequest.post("http://localhost:"+port+"/view/").send(new Gson().toJson(v2));
        //Then
        String response= HttpRequest.get("http://localhost:"+port+"/view").body();
        logger.info(response);
        View[] views= new Gson().fromJson(response, View[].class);
        logger.info(views[0].toString());
        Assertions.assertThat(views).isNotEmpty();
        //When
        String response2= HttpRequest.get("http://localhost:"+port+"/view/"+views[0].id).body();
        logger.info(response2);
        //Then
        View view= new Gson().fromJson(response2, View.class);
        Assertions.assertThat(view).isNotNull();
        //When
        view.projectKeys=new ArrayList<String>();
        view.projectKeys.add("key1");
        view.projectKeys.add("key2");
        logger.info(view.toString());
        Assertions.assertThat(HttpRequest.put("http://localhost:"+port+"/view/"+views[0].id).send(new Gson().toJson(view)).code()).isEqualTo(204);
        //Then
        View view2= new Gson().fromJson(HttpRequest.get("http://localhost:"+port+"/view/"+views[0].id).body(), View.class);
        logger.info(view2.toString());
        Assertions.assertThat(view).isEqualTo(view2);
        //When
        int status2=HttpRequest.delete("http://localhost:"+port+"/view/"+views[0].id).code();
        //Then
        Assertions.assertThat(status2).isEqualTo(204);
        Assertions.assertThat(HttpRequest.get("http://localhost:"+port+"/view/"+views[0].id).code()).isEqualTo(404);
    }
}
