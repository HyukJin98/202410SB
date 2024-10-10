package edu.du.chap17.dao;

import edu.du.chap17.model.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper
public interface ArticleDao {

	@Select("select count(*) from article")
	public int selectCount();

	@Select("select article_id id, group_id groupId, sequence_no sequenceNumber, posting_date postingDate, read_count readCount, writer_name writerName, password, title from article order by sequence_no desc limit #{firstRow}, #{endRow}")
	public List<Article> select(int firstRow, int endRow);

	@Insert("insert into article(title,writerName,password,content) values (#{title},#{writerName},#{password},#{content})")
	public int insert(Article article);

	@Select("select article_id id, group_id groupId, sequence_no sequenceNumber, posting_date postingDate, read_count readCount, writer_name writerName, password, title, content from article where article_id = #{articleId}")
	public Article selectById(int articleId);

	@Update("update article "
			+ "set read_count = read_count + 1 "
			+ "where article_id = #{articleId}")
	public void increaseReadCount(int articleId);
	public String selectLastSequenceNumber(String searchMaxSeqNum, String searchMinSeqNum);
	public int update(Article article);
	public void delete(int articleId);

}