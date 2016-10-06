package com.goeuro.test;

import com.goeuro.test.controller.SuggestionsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EntryPoint {
    private static Logger log = LoggerFactory.getLogger(EntryPoint.class);

    public static void main(String[] args) {
        String cityName;
        if (args.length == 0 || (cityName = args[0]) == null) {
            log.info("There's no required argument.");
            return;
        }
        AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("ioc-context.xml");
        context.registerShutdownHook();
        SuggestionsController controller = (SuggestionsController) context.getBean("controller");
        try {
            controller.suggestLocations(cityName);
        } catch (Exception e) {
            log.error("Caught exception during main scenario execution. {} ", e.getMessage());
        }
    }

}
