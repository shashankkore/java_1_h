@GetCust_mktgTypeBenefits
Feature: Get Customer - mktgTypeBenefits

  @Smoke @Regression
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And MktgTypeBenefits should be available in the response
    Examples:
      | xtraCard  | cardType   | channel | node      						  |scenario                      							    									   |
      | 44380400  | "xtraCard" | "M"     | "mktgTypeBenefits"     |"Verify mktgTypeBenefits node in getCust response at Channel = 'M'" |

	@Smoke @Regression
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate MktgTypeBenefits in the response for type code "E"
    Examples:
      | xtraCard  | cardType   | channel | node      						  |scenario                      							    									       |
      | 44380400  | "xtraCard" | "M"     | "mktgTypeBenefits"     |"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'E'" |
         
 	@Smoke @Regression
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate MktgTypeBenefits in the response for type code "A"
    Examples:
      | xtraCard  | cardType   | channel | node      						  |scenario                      							    									       |
      | 44380401  | "xtraCard" | "M"     | "mktgTypeBenefits"     |"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'A'" |
     
  @Smoke @Regression
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate MktgTypeBenefits in the response for type code "D"
    Examples:
      | xtraCard  | cardType   | channel | node      						  |scenario                      							    									       |
      | 44380402  | "xtraCard" | "M"     | "mktgTypeBenefits"     |"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'D'" |

  @Smoke @Regression 
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate MktgTypeBenefits in the response for type code <typeCode>
    Examples:
      | xtraCard  | cardType   | channel | node      						  | typeCode  	|scenario                      							    									        |
      | 44380403  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "G1"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'G1'" |
      | 44380404  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "G2"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'G2'" |
      | 44380405  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "H"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'H'"  |
      | 44380406  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "M"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'M'"  |
      | 44380407  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "O"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'O'"  |
      | 44380408  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "P"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'P'"  |
      | 44380409  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "S"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'S'"  |
      | 44380410  | "xtraCard" | "M"     | "mktgTypeBenefits"     | "W"     		|"Verify mktgTypeBenefits node in getCust response for MKTG TYPE CD 'W'"  |
      
  @Smoke @Regression 
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response
    Given <scenario>
    When I have my user with xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And Validate MktgTypeBenefits in the response for employee card
    Examples:
      | xtraCard   | cardType   | channel | node      						  |scenario                      							    									       									 |
      | 900017221  | "xtraCard" | "M"     | "mktgTypeBenefits"      |"Verify mktgTypeBenefits node in getCust response for employee card for MKTG TYPE CD 'E'" |
      
  @Smoke @Regression
  Scenario Outline: Verify mktgTypeBenefits node in GetCust response - error scenarios
    Given <scenario>
    When I use xtraCard <xtraCard>
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | xtraCard   | cardType   | channel | node         	  	  |errorCode | errorMessage        |scenario                                                  		  |
      | "22222222" | "xtraCard" | "W"     | "mktgTypeBenefits"  |4         | "Card Not on File"  |"Verify mktgTypeBenefits node when invalid card number is sent" |
      | "900058650"| "xtraCard" | "W"     | "mktgTypeBenefits"  |5         | "HOT XC Card"       |"Verify mktgTypeBenefits node when hot XC card number is sent"  |
      
      
             