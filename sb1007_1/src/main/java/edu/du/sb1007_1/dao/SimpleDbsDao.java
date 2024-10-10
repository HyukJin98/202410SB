package edu.du.sb1007_1.dao;

import edu.du.sb1007_1.dto.SimpleBdsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleDbsDao implements ISimpleBdsDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<SimpleBdsDto> listDao(){
        System.out.println("listDao()");

        String query = "select * from simple_bbs order by id desc";
        List<SimpleBdsDto> dtos = template.query(query, new BeanPropertyRowMapper<SimpleBdsDto>(SimpleBdsDto.class));

        return dtos;
    }

    @Override
    public SimpleBdsDto viewDao(String id){
        System.out.println("viewDao()");

        String query = "select * from simple_bbs where id = " + id;
        SimpleBdsDto dto = template.queryForObject(
                query, new BeanPropertyRowMapper<SimpleBdsDto>(SimpleBdsDto.class));
        return dto;
    }

    @Override
    public int writeDao(String writer, String title, String content){
        System.out.println("writeDao()");

        String query = "insert into simple_bbs (writer, title, content) values (?,?,?)";
        return template.update(query, writer, title, content);
    }

    @Override
    public int deleteDao(String id) {
        System.out.println("deleteDao()");

        String query = "delete from simple_bbs where id = ?";
        return template.update(query, Integer.parseInt(id));
    }

    @Override
    public int updateDao(String id, String writer, String title, String content){
        System.out.println("updateDao()");

        String query = "update simple_bbs set writer = ?, title = ?, content = ? where id = ?";
        return template.update(query, writer, title, content, Integer.parseInt(id));
    }
}
