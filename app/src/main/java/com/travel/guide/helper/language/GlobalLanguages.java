package com.travel.guide.helper.language;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalLanguages implements Parcelable {

    @Expose
    @SerializedName("gender_restriction_warning")
    private String gender_restriction_warning;

    @Expose
    @SerializedName("age_restriction_warning")
    private String age_restriction_warning;

    @Expose
    @SerializedName("cancel")
    private String cancel;

    @Expose
    @SerializedName("other")
    private String other;

    @Expose
    @SerializedName("female")
    private String female;

    @Expose
    @SerializedName("male")
    private String male;

    @Expose
    @SerializedName("confirm_password_filed_head")
    private String confirm_password_filed_head;

    @Expose
    @SerializedName("phone_number_filed_head")
    private String phone_number_filed_head;

    @Expose
    @SerializedName("birthdate_field_head")
    private String birthdate_field_head;

    @Expose
    @SerializedName("nickname_field_head")
    private String nickname_field_head;

    @Expose
    @SerializedName("surname_field_head")
    private String surname_field_head;

    @Expose
    @SerializedName("name_field_head")
    private String name_field_head;

    @Expose
    @SerializedName("upload_photo")
    private String upload_photo;

    @Expose
    @SerializedName("and")
    private String and;

    @Expose
    @SerializedName("privacy_policy")
    private String privacy_policy;

    @Expose
    @SerializedName("terms_of_services")
    private String terms_of_services;

    @Expose
    @SerializedName("privacy_head")
    private String privacy_head;

    @Expose
    @SerializedName("connect_with-offer-body")
    private String connect_with_offer_body;

    @Expose
    @SerializedName("sign_in")
    private String sign_in;

    @Expose
    @SerializedName("sign_up")
    private String sign_up;

    @Expose
    @SerializedName("sign_up_offer_body")
    private String sign_up_offer_body;

    @Expose
    @SerializedName("forgot_password")
    private String forgot_password;

    @Expose
    @SerializedName("password_field_head")
    private String password_field_head;

    @Expose
    @SerializedName("email_field_head")
    private String email_field_head;


    public String getGender_restriction_warning() {
        return gender_restriction_warning;
    }

    public String getAge_restriction_warning() {
        return age_restriction_warning;
    }

    public String getCancel() {
        return cancel;
    }

    public String getOther() {
        return other;
    }

    public String getFemale() {
        return female;
    }

    public String getMale() {
        return male;
    }

    public String getConfirm_password_filed_head() {
        return confirm_password_filed_head;
    }

    public String getPhone_number_filed_head() {
        return phone_number_filed_head;
    }

    public String getBirthdate_field_head() {
        return birthdate_field_head;
    }

    public String getNickname_field_head() {
        return nickname_field_head;
    }

    public String getSurname_field_head() {
        return surname_field_head;
    }

    public String getName_field_head() {
        return name_field_head;
    }

    public String getUpload_photo() {
        return upload_photo;
    }

    public String getAnd() {
        return and;
    }

    public String getPrivacy_policy() {
        return privacy_policy;
    }

    public String getTerms_of_services() {
        return terms_of_services;
    }

    public String getPrivacy_head() {
        return privacy_head;
    }

    public String getConnect_with_offer_body() {
        return connect_with_offer_body;
    }

    public String getSign_in() {
        return sign_in;
    }

    public String getSign_up() {
        return sign_up;
    }

    public String getSign_up_offer_body() {
        return sign_up_offer_body;
    }

    public String getForgot_password() {
        return forgot_password;
    }

    public String getPassword_field_head() {
        return password_field_head;
    }

    public String getEmail_field_head() {
        return email_field_head;
    }

    private GlobalLanguages(Parcel in) {
        gender_restriction_warning = in.readString();
        age_restriction_warning = in.readString();
        cancel = in.readString();
        other = in.readString();
        female = in.readString();
        male = in.readString();
        confirm_password_filed_head = in.readString();
        phone_number_filed_head = in.readString();
        birthdate_field_head = in.readString();
        nickname_field_head = in.readString();
        surname_field_head = in.readString();
        name_field_head = in.readString();
        upload_photo = in.readString();
        and = in.readString();
        privacy_policy = in.readString();
        terms_of_services = in.readString();
        privacy_head = in.readString();
        connect_with_offer_body = in.readString();
        sign_in = in.readString();
        sign_up = in.readString();
        sign_up_offer_body = in.readString();
        forgot_password = in.readString();
        password_field_head = in.readString();
        email_field_head = in.readString();
    }

    public static final Creator<GlobalLanguages> CREATOR = new Creator<GlobalLanguages>() {
        @Override
        public GlobalLanguages createFromParcel(Parcel in) {
            return new GlobalLanguages(in);
        }

        @Override
        public GlobalLanguages[] newArray(int size) {
            return new GlobalLanguages[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender_restriction_warning);
        dest.writeString(age_restriction_warning);
        dest.writeString(cancel);
        dest.writeString(other);
        dest.writeString(female);
        dest.writeString(male);
        dest.writeString(confirm_password_filed_head);
        dest.writeString(phone_number_filed_head);
        dest.writeString(birthdate_field_head);
        dest.writeString(nickname_field_head);
        dest.writeString(surname_field_head);
        dest.writeString(name_field_head);
        dest.writeString(upload_photo);
        dest.writeString(and);
        dest.writeString(privacy_policy);
        dest.writeString(terms_of_services);
        dest.writeString(privacy_head);
        dest.writeString(connect_with_offer_body);
        dest.writeString(sign_in);
        dest.writeString(sign_up);
        dest.writeString(sign_up_offer_body);
        dest.writeString(forgot_password);
        dest.writeString(password_field_head);
        dest.writeString(email_field_head);
    }
}
