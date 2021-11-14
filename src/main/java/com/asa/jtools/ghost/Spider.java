package com.asa.jtools.ghost;

import com.asa.ghost.weixin.spider.WeixinSpider;
import com.asa.ghost.weixin.spider.view.MainView;
import com.asa.ghost.weixin.spider.view.SplashScreenCustom;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;


/**
 * @author andrew_asa
 * @date 2021/11/13.
 */
public class Spider{

    public static void main(String[] args) {

        //SpringApplication.run(WeixinSpider.class, args);
        //String cn = "com.asa.ghost.weixin.spider.WeixinSpider";
        //SpringApplication.run(com.asa.ghost.weixin.spider.WeixinSpider.class, args);
        //try {
        //    Class<?> mainClass = Thread.currentThread().getContextClassLoader().loadClass(cn);
        //
        //    Method mainMethod = mainClass.getDeclaredMethod("main", String[].class);
        //    mainMethod.invoke(null, args);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        //com.asa.ghost.weixin.spider.WeixinSpider.main(args);
        //launch(WeixinSpider.class, MainView.class, new SplashScreenCustom(), args);
        SpringApplication.run(WeixinSpider.class, args);
    }


}
