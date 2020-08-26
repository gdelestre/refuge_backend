Feature: Gestion des vétérinaires dans la base de données

  Scenario: Récupérer un vétérinaire via son id
    Given un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    When j'envoi au WS veterinary un GET avec en paramètre l'id du vétérinaire créé
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id est égal à l'id du vétérinaire généré

  Scenario: Récupérer la liste de tous les vétérinaire
    Given un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And un second vétérinaire crée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And un dernier vétérinaire crée avec les paramètres, prénom: Luc, nom: Durand, numéro de téléphone: 0304050607, code postal: 24150, ville: Lalinde, nom de rue: place_du_marche, numéro de rue: 7
    When j'envoi au WS veterinary un GET sans paramètre
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 3

  Scenario: Insérer un nouveau vétérinaire dans la base de données
    #Given un nouveau vétérinaire à insérer dans la base données
    When j'envoi au WS veterinary un POST avec dans le corps du WS, les paramètres suivants: {firstName=Jean,lastName=Dupond,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue de la gare,streetNum=24}
    Then  la réponse renvoie un code 200
    And dans la réponse, le nom du vétérinaire est: Dupond

  Scenario: Modifier un vétérinaire existant dans la base de données
    Given un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    When j'envoi au WS veterinary un PUT avec dans le corps du WS, id: idGénéré et les paramètres suivants: {firstName=Jean,lastName=Dupont,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue de la gare,streetNum=24}
    Then  la réponse renvoie un code 200
    And dans la réponse, le nom du vétérinaire est: Dupont
    And dans la base de données, le nom: Dupond n'existe plus

  Scenario: Supprimer un vétérinaire à partir de son id
    Given un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    When j'envoi au WS veterinary un DELETE avec en paramètre l'id du vétérinaire créé
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id du vétérinaire créé est présent

  Scenario: Insérer un nouveau vétérinaire qui a le même numéro de téléphone qu'un autre dans la base de données
    Given j'envoi au WS veterinary un POST avec dans le corps du WS, les paramètres suivants: {firstName=Jean,lastName=Dupond,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue de la gare,streetNum=24}
    When j'envoi au WS veterinary un POST avec dans le corps du WS, les paramètres suivants: {firstName=Mary,lastName=Durand,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue principale,streetNum=14}
    Then la réponse renvoie un code 403