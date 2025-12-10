package fr.pns.rentals;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class RentalSystem<T extends RentableItem> {

    // Stockage des items (T) et des locations (Rental)
    private Map<String, T> items;
    private Map<String, List<Rental>> rentalsByItem;

    private static final double DEFAULT_COST_PER_DAY = 10.0;
    private double defaultCostPerDay;

    // Constructeurs
    public RentalSystem() {
        this(DEFAULT_COST_PER_DAY);
    }

    public RentalSystem(double costPerDay) {
        this.items = new HashMap<>();
        this.rentalsByItem = new HashMap<>();
        this.defaultCostPerDay = costPerDay;
    }

    // Gestion des Items
    public void addItem(T item) {
        if (items.containsKey(item.getName())) {
            throw new IllegalArgumentException("An item with the same name already exists");
        }
        items.put(item.getName(), item);
        rentalsByItem.put(item.getName(), new ArrayList<>());
    }

    public void removeItem(T item) {
        items.remove(item.getName());
        rentalsByItem.remove(item.getName());
    }

    // Gestion de la location (AVEC EXCEPTIONS)
    public boolean isRentable(T item, LocalDate beginDate, LocalDate endDate) throws NoSuchItemException {
        // Vérification de l'existence
        if (!items.containsKey(item.getName())) {
            throw new NoSuchItemException(item);
        }

        if (!item.isAvailable()) {
            return false;
        }

        // Vérification des dates
        List<Rental> rentals = rentalsByItem.get(item.getName());
        for (Rental rental : rentals) {
            if (rental.getStartDate().isBefore(endDate) && rental.getEndDate().isAfter(beginDate)) {
                return false; // Chevauchement
            }
        }
        return true;
    }

    public boolean rentItem(T item, LocalDate beginDate, LocalDate endDate) throws NoSuchItemException {
        return rentItem(item, beginDate, endDate, computeCost(beginDate, endDate));
    }

    public boolean rentItem(T item, LocalDate beginDate, LocalDate endDate, double cost) throws NoSuchItemException {
        // L'appel à isRentable peut lever l'exception, on la laisse remonter (throws)
        if (!isRentable(item, beginDate, endDate)) {
            return false;
        }

        Rental rental = new Rental(item, beginDate, endDate, cost);
        List<Rental> itemRentals = rentalsByItem.get(item.getName());
        itemRentals.add(rental);

        // On trie les locations par date pour rester propre
        itemRentals.sort(Comparator.comparing(Rental::getStartDate));

        return true;
    }

    // Recherche
    public List<T> searchItems(String description) {
        List<T> matchingItems = new ArrayList<>();
        for (T item : items.values()) {
            if (item.match(description)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }

    public List<T> findAvailableMatches(String description, LocalDate beginDate, LocalDate endDate) {
        List<T> matchingItems = searchItems(description);
        List<T> availableItems = new ArrayList<>();

        for (T item : matchingItems) {
            try {
                // Ici on attrape l'exception car on est dans une boucle de recherche
                if (isRentable(item, beginDate, endDate)) {
                    availableItems.add(item);
                }
            } catch (NoSuchItemException e) {
                // Ce cas ne devrait pas arriver car searchItems renvoie des items qui sont dans la map
                throw new RuntimeException("Erreur inattendue : item trouvé par recherche mais introuvable pour vérification", e);
            }
        }
        return availableItems;
    }

    // Utilitaires
    public List<T> getItems() {
        return new ArrayList<>(items.values());
    }

    private double computeCost(LocalDate beginDate, LocalDate endDate) {
        return Period.between(beginDate, endDate).getDays() * defaultCostPerDay;
    }

    // Pour les tests
    public List<Rental> getRentals() {
        List<Rental> allRentals = new ArrayList<>();
        for (List<Rental> list : rentalsByItem.values()) {
            allRentals.addAll(list);
        }
        return allRentals;
    }
}