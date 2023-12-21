@GetCust_qebEarningType
Feature: Get Customer - qebEarningType

  @Smoke @Regression
  Scenario Outline: Verify qebEarningType node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And QEBEarningType should be available in the response
    Examples:
      | xtraCard  | cardType   | channel | node      						|scenario                      							    									 |
      | 900017210 | "xtraCard" | "M"     | "qebEarningType"     |"Verify qebEarningType node in getCust response at Channel = 'M'" |
      
      
  @Smoke @Regression
  Scenario Outline: Verify qebEarningType node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And QEBEarningType should be available in the response
    And EarningType should be <earningType>
    Examples:
      | xtraCard  | cardType   | channel | node 						| earningType	|scenario                      							    									 							  |
      | 900017210 | "xtraCard" | "M"     | "qebEarningType" | "I"         |"Verify earningType in qebEarningType node in getCust response at Channel = 'M'" |
      
      
  @Smoke @Regression
  Scenario Outline: Verify qebEarningType node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And QEBEarningType should be available in the response
    And Campaign IDs should be <cmpgn1> and <cmpgn2>
    Examples:
      | xtraCard  | cardType   | channel | node 						| cmpgn1	|cmpgn2 	|scenario                      							    									 							  |
      | 900017210 | "xtraCard" | "M"     | "qebEarningType" | 43916   | 48087   |"Verify earningType in qebEarningType node in getCust response at Channel = 'M'" |
      
           
  @Smoke @Regression
  Scenario Outline: Verify qebEarningType node in GetCust response - error scenarios
    Given <scenario>
    When I use xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | xtraCard   | cardType   | channel | node         		  |errorCode | errorMessage        |scenario                                                  		|
      | "22222222" | "xtraCard" | "W"     | "qebEarningType"  |4         | "Card Not on File"  |"Verify qebEarningType node when invalid card number is sent" |
      | "900058650"| "xtraCard" | "W"     | "qebEarningType"  |5         | "HOT XC Card"       |"Verify qebEarningType node when hot XC card number is sent"  |
      
      
            