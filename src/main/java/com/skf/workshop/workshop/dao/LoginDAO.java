package com.skf.workshop.workshop.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import com.skf.workshop.workshop.model.User;


@Component
public class LoginDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getUser(String username){
        String sql = "SELECT UserId, UsrName, UsrRole, FullName, PassWord,Picture,banner, job, overview, exp, linkedin FROM users WHERE UsrName=?";
        List<User> users = jdbcTemplate.query(sql,new Object[]{username} ,(resultSet, rowNum) -> new User(resultSet.getInt("UserId"),resultSet.getString("UsrName"),resultSet.getString("PassWord"),resultSet.getString("UsrRole"),resultSet.getString("FullName"),resultSet.getString("Picture"),resultSet.getString("banner"),resultSet.getString("job"),resultSet.getString("overview"),resultSet.getString("exp"),resultSet.getString("linkedin")));
        return users;
    }

}
