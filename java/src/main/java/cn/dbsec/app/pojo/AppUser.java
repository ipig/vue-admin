package cn.dbsec.app.pojo;

public class AppUser {
    private String id;

    private String phone;

    private String email;

    private String company;

    private int newLogCount;

    private String img = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getNewLogCount() {
        return newLogCount;
    }

    public void setNewLogCount(int newLogCount) {
        this.newLogCount = newLogCount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
