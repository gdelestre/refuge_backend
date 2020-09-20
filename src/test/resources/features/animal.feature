Feature: Gestion des animaux dans la base de données

  Scenario: Récupérer un animal via son id
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    When j'envoi au WS animal un GET avec en paramètre l'id de l'animal créé
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id est égal à l'id de l'animal généré

  Scenario: Récupérer la liste de tous les animaux
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    And un second animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2014-08-15, name: Shanna, sexe: Femelle
    And un dernier animal crée avec les paramètres, species: Chien, race: berger belge, date de naissance: 2010-09-05, name: Eden, sexe: Male
    When j'envoi au WS animal un GET sans paramètre
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 3


  Scenario: Insérer un nouvel animal dans la base de données
    #Given un nouvel animal à insérer dans la base données
    When j'envoi au WS animal un POST avec dans le corps du WS, les paramètres suivants: {species=Chat,race=europeen,birthDate=2015-04-21,name=Jarvos,sexe=Male}
    Then  la réponse renvoie un code 200
    And dans la réponse, le nom de l'animal est: Jarvos

  Scenario: Supprimer un animal à partir de son id
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    When j'envoi au WS animal un DELETE avec en paramètre l'id de l'animal créé
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id de l'animal créé est présent

  Scenario: Modifier un animal existant dans la base de données
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    When j'envoi au WS animal un PUT avec dans le corps du WS, id: idGénéré et les paramètres suivants: {species=Chat,race=europeen,birthDate=2014-03-11,name=Felix,sexe=Male}
    Then  la réponse renvoie un code 200
    And dans la réponse, le nom de l'animal est: Felix
    And dans la réponse, la date de naissance de l'animal est: 2014-03-11
    And dans la base de données, le nom: Jarvos n'existe plus

  Scenario: Supprimer un animal qui possède un ou plusieurs soins à partir de son id
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    And un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-08-10, heure: 10:30
    When j'envoi au WS animal un DELETE avec en paramètre l'id de l'animal créé
    Then la réponse renvoie un code 403

  Scenario: Insérer un nouvel animal qui a le même nom qu'un autre dans la base de données
    Given j'envoi au WS animal un POST avec dans le corps du WS, les paramètres suivants: {species=Chat,race=europeen,birthDate=2015-04-21,name=Jarvos,sexe=Male}
    When j'envoi au WS animal un POST avec dans le corps du WS, les paramètres suivants: {species=Chat,race=goutiere,birthDate=2013-12-21,name=Jarvos,sexe=Male}
    Then la réponse renvoie un code 403


  Scenario: Récupérer la liste des animaux en famille d'acceuil
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    And un autre animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2014-08-15, name: Shanna, sexe: Femelle
    And une famille d'accueil créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And cette famille qui accueille Jarvos
    And cette famille qui accueille Shanna
    And un autre animal crée avec les paramètres, species: Chat, race: persan, date de naissance: 2012-06-12, name: Cristal, sexe: Femelle
    And un autre animal crée avec les paramètres, species: Chat, race: persan, date de naissance: 2015-02-11, name: Berlioz, sexe: Male
    And une seconde famille d'accueil créée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And cette famille qui accueille Cristal
    And cette famille qui accueille Berlioz
    And un autre animal crée avec les paramètres, species: Chien, race: berger belge, date de naissance: 2010-09-05, name: Eden, sexe: Male
    And un autre animal crée avec les paramètres, species: Chien, race: croise, date de naissance: 2015-05-10, name: Nouk, sexe: Femelle
    Given une famille d'adoption créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And cette famille qui adopte Nouk
    When j'envoi au WS animal/host un GET sans paramètre
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 4

  Scenario: Récupérer la liste des animaux adoptés
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: Male
    And un autre animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2014-08-15, name: Shanna, sexe: Femelle
    And une famille d'adoption créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And cette famille qui adopte Jarvos
    And cette famille qui adopte Shanna
    And un autre animal crée avec les paramètres, species: Chat, race: persan, date de naissance: 2012-06-12, name: Cristal, sexe: Femelle
    And un autre animal crée avec les paramètres, species: Chat, race: persan, date de naissance: 2015-02-11, name: Berlioz, sexe: Male
    And une seconde famille d'accueil créée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And cette famille qui adopte Cristal
    And cette famille qui adopte Berlioz
    And un autre animal crée avec les paramètres, species: Chien, race: berger belge, date de naissance: 2010-09-05, name: Eden, sexe: Male
    And un autre animal crée avec les paramètres, species: Chien, race: croise, date de naissance: 2015-05-10, name: Nouk, sexe: Femelle
    Given une famille d'accueil créée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And cette famille qui accueille Nouk
    When j'envoi au WS animal/adoptive un GET sans paramètre
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 4









