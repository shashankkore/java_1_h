#Author: Vani.Puranam@CVSHealth.com
@GetCustCarepass
Feature: Get Customer - CarePass Enrollment

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass coupon as
      | cmpgnId | cpnNbr | expirDt | webDsc              |
      | 40666   | 59121  | 10      | CarePass $10 reward |
    And I get coupons as
      | cmpgnId | cpnNbr | expirDt | expSoonInd |
      | 40666   | 59121  | 10      | N          |
      | 41083   | 61622  | 10      | N          |
    Examples:
      | scenario                                                                                                                                                                                                               | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am a carepass member enrolled under monthly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 30 days" | 98158410      | "E"       | 10                     | 10        | "M"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass coupon as
      | cmpgnId | cpnNbr | expirDt | webDsc              |
      | 40666   | 59121  | 1       | CarePass $10 reward |
    And I get coupons as
      | cmpgnId | cpnNbr | expirDt | expSoonInd |
      | 40666   | 59121  | 1       | Y          |
    Examples:
      | scenario                                                                                                                                                                                            | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am a carepass member who is an employee and enrolled under monthly program and want to fetch only $10 coupon and status as E and benefit eligibility date as 30 days and expire date as 30 days" | 900058411     | "E"       | 1                      | 1         | "M"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass coupon as
      | cmpgnId | cpnNbr | expirDt | webDsc              |
      | 40666   | 59121  | 30      | CarePass $10 reward |
    And I get yearly coupons as
      | cmpgnId | cpnNbr | expirDt |
      | 40666   | 59121  | 30      |
      | 41083   | 61622  | 30      |
    Examples:
      | scenario                                                                                                                                                                                                               | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am a carepass member enrolled under yearly program where 10$ coupon hasn’t been redeemed and want to fetch $10 and 20% coupons and status as E and benefit eligibility date as 30 days and expire date as 365 days" | 98158412      | "E"       | 29                     | 364       | "Y"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass coupon as
      | cmpgnId | cpnNbr | expirDt | webDsc              |
      | 40666   | 59121  | 10      | CarePass $10 reward |
    And I get coupons as
      | cmpgnId | cpnNbr | expirDt | expSoonInd |
      | 40666   | 59121  | 10      | N          |
      | 41083   | 61622  | 10      | N          |
    Examples:
      | scenario                                                                                                                                                                                                                                        | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am an existing CarePass member and initiated cancellation of my monthly carepass membership today where I haven’t redeemed my 10$ coupon and want to fetch 10$ and 20% coupons and status as CI and benefit_eligibility_date as future date" | 98158413      | "CI"      | 10                     | 10        | "M"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass redeemed coupon as
      | cmpgnId | cpnNbr | expirDt | redeemableInd |
      | 40666   | 59121  | 10      | N             |
    And I get redeemed coupons as
      | cmpgnId | cpnNbr | expirDt | redeemableInd |
      | 40666   | 59121  | 10      | N             |
      | 41083   | 61622  | 10      | Y             |
    Examples:
      | scenario                                                                                                                                                                                                                           | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am an existing CarePass member and initiated cancellation of my yearly carepass membership today where I have redeemed my 10$ coupon and want to fetch 20% coupon and status as CI and benefit_eligibility_date as future date" | 98158414      | "CI"      | 40                     | 40        | "Y"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass redeemed coupon as
      | cmpgnId | cpnNbr | expirDt | redeemableInd |
      | 40666   | 59121  | -5      | N             |
    And I get redeemed coupons as
      | cmpgnId | cpnNbr | expirDt | redeemableInd |
      | 40666   | 59121  | -5      | N             |
      | 41083   | 61622  | -5      | N             |
    Examples:
      | scenario                                                                                                                                                                             | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am an CarePass member and cancelled my monthly carepass membership 5 day ago and want to fetch the status as U and benefit_eligibility_date as past date and redeemable Ind as N" | 98158415      | "U"       | -5                     | -5        | "M"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt as <benefit_eligibility_dt>
    And I see expiry Dt as <expiry_dt>
    And I see plan Type as <plan_type>
    And I get Carepass redeemed coupon as
      | cmpgnId | cpnNbr | expirDt | redeemableInd |
      | 40666   | 59121  | -5      | N             |
    And I get redeemed coupons as
      | cmpgnId | cpnNbr | expirDt | redeemableInd |
      | 40666   | 59121  | -5      | N             |
      | 41083   | 61622  | -5      | N             |
    Examples:
      | scenario                                                                                                                                                                             | xtra_card_nbr | status_cd | benefit_eligibility_dt | expiry_dt | plan_type |
      | "I am an CarePass member and cancelled my yearly carepass membership 5 days ago and want to fetch the status as U and benefit_eligibility_date as past date and redeemable Ind as N" | 98158416      | "U"       | -340                   | -5        | "Y"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see plan Type as <plan_type>
    And I get Carepass coupon as
      | cmpgnId | cpnNbr | expirDt | webDsc              |
      | 40666   | 59121  | 0       | CarePass $10 reward |
    And I get coupons as
      | cmpgnId | cpnNbr | expirDt | expSoonInd |
      | 40666   | 59121  | 0       | Y          |
      | 41083   | 61622  | 0       | Y          |
    Examples:
      | scenario                                                                                                                               | xtra_card_nbr | status_cd | plan_type |
      | "I am a CarePass member with Hold status under monthly membership program and want to fetch $10 and 20% coupons with expirDt as today" | 98158417      | "H"       | "M"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see plan Type as <plan_type>
    And I get Carepass coupon as
      | cmpgnId | cpnNbr | expirDt | webDsc              |
      | 40666   | 59121  | 0       | CarePass $10 reward |
    And I get coupons as
      | cmpgnId | cpnNbr | expirDt | expSoonInd |
      | 40666   | 59121  | 0       | Y          |
      | 41083   | 61622  | 0       | Y          |
    Examples:
      | scenario                                                                                                                              | xtra_card_nbr | status_cd | plan_type |
      | "I am a CarePass member with Hold status under yearly membership program and want to fetch $10 and 20% coupons with expirDt as today" | 98158418      | "H"       | "Y"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    And I switch Carepass enrollment from Monthly to yearly with next reward issue date in <eligibility_dt> days
    When I get a response from getCust API for carepass Enrollment
    Then I see the GetCust Response
    And I see the xtra card nbr as <xtra_card_nbr>
    And I see the status cd as <status_cd>
    And I see benefit Eligibility Dt for yearly subscription as <eligibility_dt>
    And I see expiry Dt yearly subscription as <expiry_dt>
    And I see plan Type as <plan_type>
    Examples:
      | scenario                                                                                                                                    | xtra_card_nbr | encoded_xtra_cardnbr | status_cd | eligibility_dt | expiry_dt | plan_type |
      | "I am a CarePass member and switch to yearly program and want to see the expire date didn't changed to 365 days but plan type changed to Y" | 98158419      | 98158419             | "E"       | 10             | 10        | "Y"       |

  @Web @Mobile
  Scenario Outline: Get customer carepass membership and coupons Information
    Given <scenario>
    And I use my Extra Card <xtra_card_nbr>
    When I get a response from getCust API for carepass Enrollment
    Then I do not see the GetCust Response
    And I see Error Code as <error_cd>
    And I see Error Message as <error_msg>
    Examples:
      | scenario                                                                   | xtra_card_nbr | error_msg     | error_cd |
      | "I am an Hot Card member and want to see my Carepass cpns and information" | 98158420      | "HOT XC Card" | 5        |