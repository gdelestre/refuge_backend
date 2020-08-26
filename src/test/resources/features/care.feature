Feature: Gestion des soins vétérinaires dans la base de données

  Scenario: Récupérer la liste des soins à partir de l'Id de l'animal
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: male
    And un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-08-10, heure: 10:30
    And un second soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: castration, date: 2020-09-11, heure: 08:30
    And un dernier soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: controle, date: 2020-09-18, heure: 11:30

    And un second animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2014-08-15, name: Shanna, sexe: femelle
    And un autre vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: sterelisation, date: 2020-08-11, heure: 14:30
    And un second soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-09-11, heure: 09:00

    When j'envoi au WS care un GET avec en paramètre l'id du dernier animal créé
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 2


  Scenario: Récupérer la liste de tous les soins
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: male
    And un vétérinaire crée avec les paramètres, prénom: Jean, nom: Dupond, numéro de téléphone: 0102030405, code postal: 24150, ville: Lalinde, nom de rue: rue_de_la_gare, numéro de rue: 23
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-08-10, heure: 10:30
    And un second soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: castration, date: 2020-09-11, heure: 08:30
    And un dernier soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: controle, date: 2020-09-18, heure: 11:30

    And un second animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2014-08-15, name: Shanna, sexe: femelle
    And un second vétérinaire crée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: sterelisation, date: 2020-08-11, heure: 14:30
    And un second soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-09-11, heure: 09:00
    And un dernier soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: controle, date: 2020-09-18, heure: 11:00

    When j'envoi au WS care un GET sans paramètre
    Then  la réponse renvoie un code 200
    And dans la réponse, le nombre d'objets est égal à 6

  Scenario: Insérer un nouveau soin vétérinaire dans la base de données pour un animal
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: male
    And un vétérinaire crée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    When j'envoi au WS care un POST avec dans le corps du WS, les paramètres suivants: {examen=sterelisation,date=2020-08-11,heure=14:30}
    Then  la réponse renvoie un code 200
    And dans la réponse, le type de soin est: sterelisation

  Scenario: Modifier un soin pour un animal
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: male
    And un vétérinaire crée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-08-10, heure: 10:30
    When j'envoi au WS care un PUT avec dans le corps du WS, id: idGénéré et les paramètres suivants: {examen=sterelisation,date=2020-08-10,heure=10:00}
    Then  la réponse renvoie un code 200
    And dans la réponse, le type de soin est: sterelisation
    And dans la base de données, l'examen: vaccination n'existe plus

  Scenario: Supprimer un soin à partir de son id
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: male
    And un vétérinaire crée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-08-10, heure: 10:30
    When j'envoi au WS care un DELETE avec en paramètre l'id du soin créé
    Then  la réponse renvoie un code 200
    And dans la réponse, l'id du soin créé est présent

  Scenario: Supprimer tous les soins pour un animal
    Given un animal crée avec les paramètres, species: Chat, race: europeen, date de naissance: 2015-04-21, name: Jarvos, sexe: male
    And un vétérinaire crée avec les paramètres, prénom: Marie, nom: Lebrun, numéro de téléphone: 0203040506, code postal: 24150, ville: Lalinde, nom de rue: rue_nationale, numéro de rue: 13
    And un soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: vaccination, date: 2020-08-10, heure: 10:30
    And un second soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: castration, date: 2020-09-11, heure: 08:30
    And un dernier soin crée avec ce vétérinaire pour cet animal avec les paramètres, examen: controle, date: 2020-09-18, heure: 11:30

    When j'envoi au WS animal/id/care un DELETE avec en paramètre l'id de l'animal
    Then  la réponse renvoie un code 200



