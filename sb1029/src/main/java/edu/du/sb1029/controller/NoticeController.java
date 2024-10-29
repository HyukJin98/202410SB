package edu.du.sb1029.controller;

import edu.du.sb1029.entity.Notice;
import edu.du.sb1029.service.NoticeServ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class NoticeController {

    @Autowired
    private NoticeServ noticeServ;

    @RequestMapping("/notice/openNoticeList")
    public String openNoticeList(Model model) throws Exception {
        log.info("====> openNoticeList 호출됨");

        List<Notice> list = noticeServ.selectNoticeList();
        log.info("List size: {}", list.size()); // 리스트 크기 확인

        model.addAttribute("list", list);
        return "notice/noticeList"; // 템플릿 경로
    }

    @RequestMapping("/notice/openNoticeWrite")
    public String openNoticeWrite() throws Exception {
        return "notice/noticeWrite";
    }

    @RequestMapping("/notice/insertNotice")
    public String insertNotice(Notice notice) throws Exception {
        noticeServ.insertNotice(notice);
        return "redirect:/notice/openNoticeList";
    }

    @RequestMapping("/notice/openNoticeDetail")
    public ModelAndView openNoticeDetail(@RequestParam int id) throws Exception {
        ModelAndView mv = new ModelAndView("notice/noticeDetail");

        Notice notice = noticeServ.selectNoticeDetail(id);
        mv.addObject("notice", notice);

        return mv;
    }

    @RequestMapping("/notice/updateNotice")
    public String updateNotice(Notice notice) throws Exception {
        noticeServ.updateNotice(notice);
        return "redirect:/notice/openNoticeList";
    }

    @RequestMapping("/notice/deleteNotice")
    public String deleteNotice(int id) throws Exception {
        noticeServ.deleteNotice(id);
        return "redirect:/notice/openNoticeList";
    }
}
