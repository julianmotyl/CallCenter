package com.nosql.redis;

import java.util.HashMap;
import java.util.Scanner;
import java.lang.System;


import redis.clients.jedis.Jedis;

public class RedisConnection {

	public static void main(String[] args) throws Exception{

		try {
			//Création d'une connexion avec la base redis
			Jedis jedis = new Jedis("localhost");
			System.out.println("Connexion réussit !");
			
			//Création des opérateurs du call center avec un ID, un nom / prenom et leur ancienneté
			Operateur operateur0= new Operateur("JY05191", "Yves-Saint-Laurent", "Jean", "3");
			Operateur operateur1 = new Operateur("JS37815", "Smith", "John", "1");
			Operateur operateur2 = new Operateur("LX40130", "Xin", "Li", "4");
			Operateur operateur3 = new Operateur("MB94835", "Benkeshida", "Mohamed", "2");
			
			//Création d'un tableau d'operateur pour les passer en paramètre du constructeur d'appels
			Operateur operateurs[] = {operateur0, operateur1, operateur2, operateur3};
			
			//Création des appels
			for (int i = 0; i < 100; i++) {
				Appel appel = new Appel(operateurs);
				appel2Redis(jedis, appel);
			}
			System.out.println("Création et Indexation des appels réussit !");

			
			
			//Création des Opérateur de la même façon que pour les appels
			for (int i = 0; i < operateurs.length; i++) {
				HashMap<String, String> hashOperateurs = new HashMap<String, String>();
				hashOperateurs.put("Identifiant", operateurs[i].identifiant);
				hashOperateurs.put("nom", operateurs[i].nom);
				hashOperateurs.put("prenom", operateurs[i].prenom);
				hashOperateurs.put("anciennete", operateurs[i].anciennete);
				jedis.hmset("Operateur:" + operateurs[i].identifiant,hashOperateurs);				
			}
			System.out.println("Création des opérateurs réussit");
			
			menu(jedis);
			jedis.close();
			
			
		} catch (Exception e) {

			System.out.println(e);
		}

	}
	
	public static void menu(Jedis jedis) {
		boolean quitter = false;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Que souhaitez vous faire ? \n (1) Afficher les appel en cours de traitement ? \n (2) Afficher les appel à affecter ? \n (3) Generer un nouvel appel  \n (4) Affecter un operateur à un appel \n (5) Quitter");
			int choix = sc.nextInt();
			switch (choix) {
			case 1:
				afficheTraitement(jedis);
				break;
			case 2:
				afficheAffecter(jedis);
				break;
			case 3:
				generationAppel(jedis);
				break;
			case 4:
				affecterOperateur(jedis);
				break;
			case 5:
				quitter = true;
				break;
			default:
				break;
			}
		} while (!quitter);

	}
	
	public static void afficheTraitement(Jedis jedis) {
		System.out.println(jedis.smembers("StatusAppel:En cours")); //Affiche tous les id des appels en cours de traitement
	}
	
	public static void afficheAffecter(Jedis jedis) {
		System.out.println(jedis.smembers("StatusAppel:Non affecté")); //Affiche tous les id des appels à affecter
	}
	
	public static void generationAppel(Jedis jedis) {
		Scanner sc = new Scanner(System.in);
 
		System.out.println("ID ?");
		String id = sc.nextLine();
		System.out.println("Numero ?");
		String numero = sc.nextLine();
		System.out.println("heure ?");
		String heure = sc.nextLine();
		System.out.println("Status");
		String status = sc.nextLine();
		System.out.println("Duree ?");
		String duree = sc.nextLine();
		System.out.println("Opérateur ?");
		String operateur = sc.nextLine();
		System.out.println("Texte ?");
		String texte = sc.nextLine();
		Appel appel = new Appel(id,numero,heure, status, duree, operateur, texte);
		appel2Redis(jedis, appel);
				
	}
	
	public static void affecterOperateur(Jedis jedis) {
		
	}
	
	public static void appel2Redis(Jedis jedis, Appel appel) {
		//L'appel est mis dans une hashMap pour pouvoir l'inserer dans la base avec la commande HMSET
		HashMap<String,String> appels = new HashMap<String, String>();
		appels.put("Identifiant", appel.identifiant);
		appels.put("Heure", appel.heure);
		appels.put("Duree", appel.duree);
		appels.put("Numero", appel.numero);
		appels.put("Status", appel.status);
		appels.put("Texte", appel.texte);
		
		//Associe un opérateur dans le cas ou il y en a un dans l'objet pour ne pas avoir de champs null
		if (appel.operateur != null) {
			appels.put("Operateur", appel.operateur);
			jedis.hmset("Appel:"+ appel.identifiant , appels); //Créer l'appel
			if (appel.status == "En cours") {
				jedis.sadd("OperateurAppelEnCour:" + appel.operateur, appel.identifiant); //Indexations dans un set en fonction de l'opérateur (si l'appel est en cour)
			}
		} else {
			jedis.hmset("Appel:"+ appel.identifiant , appels);
		}
		jedis.sadd("StatusAppel:"+appel.status, appel.identifiant ); //Indexations dans un set en fonction du status
		
	}
}
