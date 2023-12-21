@SetCust_IVR
Feature: SetCust - IVR Specific Requests

  @SetCust @IVR_Phone_Update
  Scenario Outline: setCust - IVR Specific Requests - Update PhoneNbr when user has phoneNbr with prefSeqNbr=1
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I have 'phone number' with prefSeqNbr <prefSeqNbr>
    And I want to update phone number with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                | prefSeqNbr | scenario                                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_W" | "IVR_PhoneNbr_Update" | 1          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=1, Channel = 'IVR' with 'CVS.COM'"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_M" | "IVR_PhoneNbr_Update" | 1          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=1, Channel = 'IVR' with 'MOBILE_ENT'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR"   | "IVR_PhoneNbr_Update" | 1          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=1, Channel = 'IVR'"                   |

  @SetCust @IVR_Phone_Update
  Scenario Outline: setCust - IVR Specific Requests - Update PhoneNbr when user has PhoneNbrs with prefSeqNbr= 1 & 2
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I have 'phone number' with prefSeqNbr 1
    And I have 'phone number' with prefSeqNbr <prefSeqNbr>
    And I want to update phone number with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                | prefSeqNbr | scenario                                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_W" | "IVR_PhoneNbr_Update" | 1          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=1, Channel = 'IVR' with 'CVS.COM'"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_M" | "IVR_PhoneNbr_Update" | 2          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=2, Channel = 'IVR' with 'MOBILE_ENT'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR"   | "IVR_PhoneNbr_Update" | 2          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=2, Channel = 'IVR'"                   |

  @SetCust @IVR_Phone_Update
  Scenario Outline: setCust - IVR Specific Requests - Update PhoneNbr when user doesn't have any phoneNbr
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I don't have 'phone number' with prefSeqNbr as 1
    And I want to update phone number with prefSeqNbr as <prefSeqNbr>
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    And The customer table has updated phone number
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action                | prefSeqNbr | scenario                                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_W" | "IVR_PhoneNbr_Update" | 4          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=4, Channel = 'IVR' with 'CVS.COM'"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_M" | "IVR_PhoneNbr_Update" | 4          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=4, Channel = 'IVR' with 'MOBILE_ENT'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR"   | "IVR_PhoneNbr_Update" | 4          | "SetCust - IVR, Update PhoneNbr at prefSeqNbr=4, Channel = 'IVR'"                   |

  @SetCust @IVR_Card_Reorder
  Scenario Outline: setCust - IVR Specific Requests - Card Reorder with customer existing (or) new address
    Given <scenario>
    And I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I want to reorder the card with <customer_data> customer name and address
    When I try <action> via request
    Then I get a response from setCust API
    And API returns a response with status code as 200
    And The response should have xtraCard details
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | action             | customer_data | scenario                                                               |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_W" | "IVR_Submit_Order" | "New"         | "SetCust - IVR, Card reorder with 'new' customer data, at Channel = 'IVR' with 'CVS.COM'"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_M" | "IVR_Submit_Order" | "New"         | "SetCust - IVR, Card reorder with 'new' customer data, at Channel = 'IVR' with 'MOBILE_ENT'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR"   | "IVR_Submit_Order" | "New"         | "SetCust - IVR, Card reorder with 'new' customer data, at Channel = 'IVR'"                   |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_W" | "IVR_Submit_Order" | "Existing"    | "SetCust - IVR, Card reorder with 'existing' customer data, at Channel = 'IVR' with 'CVS.COM'"    |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR_M" | "IVR_Submit_Order" | "Existing"    | "SetCust - IVR, Card reorder with 'existing' customer data, at Channel = 'IVR' with 'MOBILE_ENT'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "IVR"   | "IVR_Submit_Order" | "Existing"    | "SetCust - IVR, Card reorder with 'existing' customer data, at Channel = 'IVR'"                   |
