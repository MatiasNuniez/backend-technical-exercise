Feature: Accounts API

  Background:
    * url baseUrl
    * def createAccountData = read('data/create-account.json')

  Scenario: Create correct account
    Given path '/accounts'
    And request createAccountData
    When method POST
    Then status 201
    And match response.accountNumber == '478758'
    And match response.accountType == 'Ahorro'
    And match response.currentBalance == 1000.00
    And match response.state == true

  Scenario: Get account by inexistent id
    Given path '/accounts/999'
    When method GET
    Then status 404
    And match response.message == 'Account not found with id: 999'

  Scenario: Get all accounts
    Given path '/accounts'
    When method GET
    Then status 200
    And match response == '#array'

  Scenario: Delete account by inexistent id
    Given path '/accounts/999'
    When method DELETE
    Then status 404