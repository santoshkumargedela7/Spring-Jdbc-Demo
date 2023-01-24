package com.siva.SpringJDBCDemo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.siva.SpringJDBCDemo.model.Tutorial;

@Repository
public class JDBCTutorialRepo implements TutorialRepository{
		
	@Autowired
	private JdbcTemplate template;
	
	@Override
	  public int save(Tutorial tutorial) {
	    return template.update("INSERT INTO tutorials (title, description, published) VALUES(?,?,?)",
	        new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished() });
	  }

	@Override
	  public int update(Tutorial tutorial) {
	    return template.update("UPDATE tutorials SET title=?, description=?, published=? WHERE id=?",
	        new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId() });
	  }

	@Override
	  public Tutorial findById(Long id) {
	    try {
	      Tutorial tutorial = template.queryForObject("SELECT * FROM tutorials WHERE id=?",
	          BeanPropertyRowMapper.newInstance(Tutorial.class), id);

	      return tutorial;
	    } catch (IncorrectResultSizeDataAccessException e) {
	      return null;
	    }
	  }

	@Override
	  public int deleteById(Long id) {
	    return template.update("DELETE FROM tutorials WHERE id=?", id);
	  }

	@Override
	  public List<Tutorial> findAll() {
	    return template.query("SELECT * from tutorials", BeanPropertyRowMapper.newInstance(Tutorial.class));
	  }

	@Override
	public List<Tutorial> findByPublished(boolean published) {
		
		return template.query("select * from tutorials where published = ?", 
				BeanPropertyRowMapper.newInstance(Tutorial.class),published);
	}

	@Override
	  public List<Tutorial> findByTitleContaining(String title) {
	    String q = "SELECT * from tutorials WHERE title ILIKE '%" + title + "%'";
	   
	    return template.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
	  }

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return template.update("delete from tutorials");
	}

	@Override
	public List<Tutorial> findByIdAndTitle(long id, String title) {
		
		return template.query("select * from tutorials where id =? OR title =?", BeanPropertyRowMapper.newInstance(Tutorial.class),id,title);
	}

}
