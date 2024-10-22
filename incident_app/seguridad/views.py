import matplotlib.pyplot as plt
import pandas as pd
from io import BytesIO
import base64
from django.shortcuts import render
from .models import Incidente

# Generar gráficos basados en los incidentes
def generar_graficos():
    # Obtener datos de la base de datos
    incidentes = Incidente.objects.all()
    
    # Convertir los datos a un DataFrame de Pandas para el análisis
    data = {
        'actor': [incidente.actor.name for incidente in incidentes],
        'tipo_ataque': [incidente.tipo_ataque.tipo for incidente in incidentes],
        'region': [incidente.region.name for incidente in incidentes],
        'year': [incidente.year for incidente in incidentes],
        'motive': [incidente.motive for incidente in incidentes if incidente.motive]
    }
    
    df = pd.DataFrame(data)
    
    # Gráfico de número de incidentes por año
    incidentes_por_ano = df.groupby('year').size()
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
    
    # Pasar el gráfico a la plantilla
    return render(request, 'seguridad/inicio.html', {
        'grafico_incidentes': grafico_incidentes
    })

