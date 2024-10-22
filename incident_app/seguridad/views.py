import matplotlib.pyplot as plt
import pandas as pd
from io import BytesIO
import base64
from django.shortcuts import render, get_object_or_404
from django.http import HttpResponse
from .models import Incidente, Actor, TipoAtaque, Region

# Generar gráficos basados en los incidentes
def generar_graficos():
    # Obtener datos de la base de datos
    incidentes = Incidente.objects.all()
    
    if not incidentes.exists():
        return None  # No hay incidentes para mostrar
    
    # Convertir los datos a listas, asegurando que todas las listas tienen la misma longitud
    actores = [incidente.actor.name for incidente in incidentes]
    tipos_ataque = [incidente.tipo_ataque.tipo for incidente in incidentes]
    regiones = [incidente.region.name for incidente in incidentes]
    anios = [incidente.year for incidente in incidentes]
    motivos = [incidente.motive if incidente.motive else 'N/A' for incidente in incidentes]

    # Crear el DataFrame
    data = {
        'actor': actores,
        'tipo_ataque': tipos_ataque,
        'region': regiones,
        'year': anios,
        'motive': motivos
    }
    
    df = pd.DataFrame(data)
    
    if df.empty:
        return None  # Si el DataFrame está vacío, no hay nada que graficar
    
    # Gráfico de número de incidentes por año
    incidentes_por_ano = df.groupby('year').size()
    
    if incidentes_por_ano.empty:
        return None  # Si no hay datos agrupados, no crear gráfico
    
    fig, ax = plt.subplots()
    incidentes_por_ano.plot(kind='bar', ax=ax)
    ax.set_title('Incidentes por Año')
    ax.set_xlabel('Año')
    ax.set_ylabel('Número de Incidentes')
    
    # Guardar el gráfico en memoria como PNG
    buffer = BytesIO()
    plt.savefig(buffer, format='png')
    buffer.seek(0)
    image_png = buffer.getvalue()
    buffer.close()
    
    # Convertir la imagen a base64 para mostrarla en HTML
    grafico_base64 = base64.b64encode(image_png).decode('utf-8')
    return grafico_base64

# Vista para mostrar la página de inicio con gráficos
def inicio(request):
    # Generar el gráfico
    grafico_incidentes = generar_graficos()
    
    # Si no hay gráfico, mostrar un mensaje
    if grafico_incidentes is None:
        return render(request, 'seguridad/inicio.html', {
            'mensaje': 'No hay suficientes datos para generar el gráfico.'
        })
    
    # Pasar el gráfico a la plantilla
    return render(request, 'seguridad/inicio.html', {
        'grafico_incidentes': grafico_incidentes
    })

# Vista para descargar los datos como CSV para su uso en Machine Learning
def descargar_datos(request):
    # Obtener los datos de la base de datos
    incidentes = Incidente.objects.all()
    
    if not incidentes.exists():
        return HttpResponse("No hay datos disponibles para descargar.", content_type="text/plain")
    
    # Convertir los datos a CSV
    data = {
        'actor': [incidente.actor.name for incidente in incidentes],
        'tipo_ataque': [incidente.tipo_ataque.tipo for incidente in incidentes],
        'region': [incidente.region.name for incidente in incidentes],
        'year': [incidente.year for incidente in incidentes],
        'motive': [incidente.motive if incidente.motive else 'N/A' for incidente in incidentes]
    }
    df = pd.DataFrame(data)
    
    # Crear la respuesta como CSV
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename="datos_incidentes.csv"'
    df.to_csv(path_or_buf=response, index=False)
    
    return response

# Vista para listar incidentes por actor
def incidentes_por_actor(request, actor_id):
    actor = get_object_or_404(Actor, id=actor_id)
    incidentes = Incidente.objects.filter(actor=actor)
    return render(request, 'seguridad/incidentes_por_actor.html', {'actor': actor, 'incidentes': incidentes})

# Vista para listar incidentes por tipo de ataque
def incidentes_por_tipo_ataque(request, tipo_id):
    tipo_ataque = get_object_or_404(TipoAtaque, id=tipo_id)
    incidentes = Incidente.objects.filter(tipo_ataque=tipo_ataque)
    return render(request, 'seguridad/incidentes_por_tipo.html', {'tipo_ataque': tipo_ataque, 'incidentes': incidentes})

# Vista para listar incidentes por región
def incidentes_por_region(request, region_id):
    region = get_object_or_404(Region, id=region_id)
    incidentes = Incidente.objects.filter(region=region)
    return render(request, 'seguridad/incidentes_por_region.html', {'region': region, 'incidentes': incidentes})


