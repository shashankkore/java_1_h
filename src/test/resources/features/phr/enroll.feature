@Phrenrollment
Feature: Enroll Customer in to phr program

  @Phrenrollment
  @Phrenroll
  Scenario Outline: Enroll CVS customer in phr program
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set action code "<enroll_action_code>"
    And Set patient ID "<id_number>"
    And Set card type "<card_type>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to enroll in to Phr program
    Then The user enrolled in to Phr successfully
    And Validate the <xtra_card_nbr> in the response body
    Then Validate the phr enrolled status as "<getcust_status>" for <xtra_card_nbr> in getcust response
    And Set action code "<unenroll_action_code>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to unenroll in to Phr program
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|enroll_action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|getcust_status	|unenroll_action_code	|
      | "Validate phr enroll with long card"									 	| 298344053     |STORE								|E									|123456			|0004				|4879983440532					|Y							|U										|
      | "Validate phr enroll with short card" 									| 298344053     |STORE								|E									|123456			|0002				|4879983440532					|Y							|U										|
      | "Validate phr enroll for additional patient"						| 298344053     |STORE								|E									|123457			|0004				|4879983440532					|Y							|U										|
      | "Validate phr enroll with long card - Web"						 	| 298344053     |WEB									|E									|123456			|0004				|4879983440532					|Y							|U										|
      | "Validate phr enroll with long card - Mobile"						| 298344053     |MOBILE								|E									|123456			|0004				|4879983440532					|Y							|U										|
      
      
  @Phrenrollment
  @Error_validations
  Scenario Outline: Enroll CVS customer in phr program - Error_validations
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set action code "<action_code>"
    And Set patient ID "<id_number>"
    And Set card type "<card_type>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to enroll in to Phr program
    Then I validate the error code
    And I validate the error message "<error_message>"
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|error_message																							|
      | "Validate error message for invalid encoded Xtra card" 	| 298344053     |STORE								|E						|123456			|0004				|9999900000000					|Invalid XC Card																					|
      | "Validate error message for blank patient ID" 					| 298344053     |STORE								|E						|						|0004				|4879983440532					|Invalid request,idNbr is either missing or incorrect			|


 @Phrenrollment
  @Phrunenroll
  Scenario Outline: Unenroll CVS customer in phr program
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set action code "<enroll_action_code>"
    And Set patient ID "<id_number>"
    And Set card type "<card_type>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to enroll in to Phr program
    Then The user enrolled in to Phr successfully
    And Set action code "<unenroll_action_code>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to unenroll in to Phr program
    Then The user unenrolled from Phr successfully
    Then Validate the phr enrolled status as "<getcust_status>" for <xtra_card_nbr> in getcust response
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|enroll_action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|getcust_status	|unenroll_action_code	|
      | "Validate phr enroll with long card"									 	| 298344053     |STORE								|E									|123456			|0004				|4879983440532					|N							|U										|
      | "Validate phr enroll with short card" 									| 298344053     |STORE								|E									|123456			|0002				|4879983440532					|N							|U										|
      | "Validate phr enroll for additional patient"						| 298344053     |STORE								|E									|123457			|0004				|4879983440532					|N							|U										|
      | "Validate phr enroll with long card - Web"						 	| 298344053     |WEB									|E									|123456			|0004				|4879983440532					|N							|U										|
      | "Validate phr enroll with long card - Mobile"						| 298344053     |MOBILE								|E									|123456			|0004				|4879983440532					|N							|U										|
      
      
  @Phrenrollment
  @Error_validations
  Scenario Outline: Unenroll CVS customer in phr program - Error_validations
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set action code "<action_code>"
    And Set patient ID "<id_number>"
    And Set card type "<card_type>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to enroll in to Phr program
    Then I validate the error code
    And I validate the error message "<error_message>"
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|error_message																							|
      | "Validate error message for blank patient ID" 					| 298344053     |STORE								|U						|						|0004				|4879983440532					|Invalid request,idNbr is either missing or incorrect			|


@Phrenrollment
  @Phrreenroll
  Scenario Outline: Reenroll CVS customer in phr program
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set action code "<enroll_action_code>"
    And Set patient ID "<id_number>"
    And Set card type "<card_type>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to enroll in to Phr program
    Then The user enrolled in to Phr successfully
    And Validate the <xtra_card_nbr> in the response body
    Then Validate the phr enrolled status as "<getcust_status>" for <xtra_card_nbr> in getcust response
    And Set action code "<unenroll_action_code>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to unenroll in to Phr program
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|enroll_action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|getcust_status	|unenroll_action_code	|
      | "Validate phr enroll with long card"									 	| 298344053     |STORE								|E									|123456			|0004				|4879983440532					|Y							|U										|
      | "Validate phr enroll with short card" 									| 298344053     |STORE								|E									|123456			|0002				|4879983440532					|Y							|U										|
      | "Validate phr enroll for additional patient"						| 298344053     |STORE								|E									|123457			|0004				|4879983440532					|Y							|U										|
      | "Validate phr enroll with long card - Web"						 	| 298344053     |WEB									|E									|123456			|0004				|4879983440532					|Y							|U										|
      | "Validate phr enroll with long card - Mobile"						| 298344053     |MOBILE								|E									|123456			|0004				|4879983440532					|Y							|U										|
      
      
  @Phrenrollment
  @Error_validations
  Scenario Outline: Reenroll CVS customer in phr program - Error_validations
    Given I am a CVS user with <xtra_card_nbr> for phr enroll with message source code "<message_source_code>"
    And Set action code "<action_code>"
    And Set patient ID "<id_number>"
    And Set card type "<card_type>"
    When I use Extra Card <xtra_card_nbr> and encoded xtra card "<encoded_xtra_card_nbr>" to enroll in to Phr program
    Then I validate the error code
    And I validate the error message "<error_message>"
    
    Examples:
      | scenario                    														| xtra_card_nbr |message_source_code	|action_code	|id_number	|card_type	|encoded_xtra_card_nbr	|error_message																							|
      | "Validate error message for invalid encoded Xtra card" 	| 298344053     |STORE								|E						|123456			|0004				|9999900000000					|Invalid XC Card																					|
      | "Validate error message for blank patient ID" 					| 298344053     |STORE								|E						|						|0004				|4879983440532					|Invalid request,idNbr is either missing or incorrect			|
