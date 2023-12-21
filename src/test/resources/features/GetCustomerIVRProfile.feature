@GetCustomerIVRProfile
Feature: Get Customer - IVR Profile

  @IVRProfileLookUp
  Scenario Outline: IVR profile for extra care card linked to cvs.com account returns all nodes in the response
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return <NODE> nodes
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER  | CHANNEL | NODE                              | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables"|"I"      |  "tables" | "IVR Profile" |
   

 @IVRTopPriorityCustomerLookUp
  Scenario Outline: IVR profile for extra care card linked to cvs.com account returns all nodes in the response
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return <NODE> nodes
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER  | CHANNEL | NODE    | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "topPriority"|"I" |  "tables" | "IVR Profile" |
   

@IVRTopPriorityCustomerLookUp
  Scenario Outline: Customer profile for extra care card linked to cvs.com account returns profile data
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return tables data with information of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "customer" | "IVR Profile" |
   
@IVRProfileLookUp
  Scenario Outline: Customer profile for extra care card linked to cvs.com account returns profile data
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return tables data with information of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME                                                   | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "xtra_card,customer,customer_phone, customer_address" | "IVR Profile" |
  


 @IVRProfileLookUpAddress
  Scenario Outline: Customer profile when customer changes his primary residence address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my primary residence address to <NEW_ADDRESS>
    And I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME         | NEW_ADDRESS | SCENARIO |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "customer_address" | "APT NO 20" | "IVR Profile" |
      

  @IVRProfileLookUpPhoneNumber
  Scenario Outline: Customer profile when customer changes his primary phone number
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my primary phone number to <NEW_NUMBER>
    And I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated phone number of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | NEW_NUMBER   | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "customer_phone" | "5129003425" | "IVR Profile" |
   

  @IVRProfileLookUpDOB
  Scenario Outline: Customer profile when customer changes his date of birth
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my date of birth to <NEW_DOB>
    And I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated date of birth of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME | NEW_DOB    | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "customer" | "19951108" | "IVR Profile" |
     

   @IVRProfileLookUpAddressPref
  Scenario Outline: Customer profile when customer adds secondary residence address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request with <CRITERIA>
    And I added my secondary residence address
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return secondary address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME         | CRITERIA              | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tablesPrefernces" | "I"     | "customer_address" | "addr_pref_seq_nbr=2" | "IVR Profile" |

 @IVRProfileLookUpPhoneNumberPref
  Scenario Outline: Customer profile when customer adds secondary phone number
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request with <CRITERIA>
    And I added my secondary phone number
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return secondary phone numbers of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | CRITERIA               | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tablesPrefernces" | "I"     | "customer_phone" | "phone_pref_seq_nbr=2" | "IVR Profile" |

 
  @IVRProfileLookUpsecondPhornNumberDelete
  Scenario Outline: Customer profile when customer removes his secondary phone number
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I removed my secondary phone number
    And I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return only primary phone numbers of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "customer_phone" | "IVR Profile" |

   @IVRProfileLookUpAddressDel
  Scenario Outline: Customer profile when customer removes his secondary residence address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    And I removed my secondary residence address
    When I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return only primary address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME         | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "I"     | "customer_address" | "IVR Profile" |

 @IVRTopPriorityCustomerDOB 
  Scenario Outline: Customer profile when customer changes his date of birth
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my date of birth to <NEW_DOB>
    And I use the get customer IVR API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated date of birth of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME | NEW_DOB    | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "topPriority" | "I"     | "customer" | "19951108" | "IVR Profile" |
      