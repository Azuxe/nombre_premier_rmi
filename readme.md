Architecture client, multiples serveurs permettant de calculer les nombres premiers.

Guide d'utilisation : 

Exécuter le script "script" qui va s'occuper de compiler le code et de créer le stub.

A exécuter sur un premier terminal : java -Djava.security.policy=java.policy Serveur

Remplacer postgres_username par votre nom d'utilisateur et postgres_password par votre mot de passe.
A exécuter sur un second terminal : java -Djava.security.policy=java.policy -cp .:postgresql-42.2.5.jar Client postgres_username postgres_password