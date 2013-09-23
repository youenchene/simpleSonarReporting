package fr.youenchene.simpleSonarReport.process;

import fr.youenchene.simpleSonarReport.model.SonarMeasure;
import fr.youenchene.simpleSonarReport.model.SonarProjectDetails;
import fr.youenchene.simpleSonarReport.model.ViewDetails;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychene
 * Date: 30/04/13
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class SonarCalculation {


    public static ViewDetails calculateViewDetailsFromSonarProjectDetails(List<SonarProjectDetails> list)
    {
        Double totalline=new Double(0);
        Float totallinetrend=new Float(0);
        Double totalunittest=new Double(0);
        Float totalunittesttrend=new Float(0);
        Double totalcoverageweight=new Double(0);
        Float totalcoverageweighttrend=new Float(0);
        Double totalsuccessweight=new Double(0);

        if (list!=null)
        {
          Iterator<SonarProjectDetails> it=list.iterator();
          while(it.hasNext())
          {
              Double coverageweight=new Double(0);
              Float coverageweighttrend=new Float(0);
              Double line=new Double(0);
              Float linetrend=new Float(0);
              Double successweight=new Double(0);
              Double unittest=new Double(0);
              Float unittesttrend=new Float(0);
              SonarProjectDetails spd=it.next();
              if ((spd!=null)&&(spd.msr!=null))
              {
              Iterator<SonarMeasure> itm=spd.msr.iterator();
              if (itm!=null)
                  while(itm.hasNext())
                  {
                      SonarMeasure msr=itm.next();
                      //line_coverage,tests,test_execution_time,test_success_density,lines,weighted_violations
                      if (msr.key.equals("line_coverage"))
                      {
                          coverageweight=msr.val.doubleValue();
                          if (msr.trend!=null)
                             coverageweighttrend=msr.trend.floatValue();
                          else
                             coverageweighttrend=0f;

                      }
                      if (msr.key.equals("lines"))
                      {
                          totalline+=msr.val;
                          line=msr.val.doubleValue();
                          if (msr.trend!=null)
                              linetrend=msr.trend.floatValue();
                          else
                              linetrend=0f;
                      }
                      if (msr.key.equals("tests"))
                      {
                          totalunittest+=msr.val;
                          unittest=msr.val.doubleValue();
                          if (msr.trend!=null)
                              unittesttrend=msr.trend.floatValue();
                          else
                              unittesttrend=0f;
                      }
                      if (msr.key.equals("test_success_density"))
                      {
                          successweight=msr.val.doubleValue();
                      }
                  }
              }

              totalcoverageweight+=coverageweight*line;
              totalcoverageweighttrend+=coverageweighttrend*line.floatValue();
              totalsuccessweight+=successweight*unittest;
              totallinetrend+=linetrend*line.floatValue();
              totalunittesttrend+=unittesttrend*line.floatValue();


          }
        }

        ViewDetails d=new ViewDetails();
        d.line=totalline.intValue();
        d.unitTests=totalunittest.intValue();
        if (totalline!=0)
        {
            d.lineCoverage=totalcoverageweight/totalline;
            d.lineCoverageTrend=totalcoverageweighttrend/totalline.floatValue();
            d.lineTrend=totallinetrend/totalline.floatValue();
            d.unitTestTrend=totalunittesttrend/totalline.floatValue();
        }
        if (totalunittest!=0)
            d.unitTestsSuccess=totalsuccessweight/totalunittest;
        return d;
    }

}
