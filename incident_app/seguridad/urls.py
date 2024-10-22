from django.urls import path
from . import views

urlpatterns = [
    path('', views.inicio, name='inicio'),
    path('incidentes/actor/<int:actor_id>/', views.incidentes_por_actor, name='incidentes_por_actor'),
    path('incidentes/tipo/<int:tipo_id>/', views.incidentes_por_tipo_ataque, name='incidentes_por_tipo_ataque'),
    path('incidentes/region/<int:region_id>/', views.incidentes_por_region, name='incidentes_por_region'),
    path('descargar_datos/', views.descargar_datos, name='descargar_datos'),
]




