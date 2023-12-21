#Author: Vani.Puranam@CVSHealth.com
@BeautyClubTranHistory
Feature: View Customer Beauty Club Transaction History Details

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made single transaction of $10 purchase of qualified Beauty Club items dated today-1 day"
    And I use my Extra Card 98158347
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History transaction Date as
      | transaction_Date |
      | currentdate-1    |
    And I see my Beauty Club Transaction History transaction Amount as
      | 10 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made two transaction of $10 purchase each for qualified Beauty Club items dated today-1 day"
    And I use my Extra Card 98158348
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History transaction Date as
      | transaction_Date |
      | currentdate-1    |
    And I see my Beauty Club Transaction History transaction Amount as
      | 10 |
      | 10 |


  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and have made $5 progress is already available, made single $35 purchase of qualified Beauty Club items dated today–1 day"
    And I use my Extra Card 98158349
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-1    | 35                 | 3                 |
      | currentdate-30   | 5                  | 0                 |


  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made two separate transactions each $15 and $25 of qualified Beauty Club items dated today-1 day"
    And I use my Extra Card 98158350
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History transaction Date as
      | transaction_Date |
      | currentdate-1    |
    And I see my Beauty Club Transaction History extraBucks Earned as
      | extraBucks_Earned |
      | 3                 |
    And I see my Beauty Club Transaction History transaction Amount as
      | 15 |
      | 25 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program have $25 progress is already available and made 4 separate transactions each $5, $15, $25 & $15 of qualified Beauty Club items dated today-1 day"
    And I use my Extra Card 98158351
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History Info as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-1    | 5,15,25,15         | 6                 |
      | currentdate-100  | 25                 | 0                 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made 1 single transaction of $100 of qualified Beauty Club items dated today-5 days"
    And I use my Extra Card 98158352
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History transaction Date as
      | transaction_Date |
      | currentdate-5    |
    And I see my Beauty Club Transaction History extraBucks Earned as
      | extraBucks_Earned |
      | 9                 |
    And I see my Beauty Club Transaction History transaction Amount as
      | 100 |


  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made single $10 purchase of qualified Beauty Club items dated today-2 days and $40 purchase of qualified Beauty Club items dated today-30 days"
    And I use my Extra Card 98158353
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-2    | 10                 | 0                 |
      | currentdate-30   | 40                 | 3                 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made two $10 purchases of qualified Beauty Club items dated today-3 days and today-200 days"
    And I use my Extra Card 98158354
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-3    | 10                 | 0                 |
      | currentdate-200  | 10                 | 0                 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made single $35 purchase of qualified Beauty Club items dated current date–367 days"
    And I use my Extra Card 98158355
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History


  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made two separate transactions each $1 and $2 of qualified Beauty Club items dated today-5 days"
    And I use my Extra Card 98158356
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History transaction Date as
      | transaction_Date |
      | currentdate-5    |
    And I see my Beauty Club Transaction History transaction Amount as
      | 2 |
      | 1 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made 4 separate transactions $5, $15, $25 & $15 of qualified Beauty Club items dated today-5 days, today-10 days, today-15 days and today-20 days"
    And I use my Extra Card 98158357
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-5    | 5                  | 0                 |
      | currentdate-10   | 15                 | 3                 |
      | currentdate-15   | 25                 | 3                 |
      | currentdate-20   | 15                 | 0                 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program and made 1 single transaction of $1 of qualified Beauty Club items dated today-90 days"
    And I use my Extra Card 98158358
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History transaction Date as
      | transaction_Date |
      | currentdate-90   |
    And I see my Beauty Club Transaction History transaction Amount as
      | 1 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program had current progress $25, make purchase of $6 for qualified Beauty Club today–20 days"
    And I use my Extra Card 98158359
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History
    And I see my Beauty Club Transaction History Details as
      | transaction_Date | transaction_Amount | extraBucks_Earned |
      | currentdate-20   | 6                  | 3                 |
      | currentdate-90   | 25                 | 0                 |

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I enrolled in Beauty Club Program but has NOT made BC purchase this year"
    And I use my Extra Card 98158360
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I see the Beauty Club Transaction History

  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I am using my walgreens card and want to see my Beauty Club Transaction history"
    And I use my Extra Card 98158361
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I do not see the Beauty Club Transaction History
    And I see my Error Code as 4
    And I see Error Message as "Card Not on File"


  @Web @Mobile
  Scenario: View Customer Beauty Club Transaction History Details in DashBoard
    Given "I am an Hot card member and want to see my Beauty Club transaction history"
    And I use my Extra Card 98158362
    And I have offset and limit as
      | offset | limit |
      | 0      | 5     |
    When I view Beauty Club Transaction History Details in DashBoard for my card
    Then I do not see the Beauty Club Transaction History
    And I see my Error Code as 5
    And I see Error Message as "HOT XC Card"
