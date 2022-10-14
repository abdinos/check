# PROJET GL M2 IDL
## Description du projet 
Le jeu d'échecs, ou les échecs, est un jeu de société opposant deux joueurs de part et d'autre d'un tablier appelé « échiquier » composé de soixante-quatre cases, 32 claires et 32 sombres nommées les cases blanches et les cases noires. <br/>
Le jeu consiste à mettre l'autre joueur échec et mat, c'est-à-dire que son roi est en prise et qu'il n'y a aucun coup possible pour l'en sortir.<br/>

## La Stack utilisée
<ul>
 <li>JAVA</li>
 <li>Swing pour l'interface graphique</li>
</ul>


## Pré-requis pour le bon fonctionnement du projet
-Mettre à jour le système Ubuntu
 <ul>
    <li> Ouvrez un terminal depuis le lanceur ou le menu d'applications. </li>
 <li> Rentrez la commande <strong> sudo apt update && sudo apt upgrade -y </strong>. </li>
 </ul>
 -Installer Java JDK 17 - Méthode manuelle
 <ul>
    <li> Installer des packages de dépendances : <strong> sudo apt install libc6-i386 libc6-x32 curl -y </strong> </li>
   <li> Télécharger la dernière version de Java 17 : <strong> curl  -O https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz </strong> et        <strong> sudo apt install curl -y </strong> puis <strong> tar -xvf jdk-17_linux-x64_bin.tar.gz </strong> </li>
   <li> Configurer et installer Java 17 : <strong> sudo mv jdk-17.{version} /opt/jdk17 </strong> </li>
   <li> Vous devez maintenant définir les variables d'environnement comme ci-dessous : <strong> export JAVA_HOME=/opt/jdk17 </strong> et
     <strong> export PATH=$PATH:$JAVA_HOME/bin </strong> </li>
   <li> Java 17 est maintenant installé. Pour confirmer, utilisez les commandes suivantes : <strong>java --version
echo $JAVA_HOME </strong> </li>
 </ul>

## Pour lancer le projet
<ul>
 <li> git clone https://github.com/abdinos/chess.git </li>
 <li> </li>
</ul>

## Réalisé par :
<ul>
 <li>Loic FERY : <a href="https://github.com/loicfery"> https://github.com/loicfery </a> </li>
 <li>Yanis AMROUCHE <a href="https://github.com/yanisamrouche">https://github.com/yanisamrouche </a></li>
 <li>Farouk AGUENI <a href="https://github.com/agueni">https://github.com/agueni </a> (a19028598) </li>
 <li>Abdessettar OULD-CHIBANI <a href="https://github.com/abdinos">https://github.com/abdinos </a> </li>
</ul>

Enjoy :)
