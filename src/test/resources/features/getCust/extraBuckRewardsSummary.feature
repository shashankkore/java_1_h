@GetCust_extraBuckRewardsSummary
Feature: Get Customer - extraBuckRewardsSummary

  @Smoke @Regression
  Scenario Outline: Verify extraBuckRewardsSummary node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And ExtraBuckRewardsSummary should be available in the response
    Examples:
      | xtraCard  | cardType   | channel | node      										 |scenario                              							    									 |
      | 900017210 | "xtraCard" | "M"     | "extraBuckRewardsSummary"     |"Verify extraBuckRewardsSummary node in getCust response at Channel = 'M'" |
      
  @Smoke @Regression
  Scenario Outline: Verify extraBuckRewardsSummary node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And ExtraBuckRewardsSummary should be available in the response
    And Validate ExtraBuckRewardsSummary for "noCoupon" in response
    Examples:
      | xtraCard  | cardType   | channel | node      										 |scenario                              							    									 															 |
      | 900017210 | "xtraCard" | "M"     | "extraBuckRewardsSummary"     |"Verify extraBuckRewardsSummary node in getCust response for xtra card with no rewards at Channel = 'M'" |
      
  @Smoke @Regression
  Scenario Outline: Verify extraBuckRewardsSummary node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I hit campaign earn1 for card <xtraCard>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And ExtraBuckRewardsSummary should be available in the response
    And Validate ExtraBuckRewardsSummary for "onecoupon" in response
    Examples:
      | xtraCard  | cardType   | channel | node      										 |scenario                              							    									 															 |
      | 900017211 | "xtraCard" | "M"     | "extraBuckRewardsSummary"     |"Verify extraBuckRewardsSummary node in getCust response for xtra card with one coupon at Channel = 'M'" |  
      
  @Smoke @Regression
  Scenario Outline: Verify extraBuckRewardsSummary node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I hit campaign earn1 for card <xtraCard>
    And I hit campaign earn2 for card <xtraCard>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And ExtraBuckRewardsSummary should be available in the response
    And Validate ExtraBuckRewardsSummary for "twocoupons" in response
    Examples:
      | xtraCard  | cardType   | channel | node      										 |scenario                              							    									 															  |
      | 900017212 | "xtraCard" | "M"     | "extraBuckRewardsSummary"     |"Verify extraBuckRewardsSummary node in getCust response for xtra card with two coupons at Channel = 'M'" |
      
  @Smoke @Regression
  Scenario Outline: Verify extraBuckRewardsSummary node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I hit campaign earn1 for card <xtraCard>
    And I hit campaign earn2 for card <xtraCard>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And ExtraBuckRewardsSummary should be available in the response
    And Load a coupon
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate ExtraBuckRewardsSummary for "loadedcoupon" in response
    Examples:
      | xtraCard  | cardType   | channel | node      										 |scenario                              							    									 													 		        |
      | 900017213 | "xtraCard" | "M"     | "extraBuckRewardsSummary"     |"Verify extraBuckRewardsSummary node in getCust response for xtra card with one coupon loaded at Channel = 'M'" |
      
   @Smoke @Regression
  Scenario Outline: Verify extraBuckRewardsSummary node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I hit campaign earn1 for card <xtraCard>
    And I hit campaign earn2 for card <xtraCard>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And ExtraBuckRewardsSummary should be available in the response
    And Redeem a coupon
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate ExtraBuckRewardsSummary for "redeemablecoupon" in response
    Examples:
      | xtraCard  | cardType   | channel | node      										 |scenario                              							    									 													 		        		|
      | 900017220 | "xtraCard" | "M"     | "extraBuckRewardsSummary"     |"Verify extraBuckRewardsSummary node in getCust response for xtra card with one coupon redeemable at Channel = 'M'" |
      
   @Smoke @Regression
  Scenario Outline: Verify mfrCpnAvailPool node in GetCust response - error scenarios
    Given <scenario>
    When I use xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | xtraCard   | cardType   | channel | node         				       |errorCode | errorMessage        |scenario                                                  							|
      | "22222222" | "xtraCard" | "W"     | "extraBuckRewardsSummary"  |4         | "Card Not on File"  |"Verify extraBuckRewardsSummary node when invalid card number is sent" |
      | "900058650"| "xtraCard" | "W"     | "extraBuckRewardsSummary"  |5         | "HOT XC Card"       |"Verify extraBuckRewardsSummary node when hot XC card number is sent"  |
      