from django.contrib import admin
from .models import HackmageddonIncident, CissmIncident

# Registrar los modelos en el panel de administración
admin.site.register(HackmageddonIncident)
admin.site.register(CissmIncident)

