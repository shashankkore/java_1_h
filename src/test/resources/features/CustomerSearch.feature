@CustomerSearch
Feature: Customer Search API

  @CustomerSearch
  Scenario Outline: Customer Search with different cardType - xtraCard, encodedXtraCard, maskedXtraCard
  and LookUp - Name, Email, Phone, Encoded_Xtra_Card_Nbr, Exact_Match
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType          | channel | lookUp                  | scenario                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "W"     | "Name"                  | "Customer search with Name lookup, Channel = 'W'"                    |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "W"     | "Email"                 | "Customer search with 'Email' lookup, Channel = 'W'"                 |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "W"     | "Phone"                 | "Customer search with 'Phone' lookup, Channel = 'W'"                 |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "W"     | "Exact_Match"           | "Customer search with 'Exact_Match' lookup, Channel = 'W'"           |
      | "XC_99903" | "createExtraCard" | "encodedXtraCard" | "W"     | "Exact_Match"           | "Customer search with 'Exact_Match' lookup, Channel = 'W'"           |
      | "XC_99903" | "createExtraCard" | "maskedXtraCard"  | "W"     | "Exact_Match"           | "Customer search with 'Exact_Match' lookup, Channel = 'W'"           |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "M"     | "Name"                  | "Customer search with 'Name' lookup, Channel = 'M'"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "M"     | "Email"                 | "Customer search with 'Email' lookup, Channel = 'M'"                 |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "M"     | "Encoded_Xtra_Card_Nbr" | "Customer search with 'Encoded_Xtra_Card_Nbr' lookup, Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard"        | "M"     | "Exact_Match"           | "Customer search with 'Exact_Match' lookup, Channel = 'M'"           |
      | "XC_99903" | "createExtraCard" | "encodedXtraCard" | "M"     | "Exact_Match"           | "Customer search with 'Exact_Match' lookup, Channel = 'M'"           |

  @CustomerSearch
  Scenario Outline: Customer Search with cardType = xtraCard and LookUp = Close_Match_V1.1 and Close_Match_V1.2
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user has cardLastScanDt as <cardLastScanDt>
    And I search for customer using <cardType> at <channel>
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | cardLastScanDt | scenario                                                                                         |
      | "XC_99902" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V1" | "null"         | "Customer search with 'Close_Match V1.1' lookup when cardLastScanDt = 'null', Channel = 'W'"     |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V1" | "not null"     | "Customer search with 'Close_Match V1.1' lookup when cardLastScanDt = 'not null', Channel = 'W'" |
      | "XC_99902" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_v2" | "null"         | "Customer search with 'Close_Match V1.2' lookup when cardLastScanDt = 'null', Channel = 'W'"     |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_v2" | "not null"     | "Customer search with 'Close_Match V1.2' lookup when cardLastScanDt = 'not null', Channel = 'W'" |
      | "XC_99902" | "createExtraCard" | "xtraCard" | "M"     | "Close_Match_v2" | "null"         | "Customer search with 'Close_Match V1.2' lookup when cardLastScanDt = 'null', Channel = 'M'"     |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "Close_Match_v2" | "not null"     | "Customer search with 'Close_Match V1.2' lookup when cardLastScanDt = 'not null', Channel = 'M'" |

  @CustomerSearch
  Scenario Outline: Customer Search with Exact_Match lookup - with different combinations of fieldNames -
  Email_CardNbr, Email_Phone, Phone_CardNbr, Email, Phone, CardNbr
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames              | scenario                                                                               |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, CardNbr"        | "Customer search with 'Exact_Match' lookup for with only Email & CardNbr fields"       |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone"          | "Customer search with 'Exact_Match' lookup for with only Email & Phone fields"         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone, CardNbr"        | "Customer search with 'Exact_Match' lookup for with only Phone & CardNbr fields"       |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"                 | "Customer search with 'Exact_Match' lookup for with only Email field"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"                 | "Customer search with 'Exact_Match' lookup for with only Phone field"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"               | "Customer search with 'Exact_Match' lookup for with only CardNbr field"                |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, CardNbr, Phone" | "Customer search with 'Exact_Match' lookup for with all Email, CardNbr & Phone fields" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with lookUp - Name & Encoded_Xtra_Card_Nbr
  when same user has multiple unique Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has multiple unique "customer_address" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_email" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp                  | scenario                                           |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Name"                  | "Customer search - 'Name' lookup"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Encoded_Xtra_Card_Nbr" | "Customer search - 'Encoded_Xtra_Card_Nbr' lookup" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'Email' along with lookUp - Email, Close_Match_V2 & Exact_Match
  when same user has multiple unique Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has multiple unique "customer_address" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_email" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | prefSeqNbr | scenario                                                               |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email"          | "1"        | "Customer search - 'Email' lookup, Email with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email"          | "2"        | "Customer search - 'Email' lookup, Email with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "1"        | "Customer search - 'Close_Match_V2' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "2"        | "Customer search - 'Close_Match_V2' lookup, Email with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "2"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 2"    |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with lookUp - Phone, Close_Match_V2 & Exact_Match
  when same user has multiple unique Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has multiple unique "customer_address" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_email" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | prefSeqNbr | scenario                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone"          | "1"        | "Customer search - 'Phone' lookup, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone"          | "3"        | "Customer search - 'Phone' lookup, PhoneNbr with prefSeqNbr = 3"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "1"        | "Customer search - 'Close_Match_V2' lookup, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "3"        | "Customer search - 'Close_Match_V2' lookup, PhoneNbr with prefSeqNbr = 3" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "1"        | "Customer search - 'Exact_Match' lookup, PhoneNbr with prefSeqNbr = 1"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "3"        | "Customer search - 'Exact_Match' lookup, PhoneNbr with prefSeqNbr = 3"    |

  @CustomerSearch
  Scenario Outline: Customer Search with lookUp - Name, Encoded_Xtra_Card_Nbr
  when same user has duplicate Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has duplicate "customer_address" records
    And My user with xtraCard <xtraCard> has duplicate "customer_email" records
    And My user with xtraCard <xtraCard> has duplicate "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp                  | scenario                                           |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Name"                  | "Customer search - 'Name' lookup"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Encoded_Xtra_Card_Nbr" | "Customer search - 'Encoded_Xtra_Card_Nbr' lookup" |

  @CustomerSearch_done
  Scenario Outline: Customer Search with different 'Email' along with lookUp - Email, Close_Match_V2 & Exact_Match
  when same user has duplicate Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has duplicate "customer_address" records
    And My user with xtraCard <xtraCard> has duplicate "customer_email" records
    And My user with xtraCard <xtraCard> has duplicate "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | prefSeqNbr | scenario                                                               |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email"          | "1"        | "Customer search - 'Email' lookup, Email with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email"          | "3"        | "Customer search - 'Email' lookup, Email with prefSeqNbr = 3"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "1"        | "Customer search - 'Close_Match_V2' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "3"        | "Customer search - 'Close_Match_V2' lookup, Email with prefSeqNbr = 3" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "3"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 3"    |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with lookUp - Phone, Close_Match_V2 & Exact_Match
  when same user has duplicate Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has duplicate "customer_address" records
    And My user with xtraCard <xtraCard> has duplicate "customer_email" records
    And My user with xtraCard <xtraCard> has duplicate "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | prefSeqNbr | scenario                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone"          | "1"        | "Customer search - 'Phone' lookup, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone"          | "2"        | "Customer search - 'Phone' lookup, PhoneNbr with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "1"        | "Customer search - 'Close_Match_V2' lookup, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "2"        | "Customer search - 'Close_Match_V2' lookup, PhoneNbr with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "1"        | "Customer search - 'Exact_Match' lookup, PhoneNbr with prefSeqNbr = 1"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "2"        | "Customer search - 'Exact_Match' lookup, PhoneNbr with prefSeqNbr = 2"    |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'customerName and xtraCard'(prefSeqNbr=1) along with lookUp - Name & Encoded_Xtra_Card_Nbr
  when multiple user has same Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "xtra_card" record with xtraCards "XC_99901"
    And I use different "customer" record with xtraCards "XC_99901"
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp                  | scenario                                           |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Name"                  | "Customer search - 'Name' lookup"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Encoded_Xtra_Card_Nbr" | "Customer search - 'Encoded_Xtra_Card_Nbr' lookup" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'customerName and xtraCard'(prefSeqNbr=2) along with lookUp - Name & Encoded_Xtra_Card_Nbr
  when multiple user has same Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "xtra_card" record with xtraCards "XC_99902"
    And I use different "customer" record with xtraCards "XC_99902"
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp                  | scenario                                           |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Name"                  | "Customer search - 'Name' lookup"                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Encoded_Xtra_Card_Nbr" | "Customer search - 'Encoded_Xtra_Card_Nbr' lookup" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'Email' along with lookUp - Email, Close_Match_V2 & Exact_Match
  when multiple users has same Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | prefSeqNbr | scenario                                                               |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email"          | "1"        | "Customer search - 'Email' lookup, Email with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email"          | "3"        | "Customer search - 'Email' lookup, Email with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "1"        | "Customer search - 'Close_Match_V2' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "3"        | "Customer search - 'Close_Match_V2' lookup, Email with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "3"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 2"    |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with lookUp - Name, Encoded_Xtra_Card_Nbr, Phone, Close_Match_V2 & Exact_Match
  when multiple user has same Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp           | prefSeqNbr | scenario                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone"          | "1"        | "Customer search - 'Phone' lookup, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone"          | "2"        | "Customer search - 'Phone' lookup, PhoneNbr with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "1"        | "Customer search - 'Close_Match_V2' lookup, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Close_Match_V2" | "2"        | "Customer search - 'Close_Match_V2' lookup, PhoneNbr with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "1"        | "Customer search - 'Exact_Match' lookup, PhoneNbr with prefSeqNbr = 1"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match"    | "2"        | "Customer search - 'Exact_Match' lookup, PhoneNbr with prefSeqNbr = 2"    |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'Email' along with Exact_Match lookup - with different combinations of
  fieldNames - Email_Phone, Email, Phone, CardNbr
  when same user has multiple unique Address, Email & Phone
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has multiple unique "customer_address" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_email" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | prefSeqNbr | scenario                                                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "1"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "1"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 1"        |

      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "2"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "2"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "2"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "2"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 2"        |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with Exact_Match lookup - with different combinations of
  fieldNames - Email_Phone, Email, Phone, CardNbr
  when same user has multiple unique Address, Email & Phone
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has multiple unique "customer_address" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_email" records
    And My user with xtraCard <xtraCard> has multiple unique "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | prefSeqNbr | scenario                                                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "1"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "1"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 1"        |

      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "3"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 3" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "3"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 3"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "3"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 3"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "3"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 3"        |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'Email' along with Exact_Match lookup - with different combinations of
  fieldNames - Email_Phone, Email, Phone, CardNbr
  when same user has duplicate Address, Email & Phone
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has duplicate "customer_address" records
    And My user with xtraCard <xtraCard> has duplicate "customer_email" records
    And My user with xtraCard <xtraCard> has duplicate "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | prefSeqNbr | scenario                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |

      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "2"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "2"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "2"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "2"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 2" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with Exact_Match lookup - with different combinations of
  fieldNames - Email_Phone, Email, Phone, CardNbr
  when same user has duplicate Address, Email & Phone
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And My user with xtraCard <xtraCard> has duplicate "customer_address" records
    And My user with xtraCard <xtraCard> has duplicate "customer_email" records
    And My user with xtraCard <xtraCard> has duplicate "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | prefSeqNbr | scenario                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "1"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 1" |

      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "3"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 3" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "3"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 3" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "3"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 3" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "3"        | "Customer search - 'Exact_Match' lookup, Email with prefSeqNbr = 3" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'Email' along with Exact_Match lookup - with different combinations of
  fieldNames - Email_Phone, Email, Phone, CardNbr
  when multiple users has same Address, Email & Phone
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | prefSeqNbr | scenario                                                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "1"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "1"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 1"        |

      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "2"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "2"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "2"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 2"        |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'Email' along with Exact_Match lookup - with combinations of
  fieldNames = Email and prefSeqNbr = 2
  when multiple users has same Address, Email & Phone
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_email" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get error response with statusCd 15 from custSearch API
    And API returns a response with status code as 400
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames | prefSeqNbr | scenario                                                                                         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"    | "2"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 2" |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with Exact_Match lookup - with different combinations of
  fieldNames - Email_CardNbr, Email_Phone, Phone_CardNbr, Email, Phone, CardNbr
  when multiple user has same Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    And The response should have expected data for all mandatory fields
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | prefSeqNbr | scenario                                                                                                  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "1"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 1" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "1"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 1"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "1"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 1"        |

      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "2"        | "Customer search - 'Exact_Match' lookup for with only Email & Phone fields, PhoneNbr with prefSeqNbr = 2" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "2"        | "Customer search - 'Exact_Match' lookup for with only Email field, PhoneNbr with prefSeqNbr = 2"          |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "2"        | "Customer search - 'Exact_Match' lookup for with only CardNbr field, PhoneNbr with prefSeqNbr = 2"        |

  @CustomerSearch
  Scenario Outline: Customer Search with different 'PhoneNbr' along with Exact_Match lookup - with different combinations of
  fieldNames = Phone and prefSeqNbr = 2
  when multiple user has same Address, Email & Phone.
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_address" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_email" records
    And I have multiple users with xtraCards "XC_99901, XC_99902, XC_99903" having same "customer_phone" records
    And I search for customer using <cardType> at <channel>
    And I use different "customer_phone" record with preferred sequence number <prefSeqNbr> to search
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get error response with statusCd 15 from custSearch API
    And API returns a response with status code as 400
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames | prefSeqNbr | scenario                                                                                         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"    | "2"        | "Customer search - 'Exact_Match' lookup for with only Phone field, PhoneNbr with prefSeqNbr = 2" |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Name, Email, Phone
  when user don't have address, email and phone setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_address" set for my user <xtraCard>
    And I have no "customer_email" set for my user <xtraCard>
    And I have no "customer_phone" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp  | scenario                                                                    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Name"  | "Customer Search with 'Name' lookup - when Address, Email & Phone not set"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Email" | "Customer Search with 'Email' lookup - when Adress, Email & Phone not set"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Phone" | "Customer Search with 'Phone' lookup - when Address, Email & Phone not set" |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Exact_Match - with fieldNames = Email_Phone, Email, Phone, CardNbr
  when user don't have address setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_address" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | scenario                                                                              |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "Customer Search with 'Exact_Match' lookup with Email & Phone - when Address not set" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "Customer Search with 'Exact_Match' lookup with Email - when Address not set"         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "Customer Search with 'Exact_Match' lookup with Phone - when Address not set"         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"      | "Customer Search with 'Exact_Match' lookup with CardNbr - when Address not set"       |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Exact_Match - with fieldNames = Phone, CardNbr
  when user don't have email setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_email" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames | scenario                                                                      |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"    | "Customer Search with 'Exact_Match' lookup with Phone - when Email not set"   |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"  | "Customer Search with 'Exact_Match' lookup with CardNbr - when Email not set" |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Exact_Match - with fieldNames = Email_Phone, EMail
  when user don't have email setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_email" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get error response with statusCd 13 from custSearch API
    And API returns a response with status code as 400
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | scenario                                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "Customer Search with 'Exact_Match' lookup with Email & Phone - when Email not set" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "Customer Search with 'Exact_Match' lookup with Email - when Email not set"         |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Exact_Match - with fieldNames = Email, CardNbr
  when user don't have Phone setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_phone" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get response from custSearch API
    And API returns a response with status code as 200
    And The response should have all mandatory fields for <lookUp> look-up
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames | scenario                                                                      |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"    | "Customer Search with 'Exact_Match' lookup with Email - when Phone not set"   |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "CardNbr"  | "Customer Search with 'Exact_Match' lookup with CardNbr - when Phone not set" |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Exact_Match - with fieldNames = Email_Phone, Phone
  when user don't have Phone setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_phone" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get error response with statusCd 13 from custSearch API
    And API returns a response with status code as 400
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | scenario                                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "Customer Search with 'Exact_Match' lookup with Email & Phone - when Phone not set" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "Customer Search with 'Exact_Match' lookup with Phone - when Phone not set"         |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - Exact_Match - with fieldNames = Email_Phone, Email, Phone
  when user don't have Email & Phone setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_email" set for my user <xtraCard>
    And I have no "customer_phone" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get error response with statusCd 13 from custSearch API
    And API returns a response with status code as 400
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | scenario                                                                                    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "Customer Search with 'Exact_Match' lookup with Email & Phone - when Email & Phone not set" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "Customer Search with 'Exact_Match' lookup with Email - when Email & Phone not set"         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "Customer Search with 'Exact_Match' lookup with Phone - when Email & Phone not set"         |

  @CustomerSearch
  Scenario Outline: Customer Search when user along with LookUp - 'Exact_Match' with fieldNames = Email, Phone, Email_Phone
  when user don't have Email & Phone setup
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I search for customer using <cardType> at <channel>
    And I have no "customer_address" set for my user <xtraCard>
    And I have no "customer_email" set for my user <xtraCard>
    And I have no "customer_phone" set for my user <xtraCard>
    And I use <lookUp> look-up for Customer Search
    And I look-up with <fieldNames>
    Then I get error response with statusCd 13 from custSearch API
    And API returns a response with status code as 400
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | lookUp        | fieldNames     | scenario                                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email, Phone" | "Customer Search with 'Exact_Match' lookup with Email & Phone - when Address, Email & Phone not set" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Email"        | "Customer Search with 'Exact_Match' lookup with Email - when Address, Email & Phone not set"         |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "Exact_Match" | "Phone"        | "Customer Search with 'Exact_Match' lookup with Phone - when Address, Email & Phone not set"         |

