package fr.youenchene.simpleSonarReport.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public class SonarProjectDetails extends SonarProject {


     public List<SonarMeasure> msr;


    @Override
    public String toString() {
        return "SonarProjectDetails{" +
                "msr=" + msr +
                "} " + super.toString();
    }
}
