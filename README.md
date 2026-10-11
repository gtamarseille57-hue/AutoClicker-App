# AutoClicker App

Application Android de démonstration pour :
- clics automatiques / IA assist
- menu flottant
- enregistrement de mouvements
- exécution des séquences enregistrées
- intégration Shizuku pour les permissions système
- demande des autorisations de superposition et d’overlay

## Fonctionnalités prévues

- Bouton de démarrage dans l’écran principal
- Menu flottant overlay pouvant être montré/masqué
- Onglet IA Assist : bot de clics rapides configurable selon une vitesse en ms ou en clics/s
- Onglet Enregistrement : bouton rouge pour enregistrer les actions sur l’écran
- Onglet Bibliothèque : liste des enregistrements, suppression, renommage, exécution
- Support Shizuku pour les tâches qui demandent des privilèges système
- Demandes des permissions :
  - `SYSTEM_ALERT_WINDOW`
  - `FOREGROUND_SERVICE`
  - `BIND_ACCESSIBILITY_SERVICE`

## Structure principale

- `app/src/main/java/com/example/autoclicker/MainActivity.kt` : écran principal
- `app/src/main/java/com/example/autoclicker/FloatingMenuService.kt` : service de menu flottant
- `app/src/main/java/com/example/autoclicker/assist/AssistController.kt` : contrôleur IA assist
- `app/src/main/java/com/example/autoclicker/recording/RecordingManager.kt` : enregistrement JSON
- `app/src/main/java/com/example/autoclicker/shizuku/ShizukuManager.kt` : gestion Shizuku
- `app/src/main/java/com/example/autoclicker/accessibility/AutoClickAccessibilityService.kt` : service d’accessibilité

## Vérification avant compilation

1. Ouvrir le projet dans Android Studio.
2. Synchroniser Gradle.
3. Vérifier l’installation des SDK Android.
4. Lancer le projet sur un émulateur ou un appareil réel.

## Permissions requises

- Overlay / afficher sur d’autres applications
- Accessibilité
- Shizuku activé sur l’appareil
- Autorisation de fond / foreground service

## Limitation importante

Cette version est un squelette fonctionnel prêt à étendre. Pour un comportement complet de click automation sur écran, il faut ajouter les appels d’accessibilité/UiAutomation/Shizuku et la logique de simulation d’entrées utilisateur sur l’écran ciblé. Le code est structuré pour permettre cette extension.

## À venir

- Ecran de gestion des enregistrements avec RecyclerView/Compose
- Interface de menu à onglets
- Prévisualisation des captures
- Exécution répéter
- Intégration avancée de Shizuku et de l’accessibilité
