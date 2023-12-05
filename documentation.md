## Model bazy danych składa się z 3 pakietów: books, loans, users.

### Pakiet users
Pakiet ten składa się z abstrakcyjnej klasy User, w której mamy podstawowe informacje o każdym użytkowniku tj. id, firstName, lastName, email, joinDate, expirationDate. Klasy Admin, Librarian, Member dziedziczą po klasie User, (w bazie danych są osobne tabele połączone 1 do 1 z User) oraz mają swoje pola specyficzne dla danego typu użytkownika. 
* Member posiada pole newsletter informujące czy dana osoba chce odszymywać.
* Libriarian posiada pole room informujące o numerze pokoju, w którym pracuje.
* Admin posiada pola room oraz phoneNumber.

### Pakiet books
Pakiet ten składa się z enumeratora CoverType pozwalającego określić czy dany egzemplaż książki jest w twardej czy w miękkiej oprawie. Klasy Title z polami title_id, isbn, title, author, reprezentuje ona dany tytuł dostępny w bibliotece. Klasa Book posiada pola book_id, coverType, loan, historicalLoans, title.

### Pakiet loans
Pakiet ten składa się z klasy Loan z polami loanId, startLoanDate, endLoanDate, member, book, user, klasa ta reprezentuje wypożyczenie książki przez użytkownika. Klasa HistoricalLoan z polami loanId, startLoanDate, endLoanDate, returLoanDate, member, book, jest klasą reprezentującą historię wypożyczeń książek.


## Pakiet repository
W pakiecie repository znajdują się analogiczne pakiety jak w pakiecie model. W tych analogicznych pakietach mamy interfejsy pozwalające na komunikację warstwy usług z bazą danych.


## Pakiet validator
W pakiecie validator znajduje się klasa UserValidator pozwalająca na walidację danych wprowadzanych przez użytkownika. Sprawdzane są email, imie i nazwisko.
* email musi zacząć się od ciągu znaków alfanumerycznych po których nastąpi znak '@', następnie dowolna sekcja nazw domenowych oddzielone kropką, ostatnia sekwencja domeny może składać się tylko z małych i dużych liter
* imie musi zaczynać się z dużej litery i może zawierać tylko litery, długość imienia musi się mieścić w przedziale [2, 30]
* nazwisko musi zaczynać się z dużej litery i może zawierać tylko litery, może to być nazwisko podwójne wtedy kolejne człony przedzielone są zankiem pauzy '-,' długość nazwiska musi się mieścić w przedziale [2, 30]