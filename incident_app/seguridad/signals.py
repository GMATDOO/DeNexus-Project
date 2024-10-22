import csv
from .models import Actor, TipoAtaque, Region, Incidente

def cargar_datos_hackmageddon():
    with open('/path/to/HACKMAGEDDON_clean.csv', 'r') as file:
        reader = csv.DictReader(file)
        for row in reader:
            # Si la clave 'Region' no existe, usa 'Unknown' como valor predeterminado
            region_name = row.get('Region', 'Unknown')
            
            # Obtén o crea la región con el valor predeterminado
            region, _ = Region.objects.get_or_create(name=region_name)
            
            # Carga otros datos del archivo de forma similar
            actor, _ = Actor.objects.get_or_create(name=row['Author'], actor_type=row.get('Actor Type', 'Unknown'))
            tipo_ataque, _ = TipoAtaque.objects.get_or_create(tipo=row.get('Attack', 'Unknown'))
            
            # Crear o actualizar el incidente
            Incidente.objects.get_or_create(
                actor=actor,
                tipo_ataque=tipo_ataque,
                region=region,
                year=row.get('Year', 2023),
                description=row.get('Description', ''),
                event_date=row.get('Date Occurred', 'Unknown')
            )
