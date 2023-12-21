#Author: Vani.Puranam@CVSHealth.com
@BeautyClub
Feature: View Customer Beauty Club Details

  @Web @Mobile
  Scenario Outline: View Customer Beauty Club Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view Beauty Club Details in DashBoard for my card
    Then I see the Beauty Club Rewards
    And I see my Beauty Club enrollment status as <enrolled>
    And I see my Beauty Club  Campaign Id as <campaign_id>
    And I see my Beauty Club  <web_description>
    And I see my Beauty Club  Campaign End Date as <campaign_end_date>
    And I see my Beauty Club  Threshold Type Code as <threshold_type_code>
    And I see my Beauty Club  First Threshold as <first_threshold>
    And I see my Beauty Club  Reward Amount as <reward_amount>
    And I see my Beauty Club  Points required to get next coupon as <points_to_next_threshold>
    And I see my Beauty Club  Offer Limit Reached as <offer_limit_reached>
    And I see my Beauty Club  Points Progress as <points_progress>

    Examples:
      | scenario                                                                                    | xtra_card_nbr | campaign_id | web_description                                      | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | channel | enrolled |
      | "I am active Beauty Club member"                                                            | 98158292      | 59727       | "$3 ExtraBucks Rewards when you spend $30 on beauty" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 25.43                    | "false"             | 4.57            | "WEB"   | "true"   |
      #| "I have enrolled in Beauty club program but not made any purchased related to beauty items" | 98158293      | 59727       | "$3 ExtraBucks Rewards when you spend $30 on beauty" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 30.00                    | "false"             | 0.00            | "WEB"   | "true"   |
      #| "I have enrolled in Beauty club program and spend on beauty items"                          | 98158294      | 59727       | "$3 ExtraBucks Rewards when you spend $30 on beauty" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 5.43                     | "false"             | 24.57           | "WEB"   | "true"   |
      #| "I am in Beauty Club program since last year"                                               | 98158295      | 59727       | "$3 ExtraBucks Rewards when you spend $30 on beauty" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 15.03                    | "false"             | 14.97           | "WEB"   | "true"   |
      #| "I am in Beauty Club program from Feb 2020 and spent more than $65"                         | 98158296      | 59727       | "$3 ExtraBucks Rewards when you spend $30 on beauty" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 9.99                     | "false"             | 20.01           | "WEB"   | "true"   |
      #| "I am in Beauty Club program from May 2020 and spent less than $30"                         | 98158297      | 59727       | "$3 ExtraBucks Rewards when you spend $30 on beauty" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 29.50                    | "false"             | 0.50            | "WEB"   | "true"   |
#
#
  #@Web @Mobile
  #Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    #Given <scenario>
    #And I use my Extra Card <xtra_card_nbr>
    #When I view Beauty Club Details in DashBoard for my card
    #Then I see the Beauty Club Rewards
    #And I see my Beauty Club enrollment as <enrolled>
    #And I see my Beauty Club  Campaign Id as <campaign_id>
    #And I see my Beauty Club  <web_description>
    #And I see my Beauty Club  Campaign End Date as <campaign_end_date>
    #And I see my Beauty Club  Threshold Type Code as <threshold_type_code>
    #And I see my Beauty Club  First Threshold as <first_threshold>
    #And I see my Beauty Club  Reward Amount as <reward_amount>
#
    #Examples:
      #| scenario                                       | xtra_card_nbr | campaign_id | web_description    | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | channel | enrolled |
      #| "I am Not enrolled in Beauty Club program now" | 98158298      | 44498       | "Beauty Club 2021" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 30.0                     | "false"             | 0.00            | "WEB"   | "false"  |
      #| "I have never enrolled in Beauty Club program" | 98158299      | 44498       | "Beauty Club 2021" | "2021-12-31"      | "D"                 | 30.0            | 3.0           | 30.0                     | "false"             | 0.00            | "WEB"   | "false"  |
