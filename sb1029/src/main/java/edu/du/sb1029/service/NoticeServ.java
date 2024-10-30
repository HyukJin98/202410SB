package edu.du.sb1029.service;

import edu.du.sb1029.entity.Notice;

import javax.transaction.Transactional;
import java.util.List;

public interface NoticeServ {
    List<Notice> selectNoticeList();

    @Transactional
    void insertNotice(Notice notice);

    @Transactional
    Notice selectNoticeDetail(int id);

    @Transactional
    void updateNotice(Notice notice) throws Exception;

    @Transactional
    void deleteNotice(int id);
}
