@SetCustCarepass
Feature: Set Customer CarePass Enrollment


  @Smoke @Web @Mobile
  Scenario Outline: Enroll customer in Carepass monthly Program membership
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I get 10 $ coupon <coupon_10>
    And I get 20 % coupon <coupon_20>
    Examples:
      | scenario                                                                                                          | xtra_card_nbr | encoded_xtra_cardnbr | coupon_10        | coupon_20        | type            |
      | "I joined ExtraCare program today to participate in CarePass Monthly program and want to see 10$ and 20% coupons" | 98158393      | 98158393             | "98158393_40666" | "98158393_41083" | "MonthlyEnroll" |


  @Smoke @Web @Mobile
  Scenario Outline: Enroll customer in Carepass yearly Program membership
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I get 10 $ coupon <coupon_10>
    And I get 20 % coupon <coupon_20>
    Examples:
      | scenario                                                                                                         | xtra_card_nbr | encoded_xtra_cardnbr | coupon_10        | coupon_20        | type           |
      | "I joined ExtraCare program today to participate in CarePass Yearly program and want to see 10$ and 20% coupons" | 98158394      | 98158394             | "98158394_40666" | "98158394_41083" | "YearlyEnroll" |


  @Smoke @Web @Mobile
  Scenario Outline: Enroll customer in Carepass monthly Program membership
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I get 10 $ coupon <coupon_10>
    And I get 20 % coupon <coupon_20>
    Examples:
      | scenario                                                                                                             | xtra_card_nbr | encoded_xtra_cardnbr | coupon_10        | coupon_20        | type            |
      | "I am an existing ExtraCare member and joined in CarePass Monthly program today and want to see 10$ and 20% coupons" | 98158395      | 98158395             | "98158395_40666" | "98158395_41083" | "MonthlyEnroll" |


  @Smoke @Web @Mobile
  Scenario Outline: Enroll customer in Carepass monthly Program membership
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I get 10 $ coupon <coupon_10>
    And I get 20 % coupon <coupon_20>
    Examples:
      | scenario                                                                                                            | xtra_card_nbr | encoded_xtra_cardnbr | coupon_10        | coupon_20        | type           |
      | "I am an existing ExtraCare member and joined in CarePass Yearly program today and want to see 10$ and 20% coupons" | 98158396      | 98158396             | "98158396_40666" | "98158396_41083" | "YearlyEnroll" |


  @Regression @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                                         | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                             |
      | "I am an existing CarePass member and initiated cancellation of my monthly carepass membership today and want to see the status as U and benefit_eligibility_date as future date in carepass_member_status_hist and carepass_member_smry tables" | 98158397      | "U"    | 6                      | 98158397             | "Cancellation_Initiated_Monthly" |


  @Regression @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                                        | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                            |
      | "I am an existing CarePass member and initiated cancellation of my yearly carepass membership today and want to see the status as U and benefit_eligibility_date as future date in carepass_member_status_hist and carepass_member_smry tables" | 98158398      | "U"    | -259                   | 98158398             | "Cancellation_Initiated_Yearly" |


  @Regression @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                  | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                   |
      | "I am an CarePass member and cancelled my monthly carepass membership 3 day ago and want to see the status as U and benefit_eligibility_date as past date in carepass_member_status_hist and carepass_member_smry tables" | 98158399      | "U"    | -3                     | 98158399             | "Cancellation_Monthly" |


  @Regression @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                 | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                  |
      | "I am an CarePass member and cancelled my yearly carepass membership 5 day ago and want to see the status as U and benefit_eligibility_date as past date in carepass_member_status_hist and carepass_member_smry tables" | 98158400      | "U"    | -340                   | 98158400             | "Cancellation_Yearly" |


  @Smoke @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see reactivate status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                   | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                 |
      | "I am a CarePass member with Hold status and benefit_eligibility_date as expire date and want to reactivate my monthly membership and see the status changed to E and benefit_eligibility_date changed to future 30 days from current date in carepass_member_status_hist and carepass_member_smry tables" | 98158401      | "E"    | 30                     | 98158401             | "Hold_to_Reactivate" |

  @Smoke @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see reactivate status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                  | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                        |
      | "I am a CarePass member with Hold status and benefit_eligibility_date as expire date and want to reactivate my yearly membership and see the status changed to E and benefit_eligibility_date changed to future 30 days from current date in carepass_member_status_hist and carepass_member_smry tables" | 98158402      | "E"    | 30                     | 98158402             | "Hold_to_Reactivate_Yearly" |


  @Smoke @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see hold status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                                    | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type   |
      | "I am a CarePass member with monthly membership program and my membership status went on Hold and want to see the status changed to H and benefit_eligibility_date as today in carepass_member_status_hist and carepass_member_smry tables" | 98158403      | "H"    | 0                      | 98158403             | "Hold" |


  @Smoke @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see hold status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                                   | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type          |
      | "I am a CarePass member with yearly membership program and my membership status went on Hold and want to see the status changed to H and benefit_eligibility_date as today in carepass_member_status_hist and carepass_member_smry tables" | 98158404      | "H"    | -336                   | 98158404             | "Hold_Yearly" |

  @Regression @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                          | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type            |
      | "I am a CarePass member with Hold status and want to unenroll from monthly membership program and see the status changed to U and benefit_eligibity_date to today in carepass_member_status_hist and carepass_member_smry tables" | 98158405      | "U"    | 0                      | 98158405             | "Hold_Unenroll" |


  @Regression @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    Examples:
      | scenario                                                                                                                                                                                                                         | xtra_card_nbr | status | benefit_eligibility_dt | encoded_xtra_cardnbr | type                   |
      | "I am a CarePass member with Hold status and want to unenroll from yearly membership program and see the status changed to U and benefit_eligibity_date to today in carepass_member_status_hist and carepass_member_smry tables" | 98158406      | "U"    | 0                      | 98158406             | "Hold_Unenroll_Yearly" |


  @Smoke @Web @Mobile @ignore
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I see the SetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the encoded XtraCardNbr as <encoded_xtra_cardnbr>
    And I see enroll status as <status>
    And I see benefit eligibility dt as <benefit_eligibility_dt>
    And I see expire date as <expire_dt>

    Examples:
      | scenario                                                                                                                                                                                                                                     | xtra_card_nbr | status | benefit_eligibility_dt | expire_dt | encoded_xtra_cardnbr | type  |
      | "I am a CarePass member currently under monthly program and would like to switch to yearly program and see the expire date changed to 365 days and benefit eligibility date changes to 1 month from current date and plan type changes to Y" | 98158407      | "E"    | 30                      | 30         | 98158407             | "M2A" |


  @Smoke @Web @Mobile
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I don't see the SetCust Response
    And I see my Error Code as 1
    And I see Error Message as "ERROR: Unsupported search card type provided: 0001"
    Examples:
      | scenario                                                                 | xtra_card_nbr | type               |
      | "I am using invalid search card type and want to see the error response" | 98158408      | "invalid_cardtype" |


  @Smoke @Web @Mobile @ignore
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I enroll in carepass using setCust for type <type>
    Then I don't see the SetCust Response
    And I see my Error Code as 10
    And I see Error Message as "ERROR: Invalid table, Invlaid operation on: CAREPASS_ENROLLFORM_HIST"
    Examples:
      | scenario                                                                                  | xtra_card_nbr | type            |
      | "I am using invalid table name in the request payload and want to see the error response" | 98158409      | "invalid_table" |
