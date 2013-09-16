package fr.youenchene.simpleSonarReport;

import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;
import fr.ybonnel.simpleweb4j.handlers.resource.RestResource;
import fr.youenchene.simpleSonarReport.model.SonarProject;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;
import fr.youenchene.simpleSonarReport.model.View;
import fr.youenchene.simpleSonarReport.model.ViewDetails;
import fr.youenchene.simpleSonarReport.process.SonarCalculation;
import fr.youenchene.simpleSonarReport.services.SonarServices;
import fr.youenchene.simpleSonarReport.services.ViewServices;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static fr.ybonnel.simpleweb4j.SimpleWeb4j.*;

/**
 * Main class.
 */
public class SimpleSonarReportController {


    private static SonarServices sonarServices=new SonarServices("http://10.31.0.92:9000/api/");

    private static ViewServices viewServices;;

    /**
     * Start the server.
     * @param port http port to listen.
     * @param waitStop true to wait the stop.
     */
    public static void startServer(int port, boolean waitStop)  {
        // Set the http port.
        setPort(port);
        // Set the path to static resources.
        setPublicResourcesPath("/fr/youenchene/simpleSonarReport/public");

        try {
            viewServices=new ViewServices("mongodb://10.190.2.11:27017/");
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        resource(new RestResource<View> ("view", View.class) {

            @Override
            public View getById(String id) throws HttpErrorException {
                try
                {
                    View v=viewServices.get(id);
                    if (v!=null)
                     return v;
                    else
                     throw new HttpErrorException(404);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    throw e;
                }
            }

            @Override
            public List<View> getAll() throws HttpErrorException {
                return viewServices.getAll();
            }

            @Override
            public void update(String id, View resource) throws HttpErrorException {
                viewServices.update(resource);
            }

            @Override
            public void create(View resource) throws HttpErrorException {
                try
                {
                viewServices.add(resource);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    throw e;
                }
            }

            @Override
            public void delete(String id) throws HttpErrorException {
                viewServices.delete(id);
            }
        });


        get(new Route<Void, ViewDetails>("/viewWithDetails/:id", Void.class) {
            @Override
            public Response<ViewDetails> handle(Void param, RouteParameters routeParams) throws HttpErrorException {
                String id=routeParams.getParam("id");
                List<View> vl=viewServices.getAll();

                    View v=viewServices.get(id);
                    List<SonarProjectDetails> spdl=new ArrayList<>();
                    if (v.projectKeys!=null)
                    {
                        Iterator<String> it=v.projectKeys.iterator();
                        while(it.hasNext())
                        {
                           String key=it.next();
                           spdl.add(sonarServices.getSonarProjectDetails(key));
                        }
                    }
                    ViewDetails vd=SonarCalculation.calculateViewDetailsFromSonarProjectDetails(spdl);
                    vd.viewId=v.id;
                    vd.viewName=v.name;
                    return new Response<>(vd);
            }
        });

        get(new Route<Void, List<ViewDetails>>("/viewWithDetails", Void.class) {
            @Override
            public Response<List<ViewDetails>> handle(Void param, RouteParameters routeParams) throws HttpErrorException {

                List<View> vl=viewServices.getAll();
                List<ViewDetails> details=new ArrayList<ViewDetails>();
                Iterator<View> itv=vl.iterator();
                while(itv.hasNext())
                {
                    View v=itv.next();
                    List<SonarProjectDetails> spdl=new ArrayList<>();
                    if (v.projectKeys!=null)
                    {
                        Iterator<String> it=v.projectKeys.iterator();
                        while(it.hasNext())
                        {
                            String key=it.next();
                            spdl.add(sonarServices.getSonarProjectDetails(key));
                        }
                    }
                    ViewDetails vd=SonarCalculation.calculateViewDetailsFromSonarProjectDetails(spdl);
                    vd.viewId=v.id;
                    vd.viewName=v.name;
                    details.add(vd);
                }
                return new Response<>(details);
            }
        });


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
