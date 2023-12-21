@DigitalReceipts
Feature: Enroll Customer in to DigitalReceipts program
	
  @DigitalReceipts_savings	
  Scenario Outline: Verify customer who is enrolled in carepass, beauty club and PHR saved on shopping trip with digital receipts
    Given I am a CVS user with <xtra_card_nbr>
    And The customer is enrollend in Carepass <CarePass_Enroll>
    And The customer is enrollend in Beauty Club <BC_Enroll>
    And The customer is enrollend in PHR <PHR_Enroll>
    When The customer make the transaction with digital receipts
    Then The user verify the transaction is completed successfully
    When The customer access the digital receipts
    Then The user verify the saving with digital receipts
    
    Examples:
      | scenario                                                  																					| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991234      	|Yes						|Yes			|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|Yes						|No				|No					|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|Yes						|No				|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|Yes						|Yes			|No					|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|Yes			|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|Yes			|No					|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|No				|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|No				|No					|			

	@DigitalReceipts_no_savings	
  Scenario Outline: Verify customer who is enrolled in carepass, beauty club and PHR not saved on shopping trip with digital receipts
    Given I am a CVS user with <xtra_card_nbr>
    And The customer is enrollend in Carepass <CarePass_Enroll>
    And The customer is enrollend in Beauty Club <BC_Enroll>
    And The customer is enrollend in PHR <PHR_Enroll>
    When The customer make the transaction with digital receipts
    Then The user verify the transaction is completed successfully
    When The customer access the digital receipts
    Then The user verified no savings with digital receipts
    
    Examples:
      | scenario                                                  																					| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991234      	|Yes						|Yes			|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|Yes						|No				|No					|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|Yes						|No				|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|Yes						|Yes			|No					|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|Yes			|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|Yes			|No					|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|No				|Yes				|
      | "Verify customer who is enrolled/not enrolled in carepass, beauty club and PHR saved on shopping" 	| 99991235      	|No							|No				|No					|			
	
	@DigitalReceipts_duplicate_transaction	
  Scenario Outline: Verify customer get duplicate exception when the transaction occured 
    Given I am a CVS user with <xtra_card_nbr>
    And The customer is enrollend in Carepass <CarePass_Enroll>
    And The customer is enrollend in Beauty Club <BC_Enroll>
    And The customer is enrollend in PHR <PHR_Enroll>
    When The customer make the transaction with digital receipts
    Then The customer received <error_message>
    
    Examples:
      | scenario                                                  							| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|error_message|
      | "Verify customer get duplicate exception when the transaction occured" 	| 99991234      	|Yes						|Yes			|Yes				|"Duplicate"	|
  
  @DigitalReceipts_server_exception	
  Scenario Outline: Verify customer get server exception when the transaction occured 
    Given I am a CVS user with <xtra_card_nbr>
    And The customer is enrollend in Carepass <CarePass_Enroll>
    And The customer is enrollend in Beauty Club <BC_Enroll>
    And The customer is enrollend in PHR <PHR_Enroll>
    When The customer make the transaction with digital receipts
    Then The customer received <error_message>
    
    Examples:
      | scenario                                                  							| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|error_message|
      | "Verify customer get server exception when the transaction occured" 		| 99991234      	|Yes						|Yes			|Yes				|"Server Exception Occured."	|
  
  
  @DigitalReceipts_Ambiguous_receipt_error	
  Scenario Outline: Verify customer get Ambiguous token exception when the customer retrieves the receipt 
    Given I am a CVS user with <xtra_card_nbr>
    And The customer is enrollend in Carepass <CarePass_Enroll>
    And The customer is enrollend in Beauty Club <BC_Enroll>
    And The customer is enrollend in PHR <PHR_Enroll>
    When The customer make the transaction with digital receipts
    Then The user verify the transaction is completed successfully
    When The customer access the digital receipts
    Then The customer received <error_message>
    
    Examples:
      | scenario                                                  							| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|error_message|
      | "Verify customer get Ambiguous token exception when the customer retrieves the receipt" 	| 99991234      	|Yes						|Yes			|Yes				|"Ambiguous token"	|
  
	@DigitalReceipts_server_exception_with_receipts	
  Scenario Outline: Verify customer get server exception when the receipt pulled 
    Given I am a CVS user with <xtra_card_nbr>
    And The customer is enrollend in Carepass <CarePass_Enroll>
    And The customer is enrollend in Beauty Club <BC_Enroll>
    And The customer is enrollend in PHR <PHR_Enroll>
    When The customer make the transaction with digital receipts
    Then The user verify the transaction is completed successfully
    When The customer access the digital receipts
    Then The customer received <error_message>
    
    Examples:
      | scenario                                                  				| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|error_message|
      | "Verify customer get server exception when the receipt pulled" 		| 99991234      	|Yes						|Yes			|Yes				|"Server Exception Occured."	|
 
 
	@Digital_Receipts_Savings_Equaivalent_to_purchaseAmount
	Scenario Outline: Digital Receipts for Xtra Card where Customer with Savings Equaivalent to the purchase Amount in the shopping trip and is enrolled into Carepass, Beauty Club and PHR
		Given I am a CVS user with <xtra_card_nbr>
	  And The customer is enrollend in Carepass <CarePass_Enroll>
	  And The customer is enrollend in Beauty Club <BC_Enroll>
	  And The customer is enrollend in PHR <PHR_Enroll>
	  When The customer saved with digital receipts
	  Then The user has savings equaivalent to the purchase amount with digital receipts
	 	Examples:
	    | scenario                                                  																																																								| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|
	    | "Digital Receipts for Xtra Card where Customer with Savings Equaivalent to the purchase Amount in the shopping trip and is enrolled into Carepass, Beauty Club and PHR" 	| 99991234      	|Yes						|Yes			|Yes				|
	  
	@Digital_Receipts_Savings_FreeItem_in_theshoppingtrip
	Scenario Outline: Digital Receipts for Xtra Card where Customer with Free Item in the shopping trip and is enrolled into Carepass, Beauty Club and PHR
		Given I am a CVS user with <xtra_card_nbr>
	  And The customer is enrollend in Carepass <CarePass_Enroll>
	  And The customer is enrollend in Beauty Club <BC_Enroll>
	  And The customer is enrollend in PHR <PHR_Enroll>
	  When The customer saved with digital receipts
	  Then The user has a free item with digital receipts
	 	Examples:
	    | scenario                                                  																																							| xtra_card_nbr 	|CarePass_Enroll|BC_Enroll|PHR_Enroll	|
	    | "Digital Receipts for Xtra Card where Customer with Free Item in the shopping trip and is enrolled into Carepass, Beauty Club and PHR" 	| 99991234      	|Yes						|Yes			|Yes				|
	  