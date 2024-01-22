package com.juice.utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
public class Utils  {


    public static String takeScreenBase64(WebDriver driver) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;

            // Obtener la captura de pantalla en formato Base64
            return "data:image/png;base64," + screenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
           return e.getMessage();
        }
    }

}
