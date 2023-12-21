@GetCust_EverDigitizedCpnInd
Feature: Get Customer - EverDigitizeCpnInd and DigitizedCpnInd

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card has everDigitizedCpnInd = 'N' when no Cpns digitized yet.
  Also Xtra_Card shouldn't exist in 'Xtra_Card_Summary' Table.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And The response for xtraCard should have <node> with value <resp>
    And None of the coupons should be digitized
    And My xtraCard <exist> in "XTRA_CARD_SUMMARY" table
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  | resp | exist | scenario                                                                             |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "N"  | "N"   | "Verify everDigitizedCpnInd = 'N' when none of the Cpns digitized, at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "N"  | "N"   | "Verify everDigitizedCpnInd = 'N' when none of the Cpns digitized, at Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card has everDigitizedCpnInd = 'Y' when atleast one of the Cpns digitized.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And I have a coupon with digitizedCpnInd as "N"
    And I 'print' a coupon for my xtraCard
    And I 'digitize' a coupon for my xtraCard
    And I get a response from getCust API
    Then The response for xtraCard should have <node> with value <resp>
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  | resp | scenario                                                                            |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Y"  | "Verify everDigitizedCpnInd = 'Y' when one of the Cpns digitized, at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Y"  | "Verify everDigitizedCpnInd = 'Y' when one of the Cpns digitized, at Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card has everDigitizedCpnInd = 'Y' when atleast one of the Cpns digitized.
  Also Xtra_Card shouldn exist in 'XTRA_CARD_SUMMARY', 'XTRA_CARD_COUPON_AUDIT', 'DIGITIZE_COUPON_AUDIT' Tables.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    And The response for Cpns should have <node2> with value "N"
    And I 'print' a coupon for my xtraCard
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    Then I get a response from getCust API
    And Atleast one coupon should be digitized
    And The response for xtraCard should have <node1> with value <resp>
    And The response for Cpns should have <node2> with value <resp>
    And My xtraCard <exist> in "XTRA_CARD_SUMMARY" table
    And My xtraCard <exist> in "XTRA_CARD_COUPON_AUDIT" table
    And My xtraCard <exist> in "DIGITIZE_COUPON_AUDIT" table
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | node2             | resp | exist | scenario                                                                |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "digitizedCpnInd" | "Y"  | "Y"   | "Verify digitizedCpnInd = 'Y' when I digitized a Cpn, at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "digitizedCpnInd" | "Y"  | "Y"   | "Verify digitizedCpnInd = 'Y' when I digitized a Cpn, at Channel = 'H'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Digitize a Cpn which is digitized already and everDigitizedCpnInd = "Y"
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node> in the request
    Then I get a response from getCust API
    Then API returns a response with status code as 200
    And I have a coupon with digitizedCpnInd as "N"
    And I 'print' a coupon for my xtraCard
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    When I get a response from getCust API
    And Atleast one coupon should be digitized
    And The response for xtraCard should have <node> with value "Y"
    And I 'print' a coupon for my xtraCard
    Then I get a response with statusCd 14 from Coupons API
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 11 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  | scenario                                                                                            |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Verify already digitized Cpn cannot be digitized again and throws statusCd = 11, at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Verify already digitized Cpn cannot be digitized again and throws statusCd = 11, at Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Digitize a Cpn which is not yet digitized when everDigitizedCpnInd = "Y"
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node> in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And I have a coupon with digitizedCpnInd as "N"
    And I 'print' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    When I get a response from getCust API
    And Atleast one coupon should be digitized
    And The response for xtraCard should have <node> with value "Y"
    And I have a coupon with digitizedCpnInd as "N"
    And I 'print' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node                  | scenario                                                                           |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Verify new cpn can be digitized when everDigitizedCpnInd = 'Y', at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Verify new cpn can be digitized when everDigitizedCpnInd = 'Y', at Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be digitized before 'print' and throws statusCd '11'.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'view' a coupon for my xtraCard
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 11 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                           |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Digitize XtraCard before 'Print' => statusCd = 11, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Digitize XtraCard before 'Print' => statusCd = 11, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be digitized after 'Print' & 'Redeem' and throws statusCd = 14
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    And I 'print' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'redeem' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 14 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                     |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Digitize XtraCard after 'Print' & 'Redeem' => statusCd = 14, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Digitize XtraCard after 'Print' & 'Redeem' => statusCd = 14, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn can be redeemed after 'Print' & before 'Digitize'
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    And I 'print' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'redeem' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                             |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Redeem XtraCard after 'Print' and before 'Digitize' => statusCd = 0, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Redeem XtraCard after 'Print' and before 'Digitize' => statusCd = 0, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn can be redeemed after 'Print' & 'Digitize'
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'print' a coupon for my xtraCard
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'redeem' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                      |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Redeem XtraCard after 'Print' and 'Digitize' => statusCd = 0, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Redeem XtraCard after 'Print' and 'Digitize' => statusCd = 0, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be redeemed before 'Print' and 'digitize' and throws statusCd = 11
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'view' a coupon for my xtraCard
    And I 'digitize' a coupon for my xtraCard
    Then I get a response with statusCd 11 from Coupons API
    And I 'redeem' a coupon for my xtraCard
    Then I get a response with statusCd 11 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                        |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Redeem XtraCard before 'Print' and 'Digitize' => statusCd = 11, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Redeem XtraCard before 'Print' and 'Digitize' => statusCd = 11, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn can be 'Loaded' before 'Print', 'Redeem' or 'Digitize'.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'View' a coupon for my xtraCard
    And I 'Load' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                            |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Load XtraCard before 'Print', Redeem or 'Digitize' => statusCd = 0, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Load XtraCard before 'Print', Redeem or 'Digitize' => statusCd = 0, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be Loaded after 'Print' and throws statusCd = 12.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'View' a coupon for my xtraCard
    And I 'Print' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'Load' a coupon for my xtraCard
    Then I get a response with statusCd 12 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                      |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Load XtraCard after 'Print' => statusCd = 12, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Load XtraCard after 'Print' => statusCd = 12, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be Loaded after 'Print & 'Redeem' and throws statusCd = 12.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    And I 'Print' a coupon for my xtraCard
    And I 'Redeem' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'Load' a coupon for my xtraCard
    Then I get a response with statusCd 12 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                 |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Load XtraCard after 'Print' & 'Redeem' => statusCd = 12, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Load XtraCard after 'Print' & 'Redeem' => statusCd = 12, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be loaded after 'Print' & 'Digitize' and throws statusCd = 13.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'View' a coupon for my xtraCard
    And I 'Print' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'Digitize' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'Load' a coupon for my xtraCard
    Then I get a response with statusCd 13 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                                                   |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Load XtraCard after 'Print' & 'Digitize' => statusCd = 13, Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Load XtraCard after 'Print' & 'Digitize' => statusCd = 13, Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn cannot be digitized after 'Loaded' and throws statusCd = 11.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'View' a coupon for my xtraCard
    And I 'Load' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'Digitize' a coupon for my xtraCard
    Then I get a response with statusCd 11 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                        |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Digitize XtraCard after 'Load', Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Digitize XtraCard after 'Load', Channel = 'M'" |

  @everDigitizedCpnInd @digitizedCpnInd
  Scenario Outline: Verify Xtra_Card Cpn can be Redeemed after 'Load'
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I 'carePass_Enroll_Monthly' via setCust
    And I filter with <node1> in the request
    When I get a response from getCust API
    Then The response for xtraCard should have <node1> with value "N"
    And I have a coupon with digitizedCpnInd as "N"
    When I 'View' a coupon for my xtraCard
    And I 'Load' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    And I 'Redeem' a coupon for my xtraCard
    Then I get a response with statusCd 0 from Coupons API
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | node1                 | scenario                                      |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "everDigitizedCpnInd" | "Redeem XtraCard after 'Load', Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "everDigitizedCpnInd" | "Redeem XtraCard after 'Load', Channel = 'M'" |
