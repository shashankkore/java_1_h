@CarePass
Feature: Enroll Customer in to carePass program
	
  @Enroll_Monthly	
  Scenario Outline: Enroll CVS customer in Carepass Monthly program
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    
    Examples:
      | xtra_card_nbr|
      | 9991234      |

  @Enroll_Annually	
  Scenario Outline: Enroll CVS customer in Carepass Annual program
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass annually
    Then The user enroled in to carepass annual subscription successfully
    
    Examples:
      | scenario                                                  | xtra_card_nbr |
      | "Enroll in to carepass program with annual subscription" 	| 99991230      |
      
  @Enroll_Monthly_Renewal	
  Scenario Outline: Renew CVS customer in Carepass Monthly program
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When I use Extra Card <xtra_card_nbr> to renew carepass monthly enrollment
    Then The user renewed in to carepass monthly successfully
    
    Examples:
      | scenario                              | xtra_card_nbr |
      | "Renew carepass monthly subscription" | 99991234      |

  @Enroll_Annually_Renewal	
  Scenario Outline: Renew CVS customer in Carepass Annual program
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass annually
    Then The user enroled in to carepass annual subscription successfully
    When I use Extra Card <xtra_card_nbr> to renew carepass annual enrollment
    Then The user renewed in to carepass annual successfully
    
    Examples:
      | scenario                              | xtra_card_nbr |
      | "Renew carepass annual subscription" 	| 99991230      |

  @Cancel_carepass_monthly_subscription	
  Scenario Outline: CVS customer cancels carepass monthly subscription
    Given I am a CVS user with <xtra_card_nbr>
    When use Extra Card <xtra_card_nbr> to unenroll in to monthly carepass
    Then The user unenrolled in to carepass monthly successfully
    
    Examples:
      | scenario                                                  | xtra_card_nbr |
      | "UnEnroll in to carepass program with monthly subscription" | 99991234      |

  @Cancel_carepass_annual_subscription	
  Scenario Outline: CVS customer cancels carepass annual subscription
    Given I am a CVS user with <xtra_card_nbr>
    When use Extra Card <xtra_card_nbr> to unenroll in to annual carepass
    Then The user unenrolled in to carepass annual successfully
    
    Examples:
      | scenario                                                  | xtra_card_nbr |
      | "UnEnroll in to carepass program with annual subscription" 	| 99991230      |

  @Enroll_Monthly_Reactivation	
  Scenario Outline: CVS customer reenroll in to carepass monthly program
    Given I am a CVS user with <xtra_card_nbr>
    When use Extra Card <xtra_card_nbr> to reenroll in to monthly carepass
    Then The user reenrolled in to carepass monthly successfully
    
    Examples:
      | scenario                              			| xtra_card_nbr |
      | "Reactivate carepass monthly subscription" 	| 99991234      |

  @Enroll_Annually_Reactivation	
  Scenario Outline: CVS customer reenroll in to carepass annual program
    Given I am a CVS user with <xtra_card_nbr>
    When use Extra Card <xtra_card_nbr> to reenroll in to annual carepass
    Then The user reenrolled in to carepass annual successfully
    
    Examples:
      | scenario                              			| xtra_card_nbr |
      | "Reactivate carepass annual subscription" 	| 99991230      |
      
  @Enroll_Monthly_onhold	
  Scenario Outline: CVS customer's carepass monthly subscription put on hold
    Given I am a CVS user with <xtra_card_nbr>
    And use Extra Card <xtra_card_nbr> to put carepass monthly membership on hold
    Then I validate the user carepass monthly membership is on hold
    
    Examples:
      | scenario                              			| xtra_card_nbr |
      | "Hold carepass monthly subscription" 	| 99991234      |

  @Enroll_Annually_onhold	
  Scenario Outline: CVS customer's carepass annual subscription put on hold
    Given I am a CVS user with <xtra_card_nbr>
    And use Extra Card <xtra_card_nbr> to put carepass annual membership on hold
    Then I validate the user carepass annual membership is on hold
    
    Examples:
      | scenario                              | xtra_card_nbr |
      | "Hold carepass annual subscription" 	| 99991230      |

	@ignore
  @UnEnroll_Annually_fromhold	
  Scenario Outline: CVS customer unenrolled carepass annual subscription which is on hold
    Given I am a CVS user with <xtra_card_nbr>
    And use Extra Card <xtra_card_nbr> to unenroll carepass membership
    Then I validate the user carepass annual membership is unenrolled
    
    Examples:
      | scenario                              										| xtra_card_nbr |
      | "Unenroll carepass annual subscription which is on hold" 	| 99991230      |

	@ignore
  @UnEnroll_Monthly_fromhold	
  Scenario Outline: CVS customer unenrolled carepass monthly subscription which is on hold
    Given I am a CVS user with <xtra_card_nbr>
    And use Extra Card <xtra_card_nbr> to unenroll carepass membership
    Then I validate the user carepass monthly membership is unenrolled
    
    Examples:
      | scenario                              		      						| xtra_card_nbr |
      | "Unenroll carepass monthly subscription which is on hold" 	| 99991234      |

	@ignore
  @UnEnroll_Annually	
  Scenario Outline: CVS customer unenrolled carepass monthly subscription
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass annually
    Then The user enroled in to carepass monthly successfully
    When use Extra Card <xtra_card_nbr> to unenroll carepass membership
    Then I validate the user carepass annual membership is unenrolled
    
    Examples:
      | scenario                              		| xtra_card_nbr |
      | "Unenroll carepass annual subscription" 	| 99991230      |

	@ignore
  @UnEnroll_Monthly	
  Scenario Outline: CVS customer unenrolled carepass annual subscription
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When use Extra Card <xtra_card_nbr> to unenroll carepass membership
    Then I validate the user carepass monthly membership is unenrolled
    
    Examples:
      | scenario                              		| xtra_card_nbr |
      | "Unenroll carepass monthly subscription" 	| 99991234      |
	
	@ignore
  @Monthly_to_Annual_switch	
  Scenario Outline: CVS customer switches monthly carepass membership to annual 
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When The user with <xtra_card_nbr> switch monthly membership to annual
    Then The user enrolled in annual membership successfully
    
    Examples:
      | scenario                              						| xtra_card_nbr |
      | "Swicth carepass monthly subscription to annual" 	| 99991234      |
  
  @ignore    
  @Unenroll_to_hold_annual	
  Scenario Outline: CVS customer carepass annual membership put on hold while unenrolling 
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass annually
    Then The user enroled in to carepass monthly successfully
    When I validate the user unenrolled in to carepass
    Then The user unenroled in to carepass monthly subscription successfully
    When The user put carepass subscription on hold
    Then I validate the user carepass annual membership is on hold
    
    Examples:
      | scenario                              															| xtra_card_nbr |
      | "Hold carepass annual subscription when subscripton is unenrolled" 	| 99991230      |	
	
	@ignore
  @Unenroll_to_hold_monthly	
  Scenario Outline: CVS customer carepass monthly membership put on hold while unenrolling
    Given I am a CVS user with <xtra_card_nbr>
    When I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then The user enroled in to carepass monthly successfully
    When I validate the user unenrolled in to carepass
    Then The user unenroled in to carepass monthly subscription successfully
    When The user put carepass subscription on hold
    Then I validate the user carepass monthly membership is on hold
    
    Examples:
      | scenario                              																| xtra_card_nbr |
      | "Hold carepass monthly subscription when subscripton is unenrolled" 	| 99991234      |	

	@ignore
  @Card_Not_OnFile	
  Scenario Outline: Validate error message when xtra card is not a CVS customer
    Given I am a CVS user with <xtra_card_nbr>
    And use Extra Card <xtra_card_nbr> to enroll in to carepass
    Then I validate the <error message>
    
    Examples:
      | scenario                              		| xtra_card_nbr |error message					|
      | "Unenroll carepass monthly subscription" 	| 99990000      |"Card Not on File"|
	
	@ignore
  @Error_validations	
  Scenario Outline: View Customer CarePass Details in DashBoard
    Given I am a CVS user with <xtra_card_nbr>
    And I use Extra Card <xtra_card_nbr> to enroll in to carepass monthly
    Then I validate the <error message>
    
    Examples:
      | scenario                    | xtra_card_nbr |error message			|
      | "Validate error message " 	| 999999999999999      |"Card not on file"	|
      | "Validate error message " 	| XXXXXXXX      |"HOT XC Card"			|
      | "Validate error message " 	| 12345      		|"Invalid Message source code."			|
      | "Validate error message " 	| XXXXXXXX      |"Invalid carepass request, tables should be present."			|
      | "Validate error message " 	| 99990000      |"Mandatory fields missing"			|
      | "Validate error message " 	| XXXXXXXX      |"Status mismatch (Has curStatus attribute)"			|
      | "Validate error message " 	| XXXXXXXX      |"Customer is not enrolled into carepass"			|
      | "Validate error message " 	| XXXXXXXX      |"Invalid Channel [%s] for table [%s]"			|
      | "Validate error message " 	| XXXXXXXX      |"ERROR: Invalid table, Invlaid operation on:"			|
