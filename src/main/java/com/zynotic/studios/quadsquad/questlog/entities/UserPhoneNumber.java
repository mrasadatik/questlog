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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.zynotic.studios.quadsquad.questlog.annotations.AllowedValues;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a user's phone number in the system.
 * This class provides methods to format phone numbers and retrieve country codes.
 */
public class UserPhoneNumber implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private static final String COUNTRY_CALLING_CODE_DATA = "database/countryCallingCode.json"; // File path for country calling codes data
    @JsonIgnore
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance(); // PhoneNumberUtil instance
    @JsonIgnore
    private static Map<String, String> countryCallingCodes; // Map to store country calling codes

    static {
        try {
            loadCountryCallingCodes(); // Load country calling codes from file
        } catch (IOException e) {
            // Handle the exception
        }
    }

    @JsonIgnore
    private final Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber(); // PhoneNumber instance
    @NotNull(message = "Country code cannot be null")
    @NotBlank(message = "Country code cannot be blank")
    @Length(min = 2, max = 2, message = "Country code should be 2 characters long")
    @AllowedValues(valueList = {"AC", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CW", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SV", "SX", "SY", "SZ", "TA", "TC", "TD", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "XK", "YE", "YT", "ZA", "ZM", "ZW"})
    @JsonProperty("countryCode")
    private String countryCode; // The country code of the user's phone number

    @NotNull(message = "Country calling code cannot be null")
    @NotBlank(message = "Country calling code cannot be blank")
    @Length(min = 1, max = 4, message = "Country calling code should be at least 1 character long and less than 4")
    @AllowedValues(valueList = {"247", "376", "971", "93", "1", "355", "374", "244", "54", "43", "61", "297", "358", "994", "387", "880", "32", "226", "359", "973", "257", "229", "590", "673", "591", "599", "55", "975", "267", "375", "501", "243", "236", "242", "41", "225", "682", "56", "237", "86", "57", "506", "53", "238", "357", "420", "49", "253", "45", "213", "593", "372", "20", "212", "291", "34", "251", "679", "500", "691", "298", "33", "241", "44", "995", "594", "233", "350", "299", "220", "224", "240", "30", "502", "245", "592", "852", "504", "385", "509", "36", "62", "353", "972", "91", "246", "964", "98", "354", "39", "962", "81", "254", "996", "855", "686", "269", "850", "82", "965", "7", "856", "961", "423", "94", "231", "266", "370", "352", "371", "218", "377", "373", "382", "261", "692", "389", "223", "95", "976", "853", "596", "222", "356", "230", "960", "265", "52", "60", "258", "264", "687", "227", "672", "234", "505", "31", "47", "977", "674", "683", "64", "968", "507", "51", "689", "675", "63", "92", "48", "508", "970", "351", "680", "595", "974", "262", "40", "381", "250", "966", "677", "248", "249", "46", "65", "290", "386", "421", "232", "378", "221", "252", "597", "211", "239", "503", "963", "268", "235", "228", "66", "992", "690", "670", "993", "216", "676", "90", "688", "886", "255", "380", "256", "598", "998", "58", "84", "678", "681", "685", "383", "967", "27", "260", "263"})
    @JsonProperty("countryCallingCode")
    private String countryCallingCode; // The country calling code of the user's phone number

    @NotNull(message = "National number cannot be null")
    @NotBlank(message = "National number cannot be blank")
    @JsonProperty("nationalNumber")
    private String nationalNumber; // The national number of the user's phone number

    @NotNull(message = "Phone number type cannot be null")
    @NotBlank(message = "Phone number type cannot be blank")
    @AllowedValues(enumClass = PhoneNumberUtil.PhoneNumberType.class)
    @JsonProperty("numberType")
    private PhoneNumberUtil.PhoneNumberType numberType; // The type of phone number.

    // Default constructor
    public UserPhoneNumber() {
    }

    /**
     * Constructs a UserPhoneNumber object with the specified country code, country calling code, national number, and phone number type.
     * <br>
     * Possible number types are:
     * <ul>
     *     <li>FIXED_LINE</li>
     *     <li>FIXED_LINE_OR_MOBILE</li>
     *     <li>MOBILE</li>
     *     <li>PAGER</li>
     *     <li>PERSONAL_NUMBER</li>
     *     <li>PREMIUM_RATE</li>
     *     <li>SHARED_COST</li>
     *     <li>TOLL_FREE</li>
     *     <li>UAN</li>
     *     <li>UNKNOWN</li>
     *     <li>VOICEMAIL</li>
     *     <li>VOIP</li>
     * </ul>
     *
     * @param countryCode    The two-letter country code (ISO 3166-1 alpha-2) of the phone number's country.
     * @param nationalNumber The national number (without the country calling code).
     * @param numberType     The type of phone number (e.g., MOBILE, FIXED_LINE).
     */
    public UserPhoneNumber(
            @NotNull(message = "Country code cannot be null")
            @NotBlank(message = "Country code cannot be blank")
            @Length(min = 2, max = 2, message = "Country code should be 2 characters long")
            @AllowedValues(valueList = {"AC", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CW", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SV", "SX", "SY", "SZ", "TA", "TC", "TD", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "XK", "YE", "YT", "ZA", "ZM", "ZW"})
            String countryCode,

            @NotNull(message = "National number cannot be null")
            @NotBlank(message = "National number cannot be blank")
            String nationalNumber,

            @NotNull(message = "Phone number type cannot be null")
            @NotBlank(message = "Phone number type cannot be blank")
            @AllowedValues(enumClass = PhoneNumberUtil.PhoneNumberType.class)
            PhoneNumberUtil.PhoneNumberType numberType
    ) {
        // Set country code and national number for phoneNumber
        phoneNumber.setCountryCode(Integer.parseInt(Objects.requireNonNull(getCountryCallingCode(countryCode))));
        phoneNumber.setNationalNumber(Long.parseLong(nationalNumber));


        this.countryCode = countryCode;
        this.countryCallingCode = getCountryCallingCode(countryCode);
        this.numberType = numberType;

        this.nationalNumber = getNationalNumber();
    }

    @JsonIgnore
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
    @JsonIgnore
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
    public static String getCountryCallingCode(
            @NotNull(message = "Country code cannot be null")
            @NotBlank(message = "Country code cannot be blank")
            @Length(min = 2, max = 2, message = "Country code should be 2 characters long")
            @AllowedValues(valueList = {"AC", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CW", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SV", "SX", "SY", "SZ", "TA", "TC", "TD", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "XK", "YE", "YT", "ZA", "ZM", "ZW"})
            String countryCode
    ) {
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
     * <br>
     * Possible types are:
     * <ul>
     *     <li>FIXED_LINE</li>
     *     <li>FIXED_LINE_OR_MOBILE</li>
     *     <li>MOBILE</li>
     *     <li>PAGER</li>
     *     <li>PERSONAL_NUMBER</li>
     *     <li>PREMIUM_RATE</li>
     *     <li>SHARED_COST</li>
     *     <li>TOLL_FREE</li>
     *     <li>UAN</li>
     *     <li>UNKNOWN</li>
     *     <li>VOICEMAIL</li>
     *     <li>VOIP</li>
     * </ul>
     *
     * @param countryCode The country code.
     * @param type        the type of phone number to check.
     * @return The example phone number.
     */
    @JsonIgnore
    public static String getExampleNumber(
            @NotNull(message = "Country code cannot be null")
            @NotBlank(message = "Country code cannot be blank")
            @Length(min = 2, max = 2, message = "Country code should be 2 characters long")
            @AllowedValues(valueList = {"AC", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CW", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SV", "SX", "SY", "SZ", "TA", "TC", "TD", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "XK", "YE", "YT", "ZA", "ZM", "ZW"})
            String countryCode,

            @NotNull(message = "Phone number type cannot be null")
            @NotBlank(message = "Phone number type cannot be blank")
            @AllowedValues(enumClass = PhoneNumberUtil.PhoneNumberType.class)
            PhoneNumberUtil.PhoneNumberType type
    ) {
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
    public void setCountryCode(
            @NotNull(message = "Country code cannot be null")
            @NotBlank(message = "Country code cannot be blank")
            @Length(min = 2, max = 2, message = "Country code should be 2 characters long")
            @AllowedValues(valueList = {"AC", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AO", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CW", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GT", "GU", "GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SV", "SX", "SY", "SZ", "TA", "TC", "TD", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "XK", "YE", "YT", "ZA", "ZM", "ZW"})
            String countryCode
    ) {
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
    public void setCountryCallingCode(
            @NotNull(message = "Country calling code cannot be null")
            @NotBlank(message = "Country calling code cannot be blank")
            @Length(min = 1, max = 4, message = "Country calling code should be at least 1 character long and less than 4")
            @AllowedValues(valueList = {"247", "376", "971", "93", "1", "355", "374", "244", "54", "43", "61", "297", "358", "994", "387", "880", "32", "226", "359", "973", "257", "229", "590", "673", "591", "599", "55", "975", "267", "375", "501", "243", "236", "242", "41", "225", "682", "56", "237", "86", "57", "506", "53", "238", "357", "420", "49", "253", "45", "213", "593", "372", "20", "212", "291", "34", "251", "679", "500", "691", "298", "33", "241", "44", "995", "594", "233", "350", "299", "220", "224", "240", "30", "502", "245", "592", "852", "504", "385", "509", "36", "62", "353", "972", "91", "246", "964", "98", "354", "39", "962", "81", "254", "996", "855", "686", "269", "850", "82", "965", "7", "856", "961", "423", "94", "231", "266", "370", "352", "371", "218", "377", "373", "382", "261", "692", "389", "223", "95", "976", "853", "596", "222", "356", "230", "960", "265", "52", "60", "258", "264", "687", "227", "672", "234", "505", "31", "47", "977", "674", "683", "64", "968", "507", "51", "689", "675", "63", "92", "48", "508", "970", "351", "680", "595", "974", "262", "40", "381", "250", "966", "677", "248", "249", "46", "65", "290", "386", "421", "232", "378", "221", "252", "597", "211", "239", "503", "963", "268", "235", "228", "66", "992", "690", "670", "993", "216", "676", "90", "688", "886", "255", "380", "256", "598", "998", "58", "84", "678", "681", "685", "383", "967", "27", "260", "263"})
            String countryCallingCode
    ) {
        this.countryCallingCode = countryCallingCode;
    }

    /**
     * Gets the national number of the user's phone number.
     *
     * @return The national number.
     */
    @JsonIgnore
    public String getNationalNumber() {
        return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }

    /**
     * Sets the national number of the user's phone number.
     *
     * @param nationalNumber The national number to set.
     */
    public void setNationalNumber(
            @NotNull(message = "National number cannot be null")
            @NotBlank(message = "National number cannot be blank")
            String nationalNumber
    ) {
        this.nationalNumber = nationalNumber;
    }

    /**
     * Gets the formatted representation of the user's phone number according to the specified format.
     * <br>
     * Possible formats are:
     * <ul>
     *     <li>E164: The international format without formatting characters but with a leading '+' sign.</li>
     *     <li>INTERNATIONAL: The international format with country code, but without any formatting characters.</li>
     *     <li>NATIONAL: The national format suitable for display to the user.</li>
     *     <li>RFC3966: The format specified by the RFC 3966 standard, also known as the "tel:" URL format.</li>
     * </ul>
     *
     * @param phoneNumberFormat The format in which the phone number should be displayed.
     * @return The formatted representation of the phone number.
     */
    @JsonIgnore
    public String getNumber(
            @NotNull(message = "Phone number format cannot be null")
            @NotBlank(message = "Phone number format cannot be blank")
            @AllowedValues(enumClass = PhoneNumberUtil.PhoneNumberFormat.class)
            PhoneNumberUtil.PhoneNumberFormat phoneNumberFormat
    ) {
        return phoneNumberUtil.format(phoneNumber, phoneNumberFormat);
    }


    /**
     * Checks if the user's phone number is valid.
     *
     * @return true if the phone number is valid, false otherwise.
     */
    @JsonIgnore
    public boolean isValid() {
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }

    /**
     * Checks if the user's phone number is possible.
     *
     * @return true if the phone number is possible, false otherwise.
     */
    @JsonIgnore
    public boolean isPossible() {
        return phoneNumberUtil.isPossibleNumberForType(phoneNumber, numberType);
    }

    /**
     * Checks if the user's phone number is possible.
     *
     * @return A string describing why the phone number is impossible, or null if the phone number is possible.
     */
    @JsonIgnore
    public String impossibleReason() {
        PhoneNumberUtil.ValidationResult reasons = phoneNumberUtil.isPossibleNumberWithReason(phoneNumber);
        return switch (reasons) {
            case IS_POSSIBLE -> null;
            case IS_POSSIBLE_LOCAL_ONLY ->
                    "The phone number format might only be valid for local calls. Consider including the country code.";
            case TOO_LONG -> "The phone number is too long.";
            case TOO_SHORT -> "The phone number is too short.";
            case INVALID_COUNTRY_CODE -> "The country code is invalid.";
            case INVALID_LENGTH -> "The phone number has an invalid length for this region.";
        };
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPhoneNumber that = (UserPhoneNumber) o;
        return countryCode.equals(that.countryCode) && countryCallingCode.equals(that.countryCallingCode) && nationalNumber.equals(that.nationalNumber) && numberType == that.numberType;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(countryCode, countryCallingCode, nationalNumber, numberType);
    }
}
