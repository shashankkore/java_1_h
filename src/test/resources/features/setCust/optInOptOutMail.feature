@SetCust_OptIn_OptOut_Mail
Feature: SetCust - OptIn/OptOut Mail

  @optInMail @SetCust
  Scenario Outline: setCust - Customer optIn Mail
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action      | scenario                                |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optInMail" | "SetCust - OptInMail, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optInMail" | "SetCust - OptInMail, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optInMail" | "SetCust - OptInMail, at Channel = 'R'" |

  @optOutMail @SetCust
  Scenario Outline: setCust - Customer optOut Mail
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action       | scenario                                 |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutMail" | "SetCust - OptOutMail, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutMail" | "SetCust - OptOutMail, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutMail" | "SetCust - OptOutMail, at Channel = 'R'" |

  @optInMail @optOutMail @SetCust
  Scenario Outline: setCust - Customer optOut Mail who is already optedIn
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try 'optInMail' via request
    And I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action       | scenario                                 |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutMail" | "SetCust - OptOutMail which is already opted In, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutMail" | "SetCust - OptOutMail which is already opted In, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutMail" | "SetCust - OptOutMail which is already opted In, at Channel = 'R'" |

  @optInMail @SetCust
  Scenario Outline: setCust - optInMail - Verify 'optCd=I' and 'optTypeCd=29' from customer_opt table
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And The response should have xtraCard details
    And The customerOpt table updated with optCd as <optCd> and optTypeCd as <optTypeCd>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action      | optCd | optTypeCd | scenario                                |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optInMail" | "I"   | "29"      | "SetCust - OptInMail verify customer_opt table updated with optCd='I' & optTypeCd='29', at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optInMail" | "I"   | "29"      | "SetCust - OptInMail verify customer_opt table updated with optCd='I' & optTypeCd='29', at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optInMail" | "I"   | "29"      | "SetCust - OptInMail verify customer_opt table updated with optCd='I' & optTypeCd='29', at Channel = 'R'" |

  @optOutMail @SetCust
  Scenario Outline: setCust - optInMail - Verify 'optCd=O' and 'optTypeCd=29' from customer_opt table
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And The response should have xtraCard details
    And The customerOpt table updated with optCd as <optCd> and optTypeCd as <optTypeCd>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action       | optCd | optTypeCd | scenario                                                                                                   |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutMail" | "O"   | "29"      | "SetCust - optOutMail verify customer_opt table updated with optCd='O' & optTypeCd='29', at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutMail" | "O"   | "29"      | "SetCust - optOutMail verify customer_opt table updated with optCd='O' & optTypeCd='29', at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutMail" | "O"   | "29"      | "SetCust - optOutMail verify customer_opt table updated with optCd='O' & optTypeCd='29', at Channel = 'R'" |
