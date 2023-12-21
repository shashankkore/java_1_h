@GetCustomerProfile
Feature: Get Customer - Full Profile

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile for extra care card linked to cvs.com account returns all nodes in the response
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return <NODE> nodes
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER                                      | CHANNEL | NODE                              | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "xtraCard, xtraCare, xtraCarePrefs, tables" | "M"     | "xtraCard, xtraCarePrefs, tables" | "Customer Profile" |
      | "XC_99901" | "createExtraCard" | "xtraCard, xtraCare, xtraCarePrefs, tables" | "W"     | "xtraCard, xtraCarePrefs, tables" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile for extra care card linked to cvs.com account returns profile data
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return tables data with information of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME                                                   | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer, customer_email, customer_phone, customer_address" | "Customer Profile" |
      | "XC_99901" | "createExtraCard" | "tables" | "W"     | "customer, customer_email, customer_phone, customer_address" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer changes his primary residence address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my primary residence address to <NEW_ADDRESS>
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME         | NEW_ADDRESS | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_address" | "APT NO 20" | "Customer Profile" |
      | "XC_99901" | "createExtraCard" | "tables" | "W"     | "customer_address" | "APT NO 20" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer changes his primary email address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my primary email address to <NEW_EMAIL>
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated email address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | NEW_EMAIL            | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_email" | "testauto@gmail.com" | "Customer Profile" |
      | "XC_99901" | "createExtraCard" | "tables" | "W"     | "customer_email" | "testauto@gmail.com" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer changes his primary phone number
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my primary phone number to <NEW_NUMBER>
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated phone number of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | NEW_NUMBER   | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_phone" | "5129003425" | "Customer Profile" |
      | "XC_99901" | "createExtraCard" | "tables" | "W"     | "customer_phone" | "5129003425" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer changes his date of birth
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I change my date of birth to <NEW_DOB>
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return updated date of birth of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME | NEW_DOB    | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer" | "19951108" | "Customer Profile" |
      | "XC_99901" | "createExtraCard" | "tables" | "W"     | "customer" | "19951108" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer adds secondary residence address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request with <CRITERIA>
    And I added my secondary residence address
    When I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return secondary address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME         | CRITERIA              | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_address" | "addr_pref_seq_nbr=2" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer adds secondary phone number
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request with <CRITERIA>
    And I added my secondary phone number
    When I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return secondary phone numbers of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | CRITERIA               | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_phone" | "phone_pref_seq_nbr=2" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer adds secondary email address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request with <CRITERIA>
    When I added my secondary email address
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return secondary email address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | CRITERIA               | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_email" | "email_pref_seq_nbr=2" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer removes his secondary email address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I removed my secondary email address
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return only primary email address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_email" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer removes his secondary phone number
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    When I removed my secondary phone number
    And I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return only primary phone numbers of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME       | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_phone" | "Customer Profile" |

  @Web @Mobile @CustomerProfile
  Scenario Outline: Customer profile when customer removes his secondary residence address
    Given <SCENARIO>
    When I have my user with xtraCard <XTRA_CARD> from <TEST_DATA_FILE> testDataFile
    And I filter for <FILTER> in the request
    And I removed my secondary residence address
    When I use the get customer API for channel <CHANNEL>
    Then I receive a response with success status
    And The response should return only primary address of the customer in <TABLE_NAME> node
    Examples:
      | XTRA_CARD  | TEST_DATA_FILE    | FILTER   | CHANNEL | TABLE_NAME         | SCENARIO           |
      | "XC_99901" | "createExtraCard" | "tables" | "M"     | "customer_address" | "Customer Profile" |
