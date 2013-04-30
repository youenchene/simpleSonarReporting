package fr.youenchene.simpleSonarReport.services;

import fr.youenchene.simpleSonarReport.Dao.ViewMongoDao;
import fr.youenchene.simpleSonarReport.model.View;
import fr.youenchene.simpleSonarReport.model.ViewDaoObject;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
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
        List<View> out=new ArrayList<View>();
        List<ViewDaoObject> l=viewDao.find().asList();
        if (l!=null)
        {
            Iterator<ViewDaoObject> it=l.iterator();
            while(it.hasNext())
            {
                ViewDaoObject vdo=it.next();
                out.add(vdo.convertToView());
            }
        }
        return out;
    }

    public View get(String id)
    {
        ViewDaoObject v =viewDao.get(new ObjectId(id));
        if (v!=null)
            return v.convertToView();
        else
            return null;
    }


    public void add(View view)
    {
        logger.info("Creating :"+view.toString());
        ViewDaoObject vdo=new ViewDaoObject(view);
        viewDao.save(vdo);
        view.id=vdo.id.toString();
        logger.info("New id:"+view.id);
    }


    public void update(View view)
    {
        logger.info("Updating :"+view.toString());
        viewDao.save(new ViewDaoObject(view));
    }

    public void delete(String id)
    {
        logger.info("Deleting :"+id);
        viewDao.deleteById(new ObjectId(id));
    }

}
