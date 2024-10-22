import csv
from django.db.models.signals import post_migrate
from django.dispatch import receiver
from .models import Actor, TipoAtaque, Region, Organization, Incidente

@receiver(post_migrate)
def cargar_datos_automaticamente(sender, **kwargs):
    cargar_datos_hackmageddon()
    cargar_datos_cissm()

def cargar_datos_hackmageddon():
    with open('data/HACKMAGEDDON_clean.csv', newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            actor, _ = Actor.objects.get_or_create(name=row['Author'], actor_type=row.get('Actor Type', 'Unknown'))
            tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row['Attack'])
            region, _ = Region.objects.get_or_create(name=row['Region'])

            Incidente.objects.get_or_create(
                description=row['Description'],
                event_date=f"{row['Day']}/{row['Month']}/{row['Year']}",
                day=row['Day'],
                month=row['Month'],
                year=row['Year'],
                actor=actor,
                tipo_ataque=tipo_ataque,
                region=region,
                motive=row.get('Motive', None),
                motive_code=row.get('Motive Code', None)
            )

def cargar_datos_cissm():
    with open('data/CISSM_clean.csv', newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            actor, _ = Actor.objects.get_or_create(name=row['actor'], actor_type=row['actor_type'])
            tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row['event_type'])
            region, _ = Region.objects.get_or_create(name=row.get('region', 'Unknown'))
            organization, _ = Organization.objects.get_or_create(name=row['organization'])

            Incidente.objects.get_or_create(
                description=row['event_description'],
                event_date=f"{row['day']}/{row['month']}/{row['year']}",
                day=row['day'],
                month=row['month'],
                year=row['year'],
                actor=actor,
                tipo_ataque=tipo_ataque,
                region=region,
                organization=organization,
                motive=row['motive'],
                motive_code=row['motive_code']
            )
