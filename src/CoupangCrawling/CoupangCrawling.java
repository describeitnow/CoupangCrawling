package CoupangCrawling;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.URLEncoder;
import java.lang.Exception;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CoupangCrawling {
	
	public static void main(String[] args) throws Exception {
		WebDriver driver = null;
		List<WebElement> webElements = null;	
		int endPage =10;
		OutputStreamWriter out 
			= new OutputStreamWriter(new FileOutputStream("coupang.txt"));
		System.setProperty("webdriver.chrome.driver", 
				".\\src\\CoupangCrawling\\chromedriver.exe");
		
		for(int page=1; page <= endPage; page++)
		{
			// WebDriver 객체 생성
			driver = new ChromeDriver();
			// 로드 웹페이지에서 특정 요소를 찾을 때까지 기다리는 시간 설정
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			// 페이지로드가 완료 될 때까지 기다리는 시간 설정
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// 브라우저 창 최대화
			//driver.manage().window().maximize();
			
			StringBuilder urlBuilder=new StringBuilder("https://www.coupang.com/np/search?q=");
			String keyword ="겨울옷";
			urlBuilder.append(URLEncoder.encode(keyword,"UTF-8"));
			urlBuilder.append("&page="+page);
			driver.get(urlBuilder.toString());
			webElements = driver.findElements(By.className("name"));
			getItemsName(webElements, out, page);
			driver.close();
		}
		out.close();
	}
	
	public static void getItemsName(List<WebElement> webElements, OutputStreamWriter out, int page) throws Exception
	{
		String itemNames="";
		itemNames += (page+"\r\n");
		for(WebElement webElement : webElements) {
			String str = webElement.getText();
			itemNames += (str +"\r\n");
		}
		itemNames += ("\r\n" +"\r\n");
		out.write(itemNames);
	}
	
}