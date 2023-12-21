#Author: Vani.Puranam@CVSHealth.com
@RecentlyRedeemTranHistory
Feature: View Customer Recently Redeemed Transaction History Details

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "	I am an Xtra Card member and redeemed $3 coupons yesterday"
    And I use my Extra Card 98158327
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 1           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header | coupon_description |
      | $3 OFF        | $3 off purchase    |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 3.0          |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed two $ 2 and $3 coupons in single transaction two days before"
    And I use my Extra Card 98158328
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 2           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header          | coupon_description         |
      | $2 off LOREAL LIPSTICK | $2 OFF (3) LOREAL LIPSTICK |
      | $3 OFF                 | $3 off purchase            |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 2.0          |
      | 3.0          |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed three $1, $2 coupon and $3EB in three seperate transaction today"
    And I use my Extra Card 98158329
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 0           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header          | coupon_description         |
      | Save $1 on Ice Cubes   | Save $1 on Ice Cubes 3pk   |
      | $2 off LOREAL LIPSTICK | $2 OFF (3) LOREAL LIPSTICK |
      | $3 OFF                 | $3 off purchase            |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 1.0          |
      | 2.0          |
      | 3.0          |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed $2 coupons $10 EB carepass coupon"
    And I use my Extra Card 98158330
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 1           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header          | coupon_description         |
      | $2 off LOREAL LIPSTICK | $2 OFF (3) LOREAL LIPSTICK |
      | CarePass $10 reward    | CarePass $10 Reward        |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 2.0         |
      | 10.0         |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed $10 off on $50 above 3 days before"
    And I use my Extra Card 98158331
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 3           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header           | coupon_description              |
      | $10 off Purchase of $50 | $10 off Purchase of $50 or More |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 10.0         |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given  "I am an Xtra Card member and redeemed 20% carepass coupon within 14 days"
    And I use my Extra Card 98158332
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 14          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header              | coupon_description                                    |
      | CAREPASS 20%OFF CVS HEALTH | 20% off CVS Health brand benefit for CarePass members |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 10.0         |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given  "I am an Xtra Card member and redeemed BOGO offer or %50 on 2nd item within 14 days"
    And I use my Extra Card 98158333
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 13          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header | coupon_description |
      | BOGO CANDY    | BOGO Candy Offer   |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 2.99         |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given  "I am an Xtra Card member and redeemed $1 off MFR offer on Bounty within 14 days"
    And I use my Extra Card 98158334
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 14          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header          | coupon_description                              |
      | $1 off Nature's Bounty | $1 off Nature's Bounty or Radiance Black Cohosh |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 1.0          |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given  "I am an Xtra Card member and redeemed $2 off MFR lysol offer and $2 CVS offer in same transaction within last 14 days"
    And I use my Extra Card 98158335
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 13          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header          | coupon_description                 |
      | $2 off 2 Lysol Wipes   | $2 off 2 Lysol Wipes. One Day Only |
      | $2 off LOREAL LIPSTICK | $2 OFF (3) LOREAL LIPSTICK         |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 2.0          |
      | 2.0          |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed 20% non carepass coupon within 14 days"
    And I use my Extra Card 98158336
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 12          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header         | coupon_description |
      | 20% Off any Vicks_txt | 20% Off any Vicks  |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 20.0         |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed $5 EB 10 days before"
    And I use my Extra Card 98158337
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 10          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header | coupon_description       |
      | PE coupon $5  | $5.00 ExtraBucks Rewards |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 5.0          |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and made one $3 coupon transaction online and $2 coupon in store yesterday"
    And I use my Extra Card 98158338
    And I use "WEB"
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 1           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header              | coupon_description                                              |
      | 2 Dollars off Cosmetics    | You received a 2 dollar off cosmetics coupon!!                  |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 2.0          |
      | 3.0          |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and made one $3 coupon transaction online and $2 coupon in store today"
    And I use my Extra Card 98158339
    And I use "WEB"
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 0           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header              | coupon_description                                              |
      | 2 Dollars off Cosmetics    | You received a 2 dollar off cosmetics coupon!!                  |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 2.0          |
      | 3.0          |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member have request for XX limit  transactions (Check for response is sorted in descending order by date"
    And I use my Extra Card 98158340
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History details as
      | redeem_date | coupon_header              | coupon_description                                              | saved_amount |
      | 1           | 2 Dollars off Cosmetics    | You received a 2 dollar off cosmetics coupon!!                  | 2.0          |
      | 2           | $1 off Nature's Bounty     | $1 off Nature's Bounty or Radiance Black Cohosh                 | 1.0          |
      | 3           | 2 Dollars off Cosmetics    | You received a 2 dollar off cosmetics coupon!!                  | 2.0          |
      | 4           | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! | 3.0          |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "Check for offset & limit for service response for first call vs subsequent calls"
    And I use my Extra Card 98158341
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 0           |
    And I see my Recently Redeemed Transaction History  coupon Info with offset 0 as
      | coupon_header              | coupon_description                                              |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $1 off Nature's Bounty     | $1 off Nature's Bounty or Radiance Black Cohosh                 |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      | $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 3.0         |
      | 3.0         |
      | 3.0         |
      | 3.0         |
      | 3.0         |
      | 1.0         |
      | 3.0         |
      | 3.0         |
      | 3.0         |
      | 3.0         |
    #Given I have offset and limit as
      #| offset | limit |
      #| 5      | 5     |
    #When I view Recently Redeemed Transaction History Details in DashBoard for my card
    #Then I see the Recently Redeemed Transaction History
    #And I see my Recently Redeemed Transaction History redeem Date as
      #| redeem_date |
      #| 0           |
    #And I see my Recently Redeemed Transaction History  coupon Info with offset 5 as
      #| coupon_header              | coupon_description                                              |
      #| $1 off Nature's Bounty     | $1 off Nature's Bounty or Radiance Black Cohosh                 |
      #| $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      #| $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      #| $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
      #| $3 off ThermaCare Heatwrap | $3 off ThermaCare Back Heatwrap (1ct). Only $0.99 after coupon! |
    #And I see my Recently Redeemed Transaction History  saved Amount as
      #| saved_amount |
      #| 1.0         |
      #| 3.0         |
      #| 3.0         |
      #| 3.0         |
      #| 3.0         |


  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "Check if limit is more than number of record during first calls vs next calls"
    And I use my Extra Card 98158342
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 5           |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header          | coupon_description                              |
      | $1 off Nature's Bounty | $1 off Nature's Bounty or Radiance Black Cohosh |
      | $1 off Nature's Bounty | $1 off Nature's Bounty or Radiance Black Cohosh |
      | $1 off Nature's Bounty | $1 off Nature's Bounty or Radiance Black Cohosh |
      | $1 off Nature's Bounty | $1 off Nature's Bounty or Radiance Black Cohosh |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 1.0          |
      | 1.0          |
      | 1.0          |
      | 1.0          |

  @Web @Mobile
  Scenario: View Customer Recently Redeemed Transaction History Details in DashBoard
    Given "I am an Xtra Card member and redeemed three $ 10 coupons in single transaction 14 days before"
    And I use my Extra Card 98158343
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    And I see my Recently Redeemed Transaction History redeem Date as
      | redeem_date |
      | 15          |
    And I see my Recently Redeemed Transaction History  coupon Info as
      | coupon_header           | coupon_description              |
      | $10 off Purchase of $50 | $10 off Purchase of $50 or More |
      | $10 off Purchase of $50 | $10 off Purchase of $50 or More |
      | $10 off Purchase of $50 | $10 off Purchase of $50 or More |
    And I see my Recently Redeemed Transaction History  saved Amount as
      | saved_amount |
      | 10.0         |
      | 10.0         |
      | 10.0         |

  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I see the Recently Redeemed Transaction History
    Examples:
      | scenario                                                               | xtra_card_nbr | error_cd | error_desc         |
      | "I am an Xtra card member and didnt redeem any coupon in last 14 days" | 98158344      | 4        | "Card Not on File" |

  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Recently Redeemed Transaction History Details in DashBoard for my card
    Then I do not see the Recently Redeemed Transaction History
    And I see my Error Code as <error_cd>
    And I see Error Message as <error_desc>
    Examples:
      | scenario                                                                                    | xtra_card_nbr | error_cd | error_desc         |
      | "I am using my walgreens card and want to see my redeem transaction history"                | 98158345      | 4        | "Card Not on File" |
      | "I am an Hot card member and want to see  my redeem transaction history for witnin 14 days" | 98158346      | 5        | "HOT XC Card"      |
