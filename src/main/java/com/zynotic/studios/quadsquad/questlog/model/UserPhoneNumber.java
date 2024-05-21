package com.zynotic.studios.quadsquad.questlog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class UserPhoneNumber {
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_calling_code")
    private String countryCallingCode;
    @JsonProperty("national_number")
    private String nationalNumber;

    public UserPhoneNumber(String countryCode, String countryCallingCode, String nationalNumber) {
        this.countryCode = countryCode;
        this.countryCallingCode = countryCallingCode;
        this.nationalNumber = nationalNumber;
    }

    public String getCountry() {
        return countryCode;
    }
    public void setCountry(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }
    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public String getNationalNumber() {
        phoneNumber.setCountryCode(Integer.parseInt(countryCallingCode));
        phoneNumber.setNationalNumber(Long.parseLong(nationalNumber));
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }
    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public String getInternationalNumber() {
        phoneNumber.setCountryCode(Integer.parseInt(countryCallingCode));
        phoneNumber.setNationalNumber(Long.parseLong(nationalNumber));
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }
}
