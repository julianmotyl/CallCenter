package com.nosql.redis;

import java.util.Random;


public class Appel {
	String identifiant;
	String heure;
	String numero;
	String status;
	String duree;
	String operateur;
	String texte;
	

	public Appel(Operateur[] operateurs) {
		int max = 9;
		int min = 0;
		Random random = new Random();
		this.numero = "0" + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min);
		this.identifiant = (random.nextInt(max - min + 1) + min) + this.numero + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min);
		this.heure = (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min) + "h" + (random.nextInt(max - min + 1) + min) + (random.nextInt(max - min + 1) + min);
		this.duree = "0" + (random.nextInt(max - 6 - min + 1) + min) + (random.nextInt(max - min + 1) + min);
		int status =  (random.nextInt(max - 6 - min + 1) + min);
		switch (status) {
		case 0: 
			this.status = "Non affecté";
			break;
		case 1: 
			this.status = "Non pris en compte";
			break;
		case 2: 
			this.status = "En cours";
			break;
		default:
			this.status = "Terminé";
			break;
		}
		
		if (this.status.equals("En cours") || this.status.equals("Terminé") ) {
			int choix = random.nextInt(operateurs.length);
			this.operateur = operateurs[choix].identifiant;
		}
		this.texte = "Texte par défaut";
		
	}


	public void show() {
		System.out.println(this.identifiant);
		System.out.println(this.heure);
		System.out.println(this.numero);
		System.out.println(this.duree);
		System.out.println(this.operateur);
		System.out.println(this.texte);
	}
}
