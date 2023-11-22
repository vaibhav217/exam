package com.skf.workshop.workshop.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import com.skf.workshop.workshop.model.Page;


@Component
public class DashboardDAO {

@Autowired
private JdbcTemplate jdbcTemplate;

    public List<Page> getPage(int pageId){
        String sql = "SELECT pageId, title, content FROM pages WHERE pageId=?";
        List<Page> pages = jdbcTemplate.query(sql,new Object[]{pageId} ,(resultSet, rowNum) -> new Page(resultSet.getInt("pageId"),resultSet.getString("title"), resultSet.getString("content")));
        return pages;
    }
    
}
