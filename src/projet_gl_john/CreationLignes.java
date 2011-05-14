import java.util.LinkedList;
import 

/**
 * Classe pour creer le reseau de metro
 * @author Comma, Diallo, Minatchy, Ngatcha
 */
public class CreationLignes {

    
    /**
     * Methode qui initialise une ligne des stations et des informations qu'elle doit contenir, en fonction de son numero qui est passe en parametre et renvoit cette ligne
     * @param numero Numero de la ligne qu'on veut initialiser
     * @return Ligne initialisée
     */
    public static Ligne initialiserLigne (int numero) {

        Ligne ligne = null; //Ligne a renvoyer
        LinkedList<Station> listStations = new LinkedList<Station>(); //Creation de la liste de stations a ajouter a la ligne

        /* Creation des stations */
        Station chateauDeVincennes = new Station ("Chateau de Vincennes", 3, false);
        Station berault = new Station ("Berault", 4, false);
        Station saintMande = new Station ("Saint Mande", 2, false);
        Station porteDeVincennes = new Station ("Porte de Vincennes", 3, false);
        Station nation = new Station ("Nation", 2, false);
        Station reuillyDiderot = new Station ("Reuilly-Diderot", 4, false);
        Station gareDeLyon = new Station ("Gare de Lyon", 1, false);
        Station bastille = new Station ("Bastille", 5, false);
        Station stPaul = new Station ("St-Paul", 6, false);
        Station hotelDeVille = new Station ("Hotel de Ville", 2, false);
        Station chatelet = new Station ("Chatelet", 1, false);
        Station louvreRivoli = new Station ("Louvre Rivoli", 3, false);
        Station palaisRoyalMuseeDuLouvre = new Station ("Palais royal - Musee du Louvre", 2, false);
        Station tuileries = new Station ("Tuileries", 6, false);
        Station concorde = new Station ("Concorde", 3, false);
        Station champsElyseesClemenceau = new Station ("Champs Elysees - Clemenceau", 2, false);
        Station franklinDRoosevelt = new Station ("Franklin D. Roosevelt", 7, false);
        Station georgeV = new Station ("georgeV", 3, false);
        Station charlesDeGaulleEtoile = new Station ("Charles de Gaulle Etoile", 1, false);
        Station argentine = new Station ("Argentine", 10, false);
        Station porteMaillot = new Station ("Porte Maillot", 4, false);
        Station lesSablons = new Station ("Les Sablons", 3, false);
        Station pontDeNeuilly = new Station ("Pont de Neuilly", 2, false);
        Station esplanadeDeLaDefense = new Station ("Esplanade de la Defense", 2, false);
        Station laDefense = new Station ("La Defense", 1, false);
        Station porteDeClignancourt = new Station ("Porte de Clignancourt", 2, false);
        Station simplon = new Station ("Simplon", 3, false);
        Station marcadetPoissonniers = new Station ("Marcadet Poissonniers", 5, false);
        Station chateauRouge = new Station ("Chateau Rouge", 6, false);
        Station barbesRochechouart = new Station ("Barbes Rochechouart", 4, false);
        Station gareDuNord = new Station ("Gare du Nord", 2, false);
        Station gareDeLEst = new Station ("Gare de l'Est", 2, false);
        Station chateauDEau = new Station ("Chateau d'Eau", 6, false);
        Station strasbourgSaintDenis = new Station ("Strasbourg Saint-Denis", 3, false);
        Station reaumurSebastopol = new Station ("Reaumur Sebastopol", 6, false);
        Station etienneMarcel = new Station ("Etienne Marcel", 5, false);
        Station lesHalles = new Station ("Les Halles", 2, false);
        Station cite = new Station ("Cite", 4, false);
        Station stMichel = new Station ("St-Michel", 6, false);
        Station odeon = new Station ("Odeon", 5, false);
        Station saintGermainDesPres = new Station ("Saint Germain des-Pres", 2, false);
        Station saintSulpice = new Station ("Saint-Sulpice", 3, false);
        Station stPlacide = new Station ("St-Placide", 8, false);
        Station montparnasseBienvenue = new Station ("Montparnasse Bienvenue", 3, false);
        Station valvin = new Station ("Valvin", 2, false);
        Station raspail = new Station ("Raspail", 1, false);
        Station denfertRochereau = new Station ("Denfert Rochereau", 1, false);
        Station moutonDuvernet = new Station ("Mouton Duvernet", 4, false);
        Station alesia = new Station ("Alesia", 4, false);
        Station porteDOrleans = new Station ("Porte d'Orleans", 9, false);
        Station balard = new Station ("Balard", 7, false);
        Station lourmel = new Station ("Lourmel", 4, false);
        Station boucicaut = new Station ("Boucicaut", 3, false);
        Station felixFaure = new Station ("Felix Faure", 2, false);
        Station commerce = new Station ("Commerce", 7, false);
        Station laMottePicquetGrenelle = new Station ("La Motte Picquet Grenelle", 2, false);
        Station ecoleMilitaire = new Station ("Ecole Militaire", 1, false);
        Station laTourMaubourg = new Station ("La Tour Maubourg", 3, false);
        Station invalides = new Station ("Invalides", 5, false);
        Station madeleine = new Station ("Madeleine", 6, false);
        Station opera = new Station ("Opera", 2, false);
        Station richelieuDrouot = new Station ("Richelieu Drouot", 2, false);
        Station grandsBoulevards = new Station ("Grands Boulevards", 4, false);
        Station bonneNouvelle = new Station ("Bonne Nouvelle", 6, false);
        Station republique = new Station ("Republique", 8, false);
        Station fillesDuCalvaire = new Station ("Filles du Calvaire", 2, false);
        Station saintSebastienFroissart = new Station ("St-Sebastien Froissart", 7, false);
        Station cheminVert = new Station ("Chemin Vert", 6, false);
        Station ledruRollin = new Station ("Ledru-Rollin", 3, false);
        Station faidherbeChaligny = new Station ("Faidherbe Chaligny", 2, false);
        Station montgallet = new Station ("Montgallet", 1, false);
        Station daumesnil = new Station ("Daumesnil", 2, false);
        Station michelBizot = new Station ("Michel Bizot", 8, false);
        Station porteDoree = new Station ("Porte Doree", 9, false);
        Station porteDeCharenton = new Station ("Porte de Charenton", 5, false);
        Station liberte = new Station ("Liberte", 3, false);
        Station charentonEcoles = new Station ("Charenton-Ecoles", 2, false);
        Station ecoleVeterinaireDeMaisonsAlfort = new Station ("Ecole Veterinaire de Maisons-Alfort", 3, false);
        Station maisonsAlfortStade = new Station ("Maisons-Alfort-Stade", 4, false);
        Station maisonsAlfortLesJuilliotes = new Station ("Maisons-Alfort Les Juilliotes", 5, false);
        Station creteilLEchat = new Station ("Creteil L'Echat", 7, false);
        Station creteilUniversite = new Station ("Creteil-Universite", 2, false);
        Station creteilPrefecture = new Station ("Creteil-Prefecture", 5, false);
        Station creteilPointeDuLac = new Station ("Creteil Pointe du Lac", 4, false);
        Station porteDeLaChapelle = new Station ("Porte de la Chapelle", 1, false);
        Station marxDormoy = new Station ("Marx Dormoy", 3, false);
        Station julesJoffrin = new Station ("Jules Joffrin", 2, false);
        Station lamarckCaulaincourt = new Station ("Lamarck Caulaincourt", 1, false);
        Station abbesses = new Station ("Abbesses", 10, false);
        Station pigalle = new Station ("Pigalle", 8, false);
        Station saintGeorges = new Station ("Saint-Georges", 4, false);
        Station notreDameDeLorette = new Station ("Notre-Dame de-Lorette", 5, false);
        Station triniteDEstienneDOrves = new Station ("Trinite d'Estienne d'Orves", 1, false);
        Station assembleeNationale = new Station ("Assemblee Nationale", 1, false);
        Station solferino = new Station ("Solferino", 9, false);
        Station rueDuBac = new Station ("Rue du Bac", 5, false);
        Station sevresBabylone = new Station ("Sevres Babylone", 3, false);
        Station rennes = new Station ("Rennes", 2, false);
        Station notreDameDesChamps = new Station ("Notre-Dame des-Champs", 4, false);
        Station falguiere = new Station ("Falguiere", 5, false);
        Station pasteur = new Station ("Pasteur", 2, false);
        Station volontaires = new Station ("Volontaires", 10, false);
        Station vaugirard = new Station ("Vaugirard", 3, false);
        Station convention = new Station ("Convention", 2, false);
        Station porteDeVersailles = new Station ("Porte de Versailles", 1, false);
        Station corentinCelton = new Station ("Corentin Celton", 5, false);
        Station mairieDIssy = new Station ("Mairie d'Issy", 6, false);
        Station saintLazare = new Station ("Saint-Lazare", 9, false);
        Station pyramides = new Station ("Pyramides", 8, false);
        Station bercy = new Station ("Bercy", 4, false);
        Station courStEmilion = new Station ("Cour St-Emilion", 2, false);
        Station bibliothequeFrMitterrand = new Station ("Bibliotheque Fr. Mitterrand", 3, false);
        Station olympiades = new Station ("Olympiades", 5, false);

        switch (numero) {
            case 1: //Si c'est la ligne 1 qu'on veut initialiser
                ligne = new Ligne(1, "Ligne 1", 24); //Creation de la ligne

                listStations.add(chateauDeVincennes);/*ligne.add(this.getStation("Chateau de Vincennes"));*/
                listStations.add(berault);
                listStations.add(saintMande);
                listStations.add(porteDeVincennes);
                listStations.add(nation);
                listStations.add(reuillyDiderot);
                listStations.add(gareDeLyon);
                listStations.add(bastille);
                listStations.add(stPaul);
                listStations.add(hotelDeVille);
                listStations.add(chatelet);
                listStations.add(louvreRivoli);
                listStations.add(palaisRoyalMuseeDuLouvre);
                listStations.add(tuileries);
                listStations.add(concorde);
                listStations.add(champsElyseesClemenceau);
                listStations.add(franklinDRoosevelt);
                listStations.add(georgeV);
                listStations.add(charlesDeGaulleEtoile);
                listStations.add(argentine);
                listStations.add(porteMaillot);
                listStations.add(lesSablons);
                listStations.add(pontDeNeuilly);
                listStations.add(esplanadeDeLaDefense);
                listStations.add(laDefense);

                ligne.setListStations(listStations);
                break;

            case 4: //Si c'est la ligne 4 qu'on veut initialiser
                ligne = new Ligne(4, "Ligne 4", 26); //Creation de la ligne

                listStations.add(porteDeClignancourt);
                listStations.add(simplon);
                listStations.add(marcadetPoissonniers);
                listStations.add(chateauRouge);
                listStations.add(barbesRochechouart);
                listStations.add(gareDuNord);
                listStations.add(gareDeLEst);
                listStations.add(chateauDEau);
                listStations.add(strasbourgSaintDenis);
                listStations.add(reaumurSebastopol);
                listStations.add(etienneMarcel);
                listStations.add(lesHalles);
                listStations.add(chatelet);
                listStations.add(cite);
                listStations.add(stMichel);
                listStations.add(odeon);
                listStations.add(saintGermainDesPres);
                listStations.add(saintSulpice);
                listStations.add(stPlacide);
                listStations.add(montparnasseBienvenue);
                listStations.add(valvin);
                listStations.add(raspail);
                listStations.add(denfertRochereau);
                listStations.add(moutonDuvernet);
                listStations.add(alesia);
                listStations.add(porteDOrleans);

                ligne.setListStations(listStations);
                break;

            case 8:
                ligne = new Ligne(8, "Ligne 8", 40); //Creation de la ligne

                ligne.getListStation
                listStations.add(balard);
                listStations.add(lourmel);
                listStations.add(boucicaut);
                listStations.add(felixFaure);
                listStations.add(commerce);
                listStations.add(laMottePicquetGrenelle);
                listStations.add(ecoleMilitaire);
                listStations.add(laTourMaubourg);
                listStations.add(invalides);
                listStations.add(concorde);
                listStations.add(madeleine);
                listStations.add(opera);
                listStations.add(richelieuDrouot);
                listStations.add(grandsBoulevards);
                listStations.add(bonneNouvelle);
                listStations.add(strasbourgSaintDenis);
                listStations.add(republique);
                listStations.add(fillesDuCalvaire);
                listStations.add(saintSebastienFroissart);
                listStations.add(cheminVert);
                listStations.add(bastille);
                listStations.add(ledruRollin);
                listStations.add(faidherbeChaligny);
                listStations.add(reuillyDiderot);
                listStations.add(montgallet);
                listStations.add(daumesnil);
                listStations.add(michelBizot);
                listStations.add(porteDoree);
                listStations.add(porteDeCharenton);
                listStations.add(liberte);
                listStations.add(charentonEcoles);
                listStations.add(ecoleVeterinaireDeMaisonsAlfort);
                listStations.add(maisonsAlfortStade);
                listStations.add(maisonsAlfortLesJuilliotes);
                listStations.add(creteilLEchat);
                listStations.add(creteilUniversite);
                listStations.add(creteilPrefecture);
                listStations.add(creteilPointeDuLac);

                ligne.setListStations(listStations);
                break;

            case 12:
                ligne = new Ligne(12, "Ligne 12", 30); //Creation de la ligne

                listStations.add(porteDeLaChapelle);
                listStations.add(marxDormoy);
                listStations.add(marcadetPoissonniers);
                listStations.add(julesJoffrin);
                listStations.add(lamarckCaulaincourt);
                listStations.add(abbesses);
                listStations.add(pigalle);
                listStations.add(saintGeorges);
                listStations.add(notreDameDeLorette);
                listStations.add(triniteDEstienneDOrves);
                listStations.add(saintLazare);
                listStations.add(madeleine);
                listStations.add(concorde);
                listStations.add(assembleeNationale);
                listStations.add(solferino);
                listStations.add(rueDuBac);
                listStations.add(sevresBabylone);
                listStations.add(rennes);
                listStations.add(notreDameDesChamps);
                listStations.add(montparnasseBienvenue);
                listStations.add(falguiere);
                listStations.add(pasteur);
                listStations.add(volontaires);
                listStations.add(vaugirard);
                listStations.add(convention);
                listStations.add(porteDeVersailles);
                listStations.add(corentinCelton);
                listStations.add(mairieDIssy);

                ligne.setListStations(listStations);
                break;

            case 14:
            ligne = new Ligne(14, "Ligne 14", 10); //Creation de la ligne

            listStations.add(saintLazare);
            listStations.add(madeleine);
            listStations.add(pyramides);
            listStations.add(chatelet);
            listStations.add(gareDeLyon);
            listStations.add(bercy);
            listStations.add(courStEmilion);
            listStations.add(bibliothequeFrMitterrand);
            listStations.add(olympiades);

            ligne.setListStations(listStations);
                break;
        }

        return ligne;
    }

}
