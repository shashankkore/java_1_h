@CarePassOld @ignore
Feature: View Customer CarePass Details

  @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view CarePass Details in DashBoard for my card
    Then I see the Carepass Rewards
    And I see my Carepass reward Next Issue Days Count as <reward_next_issue_days_count>
    And I see my Carepass status Code as <status_code>
    And I see my Carepass coupon expiry Date as <expiry_date>
    And I see my Carepass enrollment expiry Date as <enroll_expiry_date>
    And I see my Carepass coupon Sequence Number as <coupon_sequence_number>
    And I see my Carepass campaign Id as <campaign_id>
    And I see my Carepass coupon Number as <coupon_number>
    Examples:
      | scenario                                                                                                                              | xtra_card_nbr | reward_next_issue_days_count | status_code | expiry_date | enroll_expiry_date | coupon_sequence_number | campaign_id | coupon_number | channel |
      | "I recently enrolled in CVS carepass program under monthly renewal membership and have $10 reward available to redeem in next 5 days" | 98158307      | 5                           | "E"         | "In 5 days" | "In 5 days"        | "98158307_40666"       | 40666       | 59121         | "WEB"   |
      | "I recently enrolled in CVS carepass under yearly renewal membership  and have $10 reward available for redeem in next 6 days"        | 98158308      | 6                            | "E"         | "In 6 days" | "In 341 days"      | "98158308_40666"       | 40666       | 59121         | "WEB"   |

  @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view CarePass Details in DashBoard for my card
    Then I see the Carepass Rewards
    And I see my Carepass reward Next Issue Days Count as <reward_next_issue_days_count>
    And I see my Carepass status Code as <status_code>
    Examples:
      | scenario                                                                                                                                                            | xtra_card_nbr | reward_next_issue_days_count | status_code | channel |
      | "I am recently enrolled in CVS carepass program under monthly renewal membership and I have redeemed $10 reward today , next monthly renewable due in next 16 days" | 98158309      | 16                           | "E"         | "WEB"   |
      | "I am recently enrolled in CVS carepass program under yearly renewal membership and I have redeemed $10 reward today, next yearly renewable due in next 8 days"     | 98158310      | 8                            | "E"         | "WEB"   |


  @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view CarePass Details in DashBoard for my card
    Then I see the Carepass Rewards
    And I see my Carepass status Code as <status_code>
    And I see my Carepass coupon expiry Date as <expiry_date>
    And I see my Carepass enrollment expiry Date as <enroll_expiry_date>
    And I see my Carepass coupon Sequence Number as <coupon_sequence_number>
    And I see my Carepass campaign Id as <campaign_id>
    And I see my Carepass coupon Number as <coupon_number>
    Examples:
      | scenario                                                                                                                              | xtra_card_nbr | status_code | expiry_date  | enroll_expiry_date | coupon_sequence_number | campaign_id | coupon_number | channel |
      | "I cancelled my CVS carepass membership today and I have $10 reward available for redeem in next 30 days"                             | 98158311      | "U"        | "In 30 days" | "In 30 days"       | "98158311_40666"       | 40666       | 59121         | "WEB"   |
      | "I have initiated CVS carepass membership cancellation today under monthly membership and have  $10 reward to redeem in next 30 days" | 98158312      | "U"        | "In 30 days" | "In 30 days"       | "98158312_40666"       | 40666       | 59121         | "WEB"   |
      | "I have initiated CVS carepass membership cancellation today under yearly membership and have  $10 reward to redeem in next 25 days"  | 98158313      | "U"        | "In 25 days" | "In 360 days"      | "98158313_40666"       | 40666       | 59121         | "WEB"   |


  @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view CarePass Details in DashBoard for my card
    Then I see the Carepass Rewards
    And I see my Carepass status Code as <status_code>
    Examples:
      | scenario                                                                                                              | xtra_card_nbr | status_code | channel |
      | "I cancelled my CVS carepass membership yesterday and redeemed $10 monthly reward today"                              | 98158314      | "U"        | "WEB"   |
      | "My monthly CVS carepass membership payment did not go through yesterday and no $10 reward is available"              | 98158315      | "H"         | "WEB"   |
      | "I have initiated CVS carepass membership cancellation today under monthly membership and no $10 reward is available" | 98158316      | "U"        | "WEB"   |
      | "I have initiated CVS carepass membership cancellation today under yearly membership and no $10 reward is available"  | 98158317      | "U"        | "WEB"   |
      | "I cancelled my CVS carepass membership last year"                                                                    | 98158318      | "U"         | "WEB"   |
   

 