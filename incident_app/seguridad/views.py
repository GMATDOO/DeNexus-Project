import csv
from django.http import HttpResponse
from .models import HackmageddonIncident, CissmIncident

# Vista para cargar los datos de HACKMAGEDDON desde CSV
def cargar_datos_hackmageddon(request):
    with open('data/HACKMAGEDDON_clean.csv', newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            HackmageddonIncident.objects.create(
                author=row['Author'],
                target=row['Target'],
                description=row['Description'],
                attack=row['Attack'],
                target_class=row['Target Class'],
                attack_class=row['Attack Class'],
                link=row['Link'],
                tags=row['Tags'],
                day=row['Day'],
                month=row['Month'],
                year=row['Year'],
                africa=row['Africa'],
                asia=row['Asia'],
                europe=row['Europe'],
                north_america=row['North America'],
                oceania=row['Oceania'],
                south_america=row['South America'],
            )
    return HttpResponse("Datos de HACKMAGEDDON importados correctamente.")

# Vista para cargar los datos de CISSM desde CSV
def cargar_datos_cissm(request):
    with open('data/CISSM_clean.csv', newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            CissmIncident.objects.create(
                id=row['id'],
                event_description=row['event_description'],
                event_date=row['event_date'],
                actor=row['actor'],
                actor_type=row['actor_type'],
                event_type=row['event_type'],
                organization=row['organization'],
                event_subtype=row['event_subtype'],
                motive=row['motive'],
                motive_code=row['motive_code'],
                event_source=row['event_source'],
                day=row['day'],
                month=row['month'],
                year=row['year'],
                africa=row['Africa'],
                asia=row['Asia'],
                australia=row['Australia'],
                europe=row['Europe'],
                north_america=row['North America'],
                south_america=row['South America'],
            )
    return HttpResponse("Datos de CISSM importados correctamente.")

