@RHB
Feature: Reward Health Benefits for CVS users

  @RHB
  @Create_RHB_Onboard_Coupon	
  Scenario Outline: Create RHB device onboarding coupon
    Given I use my Extra Card <xtra_card_nbr> and <xtra_card_type>
    When I use Extra Card for given <program_type> and <coupon_event_type> to create RHB onboard coupon
    Then The RHB device onboarding coupon created successfully
    
    Examples:
      | xtra_card_nbr 	|xtra_card_type	|program_type |coupon_event_type|
      | "102"      			|"0002"					|"RHB"				|"DO"							|

  @RHB
  @Create_RHB_Steps_Coupon	
  Scenario Outline: Create RHB device steps coupon
    Given I use my Extra Card <xtra_card_nbr> and <xtra_card_type>
    When I use Extra Card for given <program_type> and <coupon_event_type> to create RHB onboard coupon
    Then The RHB device onboarding coupon created successfully
    
    Examples:
      | xtra_card_nbr 	|xtra_card_type	|program_type |coupon_event_type|
      | "103"      			|"0002"					|"RHB"				|"S"							|
 
 	@RHB
  @Invalid_program_type_code	
  Scenario Outline: Validate error message when xtra card is not a CVS customer
    Given I use my Extra Card <xtra_card_nbr> and <xtra_card_type>
    When I use Extra Card for given <program_type> and <coupon_event_type> to create RHB onboard coupon
    Then I validate the <error message>
    
    Examples:
      | scenario                              						| xtra_card_nbr |xtra_card_type	|program_type	|coupon_event_type |error message					  |
      | "Create RHB coupon with Invalid program type code"| "102"      		|"0002"					|"RH"					|"S"							 |"Invalid program type"	|
    
  @RHB
  @Invalid_event_type_code	
  Scenario Outline: Validate error message when xtra card is not a CVS customer
    Given I use my Extra Card <xtra_card_nbr> and <xtra_card_type>
    When I use Extra Card for given <program_type> and <coupon_event_type> to create RHB onboard coupon
    Then I validate the <error message>
    
    Examples:
      | scenario                              						| xtra_card_nbr |xtra_card_type	|program_type		|coupon_event_type |error message					  		|
      | "Create RHB coupon with Invalid coupon event type"| "102"      		|"0002"					|"RHB"					|"SP"							 |"Invalid coupon event type"	|
      
  @RHB
  @Invalid_XC_card	
  Scenario Outline: Validate error message when xtra card is not a CVS customer
    Given I use my Extra Card <xtra_card_nbr> and <xtra_card_type>
    When I use Extra Card for given <program_type> and <coupon_event_type> to create RHB onboard coupon
    Then I validate the <error message>
    
    Examples:
      | scenario                              						| xtra_card_nbr |xtra_card_type	|program_type		|coupon_event_type |error message			|
      | "Create RHB coupon with Invalid xtra card number"	| "ABC"      		|"0002"					|"RHB"					|"S"							 |"Invalid XC Card"	|
      | "Create RHB coupon with Invalid xtra card number"	| "ABC"      		|"0002"					|"RHB"					|"DO"							 |"Invalid XC Card"	|
  
  @RHB
  @Invalid_XC_card_type	
  Scenario Outline: Validate error message when xtra card is not a CVS customer
    Given I use my Extra Card <xtra_card_nbr> and <xtra_card_type>
    When I use Extra Card for given <program_type> and <coupon_event_type> to create RHB onboard coupon
    Then I validate the <error message>
    
    Examples:
      | scenario                              						| xtra_card_nbr |xtra_card_type	|program_type		|coupon_event_type |error message														|
      | "Create RHB coupon with Invalid xtra card type"		| "102"      		|"ABCD"					|"RHB"					|"S"							 |"Invalid XC Card Type Search Criteria"	|
      | "Create RHB coupon with Invalid xtra card type"		| "102"      		|"ABCD"					|"RHB"					|"DO"							 |"Invalid XC Card Type Search Criteria"	|
      
  @RHB @ignore
  @CreateandVerify_RHB_Onboard_Coupon	
  Scenario Outline: Create RHB device onboarding coupon
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> and <xtra_card_type> to create RHB <type> coupon
    Then The RHB device onboarding coupon created successfully
    When I retrive RHB <type> coupon 
    Then I validate RHB <type> coupon successfully 
    
    Examples:
      | scenario                              						| xtra_card_nbr |xtra_card_type	|type	|
      | "Create RHB device onboard coupon and verify" 		| 103      			|0002						|"DO"	|
      | "Create RHB device onboard coupon and verify" 		| 104      			|0002						|"S"	|
      