@Phrgetcustnode @getcust
Feature: PHR details in GetCust response

  @Phrgetcust
  Scenario Outline: PHR details in GetCust response for regular PHR
    Given I am a CVS user with <id_number> for getcust with message source code "<message_source_code>"
    When I execute getcust service
    Then Check getcust response
    And Validate the phr enrollment status as "<phrenrollmentstatus>" in the getcust response body
    And Validate the campaign id as <cmpgnId> in the getcust response body
    And Validate the encoded Xtra card number "<encoded_xtra_card_nbr>" in the getcust response body
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|id_number	|encoded_xtra_card_nbr	|phrenrollmentstatus	|cmpgnId		|
      | "PHR details in GetCust responsefor regular phr"	 			| 298344808     |PD										|87654321		|4879983448088					|Y										|44496			|

	@Phrgetcust
  Scenario Outline: PHR details in GetCust response wrong source codes
    Given I am a CVS user with <id_number> for getcust with message source code "<message_source_code>"
    When I execute getcust service
    Then Check getcust error response
    And Validate getcust error code as 12
    And Validate getcust error message as "Unallowed source for lookup of HR Enroll Status"
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|id_number	|encoded_xtra_card_nbr	|phrenrollmentstatus	|cmpgnId		|
      | "PHR details in GetCust response wrong source codes"		| 298344808     |MOBILE							  |87654321		|4879983448088					|Y										|44496			|
      | "PHR details in GetCust response wrong source codes"		| 298344808     |WEB									|87654321		|4879983448088					|Y										|44496			|
      | "PHR details in GetCust response wrong source codes"		| 298344808     |STORE								|87654321		|4879983448088					|Y										|44496			|

	  @Phrgetcust
  Scenario Outline: PHR details in GetCust response for regular PHR with HR events
    Given I am a CVS user with <id_number> for getcust with message source code "<message_source_code>"
    When I execute getcust service
    Then Check getcust response
    And Validate the phr enrollment status as "<phrenrollmentstatus>" in the getcust response body
    And Validate the encoded Xtra card number "<encoded_xtra_card_nbr>" in the getcust response body
    And Validate the ephLinkId under HR Event details <id_number> as in the getcust response body
    And Validate the idNbr under HR Event details <id_number> as in the getcust response body
    
    Examples:
      | scenario                    																						| xtra_card_nbr |message_source_code	|id_number	|encoded_xtra_card_nbr	|phrenrollmentstatus	|cmpgnId		|
      | "PHR details in GetCust response for regular PHR with HR events"	 			| 298344419     |PD										|654321			|4879983444196					|Y										|44496			|
      
      @Phrgetcust
  Scenario Outline: PHR details in GetCust response for unenrolled patients
    Given I am a CVS user with <id_number> for getcust with message source code "<message_source_code>"
    When I execute getcust service
    Then Check getcust error response
    And Validate getcust error code as 4
    And Validate getcust error message as "Invalid XC Card"
    
    Examples:
      | scenario                    																		| xtra_card_nbr |message_source_code	|id_number	|encoded_xtra_card_nbr	|phrenrollmentstatus	|cmpgnId		|
      | "PHR details in GetCust response for unenrolled patients"	 			| 298344053     |PD										|123456			|4879983440532					|Y										|44496			|