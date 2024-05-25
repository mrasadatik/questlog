/*
 * UserPhoneNumber.java
 * Represents a user's phone number in the system.
 * Provides methods to format phone numbers and retrieve country codes.
 * Created by Md asaduzzaman Atik on Tuesday, May 21, 2024, 1:31:25 AM.
 * Copyright (C) 2024 Zynotic Studios, Quad Squad
 * Licensed under the GNU General Public License, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://www.gnu.org/licenses/gpl-3.0.html
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zynotic.studios.quadsquad.questlog.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
//
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
//
import java.util.Map;

/**
 * Represents a user's phone number in the system.
 * This class provides methods to format phone numbers and retrieve country codes.
 */
public class UserPhoneNumber implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String COUNTRY_CALLING_CODE_DATA = "database/countryCallingCode.json"; // File path for country calling codes data
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance(); // PhoneNumberUtil instance
    private static Map<String, String> countryCallingCodes; // Map to store country calling codes

    static {
        try {
            loadCountryCallingCodes(); // Load country calling codes from file
        } catch (IOException e) {
            // Handle the exception
        }
    }

    private final Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber(); // PhoneNumber instance
    @JsonProperty("country_code")
    private String countryCode; // The country code of the user's phone number
    @JsonProperty("country_calling_code")
    private String countryCallingCode; // The country calling code of the user's phone number
    @JsonProperty("national_number")
    private String nationalNumber; // The national number of the user's phone number

    /**
     * Constructs a UserPhoneNumber object with the specified country code, country calling code, and national number.
     *
     * @param countryCode        The country code.
     * @param countryCallingCode The country calling code.
     * @param nationalNumber     The national number.
     */
    public UserPhoneNumber(String countryCode, String countryCallingCode, String nationalNumber) {
        this.countryCode = countryCode;
        this.countryCallingCode = countryCallingCode;
        this.nationalNumber = nationalNumber;
    }

    private static void loadCountryCallingCodes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(COUNTRY_CALLING_CODE_DATA);
        countryCallingCodes = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {
        });
    }

    /**
     * Gets an array of country codes.
     *
     * @return An array of country codes.
     * @throws IOException If an I/O error occurs.
     */
    public static String[] getCountries() throws IOException {
        if (countryCallingCodes == null) {
            loadCountryCallingCodes();
        }
        return countryCallingCodes.keySet().toArray(new String[0]);
    }

    /**
     * Gets the country calling code for a specific country code.
     *
     * @param countryCode The country code.
     * @return The country calling code.
     */
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

    /**
     * Gets an example phone number for a specific country code and type.
     *
     * @param countryCode The country code.
     * @param type        The phone number type.
     * @return The example phone number.
     */
    public static String getExampleNumber(String countryCode, PhoneNumberUtil.PhoneNumberType type) {
        return String.valueOf(phoneNumberUtil.getExampleNumberForType(countryCode, type).getNationalNumber());
    }

    /**
     * Gets the country code of the user's phone number.
     *
     * @return The country code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the country code of the user's phone number.
     *
     * @param countryCode The country code to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the country calling code of the user's phone number.
     *
     * @return The country calling code.
     */
    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    /**
     * Sets the country calling code of the user's phone number.
     *
     * @param countryCallingCode The country calling code to set.
     */
    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    /**
     * Gets the national number of the user's phone number.
     *
     * @return The national number.
     */
    public String getNationalNumber() {
        phoneNumber.setCountryCode(Integer.parseInt(countryCallingCode));
        phoneNumber.setNationalNumber(Long.parseLong(nationalNumber));
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }

    /**
     * Sets the national number of the user's phone number.
     *
     * @param nationalNumber The national number to set.
     */
    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    /**
     * Gets the international format of the user's phone number.
     *
     * @return The international format of the phone number.
     */
    public String getInternationalNumber() {
        phoneNumber.setCountryCode(Integer.parseInt(countryCallingCode));
        phoneNumber.setNationalNumber(Long.parseLong(nationalNumber));
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }
}
