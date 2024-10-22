import csv
from .models import Actor, TipoAtaque, Region, Incidente
import pandas as pd
from django.db.utils import IntegrityError

def cargar_datos_hackmageddon():
    try:
        print("Iniciando la carga de datos de HACKMAGEDDON...")
        with open('data/HACKMAGEDDON_clean.csv', 'r') as file:
            reader = csv.DictReader(file)
            for row in reader:
                print(f"Procesando fila: {row}")
                # Manejo de valores por defecto para la región
                region_name = row.get('Region', 'Unknown')
                region, _ = Region.objects.get_or_create(name=region_name)
                print(f"Región: {region_name}")

                # Obtener o crear Actor y Tipo de Ataque
                actor, _ = Actor.objects.get_or_create(name=row.get('Author', 'Unknown'), actor_type=row.get('Actor Type', 'Unknown'))
                print(f"Actor: {actor.name}")
                
                tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row.get('Attack', 'Unknown'))
                print(f"Tipo de ataque: {tipo_ataque.tipo}")

                # Convertir la fecha a un formato adecuado
                try:
                    fecha_incidente = pd.to_datetime(row.get('Date Occurred', 'Unknown'), errors='coerce').date()
                    print(f"Fecha incidente: {fecha_incidente}")
                except Exception as e:
                    print(f"Error al parsear la fecha en la fila {row}: {e}")
                    fecha_incidente = None

                # Crear o actualizar el incidente
                Incidente.objects.get_or_create(
                    actor=actor,
                    tipo_ataque=tipo_ataque,
                    region=region,
                    year=row.get('Year', 2023),  # Si no hay año, usar 2023 por defecto
                    description=row.get('Description', ''),
                    event_date=fecha_incidente
                )
                print("Incidente creado o actualizado correctamente")

    except FileNotFoundError as e:
        print(f"Archivo no encontrado: {e}")
    except Exception as e:
        print(f"Error al procesar los datos: {e}")

def cargar_datos_cissm():
    try:
        print("Iniciando la carga de datos de CISSM...")
        with open('data/CISSM_clean.csv', 'r') as file:
            reader = csv.DictReader(file)
            for row in reader:
                print(f"Procesando fila: {row}")
                # Manejo de valores por defecto para la región
                region_name = row.get('Region', 'Unknown')
                region, _ = Region.objects.get_or_create(name=region_name)
                print(f"Región: {region_name}")

                # Obtener o crear Actor y Tipo de Ataque
                actor, _ = Actor.objects.get_or_create(name=row.get('Actor', 'Unknown'), actor_type=row.get('Actor Type', 'Unknown'))
                print(f"Actor: {actor.name}")
                
                tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row.get('Event Type', 'Unknown'))
                print(f"Tipo de ataque: {tipo_ataque.tipo}")

                # Convertir la fecha a un formato adecuado
                try:
                    fecha_incidente = pd.to_datetime(row.get('Event Date', 'Unknown'), errors='coerce').date()
                    print(f"Fecha incidente: {fecha_incidente}")
                except Exception as e:
                    print(f"Error al parsear la fecha en la fila {row}: {e}")
                    fecha_incidente = None

                # Crear o actualizar el incidente
                Incidente.objects.get_or_create(
                    actor=actor,
                    tipo_ataque=tipo_ataque,
                    region=region,
                    year=row.get('Year', 2023),  # Si no hay año, usar 2023 por defecto
                    description=row.get('Event Description', ''),
                    event_date=fecha_incidente
                )
                print("Incidente creado o actualizado correctamente")

    except FileNotFoundError as e:
        print(f"Archivo no encontrado: {e}")
    except Exception as e:
        print(f"Error al procesar los datos: {e}")
