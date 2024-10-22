from django.urls import path
from . import views

urlpatterns = [
    path('cargar_datos_hackmageddon/', views.cargar_datos_hackmageddon, name='cargar_datos_hackmageddon'),
    path('cargar_datos_cissm/', views.cargar_datos_cissm, name='cargar_datos_cissm'),
]
