package fr.youenchene.simpleSonarReport.services;

import fr.youenchene.simpleSonarReport.Dao.ViewMongoDao;
import fr.youenchene.simpleSonarReport.model.View;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 29/04/13
 * Time: 08:12
 * To change this template use File | Settings | File Templates.
 */
public class ViewServices {

    ViewMongoDao viewDao;

    public ViewServices(String daoUri) throws UnknownHostException {
        viewDao=new ViewMongoDao(daoUri);
    }

    public List<View> getAll()
    {
        return  viewDao.find().asList();
    }

    public View get(String id)
    {
        return viewDao.get(new ObjectId(id));
    }


    public void add(View view)
    {
        viewDao.save(view);
    }


    public void update(View view)
    {
        viewDao.save(view);
    }

    public void delete(String id)
    {
        viewDao.deleteById(new ObjectId(id));
    }

}
