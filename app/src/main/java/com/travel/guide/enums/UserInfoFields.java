package com.travel.guide.enums;

public enum UserInfoFields {

    NAME(1),
    SURNAME(2),
    NICKNAME(3),
    BIRTH_DATE(4),
    EMAIL(5),
    PHONE_NUMBER(6),
    COUNTRY(7),
    CITY(8),
    BIO(9),
    GENDER(10),
    PROFILE_PIC(11);

    private int value;

    UserInfoFields(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
