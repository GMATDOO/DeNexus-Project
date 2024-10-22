import csv
import os
from datetime import datetime
from django.conf import settings
from django.db.models.signals import post_migrate
from django.dispatch import receiver
from .models import Actor, TipoAtaque, Region, Incidente

# Función para cargar los datos de HACKMAGEDDON
def cargar_datos_hackmageddon():
    ruta_csv = os.path.join(settings.BASE_DIR, 'data', 'HACKMAGEDDON_clean.csv')
    try:
        with open(ruta_csv, 'r') as file:
            reader = csv.DictReader(file)
            for row in reader:
                region_name = row.get('Region', 'Unknown')
                region, _ = Region.objects.get_or_create(name=region_name)

                actor, _ = Actor.objects.get_or_create(name=row.get('Author', 'Unknown'), actor_type=row.get('Actor Type', 'Unknown'))
                tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row.get('Attack', 'Unknown'))

                # Extraer la fecha y manejar errores
                event_date_str = row.get('Date Occurred', None)
                day, month, year = None, None, 2023  # Valores por defecto

                if event_date_str:
                    try:
                        event_date = datetime.strptime(event_date_str, '%Y-%m-%d')
                        day = event_date.day
                        month = event_date.month
                        year = event_date.year
                    except ValueError:
                        print(f"Error al convertir la fecha {event_date_str}, usando valores predeterminados.")

                # Crear o actualizar el incidente
                Incidente.objects.get_or_create(
                    actor=actor,
                    tipo_ataque=tipo_ataque,
                    region=region,
                    year=year,
                    month=month,
                    day=day,
                    description=row.get('Description', ''),
                    event_date=event_date_str or 'Desconocida'
                )
        print("Datos cargados correctamente de HACKMAGEDDON")
    except FileNotFoundError:
        print(f"El archivo {ruta_csv} no se encontró")
    except Exception as e:
        print(f"Error al cargar datos: {e}")

# Función para cargar los datos de CISSM
def cargar_datos_cissm():
    ruta_csv = os.path.join(settings.BASE_DIR, 'data', 'CISSM_clean.csv')
    try:
        with open(ruta_csv, 'r') as file:
            reader = csv.DictReader(file)
            for row in reader:
                region_name = row.get('Region', 'Unknown')
                region, _ = Region.objects.get_or_create(name=region_name)

                actor, _ = Actor.objects.get_or_create(name=row.get('Actor', 'Unknown'), actor_type=row.get('Actor Type', 'Unknown'))
                tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row.get('Event Type', 'Unknown'))

                # Extraer la fecha y manejar errores
                event_date_str = row.get('Event Date', None)
                day, month, year = None, None, 2023  # Valores por defecto

                if event_date_str:
                    try:
                        event_date = datetime.strptime(event_date_str, '%Y-%m-%d')
                        day = event_date.day
                        month = event_date.month
                        year = event_date.year
                    except ValueError:
                        print(f"Error al convertir la fecha {event_date_str}, usando valores predeterminados.")

                # Crear o actualizar el incidente
                Incidente.objects.get_or_create(
                    actor=actor,
                    tipo_ataque=tipo_ataque,
                    region=region,
                    year=year,
                    month=month,
                    day=day,
                    description=row.get('Event Description', ''),
                    event_date=event_date_str or 'Desconocida'
                )
        print("Datos cargados correctamente de CISSM")
    except FileNotFoundError:
        print(f"El archivo {ruta_csv} no se encontró")
    except Exception as e:
        print(f"Error al cargar datos: {e}")

# Conectar la función al evento post_migrate para que se carguen los datos al hacer migraciones
@receiver(post_migrate)
def cargar_datos_automaticamente(sender, **kwargs):
    cargar_datos_hackmageddon()
    cargar_datos_cissm()


