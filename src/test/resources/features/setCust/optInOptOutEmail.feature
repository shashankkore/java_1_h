@SetCust_OptIn_OptOut_Email
Feature: SetCust - OptIn/OptOut Email

  @optInEmail @SetCust
  Scenario Outline: setCust - Customer optIn Email
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action       | scenario                                 |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optInEmail" | "SetCust - OptInEmail, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optInEmail" | "SetCust - OptInEmail, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optInEmail" | "SetCust - OptInEmail, at Channel = 'R'" |

  @optOutEmail @SetCust
  Scenario Outline: setCust - Customer optOut Email
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action        | scenario                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutEmail" | "SetCust - OptOutEmail, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutEmail" | "SetCust - OptOutEmail, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutEmail" | "SetCust - OptOutEmail, at Channel = 'R'" |

  @optInEmail @optOutEmail @SetCust
  Scenario Outline: setCust - Customer optOut Email who is already optedIn
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try 'optInEmail' via request
    And I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action        | scenario                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutEmail" | "SetCust - OptOutEmail which is already opted In, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutEmail" | "SetCust - OptOutEmail which is already opted In, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutEmail" | "SetCust - OptOutEmail which is already opted In, at Channel = 'R" |

  @optInEmail @SetCust
  Scenario Outline: setCust - optInEmail - Verify 'emailStatusCd=A' from customer_email table
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And The response should have xtraCard details
    And The customerEmail table updated with emailStatusCd as <emailStatusCd>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action       | emailStatusCd | scenario                                                                                      |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optInEmail" | "A"           | "SetCust - OptInEmail verify customer_email updated with emailStatusCd='A', at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optInEmail" | "A"           | "SetCust - OptInEmail verify customer_email updated with emailStatusCd='A', at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optInEmail" | "A"           | "SetCust - OptInEmail verify customer_email updated with emailStatusCd='A', at Channel = 'R'" |

  @optOutEmail @SetCust
  Scenario Outline: setCust - optOutEmail - Verify 'emailStatusCd=U' from customer_email table
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    When I try <action> via request
    Then I get a response from setCust API
    And The response should have xtraCard details
    And The customerEmail table updated with emailStatusCd as <emailStatusCd>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action        | emailStatusCd | scenario                                                                                       |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "optOutEmail" | "U"           | "SetCust - OptOutEmail verify customer_email updated with emailStatusCd='U', at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "optOutEmail" | "U"           | "SetCust - OptOutEmail verify customer_email updated with emailStatusCd='U', at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "R"     | "optOutEmail" | "U"           | "SetCust - OptOutEmail verify customer_email updated with emailStatusCd='U', at Channel = 'R'" |
