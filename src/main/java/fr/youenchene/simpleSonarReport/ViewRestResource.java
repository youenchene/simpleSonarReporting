package fr.youenchene.simpleSonarReport;

import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.resource.RestResource;
import fr.youenchene.simpleSonarReport.model.View;
import fr.youenchene.simpleSonarReport.services.ViewServices;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 29/04/13
 * Time: 09:13
 * To change this template use File | Settings | File Templates.
 */
public class ViewRestResource extends RestResource<View> {

    private ViewServices viewServices;

    public ViewRestResource(String resourceRoute,ViewServices viewServices)
    {
        super(resourceRoute,View.class);
        this.viewServices=viewServices;
    }

    @Override
    public View getById(String id) throws HttpErrorException {
        return viewServices.get(id);
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
        viewServices.add(resource);
    }

    @Override
    public void delete(String id) throws HttpErrorException {
        viewServices.delete(id);
    }
}
