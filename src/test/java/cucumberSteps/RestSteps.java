package cucumberSteps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import utilities.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestSteps {

    private Response response;
    private static final String BASE_URL = "http://localhost:8080/api";
    private static String jsonString;
    private String animalId;
    private String careId;
    private String veterinaryId;
    private String hostId;
    private String adoptiveId;
    private Connection myConnex;

    // Reset des valeurs à la fin d'un scénario pour pouvoir les enchainer
    @After
    public void resetValue() {
        jsonString = null;
        animalId = null;
        careId = null;
        veterinaryId = null;
        hostId = null;
        adoptiveId= null;
        response = null;
        myConnex = null;
        System.out.println("Values are now reseted!");
    }


    // ######################################################### GIVEN #########################################################

    @Given("^un \\w*?\\s?animal crée avec les paramètres, species: (\\w*), race: (\\w*\\s?\\w*), date de naissance: (\\d{4}-\\d{2}-\\d{2}), name: (\\w*), sexe: (\\w*)$")
    public void createAnimal(String specie, String race, String birthDate_str, String name, String sexe) {
        Connection myConnex = null;

        Date birthDate = Date.valueOf(birthDate_str);

        try {
            myConnex = ConnectionDB.give_connection();

            Date date = Date.valueOf(LocalDate.now());

            // the mysql insert statement
            String query = "insert into animal (species, race, birth_date, name, sexe, arrival_date) VALUES (?, ?, ? ,?, ?, ?)";
            PreparedStatement predStmtInsert = myConnex.prepareStatement(query);

            // create the mysql insert preparedstatement
            predStmtInsert.setString(1, specie);
            predStmtInsert.setString(2, race);
            predStmtInsert.setDate(3, birthDate);
            predStmtInsert.setString(4, name);
            predStmtInsert.setString(5, sexe);
            predStmtInsert.setDate(6, date);

            // execute the preparedstatement
            predStmtInsert.execute();

            System.out.println("Animal inserted in DB");

            //Creating a Statement object
            PreparedStatement predStmtSelect = myConnex.prepareStatement("select id from animal where name =?");
            predStmtSelect.setString(1, name);
            //Retrieving the data
            ResultSet rs = predStmtSelect.executeQuery();
            rs.next();
            animalId = rs.getString(1);

            System.out.println("Animal ID: " + animalId);

            myConnex.close();

        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
    }


    @Given("^un \\w*?\\s?soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: (\\w*), date: (\\d{4}-\\d{2}-\\d{2}), heure: (\\d{2}:\\d{2})$")
    public void createCare(String examenName, String examenDate, String examenTime) {

        int animal = Integer.parseInt(animalId);
        int veterinary = Integer.parseInt(veterinaryId);
        Date date = Date.valueOf(examenDate);

        try {
            myConnex = ConnectionDB.give_connection();


            // the mysql insert statement
            String query = "insert into veterinary_care (examen, examen_date, examen_time, id_animal, id_veterinary) VALUES (?, ?, ? ,?,?)";
            PreparedStatement predStmtInsert = myConnex.prepareStatement(query);

            // create the mysql insert preparedstatement
            predStmtInsert.setString(1, examenName);
            predStmtInsert.setDate(2, date);
            predStmtInsert.setString(3, examenTime);
            predStmtInsert.setInt(4, animal);
            predStmtInsert.setInt(5, veterinary);


            // execute the preparedstatement
            predStmtInsert.execute();

            System.out.println("Veterinary care inserted in DB");

            //Creating a Statement object
            PreparedStatement predStmtSelect = myConnex.prepareStatement("select id from veterinary_care where examen =?");
            predStmtSelect.setString(1, examenName);
            //Retrieving the data
            ResultSet rs = predStmtSelect.executeQuery();
            rs.next();
            careId = rs.getString(1);

            System.out.println("Veterinary care Id: " + careId);

            myConnex.close();

        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
    }


    @Given("^un \\w*?\\s?vétérinaire crée avec les paramètres, prénom: (\\w*), nom: (\\w*), numéro de téléphone: (\\d{10}), code postal: (\\d{5}), ville: (\\w*), nom de rue: (\\w*), numéro de rue: (\\d{1,2})$")
    public void createVeterinary(String firstName, String lastName, String phoneNumber, String zipCodeStr, String city, String streetName, String streetNumStr) {
        Connection myConnex = null;
        int streetNum = Integer.parseInt(streetNumStr);
        int zipCode = Integer.parseInt(zipCodeStr);


        try {
            myConnex = ConnectionDB.give_connection();

            // the mysql insert statement
            String query = "insert into veterinary (first_name, last_name, phone_number, street_name, street_number, zip_code, city) VALUES (?, ?, ? ,?, ?, ?, ?)";
            PreparedStatement predStmtInsert = myConnex.prepareStatement(query);

            // create the mysql insert preparedstatement
            predStmtInsert.setString(1, firstName);
            predStmtInsert.setString(2, lastName);
            predStmtInsert.setString(3, phoneNumber);
            predStmtInsert.setString(4, streetName);
            predStmtInsert.setInt(5, streetNum);
            predStmtInsert.setInt(6, zipCode);
            predStmtInsert.setString(7, city);


            // execute the preparedstatement
            predStmtInsert.execute();

            System.out.println("Veterinary inserted in DB");

            //Creating a Statement object
            PreparedStatement predStmtSelect = myConnex.prepareStatement("select id from veterinary where last_name =?");
            predStmtSelect.setString(1, lastName);
            //Retrieving the data
            ResultSet rs = predStmtSelect.executeQuery();
            rs.next();
            veterinaryId = rs.getString(1);

            System.out.println("Veterinary ID: " + veterinaryId);

            myConnex.close();


        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
    }

    @Given("^une \\w*?\\s?famille d'(\\w*) créée avec les paramètres, prénom: (\\w*), nom: (\\w*), numéro de téléphone: (\\d{10}), code postal: (\\d{5}), ville: (\\w*), nom de rue: (\\w*), numéro de rue: (\\d{1,2})$")
    public void createHost(String family, String firstName, String lastName, String phoneNumber, String zipCodeStr, String city, String streetName, String streetNumStr) {
        Connection myConnex = null;
        int streetNum = Integer.parseInt(streetNumStr);
        int zipCode = Integer.parseInt(zipCodeStr);

        //Si family est égale à accueil, familyType = host_family, sinon familyType = adoptive_family
        String familyType = family.equals("accueil") ? "host_family" : "adoptive_family";


        try {
            myConnex = ConnectionDB.give_connection();


            // the mysql insert statement
            String query = "insert into "+familyType+" (first_name, last_name, phone_number, street_name, street_number, zip_code, city) VALUES (?, ?, ? ,?, ?, ?, ?)";
            PreparedStatement predStmtInsert = myConnex.prepareStatement(query);

            // create the mysql insert preparedstatement
            predStmtInsert.setString(1, firstName);
            predStmtInsert.setString(2, lastName);
            predStmtInsert.setString(3, phoneNumber);
            predStmtInsert.setString(4, streetName);
            predStmtInsert.setInt(5, streetNum);
            predStmtInsert.setInt(6, zipCode);
            predStmtInsert.setString(7, city);


            // execute the preparedstatement
            predStmtInsert.execute();

            System.out.println(familyType+ "inserted in DB");

            //Creating a Statement object
            PreparedStatement predStmtSelect = myConnex.prepareStatement("select id from "+familyType+" where last_name =?");
            predStmtSelect.setString(1, lastName);
            //Retrieving the data
            ResultSet rs = predStmtSelect.executeQuery();
            rs.next();
            if(familyType.equals("host_family")){
                hostId = rs.getString(1);
                System.out.println("Host family ID: " + hostId);
            }else{
                adoptiveId = rs.getString(1);
                System.out.println("Adoptive family ID: " + adoptiveId);
            }


            myConnex.close();


        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
    }

    @Given("^cette famille qui (\\w*) (\\w*)$")
    public void adoptOrhostAnimal(String family, String name){
        Connection myConnex = null;

        try {
            myConnex = ConnectionDB.give_connection();

            String query = null;
            PreparedStatement predStmt = null;

            switch (family){
                case "accueille":
                    query = "update animal set id_host_family =? where name = ?";
                    predStmt = myConnex.prepareStatement(query);
                    predStmt.setString(1, hostId);
                    break;
                case "adopte":
                    query = "update animal set id_adoptive_family =? where name = ?";
                    predStmt = myConnex.prepareStatement(query);
                    predStmt.setString(1, adoptiveId);
                    break;
            }

            predStmt.setString(2, name);

            predStmt.executeUpdate();
            myConnex.close();

        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }


    }


    // ######################################################### WHEN #########################################################

    @When("^j'envoi au WS (\\w*) un (GET|DELETE) avec en paramètre l'id d(?:e|u) \\w*?\\s?(?:l'|la)?(?:animal|soin|vétérinaire|famille) (?:créé[e]?)$")
    public void getOrDeleteOneObject(String address, String method) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        String adressComplement = null;

        switch (address) {
            case "animal":
                adressComplement = "/" + address + "/" + animalId;
                break;
            case "care":
                if (method.equals("GET")) {
                    adressComplement = "/" + animalId + "/" + address;
                } else {
                    adressComplement = "/" + address + "/" + careId;
                }
                break;
            case "veterinary":
                adressComplement = "/" + address + "/" + veterinaryId;
                break;
            case "host":
                adressComplement = "/" + address + "/" + hostId;
                break;
            case "adoptive":
                adressComplement = "/" + address + "/" + adoptiveId;
                break;
        }


        if (method.equals("GET")) {
            response = request.get(adressComplement);
            jsonString = response.asString();
        }
        if (method.equals("DELETE")) {
            response = request.delete(adressComplement);
            jsonString = response.asString();
        }
    }

    @When("^j'envoi au WS (\\w*)/id/(\\w*) un (DELETE) avec en paramètre l'id de l'animal$")
    public void deleteAllCaresByAnimal(String adress1, String adress2, String method) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.delete(adress1 + "/" + animalId + "/" + adress2);
        jsonString = response.asString();
    }


    @When("^j'envoi au WS (\\w*|\\w*\\/\\w*) un GET sans paramètre$")
    public void getAll(String address) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        response = request.get("/" + address);
        jsonString = response.asString();

    }

    @When("^j'envoi au WS (\\w*) un (POST|PUT) avec dans le corps du WS, (?:id: idGénéré et )?les paramètres suivants: \\{(.*)\\}$")
    public void postObject(String address, String method, String hashMap) {

        //Transforme la chaine de caractère en HashMap
        Map<String, String> myMap = new HashMap<String, String>();
        String pairs[] = hashMap.split(",");
        for (String pair : pairs) {
            String keyValue[] = pair.split("=");
            myMap.put(keyValue[0], keyValue[1]);
        }

        // Spécifier au WS que c'est du JSON qu'on lui envoie
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        JSONObject requestParams = new JSONObject();

        String finalAdress = null;

        switch (address) {
            case "animal":
                // Si animalId n'est pas null on est pas le cas d'un PUT. Sinon c'est un POST --> Pas d'id à indiquer
                if (animalId != null) {
                    requestParams.put("id", animalId);
                }

                // get today's current date
                java.util.Date date = new java.util.Date();
                java.sql.Date today = new java.sql.Date(date.getTime());

                requestParams.put("species", myMap.get("species"));
                requestParams.put("race", myMap.get("race"));
                requestParams.put("birthDate", myMap.get("birthDate"));
                requestParams.put("name", myMap.get("name"));
                requestParams.put("sexe", myMap.get("sexe"));
                requestParams.put("arrivalDate", today.toString());

                finalAdress = "/" + address;
                break;

            case "care":
                // Si careId n'est pas null on est pas le cas d'un PUT. Sinon c'est un POST --> Pas d'id à indiquer
                if (careId != null) {
                    requestParams.put("id", careId);
                }

                requestParams.put("examen", myMap.get("examen"));
                requestParams.put("examenDate", myMap.get("date"));
                requestParams.put("examenTime", myMap.get("heure"));

                finalAdress = "/animal/" + animalId + "/veterinary/" + veterinaryId + "/" + address;
                break;

            case "veterinary":
            case "host":
            case "adoptive":
                requestParams.put("firstName", myMap.get("firstName"));
                requestParams.put("lastName", myMap.get("lastName"));
                requestParams.put("phoneNumber", myMap.get("phoneNumber"));
                requestParams.put("zipCode", myMap.get("zipCode"));
                requestParams.put("city", myMap.get("city"));
                requestParams.put("streetName", myMap.get("streetName"));
                requestParams.put("streetNumber", myMap.get("streetNum"));


                // Si veterinaryId n'est pas null on est pas le cas d'un PUT. Sinon c'est un POST --> Pas d'id à indiquer
                if (address.equals("veterinary") && veterinaryId != null) {
                    requestParams.put("id", veterinaryId);
                }

                // Si hostId n'est pas null on est pas le cas d'un PUT. Sinon c'est un POST --> Pas d'id à indiquer
                if (address.equals("host") && hostId != null) {
                    requestParams.put("id", hostId);
                }

                // Si adoptive n'est pas null on est pas le cas d'un PUT. Sinon c'est un POST --> Pas d'id à indiquer
                if (address.equals("adoptive") && adoptiveId != null) {
                    requestParams.put("id", adoptiveId);
                }

                finalAdress = "/" + address;
                break;
        }

        if (method.equals("POST")) {
            response = request.body(requestParams.toJSONString()).post(finalAdress);
        } else {
            response = request.body(requestParams.toJSONString()).put(finalAdress);
        }
        jsonString = response.asString();
    }


    // ######################################################### THEN #########################################################

    @Then("^la réponse renvoie un code (\\d{3})$")
    public void compareStatusCode(String code_str) {
        int code = Integer.parseInt(code_str);
        Assert.assertEquals(code, response.getStatusCode());

    }

    @And("^dans la réponse, l'id est égal à l'id d(?:e|u) (?:la )?(l'animal|vétérinaire|famille d'accueil|famille d'adoption) généré[e]?$")
    public void compareId(String entity) {
        int generatedId = 0;

        switch (entity) {
            case "l'animal":
                generatedId = Integer.parseInt(animalId);
                break;
            case "vétérinaire":
                generatedId = Integer.parseInt(veterinaryId);
                break;
            case "famille d'accueil":
                generatedId = Integer.parseInt(hostId);
                break;
            case "famille d'adoption":
                generatedId = Integer.parseInt(adoptiveId);
        }

        int responseId = JsonPath.from(jsonString).get("id");
        Assert.assertEquals(generatedId, responseId);

    }


    @And("^dans la réponse, le nombre d'objets est égal à (\\d)$")
    public void compareListSize(String size_str) {
        int size = Integer.parseInt(size_str);
        List<Integer> objects = JsonPath.from(jsonString).get("id");
        Assert.assertEquals(size, objects.size());
        objects = null;
    }

    @And("^dans la réponse, l(?:e|a) (nom|date de naissance|type) d(?:e|u) (?:la )?(l'animal|soin|vétérinaire|famille) est: (\\d{4}-\\d{2}-\\d{2}|\\w*)")
    public void compareName(String param, String entity, String newValue) {
        String actualValue = null;

        switch (entity) {
            case "l'animal":
                if (param.equals("nom")) {
                    actualValue = JsonPath.from(jsonString).get("name");

                } else {
                    actualValue = JsonPath.from(jsonString).get("birthDate").toString().substring(0, 9);
                    newValue = newValue.substring(0, 9);
                }
                break;
            case "soin":
                actualValue = JsonPath.from(jsonString).get("examen");
                break;
            case "vétérinaire":
            case "famille":
                actualValue = JsonPath.from(jsonString).get("lastName");
                break;
        }
        Assert.assertEquals(newValue, actualValue);

    }

    @And("^dans la réponse, l'id d(?:e|u) (?:la )?(l'animal|soin|vétérinaire|famille d'accueil|famille d'adoption) créé[e]? est présent")
    public void findId(String entity) {
        Boolean containsId = false;

        switch (entity) {
            case "l'animal":
                containsId = jsonString.contains(animalId);
                break;
            case "soin":
                containsId = jsonString.contains(careId);
                break;
            case "vétérinaire":
                containsId = jsonString.contains(veterinaryId);
                break;
            case "famille d'accueil":
                containsId = jsonString.contains(hostId);
                break;
            case "famille d'adoption":
                containsId = jsonString.contains(adoptiveId);
                break;
        }
        Assert.assertTrue(containsId);
    }

    @And("^dans la base de données, l(?:e |'|a )(nom|examen|famille d'acceuil|famille d'adoption): (\\w*) n'existe plus$")
    public void searchName(String param, String value) {

        try {
            myConnex = ConnectionDB.give_connection();
            ResultSet rs = null;
            PreparedStatement prepStmt = null;


            switch (param) {
                case "nom":
                    //Creating a Statement object
                    prepStmt = myConnex.prepareStatement("select * from animal where name =?");
                    break;
                case "examen":
                    //Creating a Statement object
                    prepStmt = myConnex.prepareStatement("select * from veterinary_care where examen =?");
                    break;
                case "famille d'acceuil":
                    //Creating a Statement object
                    prepStmt = myConnex.prepareStatement("select * from host_family where last_name =?");
                    break;
                case "famille d'adoption":
                    //Creating a Statement object
                    prepStmt = myConnex.prepareStatement("select * from adoptive_family where last_name =?");
                    break;
            }

            prepStmt.setString(1, value);
            //Retrieving the data
            rs = prepStmt.executeQuery();

            // If rs.next() == False, rs is empty.
            Assert.assertFalse(rs.next());

            myConnex.close();

        } catch (SQLException excep) {
            excep.printStackTrace();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
    }
}
