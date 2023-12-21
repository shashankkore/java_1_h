@GetCust
Feature: Get Customer - XtraCardCipherTxt

  @GetCust @XtraCardCipherTxt
  Scenario Outline: GET Customer API returns XtraCardCipherTxt for valid xtracare card
    Given I use my Extra Card <cardNbr>
    And I filter for  "xtraCardCipherTxt" in the request
    When I use the get customer API for channel <channel>
    Then I receive a response with success status
    And The response should contain "xtraCardCipherTxt" node

    Examples:
      | channel      | cardNbr |
      | "W"     | "13976" |
      | "M"     | "13976" |
#      | "R"     | "13976" |
