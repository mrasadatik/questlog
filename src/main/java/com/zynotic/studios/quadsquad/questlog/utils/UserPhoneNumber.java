package com.zynotic.studios.quadsquad.questlog.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class UserPhoneNumber {
    private static final String COUNTRY_CALLING_CODE_DATA = "/data/countryCallingCode.json";
    private static Map<String, String> countryCallingCodes;

    static {
        try {
            loadCountryCallingCodes();
        } catch (IOException e) {
            //
        }
    }
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
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

    private static void loadCountryCallingCodes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream inputStream = UserPhoneNumber.class.getResourceAsStream(COUNTRY_CALLING_CODE_DATA);
        if (inputStream == null) {
            throw new IOException("File not found: " + COUNTRY_CALLING_CODE_DATA);
        }

        countryCallingCodes = objectMapper.readValue(inputStream, new TypeReference<Map<String, String>>() {});
    }

    public static String[] getCountries() throws IOException {
        if (countryCallingCodes == null) {
            loadCountryCallingCodes();
        }
        return countryCallingCodes.keySet().toArray(new String[0]);
    }

    public static String getCountryCallingCode(String countryCode) {
        if (countryCallingCodes == null) {
            try {
                loadCountryCallingCodes();
            } catch (IOException e) {
                return null;
            }
        }
        return countryCallingCodes.get(countryCode);
    }

    public static String getExampleNumber(String countryCode, PhoneNumberUtil.PhoneNumberType type) {
        return String.valueOf(phoneNumberUtil.getExampleNumberForType(countryCode, type).getNationalNumber());
    }
}
