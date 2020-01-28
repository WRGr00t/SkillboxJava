package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String index(){
        Date date = new Date();
        String str = String.format("%1$s %2$td %2$tB %2$tY", "Дата:", date);
        return (str);
    }
}
