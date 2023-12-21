#Author: Vikas <Vikas.Ramayampet@CVSHealth.com>
@GetCustPeb
Feature: Get Customer - PEB Available Pool

  @peb
  Scenario Outline: GET Customer API for valid coupons with PEB available pool with one record
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "pebAvailPool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc01_pebavlbpool_one_record"

    Examples:
      | channel | cardNbr    | cardType |
      | "M"     | "12345607" | "0002"   |
      | "W"     | "12345607" | "0002"   |
      | "R"     | "12345607" | "0002"   |

  @peb
  Scenario Outline: GET Customer API for valid coupons with PEB available pool with no coupon category
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "pebAvailPool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc02_pebavlbpool_no_cpn_category"

    Examples:
      | channel | cardNbr | cardType |
      | "M"     | "7655"  | "0002"   |
      | "W"     | "7655"  | "0002"   |
      | "R"     | "7655"  | "0002"   |

  @peb
  Scenario Outline: GET Customer API for valid coupons with PEB available pool with multi records
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "pebAvailPool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc03_pebavlbpool_multi_records"

    Examples:
      | channel | cardNbr     | cardType |
      | "M"     | "900003995" | "0002"   |
      | "W"     | "900003995" | "0002"   |
      | "R"     | "900003995" | "0002"   |

  @peb @negative
  Scenario Outline: GET Customer API for valid coupons with NO PEB available pool
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtracare" "pebAvailPool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should be empty

    Examples:
      | channel | cardNbr | cardType |
      | "M"     | "1234"  | "0002"   |
      | "W"     | "1234"  | "0002"   |
      | "R"     | "1234"  | "0002"   |

  @peb @negative
  Scenario Outline: GET Customer API for with invalid  PEB available pool
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "pebAvailablePool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status 500
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | errorCode | errorMessage              |
      | "M"     | "22211111" | "0002"   | 1         | "Invalid Search Criteria" |
      | "W"     | "22211111" | "0002"   | 1         | "Invalid Search Criteria" |
      | "R"     | "22211111" | "0002"   | 1         | "Invalid Search Criteria" |
