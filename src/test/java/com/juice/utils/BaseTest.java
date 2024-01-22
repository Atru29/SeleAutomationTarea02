package com.juice.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.IResultMap;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class BaseTest {
    //definir el scope de las varibales
    public FirefoxDriver driver;
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    String reportName =  new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

    //Inicializar reporte
    @BeforeTest
    public void setupReport() {
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reportes/"+reportName+".html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://juice-shop.herokuapp.com");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            //label fallo
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case Failed", ExtentColor.RED));
            //root cause
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - possible root cause", ExtentColor.AMBER));
            //takescreen
            //String ScreenPath = "TAREA: cree su metodo para tomar screen";
            //extentTest.fail("details", MediaEntityBuilder.createScreenCaptureFromPath(ScreenPath).build());
            String ScreenPath = Utils.takeScreenBase64(driver);
            // Agregar la captura de pantalla al informe
            extentTest.fail("details", MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenPath).build());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test case skipped", ExtentColor.ORANGE));

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test case passed", ExtentColor.GREEN));
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest
    public void makeReport() {
        extentReports.flush();
    }
}
