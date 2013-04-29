package fr.youenchene.simpleSonarReport.model;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 29/04/13
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class View {

    @Id
    public String id;

    public String name;

    public List<String> projectKeys;

    @Override
    public String toString() {
        return "View{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", projectKeys=" + projectKeys +
                '}';
    }
}
