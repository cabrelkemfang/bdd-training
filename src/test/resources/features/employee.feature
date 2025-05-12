Feature: Employee management operations
  En tant qu'utilisateur de l'application Employee Service
  Je veux pouvoir gérer les employés
  Afin de créer, récupérer, mettre à jour et supprimer des employés

  Scenario: Create Employee
    Given L'utilisateur souhaite créer un employé avec les informations suivantes:
      | First Name | Last Name | Email          | Phone Number | Department |
      | John       | Doe       | john@gmail.com | +23058239867 | IT         |
    When L'utilisateur soumet la requête pour créer l'employé
    Then L'employé doit être créé avec succès avec le message: "Employee created Successfully"

  Scenario: Get Employee by ID
    Given L'utilisateur souhaite consulter les détails de l'employé avec l'ID "1"
    When L'utilisateur soumet la requête pour obtenir les détails de l'employé
    Then Les détails de l'employé doivent être retournés avec les informations suivantes:
      | Employee Id | First Name | Last Name | Email          | Phone Number | Department |
      | 1           | John       | Doe       | john@gmail.com | +23058239867 | IT         |

  Scenario: Récupérer un employé inexistant par ID
    Given L'utilisateur souhaite consulter les détails d'un employé avec l'ID "99"
    When L'utilisateur soumet la requête pour obtenir les détails de l'employé
    Then La requête doit échouer avec le message suivant: "Employee with ID 99 not found"

  Scenario: Get All Employees
    When L'utilisateur demande la liste de tous les employés
    Then La liste des employés doit être retournée avec les informations suivantes:
      | Employee Id | First Name | Last Name | Email          | Phone Number | Department |
      | 1           | John       | Doe       | john@gmail.com | +23058239867 | IT         |


  Scenario: Create Employee with Invalid Email
    Given L'utilisateur souhaite créer un employé avec les informations suivantes:
      | First Name | Last Name | Email    | Phone Number | Department  |
      | Franco     | Franco    | test.com | +23058239865 | ENGINEERING |
    When L'utilisateur soumet la requête pour créer l'employé
    Then La requête doit échouer avec le message suivant: "Invalid email format: test.com"


  Scenario: Créer un employé avec un numéro de téléphone invalide
    Given L'utilisateur souhaite créer un employé avec les informations suivantes:
      | First Name | Last Name | Email           | Phone Number | Department |
      | Alice      | Brown     | alice@gmail.com | 58239865     | HR         |
    When L'utilisateur soumet la requête pour créer l'employé
    Then La requête doit échouer avec le message suivant: "Invalid phone number format: 58239865"


  Scenario: Créer un employé avec un email déjà existant
    Given L'utilisateur souhaite créer un employé avec les informations suivantes:
      | First Name | Last Name | Email          | Phone Number | Department |
      | Cabrel     | Doe       | john@gmail.com | +23058239867 | HR         |
    When L'utilisateur soumet la requête pour créer l'employé
    Then La requête doit échouer avec le message suivant: "Employee with email test@gmail.com already exists."