package ca.gc.agr.mbb.androidmockws;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static spark.Spark.*;
import spark.*;
import spark.webserver.*;


@RunWith(JUnit4.class)
public class AndroidMockWSTest
{
    AndroidMockWS service = null;
    static SparkServer ss = null;

    @Before
    public void init()
    {
	service = new AndroidMockWS();
	ss = SparkServerFactory.create(true);
	service.start();
    }



    @After
    public void tearDown() {
	ss.stop();
    }
    
    @Test(timeout=20000)
    public void getTest(){

    }

}
