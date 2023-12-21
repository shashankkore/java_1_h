@Optical
Feature: Optical - Validate Customer Single Sign On
  

@Valid_User_Credentials_Auth_Token @runThis
  Scenario Outline: Validate Customer with valid client credentials to receive Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    Then I receive a token

    Examples: 
      | 	scenario  			|		user_id			   															| Password   														|	
      | "I am an Ocuco user"|   "9b4540e8-aeea-4354-baaa-e128aef72836"		   |  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	| 
      | "I am an Acuitas user"|   "9b4540e8-aeea-4354-baaa-e128aef72836"		   |  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	| 
      

      
 @Invalid_User_Credentials_Auth_Token    
  Scenario Outline: Validate Customer with Invalid User Credentials
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password> 
    When I fetch my auth token
    Then I see unauthorized error_code as <error_code>

    Examples: 
      | scenario  				|		user_id		  													 		|  Password  														  | error_code  |
      | "I am an Ocuco user"|   "9b4540e8-aeea-4354-baaa-e128aef72837"  |  "0e5e7618-fbdb-4de5-9d24-e2defd71effd" |    401		  |
      | "I am Acuitas user" |   "9b4540e8-aeea-4354-baaa-e128aef72837"  |  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	| 	 401		  |
      | "I am an Ocuco user"|   "9b4540e8-aeea-4354-baaa-e128aef72836"	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effe"	|    401		  |
      | "I am Acuitas user" |   "9b4540e8-aeea-4354-baaa-e128aef72836"	|	"0e5e7618-fbdb-4de5-9d24-e2defd71effe"	| 	 401	  	|
      | "I am an Ocuco user"|   "9b4540e8-aeea-4354-baaa-e128aef72837" 	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effe"	|    401		  |
      | "I am Acuitas user" |   "9b4540e8-aeea-4354-baaa-e128aef72837" 	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effe"	| 	 401	  	|
      
     
 @Valid_Extra_Card_Number
     Scenario Outline: Validate Customer with valid client credentials to receive Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my Extra Card number as <Extra_Card> for <user>
    Then I find a matching profile
    And I receive encoded XtraCardNbr as <encoded_xtra_cardnbr>

    Examples: 
      | 	scenario  				|		user_id			   																| Password   															|Extra_Card 	|user|encoded_xtra_cardnbr	|
      | "I am an Ocuco user"|   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4872000175738"|"Ocuco"|"4872000175738"		|
      | "I am Acuitas user" |   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4872000175738"|"Acuitas"|"4872000175738"	 	|
      | "I am an Ocuco user 12 Digit Extra Card Number"|   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"912000166993"|"Ocuco"|"912000166993"		|
      | "I am Acuitas user 12 Digit Extra Card Number" |   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"912000166993"|"Acuitas"|"912000166993"	 	|
      | "I am an Ocuco user 15 Digit Extra Card Number"|   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"800000001670027"|"Ocuco"|"800000001670027"|
      | "I am Acuitas user 15 Digit Extra Card Number" |   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"800000001670027"|"Acuitas"|"800000001670027"|
      
 @Invalid_Extra_Card_Number
     Scenario Outline: Validate Customer with invalid extra card details
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my Extra Card number as <Extra_Card> for <user>
    Then I dont find a matching profile
    And I see an error_code as <error_code>

    Examples: 
      | 	scenario  				|		user_id			   																| Password   															| 		Extra_Card 		|user			|	error_code|
      | "I am an Ocuco user"|   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4872000175733"		|"Ocuco"	|400			|
      | "I am Acuitas user" |   "9b4540e8-aeea-4354-baaa-e128aef72836"		   	|  "0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4872000175733"		|"Acuitas"|400	 		|
      
 @Valid_Auth_Token_With_Phone_Number     
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my Phone_number as <Phone_number> for <user>
    Then I find a matching profile
    And I receive encoded XtraCardNbr as <encoded_xtra_cardnbr>

    Examples: 
      | scenario  					|user_id			   												|Password   														|Phone_number 	|user			|encoded_xtra_cardnbr   |
      | "I am an Ocuco user"|"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4017718573"		|"Ocuco"	|"4872000175738"				|
      | "I am Acuitas user" |"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4017718573"		|"Acuitas"|"4872000175738"				|
      
  @Valid_Auth_Token_With_Invalid_Phone_Number     
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my Phone_number as <Phone_number> for <user>
    Then I dont find a matching profile
    And I see an error_code as <error_code>

    Examples: 
    	| scenario  					|user_id			   												|Password   														|Phone_number 	|user			|error_code   |
      | "I am an Ocuco user"|"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4017718579"		|"Ocuco"	|400				|
      | "I am Acuitas user" |"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"4017718579"		|"Acuitas"|400				|
      
@Invalid_Auth_Token_With_diff_inputs    
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
    When I use invalid AuthToken
    And I use my <input> as <inputvalue> for <user>
    Then I do get unauthorized error
    And I see an error_code as <error_code>

    Examples: 
      | scenario  					|	input 				|inputvalue 							|user			|error_code	|	
      | "I am an Ocuco user"|"Phone_number" |"4017718573"	 						|"Ocuco"	| 401				|
      | "I am Acuitas user" |"Email_Address"|"emailcvs17573@gmail.com"|"Acuitas"|	401				|
      | "I am Acuitas user" |"Extra_Card"		|"912000166993"			 			|"Acuitas"|	401				|
      
      
 @Valid_Auth_Token_With_Email_Address    
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my email as <Email_Address> for <user>
    Then I find a matching profile
    And I receive encoded XtraCardNbr as <encoded_xtra_cardnbr>

    Examples: 
    	| scenario  					|user_id			   												|Password   														|Email_Address 	|user			|encoded_xtra_cardnbr   |
      | "I am an Ocuco user"|"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"emailcvs17573@gmail.com"		|"Ocuco"	|"4872000175738"				|
      | "I am Acuitas user" |"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"emailcvs17573@gmail.com"		|"Acuitas"|"4872000175738"				|
     
@Auth_Token_With_Invalid_Email_Address   
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my email as <Email_Address> for <user> 
    Then I dont find a matching profile
    And I see an error_code as <error_code>

    Examples: 
    	| scenario  					|user_id			   												|Password   														|Email_Address 							|user			|error_code   |
      | "I am an Ocuco user"|"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"invalidemailcvs@gmail.com"|"Ocuco"	|400				|
      | "I am Acuitas user" |"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"invalidemailcvs@gmail.com"|"Acuitas"|400				|
     
 @Valid_Auth_Token_With_Phone_Number_And_Email_Address    
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
    And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my <Email_Address> and <Phone_number> for <user> 
    Then I find a matching profile
    And I receive encoded XtraCardNbr as <encoded_xtra_cardnbr>

    Examples: 
    | scenario  					|user_id			   												|Password   														|Email_Address						|Phone_number |user			|encoded_xtra_cardnbr |
    | "I am an Ocuco user"|"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"emailcvs17573@gmail.com"|"4017718573"	|"Ocuco"	|"4872000175738"			|
    | "I am Acuitas user" |"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"emailcvs17573@gmail.com"|"4017718573"	|"Acuitas"|"4872000175738"			|
      
@Invalid_Auth_Token_With_Phone_Number_And_Email_Address   
 Scenario Outline: Get Customer Details using Auth Token
    Given <scenario>
     And I use my user_id as <user_id> and Password as <Password>
    When I fetch my auth token
    And I use my <Email_Address> and <Phone_number> for <user>
    Then I dont find a matching profile
    And I see an error_code as <error_code>

    Examples: 
    | scenario  					|user_id			   												|Password   														|Email_Address						|Phone_number |user			|error_code |
    | "I am an Ocuco user"|"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"emailcvs17573@gmail.com"|"4017718579"	|"Ocuco"	|400				|
    | "I am Acuitas user" |"9b4540e8-aeea-4354-baaa-e128aef72836"	|"0e5e7618-fbdb-4de5-9d24-e2defd71effd"	|"invalidemailcvs@gmail.com"|"4017718573"	|"Acuitas"|400			|
      