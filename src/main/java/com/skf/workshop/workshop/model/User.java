package com.skf.workshop.workshop.model;


public class User {
    
    private int userId;
    private String username;
    private String userrole;
    private String password;
    private String fullname;
    private String picture;
    private String banner;
    private String job;
    private String overview;
    private String exp;
    private String linkedin;


    public User(int userId, String username, String password, String userrole, String fullname, String picture, String banner,
            String job, String overview, String exp, String linkedin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userrole = userrole;
        this.fullname = fullname;
        this.picture = picture;
        this.banner = banner;
        this.job = job;
        this.overview = overview;
        this.exp = exp;
        this.linkedin = linkedin;
    }

    public int getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserrole() {
        return userrole;
    }
    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getExp() {
        return exp;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
    public String getLinkedin() {
        return linkedin;
    }
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d | username=%s | userrole=%s | password=%s | fullname=%s | picture=%s | banner=%s | job=%s | overview=%s | exp=%s | linkedin=%s ]",userId,username,userrole,password,fullname,picture,banner,job,overview,exp,linkedin);
    }
}
