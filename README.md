# CalculatriceWS : Service web SOAP avec Jakarta XML Web Services

## Informations

| | |
|---|---|
| **Nom et prénom** | AMRI Mohamed Amine |
| **Filière** | LSI3-GLSI |
| **Matière** | Architecture SOA et services web |
| **TP** | TP2 : Mise en oeuvre de Jakarta XML Web Services |
| **Établissement** | Faculté des Sciences de Sfax, Département d'informatique et des Communications |
| **Année universitaire** | 2025-2026 |
| **Enseignantes** | Corinne Amel ZAYANI, Hela Hakim, Rim Mohamed |

---

## 1. Objectifs

Au terme de ce TP, l'objectif est de :

- Créer un service web SOAP en Java avec Jakarta XML Web Services (JAX-WS), intégré comme dépendance Maven.
- Découvrir la structure de données générée (fichier XSD).
- Annoter le code pour exposer le service web.
- Publier le service sur une URL.
- Tester les opérations avec SoapUI.

L'application **CalculatriceWS** expose quatre opérations : **somme**, **soustraction**, **multiplication** et **division**.

---

## 2. Structure du projet

```
CalculatriceWS
├── pom.xml
├── README.md
└── src/main/java
    ├── service
    │   ├── ICalculatrice.java     # Interface : le contrat du service
    │   └── Calculatrice.java      # Classe métier : l'implémentation
    └── serveur
        └── ServeurJWS.java        # Publication du service (Endpoint.publish)
```

### Dépendances Maven (`pom.xml`)

| Dépendance | Rôle |
|---|---|
| `jakarta.xml.ws-api` | Interfaces JAX-WS (ex. `Endpoint`) |
| `jakarta.jws-api` | Annotations `@WebService`, `@WebMethod`, `@WebParam` |
| `jaxws-rt` | Implémentation JAX-WS : moteur SOAP et serveur HTTP intégré |

### Classes

- **`ICalculatrice`** : interface annotée `@WebService`. Elle définit les 4 opérations (contrat).
- **`Calculatrice`** : classe qui implémente l'interface. Elle contient la logique de calcul. La division par zéro lève une exception, transformée en SOAP Fault.
- **`ServeurJWS`** : classe avec `main` qui publie le service :
  ```java
  Endpoint.publish("http://localhost:8080/calculatrice", new Calculatrice());
  ```

### Exécution

1. Importer le projet : `File > Import > Maven > Existing Maven Projects`.
2. Clic droit sur `ServeurJWS.java` > `Run As > Java Application`.
3. Le service est disponible sur :
   - Service : `http://localhost:8080/calculatrice`
   - WSDL : `http://localhost:8080/calculatrice?wsdl`
   - XSD : `http://localhost:8080/calculatrice?xsd=1`

### Test avec SoapUI

1. `File > New SOAP Project`, avec comme Initial WSDL `http://localhost:8080/calculatrice?wsdl`.
2. Ouvrir `somme > Request 1`, saisir `a = 5` et `b = 3`.
3. Exécuter : la réponse attendue est `<return>8.0</return>`.
4. Tester `division` avec `b = 0` : le service renvoie un SOAP Fault « Division par zéro impossible ».
<img width="1273" height="556" alt="image" src="https://github.com/user-attachments/assets/b9381888-cc04-4443-9d84-db02559abaae" />
<img width="1323" height="586" alt="image" src="https://github.com/user-attachments/assets/4ca56fcf-f66d-4b3c-95b9-b4fb55b587c7" />
<img width="1402" height="687" alt="image" src="https://github.com/user-attachments/assets/9891f89d-1b07-4095-8032-56abd841d6df" />



---

## 3. Explication des concepts

### Service web
Application accessible à distance via HTTP, qui échange des messages XML. Elle est indépendante du langage et de la plateforme.

### SOAP (Simple Object Access Protocol)
Protocole d'échange de messages XML. Un message SOAP contient :
- **Envelope** : l'enveloppe, racine du message.
- **Header** : en-tête optionnel (sécurité, authentification).
- **Body** : le contenu (requête ou réponse).
- **Fault** : message d'erreur standardisé.

### WSDL (Web Services Description Language)
Le **contrat** du service, au format XML. Il décrit les opérations, les messages, le protocole et l'adresse.

| Élément | Rôle |
|---|---|
| `types` | Structure des données (XSD) |
| `message` | Messages d'entrée et de sortie |
| `portType` | Opérations abstraites (correspond à `ICalculatrice`) |
| `binding` | Protocole et format (SOAP over HTTP) |
| `service` / `port` | Adresse du service |

### XSD (XML Schema Definition)
Décrit la structure et les types des données échangées. Il permet de valider les messages.

Correspondance entre le code Java et le XSD généré :

| Java | XSD |
|---|---|
| Méthode `somme` | Éléments `somme` et `sommeResponse` |
| `@WebParam(name="a") double a` | `<xs:element name="a" type="xs:double"/>` |
| Valeur de retour `double` | `<xs:element name="return" type="xs:double"/>` |
| `targetNamespace` | `targetNamespace="http://service/"` |

### Jakarta XML Web Services (JAX-WS)
API Java standard pour créer et consommer des services SOAP. Elle convertit automatiquement les objets Java en XML et génère le WSDL et le XSD. Les versions 2.3.x utilisent le namespace `javax.*`, les versions 3.x et plus utilisent `jakarta.*`.

### Annotations utilisées

| Annotation | Rôle |
|---|---|
| `@WebService` | Déclare une classe ou interface comme service web (`serviceName`, `portName`, `targetNamespace`, `endpointInterface`) |
| `@WebMethod` | Expose une méthode comme opération du service |
| `@WebParam` | Donne un nom lisible aux paramètres (sinon `arg0`, `arg1`) |

### Endpoint.publish
Démarre un serveur HTTP léger et publie le service sur une URL, sans serveur d'applications externe (type Tomcat).

### Approche code-first
Le code Java est écrit en premier, puis le WSDL est généré automatiquement. L'approche inverse, contract-first, part du WSDL pour générer le code.

### SOAP Fault
Mécanisme standard de gestion d'erreur. L'exception Java levée lors d'une division par zéro est transmise au client sous forme de `<soap:Fault>`.

### Maven
Outil de gestion de projet et de dépendances. Le fichier `pom.xml` déclare les bibliothèques, téléchargées automatiquement depuis Maven Central.

### SoapUI
Outil de test de services web. Il lit le WSDL, génère des requêtes d'exemple et affiche les réponses.

---

## 4. Conclusion

Ce TP m'a permis de mettre en oeuvre un service web SOAP complet : configuration d'un projet Maven, création de la classe métier, annotation avec JAX-WS, publication avec `Endpoint.publish`, puis test avec SoapUI.

J'ai compris le lien entre la classe Java et le contrat généré : l'interface devient le `portType`, les méthodes deviennent des opérations, et les paramètres deviennent des éléments du XSD. J'ai aussi vu comment une exception Java est transformée en SOAP Fault.

Cette approche code-first est simple et rapide pour développer un service, et le WSDL permet à n'importe quel client, quel que soit son langage, de l'utiliser. Ce TP constitue une bonne base pour aborder ensuite le développement de clients SOAP et la comparaison avec les services REST.
