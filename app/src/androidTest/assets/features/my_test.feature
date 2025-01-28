Feature: Cucumber Compose Test Feature

  Scenario Outline: Search Movie
    Given I initialize The autoComplete widget
    When I Search for the Movie "<title>"
    And Click on the first search result
    Then Make sure the first item in the result list is "<title>"
    Examples:
      | title         |
      | The Godfather |
      | Hello         |