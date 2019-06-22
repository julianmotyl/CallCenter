package com.nosql.redis;

public class Operateur {
	String identifiant;
	String nom;
	String prenom;
	String anciennete;
	
	public Operateur(String id, String nom, String prenom, String anciennete) {
		this.identifiant = id;
		this.nom = nom;
		this.prenom = prenom;
		this.anciennete = anciennete;
	}
}
