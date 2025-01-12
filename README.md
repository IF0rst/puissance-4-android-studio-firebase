Petite Précision: J'ai décidé d'enlever le système de tours sur le jeu pour simplifier les test. En revanche, la logique et le placement des pions se fait normalement.

Il peut y avoir jusqu'a 2 joueurs en même temps sur la partie, le reste deviendront des spectateurs.

Liste des fonctionnalités implémentées :
- [X] Utilisation de l'Api Preference (pour les paramètres)
- [X] Ecriture/lecture dans un Fichier (pour les résultats des match)
- [ ] Utilisation de SQLite
- [ ] Utilisation de Room
- [X] Utilisation de Firebase (Pour le lien entre les clients)
- [X] Nombre d'activités ou fragment supérieur ou égal à 3
- [X] Gestion du bouton Back (message pour confirmer que l'on veut réellement quitter l'application)
- [X] L'affichage d'une liste avec son adapter
- [ ] L'affichage d'une liste avec un custom adapter (avec gestion d’événement)
- [X] La pertinence d'utilisation des layouts (L'application doit être responsive et supporter: portrait/paysage et tablette)
- [ ] L'utilisation de d’événement améliorant l'ux (pex: swipe). Préciser : J'ai décidé d'utiliser Paint2D pour les crédits afin de valider cette compétence, j'ai donc crée une classe qui etends le Canvas pour ajouter une logique de défilement
- [X] La réalisation de composant graphique custom (Paint 2D, Calendrier,...) Préciser :
- [X] Les taches en background (codage du démarrage d'un thread)
- [X] Le codage d'un menu (contextuel ou non, utilisation d'un menu en resource XML)
- [X] L'application de pattern (Reactive programming, singleton, MVC,...) Liste : MVC (J'ai une classe pour la vue du jeu qui n'utilise aucune logique et va simplement s'abonner a la mise a jour du plateau dans la clase dédiée au jeu)
