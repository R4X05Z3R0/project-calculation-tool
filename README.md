# ProjectCalculationTool
Eksamensprojekt for 2. semester datamatiker F2026 I skal lave et projektkalkulationsværktøj for jeres kravstiller (kunde) Alpha Solutions. Kravene til systemet er blevet præsenteret til kickoff onsdag 29/4 og beskrevet i slides fra Alpha Solutions projektpræsentation. 
# ASPCT — Projektkalkulationsværktøj

ASPCT er et estimerings- og ressourceplanlægningsværktøj bygget til Alpha Solutions. Projektledere kan opdele arbejde i projekter, subprojekter og opgaver med tidsestimater, tildele medarbejdere til projekter og få realistiske tids- og prisberegninger baseret på arbejdsstyrkens kapacitet og arbejdsdage.

Applikationen er deployet og tilgængelig her: [ASPCT Live](https://aspct-bzb8asaafucadrdk.swedencentral-01.azurewebsites.net/projects/)


## Arkitektur

```
src/main/java/com/example/aspct/
├── controller/          # HTTP-routing (Project, SubProject, Task, Archive)
├── service/             # Forretningslogik (inkl. PlannerService til planlægning)
├── repository/          # JDBC-baseret dataadgang
├── mapper/              # Spring RowMapper-implementeringer
├── model/               # Domæneobjekter
└── exceptions/          # Egne exceptions (InvalidDeadlineException, ResourceNotFoundException)

src/main/resources/
├── templates/           # Thymeleaf-skabeloner (create/, edit/, views/)
├── db/
│   ├── schema.sql       # Tabeldefinitioner
│   └── data.sql         # Testdata
├── application.properties          # Standardprofil (dev)
├── application-dev.properties      # Dev-overrides
└── application-prod.properties     # Produktions-overrides
```

## Datamodel

```
project  ──<  subproject  ──<  task  >──  competency
   │
   └──<  project_employee  >──  employee  >──  competency
```

Et projekt tilhører en virksomhed, har en deadline og kan arkiveres (soft delete). Hvert projekt indeholder subprojekter, som indeholder opgaver. Opgaver har tidsestimater og kan referere til en kompetence (f.eks. "Backend Developer" eller "Cloud Architect"). 

Kompetencer er dog ikke implementeret endnu. 

Medarbejdere har en kompetence og en daglig timekapacitet. De tildeles projekter via project_employee, som udgør projektets arbejdsstyrke. Denne arbejdsstyrke driver plannerens beregninger.

## Kernefunktionalitet

Projekthierarki: Opret, rediger og slet projekter, subprojekter og opgaver. Timer aggregeres fra opgaver op gennem subprojekter til det samlede projektestimat.

Arbejdsstyrke: Tilføj og fjern medarbejdere fra et projekt. Systemet viser hvem der er tilgængelig og hvem der allerede er tildelt.

Planner: Ud fra projektets samlede arbejdsbyrde og arbejdsstyrkens daglige kapacitet beregner planneren antal nødvendige arbejdsdage (weekender ekskluderet), en forventet færdiggørelsesdato fra i dag, om projektet er on track i forhold til deadline, hvor mange arbejdsdage det eventuelt overskrider, og et standardprisestimat (timer × 250 DKK/time).

Arkiv: Soft-delete af projekter til en arkivvisning. Arkiverede projekter kan gendannes eller slettes permanent.

Deadline-validering: Projekter, subprojekter og opgaver afviser deadlines i fortiden.

## Profiler

Standardprofilen bruger MySQL via `application.properties` til lokal udvikling. `prod`-profilen bruger MySQL via `application-prod.properties` til Azure-produktion, med credentials styret via Azure-miljøvariabler (`PROD_DATABASE`, `PROD_USERNAME`, `PROD_PASSWORD`). `test`-profilen bruger H2 via `application-test.properties` til automatiserede tests.

## CI/CD

Projektet bruger GitHub Actions: Maven CI (`maven_ci.yml`) bygger og kører tests ved push og PR til `main`. Azure Deploy (`main_aspct.yml`) deployer til Azure App Service ved push til `main`. Qodana (`code_quality.yml`) kører statisk kodeanalyse ved PR og push.

## Bidrag

Se [CONTRIBUTING.md](CONTRIBUTING.md) for workflow, branching-strategi og kodestandarder.
