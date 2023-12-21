@HREvent
Feature: Create HR Events

  @HREvent
  @HREventTypecode'CF'
  Scenario Outline: Enroll CVS customer in phr program
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set EPHID "<ephid>"
    And Set id number "<id_number>"
    And Set id type "<id_type>"
    And Set event type code "<event_type_code>"
    And Set event earn code "<event_earn_code>"
    And Set event earn reason code "<event_earn_reason_code>"
    When I create HR event
    Then The HR event is successfully created
    
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|enroll_action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|ephid	|id_type	|event_type_code	|event_earn_code	|event_earn_reason_code|
      | "Validate HR Event for event type code CF"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CF								|2								|12										 |
      | "Validate HR Event for event type code CS"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CS								|2								|12										 |
      | "Validate HR Event for event type code CM"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CM								|2								|12										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|2								|12										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|1								|12										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|3								|12										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|4								|12										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|2								|1										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|2								|2										 |
      | "Validate HR Event for event type code CR"						 	| 298344419     |WEB									|E									|654321			|0004				|4879983444196					|654321 |R				|CR								|2								|11										 |
      | "Validate HR Event for event type code CF"						 	| 298344419     |MOBILE								|E									|654321			|0004				|4879983444196					|654321 |R				|CF								|2								|12										 |
      | "Validate HR Event for event type code RD"						 	| 298344419     |STORE								|E									|654321			|0004				|4879983444196					|654321 |R				|RD								|2								|12										 |
      
  @HREvent
  @Error_validations
  Scenario Outline: Create HREvent - Error_validations 
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set EPHID "<ephid>"
    And Set id number "<id_number>"
    And Set id type "<id_type>"
    And Set event type code "<event_type_code>"
    And Set event earn code "<event_earn_code>"
    And Set event earn reason code "<event_earn_reason_code>"
    When I create HR event
    Then I validate the error code
    And I validate the error message for blank EPHID or IDnbr "<error_message>"
    
    Examples:
      | scenario                    													| xtra_card_nbr |message_source_code	|id_number	|ephid |id_type	|event_type_code	|event_earn_code	|event_earn_reason_code|error_message											|
      | "Validate error message for blank EPHID" 							| 298344419     |WEB	  							|654321			|			 |R				|CF								|2								|12										 |ephId cannot be null or empty			|
      | "Validate error message for blank IDnbr"							| 298344419     |WEB  								|     			|654321|R				|CF								|2								|12										 |idNbr cannot be null or empty			|
      | "Validate error message for blank EPHID" 							| 298344419     |WEB	  							|654321			|			 |R				|CR								|2								|12										 |ephId cannot be null or empty			|
      | "Validate error message for blank EPHID" 							| 298344419     |WEB	  							|654321			|			 |R				|CS								|2								|12										 |ephId cannot be null or empty			|
      | "Validate error message for blank EPHID" 							| 298344419     |WEB	  							|654321			|			 |R				|CM								|2								|12										 |ephId cannot be null or empty			|
      
  @HREvent
  @Error_validations
  Scenario Outline: Create HREvent - Error_validations 
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set EPHID "<ephid>"
    And Set id number "<id_number>"
    And Set id type "<id_type>"
    And Set event type code "<event_type_code>"
    And Set event earn code "<event_earn_code>"
    And Set event earn reason code "<event_earn_reason_code>"
    When I create HR event
    Then I validate the error code
    And I validate the error message "<error_message>"
    And I validate the error message for blank id type or event type code "<error_message>"
    
    Examples:
      | scenario                    													| xtra_card_nbr |message_source_code	|id_number	|ephid |id_type	|event_type_code	|event_earn_code	|event_earn_reason_code|error_message											|
      | "Validate error message for blank ID type" 					  | 298344419     |WEB								  |654321			|654321|				|CF								|2								|12										 |INVALID_ID_TYPE										|
      | "Validate error message for blank event type code"		| 298344419     |WEB							  	|654321 		|654321|R				| 								|2								|12										 |INV SYS PARMS											|
      | "Validate error message for wrong source code"				| 298344419     |STORE						  	|654321 		|654321|R				|CF								|2								|12										 |INV SYS PARMS											|
      | "Validate error message for blank ID type" 					  | 298344419     |WEB								  |654321			|654321|				|CR								|2								|12										 |INVALID_ID_TYPE										|
      | "Validate error message for blank ID type" 					  | 298344419     |WEB								  |654321			|654321|				|CS								|2								|12										 |INVALID_ID_TYPE										|
      | "Validate error message for blank ID type" 					  | 298344419     |WEB								  |654321			|654321|				|CM								|2								|12										 |INVALID_ID_TYPE										|
      