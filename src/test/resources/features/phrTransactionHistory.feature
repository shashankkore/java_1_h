 #Author: Vani.Puranam@CVSHealth.com
 @PHRTranHistory
 Feature: View Customer PHR Transaction History Details


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I recently enrolled in PHR as non-targeted customer and made single Flu Shot transaction 3 days ago"
     And I use my Extra Card 98158363
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType | firstName |
       | currentdate-3    | 10            | 5                 | Flu shot  | John      |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I recently enrolled in PHR as targeted customer and made single 90-day prescription,30-day prescription, Online prescription access, Flu Shot and Immunization yesterday"
     And I use my Extra Card 98158364
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Transaction Date as
       | transaction_Date |
       | currentdate-1    |
     And I see my Pharmacy Health Rewards Transaction History creditsSummary as
       | credits_Earned | event_Type                       | first_Name |
       | 1              | 30-day prescription              | John       |
       | 2              | Online prescription access added | John       |
       | 10             | Flu shot                         | John       |
       | 3              | 90-day prescription              | John       |
       | 6              | Immunization                     | John       |
     And I see my Pharmacy Health Rewards Transaction History extraBucksEarned as
       | extraBucks_Earned |
       | 10                |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I am a non-targeted customer and made 90-day prescription 2 days ago,30-day prescription every month, Online prescription access last month, Flu Shot in 20 days before and Immunization 10 days before"
     And I use my Extra Card 98158365
     And I have offset and limit as
       | offset | limit |
       | 0      | 20    |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType                        | firstName |
       | currentdate-2    | 3             | 5                 | 90-day prescription              | John      |
       | currentdate-5    | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-10   | 6             | 5                 | Immunization                     | John      |
       | currentdate-20   | 10            | 5                 | Flu shot                         | John      |
       | currentdate-35   | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-45   | 5             | 0                 | Online prescription access added | John      |
       | currentdate-65   | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-95   | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-125  | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-155  | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-185  | 1             | 0                 | 30-day prescription              | John      |
       | currentdate-215  | 1             | 0                 | 30-day prescription              | John      |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I am a non-targeted customer with 1 PHR dependent who recently enrolled and both of us made single Prescription fill yesterday"
     And I use my Extra Card 98158366
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Transaction Date as
       | transaction_Date |
       | currentdate-1    |
     And I see my Pharmacy Health Rewards Transaction History creditsSummary as
       | credits_Earned | event_Type          | first_Name |
       | 1              | 30-day prescription | John       |
       | 1              | 30-day prescription | Mary       |
     And I see my Pharmacy Health Rewards Transaction History extraBucksEarned as
       | extraBucks_Earned |
       | 0                 |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I and my wife are non-targeted customers with 2 PHR dependents and I made one 90-day prescription 2 days ago, my wife made 30-day prescription yesterday and my kids had Immunization last month on different days"
     And I use my Extra Card 98158367
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType           | firstName |
       | currentdate-1    | 1             | 0                 | 30-day prescription | Mary      |
       | currentdate-2    | 3             | 0                 | 90-day prescription | John      |
       | currentdate-35   | 6             | 5                 | Immunization        | Sam       |
       | currentdate-45   | 6             | 0                 | Immunization        | Alisha    |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I am targeted customer with 2 PHR dependents who recently enrolled and made multiple same Prescription fills on same day and earned Extra bucks"
     And I use my Extra Card 98158368
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Transaction Date as
       | transaction_Date |
       | currentdate-5    |
     And I see my Pharmacy Health Rewards Transaction History creditsSummary as
       | credits_Earned | event_Type          | first_Name |
       | 3              | 90-day prescription | John       |
       | 3              | 90-day prescription | Mary       |
       | 3              | 90-day prescription | Sam        |
       | 3              | 90-day prescription | Alisha     |
     And I see my Pharmacy Health Rewards Transaction History extraBucksEarned as
       | extraBucks_Earned |
       | 6                 |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I and my wife joined PHR as non-targeted customers and made single 90-day prescription,30-day prescription, Online prescription access, Flu Shot and Immunization last week"
     And I use my Extra Card 98158369
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Transaction Date as
       | transaction_Date |
       | currentdate-10   |
     And I see my Pharmacy Health Rewards Transaction History creditsSummary as
       | credits_Earned | event_Type                       | first_Name |
       | 1              | 30-day prescription              | John       |
       | 1              | 30-day prescription              | Mary       |
       | 5              | Online prescription access added | John       |
       | 5              | Online prescription access added | Mary       |
       | 10             | Flu shot                         | John       |
       | 10             | Flu shot                         | Mary       |
       | 3              | 90-day prescription              | John       |
       | 3              | 90-day prescription              | Mary       |
       | 6              | Immunization                     | John       |
       | 6              | Immunization                     | Mary       |
     And I see my Pharmacy Health Rewards Transaction History extraBucksEarned as
       | extraBucks_Earned |
       | 25                |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I have one of the member filled 100(30 days prescriptions) from multiple transactions on multiple days and now I get 100 points"
     And I use my Extra Card 98158370
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType           | firstName |
       | currentdate-1    | 30            | 15                | 30-day prescription | Sam       |
       | currentdate-2    | 10            | 5                 | 30-day prescription | Sam       |
       | currentdate-3    | 45            | 25                | 30-day prescription | Sam       |
       | currentdate-4    | 15            | 5                 | 30-day prescription | Sam       |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I and my dependents are non-targeted customers and made 50 same transactions across 10 days and want to see with offset 5 and limit 5"
     And I use my Extra Card 98158372
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType    | firstName |
       | currentdate-1    | 30            | 15                | Immunization | John      |
       | currentdate-2    | 30            | 15                | Immunization | Mary      |
       | currentdate-3    | 30            | 15                | Immunization | Sam       |
       | currentdate-4    | 30            | 15                | Immunization | Alisha    |
       | currentdate-5    | 30            | 15                | Immunization | John      |

     And I have offset and limit as
       | offset | limit |
       | 5      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType    | firstName |
       | currentdate-6    | 30            | 15                | Immunization | Mary      |
       | currentdate-7    | 30            | 15                | Immunization | Sam       |
       | currentdate-8    | 30            | 15                | Immunization | Alisha    |
       | currentdate-9    | 30            | 15                | Immunization | John      |
       | currentdate-10   | 30            | 15                | Immunization | Mary      |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I and my dependents are non-targeted customers and made 20 different transactions across 20 days and want to see with offset 0 and limit 10"
     And I use my Extra Card 98158371
     And I have offset and limit as
       | offset | limit |
       | 0      | 10    |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType           | firstName |
       | currentdate-1    | 3             | 5                 | 90-day prescription | John      |
       | currentdate-2    | 1             | 0                 | 30-day prescription | Mary      |
       | currentdate-3    | 6             | 0                 | Immunization        | Sam       |
       | currentdate-4    | 6             | 5                 | Immunization        | Alisha    |
       | currentdate-5    | 3             | 0                 | 90-day prescription | John      |
       | currentdate-6    | 1             | 0                 | 30-day prescription | Mary      |
       | currentdate-7    | 6             | 5                 | Immunization        | Sam       |
       | currentdate-8    | 6             | 5                 | Immunization        | Alisha    |
       | currentdate-9    | 3             | 0                 | 90-day prescription | John      |
       | currentdate-10   | 1             | 0                 | 30-day prescription | Mary      |
     And I have offset and limit as
       | offset | limit |
       | 10     | 10    |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType           | firstName |
       | currentdate-11   | 6             | 5                 | Immunization        | Sam       |
       | currentdate-12   | 6             | 0                 | Immunization        | Alisha    |
       | currentdate-13   | 3             | 5                 | 90-day prescription | John      |
       | currentdate-14   | 1             | 0                 | 30-day prescription | Mary      |
       | currentdate-15   | 6             | 0                 | Immunization        | Sam       |
       | currentdate-16   | 6             | 5                 | Immunization        | Alisha    |
       | currentdate-17   | 3             | 0                 | 90-day prescription | John      |
       | currentdate-18   | 1             | 0                 | 30-day prescription | Mary      |
       | currentdate-19   | 6             | 5                 | Immunization        | Sam       |
       | currentdate-20   | 6             | 0                 | Immunization        | Alisha    |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I and my dependents are non-targeted customers and one of us reached the CAP"
     And I use my Extra Card 98158373
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType    | firstName |
       | currentdate-1    | 36            | 15                | Immunization | John      |
       | currentdate-2    | 24            | 10                | Immunization | Mary      |
       | currentdate-3    | 12            | 5                 | Immunization | Sam       |
       | currentdate-4    | 102           | 50                | Immunization | Alisha    |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I and my dependents are non-targeted customers and all of us reached the CAP"
     And I use my Extra Card 98158374
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType    | firstName |
       | currentdate-1    | 102           | 50                | Immunization | John      |
       | currentdate-2    | 102           | 50                | Immunization | Mary      |
       | currentdate-3    | 102           | 50                | Immunization | Sam       |
       | currentdate-4    | 102           | 50                | Immunization | Alisha    |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I am a non-targeted customer with PHR dependent who recently enrolled and made 0 transactions"
     And I use my Extra Card 98158375
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I have unenrolled from PHR yesterday and want to see my transactions history till yesterday"
     And I use my Extra Card 98158376
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
     And I see my Pharmacy Health Rewards Transaction History Information as
       | transaction_Date | creditsEarned | extraBucks_Earned | eventType                        | firstName |
       | currentdate-1    | 3             | 0                 | 90-day prescription              | John      |
       | currentdate-2    | 10            | 5                 | Flu shot                         | John      |
       | currentdate-3    | 6             | 5                 | Immunization                     | John      |
       | currentdate-4    | 5             | 0                 | Online prescription access added | John      |


   @Web @Mobile
   Scenario: View Customer Pharmacy Health Rewards Transaction History Details in DashBoard
     Given "I have never enrolled into PHR and I haven't made any transactions"
     And I use my Extra Card 98158377
     And I have offset and limit as
       | offset | limit |
       | 0      | 5     |
     When I view PHR Transaction History Details in DashBoard for my card
     Then I see the Pharmacy Health Rewards Transaction History
