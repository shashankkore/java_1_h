#Author: Vikas <Vikas.Ramayampet@CVSHealth.com>
@GetCustCpns
Feature: Get Customer - Cpns

  @cpns
  Scenario Outline: GET Customer API for valid xtracare coupons
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "cpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc01_onecpn_expected_output"

    Examples:
      | channel | cardNbr    | cardType |
      | "M"     | "12345607" | "0002"   |
      | "W"     | "12345607" | "0002"   |
      | "R"     | "12345607" | "0002"   |

  @cpns @pass
  Scenario Outline: GET Customer API for No xtracare coupons
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "cpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should be empty

    Examples:
      | channel | cardNbr    | cardType |
      | "M"     | "22211111" | "0002"   |
      | "W"     | "22211111" | "0002"   |
      | "R"     | "22211111" | "0002"   |


  @cpns
  Scenario Outline: GET Customer API for multi xtracare coupons
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "cpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc02_multicpns_expected_output"

    Examples:
      | channel | cardNbr | cardType | exexpirDt    |
      | "M"     | "606"   | "0002"   | "2019-04-08" |
      | "W"     | "606"   | "0002"   | "2018-04-08" |
      | "R"     | "606"   | "0002"   | "2020-04-08" |


  @cpns @negative
  Scenario Outline: GET Customer API for invalid xtracare card types
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "cpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | errorCode | errorMessage      |
      | "M"     | "22211111" | "0003"   | 4         | "Invalid XC Card" |
      | "W"     | "22211111" | "0003"   | 4         | "Invalid XC Card" |
      | "R"     | "22211111" | "0003"   | 4         | "Invalid XC Card" |

  @cpns @negative
  Scenario Outline: GET Customer API for invalid xtracare card types
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "active_coupon" "cpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | active_coupon |errorCode | errorMessage      |
      | "M"     | "22211111" | "0003"   | 105982        |4         | "Invalid XC Card" |
      | "W"     | "22211111" | "0003"   | 105982        |4         | "Invalid XC Card" |
      | "R"     | "22211111" | "0003"   | 105982        |4         | "Invalid XC Card" |


  @cpns @negative
  Scenario Outline: GET Customer API for invalid cpn_nbr  types
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "cpn_nbr" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status 500
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | active_coupon |errorCode | errorMessage      |
      | "M"     | "22211111" | "0002"   | 105982        |1         | "Invalid Search Criteria" |
      | "W"     | "22211111" | "0002"   | 105982        |1         | "Invalid Search Criteria" |
      | "R"     | "22211111" | "0002"   | 105982        |1         | "Invalid Search Criteria" |

  @cpns @negative
  Scenario Outline: GET Customer API for invalid storeNbr types
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "storeNbr" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status 500
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | errorCode | errorMessage      |
      | "M"     | "22211111" | "0002"   | 1         | "Invalid Search Criteria" |
      | "W"     | "22211111" | "0002"   | 1         | "Invalid Search Criteria" |
      | "R"     | "22211111" | "0002"   | 1         | "Invalid Search Criteria" |


  @cpns @negative
  Scenario Outline: GET Customer API for  xtracare card types
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "maxRedeemAmtcpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status 500
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | maxRedeemAmt |errorCode | errorMessage      |
      | "M"     | "22211111" | "0002"   | 101.00       |1         | "Invalid Search Criteria" |
      | "W"     | "22211111" | "0002"   | 102.00       |1         | "Invalid Search Criteria" |
      | "R"     | "22211111" | "0002"   | 105.00       |1         | "Invalid Search Criteria" |

  @cpns
  Scenario Outline: GET Customer API for valid digital only coupons
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "cpns" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc03_digital_expected_output"
#select xcac.XTRA_CARD_NBR, xcac.LOAD_ACTL_REFER_BY_CD, cp.START_DT, cp.END_DT from campaign cp inner join xtra_card_active_coupon xcac ON cp.CMPGN_ID=xcac.CMPGN_ID
#where xcac.LOAD_ACTL_REFER_BY_CD='P';
    Examples:
      | channel | cardNbr    | cardType |
      | "M"     | "15913" | "0002"   |
      | "W"     | "15913" | "0002"   |
      | "R"     | "15913" | "0002"   |






















