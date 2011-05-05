/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projetgenielogiciel;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author HAMADACITY
 */
public class ReseauTest {

       Station t1 = new Station("A", 3);
       Station t2 = new Station("B", 4);
       Station t3 = new Station("C", 5);
       Station t4 = new Station("D", 5);
       Station t5 = new Station("E", 5);
       Station t6 = new Station("F", 6);
       Station t7 = new Station("G", 16);
       

     
    public ReseauTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllStations method, of class Reseau.
     */
    @Test
    public void testGetAllStations() {
        System.out.println("getAllStations");
      /*  Reseau instance = null;
        LinkedList expResult = null;
        LinkedList result = instance.getAllStations();
        assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getStartStation method, of class Reseau.
     */
    @Test
    public void testGetStartStation() {
        System.out.println("getStartStation");
        /*Reseau instance = null;
        Station expResult = null;
        Station result = instance.getStartStation();
        assertEquals(expResult, result);
        */
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of getVisiteStations method, of class Reseau.
     */
    @Test
    public void testGetVisiteStations() {
        System.out.println("getVisiteStations");
       /* Reseau instance = null;
        LinkedList expResult = null;
        LinkedList result = instance.getVisiteStations();
        assertEquals(expResult, result);
       */
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of setAllStations method, of class Reseau.
     */
    @Test
    public void testSetAllStations() {
        System.out.println("setAllStations");
        /*
        LinkedList<Station> allStations = null;
        Reseau instance = null;
        instance.setAllStations(allStations);*/
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of setStartStation method, of class Reseau.
     */
    @Test
    public void testSetStartStation() {
        System.out.println("setStartStation");
        /*Station startStation = null;
        Reseau instance = null;
        instance.setStartStation(startStation);
        */
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of setVisiteStations method, of class Reseau.
     */
    @Test
    public void testSetVisiteStations() {
        /*
        System.out.println("setVisiteStations");
        LinkedList<Integer> visiteStations = null;
        Reseau instance = null;
        instance.setVisiteStations(visiteStations);
        */
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of ajouterLigne method, of class Reseau.
     */
    @Test
    public void testAjouterLigne() {
       /*
        System.out.println("ajouterLigne");
        Ligne l = null;
        Reseau instance = null;
        instance.ajouterLigne(l);
        */
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of setReseau method, of class Reseau.
     */
    @Test
    public void testSetReseau() {
        System.out.println("setReseau");
        /*Reseau instance = null;
        instance.setReseau();
        */
         // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of afficherReseau method, of class Reseau.
     */
    @Test
    public void testAfficherReseau() {
        /* System.out.println("afficherReseau");
      
        Reseau instance = null;
        instance.afficherReseau();
        */
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of affichageLigne method, of class Reseau.
     */
    @Test
    public void testAffichageLigne() {
        System.out.println("affichageLigne");
        /*LinkedList<Ligne> L = null;
        Reseau instance = null;
        instance.affichageLigne(L);
        */
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of afficherLigne2 method, of class Reseau.
     */
    @Test
    public void testAfficherLigne2() {
        System.out.println("afficherLigne2");
      /*  Trajet T = null;
        Reseau instance = null;
        instance.afficherLigne2(T);
       */
       // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of changerMoins method, of class Reseau.
     */
    @Test
    public void testChangerMoins() {
        System.out.println("changerMoins");
       t1.addAdjacent( new Adjacent(t2,10) );
       t2.addAdjacent(new Adjacent(t1,10));

       t1.addAdjacent(new Adjacent(t3,11));
       t3.addAdjacent(new Adjacent(t1,11));

       t2.addAdjacent( new Adjacent(t3,10));
       t3.addAdjacent( new Adjacent(t2,10));

       t3.addAdjacent( new Adjacent(t4,12));
       t4.addAdjacent(new Adjacent(t3,12));

       t1.addAdjacent( new Adjacent(t4,15));
       t4.addAdjacent(new Adjacent(t1,15));

        t5.addAdjacent( new Adjacent(t3,15));
        t3.addAdjacent(new Adjacent(t5,15));

        t6.addAdjacent( new Adjacent(t3,15));
        t3.addAdjacent(new Adjacent(t6,15));

        t7.addAdjacent( new Adjacent(t1,15));
        t1.addAdjacent(new Adjacent(t7,15));

       LinkedList<Station> liste = new LinkedList<Station>();
       liste.add(t1);
       liste.add(t2);
       liste.add(t3);
       liste.add(t5);

        Ligne A =new Ligne(1,"TER1",12);
        A.setListStations(liste);
        //Ligne A = null;
       LinkedList<Station> liste2 = new LinkedList<Station>();
       liste2.add(t3);
       liste2.add(t6);
       liste2.add(t4);
       Ligne l = new Ligne(1,"TER1",12);
       Ligne B =new Ligne(2,"TER2",20);
       B.setListStations(liste2);
        //Ligne B = null;
        Reseau R1 =new Reseau(t1);
        
        LinkedList<Trajet> t= new LinkedList<Trajet>();
        Trajet trajet = new Trajet();
        Ligne D ;
        Ligne C =new Ligne(2,"TER1",20);
        trajet.setLigne1(C);
        t.add(trajet);
       // trajet.setLigne2(D);
        
        /*LinkedList<Trajet> attendu =new LinkedList<Trajet>();
        R1.ajouterLigne(A);
        R1.ajouterLigne(B);
        attendu = R1.MoinsDeChangement(t1, t5);
        assertEquals(t, attendu);*/






        //Station A = null;
        //Station B = null;
        //Reseau instance = null;
        //instance.changerMoins(A, B);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of commun method, of class Reseau.
     */
    @Test
    public void testCommun() {
        System.out.println("commun");

       t1.addAdjacent( new Adjacent(t2,10) );
       t2.addAdjacent(new Adjacent(t1,10));

       t1.addAdjacent(new Adjacent(t3,11));
       t3.addAdjacent(new Adjacent(t1,11));

       t2.addAdjacent( new Adjacent(t3,10));
       t3.addAdjacent( new Adjacent(t2,10));

       t3.addAdjacent( new Adjacent(t4,12));
       t4.addAdjacent(new Adjacent(t3,12));

       t1.addAdjacent( new Adjacent(t4,15));
       t4.addAdjacent(new Adjacent(t1,15));

        t5.addAdjacent( new Adjacent(t3,15));
        t3.addAdjacent(new Adjacent(t5,15));

        t6.addAdjacent( new Adjacent(t3,15));
        t3.addAdjacent(new Adjacent(t6,15));

        t7.addAdjacent( new Adjacent(t1,15));
        t1.addAdjacent(new Adjacent(t7,15));

       LinkedList<Station> liste = new LinkedList<Station>();
       liste.add(t1);
       liste.add(t2);
       liste.add(t3);
       liste.add(t5);

        Ligne A =new Ligne(1,"TER1",12);
        A.setListStations(liste);
        //Ligne A = null;
       LinkedList<Station> liste2 = new LinkedList<Station>();
       liste2.add(t3);
       liste2.add(t6);
       liste2.add(t4);

       Ligne B =new Ligne(2,"TER2",20);
       B.setListStations(liste2);
        //Ligne B = null;
       Reseau R1 =new Reseau(t1);
       R1.ajouterLigne(A);
       R1.ajouterLigne(B);
       LinkedList<Station> expResult = new LinkedList<Station>();
       expResult.add(t3);
       //LinkedList expResult = null;
       LinkedList<Station> result = R1.commun(A, B);
       assertEquals(expResult, result);
       //assertSame(expResult, result);
     //  assertTrue(expResult.retainAll(result));
      
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

}