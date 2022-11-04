package TestCaseTester;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TesterYouCase {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.imdb.com/");
        String expectURL = "https://www.imdb.com/";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(expectURL, actualURL);

        driver.findElement(By.xpath("//*[text()='Menu']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[text()='Oscars']")).click();
        driver.findElement(By.xpath("//*[text()='1929']")).click();
        driver.findElement(By.xpath("(//*[text()='The Jazz Singer'])[2]")).click();


        List<WebElement> abc = driver.findElements(By.xpath("//ul[@class='ipc-inline-list ipc-inline-list--show-dividers ipc-inline-list--inline ipc-metadata-list-item__list-content baseAlt']//a"));
        List<String> menuAll = new ArrayList<>();

        for (WebElement s : abc) {
            menuAll.add(s.getText());
        }

        driver.findElement(By.id("home_img_holder")).click();
        WebElement btn = driver.findElement(By.id("suggestion-search"));
        btn.sendKeys("The Jazz Singer");

        driver.findElement(By.xpath("(//div[@class='sc-d2740ffb-2 STkQo searchResult__constTitle'])[1]")).click();

        List<WebElement> abcd = driver.findElements(By.xpath("//ul[@class='ipc-inline-list ipc-inline-list--show-dividers ipc-inline-list--inline ipc-metadata-list-item__list-content baseAlt']//a"));
        List<String> searchAll = new ArrayList<>();


        for (WebElement m : abcd) {
            searchAll.add(m.getText());
        }

        Assert.assertTrue("Fail", menuAll.containsAll(searchAll));

        driver.findElement(By.xpath("//*[text()='See all the photos']")).click();

        driver.findElement(By.id("iconContext-grid-view")).click();

        List<WebElement> images = driver.findElements(By.tagName("img"));

        for (WebElement image : images) {
            String imageSrc = image.getAttribute("src");
            try {
                URL url = new URL(imageSrc);
                URLConnection urlConnection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 200){
                    System.out.println(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> " + httpURLConnection.getResponseMessage());
                } else {
                    System.err.println(imageSrc + " >> " + httpURLConnection.getResponseCode() + " >> " + httpURLConnection.getResponseMessage());
                }
                httpURLConnection.disconnect();
            }catch (Exception e){
                System.err.println(imageSrc);
            }
        }
        driver.quit();
    }
}

