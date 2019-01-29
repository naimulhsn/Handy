package com.skillhunternaim.anotherloser.alonechat;


public class ModelBatchStudent {
    private String profilePic,
            fullname, academic_id, gender ,
            phone1, phone2, email, blood ,
            current_address, hometown,
            university, dept , batch ,userId;

    public ModelBatchStudent() {
    }

    public ModelBatchStudent(String profilePic, String fullname, String academic_id,
                        String gender, String phone1, String phone2,
                        String email, String blood, String current_address,
                        String hometown, String university,
                        String dept, String batch,String userId) {
        this.profilePic = profilePic;
        this.fullname = fullname;
        this.academic_id = academic_id;
        this.gender = gender;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.blood = blood;
        this.current_address = current_address;
        this.hometown = hometown;
        this.university = university;
        this.dept = dept;
        this.batch = batch;
        this.userId=userId;
    }

    //Getter Setter
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAcademic_id() {
        return academic_id;
    }

    public void setAcademic_id(String academic_id) {
        this.academic_id = academic_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
