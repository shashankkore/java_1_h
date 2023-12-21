#Author: Vani.Puranam@CVSHealth.com
@dealsInProgress
Feature: View Customer Deals In Progress Details


  @Web @Mobile @issue123
  Scenario: View Customer Deals In Progress Details in DashBoard
    Given "I am CVS EC member but there are no active deals in progress - Zero State"
    And I use my Extra Card 98158319
    And I hit campaign earn service for zero state
    When I view Deals In Progress Details in DashBoard for my card
    Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 41624       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 30.0                     | "false"             | E                  | T                     | 0.0             | "WEB"   |
    And I see my Deals In Progress as
      | campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      | 49213       | PE coupon $5    | 60                | D                   | 30.0            | 1.0           | 30.0                     | "false"             | E                  | T                     | 0.0             | "WEB"   |
    And I see my Deals In Progress NewDeal Indicator as false
      | new_deal |
      | false    |
    And I see my Deals In Progress Deal Ending Indicator as false
      | deal_ending_soon |
      | false            |


  #@Mobile @Negative
  #Scenario Outline: View Customer Deals In Progress Details in DashBoard
    #Given <scenario>
    #And I use my Extra Card <xtra_card_nbr>
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I do not see the DIP Rewards
    #Examples:
      #| scenario                                                                                              | xtra_card_nbr | channel |
      #| "I am CVS EC member but does not have any activity towards Circular EB and Personalized EB campaigns" | 98158320      | "WEB"   |
#
#
  #@Web @Mobile
  #Scenario: View Customer Deals In Progress Details in DashBoard
    #Given "I am CVS EC member and showing deals in progress point is zero"
    #And I use my Extra Card 98158321
    #And I hit campaign earn service for card 98158321 for quantity 0 for amount 0 and sku 200926
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 41624       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 30.0                     | "false"             | E                  | T                     | 0.0             | "WEB"   |
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 49213       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 30.0                     | "false"             | E                  | T                     | 0.0             | "WEB"   |
    #And I see my Deals In Progress NewDeal Indicator as false
      #| new_deal |
      #| false    |
    #And I see my Deals In Progress Deal Ending Indicator as false
      #| deal_ending_soon |
      #| false            |
#
#
  #@Web @Mobile
  #Scenario: View Customer Deals In Progress Details in DashBoard
    #Given "I am able to see deals mapped to correct type based on threshold Q (Qty) and D (Dollar)"
    #And I use my Extra Card 98158322
    #And I hit campaign earn service for card 98158322 for quantity 4 for amount 30 and sku 200926
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 41624       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 30.0                     | "false"             | E                  | T                     | 30.0            | "WEB"   |
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 49213       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 30.0                     | "false"             | E                  | T                     | 30.0            | "WEB"   |
    #And I see my Deals In Progress NewDeal Indicator as false
      #| new_deal |
      #| false    |
    #And I see my Deals In Progress Deal Ending Indicator as false
      #| deal_ending_soon |
      #| false            |
#
  #@Web @Mobile
  #Scenario: View Customer Deals In Progress Details in DashBoard
    #Given "I am CVS EC member and have at least one deals in progress"
    #And I use my Extra Card 98158323
    #And I hit campaign earn service for card 98158323 for quantity 4 for amount 40 and sku 200926
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 41624       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 20.0                     | "false"             | E                  | T                     | 40.0            | "WEB"   |
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 49213       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 20.0                     | "false"             | E                  | T                     | 40.0            | "WEB"   |
    #And I see my Deals In Progress NewDeal Indicator as false
      #| new_deal |
      #| false    |
    #And I see my Deals In Progress Deal Ending Indicator as false
      #| deal_ending_soon |
      #| false            |
#
#
  #@Web @Mobile
  #Scenario: View Customer Deals In Progress Details in DashBoard
    #Given "I am CVS EC member but deal is expiring soon"
    #And I use my Extra Card 98158324
    #And I hit campaign earn service for card 98158324 for quantity 4 for amount 10 and sku 999975
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description              | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 40225       | PEB_FF_CYL_$2EB on 4 Candies | 2                 | Q                   | 4.0             | 2.0           | 2.0                      | "false"             | E                  | T                     | 10.0            | "WEB"   |
    #And I see my Deals In Progress as
      #| campaign_id | web_description              | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 40963       | PEB_FF_CYL_$2EB on 4 Candies | 2                 | Q                   | 4.0             | 2.0           | 2.0                      | "false"             | E                  | T                     | 10.0            | "WEB"   |
    #And I see my Deals In Progress NewDeal Indicator as false
      #| new_deal |
      #| false    |
    #And I see my Deals In Progress Deal Ending Indicator as true
      #| deal_ending_soon |
      #| true             |
#
  #@Web @Mobile
  #Scenario: View Customer Deals In Progress Details in DashBoard
    #Given "I am CVS EC member joined recently in deal"
    #And I use my Extra Card 98158325
    #And I hit campaign earn service for card 98158325 for quantity 4 for amount 32 and sku 2030
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description       			 | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 40226       | PEB_FF_Cyl_$2EB on $30 Cold | 60                | D                   | 30.0            | 2.0           | 28.0                     | "false"             | E                  | T                     | 32.0            | "WEB"   |
    #And I see my Deals In Progress as
      #| campaign_id | web_description       			| campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 40780       | PEB_FF_Cyl_$2EB on $30 Cold | 60                | D                   | 30.0            | 2.0           | 28.0                     | "false"             | E                  | T                     | 32.0            | "WEB"   |
    #And I see my Deals In Progress NewDeal Indicator as true
      #| new_deal |
      #| true     |
    #And I see my Deals In Progress Deal Ending Indicator as false
      #| deal_ending_soon |
      #| false            |
#
#
  #@Web @Mobile
  #Scenario: View Customer Deals In Progress Details in DashBoard
    #Given "I am CVS EC member and showing deals in progress points near threshold"
    #And I use my Extra Card 98158326
    #And I hit campaign earn service for card 98158326 for quantity 29 for amount 10 and sku 200926
    #When I view Deals In Progress Details in DashBoard for my card
    #Then I see the DIP Rewards
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 41624       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 1.0                      | "false"             | E                  | T                     | 29.0            | "WEB"   |
    #And I see my Deals In Progress as
      #| campaign_id | web_description | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | campaign_type_code | campaign_subtype_code | points_quantity | channel |
      #| 49213       | PE coupon $5    | 60                | D                   | 30.0            | 2.0           | 1.0                      | "false"             | E                  | T                     | 29.0            | "WEB"   |
    #And I see my Deals In Progress NewDeal Indicator as false
      #| new_deal |
      #| false    |
    #And I see my Deals In Progress Deal Ending Indicator as false
      #| deal_ending_soon |
      #| false            |
