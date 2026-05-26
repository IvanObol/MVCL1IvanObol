# Kinoza — System Rezerwacji Biletów Kinowych

## 1. Opis projektu
Projekt „Kinoza” (NAZWA MOZE SIE ZMIENIC) to nowoczesna aplikacja webowa służąca do zarządzania procesem rezerwacji biletów w kinie. System umożliwia przeglądanie aktualnego repertuaru, interaktywny wybór miejsc na sali oraz prowadzenie osobistej biblioteki obejrzanych filmów wraz z ocenami.

**Technologie:**
* **Backend:** Java 17+, Spring Boot 3.x
* **Frontend:** Thymeleaf, HTML5, CSS3 (Bootstrap/Tailwind)
* **Baza danych:** PostgreSQL (wspierana przez Spring Data JPA)
* **Architektura:** MVC (Model-View-Controller)

## 2. Funkcjonalności (MVP)
1. **Strona główna:** Dynamiczna lista filmów pobierana z bazy danych[cite: 4].
2. **System rezerwacji:** Wybór konkretnego seansu (2D/3D) i interaktywny wybór miejsc na mapie sali[cite: 4].
3. **Logowanie i Profile:** System autoryzacji użytkowników[cite: 1, 4].
4. **Biblioteka użytkownika:** Katalog obejrzanych filmów z możliwością dodawania komentarzy i ocen (zgodnie z wymogiem Katalogu Kolekcji Filmów).
5. **System biletowy:** Generowanie potwierdzenia rezerwacji z unikalnym kodem QR[cite: 4].

## 3. Rozwiązania dodatkowe (Punkty bonusowe) (NIE WIEM CZY DOKONCZE)
* **Walidacja:** Pełna walidacja formularzy po stronie klienta i serwera[cite: 1].
* **UI/UX:** Dopracowany, schludny interfejs przygotowany na podstawie profesjonalnych makiet[cite: 1, 4].
* **Docker:** Konteneryzacja aplikacji (Dockerfile i docker-compose) dla łatwego wdrożenia[cite: 1, 3].

## 4. Instrukcja uruchomienia 
1. Sklonuj repozytorium: `git clone https://github.com/TwojUser/Kinoza.git`
2. Skonfiguruj bazę danych w `application.properties`.
3. Uruchom aplikację: `./mvnw spring-boot:run`
4. Aplikacja dostępna pod adresem: `http://localhost:8080`