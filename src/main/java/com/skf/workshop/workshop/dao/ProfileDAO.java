package com.skf.workshop.workshop.dao;

import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import com.skf.workshop.workshop.model.User;

@Component
public class ProfileDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getProfile(String userId){
        String sql = "SELECT UserId, UsrName, UsrRole, FullName, PassWord,Picture,banner, job, overview, exp, linkedin FROM users WHERE UserId="+userId;
        List<User> users = jdbcTemplate.query(sql ,(resultSet, rowNum) -> new User(resultSet.getInt("UserId"),resultSet.getString("UsrName"), resultSet.getString("PassWord"),resultSet.getString("UsrRole"),resultSet.getString("FullName"),resultSet.getString("Picture"),resultSet.getString("banner"),resultSet.getString("job"),resultSet.getString("overview"),resultSet.getString("exp"),resultSet.getString("linkedin")));
        return users;
    }
    
    public List<User> getProfiles(){
        String sql = "SELECT UserId, UsrName,UsrRole, FullName, PassWord,Picture,banner, job, overview, exp, linkedin FROM users";
        List<User> users = jdbcTemplate.query(sql ,(resultSet, rowNum) -> new User(resultSet.getInt("UserId"),resultSet.getString("UsrName"), resultSet.getString("PassWord"),resultSet.getString("UsrRole"),resultSet.getString("FullName"),resultSet.getString("Picture"),resultSet.getString("banner"),resultSet.getString("job"),resultSet.getString("overview"),resultSet.getString("exp"),resultSet.getString("linkedin")));
        return users;
    }

    public void updateProfile(int userId, String fullName, String job, String overview, String exp, String linkedin){
        String sql = "UPDATE users SET FullName=?, job=?, overview=?, exp=?, linkedin=? where userid=?";
        Object[] params = {fullName, job, overview, exp, linkedin,userId};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.INTEGER};
        jdbcTemplate.update(sql, params, types);
    }

    public void updatePicture(int userId, String picture){
        String sql = "UPDATE users SET Picture=? where userid=?";
        Object[] params = {picture, userId};
        int[] types = {Types.VARCHAR,Types.INTEGER};
        jdbcTemplate.update(sql, params, types);
    }
}
