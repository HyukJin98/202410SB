package edu.du.sb1008.dao;



import edu.du.sb1008.dto.SimpleBdsDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ISimpleBdsDao {
    public List<SimpleBdsDto> listDao();
    public Object viewDao(String id);
    public int writeDao(String writer, String title, String content);
    @Delete("delete from simple_bbs where id = #{id}")
    public int deleteDao(String id);
    @Update("update simple_bbs set writer = #{writer}, title = #{title}, content = #{content} where id = #{id}")
    public int updateDao(String id, String writer, String title, String content);
}
