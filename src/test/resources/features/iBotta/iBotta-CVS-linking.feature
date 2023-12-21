#Author: madhu.mohan@cvshealth.com

@IBotta
Feature: Ibotta Integration: Account Look-up & Verification
	@Scenarios_to_be_added_to_validate_with_other_parameters
	
  @Valid_Extra_Card_Number_200
  @Acceptance
  Scenario Outline: Integrate iBotta user to CVS with a valid Extra Card number
    Given I am a iBotta user with <scenario>
    When I use my <Extra Card Number> to link with CVS loyalty account 
    Then I found the customer profile with CVS loyalty account
  	Examples:
  	|scenario								|Extra Card Number|
  	|"valid EC Card Number"	|"4872000175738"	|
  	|"valid 15 digit EC Card Number"	|"800000001670027"|
  
  @Valid_PhoneandEmail_200
  @Acceptance
  Scenario Outline: Integrate iBotta user to CVS with a valid Phone and Email
    Given I am a iBotta user with <scenario>
    When I use my <Phone Number> and <Email> to link with CVS loyalty account  
    Then I found the customer profile with CVS loyalty account
  	Examples:
  	|scenario								|Phone Number				|Email										|
  	|"home phone and email"	|"4017718573"				|"emailcvs17573@gmail.com"|
  
  @Valid_Extra_Card_Number 
  Scenario Outline: Integrate iBotta user to CVS with a valid Extra Card number
    Given I am a iBotta user with <scenario>
    When I use my <Extra Card Number> to link with CVS loyalty account 
    Then I found the customer profile with CVS loyalty account
    When CVS sends an email to the customer with the link
    And The customer access the email link
    Then The customer linked to IBotta successfully
  	Examples:
  	|scenario								|Extra Card Number	|
  	|"valid EC Card Number"	|"4872000175738"						|
  
  
  @Invalid_Extra_Card_Number
  @Acceptance 
  Scenario Outline: Integrate iBotta user to CVS with a invalid Extra Card number
    Given I am a iBotta user with <scenario>
    When I use my <Extra Card Number> to link with CVS loyalty account  
    Then I do not found the customer profile with CVS
    And I validate the <error message>
    And CVS sends <error message2> to iBotta
    
  	Examples:
  	|scenario									|Extra Card Number|error message										|error message2										|
  	|"Exact Match Not Found"	|"487200017573"		|"Exact customer match not found"	|"Exact customer match not found"	|

	@Valid_PhoneandEmail 
  Scenario Outline: Integrate iBotta user to CVS with a valid Phone and Email
    Given I am a iBotta user with <scenario>
    When I use my <Phone Number> and <Email> to link with CVS loyalty account  
    Then I found the customer profile with CVS loyalty account
    When CVS sends an email to the customer with the link
    And The customer access the email link
    Then The customer linked to IBotta successfully
  	Examples:
  	|scenario													|Phone Number				|Email|
  	|"Valid phone and email"					|"4017718573"						|"emailcvs17573@gmail.com"|
  	|"home phone and email"						|"4017718573"						|""|
  	|"secondary phone and email"			|""						|"emailcvs17573@gmail.com"|
  	
  @Invalid_PhoneandEmail
  @Acceptance 
  Scenario Outline: Integrate iBotta user to CVS with a invalid Phone and email combinations
    Given I am a iBotta user with <scenario>
    When I use my <Phone Number> and <Email> to link with CVS loyalty account  
    Then I do not found the customer profile with CVS
    And I validate the <error message>
    And CVS sends <error message2> to iBotta
  Examples:
  	|scenario														|Phone Number	|Email																|error message										|error message2|
  	|"phone num not found"							|"4011316522"	|""																		|"Exact customer match not found"	|"Exact customer match not found"	|
  	|"invalid phone number provided"		|"1234"				|"invalidemail@CVSCaremarkHealth.com"	|"Invalid Sys Params. Invalid Phone Number"									|"Invalid Sys Params. Invalid Phone Number"									|
  	|"phone number and email not found"	|"4011316522"	|"invalidemail@CVSCaremarkHealth.com"	|"Exact customer match not found"	|"Exact customer match not found"	|
  	|"phone num not provided"						|""						|"invalidemail@CVSCaremarkHealth.com"	|"Exact customer match not found"	|"Exact customer match not found"	|
  	
  
  @Invalid_PhoneandEmail
  @Acceptance @ignore
  Scenario Outline: Integrate iBotta user to CVS with a invalid Phone and email combinations
    Given I am a iBotta user with <scenario>
    When I use my <Phone Number> and <Email> to link with CVS loyalty account  
    Then I do not found the customer profile with CVS
    And I validate the <error message>
    And CVS sends <error message2> to iBotta
  Examples:
  	|scenario														|Phone Number	|Email																|error message										|error message2|
 		|"phone with multiple customers"		|"4017703957"	|""																		|"Multiple customer matches found"|"Multiple customer matches found"	|
  
  @Valid_EC_E2E 
  Scenario Outline: Integrate iBotta user to CVS with a valid Extra Card number and customer verfication is successful
    Given I am a iBotta user with <scenario>
    When I use my <Extra Card Number> to link with CVS loyalty account  
    Then I found the customer profile with CVS loyalty account
    When CVS sends an email to the customer with the link
    And The customer access the email link
    Then The customer linked to IBotta successfully
  Examples:
  	|scenario																							|Extra Card Number|
  	|"valid EC Card num to send an email to the customer" |"4872000175738"	|
  
  @Email_link_expired_with_ECCard
  Scenario Outline: Integrate iBotta user to CVS with a valid Extra Card number and customer attempted verification after link is expired
    Given I am a iBotta user with <scenario>
    When I use my <Extra Card Number> to link with CVS loyalty account  
    Then I found the customer profile with CVS loyalty account
    When CVS sends an email to the customer with the link
    And The customer access the email link
    Then The customer received <error message> 
  Examples:
  	|scenario													|Extra Card Number|error message|
  	|"expired email link"							|"4872000175738"						|"Email Link has expired"|
  
  @Invalid_email_link_with_PhoneandEmail 
  Scenario Outline: Integrate iBotta user to CVS with a valid Phone & Email and customer attempted verification after link is expired
    Given I am a iBotta user with <scenario>
    When I use my <Phone Number> and <Email> to link with CVS loyalty account  
    Then I found the customer profile with CVS loyalty account
    When CVS sends an email to the customer with the link
		And The customer access the email link
    Then The customer received <error message>
  Examples:
  	|scenario													|Phone Number			|Email										|error message|
  	|"expired email link"							|"4017718573"			|"emailcvs17573@gmail.com"|"Email Link has expired"    |

  @Data_setup
  Scenario Outline: Create a customer data with valid extra card
  	Given I have customer details
  	When I create a customer with data file "11customer_onerecord_each"
  