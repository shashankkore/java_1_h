@SetCust_DOB_Collection
Feature: SetCust - DOB Collection

  @DOBCollection @SetCust @DOB_Only_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB for a Customer without Phone Number
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I want to update date of birth as <date_of_birth>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action          | date_of_birth | scenario                                              |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection" | "valid"       | "SetCust - Update with 'valid' DOB, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection" | "null"        | "SetCust - Update with 'null' DOB, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection" | "valid"       | "SetCust - Update with 'valid' DOB, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection" | "null"        | "SetCust - Update with 'null' DOB, at Channel = 'M'"  |

  @DOBCollection @SetCust @DOB_Phone_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Phone Number for a Customer with prefSeqNbr, when they have PhoneNbr with prefSeqNbr=1
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I have 'phone number' with prefSeqNbr 1
    And I want to update date of birth as <date_of_birth> and 'phone number' with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    When I get a response from getCust API
    Then The getCust response 'should' have updated date of birth
    And The customer table has updated date of birth
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | prefSeqNbr | scenario                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 5          | "SetCust - Update with 'valid' DOB and PhoneNb with prefSeqNbr=5, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 5          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=5, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'M'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 4          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 4          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'M'"  |

  @DOBCollection @SetCust @DOB_Phone_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Phone Number for a Customer with any prefSeqNbr = 4 (or) 1
  when user have two Phone numbers with prefSeqNbr = 1 & 4
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I have 'phone number' with prefSeqNbr 1
    And I have 'phone number' with prefSeqNbr 4
    And I want to update date of birth as <date_of_birth> and 'phone number' with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated date of birth
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | prefSeqNbr | scenario                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 4          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 4          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 6          | "SetCust - Update with 'valid' DOB and PhoneNbr with new prefSeqNbr=6, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 6          | "SetCust - Update with 'null' DOB and PhoneNbr with new prefSeqNbr=6, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=1, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 4          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 4          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 2          | "SetCust - Update with 'valid' DOB and PhoneNbr with new prefSeqNbr=2, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 2          | "SetCust - Update with 'null' DOB and PhoneNbr with new prefSeqNbr=2, at Channel = 'M'" |

  @DOBCollection @SetCust @DOB_Phone_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Phone Number for a Customer with prefSeqNbr = 4 & without prefSeqNbr = 1
    (when user don't have Phone with PrefSeqNbr = 1)
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I don't have 'phone number' with prefSeqNbr as 1
    And I have 'phone number' with prefSeqNbr 4
    And I want to update date of birth as <date_of_birth> and 'phone number' with prefSeqNbr as 4
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated date of birth
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | scenario                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=4, at Channel = 'M'" |

  @DOBCollection @SetCust @DOB_Phone_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Phone Number for a Customer with any prefSeqNbr = 4 when customer doesn't have any phoneNbr
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I don't have any 'phone number' for my user
    And I want to update date of birth as <date_of_birth> and 'phone number' with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated date of birth
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | prefSeqNbr | scenario                                                                                                       |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=1 when there in no phoneNbr, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=1 when there in no phoneNbr, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 9          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=9 when there in no phoneNbr, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 9          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=9 when there in no phoneNbr, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=1 when there in no phoneNbr, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=1 when there in no phoneNbr, at Channel = 'M'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "valid"       | 8          | "SetCust - Update with 'valid' DOB and PhoneNbr with prefSeqNbr=8 when there in no phoneNbr, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Phone_PrefSeqNbr" | "null"        | 8          | "SetCust - Update with 'null' DOB and PhoneNbr with prefSeqNbr=8 when there in no phoneNbr, at Channel = 'M'"  |

  @DOBCollection @SetCust @DOB_Email_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Email for a Customer with prefSeqNbr, when they have Email with prefSeqNbr=1
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I have 'email' with prefSeqNbr 1
    And I want to update date of birth as <date_of_birth> and 'email' with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    When I get a response from getCust API
    Then The getCust response 'should' have updated date of birth
    And The customer table has updated date of birth
    And The customer table has updated email
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | prefSeqNbr | scenario                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=1, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=1, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 2          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=5, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 2          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=5, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=1, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=1, at Channel = 'M'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 4          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 4          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=4, at Channel = 'M'"  |

  @DOBCollection @SetCust @DOB_Email_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Email for a Customer with any prefSeqNbr = 4 (or) 1
  when user have two Emails with prefSeqNbr = 1 & 4
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I have 'email' with prefSeqNbr 1
    And I have 'email' with prefSeqNbr 4
    And I want to update date of birth as <date_of_birth> and 'email' with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated date of birth
    And The customer table has updated email
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | prefSeqNbr | scenario                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=1, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=1, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 4          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 4          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 6          | "SetCust - Update with 'valid' DOB and Email with new prefSeqNbr=6, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 6          | "SetCust - Update with 'null' DOB and Email with new prefSeqNbr=6, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=1, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=1, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 4          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 4          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 2          | "SetCust - Update with 'valid' DOB and Email with new prefSeqNbr=2, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 2          | "SetCust - Update with 'null' DOB and Email with new prefSeqNbr=2, at Channel = 'M'" |

  @DOBCollection @SetCust @DOB_Email_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Email for a Customer with prefSeqNbr = 4 & without prefSeqNbr = 1
    (when user don't have Email with PrefSeqNbr = 1)
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I don't have 'email' with prefSeqNbr as 1
    And I have 'email' with prefSeqNbr 4
    And I want to update date of birth as <date_of_birth> and 'email' with prefSeqNbr as 4
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated date of birth
    And The customer table has updated email
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | scenario                                                                             |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=4, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=4, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=4, at Channel = 'M'" |

  @DOBCollection @SetCust @DOB_Email_Update
  Scenario Outline: setCust - Customer DOB Collections - Update DOB and Email for a Customer with any prefSeqNbr = 4 when customer doesn't have any email
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I don't have any 'email' for my user
    And I want to update date of birth as <date_of_birth> and 'email' with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated date of birth
    And The customer table has updated email
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                                | date_of_birth | prefSeqNbr | scenario                                                                                                       |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=1 when there in no email, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=1 when there in no email, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 9          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=9 when there in no email, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 9          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=9 when there in no email, at Channel = 'W'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 1          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=1 when there in no email, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 1          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=1 when there in no email, at Channel = 'M'"  |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "valid"       | 8          | "SetCust - Update with 'valid' DOB and Email with prefSeqNbr=8 when there in no email, at Channel = 'M'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "DOBCollection_with_Email_PrefSeqNbr" | "null"        | 8          | "SetCust - Update with 'null' DOB and Email with prefSeqNbr=8 when there in no email, at Channel = 'M'"  |


