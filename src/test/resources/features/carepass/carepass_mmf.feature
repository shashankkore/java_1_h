@CarePassMMF
Feature: Carepass MMF coupons
	
  @Enroll_mmf_reissue_coupons
 
  Scenario Outline: Reissue CVS customer mmf coupons
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When The user enroll <xtra_card_nbr> in to mmf for reissue coupons
    Then Validate the coupon issued no of times
    
    Examples:
      | xtra_card_nbr|
      | 99991234      |

  @Promo_mmf_coupon	
  Scenario Outline: Issue CVS customer  Carepass mmf coupon
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass monthly
    Then Validate the promo mmf coupon issued to carepass user
    
    Examples:
      | scenario                                  | xtra_card_nbr |
      | "Issue CVS customer mmf carepass coupon" 	| 99991234      |
      
  @Enroll_Monthly_MMF
  Scenario Outline: Enroll CVS customer in Carepass MMF Monthly program
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass
    Then Validate the promo mmf coupon issued to carepass user
    
    Examples:
      | scenario                              | xtra_card_nbr |
      | "Enroll carepass monthly mmf program" | 99991234      |

  @Reissue_carepass_Xnoof_coupons	
  Scenario Outline: Re issue X no of carepass MMF coupons 
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass
    Then Validate the promo mmf coupon issued to carepass user
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass
    Then Validate the promo mmf coupon issued to carepass user
    
    Examples:
      | scenario                              		| xtra_card_nbr |
      | "Re issue carepass x no of mmf coupons" 	| 99991234      |

  @Validate_issuance_2nd&3rd_month_coupons 
  Scenario Outline: Validate issuance of 2nd and 3rd month mmf coupons
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When I use Extra Card <xtra_card_nbr> to reissue <number> mmf carepass coupon
    Then Validate the promo mmf coupon issued to carepass user
    
    
    Examples:
      | scenario                                     | xtra_card_nbr |number|
      | "Validate 2nd month issuance of mmf coupons" | 99991234      |2			|
      | "Validate 3rd month issuance of mmf coupons" | 99991234      |3			|

@Enroll_Annual_carepass_MMF  
  Scenario Outline: Enroll CVS customer in Carepass MMF Annual program
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass annually
    Then The user enroled in to carepass annual successfully
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass annual
    Then Validate the promo mmf coupon issued to carepass user
    
    Examples:
      | scenario                              | xtra_card_nbr |
      | "Enroll carepass annual mmf program" 	| 99991234      |
      
 @Validate_issuuance_mmf_coupons_nontarget_users 
  Scenario Outline: Issue MMF coupons to non carepass users
    Given I am a CVS user with <xtra_card_nbr>
    When use Extra Card <xtra_card_nbr> to issue mmf coupons for non carepass users
    Then Validate the promo mmf coupon issued to non carepass user
    
    Examples:
      | scenario                                              | xtra_card_nbr |
      | "Validate issuuance of mmf coupons nontarget users" 	| 99991234      |

@Redeem_mmf_coupon_campaign 
  Scenario Outline: Redeem MMF coupons 
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass monthly
    Then Validate the promo mmf coupon issued to carepass user
    When I redeem mmf carepass coupon
    Then Validate the promo mmf coupon redeemed successfully
    
    Examples:
      | scenario                      | xtra_card_nbr |
      | "Redeem issued mmf coupons" 	| 99991234      |
      
@Validate_issuance_MMF_coupon_campaign_HOLD 
  Scenario Outline: Issue MMF coupons when campaign is on hold
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass monthly
    Then Validate the promo mmf coupon issued to carepass user
    When I use Extra Card <xtra_card_nbr> to reissue <number> mmf carepass coupon
    Then Validate the promo mmf coupon issued to carepass user
    When The user campaign should be kept on hold
    Then validate the user can not be issued new coupons
    Examples:
      | scenario                                                                    | xtra_card_nbr |
      | "Validate the issuance of MMF coupon if the MMF campaign is kept on HOLD" 	| 99991234      |
  
 @ReissueMmfCoupon_carepass_Xnoof_coupons 
  Scenario Outline: Re issue X no of carepass MMF coupons
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When use Extra Card <xtra_card_nbr> to issue mmf coupons with <ReissueMmfCoupon>
    Then Validate the promo mmf coupon issued to carepass user
    
    Examples:
      | scenario                              		| xtra_card_nbr |ReissueMmfCoupon|
      | "Re issue carepass x no of mmf coupons" 	| 99991230      |"false" 	|
  
  @Issue_MMF_noof_coupons_carepassmember_HOLD	@runThis 
  Scenario Outline: Issue MMF coupons to carepass member who is on hold
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass monthly
    Then Validate the promo mmf coupon issued to carepass user
    When The user carepass membership is kept on hold
    Then validate the user carepass membership is on hold
    When I use Extra Card <xtra_card_nbr> to reissue <number> mmf carepass coupon
    Then Validate the promo mmf coupon issued to carepass user
    
    Examples:
      | scenario                                                  											| xtra_card_nbr |
      | "Validate the issuance of MMF coupon when carepass membership is kept on HOLD" 	| 99991230      |
  
  @Validate_issuance_MMF_coupon_campaign_HOLD	@runThis
  Scenario Outline: Issue mmf coupon when campaign is on hold
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to mmf carepass monthly
    Then Validate the promo mmf coupon issued to carepass user
    When I use Extra Card <xtra_card_nbr> to reissue <number> mmf carepass coupon
    Then Validate the promo mmf coupon issued to carepass user
    When The user campaign should be kept on hold
    Then validate the user can not be issued new coupons
    Examples:
      | scenario                                                  | xtra_card_nbr |
      | "Validate the issuance of MMF coupon if the MMF campaign is kept on HOLD" 	| 99991230      |
   
   @Issue_Xnoof_nonmmf_coupons	@runThis 
  Scenario Outline: Issue X no of carepass MMF coupons
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When I use Extra Card <xtra_card_nbr> to issue non mmf coupons
    Then Validate the promo non mmf coupon issued to carepass user

    
    Examples:
      | scenario                              		| xtra_card_nbr |
      | "Issue carepass x no of non mmf coupons" 	| 99991230      |
      
  