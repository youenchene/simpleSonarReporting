package fr.youenchene.simpleSonarReport.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class SonarProject {

    public String id;

    public String key;

    public String name;

    public String version;

    public String date;

    @Override
    public String toString() {
        return "SonarProject{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", date=" + date +
                '}';
    }
}
