@GetCust_MfrCpnAvailPool
Feature: Get Customer - MFRCpnAvailPool

  @mfrCpnAvailPool
  Scenario Outline: Verify mfrCpnAvailPool node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And All active MFR coupons should be available in response
    And Check the data in the response
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  |scenario                                 										 |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "mfrCpnAvailPool"     |"Verify mfrCpnAvailPool in getCust response at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "mfrCpnAvailPool"     |"Verify mfrCpnAvailPool in getCust response at Channel = 'M'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "R"     | "mfrCpnAvailPool"     |"Verify mfrCpnAvailPool in getCust response at Channel = 'R'" |

  @mfrCpnAvailPool
  Scenario Outline: Verify mfrCpnAvailPool node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    And I choose a coupon for "everWebRedeemableInd"
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And All active MFR coupons should be available in response
    And Validate the flag of "everWebRedeemableInd" is "N"
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  |scenario                                                           |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "mfrCpnAvailPool"     |"Verify mfrCpnAvailPool for coupon with everWebRedeemableInd = 'N'"|

@mfrCpnAvailPool
  Scenario Outline: Verify mfrCpnAvailPool node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    And I choose a coupon for "expSoonInd"
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And All active MFR coupons should be available in response
    And Validate the flag of "expSoonInd" is "Y"
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  |scenario                                                  |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "mfrCpnAvailPool"     |"Verify mfrCpnAvailPool for coupon with expSoonInd = 'Y'" |
  
  @mfrCpnAvailPool
  Scenario Outline: Verify mfrCpnAvailPool node in GetCust response - error scenarios
    Given <scenario>
    When I use xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | xtraCard   | cardType   | channel | node               |errorCode | errorMessage        |scenario                                                  |
      | "22222222" | "xtraCard" | "W"     | "mfrCpnAvailPool"  |4         | "Card Not on File"  |"Verify mfrCpnAvailPool when invalid card number is sent" |
      | "900058650"| "xtraCard" | "W"     | "mfrCpnAvailPool"  |5         | "HOT XC Card"       |"Verify mfrCpnAvailPool when hot XC card number is sent"  |

      