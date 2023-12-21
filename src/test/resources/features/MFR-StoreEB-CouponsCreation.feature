# Tables validating for MFR Coupon are campaign, campaign_coupon and campaign_o_m_coupon
@MFR-StoreEB
Feature: Create MFR and StoreEB Coupons

  @Smoke @Regression
  Scenario Outline: Create MFR Coupon from Store and online
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I use Campaign ID <campaign_id>
    And I use recent coupon number
    When I want MFR Coupon to be Created for <xtra_card_nbr>
    Then I see the Coupon got Created
    And I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit
    And I see my cpnStatusCd as <cpn_status_cd>
    And I see my cmpgnId as <campaign_id>
    And I see my cpnSkuNbr

    Examples:
      | scenario                                                                                  | xtra_card_nbr | cpn_status_cd | campaign_id | channel |
      | "Am a CVS Customer and joined Extra Care Program today and scanned MFR coupon from Store" | 900058490     | 0             | 43325       | "STORE" |
      | "Am an existing CVS EC Customer and want to get my MFR coupon created through online"     | 900058491     | 0             | 43325       | "WEB"   |
      | "Am an CVS Employee and want to get my MFR coupon created from store"                     | 900058493     | 0             | 43325       | "STORE" |


  @Regression
  Scenario Outline: Create MFR Coupon from Store which already created in online
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I use Campaign ID <campaign_id>
    And I use recent coupon number
    When I want MFR Coupon to be Created for <xtra_card_nbr>
    Then I see the Coupon got Created
    And I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit
    And I use channel <channel1>
    And I use my Extra Card <xtra_card_nbr>
    And I use Campaign ID <campaign_id>
    And I use recent coupon number
    When I want MFR Coupon to be Created for <xtra_card_nbr>
    Then I see the Coupon got Created
    And I see my cpnStatusCd as <cpn_status_cd>
    And I see my cmpgnId as <campaign_id>
    And I see my coupon sequence number as <cpn_seq_nbr>
		
    Examples:
      | scenario                                                                                                                  | xtra_card_nbr | cpn_status_cd | cpn_seq_nbr | campaign_id | channel | channel1 |
      | "Am an existing CVS EC Customer and want to get my MFR coupon created from store when its already created through online" | 900058492     | 14            | 0					  | 43325       | "WEB"   | "STORE"  |


  @Regression
  Scenario Outline: Create MFR Coupon for Invalid Cards
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    And I use Campaign ID <campaign_id>
    And I use recent coupon number
    When I want MFR Coupon to be Created for <xtra_card_nbr>
    Then I see the Coupon got Created
    And I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit
    And I see my cpnStatusCd as <cpn_status_cd>
    And I see my cmpgnId as <campaign_id>
    And I see my cpnSkuNbr

    Examples:
      | scenario                                                                        | xtra_card_nbr | cpn_status_cd | campaign_id | channel |
      | "Am an Walgreens customer and want to get my MFR coupon created through online" | 900058494     | 0             | 43325       | "STORE" |
      | "Am an Hot Card member and want to get my MFR coupon created from store"        | 900058495     | 0             | 43325       | "STORE" |
  
    @Smoke @Regression
  Scenario Outline: Create Store ExtraBucks Coupons from store and online
    Given <scenario>
    And I use <channel>
    And I use my ExtraCard <xtra_card_nbr>
    When I want storeEB Coupon to be Created for amount <amount>
    Then I see storeEB Coupon got Created
    And I see valid coupon is being applied for amount <amount1>
    And I see my ExtraCard as <xtra_card_nbr>
    And I see my storeEB cpnFlag as <cpn_flag>
    And I see my storeEB firstName as <first_name>
    And I see my storeEB lastName as <last_name>
    And I see my cpnSeqNbr got inserted in XtraCardActiveCoupon and XtraCardCouponAudit for StoreEB coupon
    
    Examples:
      | scenario                                                                                                                                                                                    | xtra_card_nbr | cpn_flag | first_name | last_name | channel | amount | amount1 |
      | "I am an existing CVS Customer and joined Extra Care Program today and want to get Store ExtraBucks coupons created for a purchase at a store today and returned at same store on same day" | 900058496     | "P"      | "auto"     | "test"    | "STORE" | 2.00   | "2.00"  |
  		| "I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at a store yesterday and returned at different store today"                               | 900058497     | "P"      | "auto"     | "test"    | "WEB"   | .50    | ".50"   |
      | "I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchased online last week and returned at store today"                                            | 900058498     | "P"      | "auto"     | "test"    | "STORE" | 10.50  | "10.50" |
      | "I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at a store which is Refundable"                                                           | 900058499     | "P"      | "auto"     | "test"    | "WEB"   | 3.15   | "3.15"  |
      | "I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at online which is non-Refundable"                                                        | 900058500     | "P"      | "auto"     | "test"    | "WEB"   | 20.00  | "20.00" |
      | "I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at online which is Refundable and Customer Cancelled the order"                           | 900058501     | "P"      | "auto"     | "test"    | "STORE" | 5.00   | "5.00"  |
      | "I am an existing CVS EC Customer and want to get Store ExtraBucks coupons created for a purchase at online which is non-Refundable and Customer Cancelled the order"                       | 900058502     | "P"      | "auto"     | "test"    | "WEB"   | .90    | ".90"	 |

Scenario Outline: Create StoreEB Coupon for Invalid START_DT
    Given <scenario>
    And I use <channel>
    And I use my ExtraCard <xtra_card_nbr>
    When I want storeEB Coupon to be Created for amount <amount>
    And I see Error Message as <error_msg>
    
    Examples:
      | scenario                                                                       | xtra_card_nbr | channel | amount | error_msg          |
      | "Am an Walgreens Customer and want to get my Store ExtraBucks coupons created" | 900058503     | "STORE" | 2.00   | "Card Not on File" |
  
  