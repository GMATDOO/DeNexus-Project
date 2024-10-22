from django.contrib import admin
from .models import Actor, TipoAtaque, Region, Organization, Incidente

# Registrar las dimensiones y la tabla de hechos
admin.site.register(Actor)
admin.site.register(TipoAtaque)
admin.site.register(Region)
admin.site.register(Organization)
admin.site.register(Incidente)


