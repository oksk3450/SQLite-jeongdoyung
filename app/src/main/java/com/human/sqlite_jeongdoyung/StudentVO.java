package com.human.sqlite_jeongdoyung;
/*
StudentVO클래스는 xml과 DB와 메인액티비티java와 데이터를 GET/SET 하기위해서
 */
public class StudentVO {
    //VO클래스의 멤버변수(클래스전역변수)
    //Cursor id(레코드-한줄아이디)
    private int mId;
    //학년
    private int mGrade;
    //학번
    private int mNumber;
    //이름
    private String mName;

    public StudentVO(int p_id, int p_grade, int p_number, String p_name) {
        mId = p_id;
        mGrade = p_grade;
        mNumber = p_number;
        mName = p_name;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmGrade() {
        return mGrade;
    }

    public void setmGrade(int mGrade) {
        this.mGrade = mGrade;
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
