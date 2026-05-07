### Uwaga 2 - Wzorzec projektowy promocji
Wzorzec projektowy Strategy, lepiej odpowiada problemowi z zadania:
 - Promocje można dodawać i zabierać, dzięki czemu zachowane jest 'Open/Closed Principle'

Implementacja nie potrzebuje; kolejkowania działań, ani możliwości cofnięć.

Wybrany styl implementacji promocji jest bezstanowy, stąd wybór wzorca 'Strategy' 

### Uwaga 4 - Mutowalmość klasy Product
Klasa Product nie powinna być mutowalna:
 - sugerowane ceny produktów są stałe, więc nie powinny się zmieniać po stworzeniu produktu.
 - promocje nakładane są poprzez stworzenie nowej instancji produktu z uaktualnioną ceną promocyjną.
