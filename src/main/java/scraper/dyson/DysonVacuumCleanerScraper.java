package scraper.dyson;

import io.ebeaninternal.server.type.ScalarTypeYear;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import scraper.VacuumCleaner;
import scraper.VacuumCleanerScraper;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DysonVacuumCleanerScraper implements VacuumCleanerScraper {

    @Override
    public Set<VacuumCleaner> scrapeVacuumCleaners() {

        List<DysonVacuumCleaner> vacuumCleaners = new ArrayList<>();

        //extract price
        String price_pattern = "\\${1}\\d+,*\\d+\\.{1}\\d*";
        Pattern p = Pattern.compile(price_pattern);
//        Matcher m = p.matcher(input);

        // Set the property for webdriver.chrome.driver to be the location to your local download of chromedriver
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        // Create new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();
        // And now use this to visit Dyson Canada
        driver.get("https://www.dysoncanada.ca/en");
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // Find the text input element by its name
        WebElement search_button = driver.findElement(By.cssSelector(".search-nav__input-overlay.js-search-open"));
        //click the search button
        search_button.click();
        //find the text field to enter search keywords
        WebElement search_field = driver.findElement(By.name("searchText"));
        // Enter something to search for
        search_field.sendKeys("vacuum");
        //find the search icon
        WebElement search_icon = driver.findElement(By.cssSelector(".icon.search-nav__icon-search"));
        //click the search icon to start searching
        search_icon.click();
        //wait until we find the "result" text indicating search results are shown
        WebDriverWait wait_for_result = new WebDriverWait(driver, Duration.ofSeconds(15));
        //Through inspection, I found that the text itself is covered by "Products-Data", so we need to get this element first
        WebElement search_result = wait_for_result.until(ExpectedConditions.visibilityOfElementLocated(By.id("Products-Data")));
        //Then we are able to access the span with the text we need
        WebElement search_result_text = search_result.findElement(By.cssSelector("h1>span"));
        //get the text from span
        String search_result_txt = search_result_text.getText();
//        System.out.println(search_result_txt);
        //find the "product" box for result filter
        WebElement filter_product = driver.findElement(By.id("search-faceat-sub-category-Type-FINISHED_GOODS"));
        //click the checkbox of "product" to filter the search result
        filter_product.click();
        //wait until the applied filter button is clickable, if it's clickable, the page element is ready, and proceed
        WebDriverWait wait_for_filter = new WebDriverWait(driver, Duration.ofSeconds(6));
        //find the applied filter button and wait until it's clickable
        WebElement applied_filter = wait_for_filter.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".search-facet__applied-filters-items > ul > li > button")));
        //find the next results div
        WebElement next_result_div = driver.findElement(By.cssSelector(".js-search-pagination"));
        //get the height of this div
        int height_of_next = next_result_div.getSize().getHeight();
        driver.findElement(By.cssSelector(".search-pagination__text.js-search-pagination-link"));
        WebElement next_result_button = driver.findElement(By.cssSelector(".search-pagination__text.js-search-pagination-link"));

        //if the height of the div=0, the button is hidden, which means there's no next result
        while (height_of_next > 99) {

            next_result_button = driver.findElement(By.cssSelector(".search-pagination__text.js-search-pagination-link"));
            Actions move_to_next_button = new Actions(driver);
            move_to_next_button.moveToElement(next_result_button);
            //click to get more results
            next_result_button.click();
//            WebDriverWait wait_for_5s = new WebDriverWait(driver, Duration.ofSeconds(5));
//            wait_for_5s.until(ExpectedConditions.attributeToBe(next_result_div,));
//            WebDriverWait waitFor5Seconds = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //wait until the applied filter button is clickable, if it's clickable, the page element is ready, and proceed
            //find the applied filter button and wait until it's clickable
            wait_for_filter.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".search-facet__applied-filters-items > ul > li > button")));
            next_result_div = driver.findElement(By.cssSelector(".search-pagination"));
//            Actions move_to_next_div = new Actions(driver);
//            move_to_next_div.moveToElement(next_result_div);
            height_of_next = next_result_div.getSize().getHeight();
        }
//        JavascriptExecutor scroll_top = (JavascriptExecutor)driver;
//        scroll_top.executeScript("window.scrollTo(0, 0)");
        //find the vacuum item title link
        List<WebElement> item_details = driver.findElements(By.cssSelector("a.search-item__title"));
        //to store all products
        List<Map<String, String>> productList = new ArrayList<>();
        //to collect all distinct features
        Set<String> allFeatures = new HashSet<>();
        //iterate evey vacuum product
        for (WebElement item_detail : item_details) {
            Map<String, String> productData = new HashMap<>();
            //get the title
//            System.out.println("Item title: " + item_detail.getText());
            productData.put("Title", item_detail.getText());
            //get the link to the product page
            String item_url = item_detail.getAttribute("href");
            System.out.println("Product url: " + item_url);
            productData.put("URL", item_url);
            //store the link of product page in the array list
//            url_results.add(item_url);
            //get the vacuum type from url
            String vacuum_type = item_url.split("/")[5];
            System.out.println(vacuum_type);
            productData.put("Type", vacuum_type);
            WebDriver driver2 = new ChromeDriver();
            driver2.get(item_url);
//            Thread.sleep(3000);
            //find product specification
            WebElement p_spec = driver2.findElement(By.cssSelector(".product-specification__specification-container"));
            String product_specification = p_spec.getText();
            System.out.println("specification length: " + product_specification.length());
            //if the length of string is 0
            //we need to click the Specification to let the text show
            if (product_specification.length() == 0) {
                //find product specification button
//                WebElement button_spec = driver2.findElement(By.className("accordion__heading"));
                List<WebElement> button_specs = driver2.findElements(By.className("accordion-span"));
//                WebElement button_spec = driver2.findElement(By.className("accordion-span"));
//                button_spec.click();
                button_specs.get(1).click();
//                Thread.sleep(3000);
                WebDriverWait waitFor5Seconds = new WebDriverWait(driver2, Duration.ofSeconds(6));
                waitFor5Seconds.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-specification__specification-container")));
//                p_spec= driver2.findElement(By.cssSelector(".product-specification__specification-container"));
//                p_spec= driver2.findElement(By.cssSelector(".product-specification.parbase"));
                p_spec = driver2.findElement(By.cssSelector(".product-specification__specification-container"));
                product_specification = p_spec.getText();
            }
            String[] p_spec_list = product_specification.split("\n");
            for (int i = 0; i < p_spec_list.length; i++) {
                System.out.println("p_spec_list " + i + ":" + p_spec_list[i]);
            }

//            System.out.println(product_specification);
            if (p_spec_list.length > 1) {
                for (int i = 0; i < p_spec_list.length; i = i + 2) {
//                if(heads.contains(p_spec_list[i])==false){
//                    heads.add(p_spec_list[i]);
//                }
                    p_spec_list[i + 1] = Pattern.compile(",\\s").matcher(p_spec_list[i + 1]).replaceAll("").trim();

                    System.out.println(p_spec_list[i] + " : " + p_spec_list[i + 1]);
                    productData.put(p_spec_list[i], p_spec_list[i + 1]);
//                System.out.println("list item :"+spec);
                }
            }

            //get stock status
            WebElement stock_status = driver2.findElement(By.className("product-hero__out-of-stock"));
            String product_stock_status = stock_status.getText();
            System.out.println("stock: " + product_stock_status);
            productData.put("Stock status", product_stock_status);
            WebElement product_price = driver2.findElement(By.className("product-hero__price-top"));
            //get price
            String price_txt = product_price.getText();
//            System.out.println("Price: "+price_txt);
            String[] productl_price_txt = price_txt.split("\n");
            for (int i = 0; i < productl_price_txt.length; i++) {
                System.out.println("productl_price_txt " + i + " " + productl_price_txt[i]);
            }
            String product_price_txt = productl_price_txt[0];
            if (!p.matcher(product_price_txt).find()) {
                product_price_txt = productl_price_txt[1];
            }
//            for(String a_txt:productl_price_txt){
//                Matcher m = p.matcher(a_txt);
//                System.out.println("productl_price_txt1:"+a_txt);
//                if (m.find()) {
//                    for(int i =0; i< m.groupCount();i++){
//                        if(m.group(0)!= "current price:"){
//                            product_price_txt = m.group(0);
//
//                        }else {
//                            product_price_txt=m.group(1);
//                        }
//                    }
//                    System.out.println("product_price_txt2:"+product_price_txt);
//                    System.out.println("group:"+m.group());
//                }
//            }

            //get rid of comma
            String product_price_txt1 = Pattern.compile(",").matcher(product_price_txt).replaceAll("").trim();
            String product_price_txt2 = Pattern.compile("\\$").matcher(product_price_txt1).replaceAll("").trim();
            double double_price = Double.parseDouble(product_price_txt2);
            System.out.println("Price: " + product_price_txt2);
            productData.put("Price", product_price_txt2);
            // add current product to productt list
            productList.add(productData);
            //add current product's features
            allFeatures.addAll(productData.keySet());
            driver2.quit();

            double double_weight_lbs = getWeight(productData);
            boolean wireless_true = isWireless(productData);
            double power = getW(productData);

            vacuumCleaners.add(
                    new DysonVacuumCleaner(item_url, item_detail.getText(), double_price, double_weight_lbs, wireless_true, power)

            );
        }
        //Close the browser
        driver.quit();

        writeCSV(productList, (new ArrayList<>(allFeatures)));

//        List<DysonVacuumCleaner> vacuumCleaners = allVacuumCleanersPage.fetchDetails(driver, waitFor10Seconds);

        return Set.copyOf(vacuumCleaners);


    }

    public static void writeCSV(List<Map<String, String>> products, List<String> allFeatures) {
        try (FileWriter writer = new FileWriter("dyson_products.csv")) {
            // Write the header
            writer.append(String.join(",", allFeatures));
            writer.append("\n");
            // Write each product's data
            for (Map<String, String> product : products) {
                for (String feature : allFeatures) {
                    writer.append(product.getOrDefault(feature, ""));  // Handle missing features with default empty value
                    writer.append(",");
                }
                writer.append("\n");
            }
            System.out.println("CSV written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getW(Map<String, String> suctin_field) {
        String[] suction_p_f = {"Suction power (MAX mode)", "Suction power (Powerful mode)", "Suction Power (Boost Mode)", "Suction power"};
        double double_w = -1;
        for (String suc_field : suction_p_f) {
            if (suctin_field.containsKey(suc_field)) {
                if (!suctin_field.get(suc_field).isEmpty()) {
                    String f_suc_field = suctin_field.get(suc_field).replaceAll("\\D", "");
                    if (!f_suc_field.isEmpty()) {
                        double_w = Double.parseDouble(f_suc_field);
                        break;
                    }
                    break;
                }
            }
        }
        return double_w;

    }

    public static double getWeight(Map<String, String> weight_field) {
        double double_weight_lbs = -1;
        if (weight_field.containsKey("Weight") == true) {
            String string_weight = weight_field.get("Weight").replaceAll("\\s+kg", "");
            double double_weight_kg = Double.parseDouble(string_weight);
            double_weight_lbs = double_weight_kg * 2.20;
        }

        return double_weight_lbs;
    }

    public static boolean isWireless(Map<String, String> cordlength_field) {
        boolean wireless = false;

        if (cordlength_field.containsKey("Cord length")) {
            String cord_length = cordlength_field.get("Cord length");
            if (cord_length == "" || cord_length == " ") {
                wireless = true;
            }
            ;
        }
        return wireless;
    }
}
