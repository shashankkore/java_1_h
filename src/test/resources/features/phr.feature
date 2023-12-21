#Author: Vani.Puranam@CVSHealth.com
@Phr
Feature: View Customer Phr Rewards

  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I am a non-targered customer with PHR dependent who recently enrolled"
    And I use my Extra Card 98158276
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR "$5 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 10.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    And I see under my PHR who all enrolled as John Doe and Mary Krisher
      | firstName | capped | maxCredits | maxRewardAmount |
      | John      | false  | 100.0      | 50.0            |
      | Mary      | false  | 100.0      | 50.0            |


  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I and my wife are non-targered customers with 2 PHR dependents who recently enrolled"
    And I use my Extra Card 98158277
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 400.0
    And I see my PHR maxRewardAmount as 200.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 59726
    And I see my PHR "$5 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 10.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    And I see under my PHR who all enrolled as John Doe, Mary Krisher, Donna Furter and Tim Richards
      | firstName | capped | maxCredits | maxRewardAmount |
      | Donna     | false  | 100.0      | 50.0            |
      | John      | false  | 100.0      | 50.0            |
      | Mary      | false  | 100.0      | 50.0            |
      | Tim       | false  | 100.0      | 50.0            |


  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I am targered customer with PHR dependent who recently enrolled"
    And I use my Extra Card 98158278
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 64355
    And I see my PHR "$2 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 4.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    And I see under my PHR who all enrolled as John Doe and Mary Krisher
      | firstName | capped | maxCredits | maxRewardAmount |
      | John      | false  | 100.0      | 50.0            |
      | Mary      | false  | 100.0      | 50.0            |


  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I and my wife are targered customers with 2 PHR dependents who recently enrolled"
    And I use my Extra Card 98158279
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 400.0
    And I see my PHR maxRewardAmount as 200.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 64355
    And I see my PHR "$2 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 4.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 0.0
    And I see under my PHR who all enrolled as John Doe, Mary Krisher, Donna Furter and Tim Richards
      | firstName | capped | maxCredits | maxRewardAmount |
      | Donna     | false  | 100.0      | 50.0            |
      | John      | false  | 100.0      | 50.0            |
      | Mary      | false  | 100.0      | 50.0            |
      | Tim       | false  | 100.0      | 50.0            |

  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I and my wife joined PHR as non-targeted customer in 2015"
    And I use my Extra Card 98158280
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 59726
    And I see my PHR "$5 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 4.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 6.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 6.0
    And I see under my PHR who all enrolled as John Doe and Mary Krisher
      | firstName | capped | maxCredits | maxRewardAmount |
      | John      | false  | 100.0      | 50.0            |
      | Mary      | false  | 100.0      | 50.0            |


  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I and my wife joined PHR as targeted customer in 2015"
    And I use my Extra Card 98158281
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as 64355
    And I see my PHR "$2 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 4.0
    And I see my PHR Reward Amount as 2.0
    And I see my PHR Points required to get next coupon as 3.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 1.0
    And I see my PHR year To Date Earned as 0.0
    And I see my PHR year To Date Credits as 1.0
    And I see under my PHR who all enrolled as John Doe and Mary Krisher
      | firstName | capped | maxCredits | maxRewardAmount |
      | John      | false  | 100.0      | 50.0            |
      | Mary      | false  | 100.0      | 50.0            |


#      @Web @Mobile
#  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
#    Given "I and my wife joined PHR as non-targeted customer in 2015 but my wife's HIPPA expired"
#    And I use my Extra Card 98158282
#    When I view PHR Summary in DashBoard for my card
#    Then I see the Pharmacy Health Rewards
#    And I see my PHR enrollment status as "true"
#    And I see my PHR Campaign Id as 59726
#    And I see my PHR "$5 Pharmacy and Health ExtraBucks Rewards"
#    And I see my PHR Campaign End Date as "2020-12-31"
#    And I see my PHR Threshold Type Code as "Q"
#    And I see my PHR First Threshold as 10.0
#    And I see my PHR Reward Amount as 5.0
#    And I see my PHR Points required to get next coupon as 10.0
#    And I see my PHR Offer Limit Reached as "false"
#    And I see my PHR Points Progress as 0.0
#    And I see under my PHR who all enrolled as John Doe and Mary Krisher
#    |firstName |
#    |John    |
#    |Mary      |


  @Web @Mobile
  Scenario: View Customer Pharmacy Health Rewards Details in DashBoard
    Given "I have one of the member filled 100 prescriptions and now I get 50 points"
    And I use my Extra Card 98158283
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see my PHR maxCredits as 200.0
    And I see my PHR maxRewardAmount as 100.0
    And I see my PHR capped status as "true"
    And I see my PHR Campaign Id as 59726
    And I see my PHR "$5 Pharmacy and Health ExtraBucks Rewards"
    And I see my PHR Campaign End Date as "2020-12-31"
    And I see my PHR Threshold Type Code as "Q"
    And I see my PHR First Threshold as 10.0
    And I see my PHR Reward Amount as 5.0
    And I see my PHR Points required to get next coupon as 10.0
    And I see my PHR Offer Limit Reached as "false"
    And I see my PHR Points Progress as 0.0
    And I see my PHR year To Date Earned as 50.0
    And I see my PHR year To Date Credits as 100.0
    And I see under my PHR who all enrolled as John Doe and Mary Krisher
      | firstName | capped | maxCredits | maxRewardAmount |
      | John      | true   | 100.0      | 50.0            |
      | Mary      | true   | 100.0      | 50.0            |


  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment as <enrolled>
    And I see my PHR maxCredits as 0.0
    And I see my PHR maxRewardAmount as 0.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as <campaign_id>
    And I see my PHR <web_description>
    And I see my PHR Campaign End Date as <campaign_end_date>
    And I see my PHR Threshold Type Code as <threshold_type_code>
    And I see my PHR First Threshold as <first_threshold>
    And I see my PHR Reward Amount as <reward_amount>
    And I see my PHR Points required to get next coupon as <points_to_next_threshold>
    And I see my PHR Offer Limit Reached as <offer_limit_reached>
    And I see my PHR Points Progress as <points_progress>
    And I see my PHR year To Date Earned as <year_todate_earned>
    And I see my PHR year To Date Credits as <year_todate_credits>
    Examples:
      | scenario                         | xtra_card_nbr | campaign_id | web_description                                        | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | year_todate_earned | year_todate_credits | channel | enrolled |
      | "I have never enrolled into PHR" | 98158289      | 42239       | "2020 Extra Care Pharmacy and Health Rewards campaign" | "2020-12-31"      | "Q"                 | 10.0            | 5.0           | 10.0                     | "false"             | 0.0             | 0.0                | 0.0                 | "WEB"   | "false"  |


  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment as <enrolled>
    And I see under my PHR who all enrolled as:
    And I see my PHR maxCredits as 0.0
    And I see my PHR maxRewardAmount as 0.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as <campaign_id>
    And I see my PHR <web_description>
    And I see my PHR Campaign End Date as <campaign_end_date>
    And I see my PHR Threshold Type Code as <threshold_type_code>
    And I see my PHR First Threshold as <first_threshold>
    And I see my PHR Reward Amount as <reward_amount>
    And I see my PHR Points required to get next coupon as <points_to_next_threshold>
    And I see my PHR Offer Limit Reached as <offer_limit_reached>
    And I see my PHR Points Progress as <points_progress>
    And I see my PHR year To Date Earned as <year_todate_earned>
    And I see my PHR year To Date Credits as <year_todate_credits>
    Examples:
      | scenario                     | xtra_card_nbr | campaign_id | web_description                             | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | year_todate_earned | year_todate_credits | channel | enrolled |
      | "I have unenrolled from PHR" | 98158290      | 59726       | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 10.0            | 5.0           | 10.0                     | "false"             | 0.0             | 0.0                | 0.0                 | "WEB"   | "false"  |
#  | "I have enrolled in PHR but my HIPPA expired"                               |      98158291 |       59726 | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"              | "Q"                 |              10.0 |             5.0 |                    10.0    | "false"               |               0.0  |    "WEB"   | "false"  |


  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as "true"
    And I see under my PHR who all enrolled true as
      | firstName | capped | maxCredits | maxRewardAmount | cappedDate |
      | John      | true   | 100.0      | 50.0            | 0          |
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped status as "true"
    And I see my PHR Campaign Id as <campaign_id>
    And I see my PHR <web_description>
    And I see my PHR Campaign End Date as <campaign_end_date>
    And I see my PHR Threshold Type Code as <threshold_type_code>
    And I see my PHR First Threshold as <first_threshold>
    And I see my PHR Reward Amount as <reward_amount>
    And I see my PHR Points required to get next coupon as <points_to_next_threshold>
    And I see my PHR Offer Limit Reached as <offer_limit_reached>
    And I see my PHR Points Progress as <points_progress>
    And I see my PHR year To Date Earned as <year_todate_earned>
    And I see my PHR year To Date Credits as <year_todate_credits>
    Examples:
      | scenario                                                    | xtra_card_nbr | campaign_id | web_description                             | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | year_todate_earned | year_todate_credits | channel |
      | "I am a non-targeted customer who filled 150 prescriptions" | 98158288      | 59726       | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 10.0            | 5.0           | 10.0                     | "false"             | 0.0             | 75.0               | 150.0               | "WEB"   |


  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment as <enrolled>
    And I see under my PHR who all enrolled as:
    And I see my PHR maxCredits as 0.0
    And I see my PHR maxRewardAmount as 0.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as <campaign_id>
    And I see my PHR <web_description>
    And I see my PHR Campaign End Date as <campaign_end_date>
    And I see my PHR Threshold Type Code as <threshold_type_code>
    And I see my PHR First Threshold as <first_threshold>
    And I see my PHR Reward Amount as <reward_amount>
    And I see my PHR Points required to get next coupon as <points_to_next_threshold>
    And I see my PHR Offer Limit Reached as <offer_limit_reached>
    And I see my PHR Points Progress as <points_progress>
    And I see my PHR year To Date Earned as <year_todate_earned>
    And I see my PHR year To Date Credits as <year_todate_credits>
    Examples:
      | scenario                     | xtra_card_nbr | campaign_id | web_description                             | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | year_todate_earned | year_todate_credits | channel | enrolled |
      | "I have unenrolled from PHR" | 98158290      | 59726       | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 10.0            | 5.0           | 10.0                     | "false"             | 0.0             | 0.0                | 0.0                 | "WEB"   | "false"  |
#  | "I have enrolled in PHR but my HIPPA expired"                               |      98158291 |       59726 | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"              | "Q"                 |              10.0 |             5.0 |                    10.0    | "false"               |               0.0  |    "WEB"   | "false"  |




  @Web @Mobile
  Scenario Outline: View Customer Pharmacy Health Rewards Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view PHR Summary in DashBoard for my card
    Then I see the Pharmacy Health Rewards
    And I see my PHR enrollment status as <enrolled>
    And I see under my PHR who all enrolled as
      | firstName | capped | maxCredits | maxRewardAmount |
      | John      | false  | 100.0      | 50.0            |
    And I see my Member maxCredits as 100.0
    And I see my Member maxRewardAmount as 50.0
    And I see my Member capped as "false"
    And I see my PHR maxCredits as 100.0
    And I see my PHR maxRewardAmount as 50.0
    And I see my PHR capped as "false"
    And I see my PHR Campaign Id as <campaign_id>
    And I see my PHR <web_description>
    And I see my PHR Campaign End Date as <campaign_end_date>
    And I see my PHR Threshold Type Code as <threshold_type_code>
    And I see my PHR First Threshold as <first_threshold>
    And I see my PHR Reward Amount as <reward_amount>
    And I see my PHR Points required to get next coupon as <points_to_next_threshold>
    And I see my PHR Offer Limit Reached as <offer_limit_reached>
    And I see my PHR Points Progress as <points_progress>
    And I see my PHR year To Date Earned as <year_todate_earned>
    And I see my PHR year To Date Credits as <year_todate_credits>
    Examples:
      | scenario                                              | xtra_card_nbr | campaign_id | web_description                             | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | points_progress | year_todate_earned | year_todate_credits | channel | enrolled |
      | "I recently enrolled in PHR as non-targeted customer" | 98158284      | 59726       | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 10.0            | 5.0           | 10.0                     | "false"             | 0.0             | 0.0                | 0.0                 | "WEB"   | "true"   |
      | "I recently enrolled in PHR as targeted customer"     | 98158285      | 64355       | "$2 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 4.0             | 2.0           | 4.0                      | "false"             | 0.0             | 0.0                | 0.0                 | "WEB"   | "true"   |
      | "I am a targeted customer who joined PHR in 2015"     | 98158286      | 64355       | "$2 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 4.0             | 2.0           | 1.0                      | "false"             | 3.0             | 0.0                | 3.0                 | "WEB"   | "true"   |
      | "I am a non-targeted customer who joined PHR in 2015" | 98158287      | 59726       | "$5 Pharmacy and Health ExtraBucks Rewards" | "2020-12-31"      | "Q"                 | 10.0            | 5.0           | 8.0                      | "false"             | 2.0             | 0.0                | 2.0                 | "WEB"   | "true"   |

