package fr.youenchene.simpleSonarReport.model;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 30/04/13
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
@Entity("view")
public class ViewDaoObject {

    @Id
    public ObjectId id;

    public String name;

    public List<String> projectKeys;


    public ViewDaoObject() {
    }

    public ViewDaoObject(View v) {
        if (v.id!=null)
         id=new ObjectId(v.id);
        name=v.name;
        projectKeys=v.projectKeys;
    }

    @Override
    public String toString() {
        return "View{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projectKeys=" + projectKeys +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (id != null ? !id.equals(view.id) : view.id != null) return false;
        if (name != null ? !name.equals(view.name) : view.name != null) return false;
        if (projectKeys != null ? !projectKeys.equals(view.projectKeys) : view.projectKeys != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (projectKeys != null ? projectKeys.hashCode() : 0);
        return result;
    }


    public View convertToView(){
        View v=new View();
        v.id=this.id.toString();
        v.name=this.name;
        v.projectKeys=this.projectKeys;
        return v;
    }



}
