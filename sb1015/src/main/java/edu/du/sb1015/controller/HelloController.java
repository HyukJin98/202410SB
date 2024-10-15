package edu.du.sb1015.controller;

import edu.du.sb1015.dto.DataObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class HelloController {

//    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("msg", "이름을 적어 주세요");
        return "index";
    }

    @PostMapping("/")
    public String helloPost(@RequestParam("text1")String name, Model model) {
        model.addAttribute("value", name);
        model.addAttribute("msg","당신의 이름은 " + name + "이군요!!!!!!!");
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("msg","message 1<hr/>message 2<br/>message 3");
        DataObject obj = new DataObject(123,"lee","lee@flower");
        model.addAttribute("object",obj);
        return "index";
    }

    @GetMapping("/id/{id}")
    public String index(@PathVariable int id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("check",id>=0);
        model.addAttribute("trueVal","Positive!!");
        model.addAttribute("falseVal","negative.....ㅜㅜ");
        DataObject obj = new DataObject(123,"lee","lee@flower");
        model.addAttribute("object",obj);
        return "index";
    }

    @RequestMapping("/month/{month}")
    public ModelAndView index(@PathVariable int month, ModelAndView model) {
        model.setViewName("index2");
        int m = Math.abs(month) % 12;
        m = m == 0 ? 12 : m;
        model.addObject("month",m);
        model.addObject("check",Math.floor(m/3));
        return model;
    }

    @RequestMapping("/index3")
    public ModelAndView index(ModelAndView model) {
        model.setViewName("index3");
        ArrayList<String[]> data = new ArrayList<String[]>();
        data.add(new String[]{"park","park@yamada","090-999-999"});
        data.add(new String[]{"Lee","Lee@flower","080-888-888"});
        data.add(new String[]{"choi","choi@happy","070-777-777"});
        model.addObject("data",data);
        return model;
    }

    @RequestMapping("/index4")
    public ModelAndView index4(ModelAndView model) {
        model.setViewName("index4");
        ArrayList<String[]> data = new ArrayList<String[]>();
        data.add(new String[]{"park","park@yamada","090-999-999"});
        data.add(new String[]{"Lee","Lee@flower","080-888-888"});
        data.add(new String[]{"choi","choi@happy","070-777-777"});
        model.addObject("data",data);
        return model;
    }

    @RequestMapping("/tax/{tax}")
    public ModelAndView index5(@PathVariable int tax, ModelAndView model) {
        model.setViewName("index5");
        model.addObject("tax",tax);
        return model;
    }
}
