<h1 align="center" style="color: #4285F4"> Medii de proiectare si programare - Proiect C# </h1>

## <span style="color: #4285F4"> Problema 2 - Cerinta

Organizatorii unui festival de muzica au mai multe oficii în țară unde se vând bilete la festival. La fiecare oficiu se folosește un sistem soft pentru a vinde bilete. Persoana de la fiecare oficiu folosește o aplicație desktop cu următoarele funcționalități:

1. **<i>Login</i>**: După autentificarea cu succes, o nouă fereastră se deschide în care sunt afișați toți artiștii (numele, data și locul spectacolului, numărul de locuri disponibile și numărul de locuri deja vândute). Un artist poate susține mai multe spectacole.
2. **<i>Căutare</i>**: După autentificarea cu succes, persoana de la oficiu poate căuta artiștii care au spectacol într-o anumită zi. Aplicația va afișa în altă listă/alt tabel toți artiștii care au spectacol în acea zi, locația, ora începerii și numărul de locuri disponibile.
3. **<i>Cumpărare</i>**: Angajatul poate vinde bilete pentru un anumit spectacol. Pentru vânzare se introduce
   numele cumpărătorului și numărul de locuri dorite. După vânzare toți angajații de la toate oficiile văd
   lista actualizată a spectacolelor. Dacă la un spectacol nu mai sunt locuri disponibile, spectacolul va fi
   afișat cu culoare roșie.
4. **<i>Logout</i>**

## <span style="color: #4285F4"> Structura solutiei

1. **Dao**: Acest pachet conține clasele care definesc accesul la date pentru entitățile proiectului.
    - **Model**: Aici sunt definite entitățile relevante pentru proiect:
        - **User**:
            - `UserId` (tip: long): Identificatorul unic al utilizatorului.
            - `Username` (tip: string): Numele de utilizator al utilizatorului.
            - `Password` (tip: string): Parola utilizatorului.
        - **Spectacle**:
            - `SpectacleId` (tip: long): Identificatorul unic al spectacolului.
            - `ArtistName` (tip: string): Numele artistului sau trupei care susține spectacolul.
            - `Date` (tip: Date): Data spectacolului.
            - `Place` (tip: String): Locul unde are loc spectacolul.
            - `SeatsAvailable` (tip: long): Numărul de locuri disponibile pentru spectacol.
            - `SeatsSold` (tip: long): Numărul de locuri vândute pentru spectacol.

        - **Order**:
            - `OrderId` (tip: long): Identificatorul unic al comenzii.
            - `BuyerName` (tip: string): Numele cumpărătorului.
            - `Spectacle` (tip: Spectacle): Referință către spectacolul pentru care s-a făcut comanda.
            - `NumberOfSeats` (tip: long): Numărul de locuri achiziționate în cadrul comenzii.
    - **IRepository**: Aici sunt definite interfețele pentru repository-urile asociate fiecărei entități.
