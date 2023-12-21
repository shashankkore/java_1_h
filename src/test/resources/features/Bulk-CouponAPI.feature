#Author: Vani.Puranam@CVSHealth.com
@Bulk-CpnAPI
Feature: Create Bulk Coupons

  @Smoke @Regression
  Scenario Outline: View a Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my cmpgnId as <campaign_id> <type>
    And I see my cpnSkuNbr1 as <cpn_sku_nbr1> <type>
    And I see my cpnSkuNbr2 as <cpn_sku_nbr2> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type   | match_cd |
      #| "I am an existing CVS EC Customer and want to View a Coupon which is not viewed before" | 900058660     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "view" | 1        |
      | "I am an existing CVS EC Customer and want to View a Coupon which is not viewed before" | 900017912     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "view" | 1        |


  @Smoke @Regression
  Scenario Outline: View an already viewed Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                    | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type   | match_cd |
      #| "I am an existing CVS EC Customer and want to View a Coupon which is already viewed before" | 900058661     | 11            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "view" | 1        |
      | "I am an existing CVS EC Customer and want to View a Coupon which is already viewed before" | 900058661     | 11            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "view" | 1        |


  @Smoke @Regression
  Scenario Outline: Print a Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                  | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type    | match_cd |
      #| "I am an existing CVS EC Customer and want to Print a Coupon which is not printed/Loaded/Redeemed before" | 900058662     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "print" | 1        |
      | "I am an existing CVS EC Customer and want to Print a Coupon which is not printed/Loaded/Redeemed before" | 900017916     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "print" | 1        |

  @Smoke @Regression
  Scenario Outline: Print an already printed Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                            | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type    | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Print a Coupon which is already printed before" | 900058663     | 13            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "print" | 1        |
      | "I am an existing CVS EC Customer and don't want to Print a Coupon which is already printed before" | 900017917     | 13            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "print" | 1        |


  @Smoke @Regression
  Scenario Outline: Print an already loaded Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                           | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type    | pretype | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Print a Coupon which is already loaded before" | 900058664     | 14            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "print" | "load"  | 1        |
      | "I am an existing CVS EC Customer and don't want to Print a Coupon which is already loaded before" | 900017918     | 14            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "print" | "load"  | 1        |

  @Smoke @Regression
  Scenario Outline: Print an already redeemed Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                             | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type    | pretype  | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Print a Coupon which is already redeemed before" | 900058665     | 14            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "print" | "redeem" | 1        |
      | "I am an existing CVS EC Customer and don't want to Print a Coupon which is already redeemed before" | 900017919     | 14            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "print" | "redeem" | 1        |

  @Smoke @Regression
  Scenario Outline: Dont Print a Coupon when Print actual timestamp is greater than print end date
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                                       | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type       | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is greater than print end date" | 900058666     | 16            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "printend" | 1        |
      | "I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is greater than print end date" | 900017920     | 16            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "printend" | 1        |

  @Smoke @Regression
  Scenario Outline: Dont Print a Coupon when Print actual timestamp is less than print start date
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                                      | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type         | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is less than print start date" | 900058667     | 15            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "printstart" | 1        |
      | "I am an existing CVS EC Customer and don't want to Print a Coupon when Print actual timestamp is less than print start date" | 900017921     | 15            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "printstart" | 1        |


  @Smoke @Regression
  Scenario Outline: Load a Coupon
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                 | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type   | match_cd |
      #| "I am an existing CVS EC Customer and want to Load a Coupon which is not loaded/printed/redeemed before" | 900058668     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "load" | 1        |
      | "I am an existing CVS EC Customer and want to Load a Coupon which is not loaded/printed/redeemed before" | 900017922     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "load" | 1        |


  @Smoke @Regression
  Scenario Outline: Dont Load a Coupon which is already Printed
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                             | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type            | pretype | match_cd |
      #| "I am an existing CVS EC Customer and I don't want to Load a Coupon which is already Printed before" | 900058670     | 12            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "loadredeemend" | "print" | 1        |
      | "I am an existing CVS EC Customer and I don't want to Load a Coupon which is already Printed before" | 900017025     | 12            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "loadredeemend" | "print" | 1        |


  @Smoke @Regression
  Scenario Outline: Dont Load a Coupon which is already Printed
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                              | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type   | pretype  | match_cd |
      #| "I am an existing CVS EC Customer and I don't want to Load a Coupon which is already Redeemed before" | 900058671     | 13            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "load" | "redeem" | 1        |
      | "I am an existing CVS EC Customer and I don't want to Load a Coupon which is already Redeemed before" | 900017024     | 13            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "load" | "redeem" | 1        |


  @Smoke @Regression
  Scenario Outline: Digitize a Coupon using Referral Cd as P First Time
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see the record in xtra_card_summary for <xtra_card_nbr>
    Examples:
      | scenario                                                                                           | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type          | match_cd |
      #| "I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P First time" | 900058680     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "predigitize" | 1        |
      | "I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P First time" | 900017026     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "predigitize" | 1        |


  @Smoke @Regression
  Scenario Outline: Digitize a Coupon using Referral Cd as P Second Time
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see the record in xtra_card_summary for <xtra_card_nbr>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                            | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type       | pretype       | match_cd |
      #| "I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P Second time" | 900058681     | 11            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "digitize" | "predigitize" | 1        |
      | "I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P Second time" | 900017911     | 11            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "digitize" | "predigitize" | 1        |


  @Smoke @Regression
  Scenario Outline: Digitize a Coupon using Referral Cd as P and Redeem same
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see the record in xtra_card_summary for <xtra_card_nbr>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                    | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | pretype       | match_cd |
      #| "I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P and Redeem the same" | 900058682     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | "predigitize" | 1        |
      | "I am an existing CVS EC Customer and want to Digitize a Coupon using Referral Cd as P and Redeem the same" | 900017930     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeem" | "predigitize" | 1        |


  @Smoke @Regression
  Scenario Outline: Dont Redeem and Coupon which is not loaded or printed with match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                                    | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type              | match_cd |
      #| "I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is not loaded/printed before with match code 1" | 900058683     | 11            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "notloadedredeem" | 1        |
      | "I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is not loaded/printed before with match code 1" | 900017931     | 11            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "notloadedredeem" | 1        |


  @Smoke @Regression 
  Scenario Outline: Redeem a loaded Coupon for match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                   | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before match code 1" | 900058684     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 1        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before match code 1" | 900017932     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 1        |


  @Smoke @Regression
  Scenario Outline: Redeem a Printed Coupon before match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                    | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type              | match_cd | pretype |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already printed before match code 1" | 900058685     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "notloadedredeem" | 1        | "print" |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already printed before match code 1" | 900017933     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "notloadedredeem" | 1        | "print" |


  @Smoke @Regression
  Scenario Outline: Dont Redeem a redeemed Coupon before match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                             | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | pretype  | type              | match_cd |
      #| "I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is already redeemed before match code 1" | 900058686     | 12            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "redeem" | "notloadedredeem" | 1        |
      | "I am an existing CVS EC Customer and I don't want to Redeem a Coupon which is already redeemed before match code 1" | 900017934     | 12            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "redeem" | "notloadedredeem" | 1        |


  @Smoke @Regression
  Scenario Outline: Dont Redeem a Coupon when Redeem actual timestamp is greater than Redeem end date with match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                                                            | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type        | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is greater than Redeem end date with match code 1" | 900058691     | 15            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "redeemend" | 1        |
      | "I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is greater than Redeem end date with match code 1" | 900017935     | 15            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "redeemend" | 1        |


  @Smoke @Regression
  Scenario Outline: Dont Redeem a Coupon when Redeem actual timestamp is less than Redeem start date with match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                                                                                           | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type          | match_cd |
      #| "I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is less than Redeem start date with match code 1" | 900058692     | 14            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "redeemstart" | 1        |
      | "I am an existing CVS EC Customer and don't want to Redeem a Coupon when Redeem actual timestamp is less than Redeem start date with match code 1" | 900017936     | 14            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "redeemstart" | 1        |


  @Smoke @Regression
  Scenario Outline: Walgreens customer and dont want to View a Coupon which is not viewed before
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    Examples:
      | scenario                                                                               | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type           | match_cd |
      #| "I am a Walgreens customer and don't want to View a Coupon which is not viewed before" | 900058696     | 10            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "viewnegative" | 1        |
      | "I am a Walgreens customer and don't want to View a Coupon which is not viewed before" | 900017937     | 10            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "viewnegative" | 1        |


  @Smoke @Regression
  Scenario Outline: Walgreens customer and dont want to Print a Coupon which is not printed before
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    Examples:
      | scenario                                                                                 | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type            | match_cd |
      #| "I am a Walgreens customer and don't want to Print a Coupon which is not printed before" | 900058697     | 10            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "printnegative" | 1        |
      | "I am a Walgreens customer and don't want to Print a Coupon which is not printed before" | 900058697     | 10            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "printnegative" | 1        |


  @Smoke @Regression
  Scenario Outline: Walgreens customer and dont want to Load a Coupon which is not loaded before
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    Examples:
      | scenario                                                                               | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type           | match_cd |
      #| "I am a Walgreens customer and don't want to Load a Coupon which is not loaded before" | 900058698     | 10            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "loadnegative" | 1        |
      | "I am a Walgreens customer and don't want to Load a Coupon which is not loaded before" | 900058698     | 10            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "loadnegative" | 1        |

  @Smoke @Regression
  Scenario Outline: Walgreens customer and dont want to Redeemed a Coupon which is not printed before with match code 1
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    Examples:
      | scenario                                                                                                   | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type             | match_cd |
      #| "I am a Walgreens customer and don't want to Redeem a Coupon which is not loaded before with match code 1" | 900058699     | 10            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeemnegative" | 1        |
      | "I am a Walgreens customer and don't want to Redeem a Coupon which is not loaded before with match code 1" | 900058699     | 10            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeemnegative" | 1        |

  @Smoke @Regression
  Scenario Outline: Want to see cpn_seq_nbr when Status code is 0
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    Examples:
      | scenario                                                                             | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type   | match_cd |
      #| "I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 0" | 900058700     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "view" | 1        |
      | "I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 0" | 900017938     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "view" | 1        |


  @Smoke @Regression
  Scenario Outline: Want to see cpn_seq_nbr when Status code is 13
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                              | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type    | match_cd |
      #| "I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 13" | 900058701     | 13            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "print" | 1        |
      | "I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 13" | 900017939     | 13            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "print" | 1        |


  @Smoke @Regression
  Scenario Outline: Want to see cpn_seq_nbr when Status code is 14
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <pretype> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                              | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type    | pretype | match_cd |
      #| "I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 14" | 900058702     | 14            | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "STORE" | "print" | "load"  | 1        |
      | "I am an existing CVS EC Customer and want to see cpn_seq_nbr when Status code is 14" | 900017940     | 14            | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "STORE" | "print" | "load"  | 1        |


  @Smoke @Regression
  Scenario Outline: Redeem a Coupon which is already loaded with match code 2
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 2" | 900058703     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 2        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 2" | 900058704     | 0             | 48143       | "48143_120344"   			  | 120344       | "48143_126066"  		     | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 2        |

  @Smoke @Regression
  Scenario Outline: Redeem a Coupon which is already loaded before with match code 3
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 3" | 900058704     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 3        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 3" | 900017901     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 3        |


  @Smoke @Regression
  Scenario Outline: Redeem a Coupon which is already loaded before with match code 4
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 4" | 900058705     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 4        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 4" | 900058705     | 0             | 48143       | "48143_120344"      		 | 120344       | "48143_126066"       	  | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 4        |

  @Smoke @Regression
  Scenario Outline: Redeem a Coupon which is already loaded before with match code 5
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 5" | 900058706     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 5        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 5" | 900058706     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 5        |
  
  @Smoke @Regression
  Scenario Outline: Redeem a Coupon which is already loaded before with match code 6
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 6" | 900058707     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 6        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 6" | 900058707     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 6        |


  @Smoke @Regression
  Scenario Outline: Redeem a Coupon which is already loaded before with match code 7
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I hit campaign earn1 for card <xtra_card_nbr>
    And I hit campaign earn2 for card <xtra_card_nbr>
    When I want Bulk Coupon to be Created for <xtra_card_nbr> <type> <campaign_id> <cpn_sku_nbr1> <cpn_sku_nbr2> <match_cd>
    Then I see the Coupon Response
    And I see my cpnStatusCd as <cpn_status_cd> <type>
    And I see my First cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number1> <type> <match_cd>
    And I see my Second cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit as <xtra_card_nbr> <coupon_sequence_number2> <type> <match_cd>
    And I see my redeemStartDt as <redeem_start_dt> <type>
    And I see my redeemEndDt as <redeem_end_dt> <type>
    Examples:
      | scenario                                                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | coupon_sequence_number1 | cpn_sku_nbr1 | coupon_sequence_number2 | cpn_sku_nbr2 | redeem_start_dt | redeem_end_dt | channel | type     | match_cd |
      #| "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 7" | 900058708     | 0             | 43916       | "43916_103884"          | 103884       | "43916_104064"          | 104064       | "null"          | "null"        | "WEB"   | "redeem" | 7        |
      | "I am an existing CVS EC Customer and want to Redeem a Coupon which is already loaded before with match code 7" | 900058708     | 0             | 48143       | "48143_120344"          | 120344       | "48143_126066"          | 126066       | "null"          | "null"        | "WEB"   | "redeem" | 7        |
