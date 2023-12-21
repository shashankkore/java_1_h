#Author: Vikas <Vikas.Ramayampet@CVSHealth.com>
@GetCustCpns
Feature: Get Customer - MFR Cpns Available Pool

  @mfrcpns
  Scenario Outline: GET Customer API for valid MFR Cupon
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "mfrCpnAvailPool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should match with database tables data "tc01_mfrcpns_allavlcpns"
    Examples:
      | channel | cardNbr    | cardType | MFR_CPNs |
      | "M"     | "22211111" | "0002"   | 43325    |
      | "W"     | "22211111" | "0002"   | 58587    |
      | "R"     | "22211111" | "0002"   | 58580    |

  @mfrcpns
  Scenario Outline: GET Customer API for Invalid Card
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "mfrCpnAvailPool" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status
    And  The response should have status code <errorCode> with message <errorMessage>
    Examples:
      | channel | cardNbr    | cardType | MFR_CPNs |errorCode | errorMessage      |
      | "M"     | "" | "0002"   | 00000    |4         | "Invalid XC Card" |
      | "W"     | "" | "0002"   | 11111    |4         | "Invalid XC Card" |
      | "R"     | "" | "0002"   | 22222    |4         | "Invalid XC Card" |

  @mfrcpns @negative
  Scenario Outline: GET Customer API for  xtracare card types
    Given I use my card number <cardNbr> and card type <cardType>
    And I filter for  "xtracare" "mfrCpnAvailPoolll" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with bad request status 500
    And  The response should have status code <errorCode> with message <errorMessage>

    Examples:
      | channel | cardNbr    | cardType | maxRedeemAmt | errorCode | errorMessage              |
      | "M"     | "22211111" | "0002"   | 101.00       | 1         | "Invalid Search Criteria" |
      | "W"     | "22211111" | "0002"   | 102.00       | 1         | "Invalid Search Criteria" |
      | "R"     | "22211111" | "0002"   | 105.00       | 1         | "Invalid Search Criteria" |





























