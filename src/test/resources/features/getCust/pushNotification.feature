@GetCustPushNotifications
Feature: Get Customer - PushNotifications

  @Web @Mobile @GetCustPushNotifs
  Scenario Outline: Get Customer Push Notifications - Sent successfully
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with "pushNotifications" in the request
    When I get a response from getCust API
    Then API returns a response with status code as 200
    And My xtraCard <exist> in "PUSH_NOTIF_AUDIT" table
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | exist | scenario                                                                                            |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "W"     | "Y"   | "Verify push notifications sent successfully, at Channel = 'W'" |
      | "XC_99901" | "createExtraCard" | "xtraCard" | "M"     | "Y"   | "Verify push notifications sent successfully, at Channel = 'M'" |

  @Web @Mobile @GetCustPushNotifs
  Scenario Outline: Get customer Push Notifications - When card not on file
    Given <scenario>
    When I have my user with xtraCard <xtraCard> from <testDataFile> testDataFile
    And I use my xtraCard with card type <cardType> at <channel>
    And I filter with "pushNotifications" in the request
    When I get a response from getCust API
    Then API returns a response with status code as 400
    Then I get error response with errorCd 4 from getCust API
    And My xtraCard <exist> in "PUSH_NOTIF_AUDIT" table
    Examples:
      | xtraCard   | testDataFile      | cardType   | channel | exist | scenario                                                                                            |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "W"     | "N" |"Verify push notifications cannot be sent when card not on file, at Channel = 'W'" |
      | "XC_99903" | "createExtraCard" | "xtraCard" | "M"     | "N" |"Verify push notifications cannot be sent when card not on file, at Channel = 'M'"|
