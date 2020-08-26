Feature: Gestion des familles d'acceuil dans la base de données

  Scenario: Récupérer une famille d'accueil via son id
    Given une famille d'accueil créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    When j'envoi au WS host un GET avec en paramètre l'id de la famille créée
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id est égal à l'id de la famille d'accueil générée

  Scenario: Récupérer la liste de toutes les familles d'accueil
    Given une famille d'accueil créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And une seconde famille d'accueil créée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And une derniere famille d'accueil créée avec les paramètres, prénom: Luc, nom: Durand, numéro de téléphone: 0304050607, code postal: 24150, ville: Lalinde, nom de rue: place_du_marche, numéro de rue: 7
    When j'envoi au WS host un GET sans paramètre
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 3

  Scenario: Insérer une nouvelle famille d'accueil dans la base de données
    #Given une nouvelle famille d'accueil à insérer dans la base données
    When j'envoi au WS host un POST avec dans le corps du WS, les paramètres suivants: {firstName=Jean,lastName=Dupond,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue de la gare,streetNum=24}
    Then  la réponse renvoie un code 200
    And dans la réponse, le nom de la famille est: Dupond

  Scenario: Modifier une famille d'accueil existant dans la base de données
    Given une famille d'accueil créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    When j'envoi au WS host un PUT avec dans le corps du WS, id: idGénéré et les paramètres suivants: {firstName=Jean,lastName=Dupont,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue de la gare,streetNum=24}
    Then  la réponse renvoie un code 200
    And dans la réponse, le nom de la famille est: Dupont
    And dans la base de données, la famille d'acceuil: Dupond n'existe plus

  Scenario: Supprimer une famille d'accueil à partir de son id
    Given une famille d'accueil créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    When j'envoi au WS host un DELETE avec en paramètre l'id de la famille créée
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id de la famille d'accueil créée est présent

  Scenario: Insérer une famille d'accueil qui a le même numéro de téléphone qu'une autre dans la base de données
    Given j'envoi au WS host un POST avec dans le corps du WS, les paramètres suivants: {firstName=Jean,lastName=Dupond,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue de la gare,streetNum=24}
    When j'envoi au WS host un POST avec dans le corps du WS, les paramètres suivants: {firstName=Mary,lastName=Durand,phoneNumber=0102030405,zipCode=24150,city=Lalinde,streetName=rue principale,streetNum=14}
    Then la réponse renvoie un code 403