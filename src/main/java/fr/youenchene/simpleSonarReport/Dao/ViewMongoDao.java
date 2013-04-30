package fr.youenchene.simpleSonarReport.Dao;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
import fr.youenchene.simpleSonarReport.model.View;
import fr.youenchene.simpleSonarReport.model.ViewDaoObject;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 29/04/13
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class ViewMongoDao extends BasicDAO<ViewDaoObject,ObjectId> {

    public ViewMongoDao( String uri ) throws UnknownHostException
    {
        super(new Mongo(new MongoURI(uri)),new Morphia(),"simpleSonarReporting");
    }
}
