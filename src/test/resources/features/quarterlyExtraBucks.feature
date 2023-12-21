#Author: Vani.Puranam@CVSHealth.com
@QEB
Feature: View Customer Quarterly Extra Bucks Details

  @Web @Mobile
  Scenario Outline: View Customer quarterly Extra Bucks Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view Quarterly Extra Bucks Details in DashBoard for my card
    Then I see the QEB Rewards
    And I see my QEB Campaign Id as <campaign_id>
    And I see my QEB <web_description>
    And I see my QEB Campaign End Date as <campaign_end_date>
    And I see my QEB Threshold Type Code as <threshold_type_code>
    And I see my QEB First Threshold as <first_threshold>
    And I see my QEB Reward Amount as <reward_amount>
    And I see my QEB Points required to get next coupon as <points_to_next_threshold>
    And I see my QEB Offer Limit Reached as <offer_limit_reached>
    And I see my QEB Extrabuck Rewards Earned as <extrabuck_rewards_earned>
    And I see my QEB Coupon Issue Date as <coupon_issue_date>
    And I see my QEB Points Progress as <points_progress>

    Examples:
      | scenario                                                                                       | xtra_card_nbr | campaign_id | web_description              | campaign_end_date | threshold_type_code | first_threshold | reward_amount | points_to_next_threshold | offer_limit_reached | extrabuck_rewards_earned | coupon_issue_date | points_progress | channel | enrolled |
      | "I am CVS EC card member and made $15 purchase in current quarter"                             | 98158300      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 35.00                    | "false"             | 0.00                     | 104               | 15.00           | "WEB"   | "true"   |
      | "I am CVS EC card member and made $60 purchase in current quarter"                             | 98158301      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 40.00                    | "false"             | 1.00                     | 104               | 10.00           | "WEB"   | "true"   |
      | "I am CVS EC card member and made $0 purchase in current quarter"                              | 98158302      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 50.00                    | "false"             | 0.00                     | 104               | 0.00            | "WEB"   | "true"   |
      | "I am CVS EC card member and made $50 purchase in current quarter"                             | 98158303      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 50.00                    | "false"             | 1.00                     | 104               | 0.00            | "WEB"   | "true"   |
      | "I am CVS EC card member and made $25 purchase in in-progress and existing campaign"           | 98158304      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 30.00                    | "false"             | 0.00                     | 104               | 20.00           | "WEB"   | "true"   |
      | "I am CVS EC card member and made $150 purchase in current quarter"                            | 98158305      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 50.00                    | "false"             | 3.00                     | 104               | 0.00            | "WEB"   | "true"   |
      | "I am CVS EC card member and made $150 purchase in previous quarter and $0 in current quarter" | 98158306      | 56264       | "This_Quarter_Year_Spending" | 1                 | "D"                 | 50.00           | 1.00          | 50.00                    | "false"             | 0.00                     | 104               | 0.00            | "WEB"   | "true"   |
      | "I am CVS EC card member and made $15 purchase in current quarter ending today"                | 98158307      | 51682       | "This_Quarter_Year_Spending" | 0                 | "D"                 | 50.00           | 1.00          | 35.00                    | "false"             | 0.00                     | 104               | 15.00           | "WEB"   | "true"   |
      | "I am CVS EC card member and made $15 purchase in current quarter starting today"              | 98158308      | 58909       | "This_Quarter_Year_Spending" | 89                | "D"                 | 50.00           | 1.00          | 35.00                    | "false"             | 0.00                     | 104               | 15.00           | "WEB"   | "true"   |


  @Mobile @Negative
  Scenario Outline: View Customer quarterly Extra Bucks Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I view quarterlyExtraBucks Details in DashBoard for my card
    Then I do not see the QEB Rewards
    And I see Error Code as <error_cd>
    And I see Error Message as <error_msg>

    Examples:
      | scenario                                                                                  | xtra_card_nbr | error_cd | error_msg          | channel |
      | "I am Not a CVS EC card member but purchased $75 items in  between Jan 1st to June 15th " | 500001669     | 4        | "Card Not on File" | "WEB"   |