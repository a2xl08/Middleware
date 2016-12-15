/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package enterprise.service;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import javax.persistence.Query;
import javax.transaction.UserTransaction;

import model.Event;

@Stateless
public class StatelessSessionBean implements StatelessSession {

	@Resource
	private EJBContext context;
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	@Override
	public void printEvents() {
		
		Query query = em.createNamedQuery("All");
		ArrayList<Event> events = (ArrayList<Event>) query.getResultList();
		for (Event event : events){
			System.out.println("Nom : "+event.getNom());
			System.out.println("Artiste : "+event.getArtiste());
			System.out.println("Date : "+event.getDate());
			System.out.println("Prix : "+event.regularprice());
			boolean[] tab = event.isComplete();
			if (tab[0]){
				System.out.println("EVENEMEMT COMPLET !");
			}
		}
		
		
		
	}

	@Override
	public void printCategories(String name) {
		Query query = em.createNamedQuery("EventByName");
		query.setParameter("nom", name);
		Event event = (Event) query.getSingleResult();
			if (name == event.getNom()){
				boolean[] tab = event.isComplete();
				if (!(tab[1])){
					System.out.println("Categorie A : "+event.price("A1"));
				} else {
					System.out.println("Categorie A : COMPLET");
				}
				if (!(tab[2])){
					System.out.println("Categorie B : "+event.price("B1"));
				} else {
					System.out.println("Categorie B : COMPLET");
				}
				if (!(tab[3])){
					System.out.println("Categorie C : "+event.price("C1"));
				} else {
					System.out.println("Categorie C : COMPLET");
				}
				if (!(tab[4])){
					System.out.println("Categorie D : "+event.price("D1"));
				} else {
					System.out.println("Categorie D : COMPLET");
				}
				return;
			}
		System.out.println("Error 404 : Not found !");
		return;
		
	}

	@Override
	public void choosePlace(String name, String place, int compte) {
		Query query = em.createNamedQuery("EventByName");
		query.setParameter("nom", name);
		Event event = (Event) query.getSingleResult();
			if (name == event.getNom()){
				event.reservation(place);
				return;
			}
		System.out.println("Error 404 : Not found !");
		return;
	}

	@Override
	public void printPlaces(String name, String categplace) {
		Query query = em.createNamedQuery("EventByName");
		query.setParameter("nom", name);
		Event event = (Event) query.getSingleResult();
			if (name == event.getNom()){
				Integer index = 0;
				switch(categplace){
				case "B" : 
					index=1;
				case "C" : 
					index=2;
				case "D" : 
					index=3;
				}
				for (int k=0; k<event.getPlaces().get(index).size(); k++){
					System.out.print(categplace+" "+k+" : ");
					if (event.getPlaces().get(index).get(k)){
						System.out.println("DISPONIBLE");
					} else {
						System.out.println("COMPLET");
					}
				}
				return;
			}
		System.out.println("Error 404 : Not found !");
		return;
	}

    

}
