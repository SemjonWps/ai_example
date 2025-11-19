---
applyTo: '**'
description: 'description'
---
Provide project context and coding guidelines that AI should follow when generating code, answering questions, or reviewing changes.

---
applyTo: '**/*Tests.java, **/*IT.java'
description: 'Unit test guidelines for Java test files'
---

# Unit Test Guidelines

Follow these guidelines when creating or modifying Java test files (*Tests.java and *IT.java).

## Test Method Naming
- Test method names must follow the Given-When-Then pattern
- Use underscores to separate the three parts: `given_when_then`
- Example: `theShoppingListIsInValidState_theCustomerHitsEnterButton_theItemsWillBeSendToCustomer`

## Test Method Structure
- Use Arrange-Act-Assert comments to clearly separate test sections:
  ```java
  @Test
  void theShoppingListIsInValidState_theCustomerHitsEnterButton_theItemsWillBeSendToCustomer() {
      // Arrange
      // Setup test data and preconditions
      
      // Act
      // Execute the method under test
      
      // Assert
      // Verify the expected outcomes
  }
  ```

## Additional Guidelines
- Keep test methods focused on a single behavior
- Use descriptive variable names that reflect the business context
- Ensure tests are independent and can run in any order
