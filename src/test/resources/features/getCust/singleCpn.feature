@GetCustSingleCpn
Feature: Get Customer - Single Cpn

  @GetCust_SingleCpn
  Scenario Outline: Verify single cpn node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I hit campaign earn1 for card <xtraCard>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Single coupon details should be available in the response
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node      |scenario                              							    |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "cpn"     |"Verify cpn node in getCust response at Channel = 'M'" |
      
  @GetCust_SingleCpn
  Scenario Outline: Verify single cpn node in GetCust response when multiple coupons are available
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I hit campaign earn1 for card <xtraCard>
    And I hit campaign earn2 for card <xtraCard>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And One coupon should be available in the response
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node      |scenario                              							   																	  |
      | "XC_99900" | "createExtraCard" | "xtraCard" | "M"     | "cpn"     |"Verify cpn node in getCust response at Channel = 'M' when multiple coupons are present" |
  
  @GetCust_SingleCpn
  Scenario Outline: Verify single cpn node in GetCust response when no coupons are available
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And No coupon should be available in the response
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node      |scenario                              							   														  |
      | "XC_99902" | "createExtraCard" | "xtraCard" | "M"     | "cpn"     |"Verify cpn node in getCust response at Channel = 'M' when no coupons are present" |
                  
      
  @GetCust_SingleCpn
  Scenario Outline: Verify single cpn node in GetCust response - error scenarios
    Given <scenario>
    When I use xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | xtraCard   | cardType   | channel | node   |errorCode | errorMessage        |scenario                                           |
      | "22222222" | "xtraCard" | "M"     | "cpn"  |4         | "Card Not on File"  |"Verify cpn node when invalid card number is sent" |
      | "900058650"| "xtraCard" | "M"     | "cpn"  |5         | "HOT XC Card"       |"Verify cpn node when hot XC card number is sent"  |

  @GetCust_SingleCpn
  Scenario Outline: Verify single cpn node in GetCust response - error scenarios
    Given <scenario>
    When I use xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | xtraCard   | cardType   | channel | node   |errorCode | errorMessage        |scenario                                           |
      | "22222222" | "xtraCard" | "M"     | "cpn"  |4         | "Card Not on File"  |"Verify cpn node when invalid card number is sent" |
      | "900058650"| "xtraCard" | "M"     | "cpn"  |5         | "HOT XC Card"       |"Verify cpn node when hot XC card number is sent"  |



