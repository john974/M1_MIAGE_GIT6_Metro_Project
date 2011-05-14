/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonathan
 */
public class ReseauTest {

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
     * Test of setReseau method, of class Reseau.
     */
    @Test
    public void testSetReseau() {
        System.out.println("Test de : public void setReseau()");
        Reseau reseauDeTest = new Reseau();

        Station chateauDeVincennes = new Station ("Chateau", 3, false);
        Station nation = new Station ("Nation", 2, false);
        Station reuillyDiderot = new Station ("ReuillyDiderot", 4, false);

        chateauDeVincennes.addAdjacent(new Adjacent(nation, 1.0));
        nation.addAdjacent(new Adjacent(reuillyDiderot, 4.0));

        reseauDeTest.setStartStation(chateauDeVincennes);

        reseauDeTest.setReseau();

        assertEquals(chateauDeVincennes, reseauDeTest.getAllStations().get(0));
        assertEquals(nation, reseauDeTest.getAllStations().get(1));
        assertEquals(reuillyDiderot, reseauDeTest.getAllStations().get(2));

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of ajouter method, of class Reseau.
     */
    @Test
    public void testAjouter() {
        System.out.println("ajouter");
        Station s = null;
        Reseau instance = new Reseau();
        instance.ajouter(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isVisited method, of class Reseau.
     */
    @Test
    public void testIsVisited() {
        System.out.println("isVisited");
        Station s = null;
        Reseau instance = new Reseau();
        boolean expResult = false;
        boolean result = instance.isVisited(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of afficher method, of class Reseau.
     */
    @Test
    public void testAfficher() {
        System.out.println("afficher");
        Reseau instance = new Reseau();
        instance.afficher();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStation method, of class Reseau.
     */
    @Test
    public void testGetStation_Station() {
        System.out.println("getStation");
        Station s = null;
        Reseau instance = new Reseau();
        Station expResult = null;
        Station result = instance.getStation(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStation method, of class Reseau.
     */
    @Test
    public void testGetStation_String() {
        System.out.println("getStation");
        String nom = "";
        Reseau instance = new Reseau();
        Station expResult = null;
        Station result = instance.getStation(nom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valideChemin method, of class Reseau.
     */
    @Test
    public void testValideChemin() {
        System.out.println("valideChemin");
        Station debut = null;
        Station fin = null;
        Reseau instance = new Reseau();
        boolean expResult = false;
        boolean result = instance.valideChemin(debut, fin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valide method, of class Reseau.
     */
    @Test
    public void testValide() {
        System.out.println("valide");
        LinkedList<String> tmp = null;
        Reseau instance = new Reseau();
        boolean expResult = false;
        //boolean result = instance.valide(tmp);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChemin method, of class Reseau.
     */
    @Test
    public void testGetChemin_Station_Station() {
        System.out.println("getChemin");
        Station debut = null;
        Station fin = null;
        Reseau instance = new Reseau();
        Station expResult = null;
        Station result = instance.getChemin(debut, fin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class Reseau.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        Station s = null;
        Reseau instance = new Reseau();
        double expResult = 0.0;
        double result = instance.getCost(s);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createLines method, of class Reseau.
     */
    @Test
    public void testCreateLines() {
        System.out.println("createLines");
        Reseau instance = new Reseau();
        instance.createLines();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createStations method, of class Reseau.
     */
    @Test
    public void testCreateStations() {
        System.out.println("Test de : public void createStations()");
        Reseau reseauDeTest = new Reseau();
        reseauDeTest.createStations();

        //reseauDeTest.afficher();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class Reseau.
     */
    @Test
    public void testInit() {
        System.out.println("Test de : public void init()");
        Reseau reseauDeTest = new Reseau();
        reseauDeTest.init();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of plusCourtChemin method, of class Reseau.
     */
    @Test
    public void testPlusCourtChemin_String_String() {
        System.out.println("plusCourtChemin");
        String d = "";
        String a = "";
        Reseau instance = new Reseau();
        instance.plusCourtChemin(d, a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of plusCourtChemin method, of class Reseau.
     */
    @Test
    public void testPlusCourtChemin_LinkedList() {
        System.out.println("plusCourtChemin");
        LinkedList<String> ensemble = null;
        Reseau instance = new Reseau();
        instance.plusCourtChemin(ensemble);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLigne method, of class Reseau.
     */
    @Test
    public void testGetLigne() {
        System.out.println("getLigne");
        LinkedList<Station> ls = null;
        Reseau instance = new Reseau();
        LinkedList expResult = null;
        LinkedList result = instance.getLigne(ls);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChemin method, of class Reseau.
     */
    @Test
    public void testGetChemin_Station() {
        System.out.println("getChemin");
        Station end = null;
        Reseau instance = new Reseau();
        LinkedList expResult = null;
        LinkedList result = instance.getChemin(end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLignes method, of class Reseau.
     */
    @Test
    public void testGetLignes() {
        System.out.println("getLignes");
        Station end = null;
        Reseau instance = new Reseau();
        instance.getLignes(end);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoute method, of class Reseau.
     */
    @Test
    public void testGetRoute() {
        System.out.println("getRoute");
        LinkedList<Chemin> route = null;
        Station end = null;
        Reseau instance = new Reseau();
        //instance.getRoute(route, end);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStationLine method, of class Reseau.
     */
    @Test
    public void testGetStationLine() {
        System.out.println("getStationLine");
        LinkedList<Chemin> route = null;
        Station s = null;
        Reseau instance = new Reseau();
        Ligne expResult = null;
        //Ligne result = instance.getStationLine(route, s);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Reseau.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        LinkedList<Chemin> route = null;
        Chemin ch = null;
        Reseau instance = new Reseau();
        instance.update(route, ch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MoinsDeChangement method, of class Reseau.
     */
    @Test
    public void testMoinsDeChangement() {
        System.out.println("MoinsDeChangement");
        Station A = null;
        Station B = null;
        Reseau instance = new Reseau();
        LinkedList expResult = null;
        LinkedList result = instance.MoinsDeChangement(A, B);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of afficherTrajet method, of class Reseau.
     */
    @Test
    public void testAfficherTrajet() {
        System.out.println("afficherTrajet");
        Trajet T = null;
        Reseau instance = new Reseau();
        instance.afficherTrajet(T);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of commun method, of class Reseau.
     */
    @Test
    public void testCommun() {
        System.out.println("commun");
        Ligne A = null;
        Ligne B = null;
        Reseau instance = new Reseau();
        LinkedList expResult = null;
        LinkedList result = instance.commun(A, B);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}