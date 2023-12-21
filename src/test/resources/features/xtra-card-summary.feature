#Author: Vani.Puranam@CVSHealth.com

@XtraCardSummary
Feature: View Customer ExtraCard Summary

  @Web @runThis
  Scenario Outline: View Customer ExtraCard Summary Details in DashBoard
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I view Summary in Extracare DashBoard for my card
    Then I see the Summary Details
    And I see Total Year to date save Amount as <tot_ytd_save_amt>
    And I see Total Lifetime Savings Amount as <tot_lifetime_save_amt>
    And I see Available Extra Bucks as <avail_ebs>
    And I see Card Member Date as <card_mbr_dt>
    And I see Year to Date Savings Start Date as <ytd_start_dt>

    Examples:
      | scenario                                                                   | xtra_card_nbr | avail_ebs | tot_ytd_save_amt | tot_lifetime_save_amt | card_mbr_dt  | ytd_start_dt | channel |
      | "I recently joined as Extracare Member"                                    | 98158260      | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member since Feb 2020"                         | 98158261      | 7.00      | 9.50             | 10.00                 | "2020-02-01" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member since March 2018"                       | 98158262      | 9.00      | 12.00            | 50.00                 | "2018-03-01" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member since March 2013"                       | 98158263      | 59.00     | 6.00             | 50000.00              | "2015-01-01" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member since December 31st 2014"               | 98158264      | 38.00     | 7.00             | 100.00                | "2015-01-01" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member and an Employee"                        | 98158271      | 3.00      | 2.00             | 10.00                 | "2019-12-31" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member and have Caremark Customer Heatlh Card" | 98158273      | 8.00      | 17.00            | 40.00                 | "2017-11-30" | "2023-01-01" | "WEB"   |
      | "I will be a New Extracare Member planning to join from Jan 2nd 2021"      | 98158275      | 8.00      | 17.00            | 40.00                 | "2021-01-02" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member since Jan 1st 2015"                     | 98158258      | 38.00     | 7.00             | 100.00                | "2015-01-01" | "2023-01-01" | "WEB"   |
      | "I am an Existing Extracare Member since Jun 28th 2015"                    | 98158259      | 38.00     | 7.00             | 100.00                | "2015-06-28" | "2023-01-01" | "WEB"   |

  @Mobile
  Scenario Outline: View Customer ExtraCard Summary Details in DashBoard
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I view Summary in Extracare DashBoard for my card
    Then I see the Summary Details
    And I see Total Year to date save Amount as <tot_ytd_save_amt>
    And I see Total Lifetime Savings Amount as <tot_lifetime_save_amt>
    And I see Available Extra Bucks as <avail_ebs>
    And I see Card Member Date as <card_mbr_dt>
    And I see Year to Date Savings Start Date as <ytd_start_dt>

    Examples:
      | scenario                                                                   | xtra_card_nbr | avail_ebs | tot_ytd_save_amt | tot_lifetime_save_amt | card_mbr_dt  | ytd_start_dt | channel  |
      | "I recently joined as Extracare Member"                                    | 98158265      | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "MOBILE" |
      | "I am an Existing Extracare Member since Feb 2020"                         | 98158266      | 7.00      | 9.50             | 10.00                 | "2020-02-01" | "2023-01-01" | "MOBILE" |
      | "I am an Existing Extracare Member since March 2018"                       | 98158267      | 9.00      | 12.00            | 50.00                 | "2018-03-01" | "2023-01-01" | "MOBILE" |
      | "I am an Existing Extracare Member since March 2013"                       | 98158268      | 59.00     | 6.00             | 50000.00              | "2015-01-01" | "2023-01-01" | "MOBILE" |
      | "I am an Existing Extracare Member since December 31st 2014"               | 98158269      | 38.00     | 7.00             | 100.00                | "2015-01-01" | "2023-01-01" | "MOBILE" |
      | "I am an Existing Extracare Member and an Employee"                        | 98158270      | 3.00      | 2.00             | 10.00                 | "2019-12-31" | "2023-01-01" | "MOBILE" |
      | "I am an Existing Extracare Member and have Caremark Customer Heatlh Card" | 98158272      | 8.00      | 17.00            | 40.00                 | "2017-11-30" | "2023-01-01" | "MOBILE" |

  @Mobile @Negative
  Scenario Outline: View Customer ExtraCard Summary Details in DashBoard for Negative use cases
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I view Summary in Extracare DashBoard for my card
    Then I do not see the Summary Details
    And I see Error Code as <error_cd>
    And I see Error Message as <error_msg>

    Examples:
      | scenario                                                   | xtra_card_nbr | error_cd | error_msg          | channel  |
      | "I am using my Walgreens card to look my Dashboard"        | 500001668     | 4        | "Card Not on File" | "MOBILE" |
      | "I am looking at my Dashboard after I purged my profile"   | 990090723     | 4        | "Card Not on File"              | "MOBILE" |
      | "I am a fraudulent user, I would like to see my Dashboard" | 98158274      | 5        | "HOT XC Card"      | "MOBILE" |


  @Web @ignore
  Scenario Outline: View Customer ExtraCard Summary Details in DashBoard
    Given <scenario>
    And I use <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I view Summary in Extracare DashBoard for my card
    Then I see the Summary Details
    And I see Total Year to date save Amount as <tot_ytd_save_amt>
    And I see Total Lifetime Savings Amount as <tot_lifetime_save_amt>
    And I see Available Extra Bucks as <avail_ebs>
    And I see Card Member Date as <card_mbr_dt>
    And I see Year to Date Savings Start Date as <ytd_start_dt>
    And I see earningsType indicator as <xtra_card_nbr> <bc_earnings_type>
    Examples:
      | scenario                                                                                                              | xtra_card_nbr | avail_ebs | tot_ytd_save_amt | tot_lifetime_save_amt | card_mbr_dt  | ytd_start_dt | bc_earnings_type | channel |
      | "I am existing Default BC Customer and want to see earningsType indicator as D"                                       | 900058661     | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "D"              | "WEB"   |
      | "I am existing XC customer but never enrolled in any BC program and want to see earningsType indicator as D"          | 900058662     | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "D"              | "WEB"   |
      | "I am new XC Customer and enrolled in 10% BC program and want to see earningsType indicator as P"                     | 900058663     | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "P"              | "WEB"   |
      | "I am new XC Customer and enrolled in Free Item BC program and want to see earningsType indicator as P"               | 900058664     | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "P"              | "WEB"   |
      | "I am new XC Customer and enrolled in both Free Item and 10% BC programs and want to see earningsType indicator as P" | 900058665     | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "P"              | "WEB"   |
      | "I am existing XC Customer and I have unenrolled from BC program and want to see earningsType indicator as D"         | 900058666     | 0.00      | 0.00             | 0.00                  | "2020-01-01" | "2023-01-01" | "D"              | "WEB"   |