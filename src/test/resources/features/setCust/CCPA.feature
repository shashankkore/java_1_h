@SetCust_CCPA
Feature: SetCust - CCPA

  @SetCust @CCPA
  Scenario Outline: setCust - CCPA - Request Data Report and Data Purge at different states
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I want to request <data_request> from <state>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And My xtraCard exists in "XTRA_PRIVACY_REQUEST" table
    And The XTRA_PRIVACY_REQUEST table should have correct <data_request> values
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                              | data_request  | state | scenario                                                                                                         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Report" | "CA"  | "SetCust - CCPA, Request Data Report from State='CA', Channel = 'W' with 'Web CPA'"                              |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Report" | "CA"  | "SetCust - CCPA, Request Data Report by excluding optional fields from State='CA', Channel = 'W' with 'Web CPA'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Report" | "VA"  | "SetCust - CCPA, Request Data Report from State='VA', Channel = 'W' with 'Web CPA'"                              |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Report" | "VA"  | "SetCust - CCPA, Request Data Report by excluding optional fields from State='VA', Channel = 'W' with 'Web CPA'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Report" | "RI"  | "SetCust - CCPA, Request Data Report from State='RI', Channel = 'W' with 'Web CPA'"                              |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Report" | "RI"  | "SetCust - CCPA, Request Data Report by excluding optional fields from State='RI', Channel = 'W' with 'Web CPA'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Report" | ""    | "SetCust - CCPA, Request Data Report from State=NULL, Channel = 'W' with 'Web CPA'"                              |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Report" | ""    | "SetCust - CCPA, Request Data Report by excluding optional fields from State=NULL, Channel = 'W' with 'Web CPA'" |

#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Purge" | "CA"  | "SetCust - CCPA, Request Data Purge from State='CA', Channel = 'W' with 'Web CPA'"                                  |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Purge" | "CA"  | "SetCust - CCPA, Request Data Purge by excluding optional fields from State='CA', Channel = 'W' with 'Web CPA'"     |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Purge" | "VA"  | "SetCust - CCPA, Request Data Purge from State='VA', Channel = 'W' with 'Web CPA'"                                  |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Purge" | "VA"  | "SetCust - CCPA, Request Data Purge by excluding optional fields from State='VA', Channel = 'W' with 'Web CPA'"     |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Purge" | "MA"  | "SetCust - CCPA, Request Data Purge from State='MA', Channel = 'W' with 'Web CPA'"                                  |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Purge" | "MA"  | "SetCust - CCPA, Request Data Purge by excluding optional fields from State='MA', Channel = 'W' with 'Web CPA'"     |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data"                         | "Data Purge" | ""  | "SetCust - CCPA, Request Data Purge from State=NULL, Channel = 'W' with 'Web CPA'"                                  |
#      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "CCPA_Data_Exclude_Optional_Fields" | "Data Purge" | ""  | "SetCust - CCPA, Request Data Purge by excluding optional fields from State=NULL, Channel = 'W' with 'Web CPA'"     |

