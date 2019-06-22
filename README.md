# CallCenter
Simulation d'un call center avec Redis

### Remplissage de la base
La base est remplit avec un programme JAVA. 
Il comporte deux types d'objets :
* Des ***Call*** qui comportent
 * "Identifiant" 
 * "Operateur"
 * "Texte"
 * "Heure"
 * "Status"
 * "Numero"
* Des Opérateur qui comportent :
 * "prenom"
 * "Identifiant"
 * "anciennete"
 * "nom"




### Scripts demandés 

##### Ensemble des appels en cours 
Pour pouvoir afficher tous les appels en cours il à fallut indexer les appels avec la clé **"Status"** en utilisant: 
```
SADD StatusAppel:"Status"  appel
```
##### Ensemble des appels en cours de traitement, par opérateur
Lors de la création des Appels, nous avons choisit de faire filter 



##### Affecter un appel


```
HSETNX
```
