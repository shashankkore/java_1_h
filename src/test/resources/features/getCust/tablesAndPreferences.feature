@GetCustTablesAndPreferences
Feature: Get Customer - Tables and Preferences

  @tablesAndPrefs
  Scenario Outline: Get Customer, Customer_Email, Customer_Phone and Customer_Address table Information
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <searchTerm> at <node> as "default"
    And I get Customer table information as
      | first_name | last_name | middle_initial_txt | gndr_cd |
      | Gordon     | Nonie     | U                  | F       |
    And I get Customer Email table information as
      | email_addr_txt          | email_addr_type_cd |
      | emailcvs99900@gmail.com | H                  |
    And I get Customer Phone table information as
      | phone_area_cd_nbr | phone_pfx_nbr | phone_type_cd | phone_sfx_nbr |
      | 401               | 780           | M             | 9000          |
    And I get Customer Address table information as
      | addr1_txt        | addr2_txt | city_name  | st_cd | zip_cd |
      | 99900, CVS Drive | APT NO 1  | WOONSOCKET | RI    | 02895  |
    Examples:
      | xtraCard   | testDataFile      | cardType | channel | searchTerm    | node       |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCardNbr" | "xtraCard" |

  @tablesAndPrefs
  Scenario Outline: Verify GetCust xtraCarePrefs - PaperlessCpns, BeautyClub, DiabeticClub, Phr - Never Enrolled
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <xtraCarePrefs> at <node> has <enrollment> status <Status>
    Examples:
      | xtraCard   | testDataFile      | cardType | channel | node            | xtraCarePrefs   | enrollment | Status |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "paperlessCpns" | "enrolled" | "N"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "beautyClub"    | "enrolled" | "N"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "diabeticClub"  | "enrolled" | "N"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "phr"           | "enrolled" | "N"    |

  @tablesAndPrefs
  Scenario Outline: Verify GetCust xtraCarePrefs - Phr - Enrolled
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    And I enrolled into PHR
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <xtraCarePrefs> at <node> has <enrollment> status <Status>
    Examples:
      | xtraCard   | testDataFile      | cardType | channel | node            | xtraCarePrefs | enrollment | Status |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "phr"         | "enrolled" | "Y"    |

  @tablesAndPrefs
  Scenario Outline: Verify GetCust xtraCarePrefs - CarePass - Enrolled
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    And I "carePass_Enroll_Monthly" via setCust
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <xtraCarePrefs> at <node> has <enrollment> status <Status>
    Examples:
      | xtraCard   | testDataFile      | cardType | channel | node            | xtraCarePrefs | enrollment | Status |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "carePass"    | "statusCd" | "E"    |

  @tablesAndPrefs
  Scenario Outline: Verify GetCust xtraCarePrefs - CarePass - UnEnrolled
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    And I "carePass_Enroll_Monthly" via setCust
    And I "carePass_UnEnroll_Monthly" via setCust
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <xtraCarePrefs> at <node> has <enrollment> status <Status>
    Examples:
      | xtraCard   | testDataFile      | cardType | channel | node            | xtraCarePrefs | enrollment | Status |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "carePass"    | "statusCd" | "U"   |
#      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "carePass"    | "statusCd" | "CI"    |

  @tablesAndPrefs
  Scenario Outline: Verify GetCust xtraCarePrefs - digitalReceipt, optInEmail, optInEc - OptedIn
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    And I "OptInEmail" via setCust
    And I "OptInMail" via setCust
    And I "OptInDigitalReceipt" via setCust
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <xtraCarePrefs> at <node> as <status>
    Examples:
      | xtraCard   | testDataFile      | cardType | channel | node            | xtraCarePrefs    | status |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "optInEmail"     | "A"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "optInEc"        | "I"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "digitalReceipt" | "D"    |

  @tablesAndPrefs
  Scenario Outline: Verify GetCust xtraCarePrefs - digitalReceipt, optInEmail, optInEc - OptedOut
    Given I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have card type <cardType> at <channel>
    And I "OptOutEmail" via setCust
    And I "OptOutMail" via setCust
    And I "OptOutDigitalReceipt" via setCust
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response should have <xtraCarePrefs> at <node> as <status>
    Examples:
#     Need to setup Test data to enroll into different xtraCarePrefs
      | xtraCard   | testDataFile      | cardType | channel | node            | xtraCarePrefs    | status |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "optInEmail"     | "U"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "optInEc"        | "O"    |
      | "XC_99900" | "createExtraCard" | "0002"   | "W"     | "xtraCarePrefs" | "digitalReceipt" | "P"    |
# WIP - Need to setup Test data to enroll into different xtraCarePrefs
#      | "XC_99900" | "createExtraCard" |"0002"   | "W"      | "xtraCarePrefs" | "sms"         | "N"    |
