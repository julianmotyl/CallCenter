package com.nosql.redis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisConnection {

	public static void main(String[] args) throws Exception{

		try {
			Jedis jedis = new Jedis("localhost");
			System.out.println("Connexion réussit !");
//			System.out.println("Informations du serveur :" + jedis.info());
			
			Operateur operateur0= new Operateur("JY05191", "Yves-Saint-Laurent", "Jean", "3");
			Operateur operateur1 = new Operateur("JS37815", "Smith", "John", "1");
			Operateur operateur2 = new Operateur("LX40130", "Xin", "Li", "4");
			Operateur operateur3 = new Operateur("MB94835", "Benkeshida", "Mohamed", "2");
			
			Operateur operateurs[] = {operateur0, operateur1, operateur2, operateur3};

			for (int i = 0; i < 2000; i++) {
				Appel appel = new Appel(operateurs);
				appel.show();
				HashMap<String,String> appels = new HashMap<String, String>();
				appels.put("Identifiant", appel.identifiant);
				appels.put("Heure", appel.heure);
				appels.put("Numero", appel.numero);
				appels.put("Status", appel.status);
				appels.put("Texte", appel.texte);
				
				if (appel.operateur != null) {
					appels.put("Operateur", appel.operateur);
					jedis.hmset("Call:"+ appel.identifiant , appels);

				} else {
					jedis.hmset("Call:"+ appel.identifiant , appels);
				}
				appels.remove();
			}
			
			for (int i = 0; i < operateurs.length; i++) {
				HashMap<String, String> hashOperateurs = new HashMap<String, String>();
				hashOperateurs.put("Identifiant", operateurs[i].identifiant);
				hashOperateurs.put("nom", operateurs[i].nom);
				hashOperateurs.put("prenom", operateurs[i].prenom);
				hashOperateurs.put("anciennete", operateurs[i].anciennete);
				jedis.hmset("Operateur:" + operateurs[i].identifiant,hashOperateurs);				
			}
		} catch (Exception e) {

			System.out.println(e);
		}
	}

}
