package fr.youenchene.simpleSonarReport.model;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 26/04/13
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class SonarMeasure {

    public String key;
    public Float val;
    public String frmt_val;
    public Float trend;

    @Override
    public String toString() {
        return "SonarMeasure{" +
                "key='" + key + '\'' +
                ", val=" + val +
                ", frmt_val='" + frmt_val + '\'' +
                ", trend=" + trend +
                '}';
    }
}
