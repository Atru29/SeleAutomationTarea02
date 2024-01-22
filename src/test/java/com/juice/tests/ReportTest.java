package com.juice.tests;

import com.juice.utils.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class ReportTest extends BaseTest {


    @Test
    public void getTitle(){
        extentTest = extentReports.createTest("verify Title", "Test que verifica titulo");
        extentTest.assignAuthor("Pedro");
        extentTest.assignCategory("Regression");
        extentTest.assignDevice("Win 11 - Edge");
        Assert.assertEquals(driver.getTitle(), "OWASP Juice Shop");

    }
    @Test
    public void verifyLogo(){//FAIL
        extentTest = extentReports.createTest("verify Logo", "Test que verifica logo");
        extentTest.assignAuthor("Diana");
        extentTest.assignCategory("Smoke");
        extentTest.assignDevice("XP");


        boolean logo = driver.findElement(By.className("logo")).isDisplayed();

        assertFalse(logo);
    }
    @Test
    public void skyTest(){

    }
}
