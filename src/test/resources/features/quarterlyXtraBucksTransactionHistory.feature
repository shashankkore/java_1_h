#Author: Vani.Puranam@CVSHealth.com
@QEBTranHistory
Feature: View Customer Quarterly Extra Bucks Transaction History Details

  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made single transaction in a day of total $20 purchase of qualified QEB items in previous quarter"
    And I use my Extra Card 98158378
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History transaction Date as
      | transaction_Date |
      | currentdate-60   |
    And I see my Quarterly Extra Bucks Transaction History transaction Amount as
      | 20 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions on single day of total $40 purchase  of qualified QEB items in previous quarter"
    And I use my Extra Card 98158379
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History transaction Date as
      | transaction_Date |
      | currentdate-65   |
    And I see my Quarterly Extra Bucks Transaction History transaction Amount as
      | 1  |
      | 10 |
      | 29 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions on multiple days of total $15 purchase of qualified QEB items in previous quarter"
    And I use my Extra Card 98158380
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-50   | 3                  | 0                 |
      | currentdate-60   | 1                  | 0                 |
      | currentdate-70   | 5                  | 0                 |
      | currentdate-80   | 2                  | 0                 |
      | currentdate-90   | 4                  | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and earned quarterly Extra bucks for previous quarter"
    And I use my Extra Card 98158381
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-30   | 1                  | 1                 |
      | currentdate-50   | 27                 | 0                 |
      | currentdate-80   | 25                 | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made single transaction in a day of total $20 purchase of qualified QEB items in current quarter"
    And I use my Extra Card 98158382
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History transaction Date as
      | transaction_Date |
      | currentdate-10   |
    And I see my Quarterly Extra Bucks Transaction History transaction Amount as
      | 20 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions on single day of total $40 purchase  of qualified QEB items in current quarter"
    And I use my Extra Card 98158383
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History transaction Date as
      | transaction_Date |
      | currentdate-5    |
    And I see my Quarterly Extra Bucks Transaction History transaction Amount as
      | 1  |
      | 10 |
      | 29 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions on multiple days of total $35 purchase of qualified QEB items in current quarter"
    And I use my Extra Card 98158384
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-10   | 7,13               | 0                 |
      | currentdate-20   | 5                  | 0                 |
      | currentdate-30   | 4,6                | 0                 |

  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and eligible for quarterly Extra bucks for current quarter"
    And I use my Extra Card 98158385
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History transaction Date as
      | transaction_Date |
      | currentdate-15   |
    And I see my Quarterly Extra Bucks Transaction History transaction Amount as
      | 15 |
      | 45 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made single transaction in a day of total $15 purchase of qualified QEB items in current and $6 purchase of qualified QEB item in previous quarter"
    And I use my Extra Card 98158386
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-10   | 15                 | 0                 |
      | currentdate-90   | 6                  | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions in a day of total $25 purchase of qualified QEB items in current and $36 purchase  of qualified QEB item in previous quarter"
    And I use my Extra Card 98158387
    And I have offset and limit as
      | offset | limit |
      | 0      | 10    |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-20   | 3,10,12            | 0                 |
      | currentdate-50   | 5,6,25             | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions on multiple days of total $40 purchase of qualified QEB items in current and $45 purchase of qualified QEB item in previous quarter"
    And I use my Extra Card 98158388
    And I have offset and limit as
      | offset | limit |
      | 0      | 10    |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-10   | 8                  | 0                 |
      | currentdate-20   | 12                 | 0                 |
      | currentdate-25   | 10                 | 0                 |
      | currentdate-30   | 10                 | 0                 |
      | currentdate-48   | 5                  | 0                 |
      | currentdate-50   | 6                  | 0                 |
      | currentdate-55   | 9                  | 0                 |
      | currentdate-60   | 25                 | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and eligible for quarterly Extra bucks for current quarter and earned quarterly Extra bucks for previous quarter"
    And I use my Extra Card 98158389
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-30   | 50                 | 3                 |
      | currentdate-60   | 150                | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made multiple transactions on multiple days of total $25 purchase of qualified QEB items after the quarter ends during 15 days rolling period"
    And I use my Extra Card 98158390
    And I have offset and limit as
      | offset | limit |
      | 0      | 10    |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-31   | 1,2                | 0                 |
      | currentdate-32   | 1,2                | 0                 |
      | currentdate-33   | 2,2                | 0                 |
      | currentdate-34   | 3                  | 0                 |
      | currentdate-35   | 1,2                | 0                 |
      | currentdate-36   | 2                  | 0                 |
      | currentdate-37   | 3                  | 0                 |
      | currentdate-38   | 1,3                | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made 20 different transactions across 20 days of qualified QEB items and want to see with offset 0 and limit 10"
    And I use my Extra Card 98158391
    And I have offset and limit as
      | offset | limit |
      | 0      | 10    |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-13   | 2                  | 0                 |
      | currentdate-14   | 1                  | 0                 |
      | currentdate-15   | 2                  | 0                 |
      | currentdate-16   | 2                  | 0                 |
      | currentdate-17   | 3                  | 0                 |
      | currentdate-18   | 1                  | 0                 |
      | currentdate-19   | 2                  | 0                 |
      | currentdate-20   | 2                  | 0                 |
      | currentdate-21   | 3                  | 0                 |
      | currentdate-22   | 1                  | 0                 |
    And I have offset and limit as
      | offset | limit |
      | 10     | 10    |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-23   | 3                  | 0                 |
      | currentdate-24   | 3                  | 0                 |
      | currentdate-25   | 1,3                | 0                 |
      | currentdate-26   | 1,3                | 0                 |
      | currentdate-27   | 3                  | 0                 |
      | currentdate-28   | 3                  | 0                 |
      | currentdate-29   | 1,3                | 0                 |


  @Web @Mobile
  Scenario: View Customer Quarterly Extra Bucks Transaction History Details in DashBoard
    Given "Iam an xtracard member and made 20 different transactions across 10 days of qualified QEB items and want to see with offset 5 and limit 5"
    And I use my Extra Card 98158392
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-11   | 1,2                | 0                 |
      | currentdate-22   | 1,2                | 0                 |
      | currentdate-23   | 2,2                | 0                 |
      | currentdate-24   | 3                  | 0                 |
      | currentdate-25   | 1,2,2              | 0                 |
    And I have offset and limit as
      | offset | limit |
      | 5      | 5     |
    When I view Quarterly Extra Bucks Transaction History Details in DashBoard for my card
    Then I see the Quarterly Extra Bucks Transaction History
    And I see my Quarterly Extra Bucks Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-26   | 1,2                | 0                 |
      | currentdate-27   | 1,2                | 0                 |
      | currentdate-28   | 2,2,3              | 0                 |
      | currentdate-29   | 1,2,2              | 0                 |
