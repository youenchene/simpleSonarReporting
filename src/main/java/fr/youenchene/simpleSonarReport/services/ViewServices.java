package fr.youenchene.simpleSonarReport.services;

import fr.youenchene.simpleSonarReport.Dao.ViewMongoDao;
import fr.youenchene.simpleSonarReport.model.View;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 29/04/13
 * Time: 08:12
 * To change this template use File | Settings | File Templates.
 */
public class ViewServices {

    static Logger logger = Logger.getLogger("ViewServices");

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
        logger.info("Creating :"+view.toString());
        viewDao.save(view);
    }


    public void update(View view)
    {
        logger.info("Updating :"+view.toString());
        viewDao.save(view);
    }

    public void delete(String id)
    {
        logger.info("Deleting :"+id);
        viewDao.deleteById(new ObjectId(id));
    }

}
