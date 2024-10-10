package edu.du.sb1007_1.dao;

import edu.du.sb1007_1.dto.SimpleBdsDto;

import java.util.List;

public interface ISimpleBdsDao {
    public List<SimpleBdsDto> listDao();
    public SimpleBdsDto viewDao(String id);
    public int writeDao(String writer, String title, String content);
    public int deleteDao(String id);




    public int updateDao(String id, String writer, String title, String content);
}
