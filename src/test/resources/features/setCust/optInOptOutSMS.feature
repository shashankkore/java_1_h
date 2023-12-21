@SetCust_OptIn_OptOut_SMS
Feature: SetCust - OptIn/OptOut SMS

  @optInSMS @SetCust
  Scenario Outline: setCust - Customer optIn SMS
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action     | scenario                               |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optInSMS" | "SetCust - OptInSMS, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optInSMS" | "SetCust - OptInSMS, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optInSMS" | "SetCust - OptInSMS, at Channel = 'R'" |

  @optOutSMS @SetCust
  Scenario Outline: setCust - Customer optOut SMS
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action      | scenario                                |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutSMS" | "SetCust - OptOutSMS, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutSMS" | "SetCust - OptOutSMS, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutSMS" | "SetCust - OptOutSMS, at Channel = 'R'" |

  @optInSMS @optOutSMS @SetCust
  Scenario Outline: setCust - Customer optOut SMS who is already optedIn
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try 'optInSMS' via request
    And I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action      | scenario                                                          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutSMS" | "SetCust - OptOutSMS which is already opted In, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutSMS" | "SetCust - OptOutSMS which is already opted In, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutSMS" | "SetCust - OptOutSMS which is already opted In, at Channel = 'R'" |

  @optInSMS @SetCust
  Scenario Outline: setCust - optInSMS - Verify 'optCd=Y' and 'optTypeCd=37' from customer_opt table
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And The response should have xtraCard details
    And The customerOpt table updated with optCd as <optCd> and optTypeCd as <optTypeCd>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action     | optCd | optTypeCd | scenario                                                                                    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optInSMS" | "Y"   | "37"      | "SetCust - OptInSMS verify customer_email updated with emailStatusCd='A', at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optInSMS" | "Y"   | "37"      | "SetCust - OptInSMS verify customer_email updated with emailStatusCd='A', at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optInSMS" | "Y"   | "37"      | "SetCust - OptInSMS verify customer_email updated with emailStatusCd='A', at Channel = 'R'" |

  @optOutSMS @SetCust
  Scenario Outline: setCust - optInSMS - Verify 'optCd=N' and 'optTypeCd=37' from customer_opt table
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And The response should have xtraCard details
    And The customerOpt table updated with optCd as <optCd> and optTypeCd as <optTypeCd>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action      | optCd | optTypeCd | scenario                                                                                     |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutSMS" | "N"   | "37"      | "SetCust - OptOutSMS verify customer_email updated with emailStatusCd='U', at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutSMS" | "N"   | "37"      | "SetCust - OptOutSMS verify customer_email updated with emailStatusCd='U', at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutSMS" | "N"   | "37"      | "SetCust - OptOutSMS verify customer_email updated with emailStatusCd='U', at Channel = 'R'" |
