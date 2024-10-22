# DeNexus-Project

incident_app/
├── incident_app/                  # Carpeta del proyecto principal de Django
│   ├── __init__.py
│   ├── asgi.py
│   ├── settings.py                # Configuración del proyecto
│   ├── urls.py                    # Rutas principales del proyecto
│   ├── wsgi.py
│   └── __pycache__/
│
├── seguridad/                     # Aplicación de Django donde trabajamos con los datos
│   ├── migrations/                # Migraciones de base de datos
│   │   ├── __init__.py
│   │   └── (otros archivos de migraciones)
│   ├── templates/                 # Plantillas HTML de la aplicación
│   │   └── seguridad/             # Carpeta para las plantillas de la app
│   │       └── lista_incidentes.html  # Plantilla para listar incidentes
│   ├── __init__.py
│   ├── admin.py                   # Configuración del admin de Django
│   ├── apps.py                    # Configuración de la aplicación
│   ├── models.py                  # Definición de los modelos (HACKMAGEDDON y CISSM)
│   ├── views.py                   # Vistas para cargar CSV y mostrar datos
│   ├── urls.py                    # Rutas específicas de la app seguridad
│   ├── tests.py                   # Tests unitarios para la app
│   └── __pycache__/
│
├── data/                          # Carpeta donde se guardan los archivos CSV
│   ├── HACKMAGEDDON_clean.csv      # Archivo CSV con datos de HACKMAGEDDON
│   └── CISSM_clean.csv             # Archivo CSV con datos de CISSM
│
├── manage.py                      # Script principal de Django para gestionar el proyecto
├── db.sqlite3                     # Base de datos SQLite (si estás usando SQLite)
├── requirements.txt               # Dependencias del proyecto
└── static/                        # Carpeta para archivos estáticos (si los utilizas)
    └── (archivos estáticos)


Pasos para la creación de la aplicación
1. Crear el proyecto y la aplicación:

Bash
django-admin startproject incident_app
cd incident_app
python manage.py startapp seguridad
Usa el código con precaución.

2. Definir los modelos:

Python
# seguridad/models.py
from django.db import models

# Modelo para HACKMAGEDDON
class HackmageddonIncident(models.Model):
    # ... (código del modelo) ...

# Modelo para CISSM
class CissmIncident(models.Model):
    # ... (código del modelo) ...
Usa el código con precaución.

3. Migrar los modelos:

Bash
python manage.py makemigrations
python manage.py migrate
Usa el código con precaución.

4. Configurar el panel de administración:

Python
# seguridad/admin.py
from django.contrib import admin
from .models import HackmageddonIncident, CissmIncident

admin.site.register(HackmageddonIncident)
admin.site.register(CissmIncident)
Usa el código con precaución.

5. Crear vistas para cargar los datos:

Python
# seguridad/views.py
import csv
from django.http import HttpResponse
from .models import HackmageddonIncident, CissmIncident

# ... (código de las vistas) ...
Usa el código con precaución.

6. Configurar las URLs:

Python
# seguridad/urls.py
from django.urls import path
from . import views

urlpatterns = [
    path('cargar_datos_hackmageddon/', views.cargar_datos_hackmageddon, name='cargar_datos_hackmageddon'),
    path('cargar_datos_cissm/', views.cargar_datos_cissm, name='cargar_datos_cissm'),
]
Usa el código con precaución.

7. Crear las plantillas:

HTML
<!DOCTYPE html>
<html lang="es">
<head>
    </head>
<body>
    <h1>Incidentes de HACKMAGEDDON</h1>
    <ul>
    {% for incidente in hackmageddon_incidentes %}
        <li>{{ incidente.author }} - {{ incidente.description }} ({{ incidente.year }})</li>
    {% endfor %}
    </ul>

    <h1>Incidentes de CISSM</h1>
    <ul>
    {% for incidente in cissm_incidentes %}
        <li>{{ incidente.actor }} - {{ incidente.event_description }} ({{ incidente.year }})</li>
    {% endfor %}
    </ul>
</body>
</html>
Usa el código con precaución.

8. Añadir los archivos CSV:

Colocar los archivos HACKMAGEDDON_clean.csv y CISSM_clean.csv en la carpeta data/ dentro del proyecto Django.

9. Probar la aplicación:

Bash
python manage.py runserver

Paso 10: Probar la Aplicación
Ejecuta el servidor de desarrollo de Django:
bash
Copiar código
python manage.py runserver
Accede a las URLs para importar los datos desde los CSV:
Para cargar los datos de HACKMAGEDDON:


http://127.0.0.1:8000/cargar_datos_hackmageddon/
Para cargar los datos de CISSM:


http://127.0.0.1:8000/cargar_datos_cissm/
Gestiona los datos desde el panel de administración:

http://127.0.0.1:8000/admin/