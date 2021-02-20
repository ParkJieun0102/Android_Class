package com.android.dbcrud01;

// Bean 이라서 상속(extends) 할 필요성이 없다.
public class Student {

    //Field (필드) = Bean 에선 이렇게 한줄 씩 쓰는 것이 좋다.
    String code;
    String name;
    String dept;
    String phone;

    // Constructor (생성자)
    public Student(String code, String name, String dept, String phone) {
        this.code = code;
        this.name = name;
        this.dept = dept;
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
