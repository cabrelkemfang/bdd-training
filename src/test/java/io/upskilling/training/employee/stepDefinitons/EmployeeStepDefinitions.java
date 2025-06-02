package io.upskilling.training.employee.stepDefinitons;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.upskilling.training.employee.dto.EmployeeRequest;
import io.upskilling.training.employee.dto.EmployeeResponse;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeStepDefinitions {

    private static final String API_PATH = "/api/employees";

    private Response response;
    private EmployeeRequest employeeRequest;
    private Long employeeId;

    @LocalServerPort
    private Integer port;

    @Before
    public void setup() {
        // Initialize RestAssured with the random port assigned by Spring Boot
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Given("L'utilisateur souhaite créer un employé avec les informations suivantes:")
    public void utilisateur_souhaite_creer_employe(EmployeeRequest employees) {
        this.employeeRequest = employees;
    }

    @When("L'utilisateur soumet la requête pour créer l'employé")
    public void utilisateur_soumet_requete_creer_employe() {
        response = given()
                .contentType(ContentType.JSON)
                .body(employeeRequest)
                .when()
                .post(API_PATH);
    }

    @Then("L'employé doit être créé avec succès avec le message: {string}")
    public void employe_cree_avec_succes(String message) {
        response.then()
                .statusCode(HttpStatus.CREATED.value());

        assertThat(response.getBody().asString())
                .isEqualTo(message);
    }

    @Then("La requête doit échouer avec le message suivant: {string}")
    public void requete_echoue_avec_message(String errorMessage) {
        var problemDetail = response.as(ProblemDetail.class);
        assertThat(problemDetail)
                .isNotNull()
                .satisfies(pd -> {
                    assertThat(pd.getDetail()).isEqualTo(errorMessage);
                });
    }

    @Given("L'utilisateur souhaite consulter les détails d'un employé avec l'ID {long}")
    public void utilisateur_souhaite_consulter_details_employe(Long id) {
        employeeId = id;
    }

    @When("L'utilisateur soumet la requête pour obtenir les détails de l'employé")
    public void utilisateur_soumet_requete_obtenir_details() {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH + "/" + employeeId);
    }

    @Then("Les détails de l'employé doivent être retournés avec les informations suivantes:")
    public void details_employe_retournes(EmployeeResponse employeeResponse) {
        response.then().statusCode(HttpStatus.OK.value());
        var employee = response.as(EmployeeResponse.class);

        assertThat(employee)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("localDateTime")
                .isEqualTo(employeeResponse);

    }

    @When("L'utilisateur demande la liste de tous les employés")
    public void utilisateur_demande_liste_employes() {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_PATH);
    }

    @Then("La liste des employés doit être retournée avec les informations suivantes:")
    public void liste_employes_retournee(List<EmployeeResponse> employees) {
        response.then().statusCode(HttpStatus.OK.value());

        var employeesResponse = response.as(new TypeRef<List<EmployeeResponse>>() {
        });

        assertThat(employeesResponse)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("localDateTime")
                .isEqualTo(employees);
    }
}
