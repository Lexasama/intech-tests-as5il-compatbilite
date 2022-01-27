## Enoncé du TP noté ##

Règles de gestion : 

- RG_Compta_1	: Le solde d'un compte comptable est égal à la somme des montants au débit des lignes d'écriture diminuées de la somme des montants au crédit. Si le résultat est positif, le solde est dit "débiteur", si le résultat est négatif le solde est dit "créditeur".
- RG_Compta_2	: Pour qu'une écriture comptable soit valide, elle doit être équilibrée : la somme des montants au crédit des lignes d'écriture doit être égale à la somme des montants au débit.
- RG_Compta_3	: Une écriture comptable doit contenir au moins deux lignes d'écriture : une au débit et une au crédit.
- RG_Compta_4	: Les montants des lignes d'écriture sont signés et peuvent prendre des valeurs négatives (même si cela est peu fréquent).
- RG_Compta_5	: La référence d'une écriture comptable est composée du code du journal dans lequel figure l'écriture suivi de l'année et d'un numéro de séquence (propre à chaque journal) sur 5 chiffres incrémenté automatiquement à chaque écriture. Le formatage de la référence est : XX-AAAA/#####.
-- Ex : Journal de banque (BQ), écriture au 31/12/2016
-- --> BQ-2016/00001
- RG_Compta_6	: La référence d'une écriture comptable doit être unique, il n'est pas possible de créer plusieurs écritures ayant la même référence.
- RG_Compta_7	: Les montants des lignes d'écritures peuvent comporter 2 chiffres maximum après la virgule.

Tâches à effectuer :

- Corriger le code correspondant aux tests existants qui échouent (un commit par erreur dans le code detectée)
- Implémenter les tests unitaires de la couche service (avec Mock si nécessaire)
- Implémenter les tests d'intégration de la couche service avec la couche repositories
- Complèter le code de la méthode checkEcritureComptableUnit et écrire le test correspondant
- Implémenter la méthode addReference en appliquant la méthodologie TDD (commit du/des tests puis commit du code)
- Mettre en place une configuration pour séparer les données de production et les données de tests
- Ajouter le plugin Jacoco pour obtenir le coverage
- Implémenter un controller REST qui permettra d'ajouter, de modifier et de supprimer une écriture comptable et écrire les tests fonctionnels associés

Rendu :

- Envoyer à romain.sessa@ext.esiea.fr
- Objet du mail : A5S - Tests automatiques
- Corps du mail : Lien github avec un message gentil :)
- A envoyer le 27/01/2022
