#Author: Vani.Puranam@CVSHealth.com
@HonorEscalating-CEBCmpgnEarn
Feature: Honor escalating circular ExtraBucks online-Campaign Earn


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $80 and scanQty as 1 and make sure $5 EB are generated since the customer has met the first dollar threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount2> <scanqty2> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt2> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt2> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty2> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty2> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr2>
    And I see my offerLimitReached as <offerLimitReachedInd2> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type       | cpnNbr2 | cpnSeqNbr2         | prevPtsAmt2 | newPtsAmt2 | prevRwrdsQty2 | newRwrdsQty2 | offerLimitReachedInd2 | amount2 | scanqty2 |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $80 and scanQty as 1 and make sure $5 EB are generated since the customer has met the first dollar threshold.Run a second transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $80 and scanQty as 1 and use the coupon generated ($5) during the first visit and make sure $5 EB are generated since the customer has met the first dollar threshold and the discounts are not applicable CEB rewards calculation." | 900058720     | 10     | 1       | 72626       | 730027         | 0          | 10        | 0            | 4           | "false"              | 106623 | "900058720_106623" | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" | 106624  | "900058720_106624" | 10          | 20         | 4             | 10           | "true"                | 10      | 1        |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $8 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                      | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | channel | type       |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $8 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold." | 900058721     | 8      | 1       | 72626       | 730027         | 0          | 8         | 0            | 0           | "false"              | "WEB"   | "eligible" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $12 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold but not second.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                      | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | channel | type       | printenddt         | cpnSeqNbr          |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $12 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold and not the second dollar threshold" | 900058722     | 12     | 1       | 72626       | 730027         | 0          | 12        | 0            | 4           | "false"              | 106623 | "WEB"   | "eligible" | "DAYS_TO_PRNT_CNT" | "900058722_106623" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $20 and scanQty as 2 and make sure $10 EB are generated since the customer has met the second dollar threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                    | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | channel | type       | printenddt         | cpnSeqNbr          |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 2 and make sure $10 EB are generated since the customer has met the second dollar threshold" | 900058723     | 20     | 2       | 72626       | 730027         | 0          | 20        | 0            | 10          | "true"               | 106625 | "WEB"   | "eligible" | "DAYS_TO_PRNT_CNT" | "900058723_106625" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $40 and scanQty as 4 and make sure $10 EB are generated since the customer has met the second dollar threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                         | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | channel | type       | printenddt         |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $40 and scanQty as 4 and make sure only $10 EB are generated since the customer has met the second dollar threshold" | 900058724     | 40     | 4       | 72626       | 730027         | 0          | 20        | 0            | 10          | "true"               | 106625 | "900058724_106625" | "WEB"   | "eligible" | "DAYS_TO_PRNT_CNT" |

  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $10 and scanQty as 1 and a non eligible product in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $5 EB are generated since the customer has met the first dollar threshold
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                             | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | channel | type   | printenddt         |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $10 and scanQty as 1 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $5 EB are generated since the customer has met the first dollar threshold" | 900058725     | 10     | 1       | 10        | 1          | 72626       | 730027         | 356675         | 0          | 10        | 0            | 4           | "false"              | 106623 | "900058725_106623" | "WEB"   | "both" | "DAYS_TO_PRNT_CNT" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $20 and scanQty as 2 and a non eligible product in the basket with extndScanAmt as $20 and scanQty as 2 and make sure $10 EB are generated since the customer has met the second dollar threshold
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                               | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | channel | type   | printenddt         |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $20 and scanQty as 2 and a non eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 2 and make sure $10 EB are generated since the customer has met the second dollar threshold" | 900058726     | 20     | 2       | 20        | 2          | 72626       | 730027         | 356675         | 0          | 20        | 0            | 10          | "true"               | 106625 | "900058726_106625" | "WEB"   | "both" | "DAYS_TO_PRNT_CNT" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $6 and scanQty as 1 and a non eligible product in the basket with extndScanAmt as $10 and scanQty as 1 and make sure NO EB are generated since the customer has met the first dollar threshold
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | channel | type   |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $6 and scanQty as 1 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold" | 900058727     | 6      | 1       | 10        | 1          | 72626       | 730027         | 356675         | 0          | 6         | 0            | 0           | "false"              | "WEB"   | "both" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with a non eligible product with extndScanAmt as $12 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first dollar threshold
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see <earnings> earnings
    Examples:
      | scenario                                                                                                                                                                                                                                                       | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | channel | type          | earnings |
      | "Run an online transaction for a customer with a non eligible product (SKU) in the basket with extndScanAmt as $12 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first dollar threshold using any qualifying products" | 900058728     | 12     | 2       | 72626       | 356675         | "WEB"   | "noneligible" | 0        |

  @Smoke @Regression
  Scenario Outline: Run an online transaction for a customer(with an Employee discount) with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $4 EB are generated
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                                    | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | channel | type       | printenddt         |
      | "Run an online transaction for a customer(with an Employee discount) with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold and Employee discount will not be sent as part of the Earn API request" | 900058729     | 10     | 1       | 72626       | 730027         | 0          | 10        | 0            | 4           | "false"              | 106623 | "900058729_106623" | "WEB"   | "eligible" | "DAYS_TO_PRNT_CNT" |

  @Smoke @Regression
  Scenario Outline: Run an online transaction with a non eligible product with extndScanAmt as $120 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first dollar threshold
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount2> <scanqty2> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt2> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt2> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty2> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty2> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr2>
    And I see my offerLimitReached as <offerLimitReachedInd2> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt3> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt2> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty3> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty2> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd2> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount3> <scanqty3> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt3> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt2> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty3> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty2> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd2> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type       | cpnNbr2 | cpnSeqNbr2         | prevPtsAmt2 | newPtsAmt2 | prevRwrdsQty2 | newRwrdsQty2 | offerLimitReachedInd2 | amount2 | scanqty2 | amount3 | scanqty3 | prevPtsAmt3 | prevRwrdsQty3 |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure $4 EB are generated since the customer has met the first dollar threshold and not the second dollar threshold.Run a second online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $40 and scanQty as 1 and make sure $5 EB are generated since the customer has met the second dollar threshold and the points from the previous transaction will rollover to this transaction.Run a third online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $80 and scanQty as 1 and make sure NO EB are generated since the customer has already earned the maximum number of rewards as part of this campaign.Run a fourth online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $120 and scanQty as 2 and make sure NO EB are generated since the customer has already earned the maximum number of rewards as part of this campaign." | 900058730     | 10     | 1       | 72626       | 730027         | 0          | 10        | 0            | 4           | "false"              | 106623 | "900058730_106623" | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" | 106624  | "900058730_106624" | 10          | 20         | 4             | 10           | "true"                | 10      | 1        | 20      | 2        | 20          | 10            |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with a non eligible product with extndScanAmt as $120 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first dollar threshold
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt2> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt2> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty2> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty2> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt3> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt3> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty3> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty3> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt4> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt4> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty4> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty4> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr2>
    And I see my offerLimitReached as <offerLimitReachedIndtwo> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt5> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt5> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty5> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty5> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedIndtwo> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type       | cpnNbr2 | cpnSeqNbr2         | prevPtsAmt2 | newPtsAmt2 | prevRwrdsQty2 | newRwrdsQty2 | offerLimitReachedInd2 | prevPtsAmt3 | newPtsAmt3 | prevRwrdsQty3 | newRwrdsQty3 | prevPtsAmt4 | newPtsAmt4 | prevRwrdsQty4 | newRwrdsQty4 | prevPtsAmt5 | newPtsAmt5 | prevRwrdsQty5 | newRwrdsQty5 | offerLimitReachedIndtwo |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first dollar threshold. Run a second online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure $6 EB are generated since the customer has met the first dollar threshold and the points from the previous transaction will rollover to this transaction. Run a third online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure NO EB are generated since the customer has met the second dollar threshold and the points from the previous transactions will rollover to this transaction.Run a fourth online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure $6 EB are generated since the customer has already earned the maximum number of rewards as part of this campaign.Run a fifth online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $5 and scanQty as 1 and make sure NO EB are generated since the customer has already earned the maximum number of rewards as part of this campaign." | 900058731     | 5      | 1       | 72626       | 730027         | 0          | 5         | 0            | 0           | "false"              | 106623 | "900058731_106623" | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" | 106624  | "900058731_106624" | 5           | 10         | 0             | 4            | "true"                | 10          | 15         | 4             | 4            | 15          | 20         | 4             | 10           | 20          | 20         | 10            | 10           | "true"                  |

  @Smoke @Regression
  Scenario Outline: Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure $6 EB are generated since the customer has met the first quantity threshold.Run an online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 along with the coupon generated during visit 1 ($6 EB) and make sure $8 EB are generated since the customer has met the third dollar threshold and the discounts are not applicable CEB rewards calculation.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount2> <scanqty2> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt2> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt2> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty2> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty2> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr2>
    And I see my offerLimitReached as <offerLimitReachedInd2> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type       | cpnNbr2 | cpnSeqNbr2         | prevPtsAmt2 | newPtsAmt2 | prevRwrdsQty2 | newRwrdsQty2 | offerLimitReachedInd2 | amount2 | scanqty2 |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure $6 EB are generated since the customer has met the first quantity threshold.Run an online transaction for the same customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 along with the coupon generated during visit 1 ($6 EB) and make sure $8 EB are generated since the customer has met the third dollar threshold and the discounts are not applicable CEB rewards calculation." | 900058732     | 10     | 2       | 46680       | 301807         | 0          | 2         | 0            | 6           | "false"              | 107101 | "900058732_107101" | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" | 107102  | "900058732_107102" | 2           | 4          | 6             | 14           | "true"                | 10      | 2        |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product with extndScanAmt as $10 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                         | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | channel | type       |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 1 and make sure NO EB are generated since the customer has not met the first quantity threshold." | 900058733     | 10     | 1       | 46680       | 301807         | 0          | 1         | 0            | 0           | "false"              | "WEB"   | "eligible" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 3 and make sure $10 EB are generated since the customer has met the second quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                      | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr        | printenddt         | channel | type       |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 3 and make sure $10 EB are generated since the customer has met the second quantity threshold" | 900058735     | 10     | 3       | 46680       | 301807         | 0          | 3         | 0            | 10          | "false"              | 107103 | 900058735_107103 | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" |

  @Smoke @Regression
  Scenario Outline: Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 4 and make sure $14 EB are generated since the customer has met the third quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                      | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type       |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 4 and make sure $14 EB are generated since the customer has met the third quantity threshold." | 900058736     | 20     | 4       | 46680       | 301807         | 0          | 4         | 0            | 14          | "true"               | 107104 | "900058736_107104" | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" |

  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $50 and scanQty as 10 and make sure $14 EB are generated since the customer has met the third quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                      | xtra_card_nbr | amount | scanqty | campaign_id | contSkuNbrList | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type       |
      | "Run an online transaction for a customer with an eligible product (SKU) in the basket with extndScanAmt as $50 and scanQty as 10 and make sure $14 EB are generated since the customer has met the third quantity threshold" | 900058737     | 50     | 10      | 46680       | 301807         | 0          | 4         | 0            | 14          | "true"               | 107104 | "900058737_107104" | "DAYS_TO_PRNT_CNT" | "WEB"   | "eligible" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $10 and scanQty as 2 and non eligible product with extndScanAmt as $10 and scanQty as 2 and make sure $2 EB since the customer has met the first quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                               | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type   |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $10 and scanQty as 2 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure $2 EB are generated since the customer has met the first quantity threshold" | 900058738     | 10     | 2       | 10        | 2          | 46680       | 301807         | 356675         | 0          | 2         | 0            | 6           | "false"              | 107101 | "900058738_107101" | "DAYS_TO_PRNT_CNT" | "WEB"   | "both" |

  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $10 and scanQty as 2 and non eligible product with extndScanAmt as $10 and scanQty as 2 and make sure $2 EB since the customer has met the first quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                 | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type   |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $20 and scanQty as 3 and a non eligible product (SKU) in the basket with extndScanAmt as $20 and scanQty as 3 and make sure $10 EB are generated since the customer has met the second quantity threshold" | 900058739     | 20     | 3       | 20        | 3          | 46680       | 301807         | 356675         | 0          | 3         | 0            | 10          | "false"              | 107103 | "900058739_107103" | "DAYS_TO_PRNT_CNT" | "WEB"   | "both" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $10 and scanQty as 2 and non eligible product with extndScanAmt as $10 and scanQty as 2 and make sure $2 EB since the customer has met the first quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my cpnNbr got inserted in XtraCardActiveCoupon for <campaign_id> <cpnSeqNbr>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    And I see my expireDt as <printenddt> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | cpnNbr | cpnSeqNbr          | printenddt         | channel | type   |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $30 and scanQty as 4 and a non eligible product (SKU) in the basket with extndScanAmt as $30 and scanQty as 4 and make sure $14 EB are generated since the customer has met the third quantity threshold" | 900058740     | 30     | 4       | 30        | 4          | 46680       | 301807         | 356675         | 0          | 4         | 0            | 14          | "true"               | 107104 | "900058740_107104" | "DAYS_TO_PRNT_CNT" | "WEB"   | "both" |

  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $5 and scanQty as 1 and a non eligible product in the basket with extndScanAmt as $10 and scanQty as 2  make sure NO EB are generated since the customer has met the first quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for eligible and noneligible <xtra_card_nbr> <type> <amount> <scanqty> <contSkuNbrList> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see my prevPtsAmt as <prevPtsAmt> for campaign <campaign_id>
    And I see my newPtsAmt as <newPtsAmt> for campaign <campaign_id>
    And I see my prevRwrdsQty as <prevRwrdsQty> for campaign <campaign_id>
    And I see my newRwrdsQty as <newRwrdsQty> for campaign <campaign_id>
    And I see my offerLimitReached as <offerLimitReachedInd> for campaign <campaign_id>
    Examples:
      | scenario                                                                                                                                                                                                                                                                                                  | xtra_card_nbr | amount | scanqty | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | prevPtsAmt | newPtsAmt | prevRwrdsQty | newRwrdsQty | offerLimitReachedInd | channel | type   |
      | "Run an online transaction for a customer with an eligible product (SKU) with extndScanAmt as $5 and scanQty as 1 and a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first quantity threshold" | 900058741     | 5      | 1       | 10        | 2          | 46680       | 301807         | 356675         | 0          | 1         | 0            | 0           | "false"              | "WEB"   | "both" |


  @Smoke @Regression
  Scenario Outline: Run an online transaction with an eligible product in the basket with extndScanAmt as $5 and scanQty as 1 and a non eligible product in the basket with extndScanAmt as $10 and scanQty as 2  make sure NO EB are generated since the customer has met the first quantity threshold.
    Given <scenario>
    And I use channel <channel>
    And I use my Extra Card <xtra_card_nbr>
    When I want Honor escalating circular ExtraBucks to be Created for <xtra_card_nbr> <type> <nonamount> <nonscanqty> <nonEligibleSku>
    Then I see the Campaign Earn Response
    And I see <earnings> earnings
    Examples:
      | scenario                                                                                                                                                                                                                                                         | xtra_card_nbr | nonamount | nonscanqty | campaign_id | contSkuNbrList | nonEligibleSku | channel | type          | earnings |
      | "Run an online transaction for a customer with a non eligible product (SKU) in the basket with extndScanAmt as $10 and scanQty as 2 and make sure NO EB are generated since the customer has not met the first quantity threshold using any qualifying products" | 900058742     | 10        | 2          | 46680       | 301807         | 356675         | "WEB"   | "noneligible" | 0        |


