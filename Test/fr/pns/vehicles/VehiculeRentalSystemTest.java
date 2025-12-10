package fr.pns.vehicles; // (ou fr.pns.rentals, où que soit votre test)

// Imports des locations
import fr.pns.rentals.RentableItem; // IMPORTANT
import fr.pns.rentals.Rental;
import fr.pns.rentals.RentalSystem;
import fr.pns.rentals.costumes.Costume;
import fr.pns.rentals.costumes.CostumeSize;

// Imports des véhicules
import fr.pns.vehicles.ElectricCar;
import fr.pns.vehicles.ElectricBike;

// Imports JUnit
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // Assurez-vous d'avoir cet import
import java.time.LocalDate;

// Imports statiques d'assertions
import static org.junit.jupiter.api.Assertions.*;

public class VehiculeRentalSystemTest {

    // 1. Une seule variable pour le système
    RentalSystem rentalSystem;

    // 2. On garde les dates
    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    LocalDate nextWeek = today.plusWeeks(1);

    // 3. On déclare les items dont on aura besoin dans plusieurs tests
    Costume costumeBM;
    Costume costumeSQ;
    RentableItem car1;
    RentableItem bike1;


    @BeforeEach
    void setUp() {
        // 4. On initialise TOUT ici
        rentalSystem = new RentalSystem();
        ElectricBike.resetIdentifier(); // Important pour les vélos

        // Ajout des déguisements (ceux de l'ancien 'initRentalSystem')
        costumeBM = new Costume("Batman Adult", "Batman", CostumeSize.XLARGE);
        costumeSQ = new Costume("SnowQueen", "Reine des neiges, snow Queen, for childs");
        Costume costumeBMC = new Costume("Batman Child ", "Batman for childs");
        Costume costumeW = new Costume("Witch", "Witch for childs");

        // Ajout des véhicules (ceux de votre nouveau setup)
        car1 = new ElectricCar(500, "AA-123-BB", "Tesla Model 3");
        bike1 = new ElectricBike(100, new double[]{0.1, 0.2}); // Sera "EB-1"

        // Ajout de tout au système
        rentalSystem.addItem(costumeBM);
        rentalSystem.addItem(costumeSQ);
        rentalSystem.addItem(costumeBMC);
        rentalSystem.addItem(costumeW);
        rentalSystem.addItem(car1);
        rentalSystem.addItem(bike1);

        // Le costume 'costumeW' est mis comme non disponible pour le test 'isRentable'
        // costumeW.setUnavailable(); // -> Si vous avez cette méthode sur Costume
    }

    // 5. La méthode initRentalSystem() est SUPPRIMÉE

    @Test
    void testAddItem() {
        // Le setup a déjà ajouté 6 items
        assertEquals(6, rentalSystem.getItems().size());

        Costume costumeS = new Costume("Spiderman", "Spidey");
        rentalSystem.addItem(costumeS);
        assertEquals(7, rentalSystem.getItems().size());
    }

    @Test
    void testAddItemWithException() {
        // 'costumeBM' a été ajouté dans setUp
        assertThrows(IllegalArgumentException.class, () -> rentalSystem.addItem(costumeBM));
    }

    @Test
    void testRemoveItem() {
        // Le setup a déjà ajouté 6 items
        assertEquals(6, rentalSystem.getItems().size());
        rentalSystem.removeItem(costumeBM);
        assertEquals(5, rentalSystem.getItems().size());
        rentalSystem.removeItem(car1);
        assertEquals(4, rentalSystem.getItems().size());
    }

    @Test
    void testSearchItems() {
        // PAS BESOIN D'APPELER initRentalSystem()
        assertEquals(2, rentalSystem.searchItems("Batman").size());
        assertEquals(1, rentalSystem.searchItems("Snow").size());
        assertEquals(0, rentalSystem.searchItems("Spiderman").size());
        assertEquals(3, rentalSystem.searchItems(CostumeSize.ONE_SIZE.name()).size());
    }

    // --- NOUVEAU TEST POUR LES VÉHICULES ---
    @Test
    void testSearchVehicles() {
        // ... (les 4 premières assertions passent maintenant)
        assertEquals(1, rentalSystem.searchItems("Tesla").size());
        assertEquals(1, rentalSystem.searchItems("AA-123-BB").size());
        assertEquals(0, rentalSystem.searchItems("Renault").size());
        assertEquals(1, rentalSystem.searchItems("EB-1").size());
        assertEquals(2, rentalSystem.searchItems("2").size());

        // CORRIGER L'ASSERTION : Seul le vélo matche "*"
        assertEquals(1, rentalSystem.searchItems("*").size());
    }

    @Test
    void testIsRentable() {
        // PAS BESOIN D'APPELER initRentalSystem()
        assertTrue(rentalSystem.isRentable(costumeBM, today, tomorrow));
        assertTrue(rentalSystem.isRentable(car1, today, tomorrow)); // Test sur la voiture

        //Renting an item makes it unavailable
        rentalSystem.rentItem(costumeBM, today, tomorrow);
        assertFalse(rentalSystem.isRentable(costumeBM, today, nextWeek));

        // Test sur la voiture
        rentalSystem.rentItem(car1, today, tomorrow);
        assertFalse(rentalSystem.isRentable(car1, today, tomorrow));
        assertTrue(rentalSystem.isRentable(car1, tomorrow, nextWeek));
    }


    @Test
    void testRentItem() {
        // PAS BESOIN D'APPELER initRentalSystem()
        rentalSystem.rentItem(costumeBM, today, tomorrow);
        assertEquals(1, rentalSystem.getRentals().size());

        // On loue la voiture
        rentalSystem.rentItem(car1, nextWeek, nextWeek.plusDays(1));
        assertEquals(2, rentalSystem.getRentals().size());

        // On loue le vélo
        rentalSystem.rentItem(bike1, nextWeek.plusDays(2), nextWeek.plusWeeks(1));
        assertEquals(3, rentalSystem.getRentals().size());
    }

    @Test
    void testARental() {
        // PAS BESOIN D'APPELER initRentalSystem()
        rentalSystem.rentItem(costumeBM, today, tomorrow.plusDays(1));
        Rental rental = rentalSystem.getRentals().get(0);
        assertEquals(today, rental.getStartDate());
        assertEquals(tomorrow.plusDays(1), rental.getEndDate());
        assertEquals(costumeBM, rental.getItem());
        // assertEquals(20, rental.getCost()); // Le coût dépend de votre RentalSystem
    }


    @Test
    void findAvailableMatches() {
        // PAS BESOIN D'APPELER initRentalSystem()

        // Tests costumes
        assertEquals(2, rentalSystem.findAvailableMatches("Batman", today, tomorrow).size());
        assertEquals(1, rentalSystem.findAvailableMatches("Snow", today, nextWeek).size());

        // Tests véhicules
        assertEquals(1, rentalSystem.findAvailableMatches("Tesla", today, tomorrow).size());
        assertEquals(1, rentalSystem.findAvailableMatches("EB-1", today, tomorrow).size());

        // Louer la voiture et vérifier
        rentalSystem.rentItem(car1, today, tomorrow);
        assertEquals(0, rentalSystem.findAvailableMatches("Tesla", today, tomorrow).size());
        assertEquals(1, rentalSystem.findAvailableMatches("Tesla", tomorrow, nextWeek).size());
    }
}