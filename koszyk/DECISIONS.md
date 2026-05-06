# Decyzje Architektoniczne i Wykorzystane Wzorce - JavaMarkt

## 1. Wybór Wzorca Projektowego (Promocje)
Zastosowano wzorzec projektowy **Strategy (Strategia)**, reprezentowany przez interfejs `ICartPromotion` oraz implementujące go klasy (np. `ValuePromotion`, `Buy2Get1FreePromotion`).
**Uzasadnienie wyboru:** Promocje wpływają na sposób wyliczania końcowej ceny produktów w koszyku i mogą być dynamicznie dodawane, usuwane lub łączone w trakcie działania programu. Dzięki zastosowaniu wzorca Strategii, dodanie nowej promocji nie wymaga żadnych zmian w klasie `Cart`, co w pełni satysfakcjonuje zasadę **Open/Closed Principle (OCP)** z paradygmatu SOLID.

## 2. Enkapsulacja i Mutowalność (Klasa Product)
Zgodnie z dobrymi praktykami obiektowymi, klasa `Product` została zaprojektowana jako **częściowo niemutowalna (immutable)**.
Pola `code`, `name` oraz `price` (cena bazowa) są oznaczone jako `final` i inicjalizowane wyłącznie w konstruktorze. Nie posiadają metod typu setter.
**Uzasadnienie:** Cechy fizyczne produktu nie zmieniają się w trakcie jego przebywania w koszyku. Jedynym mutowalnym polem jest `discountPrice` (cena po rabatach), ponieważ aplikowane promocje (Strategie) muszą mieć możliwość nadpisania aktualnej ceny w ramach obliczeń koszyka. Zachowanie stałej ceny `price` pozwala dodatkowo na pokazanie klientowi pierwotnej wartości zamówienia (np. do wyliczenia łącznego zaoszczędzonego kapitału).

## 3. Złożoność Algorytmiczna Wyszukiwania
Podczas implementacji funkcji wyszukiwania (`getCheapest`, `getNCheapest`) wykorzystano mechanizmy Java Streams:
* **Wyszukiwanie pojedynczego elementu:** Używa operacji terminalnych `min()` i `max()`. Algorytm potrzebuje dokładnie jednego przebiegu po kolekcji (złożoność **O(n)**).
* **Wyszukiwanie *n* elementów:** Używa pełnego sortowania (`.sorted()`), bazującego na algorytmie TimSort, a następnie ucina strumień operatorem `limit(n)`. Złożoność wynosi **O(n log n)**. Alternatywą o niższej złożoności **O(n log k)** byłoby użycie kolejki priorytetowej (Kopca), jednak z racji ograniczonych rozmiarów koszyków zakupowych wybrano rozwiązanie czytelniejsze (Streams).

## 4. Sortowanie a Dependency Inversion Principle
Metoda sortująca `sortProducts(Comparator<Product> comparator)` przyjmuje gotowy interfejs zamiast deklarować zasady sortowania wewnątrz. Pozwala to na pełną modyfikowalność zasad układania elementów na zewnątrz klasy (np. najpierw cena, potem alfabet) bez modyfikacji kodu koszyka.